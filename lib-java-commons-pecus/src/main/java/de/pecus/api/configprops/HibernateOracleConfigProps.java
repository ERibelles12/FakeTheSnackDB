package de.pecus.api.configprops;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hibernate-oralce")
public class HibernateOracleConfigProps {

	@Value("${hibernate-oralce.show_sql}")
	private Boolean showSql;
	private String dialect;
	@Value("${hibernate-oralce.format_sql}")
	private String formatSql;
	@Value("${hibernate-oralce.hbm2ddl.auto}")
	private String hbm2dllAuto;
	@Value("${hibernate-oralce.default_schema}")
	private String defaultSchema;
	
	public Boolean getShowSql() {
		return showSql;
	}
	public void setShowSql(Boolean showSql) {
		this.showSql = showSql;
	}
	public String getDialect() {
		return dialect;
	}
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	public String getFormatSql() {
		return formatSql;
	}
	public void setFormatSql(String formatSql) {
		this.formatSql = formatSql;
	}
	public String getHbm2dllAuto() {
		return hbm2dllAuto;
	}
	public void setHbm2dllAuto(String hbm2dllAuto) {
		this.hbm2dllAuto = hbm2dllAuto;
	}
	/**
	 * @return the defaultSchema
	 */
	public String getDefaultSchema() {
		return defaultSchema;
	}
	/**
	 * @param defaultSchema the defaultSchema to set
	 */
	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}
}
