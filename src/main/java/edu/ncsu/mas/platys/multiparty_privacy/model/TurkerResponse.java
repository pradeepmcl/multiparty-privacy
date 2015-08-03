package edu.ncsu.mas.platys.multiparty_privacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "turker_response")
public class TurkerResponse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotBlank
  @Column(name = "mturk_id", nullable = false)
  private String mturkId;

  @NotNull
  @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  @Column(name = "response_time", nullable = false)
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
  private LocalDateTime responseTime;

  // See my comment about lazy initialization in Scenario class
  @NotNull
  @Column(name = "scenario_id", nullable = false)
  private int scenarioId;

  @NotBlank
  @Column(name = "policy", nullable = false)
  private String policy;
  
  @Column(name = "policy_other")
  private String policyOther;
    
  @NotBlank
  @Column(name = "policy_justification", nullable = false)
  private String policyJustification;

  @NotNull
  @Column(name = "completion_code", nullable = false)
  private String completionCode;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMturkId() {
    return mturkId;
  }

  public void setMturkId(String mturkId) {
    this.mturkId = mturkId;
  }

  public LocalDateTime getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(LocalDateTime responseTime) {
    this.responseTime = responseTime;
  }

  public int getScenarioId() {
    return scenarioId;
  }

  public void setScenarioId(int scenarioId) {
    this.scenarioId = scenarioId;
  }

  public String getPolicy() {
    return policy;
  }

  public void setPolicy(String policy) {
    this.policy = policy;
  }

  public String getPolicyOther() {
    return policyOther;
  }

  public void setPolicyOther(String policyOther) {
    this.policyOther = policyOther;
  }

  public String getPolicyJustification() {
    return policyJustification;
  }

  public void setPolicyJustification(String policyJustification) {
    this.policyJustification = policyJustification;
  }

  public String getCompletionCode() {
    return completionCode;
  }

  public void setCompletionCode(String completionCode) {
    this.completionCode = completionCode;
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
    if (!(obj instanceof TurkerResponse))
      return false;
    TurkerResponse other = (TurkerResponse) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + "]";
  }
}