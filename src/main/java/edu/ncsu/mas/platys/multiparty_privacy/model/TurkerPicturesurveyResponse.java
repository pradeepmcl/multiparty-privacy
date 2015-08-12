package edu.ncsu.mas.platys.multiparty_privacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "turker_picturesurvey_response")
public class TurkerPicturesurveyResponse {

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

  @NotNull
  @Column(name = "scenario_id", nullable = false)
  private int scenarioId;
  
  @NotNull
  @Column(name = "image_sensitivity", nullable = false)
  private String imageSensitivity;
  
  @NotNull
  @Column(name = "image_sentiment", nullable = false)
  private String imageSentiment;

  @NotNull
  @Column(name = "image_relationship", nullable = false)
  private String imageRelationship;

  @NotNull
  @Column(name = "image_people_count", nullable = false)
  private String imagePeopleCount;

  @NotBlank
  @Column(name = "case1_policy", nullable = false)
  private String case1Policy;
  
  @Column(name = "case1_policy_other")
  private String case1PolicyOther;
    
  @NotBlank
  @Column(name = "case1_policy_justification", nullable = false)
  private String case1PolicyJustification;
  
  @NotBlank
  @Column(name = "case2_policy", nullable = false)
  private String case2Policy;
  
  @Column(name = "case2_policy_other")
  private String case2PolicyOther;
    
  @NotBlank
  @Column(name = "case2_policy_justification", nullable = false)
  private String case2PolicyJustification;
  
  @Transient
  private int scenarioBundleIndex;
  
  @Transient
  private String scenariosCsv;

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

  public int getScenarioId() {
    return scenarioId;
  }

  public void setScenarioId(int scenarioId) {
    this.scenarioId = scenarioId;
  }

  public String getCase1Policy() {
    return case1Policy;
  }

  public void setCase1Policy(String policy) {
    this.case1Policy = policy;
  }

  public String getCase1PolicyOther() {
    return case1PolicyOther;
  }

  public void setCase1PolicyOther(String policyOther) {
    this.case1PolicyOther = policyOther;
  }

  public String getCase1PolicyJustification() {
    return case1PolicyJustification;
  }

  public void setCase1PolicyJustification(String policyJustification) {
    this.case1PolicyJustification = policyJustification;
  }

  public String getImageSensitivity() {
    return imageSensitivity;
  }

  public void setImageSensitivity(String imageSensitivity) {
    this.imageSensitivity = imageSensitivity;
  }

  public String getImageSentiment() {
    return imageSentiment;
  }

  public void setImageSentiment(String imageSentiment) {
    this.imageSentiment = imageSentiment;
  }

  public String getImageRelationship() {
    return imageRelationship;
  }

  public void setImageRelationship(String imageRelationship) {
    this.imageRelationship = imageRelationship;
  }

  public String getImagePeopleCount() {
    return imagePeopleCount;
  }

  public void setImagePeopleCount(String imagePeopleCount) {
    this.imagePeopleCount = imagePeopleCount;
  }

  public String getCase2Policy() {
    return case2Policy;
  }

  public void setCase2Policy(String case2Policy) {
    this.case2Policy = case2Policy;
  }

  public String getCase2PolicyOther() {
    return case2PolicyOther;
  }

  public void setCase2PolicyOther(String case2PolicyOther) {
    this.case2PolicyOther = case2PolicyOther;
  }

  public String getCase2PolicyJustification() {
    return case2PolicyJustification;
  }

  public void setCase2PolicyJustification(String case2PolicyJustification) {
    this.case2PolicyJustification = case2PolicyJustification;
  }
  
  public String getScenariosCsv() {
    return scenariosCsv;
  }

  public void setScenariosCsv(String scenariosCsv) {
    this.scenariosCsv = scenariosCsv;
  }

  public int getScenarioBundleIndex() {
    return scenarioBundleIndex;
  }

  public void setScenarioBundleIndex(int scenarioBundleIndex) {
    this.scenarioBundleIndex = scenarioBundleIndex;
  }

  public String getPolicy(String _case) {
    if (_case.equals("case1")) {
      return case1Policy;
    } else if (_case.equals("case2")) {
      return case2Policy;
    } else {
      throw new IllegalArgumentException("No such case as " + _case);
    }
  }

  public String getPolicyOther(String _case) {
    if (_case.equals("case1")) {
      return case1PolicyOther;
    } else if (_case.equals("case2")) {
      return case2PolicyOther;
    } else {
      throw new IllegalArgumentException("No such case as " + _case);
    }
  }

  public String getPolicyJustification(String _case) {
    if (_case.equals("case1")) {
      return case1PolicyJustification;
    } else if (_case.equals("case2")) {
      return case2PolicyJustification;
    } else {
      throw new IllegalArgumentException("No such case as " + _case);
    }
  }

  public void resetResponse() {
    imageSensitivity = null;
    imageSentiment = null;
    imageRelationship = null;
    imagePeopleCount = null;
    case1Policy = null;
    case1PolicyOther = null;
    case1PolicyJustification = null;
    case2Policy = null;
    case2PolicyOther = null;
    case2PolicyJustification = null;
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
    if (!(obj instanceof TurkerPicturesurveyResponse))
      return false;
    TurkerPicturesurveyResponse other = (TurkerPicturesurveyResponse) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + "]";
  }
}
