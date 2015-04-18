package br.com.pdr.demo.concurrency.statistic;

public abstract class Statistic {

	protected long timeMillis;
	// TODO MORE STATISTICS --  MEMORY / PROCESSOR...
	
	/**
	 * Executa a devida estrategia de processamento e coleta suas estatisticas.
	 * 
	 * @throws Exception
	 */
	public abstract void execute() throws Exception;
	
	/**
	 * Apresenta as informações coletadas durante o processamento.
	 */
	public void printStatistic() {
		System.out.println(this.getClass().getSimpleName() + "\t\t" + this.timeMillis + " ms");
	}
}
