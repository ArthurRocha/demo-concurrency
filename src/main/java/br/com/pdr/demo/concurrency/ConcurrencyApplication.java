package br.com.pdr.demo.concurrency;

import java.util.ArrayList;
import java.util.List;

import br.com.pdr.demo.concurrency.service.HighProccessService;
import br.com.pdr.demo.concurrency.statistic.ForkJoinPoolStatistic;
import br.com.pdr.demo.concurrency.statistic.SequentialStatistic;
import br.com.pdr.demo.concurrency.statistic.Statistic;

public class ConcurrencyApplication {

	/**
	 * Este valor influencia na quantidade de objetos criados nos servicos 
	 * e na quantidade de execuções realizadas sobre ele.
	 * ex.:
	 * HighProccessService 
	 * 10000 instancias 
	 * 10000 * 10000 = 100000000 processamentos executados
	 */
	public static int SIZE_OF_THE_GAME = 10000;

	public static void main(String[] args) throws Exception {
		List<Statistic> lsStatistics = new ArrayList<Statistic>();
		lsStatistics.add(new SequentialStatistic());
		lsStatistics.add(new ForkJoinPoolStatistic());
		for (Statistic statistic : lsStatistics) {
			statistic.execute(new HighProccessService(SIZE_OF_THE_GAME));
			statistic.printStatistic();
			System.gc();
		}
		//howLongWeCanGo(lsStatistics);
	}

	public static void howLongWeCanGo(final List<Statistic> lsStatistics)
			throws Exception {
		while (true) {
			System.out.println("-------------------");
			System.out.println("\t the game grew\t" + SIZE_OF_THE_GAME);
			SIZE_OF_THE_GAME += 100;
			for (Statistic statistic : lsStatistics) {
				statistic.execute(new HighProccessService(SIZE_OF_THE_GAME));
				statistic.printStatistic();
				System.gc();
			}
		}
	}
}
