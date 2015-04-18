package br.com.pdr.demo.concurrency.service;

import br.com.pdr.demo.concurrency.util.FakeModel;

public class SmallProccessService extends Service {

	private FakeModel[] models;

	public SmallProccessService(int size) {
		super(size);
	}

	public void createBigData() {
		models = new FakeModel[sizeOfTheGame];
		for (int i = 0; i < sizeOfTheGame; i++) {
			models[i] = new FakeModel(i, "Name " + i);
		}
	}

	public void playSequential() {
		for (FakeModel fakeModel : models) {
			fakeModel.setResultString(fakeModel.getName() + fakeModel.getId());
			fakeModel.setResultDouble(new Double(fakeModel.getId() + ""));
		}
	}

	public void playSliced(final int start, final int end) {
		for (int i = start; i < end; i++) {
			FakeModel fakeModel = models[i];
			fakeModel.setResultString(fakeModel.getName() + fakeModel.getId());
			fakeModel.setResultDouble(new Double(fakeModel.getId() + ""));
		}
	}

	public void playSequentialWithAsyncOperation() {
		// TODO
	}

	public void playSlicedWithAsyncOperation(int start, int end) {
		// TODO Auto-generated method stub
	}

}
