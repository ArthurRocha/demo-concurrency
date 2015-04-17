package br.com.pdr.demo.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolStatistic {
	
	private static final int SIZE_OF_THE_GAME = 10000;
	
	private ForkJoinPool pool;
	
	public ForkJoinPoolStatistic() {
		this.pool = new ForkJoinPool();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPoolStatistic forkJoinPoolStatistic = new ForkJoinPoolStatistic();
		long startTimeMillis = System.currentTimeMillis();
		forkJoinPoolStatistic.executeSequential();
		System.out.println("Sequential " + (System.currentTimeMillis() - startTimeMillis) + " ms");
		
		startTimeMillis = System.currentTimeMillis();
		forkJoinPoolStatistic.executeForkJoinPool();
		System.out.println("ForkJoinPool " + (System.currentTimeMillis() - startTimeMillis) + " ms");
	}
	
	private void executeSequential() {
		FakeService fakeService = new FakeService(SIZE_OF_THE_GAME);
		fakeService.playSequential();
	}

	public void executeForkJoinPool() throws InterruptedException, ExecutionException {
		FakeService fakeService = new FakeService(SIZE_OF_THE_GAME);
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
		
	}

	private void addStringBuilderTask(final List<ForkJoinTask<Boolean>> tasks, final FakeService fakeService, final int start, final int end) {
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
