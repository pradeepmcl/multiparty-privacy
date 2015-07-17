package edu.ncsu.mas.platys.multiparty_privacy.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "scenario")
public class Scenario {

  @Id
  private int id;

  /*
   * TODO: Two reasons for choosing eager initialization: (1) I think it is the
   * right option here, since we will use the objects for sure, and (2) I dont
   * know how to fix the "org.hibernate.LazyInitializationException: could not
   * initialize proxy - no Session" exception, which was thrown when I used lazy
   * initialization
   */
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "image_id")
  private Image image;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "policy_a_id")
  private Policy policyA;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "argument_a_id")
  private Argument argumentA;
  
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "policy_B_id")
  private Policy policyB;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "argument_b_id")
  private Argument argumentB;
  
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "policy_c_id")
  private Policy policyC;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "argument_c_id")
  private Argument argumentC;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public Policy getPolicyA() {
    return policyA;
  }

  public void setPolicyA(Policy policy) {
    this.policyA = policy;
  }

  public Argument getArgumentA() {
    return argumentA;
  }

  public void setArgumentA(Argument argument) {
    this.argumentA = argument;
  }

  public Policy getPolicyB() {
    return policyB;
  }

  public void setPolicyB(Policy policy) {
    this.policyB = policy;
  }

  public Argument getArgumentB() {
    return argumentB;
  }

  public void setArgumentB(Argument argument) {
    this.argumentB = argument;
  }
  
  public Policy getPolicyC() {
    return policyC;
  }

  public void setPolicyC(Policy policy) {
    this.policyC = policy;
  }

  public Argument getArgumentC() {
    return argumentC;
  }

  public void setArgumentC(Argument argument) {
    this.argumentC = argument;
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
    if (!(obj instanceof Scenario))
      return false;
    Scenario other = (Scenario) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Scenario [id=" + id + ", image.name=" + image.getName() + ", policyA.name="
        + policyA.getName() + ", argumentA.name=" + argumentA.getName() + "]";
  }
}
