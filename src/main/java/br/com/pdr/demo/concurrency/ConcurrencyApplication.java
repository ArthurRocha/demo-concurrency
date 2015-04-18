package br.com.pdr.demo.concurrency;

import java.util.ArrayList;
import java.util.List;

import br.com.pdr.demo.concurrency.statistic.ForkJoinPoolStatistic;
import br.com.pdr.demo.concurrency.statistic.SequentialStatistic;
import br.com.pdr.demo.concurrency.statistic.Statistic;

public class ConcurrencyApplication {

	/*
	 * O tamanho da brincadeira.
	 * Este valor influencia na quantidade de 'FakeModel' criados e na quantidade de execuções realizadas sobre ele.
	 * ex.:
	 * 10000 instancias
	 * 10000 * 10000 = 100000000 processamentos executados ()
	 */
	private static int SIZE_OF_THE_GAME = 10000;
	
	public static void main(String[] args) throws Exception {
		List<Statistic> lsStatistics = new ArrayList<Statistic>();
		lsStatistics.add(new SequentialStatistic(SIZE_OF_THE_GAME));
		lsStatistics.add(new ForkJoinPoolStatistic(SIZE_OF_THE_GAME));
		
		for (Statistic statistic : lsStatistics) {
			statistic.execute();
			statistic.printStatistic();
			System.gc();
		}
		howLongWeCanGo();
	}
	
	public static void howLongWeCanGo() throws Exception {
		while(true) {
			SIZE_OF_THE_GAME += 100;
			List<Statistic> lsStatistics = new ArrayList<Statistic>();
			lsStatistics.add(new SequentialStatistic(SIZE_OF_THE_GAME));
			lsStatistics.add(new ForkJoinPoolStatistic(SIZE_OF_THE_GAME));
			for (Statistic statistic : lsStatistics) {
				System.out.println("Game size\t\t" + SIZE_OF_THE_GAME);
				statistic.execute();
				statistic.printStatistic();
				System.gc();
			}
		}
	}
}
