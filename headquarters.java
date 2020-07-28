import java.util.ArrayList;
import java.util.Arrays;

// In this project I use the official porter stemmer, https://tartarus.org/martin/PorterStemmer/java.txt
// I did not create this stemmer.
public class headquarters {
    
    public static ArrayList<String> stopWords = preprocessing.stopWords();
    public static String c101 = "data/c1/article01.txt";
    public static String c102 = "data/c1/article02.txt";
    public static String c103 = "data/c1/article03.txt";
    public static String c104 = "data/c1/article04.txt";
    public static String c105 = "data/c1/article05.txt";
    public static String c106 = "data/c1/article06.txt";
    public static String c107 = "data/c1/article07.txt";
    public static String c108 = "data/c1/article08.txt";
    public static String c401 = "data/c4/article01.txt";
    public static String c402 = "data/c4/article02.txt";
    public static String c403 = "data/c4/article03.txt";
    public static String c404 = "data/c4/article04.txt";
    public static String c405 = "data/c4/article05.txt";
    public static String c406 = "data/c4/article06.txt";
    public static String c407 = "data/c4/article07.txt";
    public static String c408 = "data/c4/article08.txt";
    public static String c701 = "data/c7/article01.txt";
    public static String c702 = "data/c7/article02.txt";
    public static String c703 = "data/c7/article03.txt";
    public static String c704 = "data/c7/article04.txt";
    public static String c705 = "data/c7/article05.txt";
    public static String c706 = "data/c7/article06.txt";
    public static String c707 = "data/c7/article07.txt";
    public static String c708 = "data/c7/article08.txt";
    public static String fileNames[] = {c101,c102,c103,c104,c105,c106,c107,c108,c401,c402,c403,c404,c405,c406,c407,c408,c701,c702,c703,c704,c705,c706,c707,c708};
    public static ArrayList<String> lc101 = new ArrayList<String>();
    public static ArrayList<String> lc102 = new ArrayList<String>();
    public static ArrayList<String> lc103 = new ArrayList<String>();
    public static ArrayList<String> lc104 = new ArrayList<String>();
    public static ArrayList<String> lc105 = new ArrayList<String>();
    public static ArrayList<String> lc106 = new ArrayList<String>();
    public static ArrayList<String> lc107 = new ArrayList<String>();
    public static ArrayList<String> lc108 = new ArrayList<String>();
    public static ArrayList<String> lc401 = new ArrayList<String>();
    public static ArrayList<String> lc402 = new ArrayList<String>();
    public static ArrayList<String> lc403 = new ArrayList<String>();
    public static ArrayList<String> lc404 = new ArrayList<String>();
    public static ArrayList<String> lc405 = new ArrayList<String>();
    public static ArrayList<String> lc406 = new ArrayList<String>();
    public static ArrayList<String> lc407 = new ArrayList<String>();
    public static ArrayList<String> lc408 = new ArrayList<String>();
    public static ArrayList<String> lc701 = new ArrayList<String>();
    public static ArrayList<String> lc702 = new ArrayList<String>();
    public static ArrayList<String> lc703 = new ArrayList<String>();
    public static ArrayList<String> lc704 = new ArrayList<String>();
    public static ArrayList<String> lc705 = new ArrayList<String>();
    public static ArrayList<String> lc706 = new ArrayList<String>();
    public static ArrayList<String> lc707 = new ArrayList<String>();
    public static ArrayList<String> lc708 = new ArrayList<String>();
    public static ArrayList<ArrayList<String>> fileArrays = new ArrayList<>(Arrays.asList(lc101,lc102,lc103,lc104,lc105,lc106,lc107,lc108,lc401,lc402,lc403,lc404,lc405,lc406,lc407,lc408,lc701,lc702,lc703,lc704,lc705,lc706,lc707,lc708));

    
    public static ArrayList<String> wordList = new ArrayList<String>();
    public static ArrayList<Integer> countList = new ArrayList<Integer>();
    public static ArrayList<Integer> indexes = new ArrayList<Integer>();
    
        
    public static ArrayList<String> newWords = new ArrayList<String>();
    public static ArrayList<ArrayList<String>> wordMatrix = new ArrayList<ArrayList<String>>();

    public static ArrayList<ArrayList<String>> getFileArray(){
        return fileArrays;
    }

    // sameClusters: function to check if the *new* cluster generated by the clustering function is the same as the old one. 
    // Basically, the program runs clustering as many times as it needs to until it gets the same clusters twice in a row, eliminating
    // weird outliers where vectors are more similar to other means.

    public static boolean sameClusters(ArrayList<ArrayList<Integer>> clusters1, ArrayList<ArrayList<Integer>> clusters2, int k){
        int temp = clusters1.get(0).get(0);
        int indexOfCluster1 = 0;
        for(int i = 0; i < k; i++){
            if(clusters2.get(i).contains(temp)){
                indexOfCluster1 = i;
            }
        }
        boolean first = clusters1.get(0).equals(clusters2.get(indexOfCluster1));

        int temp1 = clusters1.get(1).get(0);
        int indexOfCluster2 = 0;
        for(int i = 0; i < k; i++){
            if(clusters2.get(i).contains(temp1)){
                indexOfCluster2 = i;
            }
        }
        boolean second = clusters1.get(1).equals(clusters2.get(indexOfCluster2));
        return first & second;
    }
    
    public static void main(String[] args) {
        for(int i = 0; i < fileNames.length; i++){
            preprocessing.dataPreparation(i);
        }
        
        double[][] myMatrix = matrix.generateMatrix();

        //analysis.pcaMachine(myMatrix);
        //matrix.printMyMatrix(myMatrix);

        // Testing cosSim functionality
        /*
        System.out.println("Cos sim analysis");
        double dist1 = clustering.cosSim(myMatrix[0], myMatrix[12]);
        double dist2 = clustering.cosSim(myMatrix[0], myMatrix[20]);
        double dist3 = clustering.cosSim(myMatrix[12], myMatrix[11]);
        double dist4 = clustering.cosSim(myMatrix[1], myMatrix[2]);
        double dist5 = clustering.cosSim(myMatrix[0], myMatrix[2]);
       
        System.out.println(dist1);
        System.out.println(dist2);
        System.out.println(dist3);
        System.out.println(dist4);
        System.out.println(dist5);
        */
        
        
        //System.out.println("Cluster analysis");


        ArrayList<ArrayList<Integer>> clusters = clustering.masterCluster(myMatrix, 3);

        boolean flag = true;
        int counter = 0;
        
        while(flag){
            counter++;
            
            ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
            for(ArrayList<Integer> cluster:clusters){
                temp.add(new ArrayList<Integer>());
            }
            int realIndex = 0;
            for(ArrayList<Integer> cluster:clusters){
                    for(int index:cluster){
                        int tempp = index - 1;
                        tempp++;
                        temp.get(realIndex).add(tempp);
                    }
                    realIndex++;
            }

            clusters = clustering.masterCluster(myMatrix, 3);
            if(sameClusters(clusters, temp, 3)){
                //System.out.println(clusters);
                //System.out.println(newClusters);
                //System.out.println("Counter");
                //System.out.println(counter);
                flag = false;
            } 
        }
        analysis.report(clusters);
        /*
        int i = 0;
        for(ArrayList<Integer> cluster:clusters){
            System.out.println(i);
            for(int index:cluster){
                System.out.print(index);
                System.out.print(",");
            }
            i++;
            System.out.println(" ");
        }
        */
        //System.out.println("Cluster mean analysis");
        //clustering.printClusterMean(myMatrix, clusters);
        
    }  
}