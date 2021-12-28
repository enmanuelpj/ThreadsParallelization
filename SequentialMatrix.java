package secuencial;

import base.Matrix;

public class SequentialMatrix extends Matrix
{


    public SequentialMatrix(int rDimension, int cDimension)
    {
        super(rDimension, cDimension);
        // TODO Auto-generated constructor stub
    }
    
    public SequentialMatrix(int rDimension, int cDimension, double[][] mData)
    {
        this.rowDimension = rDimension;
        this.columnDimension = cDimension;
        this.matrixData = mData;
    }
    

    /**
     * Codificación de producto de matrices
     * */

    @Override
    public double[][] multiply(Matrix secondMatrix)
    {
        double[][] matrizProducto = new double[rowDimension][rowDimension];
        double tiempo1, tiempo2, total;

        tiempo1 = System.currentTimeMillis();
        for(int i = 0; i < rowDimension; i++)
        {
            for(int j = 0; j < rowDimension; j++)
            {
                double suma = 0;

                for(int k = 0; k < rowDimension; k++)
                {
                    suma += this.matrixData[i][k] * secondMatrix.getEntry(k, j);
                }
                matrizProducto[i][j] = suma;

            }

        }
        tiempo2 = System.currentTimeMillis();
        total = tiempo2 - tiempo1;
        System.out.println("Producto de la Matriz Secuencial " + rowDimension + "x" + rowDimension + ": " + total + " millis");

        return matrizProducto;
    }
    
    public static void ImprimirMatriz(double[][] matriz){
        for (int i = 0; i < matriz.length; i++){
            for(int j = 0 ; j < matriz[i].length; j++){
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
        }
    }



    /**Reducción Gaussiana*/
    @Override
    public double[][] inversa()
    {
        double tiempo1, tiempo2, total;

        tiempo1 = System.currentTimeMillis();
        int dimension = this.getColumnDimension();
        double matrizInversa [][] = new double [dimension][dimension];
        double matriz [][] = new double [dimension][dimension];
        
        for(int i=0;i<dimension;i++) {
			for(int j=0;j<dimension;j++) {
				matriz[i][j] = this.getEntry(i, j);
			}
		}

        /**Llenamos la matriz de identidad*/
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                matrizInversa[i][j] = 0;

        for (int i = 0; i < dimension; i++)
            matrizInversa[i][i] = 1;


        for (int k = 0; k < dimension; k++)
        {
            for (int i = k; i < dimension; i++)
            {
                double valInv = 1.0 / matriz[i][k]; /**Hacer el elemento pivote igual a 1*/
                for (int j = k; j < dimension; j++)
                    matriz[i][j] *= valInv;  /**Convirtiendo el elemento y haciendo el cambio en toda la fila de la matriz*/
                for (int j = 0; j < dimension; j++)
                    matrizInversa[i][j] *= valInv; /**Efectuando el cambio igualmente en la fila de la matriz inversa*/
            }
            for (int i = k + 1; i < dimension; i++)
            {
                for (int j = k; j < dimension; j++)
                    matriz[i][j] -= matriz[k][j]; /**Resta con la fila pivote*/
                for (int j = 0; j < dimension; j++)
                    matrizInversa[i][j] -= matrizInversa[k][j]; /**Resta realizada tambien en la matriz inversa*/
            }
        }

        for (int i = dimension - 2; i >= 0; i--)
        {
            for (int j = dimension - 1; j > i; j--)
            {
                for (int k = 0; k < dimension; k++)
                    matrizInversa[i][k] -= matriz[i][j] * matrizInversa[j][k];
                for (int k = 0; k < dimension; k++)
                    matriz[i][k] -= matriz[i][j] * matriz[j][k]; /**Resta con la para terminar de hacer 0 a la parte inferior del elemento pivote en la matriz*/
            }
        }
        tiempo2 = System.currentTimeMillis();
        total = tiempo2 - tiempo1;
        System.out.println("Inversa de la Secuencial " + rowDimension + "x" + rowDimension + ": " + total + " millis");

        return matrizInversa;
    }
}
