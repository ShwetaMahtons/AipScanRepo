package com.castsoftware.common.logparser;

public abstract class Element {

protected Element(String key) {
		super();
		this.key = key;
	}

private String key;
private boolean compared = false;

public boolean isCompared() {
	return compared;
}

public void setCompared(boolean compared) {
	this.compared = compared;
}

public String getKey() {
	return key;
}

public void setKey(String key) {
	this.key = key;
}

@Override
public String toString() {
	return "Element [key=" + key + "]";
}


}
