package de.pecus.api.util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jboss.logging.Logger;

public final class JAXBUtil {
    
    private static final Logger LOGGER = Logger.getLogger(JAXBUtil.class);
    
    private JAXBUtil() {
    }
    
    public static final String getValueJaxbElement(JAXBElement<String> jaxbElement) {
        String value = null;
        if (!ValidatorUtil.isNull(jaxbElement) && !ValidatorUtil.isNullOrEmpty(jaxbElement.getValue())) {
            value = jaxbElement.getValue();
        }
        return value;
    }

    public static final Date getDateFromJaxbGregorianCalendar(JAXBElement<XMLGregorianCalendar> jaxbGregorianCalendar) {
        Date response = null;
        if(!ValidatorUtil.isNull(jaxbGregorianCalendar) && !ValidatorUtil.isNull(jaxbGregorianCalendar.getValue())) {
            response = jaxbGregorianCalendar.getValue().toGregorianCalendar().getTime();
        }
        return response;
    }
    
    public static final Long getLongValue(Integer integer) {
        Long response = null;
        if(!ValidatorUtil.isNullOrZero(integer)) {
            response = integer.longValue();
        }
        return response;
    }
    
    public static final Integer getIntegerValue(Long number) {
        Integer response = null;
        if(!ValidatorUtil.isNullOrZero(number)) {
            response = number.intValue();
        }
        return response;
    }

    public static final Date getDateFromJaxbGregorianCalendar(XMLGregorianCalendar gregorianCalendar) {
        Date response = null;
        if(!ValidatorUtil.isNull(gregorianCalendar)) {
            response = gregorianCalendar.toGregorianCalendar().getTime();
        }
        return response;
    }
    
    public static final String toXml(JAXBElement<?> element) {
        String response = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(element.getValue().getClass());  
            Marshaller marshaller = jc.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);  

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(element, baos);
            response = baos.toString();
        } catch (Exception e) {
            LOGGER.error(MessageFormat.format("Ocurrio un error al obtener el xml del objeto [{0}]", element), e);
        }      
        return response;
    }

    public static final String getStringValude(Long number) {
        String response = null;
        if(!ValidatorUtil.isNullOrZero(number)) {
            response = number.toString();
        }
        return response;
    }
    
    public static final Boolean getBooleanValue(JAXBElement<Boolean> jaxbElement) {
    	Boolean value = null;
    	if(!ValidatorUtil.isNull(jaxbElement) && !ValidatorUtil.isNull(jaxbElement.getValue())) {
    		value = jaxbElement.getValue();
    	}
    	return value;
    }
    
    public static final BigInteger getBigIntegerValue(JAXBElement<BigInteger> jaxbElement) {
        BigInteger response = null;
        if(!ValidatorUtil.isNull(jaxbElement) && !ValidatorUtil.isNull(jaxbElement.getValue())) {
            response = jaxbElement.getValue();
        }
        return response;
    }
}