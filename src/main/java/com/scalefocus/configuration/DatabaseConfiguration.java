package com.scalefocus.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

  @Value("${url}")
  private String url;

  @Value("${user}")
  private String user;

  @Value("${password}")
  private String password;

  @Value("${cache_prepared_statements}")
  private String cachePreparedStatements;

  @Value("${prepared_statement_cache_size}")
  private String preparedStatementCacheSize;

  @Value("${prepared_statement_cache_sql_limit}")
  private String preparedStatementCacheSqlLimit;

  @Bean
  public HikariDataSource hikariDataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(user);
    config.setPassword(password);
    config.addDataSourceProperty("cachePrepStmts", cachePreparedStatements);
    config.addDataSourceProperty("prepStmtCacheSize", preparedStatementCacheSize);
    config.addDataSourceProperty("prepStmtCacheSqlLimit", preparedStatementCacheSqlLimit);

    return new HikariDataSource(config);
  }

  @Bean
  public Scanner scanner() {
    return new Scanner(System.in);
  }
}
