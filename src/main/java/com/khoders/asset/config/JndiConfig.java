package com.khoders.asset.config;

import javax.sql.DataSource;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
public class JndiConfig {

  @Bean
  ServletWebServerFactory servletWebServerFactory() {
      return new TomcatServletWebServerFactory();
  }

    @Bean(destroyMethod = "")
    public DataSource dataSource() {
	  JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
      return dataSourceLookup.getDataSource("java:comp/env/jdbc/asset");
  }
   @Bean
   public NamedParameterJdbcTemplate jdbc(DataSource ds) {
	   return new NamedParameterJdbcTemplate(ds);
   }
}
