package com.amos.podsupapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
// @Embeddable
@Table(name = "S_KEY")
// @JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelKey implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "I_SERIAL")
  private int serial;

  @Column(name = "S_TOPIC")
  private String topic;

  @Column(name = "S_SUBTOPIC")
  private String subTopic;

  @Column(name = "C_TYPE")
  private String type;

  @Column(name = "C_VALUE")
  private String chValue;

  @Column(name = "I_VALUE")
  private int value;

  @Column(name = "S_KEYWORD")
  private String keyWord;

  @Column(name = "S_SHORTNAME")
  private String shortName;

  @Column(name = "S_LONGNAME")
  private String lognName;

  @Column(name = "S_ENGLISHNAME")
  private String engName;
}
