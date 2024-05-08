import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {

    public void serialSort(int[] array){
        int begin = 0;
        int end = array.length-1;
        
        long tempoInicial = System.currentTimeMillis();

        int[] sortedArray = array.clone();

        mergeSort(sortedArray, begin, end);

        long tempoFinal = System.currentTimeMillis();
		long tempoExecucao = tempoFinal - tempoInicial;

        String csvFile = "sorting.csv";
        try (FileWriter writer = new FileWriter(csvFile, true)) { 
            File file = new File(csvFile);
            if (file.length() == 0) {
                writer.append("sort,size,exectime,type,coresize\n"); // Headers
            }
            writer.append( "merge" + "," + sortedArray.length + "," + tempoExecucao + "," + "serial" + "," + 1 +  "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(sortedArray));
        
    }

    private static class SortThreads extends Thread{
        SortThreads(int[] array, int begin, int end){
            super(()->{
                MergeSort.mergeSort(array, begin, end);
            });
            this.start();
        }
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
            int beg = i;
            int remain = (length)-i;
            int end = remain < maxlim? i+(remain-1): i+(maxlim-1);  
            final SortThreads t = new SortThreads(sortedArray, beg, end);
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
            writer.append( "merge" + "," + sortedArray.length + "," + tempoExecucao + "," + "paralel" + "," + numThreads +  "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(sortedArray));
    }

    public static void mergeSort(int[] array, int begin, int end){
        if (begin<end){
            int mid = (begin+end)/2;
            mergeSort(array, begin, mid);
            mergeSort(array, mid+1, end);
            merge(array, begin, mid, end);
        }
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
