package com.restaurant.config;

import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Configuration
public class JooqConfig {
  @Autowired
  private DataSource dataSource;

  @Bean
  public DataSourceConnectionProvider connectionProvider() {
    return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
  }

  @Bean
  public DefaultDSLContext dsl() {
    return new DefaultDSLContext(configuration());
  }

  public DefaultConfiguration configuration() {
    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.set(connectionProvider());
    jooqConfiguration.set(new DefaultExecuteListenerProvider(new JooqExceptionTranslator()));

    return jooqConfiguration;
  }
}
