import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BubbleSort {
    
    long serialSort(int[] size){
        long tempoInicial = System.currentTimeMillis();
        int[] sortedArray = size.clone();
        int length = sortedArray.length;

        for (int i = 0; i < length-1; i++){
            for(int j = 0; j < length-i-1; j++){
                if(sortedArray[j] > sortedArray[j+1]){
                    int temp = sortedArray[j];
                    sortedArray[j] = sortedArray[j+1];
                    sortedArray[j+1] = temp;
                }
            }
        }

        long tempoFinal = System.currentTimeMillis();
		long tempoExecucao = tempoFinal - tempoInicial;

        return tempoExecucao;

        //System.out.println("SORTED SERIAL BUBBLE: " + Arrays.toString(sortedArray));
    }

    public long paralelSort(int[] size, int numCores){
        long tempoInicial = System.currentTimeMillis();
        int[] sortedArray = size.clone();
        int length = sortedArray.length;

        int numThreads = numCores;

        if(numCores == 0){numThreads = Runtime.getRuntime().availableProcessors();}

        int chunkSize = (length + numThreads - 1) / numThreads;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for(int i = 0; i < numThreads; i++){
			int threadStart = i * chunkSize;
            int threadEnd = Math.min((i + 1) * chunkSize - 1, length - 1);

			executor.execute(() -> BubbleSortTask(sortedArray, threadStart, threadEnd));
		}

        executor.shutdown();
		while(!executor.isTerminated()){

		}

       BubbleSortTask(sortedArray, 0, length -1);

        System.out.println("SORTED PARALEL BUBBLE: " + Arrays.toString(sortedArray));

        long tempoFinal = System.currentTimeMillis();
		long tempoExecucao = tempoFinal - tempoInicial;

        return tempoExecucao;
    }

    public static void BubbleSortTask(int[] arr, int start, int end) {
        for (int i = start; i <= end; i++) {
            for (int j = start; j <= end - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        
    }
}
