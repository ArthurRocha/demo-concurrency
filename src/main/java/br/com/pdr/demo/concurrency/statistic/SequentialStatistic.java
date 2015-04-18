package br.com.pdr.demo.concurrency.statistic;

import br.com.pdr.demo.concurrency.util.FakeService;

public class SequentialStatistic extends Statistic {

	private final int SIZE_OF_THE_GAME;
	
	public SequentialStatistic(int size) {
		this.SIZE_OF_THE_GAME = size;
	}
	
	public void execute() {
		long startTimeMillis = System.currentTimeMillis();
		FakeService fakeService = new FakeService(SIZE_OF_THE_GAME);
		fakeService.playSequential();
		this.timeMillis = System.currentTimeMillis() - startTimeMillis;
	}

}
