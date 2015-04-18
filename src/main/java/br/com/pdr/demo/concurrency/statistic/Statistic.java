package br.com.pdr.demo.concurrency.statistic;

public abstract class Statistic {

	protected long timeMillis;
	
	public abstract void execute() throws Exception;
	
	public void printStatistic() {
		System.out.println(this.getClass().getSimpleName() + "\t\t" + this.timeMillis + " ms");
	}
}
