package pruebaJUnit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;


import paralelo.ParallelMatrix;
import secuencial.SequentialMatrix;

public class matricesTest {
	
	private SequentialMatrix secuencial1;
	private SequentialMatrix secuencial2;
	private ParallelMatrix paralela1;
	private ParallelMatrix paralela2;
	double resultadoSec[][];
	double resultadoPar[][];
	List<Integer> dimensiones = new ArrayList<>(Arrays.asList(10, 30, 60));
	Collection<DynamicTest> dynamicTests = new ArrayList<>();
	
	@TestFactory
	Collection<DynamicTest> testProducto()
	{
		for(int i = 0; i < dimensiones.size(); i++)
		{
			int dimension = dimensiones.get(i);
			secuencial1 = new SequentialMatrix(dimension, dimension);
			secuencial2 = new SequentialMatrix(dimension, dimension);
			paralela1 = new ParallelMatrix(dimension, dimension, secuencial1.getMatrixData());
			paralela2 = new ParallelMatrix(dimension, dimension, secuencial2.getMatrixData());
			
			//Expected
			resultadoSec = secuencial1.multiply(secuencial2);
			
			//Test
			resultadoPar = paralela1.multiply(paralela2);
			
			Executable exec;
			
			for(int j = 0; j < resultadoPar[0].length; j++)
			{
				for(int k = 0; k < resultadoPar[0].length; k++)
				{
					double valor1 = resultadoSec[j][k];
					double  valor2 = resultadoPar[j][k];
					exec = () -> assertEquals(valor1, valor2);
					
					String pruebaPos = "Prueba en la posición " + j + ", " + k;
					
					DynamicTest pruebaDinamica = DynamicTest.dynamicTest(pruebaPos, exec);
					
					dynamicTests.add(pruebaDinamica);
				}
			}
			
		}
		return dynamicTests;
	}
	
	@TestFactory
	Collection<DynamicTest> testInversa()
	{
		for(int i = 0; i < dimensiones.size(); i++)
		{
			int dimension = dimensiones.get(i);
			secuencial1 = new SequentialMatrix(dimension, dimension);
			secuencial2 = new SequentialMatrix(dimension, dimension);
			paralela1 = new ParallelMatrix(dimension, dimension, secuencial1.getMatrixData());
			paralela2 = new ParallelMatrix(dimension, dimension, secuencial2.getMatrixData());
			
			//Expected
			resultadoSec = secuencial1.inversa();
			
			//Test
			resultadoPar = paralela1.inversa();
			
			for(int j = 0; j < resultadoPar[0].length; j++)
			{
				for(int k = 0; k < resultadoPar[0].length; k++)
				{
					double valor1 = resultadoSec[j][k];
					double valor2 = resultadoPar[j][k];
					Executable exec = () -> assertEquals(valor1, valor2);
					
					String pruebaPos = "Prueba en la Posicion " + j + "," + k;
					
					DynamicTest dTest = DynamicTest.dynamicTest(pruebaPos, exec);
					
				    dynamicTests.add(dTest);
				}
			}
		}
		return dynamicTests;
	}

}
