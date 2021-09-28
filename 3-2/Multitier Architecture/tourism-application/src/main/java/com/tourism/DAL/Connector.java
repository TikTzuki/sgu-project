package com.tourism.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@RequiredArgsConstructor
public abstract class Connector {
  private static final Logger logger = Logger.getLogger(Connector.class.getName());
  @NonNull
  protected String host;
  @NonNull
  protected String userName;
  @NonNull
  protected String password;
  @NonNull
  protected String database;
  protected Statement statement;
  protected ResultSet resultSet;
  static protected Connection connection;

  public abstract void getConnect();
  
  public abstract void setStatement();
  
  public abstract ResultSet executeQuery(String query);

  public abstract int executeUpdate(String query);
  
  public abstract void closeConnection();
}
