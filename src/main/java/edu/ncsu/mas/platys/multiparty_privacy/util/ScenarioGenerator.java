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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
      Map<Integer, String> images = getImages(stmt);
      Map<Integer, Map<Integer, String>> arguments = getArguments(stmt);
      Map<Integer, String> policies = getPolicies(stmt);

      insertScenarios(conn, images, policies, arguments);
      
      fixScenarioForExceptionalCaseArgument(conn); // TODO: This is temporary
      
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

  private Map<Integer, String> getPolicies(Statement stmt) throws SQLException {
    Map<Integer, String> policyMap = new LinkedHashMap<>();
    try (ResultSet rs = stmt.executeQuery("SELECT id, name FROM policy")) {
      while (rs.next()) {
        policyMap.put(rs.getInt(1), rs.getString(2));
      }
    }
    return policyMap;
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
      Map<Integer, String> policies, Map<Integer, Map<Integer, String>> arguments)
      throws SQLException {
    try (PreparedStatement stmt = conn
        .prepareStatement("INSERT INTO scenario (image_id, policy_a_id, "
            + "argument_a_id, policy_b_id, argument_b_id, policy_c_id, argument_c_id) "
            + "values (?, ?, ?, ?, ?, ?, ?)")) {
      int i = 0;
      for (Integer imageId : images.keySet()) {
        Map<Integer, String> argumentsForImage = arguments.get(imageId);
        for (Integer policyAId : policies.keySet()) {
          String policyAName = policies.get(policyAId);
          for (Integer argumentAId : argumentsForImage.keySet()) {
            String argumentAName = argumentsForImage.get(argumentAId);
            for (Integer policyBId : policies.keySet()) {
              String policyBName = policies.get(policyBId);
              for (Integer argumentBId : argumentsForImage.keySet()) {
                String argumentBName = argumentsForImage.get(argumentBId);
                for (Integer policyCId : policies.keySet()) {
                  String policyCName = policies.get(policyCId);
                  for (Integer argumentCId : argumentsForImage.keySet()) {
                    String argumentCName = argumentsForImage.get(argumentCId);
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
  
  /**
   * This method was introduced to identify some scenarios that we identified to
   * be faulty after two batches of the study
   * 
   * @param conn
   *          Database connection
   * @param faultyArgPolicyCombIds
   * @return
   * @throws SQLException
   */
  public Set<Integer> findFaultyScenarios(Connection conn,
      Map<Integer, Integer> faultyArgPolicyCombIds) throws SQLException {
    Set<Integer> faultyScenarioIds = new HashSet<Integer>();

    Statement stmt = conn.createStatement();
    Map<Integer, String> images = getImages(stmt);
    Map<Integer, Map<Integer, String>> arguments = getArguments(stmt);
    Map<Integer, String> policies = getPolicies(stmt);

    int i = 0;
    for (Integer imageId : images.keySet()) {
      Map<Integer, String> argumentsForImage = arguments.get(imageId);
      for (Integer policyAId : policies.keySet()) {
        String policyAName = policies.get(policyAId);
        for (Integer argumentAId : argumentsForImage.keySet()) {
          String argumentAName = argumentsForImage.get(argumentAId);
          for (Integer policyBId : policies.keySet()) {
            String policyBName = policies.get(policyBId);
            for (Integer argumentBId : argumentsForImage.keySet()) {
              String argumentBName = argumentsForImage.get(argumentBId);
              for (Integer policyCId : policies.keySet()) {
                String policyCName = policies.get(policyCId);
                for (Integer argumentCId : argumentsForImage.keySet()) {
                  String argumentCName = argumentsForImage.get(argumentCId);
                  if (isCombinationValid(policyAName, policyBName, policyCName, argumentAName,
                      argumentBName, argumentCName)) {
                    i++;
                    for (int faultyArgId : faultyArgPolicyCombIds.keySet()) {
                      int faultyPolicyId = faultyArgPolicyCombIds.get(faultyArgId);
                      if ((argumentAId == faultyArgId && policyAId == faultyPolicyId)
                          || (argumentBId == faultyArgId && policyBId == faultyPolicyId)
                          || (argumentCId == faultyArgId && policyCId == faultyPolicyId)) {
                        faultyScenarioIds.add(i);
                        break;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return faultyScenarioIds;
  }

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

  /*
   * This function is incorrect: exceptional_case and common is invalid.
   * However, I cannot fix it in the middle of the experiment. Hence the
   * temporary function fixScenarioForExceptionalCaseArgument. TODO; Fix it
   * later
   */
  private boolean isPolicyArgumentCombinationValid(String policyName, String argumentName) {
    if ((argumentName.equals("positive_consequence") && policyName.equals("self"))
        || (argumentName.equals("negative_consequence") && policyName.equals("all"))
        || (argumentName.equals("exceptional_case") && policyName.equals("self"))) {
      return false;
    }
    return true;
  }
  
  /*
   * This is a temporary fix. Check the comment for isPolicyArgumentCombinationValid
   */
  private void fixScenarioForExceptionalCaseArgument(Connection conn) throws SQLException {
    String[] argColNames = new String[] { "argument_a_id", "argument_b_id", "argument_c_id" };
    String[] policyColNames = new String[] { "policy_a_id", "policy_b_id", "policy_c_id" };
    for (int i = 0; i < 3; i++) {
      try (PreparedStatement stmt = conn.prepareStatement("update multiparty_privacy.scenario set "
          + policyColNames[i] + " = 3 where " + argColNames[i] + " = ? and " + policyColNames[i]
          + " = 2;");) {
        for (int argId = 3; argId <= 36; argId += 3) {
          stmt.setInt(1, argId);
          stmt.executeUpdate();
        }
      }
    }
  }

  private boolean isPolicyCombinationValid(String... policyNames) {
    boolean isValid = false;

    // All three policies being same does not make sense
    String firstPolicyName = policyNames[0];
    for (String policyName : policyNames) {
      if (!policyName.equals(firstPolicyName)) {
        isValid = true;
        break;
      }
    }

    return isValid;
  }

  private boolean isArgumentCombinationValid(String... argNames) {
    // All combinations are OK
    return true;
  }

  public static void main(String[] args) {
    ScenarioGenerator scenarioGen = new ScenarioGenerator();
    scenarioGen.generateScenarios();
  }
}
