package br.com.pdr.demo.concurrency;

import java.util.ArrayList;
import java.util.List;

import br.com.pdr.demo.concurrency.statistic.DisruptorStatistic;
import br.com.pdr.demo.concurrency.statistic.ForkJoinPoolStatistic;
import br.com.pdr.demo.concurrency.statistic.SequentialStatistic;
import br.com.pdr.demo.concurrency.statistic.Statistic;

public class ConcurrencyApplication {

	public static boolean HIGH_PROCCESS_ON = true;
	public static boolean SMALL_PROCCESS_ON = true;
	
	/**
	 * Este valor influencia na quantidade de objetos criados nos servicos 
	 * e na quantidade de execuções realizadas sobre ele.
	 * ex.:
	 * HighProccessService 
	 * 10000 instancias 
	 * 10000 * 10000 = 100000000 processamentos executados
	 */
	public static int SIZE_OF_THE_GAME;
	
	public static int INCREASE_GAME_SIZE;
	
	static {
		if (HIGH_PROCCESS_ON) {
			SIZE_OF_THE_GAME = 10000;
			INCREASE_GAME_SIZE = 100;
		} else {
			SIZE_OF_THE_GAME = 1000000;
			INCREASE_GAME_SIZE = 1000;
		}
	}

	public static void main(String[] args) throws Exception {
		List<Statistic> lsStatistics = new ArrayList<Statistic>();
		
		lsStatistics.add(new SequentialStatistic()); // TODO lsStatistics.add(new SequentialStatistic(new Service strategy operation ));
		lsStatistics.add(new ForkJoinPoolStatistic());
//		lsStatistics.add(new DisruptorStatistic());
		
		for (Statistic statistic : lsStatistics) {
			statistic.execute(SIZE_OF_THE_GAME);
			statistic.printStatistic();
		}
		howLongWeCanGo(lsStatistics);
	}

	public static void howLongWeCanGo(final List<Statistic> lsStatistics)
			throws Exception {
		while (true) {
			SIZE_OF_THE_GAME += INCREASE_GAME_SIZE;
			System.out.println("-------------------");
			System.out.println("\t the game grew\t" + SIZE_OF_THE_GAME);
			for (Statistic statistic : lsStatistics) {
				statistic.execute(SIZE_OF_THE_GAME);
				statistic.printStatistic();
				System.gc();
			}
		}
	}
}
