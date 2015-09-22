package edu.ncsu.mas.platys.multiparty_privacy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This is a scenario bundle generator to use in the middle of the experiment to
 * fix faulty scenarios to to include those not answered yet.
 * 
 * @author mpradeep
 *
 */
public class IntermediateScenarioBundleGenerator implements AutoCloseable {
  
  private static final int MIN_ID = 1;
  private static final int MAX_ID = 432;
  
  private final Properties mProps = new Properties();
  
  private Connection mConn;

  public IntermediateScenarioBundleGenerator() throws SQLException {
    try (InputStream inStream = new FileInputStream("src/main/resources/application.properties")) {
      mProps.load(inStream);
      Class.forName(mProps.getProperty("jdbc.driverClassName"));
      mConn = DriverManager.getConnection(mProps.getProperty("jdbc.url") + "?user="
          + mProps.getProperty("jdbc.username") + "&password=" + mProps.getProperty("jdbc.password"));
    } catch (ClassNotFoundException | IllegalStateException | IOException e) {
      e.printStackTrace();
      return;
    }
  }

  @Override
  public void close() throws Exception {
    mConn.close();
  }

  public Map<Integer, Integer> getScenarioToBundleMapping() throws SQLException {
    Map<Integer, Integer> scenarioToBundleMap = new LinkedHashMap<>();
    Statement stmt = mConn.createStatement();
    try (ResultSet rs = stmt.executeQuery("SELECT id, scenarios_csv FROM scenario_bundle")) {
      while (rs.next()) {
        String[] scenarios = rs.getString(2).split(",");
        Integer scenarioBundleId = rs.getInt(1);
        for (String scenario : scenarios) {
          scenarioToBundleMap.put(Integer.parseInt(scenario), scenarioBundleId);
        }
      }
    }
    return scenarioToBundleMap;
  }
  
  public Map<Integer, String> getBundleToScenarioMapping() throws SQLException {
    Map<Integer, String> BundleToScenarioMap = new LinkedHashMap<>();
    Statement stmt = mConn.createStatement();
    try (ResultSet rs = stmt.executeQuery("SELECT id, scenarios_csv FROM scenario_bundle")) {
      while (rs.next()) {
        BundleToScenarioMap.put(rs.getInt(1), rs.getString(2));
      }
    }
    return BundleToScenarioMap;
  }
  
  public Map<Integer, Integer> getResponseCounts() throws SQLException {
    Map<Integer, Integer> counts = new LinkedHashMap<>();
    Statement stmt = mConn.createStatement();
    try (ResultSet rs = stmt.executeQuery("SELECT scenario_bundle_id, count(scenario_bundle_id)"
        + " FROM turker_postsurvey_response"
        + " GROUP BY scenario_bundle_id")) {
      while (rs.next()) {
        counts.put(rs.getInt(1), rs.getInt(2));
      }
    }
    return counts;
  }

  private Set<Integer> getFaultyScenarioIds() throws SQLException {
    Map<Integer, Integer> faultyArgPolicyCombIds = new HashMap<Integer, Integer>();
    faultyArgPolicyCombIds.put(3, 2);
    faultyArgPolicyCombIds.put(6, 1);
    faultyArgPolicyCombIds.put(6, 2);
    faultyArgPolicyCombIds.put(6, 3);
    faultyArgPolicyCombIds.put(9, 2);
    faultyArgPolicyCombIds.put(12, 2);
    faultyArgPolicyCombIds.put(15, 2);
    faultyArgPolicyCombIds.put(18, 1);
    faultyArgPolicyCombIds.put(18, 2);
    faultyArgPolicyCombIds.put(18, 3);
    faultyArgPolicyCombIds.put(21, 2);
    faultyArgPolicyCombIds.put(24, 2);
    faultyArgPolicyCombIds.put(27, 2);
    faultyArgPolicyCombIds.put(30, 1);
    faultyArgPolicyCombIds.put(30, 2);
    faultyArgPolicyCombIds.put(30, 3);
    faultyArgPolicyCombIds.put(33, 2);
    faultyArgPolicyCombIds.put(36, 1);
    faultyArgPolicyCombIds.put(36, 2);
    faultyArgPolicyCombIds.put(36, 3);

    ScenarioGenerator scenarioGen = new ScenarioGenerator();

    Set<Integer> faultyScenarioIds = scenarioGen.findFaultyScenarios(mConn, faultyArgPolicyCombIds);

    return faultyScenarioIds;
  }

