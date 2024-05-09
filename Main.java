import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;
// merge sort, bubble sort, one extra sort
public class Main {
    public static void main(String[] args) {
        int[] arr5_1= {7, 2, 9, 4, 1, 6, 10, 3, 8, 5};
        int[] arr5_2= {3, 8, 5, 1, 9, 4, 10, 7, 6, 2};
        int[] arr5_3= {6, 2, 10, 8, 4, 9, 1, 3, 5, 7};

        int[] arr10_1= {54, 23, 87, 12, 65, 31, 78, 42, 9, 56};
        int[] arr10_2= {91, 18, 37, 69, 82, 45, 27, 63, 10, 74};
        int[] arr10_3= {36, 59, 14, 72, 28, 83, 47, 5, 68, 20};

        int[] arr30_1= {42, 18, 56, 93, 27, 64, 38, 75, 12, 87, 51, 29, 60, 83, 47, 20, 71, 35, 69, 14, 98, 23, 45, 78, 6, 32, 89, 55, 10, 67};
        int[] arr30_2= {16, 72, 38, 54, 89, 27, 43, 61, 35, 78, 49, 20, 96, 14, 68, 30, 83, 57, 22, 41, 75, 9, 63, 47, 11, 86, 58, 32, 70, 25};
        int[] arr30_3= {37, 52, 69, 84, 23, 45, 71, 18, 63, 29, 92, 16, 81, 57, 39, 78, 14, 66, 31, 47, 75, 26, 58, 93, 10, 84, 19, 53, 38, 74};

        Random random = new Random();

        int[] randomArray1000_1 = new int[1000];
        int[] randomArray1000_2 = new int[1000];
        for (int i = 0; i < randomArray1000_1.length; i++) {
            randomArray1000_1[i] = random.nextInt(1000) + 1; // Generates a random number between 1 and 1000
        }
        for (int i = 0; i < randomArray1000_2.length; i++) {
            randomArray1000_2[i] = random.nextInt(1000) + 1; // Generates a random number between 1 and 1000
        }
        
        MergeSort mergeSort = new MergeSort();
       
        QuickSort quickSort = new QuickSort();
        BubbleSort bubbleSort = new BubbleSort();
        InsertionSort insertionSort = new InsertionSort();
    /*
        bubbleSort.serialBubble(randomArray1000_1);
        bubbleSort.paralelBubble(randomArray1000_1, 5);
        bubbleSort.paralelBubble(randomArray1000_1, 5);
        bubbleSort.paralelBubble(randomArray1000_1, 10);
        bubbleSort.paralelBubble(randomArray1000_1, 0);
 */
        quickSort.serialSort(randomArray1000_1);
        quickSort.paralelSort(randomArray1000_1, 5);
        quickSort.paralelSort(randomArray1000_1, 5);
        quickSort.paralelSort(randomArray1000_1, 10);
        quickSort.paralelSort(randomArray1000_1, 0);
/*
        insertionSort.serialSort(randomArray1000_1);
        insertionSort.paralelSort(randomArray1000_1, 5);
        insertionSort.paralelSort(randomArray1000_1, 5);
        insertionSort.paralelSort(randomArray1000_1, 10);
        insertionSort.paralelSort(randomArray1000_1, 0);
    */  
        // You can call serialPrimo with different sizes as needed.
    }

    public static void serialPrimo(int size) {
        long tempoInicial = System.currentTimeMillis();

        int inicio = 2;
        int fim = size;

        boolean[] primos = new boolean[fim + 1];
        Arrays.fill(primos, true);

        for (int i = 2; i * i <= fim; i++) {
            if (primos[i]) {
                for (int j = i * i; j <= fim; j += i) {
                    primos[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = inicio; i <= fim; i++) {
            if (primos[i]) {
                count++;
            }
        }

        long tempoFinal = System.currentTimeMillis();
        long tempoExecucao = tempoFinal - tempoInicial;

        // Writing to CSV
        String csvFile = "prime_numbers.csv";
        try (FileWriter writer = new FileWriter(csvFile, true)) { // Append mode
            File file = new File(csvFile);
            if (file.length() == 0) {
                writer.append("size,exectime,type,coresize\n"); // Headers
            }
            writer.append(size + "," + tempoExecucao + "," + "serial" + "," + 1 +  "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total prime numbers between " + inicio + " and " + fim + ": " + count);
        System.out.println("Tempo de execução: " + tempoExecucao + " milisegundos");
    }

	public static void paraleloPrimo(int size, int numCores){
		long tempoInicial = System.currentTimeMillis();

		int inicio = 1;
		int fim = size;

		int numThreads = numCores;

        if(numCores == 0){numThreads = Runtime.getRuntime().availableProcessors();}

		int chunkSize = (fim - inicio +1) / numThreads;
		List<Integer> result = new ArrayList<>();
		
		ExecutorService executor =  Executors.newFixedThreadPool(numThreads);
		for(int i = 0; i < numThreads; i++){
			int threadStart = inicio + i * chunkSize;
			int threadEnd = threadStart + chunkSize - 1;

			executor.execute(() -> achaPrimos(threadStart, threadEnd, result));
		}

		executor.shutdown();
		while(!executor.isTerminated()){

		}
		System.out.println("Prime numbers between " + inicio + " and " + fim + ":");
		System.out.println(result);

		long tempoFinal = System.currentTimeMillis();
		long tempoExecucao = tempoFinal - tempoInicial;

        // Writing to CSV
        String csvFile = "prime_numbers.csv";
        try (FileWriter writer = new FileWriter(csvFile, true)) { // Append mode
            File file = new File(csvFile);
            if (file.length() == 0) {
                writer.append("size,exectime,type,coresize\n"); // Headers
            }
            writer.append(size + "," + tempoExecucao + "," + "paralel" + "," + numThreads + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

		System.out.println("\nExecution time: " + tempoExecucao + " miliseconds");

	}

	private static void achaPrimos(int start, int end, List<Integer> result) {
		for(int num = start; num <= end; num++){
			if(ehPrimo(num)){
				result.add(num);
			}
		}
	}

	private static boolean ehPrimo(int num){
		if(num < 2){
			return false;
		}
		for(int i = 2; i <= Math.sqrt(num); i++){
			if(num % i == 0){
				return false;
			}
		}
		return true;
	}


    
}


