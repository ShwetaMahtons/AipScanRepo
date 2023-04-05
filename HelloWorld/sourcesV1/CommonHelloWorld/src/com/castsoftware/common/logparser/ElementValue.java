package com.castsoftware.common.logparser;

public class ElementValue extends Element {

	private float value;

	public float getValue() {
		return value;
	}

	public ElementValue(String key, float value) {
		super(key);
		this.value = value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ElementValue [key=" + getKey() + ", value=" + value + "]";
	}

}
