package br.com.pdr.demo.concurrency.service;

import br.com.pdr.demo.concurrency.util.FakeModel;

public class HighProccessService extends Service {

	private FakeModel[] models;
	
	public HighProccessService(int size) {
		super(size);
	}
	
	public void createBigData() {
		models = new FakeModel[sizeOfTheGame];
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
	
	public void playSequentialWithAsyncOperation () {
		// TODO
	}

	public void playSlicedWithAsyncOperation(int start, int end) {
		// TODO Auto-generated method stub
	}
	
	// PROCCESS -----------------------------------------------
	
	private double playWithNumbers() {
		double result = 616;
		for (int i = 0 ; i < sizeOfTheGame ; i ++) {
			result = ((i * result) / 3.14) * 2;
		}
		return result;
	}
	
	private StringBuilder playWithStringBuilder() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < sizeOfTheGame ; i ++) {
			sb.append(i);
		}
		return sb;
	}

}
