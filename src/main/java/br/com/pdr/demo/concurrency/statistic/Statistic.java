package br.com.pdr.demo.concurrency.statistic;

import br.com.pdr.demo.concurrency.ConcurrencyApplication;
import br.com.pdr.demo.concurrency.service.HighProccessService;
import br.com.pdr.demo.concurrency.service.SmallProccessService;

public abstract class Statistic {

	protected long timeMillisHighProcess;
	protected long timeMillisSmallProcess;

	// TODO MORE STATISTICS -- MEMORY / PROCESSOR...

	/**
	 * Executa processamentos e coleta suas estatisticas.
	 * 
	 * @throws Exception
	 */
	public void execute(int size) throws Exception {
		init();
		if (ConcurrencyApplication.HIGH_PROCCESS_ON) {
			System.gc();
			HighProccessService highProccessService = new HighProccessService(size);
			long startTimeMillis = System.currentTimeMillis();
			executeHighProccess(highProccessService);
			this.timeMillisHighProcess = System.currentTimeMillis() - startTimeMillis;
		}
		if (ConcurrencyApplication.SMALL_PROCCESS_ON) {
			System.gc();
			SmallProccessService smallProccessService = new SmallProccessService(size);
			long startTimeMillis = System.currentTimeMillis();
			executeSmallProccess(smallProccessService);
			this.timeMillisSmallProcess = System.currentTimeMillis() - startTimeMillis;
		}
		destroy();
	}

	public abstract void executeHighProccess(HighProccessService service) throws Exception;
	
	public abstract void executeSmallProccess(SmallProccessService service) throws Exception;
	
	public abstract void init();
	
	public abstract void destroy();

	/**
	 * Apresenta as informações coletadas durante o processamento.
	 */
	public void printStatistic() {
		System.out.println(this.getClass().getSimpleName() 
				+ "\t HighProcess\t" + this.timeMillisHighProcess + " ms"
				+ "\t SmallProcess\t" + this.timeMillisSmallProcess + " ms");
	}
}
