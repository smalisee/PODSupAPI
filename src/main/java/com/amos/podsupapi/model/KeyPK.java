package com.amos.podsupapi.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class KeyPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public KeyPK(String item, String value) {
		this.setItem(item);
		this.setValue(value);
	}
	
	@Column(name="S_KEY_ITEM")
	private String item;
	
	@Column(name="S_KEY_VALUE")
	private String value;
	
	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		KeyPK that = (KeyPK) o;

		if (item.equals(that.getItem()) && value.equals(that.getValue())) {
			return true;
		}
		
		return false;
	}

	public KeyPK() { }
	
	@Override
	public int hashCode() {
		return Objects.hash(getItem(), getValue());
	}
	
}
