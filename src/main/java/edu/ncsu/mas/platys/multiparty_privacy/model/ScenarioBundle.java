package edu.ncsu.mas.platys.multiparty_privacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scenario_bundle")
public class ScenarioBundle {

  @Id
  private int id;
  
  @Column(name = "scenarios_csv", nullable = false)
  private String scenariosCsv;

  @Column(name = "num_completed", nullable = false)
  private int numCompleted;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getScenariosCsv() {
    return scenariosCsv;
  }

  public void setScenariosCsv(String scenariosCsv) {
    this.scenariosCsv = scenariosCsv;
  }

  public int getNumCompleted() {
    return numCompleted;
  }

  public void setNumCompleted(int numCompleted) {
    this.numCompleted = numCompleted;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof ScenarioBundle))
      return false;
    ScenarioBundle other = (ScenarioBundle) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Scenario [id=" + id + "scenarios_csv=" + scenariosCsv + "]";
  }
}
