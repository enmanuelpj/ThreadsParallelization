package paralelo;

import base.Matrix;

public class MultiplierThread extends Thread
{

    private double[] rowVector;
    private double[] result;
    private Matrix matrizB;
    



	/**
     * Constructor parametrizado
     *
     * @param rowVector
     * @param columnVector
     */
    public MultiplierThread(double[] rowVector, Matrix matrizB, double[] result)
    {
        this.setRowVector(rowVector);
        this.setMatrizB(matrizB);
        this.setResult(result);
    }
    
    public Matrix getMatrizB() {
		return matrizB;
	}

	public void setMatrizB(Matrix matrizB) {
		this.matrizB = matrizB;
	}

    public double[] getRowVector() {
        return rowVector;
    }

    public void setRowVector(double[] rowVector) {
        this.rowVector = rowVector;
    }


    public double[] getResult() {
        return result;
    }

    public void setResult(double[] result) {
        this.result = result;
    }

    /**
     *
     */
    public void run()
    {
    	double suma;
    	for(int j = 0; j < result.length; j++)
        {
    		suma = 0;
            for(int k = 0; k < matrizB.getColumnDimension(); k++)
            {
                suma += this.rowVector[k] * matrizB.getEntry(k, j);
            }
            result[j] = suma;

        }
    }


}
