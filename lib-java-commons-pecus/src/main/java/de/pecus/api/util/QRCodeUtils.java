package de.pecus.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.commons.codec.binary.Base64;

import com.google.common.net.MediaType;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeUtils {
	
	/**
	 * Metodo que genera imagen QR en la ruta que se indique
	 * 
	 * @param text
	 * @param width
	 * @param height
	 * @param filePath
	 * @throws WriterException
	 * @throws IOException
	 */
    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, MediaType.PNG.subtype() , path);
    }
    
    /**
     * Metodo que genera QR en base 64
     * 
     * @param text
     * @param width
     * @param height
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static String  getQRCodeImageBase64(String text, Integer width, Integer height) throws WriterException, IOException {
    	
    	String encoded = new String();
    	
    	byte[] qrCodeImageByteArray = getQRCodeImageByteArray( text,  width.intValue(),  height.intValue()) ;
    	
    	if(qrCodeImageByteArray != null) {
    		encoded = Base64.encodeBase64String(qrCodeImageByteArray);
    	}
    	
        return encoded;
    }
    
    /**
     * Metodo que genera el qr en un arreglode bytes.
     * 
     * @param text
     * @param width
     * @param height
     * @return
     * @throws Exception 
     * @throws WriterException
     * @throws IOException
     */
    private static byte[] getQRCodeImageByteArray(String text, int width, int height) throws WriterException, IOException  {
    	
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, MediaType.PNG.subtype() , pngOutputStream);

        return pngOutputStream.toByteArray();
    }
    
}