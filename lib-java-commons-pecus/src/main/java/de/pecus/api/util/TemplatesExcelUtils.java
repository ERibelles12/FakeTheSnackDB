package de.pecus.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.exception.PecusException;
import de.pecus.api.vo.ExcelSheetConfigVO;
import de.pecus.api.vo.GenerateDataTemplateRequestVO;


public class TemplatesExcelUtils {

	/**
	 * Genera en Base64 del excel lleno ocupando la plantilla 
	 * 
	 * @param listData
	 * @param template
	 * @param classVO
	 * @return String Excel en Base64
	 */
	public static String generateDataTemplate(GenerateDataTemplateRequestVO request) {
		Boolean hiddenExist = Boolean.FALSE;
		String base64File = new String();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream inReporte = new ByteArrayInputStream(Base64.getDecoder().decode(request.getTemplateBase64()));
			
		try {
			
			XSSFWorkbook book = new XSSFWorkbook(inReporte);
			
			for(ExcelSheetConfigVO sheetConfig : request.getSheetConfigList()) {
				fillSheet(book, sheetConfig, request, hiddenExist);
				//Oculta la hoja de catalogos
				if(sheetConfig.getSheetName().equals(GeneralConstants.SHEET_HIDDEN)) {
					hiddenExist = Boolean.TRUE;
					book.setSheetHidden(book.getSheetIndex(sheetConfig.getSheetName()), true);
				}
			}
			
		    book.setActiveSheet(0);
		    book.setSelectedTab(0);
		    HSSFFormulaEvaluator.evaluateAllFormulaCells(book);
		     
		    // Escribe los datos del libro en el arreglo de bytes   
		    book.write(baos);
		    book.close();
		     
		    //Escribe el Base64 que se utilizara
		    base64File = Base64.getEncoder().encodeToString(baos.toByteArray());

		} catch (Exception e) {
			throw new PecusException("Error al llenar template", e);
		}finally {
			closeFilesOpen(baos, inReporte);
		}
		
		return base64File;
	}
	
	
	private static void fillSheet(XSSFWorkbook book, ExcelSheetConfigVO sheetConfig, GenerateDataTemplateRequestVO request, Boolean hiddenExist) {
		XSSFSheet sheet = book.getSheet(sheetConfig.getSheetName());
		
		int initRowData = sheetConfig.getInitRowData().intValue();
		int initColData = sheetConfig.getInitColData().intValue();
		int dataSize;
		if(hiddenExist) { 
			if(ValidatorUtil.isNull(sheetConfig.getData()) || sheetConfig.getData().size() == 1) {
				dataSize = GeneralConstants.MAX_DATA;
			}else {
				dataSize = sheetConfig.getData().size();
			}
		} else {
			if(ValidatorUtil.isNull(sheetConfig.getData())){
				dataSize = 0;
			}else {
				dataSize = sheetConfig.getData().size();
			}
		}
		int newLastIndex = initRowData + dataSize;
   
		for (int i = initRowData + 1; i < newLastIndex ; i++) {
			sheet.shiftRows(i, i, 1);
	    	sheet.copyRows(initRowData, initRowData, i, new CellCopyPolicy());	
	    }
		
		List<XSSFTable> tables = sheet.getTables();
		if(!ValidatorUtil.isNullOrEmpty(tables)) {
			XSSFTable table = tables.get(0);

		    AreaReference newArea = new AreaReference(table.getStartCellReference(), new CellReference(newLastIndex, table.getEndColIndex()));
	        String newAreaStr = newArea.formatAsString();
	        
	        table.getCTTable().setInsertRow(true);
	        table.getCTTable().setInsertRowShift(true);	        
	        table.getCTTable().setRef(newAreaStr);
	        table.updateReferences();
	        table.updateHeaders();
	        if(hiddenExist){
	        	table.getCTTable().setTotalsRowShown(false);
	        }else {
	        	table.getCTTable().setTotalsRowShown(true);
	        }
		}
		
		fillParameters(sheet, request.getParams() , initRowData);
        
	    //llena los datos del Template    
	    Map<String, Object[]> newData = fillDataReport(sheetConfig.getData(), sheetConfig.getFieldNames());
        
	    // Escribe los datos en el Excel
	    writeDataInTemplate(newData , book , sheet, initRowData, initColData, dataSize);
	    
	}


