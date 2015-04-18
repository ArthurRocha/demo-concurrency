package br.com.pdr.demo.concurrency;

import java.util.ArrayList;
import java.util.List;

import br.com.pdr.demo.concurrency.statistic.ForkJoinPoolStatistic;
import br.com.pdr.demo.concurrency.statistic.SequentialStatistic;
import br.com.pdr.demo.concurrency.statistic.Statistic;

public class ConcurrencyApplication {

	private static final int SIZE_OF_THE_GAME = 10000;
	
	public static void main(String[] args) throws Exception {
		List<Statistic> lsStatistics = new ArrayList<Statistic>();
		lsStatistics.add(new SequentialStatistic(SIZE_OF_THE_GAME));
		lsStatistics.add(new ForkJoinPoolStatistic(SIZE_OF_THE_GAME));
		
		for (Statistic statistic : lsStatistics) {
			statistic.execute();
			statistic.printStatistic();
			System.gc();
		}
	}
}
