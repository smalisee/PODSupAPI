package com.amos.podsupapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "P_PRODLINE", schema = "MDLNX")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductLine implements Serializable {

  private static final long serialVersionUID = 1L;

  // @EmbeddedId
  // private ProductLinePK pk;
  //
  // public ProductLine() { }
  //
  // public ProductLine(ProductLinePK pordPK) {
  // this.pk = pordPK;
  // }
  @Id
  @JsonIgnore
  @JsonProperty("prodline1")
  @Column(name = "I_PRODLINE1")
  private int prodline1;

  @Id
  @JsonIgnore
  @JsonProperty("prodline3")
  @Column(name = "I_PRODLINE3")
  private int prodline3;

  @JsonIgnore
  @JsonProperty("mapProd")
  private String mapProd;

  @JsonIgnore
  @ManyToMany(mappedBy = "prodLines")
  private List<User> users = new ArrayList<>();

}
