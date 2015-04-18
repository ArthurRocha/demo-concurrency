package br.com.pdr.demo.concurrency.util;

public class FakeModel {

	private int id;
	private String name;
	private String resultString;
	private Double resultDouble;
	
	public FakeModel() {
	}
	
	public FakeModel(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public Double getResultDouble() {
		return resultDouble;
	}

	public void setResultDouble(Double resultDouble) {
		this.resultDouble = resultDouble;
	}
}
