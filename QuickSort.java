import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class QuickSort {
    public void serialSort(int[] array){
        int low = 0;
        int high = array.length - 1;

        long tempoInicial = System.currentTimeMillis();

        quickSort(array, low, high);

        long tempoFinal = System.currentTimeMillis();
		long tempoExecucao = tempoFinal - tempoInicial;

        String csvFile = "sorting.csv";
        try (FileWriter writer = new FileWriter(csvFile, true)) { // Append mode
            File file = new File(csvFile);
            if (file.length() == 0) {
                writer.append("sort,size,exectime,type,coresize\n"); // Headers
            }
            writer.append( "quick" + "," + array.length + "," + tempoExecucao + "," + "serial" + "," + 1 +  "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(array));
        
    }

    private static class SortThreads extends Thread{
        SortThreads(int[] array, int begin, int end){
            super(()->{
                QuickSort.quickSort(array, begin, end);
            });
            this.start();
        }
    }
    
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    public static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    public void paralelSort(int[] array, int numCores){
        long tempoInicial = System.currentTimeMillis();
        final int length = array.length;
        int[] sortedArray = array.clone();

        int numThreads = numCores;

        if(numCores == 0){numThreads = Runtime.getRuntime().availableProcessors();}
       
        boolean exact = length%numThreads == 0;
        int maxlim = exact? length/numThreads: length/(numThreads-1);
        maxlim = maxlim < numThreads? numThreads : maxlim;
        final ArrayList<SortThreads> threads = new ArrayList<>();
    
        for(int i=0; i < length; i+=maxlim){
            int low = i;
            int remain = (length)-i;
            int high = remain < maxlim? i+(remain-1): i+(maxlim-1);  
            final SortThreads t = new SortThreads(sortedArray, low, high);
            threads.add(t);
        }
        for(Thread t: threads){
            try{
              
                t.join();
            } catch(InterruptedException ignored){}
        }

        for(int i=0; i < length; i+=maxlim){
            int mid = i == 0? 0 : i-1;
            int remain = (length)-i;
            int end = remain < maxlim? i+(remain-1): i+(maxlim-1); 
            merge(sortedArray, 0, mid, end);
        }

        long tempoFinal = System.currentTimeMillis();
		long tempoExecucao = tempoFinal - tempoInicial;

        String csvFile = "sorting.csv";
        try (FileWriter writer = new FileWriter(csvFile, true)) { // Append mode
            File file = new File(csvFile);
            if (file.length() == 0) {
                writer.append("sort,size,exectime,type,coresize\n"); // Headers
            }
            writer.append( "quick" + "," + sortedArray.length + "," + tempoExecucao + "," + "paralel" + "," + numThreads +  "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(sortedArray));
    }

    public static void merge(int[] array, int begin, int mid, int end){
        int[] temp = new int[(end-begin)+1];
         
        int i = begin, j = mid+1; 
        int k = 0;
 
        while(i<=mid && j<=end){
            if (array[i] <= array[j]){
                temp[k] = array[i];
                i+=1;
            }else{
                temp[k] = array[j];
                j+=1;
            }
            k+=1;
        }
 
        while(i<=mid){
            temp[k] = array[i];
            i+=1; k+=1;
        } 
         
        while(j<=end){
            temp[k] = array[j];
            j+=1; k+=1;
        }
 
        for(i=begin, k=0; i<=end; i++,k++){
            array[i] = temp[k];
        }
    }

    
}
