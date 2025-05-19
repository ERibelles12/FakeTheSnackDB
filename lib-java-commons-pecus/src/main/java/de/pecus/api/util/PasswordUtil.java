package de.pecus.api.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import de.pecus.api.constant.GeneralConstants;

public final class PasswordUtil {

	private PasswordUtil() {
		
	}

	/**
	 * Metodo que genera un String de caracteres aleatorios del tamano indicado como parametro.
	 * 
	 * @param String size
	 *  
	 * @return String with random chars
	 */
	public static String generateRandomPassword(int cantCaracteres) {
        char password[] = new char[cantCaracteres];
        for (int x = 0; x < cantCaracteres; x++) {
            int random = RandomUtils.nextInt(GeneralConstants.OPTIONS_SIZE);
            switch (random) {
            case 0:
                password[x] = RandomStringUtils.random(GeneralConstants.LENGTH_OF_RAMDOM_STRING_TO_CREATE, GeneralConstants.POSITION_ASCCI_START_UPPERCASE, GeneralConstants.POSITION_ASCCI_END_UPPERCASE, true, true).charAt(GeneralConstants.ZERO);
                break;
            case 1:
                password[x] = RandomStringUtils.random(GeneralConstants.LENGTH_OF_RAMDOM_STRING_TO_CREATE, GeneralConstants.POSITION_ASCCI_START_LOWERCASE, GeneralConstants.POSITION_ASCCI_END_LOWERCASE, true, true).charAt(GeneralConstants.ZERO);
                break;
            case 2:
                password[x] = RandomStringUtils.randomNumeric(GeneralConstants.LENGTH_OF_RAMDOM_STRING_TO_CREATE).charAt(GeneralConstants.ZERO);
                break;
            case 3:
                password[x] = RandomStringUtils.random(GeneralConstants.LENGTH_OF_RAMDOM_STRING_TO_CREATE, GeneralConstants.POSITION_ASCCI_START_SPECIAL_CHARS, GeneralConstants.POSITION_ASCCI_END_SPECIAL_CHARS, false, false)
                        .replaceAll("'", RandomStringUtils.random(GeneralConstants.LENGTH_OF_RAMDOM_STRING_TO_CREATE, GeneralConstants.POSITION_ASCCI_START_LOWERCASE, GeneralConstants.POSITION_ASCCI_END_LOWERCASE, true, true)).replaceAll(String.valueOf(GeneralConstants.ZERO), String.valueOf(GeneralConstants.LENGTH_OF_RAMDOM_STRING_TO_CREATE))
                        .charAt(GeneralConstants.ZERO);
                break;
            }
        }
        return password.toString();
    }
}