	/**
	 * lee el contenido de una hoja de excel.
	 * 
	 * @param sheet
	 */
	private static void fillParameters(XSSFSheet sheet, Map<String, Object> params, Integer initDataRow ) {
		Iterator<Row> itr = sheet.iterator();
		 
		int numRow = 0;

		while (itr.hasNext() && numRow < initDataRow ) {
		    	
	        Row row = itr.next();
	        
	        Iterator<Cell> cellIterator = row.cellIterator();
	        while (cellIterator.hasNext()) {

	            Cell cell = cellIterator.next();
	            
	            replaceParam(params, sheet, row, cell);
	        }
	    }
	}


	private static void replaceParam(Map<String, Object> params, XSSFSheet sheet, Row row, Cell cell) {
		
		CellType type = cell.getCellTypeEnum();
		if(!type.equals(CellType.STRING)) {
			return;
		}
		String cellValue = cell.getStringCellValue();
		
		if (validateRegex(cellValue, GeneralConstants.VALIDAR_PARAMS_EXCEL)) {
			
			String paramName = extractParam(cellValue);
			
			if(params.containsKey(paramName)) {
				Object value = params.get(paramName);
				if(ValidatorUtil.isNull(value)) {
					value = "";
				}
				fillValue(value, cell, 0, 0, sheet);
			}
		}
	}


	private static String extractParam(String buscaParam) {

		return buscaParam.replace(GeneralConstants.PARAM_INI, GeneralConstants.EMPTY_STRING).replace(GeneralConstants.PARAM_END, GeneralConstants.EMPTY_STRING);
	}


	/**
	 * Valida una Regex
	 * 
	 * @param buscaParam
	 * @return true si cumple, false si no cumple
	 */
	private static boolean validateRegex(String buscaParam, String expression) {
		
		if(ValidatorUtil.isNullOrEmpty(buscaParam)) {
			return false;
		}
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(buscaParam);  
		return matcher.matches();
	}

	/**
	 * Escribe los Datos en el Excel.
	 * 
	 * @param listData
	 * @param headers
	 * @param sheet
	 */
	private static void writeDataInTemplate(Map<String, Object[]> newData, XSSFWorkbook book ,XSSFSheet sheet, Integer initRow, Integer initCol, Integer dataSize) {
		
		
		Set<String> newRows = newData.keySet();
		
	    int rownum = initRow;
	    
	    for (String key : newRows) {
	    	
	    	Row row = sheet.getRow(rownum);
	    	
	        Object[] objArr = newData.get(key);
	        
	        fillColumsExcel(row, objArr, initCol, initRow, dataSize, sheet);
	        
	        rownum++;
	        
	    }

	}

	/**
	 * Llena la fila y las columnas.
	 * 
	 * @param row
	 * @param objArr
	 */
	private static void fillColumsExcel(Row row, Object[] objArr, Integer initCol, Integer initRow, Integer size, XSSFSheet sheet) {
		
		int cellnum = initCol;
		
		for (Object obj : objArr) {
			
			Cell cell = row.getCell(cellnum);
			
			if(ValidatorUtil.isNull(cell)) {
				cell = row.createCell(cellnum);
			}
			
		    fillValue(obj, cell, initRow, size, sheet);
		    cellnum++;
			
		}
	}

	/**
	 * Llea el valor de la Celda
	 * @param obj
	 * @param cell
	 */
	private static void fillValue(Object obj, Cell cell, Integer initRowData, Integer size, XSSFSheet sheet) {
		
		fillStringValue (obj, cell, sheet.getSheetName());
		
		fillBooelanValue (obj, cell, sheet.getSheetName()); 
		
		fillDateValue    (obj, cell, sheet.getSheetName()); 
		
		fillDoubleValue  (obj, cell, sheet.getSheetName());
		
		fillLongValue    (obj, cell, sheet.getSheetName());
		
		fillIntegerValue    (obj, cell, sheet.getSheetName());
		
		fillListStringValue(obj, cell, sheet, initRowData, size);
	}


