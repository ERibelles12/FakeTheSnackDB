package de.pecus.api.configprops;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
public class PecusDataSourceConfigProps extends SpringDataSourceConfigProps {

}
