/**
 *
 */
package paralelo;

import java.util.ArrayList;

import java.util.List;

import base.Matrix;

import paralelo.MultiplierThread;

/**
 * @author Manuel
 *
 */
public class ParallelMatrix extends Matrix {

    /* (non-Javadoc)
     * @see base.Matrix#multiply(base.Matrix)
     */

    /**
     * Constructor parametrizado
     *
     * @param rDimension
     * @param cDimension
     */
    public ParallelMatrix(int rDimension, int cDimension)
    {
        super(rDimension, cDimension);
    }
    
    public ParallelMatrix(int rDimension, int cDimension, double[][] mData)
    {
        this.rowDimension = rDimension;
        this.columnDimension = cDimension;
        this.matrixData = mData;
    }
    
    private static void esperarHilos(List<Thread> hilos)
    {
    	for(Thread hilo : hilos)
    	{
    		try
    		{
    			hilo.join();
    		}
    		catch(InterruptedException e)
    		{
    			e.printStackTrace();
    		}
    	}
    	hilos.clear();
    }

    @Override
    public double[][] multiply(Matrix secondMatrix) {
    	double[][] matrizProducto = new double[rowDimension][rowDimension];
        double tiempo1, tiempo2, total;
        List<Thread> lista_hilos = new ArrayList<Thread>();

        tiempo1 = System.currentTimeMillis();
        for(int i = 0; i < this.getRowDimension(); i++)
        {
        	MultiplierThread hilo = new MultiplierThread(this.getRowVector(i), secondMatrix, matrizProducto[i]);
        	lista_hilos.add(hilo);
        	hilo.start();
        	if(lista_hilos.size()%rowDimension == 0)
        	{
        		esperarHilos(lista_hilos);
        	}
        }
        tiempo2 = System.currentTimeMillis();
        total = tiempo2 - tiempo1;
        System.out.println("Producto de la Matriz Paralela " + rowDimension + "x" + rowDimension + ": " + total + " millis");

        return matrizProducto;
    }

    @Override
    public double[][] inversa() 
    {
    	double tiempo1, tiempo2, total;
    	
        tiempo1 = System.currentTimeMillis();
        int dimension = this.getColumnDimension();
        double matrizInversa [][] = new double [dimension][dimension];
        double matriz [][] = new double [dimension][dimension];

        for(int i=0;i<dimension;i++) 
        {
			for(int j=0;j<dimension;j++) 
			{
				matriz[i][j] = this.getEntry(i, j);
			}
		}
        
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                matrizInversa[i][j] = 0;

        for (int i = 0; i < dimension; i++)
            matrizInversa[i][i] = 1;
        
        
        InverseThread hiloInversa = new InverseThread(matriz, matrizInversa, dimension, this);
        hiloInversa.start();
        try 
        {
        	hiloInversa.join();
        	
        }
        catch(InterruptedException e)
        {
        	e.printStackTrace();
        }
        
       for (int i = dimension - 2; i >= 0; i--)
        {
            for (int j = dimension - 1; j > i; j--)
            {
                for (int k = 0; k < dimension; k++)
                    matrizInversa[i][k] -= matriz[i][j] * matrizInversa[j][k];
                for (int k = 0; k < dimension; k++)
                    matriz[i][k] -= matriz[i][j] * matriz[j][k]; 
            }
        }
        tiempo2 = System.currentTimeMillis();
        total = tiempo2 - tiempo1;
        System.out.println("Inversa de la Paralela " + rowDimension + "x" + rowDimension + ": " + total + " millis");
        
        return matrizInversa;
        
    }
    
    
    public synchronized void calculosFila(int dimension, double[][] matriz, double[][] inversa)
    {
    	for (int k = 0; k < dimension; k++)
        {
            for (int i = k; i < dimension; i++)
            {
                double valInv = 1.0 / matriz[i][k]; 
                for (int j = k; j < dimension; j++)
                    matriz[i][j] *= valInv;  
                for (int j = 0; j < dimension; j++)
                    inversa[i][j] *= valInv;
            }
            for (int i = k + 1; i < dimension; i++)
            {
                for (int j = k; j < dimension; j++)
                    matriz[i][j] -= matriz[k][j]; 
                for (int j = 0; j < dimension; j++)
                    inversa[i][j] -= inversa[k][j]; 
            }
        }
    }
    
    public static void ImprimirMatriz(double[][] matriz)
    {
        for (int i = 0; i < matriz.length; i++){
            for(int j = 0 ; j < matriz[i].length; j++){
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }
    
}
