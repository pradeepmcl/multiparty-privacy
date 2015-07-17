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
   * know how to fix the rg.hibernate.LazyInitializationException: could not
   * initialize proxy - no Session , which I received using lazy initialization
   */
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "image_id")
  private Image image;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "policy_id")
  private Policy policy;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "argument_id")
  private Argument argument;

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

  public Policy getPolicy() {
    return policy;
  }

  public void setPolicy(Policy policy) {
    this.policy = policy;
  }

  public Argument getArgument() {
    return argument;
  }

  public void setArgument(Argument argument) {
    this.argument = argument;
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
    return "Scenario [id=" + id + ", image.name=" + image.getName() + ", policy.name="
        + policy.getName() + ", argument.name=" + argument.getName() + "]";
  }
}
