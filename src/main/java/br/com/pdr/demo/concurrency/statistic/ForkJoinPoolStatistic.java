package br.com.pdr.demo.concurrency.statistic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import br.com.pdr.demo.concurrency.ConcurrencyApplication;
import br.com.pdr.demo.concurrency.service.HighProccessService;
import br.com.pdr.demo.concurrency.service.Service;
import br.com.pdr.demo.concurrency.service.SmallProccessService;

public class ForkJoinPoolStatistic extends Statistic {

	private ForkJoinPool pool;

	public ForkJoinPoolStatistic() {
		
	}
	
	@Override
	public void init() {
		this.pool = new ForkJoinPool();
	}
	
	@Override
	public void destroy() {
		this.pool.shutdown();
	}

	public void executeHighProccess(HighProccessService service) throws Exception {
		executeForJoinPool(service);
	}
	
	@Override
	public void executeSmallProccess(SmallProccessService service)
			throws Exception {
		executeForJoinPool(service);
	}
	
	private void executeForJoinPool(Service service) throws Exception {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		int sliceData = Math.floorDiv(ConcurrencyApplication.SIZE_OF_THE_GAME,
				availableProcessors);
		List<ForkJoinTask<Boolean>> tasks = new ArrayList<ForkJoinTask<Boolean>>();
		int start = 0;
		int end = sliceData;
		for (int i = 0; i < availableProcessors; i++) {
			addTask(tasks, service, start, end);
			start = end + 1;
			end += sliceData;
		}

		for (ForkJoinTask<Boolean> forkJoinTask : tasks) {
			forkJoinTask.get();
		}
	}

	private void addTask(final List<ForkJoinTask<Boolean>> tasks,
			final Service service, final int start, final int end) {
		tasks.add(pool.submit(new ForkJoinTask<Boolean>() {

			private static final long serialVersionUID = 1L;

			Boolean result;

			@Override
			protected boolean exec() {
				service.playSliced(start, end);
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
