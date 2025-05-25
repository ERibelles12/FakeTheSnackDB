package de.pecus.api.security.services.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import de.pecus.api.security.services.PasswordService;
import de.pecus.api.security.services.impl.PasswordServiceImpl;

/**
 * Clase de pruebas para la clase PasswordService.
 *  
 * @author Luis Enrique Sanchez Santiago
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class PasswordServiceTest {

	public static final String MI_PASSWORD = "mipassword";
	
	@Configuration
    static class ContextConfiguration {
        @Bean
        public PasswordService passwordService() {
        	PasswordService passwordService = new PasswordServiceImpl();
            return passwordService;
        }
    }

	@Autowired
	private PasswordService passwordService;

	@Test
	public void testDecryptPasswordAES() {
		String dataFromAngular = "YzcyMDg0ZWM5MGI3NjdkMWIyOTMyZWNkYmM4YjIyNTU6OmM5NDRkNGRhNmQ1OGJkYWU2YmE5YjViZjNiN2U0MTA3OjpvZTJ2L2lxSnpkd05hVUxvZFFwVU13PT0=";
		String passDecryptedAES = passwordService.decryptPasswordAES(dataFromAngular);
		assertEquals(MI_PASSWORD, passDecryptedAES);
	}

	@Test
	public void testDecryptPasswordAES_NullPassword() {
		String nullPassword = passwordService.decryptPasswordAES(null);
		assertNull(nullPassword);
	}
	
	@Test
	public void testEncryptPasswordSHA256() {
		String encryptedSHAPassword = "be54a8c599ecce23cedfaa1f125fb5b2f1193729f7727e31d0980de47cae1fe4";
		String encryptedPassword = passwordService.encryptPasswordSHA256(MI_PASSWORD);
		assertEquals(encryptedSHAPassword, encryptedPassword);
	}

	@Test
	public void testEncryptPasswordSHA256_NullPassword() {
		String nullPassword = passwordService.encryptPasswordSHA256(null);
		assertNull(nullPassword);
	}
}
