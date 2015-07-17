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
      Map<Integer, String> images = getIDsAndNames(stmt, "image");
      Map<Integer, String> policies = getIDsAndNames(stmt, "policy");
      Map<Integer, String> arguments = getIDsAndNames(stmt, "argument");

      insertScenarios(conn, images, policies, arguments);
    } catch (IllegalStateException | SQLException e) {
      e.printStackTrace();
    }
  }

  private Map<Integer, String> getIDsAndNames(Statement stmt, String tableName) throws SQLException {
    Map<Integer, String> ids = new LinkedHashMap<>();
    try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM " + tableName)) {
      while (rs.next()) {
        ids.put(rs.getInt(1), rs.getString(2));
      }
    }
    return ids;
  }

  // TODO: This method needs to be restructured
  private void insertScenarios(Connection conn, Map<Integer, String> images,
      Map<Integer, String> policies, Map<Integer, String> arguments) throws SQLException {
    try (PreparedStatement stmt = conn
        .prepareStatement("INSERT INTO scenario (image_id, policy_a_id, "
            + "argument_a_id, policy_b_id, argument_b_id, policy_c_id, argument_c_id) "
            + "values (?, ?, ?, ?, ?, ?, ?)")) {
      int i = 0;
      for (Integer imageId : images.keySet()) {
        for (Integer policyAId : policies.keySet()) {
          String policyAName = policies.get(policyAId);
          for (Integer argumentAId : arguments.keySet()) {
            String argumentAName = arguments.get(argumentAId);
            for (Integer policyBId : policies.keySet()) {
              String policyBName = policies.get(policyBId);
              for (Integer argumentBId : arguments.keySet()) {
                String argumentBName = arguments.get(argumentBId);
                for (Integer policyCId : policies.keySet()) {
                  String policyCName = policies.get(policyCId);
                  for (Integer argumentCId : arguments.keySet()) {
                    String argumentCName = arguments.get(argumentCId);
                    if (isCombinationValid(policyAName, policyBName, policyCName, argumentAName,
                        argumentBName, argumentCName)) {
                      stmt.setInt(1, imageId);
                      stmt.setInt(2, policyAId);
                      stmt.setInt(3, argumentAId);
                      stmt.setInt(4, policyBId);
                      stmt.setInt(5, argumentBId);
                      stmt.setInt(6, policyCId);
                      stmt.setInt(7, argumentCId);
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
          }
        }
      }
      stmt.executeBatch();
    }
  }
    
  // TODO: Check if more validations are required
  private boolean isCombinationValid(String policyAName, String policyBName, String policyCName,
      String argAName, String argBName, String argCName) {
    if (isPolicyCombinationValid(policyAName, policyBName, policyCName)
        && isArgumentCombinationValid(argAName, argBName, argCName)
        && isPolicyArgumentCombinationValid(policyAName, argAName)
        && isPolicyArgumentCombinationValid(policyBName, argBName)
        && isPolicyArgumentCombinationValid(policyCName, argCName)) {
      return true;
    }
    return false;
  }
  
  private boolean isPolicyArgumentCombinationValid(String policyName, String argumentName) {
    if ((policyName.equals("all") && argumentName.equals("negative consequence"))
        || (policyName.equals("none") && argumentName.equals("positive consequence"))) {
      return false;
    }
    return true;
  }
  
  private boolean isPolicyCombinationValid(String... policyNames) {
    boolean isValid = false;
    String firstPolicyName = policyNames[0];
    for (String policyName: policyNames) {
      if (!policyName.equals(firstPolicyName)) {
        isValid = true;
        break;
      }
    }
    return isValid;
  }
  
  private boolean isArgumentCombinationValid(String... argNames) {
    boolean isValid = true;
    // TODO: I dont yet know if there are any invalid combinations!
    return isValid;
  }


  public static void main(String[] args) {
    ScenarioGenerator scenarioGen = new ScenarioGenerator();
    scenarioGen.generateScenarios();
  }
}
