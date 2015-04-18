package br.com.pdr.demo.concurrency.statistic;

import br.com.pdr.demo.concurrency.service.Service;

public class SequentialStatistic extends Statistic {

	public void execute(Service service) {
		long startTimeMillis = System.currentTimeMillis();
		service.playSequential();
		this.timeMillis = System.currentTimeMillis() - startTimeMillis;
	}

}
