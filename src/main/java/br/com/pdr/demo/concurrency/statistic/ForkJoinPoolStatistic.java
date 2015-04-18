package br.com.pdr.demo.concurrency.statistic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import br.com.pdr.demo.concurrency.service.HighProccessService;

public class ForkJoinPoolStatistic extends Statistic {

	private final int SIZE_OF_THE_GAME;
	private ForkJoinPool pool;
	
	
	public ForkJoinPoolStatistic(int size) {
		this.pool = new ForkJoinPool();
		this.SIZE_OF_THE_GAME = size;
	}
	
	public void execute() throws InterruptedException, ExecutionException {
		long startTimeMillis = System.currentTimeMillis();
		HighProccessService fakeService = new HighProccessService(SIZE_OF_THE_GAME);
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		int sliceData = Math.floorDiv(SIZE_OF_THE_GAME, availableProcessors);
		List<ForkJoinTask<Boolean>> tasks = new ArrayList<ForkJoinTask<Boolean>>();
		int start = 0;
		int end = sliceData; 
		for (int i = 0 ; i < availableProcessors ; i ++) {
			addStringBuilderTask(tasks, fakeService, start, end);
			start = end + 1;
			end += sliceData;
		}
		
		for (ForkJoinTask<Boolean> forkJoinTask : tasks) {
			forkJoinTask.get();
		}
		this.timeMillis = System.currentTimeMillis() - startTimeMillis;
	}

	private void addStringBuilderTask(final List<ForkJoinTask<Boolean>> tasks, final HighProccessService fakeService, final int start, final int end) {
		tasks.add(pool.submit(new ForkJoinTask<Boolean>() {

			private static final long serialVersionUID = 1L;
			
			Boolean result;
			
			@Override
			protected boolean exec() {
				fakeService.playSliced(start, end);
				setRawResult(true);
				return getRawResult();
			}

			@Override
			public Boolean getRawResult() {
				return result;
			}

			@Override
			protected void setRawResult(Boolean value) {
				this.result = value;
			}
			
		}));
	}
	
}
