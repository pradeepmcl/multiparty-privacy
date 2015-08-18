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

  @NotNull
  @Column(name = "sharing_experience", nullable = false)
  private String sharingExperience;

  @NotNull
  @Column(name = "conflict_experience", nullable = false)
  private String conflictExperience;
  
  @Column(name = "conflict_experience_type", nullable = false)
  private String conflictExperienceType;

  // I don't want to store array into conflictExperienceType in multiple tables.
  // That's why two fields.
  @Transient
  private String[] conflictExperienceTypeArray;

  @NotNull
  @Column(name = "relationship_importance", nullable = false)
  private String relationshipImportance;

  @NotNull
  @Column(name = "sensitivity_importance", nullable = false)
  private String sensitivityImportance;

  @NotNull
  @Column(name = "sentiment_importance", nullable = false)
  private String sentimentImportance;

  @NotNull
  @Column(name = "preference_importance", nullable = false)
  private String preferenceImportance;

  @NotNull
  @Column(name = "argument_importance", nullable = false)
  private String argumentImportance;
  
  @NotNull
  @Column(name = "no_preference_confidence", nullable = false)
  private String noPreferenceConfidence;

  @NotNull
  @Column(name = "preference_confidence", nullable = false)
  private String preferenceConfidence;
  
  @NotNull
  @Column(name = "preference_argument_confidence", nullable = false)
  private String preferenceArgumentConfidence;
  
  @NotNull
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

  public String getSharingExperience() {
    return sharingExperience;
  }

  public void setSharingExperience(String sharingExperience) {
    this.sharingExperience = sharingExperience;
  }

  public String getConflictExperience() {
    return conflictExperience;
  }

  public void setConflictExperience(String conflictExperience) {
    this.conflictExperience = conflictExperience;
  }

  public String getSensitivityImportance() {
    return sensitivityImportance;
  }

  public void setSensitivityImportance(String sensitivityImportance) {
    this.sensitivityImportance = sensitivityImportance;
  }

  public String getRelationshipImportance() {
    return relationshipImportance;
  }

  public void setRelationshipImportance(String relationshipImportance) {
    this.relationshipImportance = relationshipImportance;
  }

  public String getSentimentImportance() {
    return sentimentImportance;
  }

  public void setSentimentImportance(String sentimentImportance) {
    this.sentimentImportance = sentimentImportance;
  }

  public String getConflictExperienceType() {
    return conflictExperienceType;
  }

  public void setConflictExperienceType(String conflictExperienceType) {
    this.conflictExperienceType = conflictExperienceType;
  }

  public String[] getConflictExperienceTypeArray() {
    return conflictExperienceTypeArray;
  }

  public void setConflictExperienceTypeArray(String[] conflictExperienceTypeArray) {
    this.conflictExperienceTypeArray = conflictExperienceTypeArray;
  }

  public String getPreferenceImportance() {
    return preferenceImportance;
  }

  public void setPreferenceImportance(String preferenceImportance) {
    this.preferenceImportance = preferenceImportance;
  }

  public String getArgumentImportance() {
    return argumentImportance;
  }

  public void setArgumentImportance(String argumentImportance) {
    this.argumentImportance = argumentImportance;
  }

  public String getNoPreferenceConfidence() {
    return noPreferenceConfidence;
  }

  public void setNoPreferenceConfidence(String noPreferenceConfidence) {
    this.noPreferenceConfidence = noPreferenceConfidence;
  }

  public String getPreferenceConfidence() {
    return preferenceConfidence;
  }

  public void setPreferenceConfidence(String preferenceConfidence) {
    this.preferenceConfidence = preferenceConfidence;
  }

  public String getPreferenceArgumentConfidence() {
    return preferenceArgumentConfidence;
  }

  public void setPreferenceArgumentConfidence(String preferenceArgumentConfidence) {
    this.preferenceArgumentConfidence = preferenceArgumentConfidence;
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
