package br.com.pdr.demo.concurrency.statistic;

import br.com.pdr.demo.concurrency.service.HighProccessService;
import br.com.pdr.demo.concurrency.service.SmallProccessService;

public class SequentialStatistic extends Statistic {

	@Override
	public void executeHighProccess(HighProccessService service)
			throws Exception {
		service.playSequential();
	}
	
	@Override
	public void executeSmallProccess(SmallProccessService service)
			throws Exception {
		service.playSequential();
	}
	
	@Override
	public void init() {
		// NOP
	}

	@Override
	public void destroy() {
		// NOP
	}

}
