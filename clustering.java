import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class clustering extends matrix {

    public static void clusterTest(int list[], int k) {
        double floored = list.length / k;
        int testVar = (int) Math.floor(floored);
        int total = list.length;
        double[] means = new double[k];
        ArrayList<ArrayList<Integer>> clusters = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<Integer>());
        }
        int clusterIndex = 0;
        while (total != 0) {
            for (int i = 0; i < testVar; i++) {
                clusters.get(clusterIndex).add(list[list.length - total]);
                total = total - 1;
            }
            while (clusterIndex == k - 1 & total != 0) {
                clusters.get(clusterIndex).add(list[list.length - total]);
                total = total - 1;
            }
            clusterIndex += 1;
        }

        findMeanTest(clusters, means);
        updateClusterTest(clusters, means, list);

    }

    private static void updateClusterTest(ArrayList<ArrayList<Integer>> clusters, double[] means, int[] l) {
        for (int i = 0; i < means.length; i++) {
            clusters.get(i).removeAll(clusters.get(i));
        }
        for (int i = 0; i < l.length; i++) {

        }
    }

    public static void findMeanTest(ArrayList<ArrayList<Integer>> clusters, double[] means) {
        for (ArrayList<Integer> cluster : clusters) {
            int sum = 0;
            for (Integer i : cluster) {
                sum += i;
            }
            means[clusters.indexOf(cluster)] = sum / cluster.size();
        }
    }

    public static double[][] minMax(double matrix[][]) {
        double[] min = new double[matrix[0].length];
        double[] max = new double[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {
            min[i] = 1.0;
            max[i] = 0.0;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] < min[j]) {
                    min[j] = matrix[i][j];
                }
                if (matrix[i][j] > max[j]) {
                    max[j] = matrix[i][j];
                }
            }
        }

        double[][] minMaxList = { max, min };
        for (int i = 0; i < matrix[0].length; i++) {
            // System.out.println(lister[0][i]);
        }
        return minMaxList;
    }
    public static void shuffleArray(int[] l) {
        int n = l.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int index = i + random.nextInt(n - i);
            swap(l, i, index);
        }
    }

    private static void swap(int[] l, int i, int index) {
        int temp = l[i];
        l[i] = l[index];
        l[index] = temp;
    }

    public static void initializeMeanRand(double matrix[][], double[][] minMaxList) {

        // This is the function that initializes the mean by random. I prefer the other
        // method, of predetermined groups.
        double[] min = minMaxList[1];
        double[] max = minMaxList[0];
        double[][] means = new double[3][matrix[0].length];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                double low = min[j];
                double high = max[j];
                double r = (double) (Math.random() * (high - low)) + low;
                means[i][j] = r;
            }
        }
    }

    public static ArrayList<ArrayList<Integer>> initializeMeanGroup(double matrix[][], int k) {

        // Finding size of initial clusters, by divinding # of elements to be clustered
        // by k
        double floored = matrix.length / k;
        int groupSize = (int) Math.floor(floored);
        int total = matrix.length;
        ArrayList<ArrayList<Integer>> clusters = new ArrayList<ArrayList<Integer>>();

        int[] matrixSize = new int[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            matrixSize[i] = i;
        }

        shuffleArray(matrixSize);

        for(int i = 0; i < k; i++){
            clusters.add(new ArrayList<Integer>());
        }
        int clusterIndex = 0;
        while(total != 0){
            
            for(int i = 0; i<groupSize;i++){
                clusters.get(clusterIndex).add(matrixSize[matrix.length - total]);
                total = total - 1;
            }
            while(clusterIndex == k-1 & total != 0){
                clusters.get(clusterIndex).add(matrix.length - total);
                total = total - 1;
            }
            clusterIndex += 1;
        }
        return clusters;
        
    }

    public static double[] findMean(double[][] smallMatrix){
        double[] average = new double[smallMatrix[0].length];
        for(int i = 0; i < smallMatrix.length; i++){
            for(int j = 0; j < smallMatrix[0].length; j++){
                average[j] += smallMatrix[i][j];
            } 
        }
        for(int j = 0; j < average.length; j++){
            average[j] = average[j] / smallMatrix.length;
        }
        return average;
    }

    public static ArrayList<ArrayList<Integer>> updateClustersCos(double[][] matrix, ArrayList<ArrayList<Integer>> clusters){

        double[][] means = new double[clusters.size()][matrix[0].length];

        for(ArrayList<Integer> cluster:clusters){

            if(cluster.size() == 0){
                means[clusters.indexOf(cluster)] = getRandVector(matrix);
                System.out.println("this is rand vector for index " + String.valueOf(clusters.indexOf(cluster)));
                for(int i = 0; i < means[0]. length; i++){
                    System.out.print(means[clusters.indexOf(cluster)][i]); 
                    System.out.print(" , ");  
                } 
                break;
            }

            double[][] smallMatrix = new double[cluster.size()][matrix[0].length];
            int i = 0;

            for(int index:cluster){
                smallMatrix[i] = matrix[index];
                i++;
            }

            means[clusters.indexOf(cluster)] = findMean(smallMatrix);
        }

        for(ArrayList<Integer> cluster:clusters){
            cluster.removeAll(cluster);
        }
        

        // Updating which vector should be with which cluster based on the mean of the cluster last time around.
        double currentDist = 0.0;
        int currentClust = means.length + 1;
        for(int i = 0; i < matrix.length; i++){
            //System.out.println(means.length);
            for(int j = 0; j < means.length; j++){
                if(cosSim(means[j], matrix[i]) > currentDist){
                    currentClust = j;
                    currentDist = cosSim(means[j], matrix[i]);
                }
            }
            clusters.get(currentClust).add(i);
            currentDist = 0.0;
            currentClust = means.length + 1;
        }

        return clusters;
    }

    public static void printClusterMean(double[][] myMatrix, ArrayList<ArrayList<Integer>> clusters){
        for(ArrayList<Integer> cluster:clusters){
            System.out.print(clusters.indexOf(cluster));
            System.out.print("   size:  ");
            System.out.print(cluster.size());
            System.out.println(" ");
        }

        double[][] means = new double[clusters.size()][myMatrix[0].length];
        for(ArrayList<Integer> cluster:clusters){

            if(cluster.size() == 0){
                for(int i = 0; i < myMatrix[0].length;i++){
                    means[clusters.indexOf(cluster)][i] = 0;
                }
                break;
            }

            double[][] smallMatrix = new double[cluster.size()][myMatrix[0].length];
            int i = 0;

            for(int index:cluster){
                smallMatrix[i] = myMatrix[index];
                i++;
            }

            means[clusters.indexOf(cluster)] = findMean(smallMatrix);
            
        }

        matrix.printMyMatrix(means);

    }

    public static ArrayList<ArrayList<Integer>> updateClustersEuc(double[][] matrix, ArrayList<ArrayList<Integer>> clusters){

        double[][] means = new double[clusters.size()][matrix[0].length];

        for(ArrayList<Integer> cluster:clusters){

            if(cluster.size() == 0){
                means[clusters.indexOf(cluster)] = getRandVector(matrix);
                System.out.println("this is rand vector for index " + String.valueOf(clusters.indexOf(cluster)));
                for(int i = 0; i < means[0]. length; i++){
                    System.out.print(means[clusters.indexOf(cluster)][i]); 
                    System.out.print(" , ");  
                } 
                break;
            }

            double[][] smallMatrix = new double[cluster.size()][matrix[0].length];
            int i = 0;

            for(int index:cluster){
                smallMatrix[i] = matrix[index];
                i++;
            }

            means[clusters.indexOf(cluster)] = findMean(smallMatrix);
        }

        for(ArrayList<Integer> cluster:clusters){
            cluster.removeAll(cluster);
        }
        

        // Updating which vector should be with which cluster based on the mean of the cluster last time around.
        double currentDist = 1.0;
        int currentClust = means.length + 1;
        for(int i = 0; i < matrix.length; i++){
            //System.out.println(means.length);
            for(int j = 0; j < means.length; j++){
                if(eucDist(means[j], matrix[i]) < currentDist){
                    currentClust = j;
                    currentDist = eucDist(means[j], matrix[i]);
                }
            }
            clusters.get(currentClust).add(i);
            currentDist = 1.0;
            currentClust = means.length + 1;
        }

        return clusters;
    }

    public static double eucDist(double[] v1, double[]v2){

        double cumlative = 0.0;
        for(int i = 0; i < v1.length; i++){
            double temp = v1[i] - v2[i];
            cumlative += Math.pow(temp, 2);
        }

        return Math.sqrt(cumlative);
    }

    public static double cosSim(double[] v1, double[]v2){
        return cosTop(v1, v2) / cosBottom(v1, v2);
    }

    public static double cosTop(double[] v1, double[]v2){

        double cumlative = 0.0;
        for(int i = 0; i < v1.length; i++){
            cumlative += v1[i] * v2[i];
        }

        return cumlative;
    }

    public static double cosBottom(double[] v1, double[]v2){

        double aCumlative = 0.0;
        double bCumlative = 0.0;
        for(int i = 0; i < v1.length; i++){
            aCumlative += Math.pow(v1[i], 2);
            bCumlative += Math.pow(v2[i], 2);
        }

        return Math.sqrt(aCumlative) * Math.sqrt(bCumlative);
    }

    public static double[] getRandVector(double matrix[][]){
        double[][] minMaxList = minMax(matrix);
        double[] min = minMaxList[1];
        double[] max = minMaxList[0];
        double[] myVector = new double[matrix[0].length];
        for(int j = 0; j < matrix[0].length; j++){
            double low = min[j];
            double high = max[j];
            double r = (double) (Math.random() * (high - low)) + low;
            myVector[j] = r;
        }


        return myVector;
    }
        

    public static ArrayList<ArrayList<Integer>> masterCluster(double matrix[][], int k){
        ArrayList<ArrayList<Integer>> clusters = initializeMeanGroup(matrix, k);
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

            clusters = updateClustersCos(matrix, clusters);
            if(temp.equals(clusters)){
                //System.out.println(clusters);
                //System.out.println(newClusters);
                //System.out.println("Counter");
                //System.out.println(counter);
                flag = false;
            } 
        }
        
        
        return clusters;
    }
    
}