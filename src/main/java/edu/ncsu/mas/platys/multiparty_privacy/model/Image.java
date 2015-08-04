package edu.ncsu.mas.platys.multiparty_privacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image {

  @Id
  private int id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "people", nullable = false)
  private String people;

  @Column(name = "image_description", nullable = false)
  private String imageDescription;
  
  @Column(name = "sharer", nullable = false)
  private String sharer;
  
  @Column(name = "sharing_description", nullable = false)
  private String sharingDescription;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageDescription() {
    return imageDescription;
  }

  public void setImageDescription(String description) {
    this.imageDescription = description;
  }

  public String getPeople() {
    return people;
  }

  public void setPeople(String people) {
    this.people = people;
  }

  public String getSharer() {
    return sharer;
  }

  public void setSharer(String sharer) {
    this.sharer = sharer;
  }

  public String getSharingDescription() {
    return sharingDescription;
  }

  public void setSharingDescription(String sharingDescription) {
    this.sharingDescription = sharingDescription;
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
    if (!(obj instanceof Image))
      return false;
    Image other = (Image) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Image [id=" + id + ", name=" + name + ", description=" + imageDescription + "]";
  }
}
