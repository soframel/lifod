package org.soframel.lifod.repository.dao;

public class GraphData {
	private String name;
	private long average;
	private long max;
	private long min;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAverage() {
		return average;
	}
	public void setAverage(long average) {
		this.average = average;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getMin() {
		return min;
	}
	public void setMin(long min) {
		this.min = min;
	}
	@Override
	public String toString() {
		return "GraphData [name=" + name + ", average=" + average + ", max="
				+ max + ", min=" + min + "]";
	}
	
}
