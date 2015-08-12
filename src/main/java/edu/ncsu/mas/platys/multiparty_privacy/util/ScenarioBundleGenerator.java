package edu.ncsu.mas.platys.multiparty_privacy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * This is a utility class meant to run before the webapp is used. The class
 * populates the ScenarioBundle table in the database, which the webapp employs.
 * Scenarios must be generated (ScenarioGenerator) before generating the
 * ScenarioBundles.
 * 
 * @author pmuruka
 *
 */
public class ScenarioBundleGenerator {
  
  private static final int NUM_IMAGES = 9;
  private static final int NUM_POLICY_ARGS_COMBINATIONS = 180; // 6 * 6 * 5
  private static final int NUM_SCENARIOS_BUNDLE = 5;
  
  private Random rand = new Random();

  private List<String> getScenarioBundles() {
    // Initialize lists
    List<List<Integer>> scenarioIds = new ArrayList<List<Integer>>();
    for (int i = 0; i < NUM_IMAGES; i++) {
      List<Integer> scenarioIdsPerImage = new ArrayList<Integer>();
      for (int j = 1; j <= NUM_POLICY_ARGS_COMBINATIONS; j++) {
        scenarioIdsPerImage.add((i * NUM_POLICY_ARGS_COMBINATIONS) + j);
      }
      scenarioIds.add(scenarioIdsPerImage);
    }
    
    Set<String> allScenarioIds = new TreeSet<String>();
    List<String> scenarioBundles = new ArrayList<String>();
    
    int scenarioBundleIndex = 0;
    StringBuffer scenariosBuffer = new StringBuffer();
    while (true) {
      if (getNestedListSize(scenarioIds) < NUM_SCENARIOS_BUNDLE) {
        if (getNestedListSize(scenarioIds) != 0) {
          System.out.println("Some left overs; manually insert them. Left overs: " + scenarioIds);
        }
        break;
      }
      
      for (Iterator<List<Integer>> iterator = scenarioIds.iterator(); iterator.hasNext();) {
        List<Integer> scenarioIdsPerImage = iterator.next();
        if (scenarioIdsPerImage.isEmpty()) {
          iterator.remove();
        }

        int nextScenarioIndex = -1;
        int nextScenarioId = -1;
        int numAttempts = 0;
        do {
          nextScenarioIndex = randInt(0, scenarioIdsPerImage.size());
          nextScenarioId = scenarioIdsPerImage.get(nextScenarioIndex);
          if ((++numAttempts % 1000) == 0) {
            System.out.println("I am spending too much time here (after " + scenarioBundles.size()
                + ")! Better to rerun");
          }
        } while (scenariosBuffer.toString().contains(Integer.toString(nextScenarioId)));
        
        scenariosBuffer.append(nextScenarioId + ",");
        scenarioIdsPerImage.remove(nextScenarioIndex);
        
        if (++scenarioBundleIndex == NUM_SCENARIOS_BUNDLE) {
          // Remove last comma
          scenariosBuffer.replace(scenariosBuffer.length() - 1, scenariosBuffer.length(), "");
          
          allScenarioIds.addAll(Arrays.asList(scenariosBuffer.toString().split(",")));
          scenarioBundles.add(scenariosBuffer.toString());

          scenarioBundleIndex = 0;
          scenariosBuffer.setLength(0);
        }
      }
    }
    System.out.println("Total number of bundles: " + allScenarioIds.size());
    return scenarioBundles;
  }
  
  private void insertScenarioBundlesToDb(List<String> scenarioBundles) throws SQLException {
    Properties props = new Properties();
    try (InputStream inStream = new FileInputStream("src/main/resources/application.properties")) {
      props.load(inStream);
      Class.forName(props.getProperty("jdbc.driverClassName"));
    } catch (ClassNotFoundException | IllegalStateException | IOException e) {
      e.printStackTrace();
      return;
    }

    try (Connection conn = DriverManager.getConnection(props.getProperty("jdbc.url") + "?user="
        + props.getProperty("jdbc.username") + "&password=" + props.getProperty("jdbc.password"));
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO scenario_bundle "
            + "(scenarios_csv, num_completed) values (?, ?)")) {
      int i = 0;
      for (String scenarioBundle : scenarioBundles) {
        stmt.setString(1, scenarioBundle);
        stmt.setInt(2, 0);
        stmt.addBatch();
        i++;
        if (i % 1000 == 0) {
          stmt.executeBatch();
        }
      }
      stmt.executeBatch(); // Left overs
    }
  }
  
  private int getNestedListSize(List<List<Integer>> list) {
    int size = 0;
    for (List<Integer> nestedList : list) {
      size += nestedList.size();
    }
    return size;
  }
  
  /**
   * Returns a pseudo-random number between min (inclusive) and max (exclusive).
   */
  private int randInt(int min, int max) {
    int randomNum = rand.nextInt((max - min)) + min;
    return randomNum;
  }
  
  public static void main(String[] args) throws SQLException {
    ScenarioBundleGenerator scenarioBundleGen = new ScenarioBundleGenerator();
    List<String> scenarioBundles = scenarioBundleGen.getScenarioBundles();
    scenarioBundleGen.insertScenarioBundlesToDb(scenarioBundles);
  }
}