	/**
	 * Llena el valor de la celda si es Long
	 * 
	 * @param obj
	 * @param cell 
	 */
	private static void fillLongValue(Object obj, Cell cell, String sheetName) {
		if (obj instanceof Long && !sheetName.equals(GeneralConstants.SHEET_HIDDEN)) {
			cell.setCellValue( Double.parseDouble(obj.toString()) );
		}
	}
	
	private static void fillIntegerValue(Object obj, Cell cell, String sheetName) {
		if (obj instanceof Integer && !sheetName.equals(GeneralConstants.SHEET_HIDDEN)) {
			cell.setCellValue( Integer.parseInt(obj.toString()) );
		}
	}


	/**
	 * Llena el valor de la celda si es Long
	 * 
	 * @param obj
	 * @param cell 
	 */
	private static void fillDoubleValue(Object obj, Cell cell, String sheetName) {
		if (obj instanceof Double && !sheetName.equals(GeneralConstants.SHEET_HIDDEN)) {
		    cell.setCellValue((Double) obj);
		}
	}


	/**
	 * Llena el valor de la celda si es Long
	 * 
	 * @param obj
	 * @param cell   
	 */
	private static void fillDateValue(Object obj, Cell cell, String sheetName) {
		if (obj instanceof Date && !sheetName.equals(GeneralConstants.SHEET_HIDDEN)) {
			cell.setCellValue((Date) obj);
		}
	}
	
	/**
	 * Llena el valor de la celda si es Long
	 * 
	 * @param obj
	 * @param cell con valor   
	 */
	private static void fillBooelanValue(Object obj, Cell cell, String sheetName) {
		if (obj instanceof Boolean && !sheetName.equals(GeneralConstants.SHEET_HIDDEN)) {
		     cell.setCellValue((Boolean) obj);
		}
	}

	/**
	 * Llena el valor de la celda si es Long
	 * 
	 * @param obj
	 * @param cell
	 */
	private static void fillStringValue(Object obj, Cell cell, String sheetName) {
		if (obj instanceof String && !sheetName.equals(GeneralConstants.SHEET_HIDDEN)) {
			cell.setCellValue((String) obj);
		}
	}

	/**
	 * Llena el valor de la celda si es Long
	 * 
	 * @param obj
	 * @param cell
	 */
	private static void fillListStringValue(Object obj, Cell cell, XSSFSheet sheet, Integer initRowData, Integer totalReg) {
		
		if (obj instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<String> lista = (List<String>) obj;
			if(sheet.getSheetName().equals(GeneralConstants.SHEET_HIDDEN)) {
				//Solo se carga la primer fila de catalogos
				if(cell.getRowIndex() == initRowData) {
					String valor;
					Row row;
					Cell cellHidden;
					for (int i = cell.getRowIndex(), length= lista.size()+cell.getRowIndex(), j = 0; i < length; i++, j++) {
			            valor = lista.get(j);
			            row = sheet.getRow(i) == null ? sheet.createRow(i) : sheet.getRow(i);
			            cellHidden = row.createCell(cell.getColumnIndex());
			            cellHidden.setCellValue(valor);
			        }
				}
			} else {
				cell.setCellValue(lista.get(0));
				//Solo se carga la primer fila de catalogos
				if(cell.getRowIndex() == initRowData) {
					XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
					CellRangeAddressList list = new CellRangeAddressList(initRowData, initRowData + totalReg, cell.getColumnIndex(), cell.getColumnIndex());
					String columnName = CellReference.convertNumToColString(cell.getColumnIndex());
					int inicioCatalogo = initRowData+1; //+1 para quitar el encabezado
					int finCatalogo = lista.size() + initRowData;
					DataValidationConstraint constraintLista =dvHelper.createFormulaListConstraint(getListFormula(columnName,inicioCatalogo,finCatalogo));
					DataValidation validation = dvHelper.createValidation(constraintLista, list);
					validation.setSuppressDropDownArrow(true);
					validation.setShowErrorBox(true);
			        sheet.addValidationData(validation);
				}
			}
		}
		
	}
	
