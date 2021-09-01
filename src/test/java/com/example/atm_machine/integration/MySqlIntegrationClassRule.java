package com.example.atm_machine.integration;

import java.util.concurrent.TimeUnit;

import lombok.extern.log4j.Log4j2;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

@Log4j2
public class MySqlIntegrationClassRule extends TestWatcher {

  private static final int MYSQL_PORT = 3306;

  public static GenericContainer mySQLContainer;

  @Override
  protected void starting(final Description description) {
    try {
      final Network network = Network.newNetwork();
      mySQLContainer = new GenericContainer("mysql:5.7").withEnv("MYSQL_DATABASE", "atm_machine")
          .withEnv("MYSQL_USER", "user")
          .withEnv("MYSQL_PASSWORD", "password")
          .withEnv("MYSQL_ROOT_PASSWORD", "password")
          .withNetwork(network)
          .withNetworkAliases("database")
          .withExposedPorts(MYSQL_PORT);
      mySQLContainer.start();

      TimeUnit.SECONDS.sleep(10);

      System.setProperty("spring.datasource.url",
          String.format("jdbc:mysql://%s:%s/atm_machine?useSSL=false&serverTimezone=UTC",
              mySQLContainer.getContainerIpAddress(), mySQLContainer.getMappedPort(MYSQL_PORT)));
      System.setProperty("spring.datasource.username", "user");
      System.setProperty("spring.datasource.password", "password");
      System.setProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
      System.setProperty("database-platform", "org.hibernate.dialect.MySQLDialect");

    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

}
