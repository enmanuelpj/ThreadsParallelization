package paralelo;

public class InverseThread  extends Thread
{
	private double[][] matriz;
	private double[][] inversa;
	private int dimension;
	private ParallelMatrix matrizP;
	
	public InverseThread( double[][] matriz, double[][] inversa, int dimension, ParallelMatrix matrizP)
	{
		this.matriz = matriz;
		this.inversa = inversa;
		this.dimension = dimension;
		this.matrizP = matrizP;
	}
	
	
	public void run()
	{
		matrizP.calculosFila(dimension, matriz, inversa);
	}
	
}
