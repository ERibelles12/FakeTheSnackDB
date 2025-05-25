package de.pecus.api.configprops;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class SwaggerConfigProps {

	@Value("${swagger.contact.email}")
	private String contactEmail;
	@Value("${swagger.contact.url}")
	private String contactUrl;
	@Value("${swagger.contact.name}")
	private String contactName;
	@Value("${swagger.description}")
	private String description;
	@Value("${swagger.title}")
	private String title;
	@Value("${swagger.enabled}")
	private Boolean enabled;
	
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactUrl() {
		return contactUrl;
	}
	public void setContactUrl(String contactUrl) {
		this.contactUrl = contactUrl;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
