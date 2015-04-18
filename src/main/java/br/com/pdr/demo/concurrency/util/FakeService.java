package br.com.pdr.demo.concurrency.util;

public class FakeService {

	private int sizeOfTheGame; 
	private FakeModel[] models;
	
	public FakeService(int size) {
		this.sizeOfTheGame = size;
		models = new FakeModel[sizeOfTheGame];
		this.createBigData();
	}
	
	public void createBigData() {
		for (int i = 0 ; i < sizeOfTheGame ; i ++) {
			models[i] = new FakeModel(i, "Name " + i);
		}
	}
	
	public void playSequential () {
		for (FakeModel fakeModel : models) {
			fakeModel.setResultString(playWithStringBuilder().toString());
			fakeModel.setResultDouble(playWithNumbers());
		}
	}
	
	public void playSliced(final int start, final int end) {
		for (int i = start ; i < end ; i ++ ) {
			FakeModel fakeModel = models[i];
			fakeModel.setResultString(playWithStringBuilder().toString());
			fakeModel.setResultDouble(playWithNumbers());
		}
	}
	
	public double playWithNumbers() {
		double result = 616;
		for (int i = 0 ; i < sizeOfTheGame ; i ++) {
			result = ((i * result) / 3.14) * 2;
		}
		return result;
	}
	
	public StringBuilder playWithStringBuilder() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < sizeOfTheGame ; i ++) {
			sb.append(i);
		}
		return sb;
	}
}
