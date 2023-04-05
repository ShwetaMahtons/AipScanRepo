package com.castsoftware.common.logparser;

public class ElementBound extends Element {

	private Float refValue;
	private Float pctMin;
	private Float pctMax;
	private Integer nbMin;
	private Integer nbMax;
	private Boolean mustExists;
	private Boolean compare;
	
	public ElementBound(String key, Float refValue, Float pctMin, Float pctMax,
			Integer nbMin, Integer nbMax, Boolean mustExists) {
		super(key);
		this.refValue = refValue;
		this.pctMin = pctMin;
		this.pctMax = pctMax;
		this.nbMin = nbMin;
		this.nbMax = nbMax;
		this.mustExists = mustExists;
	}

	public ElementBound(String key) {
		super(key);
	}
	
	public Float getPctMin() {
		return pctMin;
	}
	public void setPctMin(Float pctMin) {
		this.pctMin = pctMin;
	}
	public Float getPctMax() {
		return pctMax;
	}
	public void setPctMax(Float pctMax) {
		this.pctMax = pctMax;
	}
	public Integer getNbMin() {
		return nbMin;
	}
	public void setNbMin(Integer nbMin) {
		this.nbMin = nbMin;
	}
	public Integer getNbMax() {
		return nbMax;
	}
	public void setNbMax(Integer nbMax) {
		this.nbMax = nbMax;
	}
	public boolean isMustExists() {
		return mustExists;
	}
	public void setMustExists(boolean mustExists) {
		this.mustExists = mustExists;
	}
	
	public Float getRefValue() {
		return refValue;
	}
	public void setRefValue(Float refValue) {
		this.refValue = refValue;
	}
	
	public Boolean isCompare() {
		return compare;
	}

	public void setCompare(boolean compare) {
		this.compare = compare;
	}

	@Override
	public String toString() {
		return "ElementBound [key=" + getKey() + ", mustExists=" + mustExists + ", nbMax=" + nbMax
				+ ", nbMin=" + nbMin + ", pctMax=" + pctMax + ", pctMin="
				+ pctMin + ", refValue=" + refValue + ", compare=" + compare + "]";
	}
}
