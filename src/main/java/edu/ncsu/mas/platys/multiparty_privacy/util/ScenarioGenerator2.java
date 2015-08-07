package edu.ncsu.mas.platys.multiparty_privacy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This is a utility class meant to run before the webapp is used. The class
 * populates the Scenario table in the database, which the webapp employs.
 * 
 * @author pmuruka
 *
 */
public class ScenarioGenerator2 {

  private void generateScenarios() {
    Properties props = new Properties();
    try (InputStream inStream = new FileInputStream("src/main/resources/application.properties")) {
      props.load(inStream);
      Class.forName(props.getProperty("jdbc.driverClassName"));
    } catch (ClassNotFoundException | IllegalStateException | IOException e) {
      e.printStackTrace();
      return;
    }

    try (Connection conn = DriverManager.getConnection(props.getProperty("jdbc.url") + "?user="
        + props.getProperty("jdbc.username") + "&password=" + props.getProperty("jdbc.password"))) {
      Statement stmt = conn.createStatement();
      Map<Integer, String> images = getImages(stmt);
      Map<Integer, Map<Integer, String>> arguments = getArguments(stmt);

      insertScenarios(conn, images, arguments);
    } catch (IllegalStateException | SQLException e) {
      e.printStackTrace();
    }
  }

  private Map<Integer, String> getImages(Statement stmt) throws SQLException {
    Map<Integer, String> ids = new LinkedHashMap<>();
    try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM image")) {
      while (rs.next()) {
        ids.put(rs.getInt(1), rs.getString(2));
      }
    }
    return ids;
  }

  private Map<Integer, Map<Integer, String>> getArguments(Statement stmt) throws SQLException {
    Map<Integer, Map<Integer, String>> argumentsMap = new LinkedHashMap<>();
    try (ResultSet rs = stmt.executeQuery("SELECT id, image_id, name FROM argument")) {
      while (rs.next()) {
        int argId = rs.getInt(1);
        int imageId = rs.getInt(2);
        String argName = rs.getString(3);
        Map<Integer, String> argForImageMap = argumentsMap.get(imageId);
        if (argForImageMap == null) {
          argForImageMap = new LinkedHashMap<>();
        }
        argForImageMap.put(argId, argName);
        argumentsMap.put(imageId, argForImageMap);
      }
    }
    return argumentsMap;
  }

  private void insertScenarios(Connection conn, Map<Integer, String> images,
      Map<Integer, Map<Integer, String>> arguments) throws SQLException {
    try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO scenario"
        + "(image_id, argument_a_id, argument_b_id, argument_c_id) VALUES (?, ?, ?, ?)")) {
      int i = 0;
      for (Integer imageId : images.keySet()) {
        Map<Integer, String> argumentsForImage = arguments.get(imageId);
        for (Integer argumentAId : argumentsForImage.keySet()) {
          String argumentAName = argumentsForImage.get(argumentAId);
          for (Integer argumentBId : argumentsForImage.keySet()) {
            String argumentBName = argumentsForImage.get(argumentBId);
            for (Integer argumentCId : argumentsForImage.keySet()) {
              String argumentCName = argumentsForImage.get(argumentCId);
              if (isArgumentCombinationValid(argumentAName, argumentBName, argumentCName)) {
                stmt.setInt(1, imageId);
                stmt.setInt(2, argumentAId);
                stmt.setInt(3, argumentBId);
                stmt.setInt(4, argumentCId);
                stmt.addBatch();
                i++;
                if (i % 1000 == 0) {
                  stmt.executeBatch();
                }
              }
            }
          }
        }
      }
      stmt.executeBatch();
    }
  }

  private boolean isArgumentCombinationValid(String... argNames) {
    boolean isValid = false;
    String firstArgName = argNames[0];
    for (String argName : argNames) {
      if (!argName.equals(firstArgName)) {
        isValid = true;
        break;
      }
    }
    return isValid;
  }

  public static void main(String[] args) {
    ScenarioGenerator2 scenarioGen = new ScenarioGenerator2();
    scenarioGen.generateScenarios();
  }
}
