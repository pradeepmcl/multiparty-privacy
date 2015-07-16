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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This is a utility class meant to run before the webapp is used. The class
 * populates the Scenario table in the database, which the webapp employs.
 * 
 * @author pmuruka
 *
 */
public class ScenarioGenerator {

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
      List<Integer> imageIds = getIDs(stmt, "image");
      List<Integer> policyIds = getIDs(stmt, "policy");
      List<Integer> argumentIds = getIDs(stmt, "argument");

      insertScenarios(conn, imageIds, policyIds, argumentIds);
    } catch (IllegalStateException | SQLException e) {
      e.printStackTrace();
    }
  }

  private List<Integer> getIDs(Statement stmt, String tableName) throws SQLException {
    List<Integer> ids = new ArrayList<>();
    try (ResultSet rs = stmt.executeQuery("SELECT id FROM " + tableName)) {
      while (rs.next()) {
        ids.add(rs.getInt(1));
      }
    }
    return ids;
  }

  private void insertScenarios(Connection conn, List<Integer> imageIds, List<Integer> policyIds,
      List<Integer> argumentIds) throws SQLException {
    try (PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO scenario (image_id, policy_id, argument_id) values (?, ?, ?)")) {
      int i = 0;
      for (Integer imageId : imageIds) {
        stmt.setInt(1, imageId);
        for (Integer policyId : policyIds) {
          stmt.setInt(2, policyId);
          for (Integer argumentId : argumentIds) {
            stmt.setInt(3, argumentId);
            stmt.addBatch();
            i++;
            if (i % 1000 == 0) {
              stmt.executeBatch();
            }
          }
        }
      }
      stmt.executeBatch();
    }
  }

  public static void main(String[] args) {
    ScenarioGenerator scenarioGen = new ScenarioGenerator();
    scenarioGen.generateScenarios();
  }
}
