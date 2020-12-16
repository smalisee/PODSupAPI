package com.amos.podsupapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "W05_P_FACTORY_POD_FILE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PODFile implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

 
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "W05_P_FACTORY_POD_FILE_SEQ")
//  @SequenceGenerator(name = "W05_P_FACTORY_POD_FILE_SEQ", sequenceName = "W05_P_FACTORY_POD_FILE_SEQ", allocationSize = 1,
//      initialValue = 1)
  @Column(name = "I_SERIAL")
  private Integer id;

  @Id
  @Column(name = "I_FILE")
  private int i_file;

  @Column(name = "S_URL")
  private String s_url;

  @Column(name = "S_FILE_NAME")
  private String s_file_name;
  
  
  @Column(name = "I_PONO")
  private int poNo;

  @Column(name = "D_CREATE")
  private LocalDateTime d_create;
}
