package com.amos.podsupapi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="W02_C_KEY")
public class Key implements Serializable{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KeyPK pk;
	
	@Column(name="S_DESCRIPTION")
	private String name;
	
	@Column(name="I_SORTING")
	private Integer sorting;
	
	public Key() { }
	
	public Key(KeyPK keyPK, String name, Integer sorting) {
        this.pk = keyPK;
        this.name = name;
        this.sorting = sorting;
    }

}