	/**
	 * Metodo encargado de obtener el rango de lectura de catalogo
	 * @param columnName
	 * @param inicioCatalogo
	 * @param finCatalogo
	 * @return
	 */
	private static String getListFormula(String columnName, int inicioCatalogo, int finCatalogo) {
		StringBuilder constraint = new StringBuilder();
		constraint.append(GeneralConstants.SHEET_HIDDEN).append("!$");
		constraint.append(columnName).append("$").append(inicioCatalogo);
		constraint.append(":$").append(columnName).append("$").append(finCatalogo);
		return constraint.toString();
	}



	/**
	 * Cierra los archivos abiertos.
	 * 
	 * @param baos
	 * @param inReporte
	 */
	private static void closeFilesOpen(ByteArrayOutputStream baos, InputStream inReporte) {
		try {
			baos.close();
			inReporte.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	
	/**
	 * Genera el mapa que se utilizara en el reporte.
	 * 
	 * @param objetoVO
	 * @param listaDatos
	 * @param headers
	 * @return
	 */
	public static <T>  Map<String, Object[]> fillDataReport (List<T> listaDatos, List<String> fields) {
		
		Map<String, Object[]> fillReporte = new LinkedHashMap<String, Object[]>(); 
		
		if(ValidatorUtil.isNullOrEmpty(listaDatos)) {
			return fillReporte;
		}
		
        for  (T objVO: listaDatos) {
        	
        	String keyValue = extractKey(objVO);
        	
        	if (!StringUtil.isNullOrEmpty(keyValue)) {
        	
        		Object[] array = fillDataReport(fields, objVO);
        		fillReporte.put(keyValue, array);
        	
        	}else {
        		break;
        	}
        	
        }
        
		return fillReporte;
		
	}



	
	/**
	 * Genera el arreglo que se poijtara en el excel 
	 * 
	 * @param fields
	 * @param objVO
	 * @return Object[] Arreglo que se escribe en el excel 
	 */
	private static <T> Object[] fillDataReport(List<String> fields, T objVO) {
		
		Object[] array = new Object[fields.size()];
		
		int idx = 0;
		
		for (String fieldName : fields) {

			try {
				
				Field field = objVO.getClass().getDeclaredField(fieldName) ;
				
				if (field != null) {
					
					if (field.isAccessible() ) {
						
						array[idx] = objVO.getClass().getMethod(buildNameMethod(field)).invoke( objVO );
						
					}else{
						field.setAccessible(true);
						array[idx] = objVO.getClass().getMethod(buildNameMethod(field)).invoke( objVO );
						field.setAccessible(false);
					}
					
				} else {
					array[idx]  = null;
				}
					
				
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException  e) {
				e.printStackTrace();
			} 
			
			idx++;
		}
		
		return array;
	}





	/**
	 * Genera la llave del reporte
	 * 
	 * @param objVO
	 * @return genera la llave del reporte.
	 */
	public static <T> String extractKey( T objVO) {
		
		String keyValue = new String();
		
		try {
			
			Field fieldKey = objVO.getClass().getDeclaredField(GeneralConstants.KEY) ;
			
			if (fieldKey.isAccessible() ) {
				keyValue = objVO.getClass().getMethod(buildNameMethod(fieldKey)).invoke( objVO ).toString();
			}else{
				fieldKey.setAccessible(true);
				keyValue = objVO.getClass().getMethod(buildNameMethod(fieldKey)).invoke( objVO ).toString();
				fieldKey.setAccessible(false);
			}
			
		} catch ( NoSuchFieldException | SecurityException  | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return keyValue;
	}

	/***
	 * Genera el nombre del metodo para su busqueda
	 * 
	 * @param fieldKey
	 * @return regresa el nombre del metodo
	 */
	private static String buildNameMethod(Field fieldKey) {
		return GeneralConstants.GET + fieldKey.getName().substring(0,1).toUpperCase() + fieldKey.getName().substring(1);
	}

}
