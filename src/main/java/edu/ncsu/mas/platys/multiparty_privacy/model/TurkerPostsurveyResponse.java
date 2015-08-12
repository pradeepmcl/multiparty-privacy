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
@Table(name = "turker_postsurvey_response")
public class TurkerPostsurveyResponse {

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
  
  @NotNull
  @Column(name = "scenario_bundle_id", nullable = false)
  private int scenarioBundleId;

  @Column(name = "completion_code")
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
  
  public int getScenarioBundleId() {
    return scenarioBundleId;
  }

  public void setScenarioBundleId(int scenarioBundleId) {
    this.scenarioBundleId = scenarioBundleId;
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
    if (!(obj instanceof TurkerPostsurveyResponse))
      return false;
    TurkerPostsurveyResponse other = (TurkerPostsurveyResponse) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "TurkerPresurveyResponse [id=" + id + "]";
  }
}
