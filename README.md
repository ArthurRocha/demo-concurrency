# demo-concurrency

	CPU - Intel(R) Core(TM) i7-3770 CPU @ 3.40GHz

	Velocidade máxima:	3,40 GHz
	Sockets:	1
	Núcleos:	4
	Processadores lógicos:	8
	Virtualização:	Desabilitado
	Suporte ao Hyper-V:	Sim
	Cache L1:	256 KB
	Cache L2:	1,0 MB
	Cache L3:	8,0 MB
	
	Memória - 8,0 GB DDR3

	Velocidade:	1600 MHz
	Slots usados:	2 de 2
	Fator forma:	DIMM
	Reservada para hardware:	44,7 MB

##Results    (10000)
First:
SequentialStatistic             2474 ms
ForkJoinPoolStatistic           719 ms
Last:
            the game grew    13900
SequentialStatistic             4862 ms
ForkJoinPoolStatistic           2055 ms
	 the game grew	14000
 ='/  -> Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