  public static void main(String[] args) {
    String outScenarioIdsFilename = args[0];
    String outBundleIdsFilename = args[1];
    
    try (IntermediateScenarioBundleGenerator bundleGen2 = new IntermediateScenarioBundleGenerator();
        PrintWriter scenariosWriter = new PrintWriter(outScenarioIdsFilename);
        PrintWriter bundlesWriter = new PrintWriter(outBundleIdsFilename);) {
      
      Set<Integer> faultyScenarioIds = bundleGen2.getFaultyScenarioIds();
      System.out.println("faultyScenarioIds.size: " + faultyScenarioIds.size());
      scenariosWriter.print("faultyScenarioIds");
      for (Integer scenarioId : faultyScenarioIds) {
        scenariosWriter.print("," + scenarioId);
      }
      scenariosWriter.println();

      Set<Integer> zeroResponseIds = new HashSet<Integer>();
      Set<Integer> oneResponseIds = new HashSet<Integer>();
      Map<Integer, Integer> counts = bundleGen2.getResponseCounts();
      for (int i = MIN_ID; i<= MAX_ID; i++) {
        Integer count = counts.get(i);
        if (count == null) {
          zeroResponseIds.add(i);
        } else if (count == 1) {
          oneResponseIds.add(i);
        }
      }
      System.out.println("zeroResponseIds.size: " + zeroResponseIds.size());
      scenariosWriter.print("zeroResponseIds");
      for (Integer scenarioId : zeroResponseIds) {
        scenariosWriter.print("," + scenarioId);
      }
      scenariosWriter.println();

      System.out.println("oneResponseIds.size: " + oneResponseIds.size());
      scenariosWriter.print("oneResponseIds");
      for (Integer scenarioId : oneResponseIds) {
        scenariosWriter.print("," + scenarioId);
      }
      scenariosWriter.println();

      Map<Integer, String> BundleToScenarioMap = bundleGen2.getBundleToScenarioMapping();
      
      Set<Integer> firstBatchIds = new HashSet<Integer>();
      firstBatchIds.addAll(faultyScenarioIds);
      for (Integer bundleId : oneResponseIds) {
        String[] scenarios = BundleToScenarioMap.get(bundleId).split(",");
        for (String scenario : scenarios) {
          firstBatchIds.add(Integer.parseInt(scenario));
        }
      }
      for (Integer bundleId : zeroResponseIds) {
        String[] scenarios = BundleToScenarioMap.get(bundleId).split(",");
        for (String scenario : scenarios) {
          firstBatchIds.add(Integer.parseInt(scenario));
        }
      }
      System.out.println("firstBatchIds.size: " + firstBatchIds.size());
      // System.out.println("firstBatchIds: " + firstBatchIds);
      
      Set<Integer> secondBatchIds = new HashSet<Integer>();
      secondBatchIds.addAll(faultyScenarioIds);
      for (Integer bundleId : zeroResponseIds) {
        String[] scenarios = BundleToScenarioMap.get(bundleId).split(",");
        for (String scenario : scenarios) {
          secondBatchIds.add(Integer.parseInt(scenario));
        }
      }
      System.out.println("secondBatchIds.size: " + secondBatchIds.size());
      // System.out.println("secondBatchIds: " + secondBatchIds);
      
      List<Integer> allIds = new ArrayList<Integer>();
      allIds.addAll(firstBatchIds);
      allIds.addAll(secondBatchIds);
      Collections.shuffle(allIds);
      
      bundlesWriter.println("INSERT INTO scenario_bundle(scenarios_csv, num_completed) VALUES");
      while(allIds.size() > 10) {
        Set<Integer> indexes = new HashSet<Integer>();
        StringBuilder bundleBuilder = new StringBuilder();
        bundleBuilder.append("('");

        for (Iterator<Integer> iterator = allIds.iterator(); iterator.hasNext();) {
          Integer scenarioId = iterator.next();
          Integer index = ((scenarioId - 1) / 180) + 1;
          if (!indexes.contains(index)) {
            indexes.add(index);
            bundleBuilder.append(scenarioId + ",");
            iterator.remove();
          }
          if (indexes.size() == 5) {
            break;
          }
        }
        bundleBuilder.replace(bundleBuilder.length()-1, bundleBuilder.length(), "', 0),");
        bundlesWriter.println(bundleBuilder.toString());
      }
      bundlesWriter.println("Ids left out: " + allIds);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
