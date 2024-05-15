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

        int[] randomArray1000_1 = new int[10000];
        for (int i = 0; i < randomArray1000_1.length; i++) {
            randomArray1000_1[i] = random.nextInt(1000) + 1; // Generates a random number between 1 and 1000
        }
        int[] selectArray = randomArray1000_1;
       
        /*
        saveMerge(selectArray, 1);
        saveMerge(selectArray, 5);
        saveMerge(selectArray, 0);

        saveBublle(selectArray, 1);
        saveBublle(selectArray, 5);
        saveBublle(selectArray, 0);

        saveInsertion(selectArray, 1);
        saveInsertion(selectArray, 5);
        saveInsertion(selectArray, 0);
*/
        saveQuick(selectArray, 1);
        saveQuick(selectArray, 5);
        saveQuick(selectArray, 0);
     
        callChart();
      
}
    public static void saveMerge(int[] selectArray, int numThread){
        MergeSort mergeSort = new MergeSort();

        if(numThread == 1){
            recordData("merge", selectArray.length , mergeSort.serialSort(selectArray), "serial", numThread);
            recordData("merge", selectArray.length , mergeSort.serialSort(selectArray), "serial", numThread);
            recordData("merge", selectArray.length , mergeSort.serialSort(selectArray), "serial", numThread);
            recordData("merge", selectArray.length , mergeSort.serialSort(selectArray), "serial", numThread);
            recordData("merge", selectArray.length , mergeSort.serialSort(selectArray), "serial", numThread);
        }else{
            recordData("merge", selectArray.length , mergeSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("merge", selectArray.length , mergeSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("merge", selectArray.length , mergeSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("merge", selectArray.length , mergeSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("merge", selectArray.length , mergeSort.paralelSort(selectArray,5), "paralel", numThread);
        } 
        
    }

    public static void saveBublle(int[] selectArray, int numThread){
        BubbleSort bubbleSort = new BubbleSort();

        if(numThread == 1){
            recordData("bubble", selectArray.length , bubbleSort.serialSort(selectArray), "serial", numThread);
            recordData("bubble", selectArray.length , bubbleSort.serialSort(selectArray), "serial", numThread);
            recordData("bubble", selectArray.length , bubbleSort.serialSort(selectArray), "serial", numThread);
            recordData("bubble", selectArray.length , bubbleSort.serialSort(selectArray), "serial", numThread);
            recordData("bubble", selectArray.length , bubbleSort.serialSort(selectArray), "serial", numThread);
        }else{
            recordData("bubble", selectArray.length , bubbleSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("bubble", selectArray.length , bubbleSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("bubble", selectArray.length , bubbleSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("bubble", selectArray.length , bubbleSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("bubble", selectArray.length , bubbleSort.paralelSort(selectArray,5), "paralel", numThread);
        } 
        
    }

    public static void saveQuick(int[] selectArray, int numThread){
        QuickSort quickSort = new QuickSort();

        if(numThread == 1){
            recordData("quick", selectArray.length , quickSort.serialSort(selectArray), "serial", numThread);
            recordData("quick", selectArray.length , quickSort.serialSort(selectArray), "serial", numThread);
            recordData("quick", selectArray.length , quickSort.serialSort(selectArray), "serial", numThread);
            recordData("quick", selectArray.length , quickSort.serialSort(selectArray), "serial", numThread);
            recordData("quick", selectArray.length , quickSort.serialSort(selectArray), "serial", numThread);
        }else{
            recordData("quick", selectArray.length , quickSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("quick", selectArray.length , quickSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("quick", selectArray.length , quickSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("quick", selectArray.length , quickSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("quick", selectArray.length , quickSort.paralelSort(selectArray,5), "paralel", numThread);
        } 
        
    }

    public static void saveInsertion(int[] selectArray, int numThread){
        InsertionSort insertionSort = new InsertionSort();

        if(numThread == 1){
            recordData("insertion", selectArray.length , insertionSort.serialSort(selectArray), "serial", numThread);
            recordData("insertion", selectArray.length , insertionSort.serialSort(selectArray), "serial", numThread);
            recordData("insertion", selectArray.length , insertionSort.serialSort(selectArray), "serial", numThread);
            recordData("insertion", selectArray.length , insertionSort.serialSort(selectArray), "serial", numThread);
            recordData("insertion", selectArray.length , insertionSort.serialSort(selectArray), "serial", numThread);
        }else{
            recordData("insertion", selectArray.length , insertionSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("insertion", selectArray.length , insertionSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("insertion", selectArray.length , insertionSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("insertion", selectArray.length , insertionSort.paralelSort(selectArray,5), "paralel", numThread);
            recordData("insertion", selectArray.length , insertionSort.paralelSort(selectArray,5), "paralel", numThread);
        } 
        
    }

    public static void callChart(){
        try {
            String command = "python chart.py";
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            
            // Print any output from the command
            // You can customize this part as per your requirements
            java.util.Scanner scanner = new java.util.Scanner(process.getInputStream());
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
            java.util.Scanner errorScanner = new java.util.Scanner(process.getErrorStream());
            while (errorScanner.hasNextLine()) {
                System.out.println(errorScanner.nextLine());
            }
            errorScanner.close();
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void recordData(String sort, int size, long tempoExecucao, String type, int numThreads){
        String csvFile = "sorting.csv";
        try (FileWriter writer = new FileWriter(csvFile, true)) { // Append mode
            File file = new File(csvFile);
            if (file.length() == 0) {
                writer.append("sort,size,exectime,type,coresize,title\n"); // Headers
            }
            writer.append( sort + "," + size + "," + tempoExecucao + "," + type + "," + numThreads + "," + type + "_" + numThreads + "_cores" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
}


