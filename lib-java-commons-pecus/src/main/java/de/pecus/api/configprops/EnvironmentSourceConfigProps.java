package de.pecus.api.configprops;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "smartlogger")
public class EnvironmentSourceConfigProps {
	private List<DBProperties> db = new ArrayList<>();

	public static class DBProperties {
		private String environment;
		private String url;
		private String user;
		private String pass;
		private String driver;
		private String validationQuery;

		@Override
		public String toString() {
			return "DBProperties [environment=" + environment + ", url=" + url + ", user=" + user + ", pass=" + pass
					+ ", driver=" + driver + ", validationQuery=" + validationQuery + "]";
		}

		public String getEnvironment() {
			return environment;
		}

		public void setEnvironment(String environment) {
			this.environment = environment;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPass() {
			return pass;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}

		public String getDriver() {
			return driver;
		}

		public void setDriver(String driver) {
			this.driver = driver;
		}

		public String getValidationQuery() {
			return validationQuery;
		}

		public void setValidationQuery(String validationQuery) {
			this.validationQuery = validationQuery;
		}
	}

	public List<DBProperties> getDb() {
		return db;
	}

	public void setDb(List<DBProperties> db) {
		this.db = db;
	}

	@Override
	public String toString() {
		return "EnvironmentSourceConfigProps [db=" + db + "]";
	}

	
}
