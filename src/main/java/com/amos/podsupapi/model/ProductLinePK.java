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
public class ProductLinePK implements Serializable{

	private static final long serialVersionUID = 1L;

	public ProductLinePK(int prodline1, int prodline3) {
		this.setProdline1(prodline1);
		this.setProdline3(prodline3);
	}
	
	@Column(name="I_PRODLINE1")
	private int prodline1;
	
	@Column(name="I_PRODLINE3")
	private int prodline3;
	
	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		ProductLinePK that = (ProductLinePK) o;

		if (prodline1 == that.getProdline1() && prodline3 ==that.getProdline3()) {
			return true;
		}
		
		return false;
	}
	
	public ProductLinePK() {}
	
	@Override
	public int hashCode() {
		return Objects.hash(getProdline1(), getProdline3());
	}
	
}
