package edu.ncsu.mas.platys.multiparty_privacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDate;
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
  @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
  @Column(name = "response_time", nullable = false)
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
  private LocalDate responseTime;

  // See my comment about lazy initialization in Scenario class
  @NotNull
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "scenario_id")
  private Scenario scenario;

  @NotBlank
  @Column(name = "policy_response", nullable = false)
  private String policyResponse;

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

  public LocalDate getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(LocalDate responseTime) {
    this.responseTime = responseTime;
  }

  public Scenario getScenario() {
    return scenario;
  }

  public void setScenario(Scenario scenario) {
    this.scenario = scenario;
  }

  public String getPolicyResponse() {
    return policyResponse;
  }

  public void setPolicyResponse(String policyResponse) {
    this.policyResponse = policyResponse;
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
