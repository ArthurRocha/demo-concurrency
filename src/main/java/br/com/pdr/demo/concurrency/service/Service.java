package br.com.pdr.demo.concurrency.service;

public abstract class Service {

	protected int sizeOfTheGame;

	public Service(int size) {
		this.sizeOfTheGame = size;
		createBigData();
	}

	/**
	 * Cria uma base de dados.
	 */
	public abstract void createBigData();

	/**
	 * Executa o processameto de todos os dados de modo sequencial.
	 */
	public abstract void playSequential();

	/**
	 * Executa o processameto de uma fatia de dados. Utilizada para
	 * processamento assincrono.
	 * 
	 * @param start
	 * @param end
	 */
	public abstract void playSliced(final int start, final int end);

	/**
	 * Executa o processameto de todos os dados de modo sequencial. Realiza uma
	 * operação assíncrona entre os processamentos.
	 */
	public abstract void playSequentialWithAsyncOperation();

	/**
	 * Executa o processameto de uma fatia de dados. Realiza uma operação
	 * assíncrona entre os processamentos. Utilizada para processamento
	 * assincrono.
	 */
	public abstract void playSlicedWithAsyncOperation(final int start,
			final int end);

}
