import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class matrix extends preprocessing {
    
    
    public static void writeFile(String s){
        File myObj = new File(s);
    }

    public static void generateKeyWords(double[][] matrix, ArrayList<Integer> indexes, ArrayList<String> wordList){

        //System.out.println(wordList);
        double[] totals1 = new double[indexes.size()];
        double[] totals2 = new double[indexes.size()];
        double[] totals3 = new double[indexes.size()];
        String[] words1 = new String[5];
        String[] words2 = new String[5];
        String[] words3 = new String[5];
        double max1 = 0.0;
        double max2 = 0.0;
        double max3 = 0.0;
        int ind1 = 0;
        int ind2 = 0;
        int ind3 = 0;

        writeFile("topics.txt");
        try {
            FileWriter myWriter = new FileWriter("topics.txt");
            for(int k = 0; k < 3; k++){
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < matrix[0].length;j++){
                        if(k == 0){
                            totals1[j] = totals1[j] + matrix[i][j];
                        }else if ( k == 1){
                            totals2[j] = totals2[j] + matrix[8 + i][j];
                        } else {
                            totals3[j] = totals3[j] + matrix[16 + i][j];
                        }
                    }
                }
            }
            for(int k = 0; k < 5; k++){
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < matrix[0].length; j++){
                        if(i == 0){
                            if(totals1[j] > max1){
                                max1 = totals1[j];
                                ind1 = j;
                            }
                        }else if (i == 1){
                            if(totals2[j] > max2){
                                max2 = totals2[j];
                                ind2 = j;
                            }
                        }else {
                            if(totals3[j] > max3){
                                max3 = totals3[j];
                                ind3 = j;
                            }
                        }
                    }
                }
                words1[k] = wordList.get(indexes.get(ind1));
                totals1[ind1] = 0.0;
                max1 = 0.0;
                ind1 = 0;
                words2[k] = wordList.get(indexes.get(ind2));
                totals2[ind2] = 0.0;
                max2 = 0.0;
                ind2 = 0;
                words3[k] = wordList.get(indexes.get(ind3));
                totals3[ind3] = 0.0;
                max3 = 0.0;
                ind3 = 0;
            }


            for(int i = 0; i < 3; i++){
                if(i == 0){
                    myWriter.write("Cluster 1 keywords: ");
                } else if (i == 1){
                    myWriter.write("Cluster 2 keywords: ");
                } else {
                    myWriter.write("Cluster 3 keywords: ");
                }
                for(int j = 0; j < 5; j++){
                    if(i == 0){
                        myWriter.write(words1[j]);
                        myWriter.write(", ");
                    }else if (i == 1){
                        myWriter.write(words2[j]);
                        myWriter.write(", ");
                    }else {
                        myWriter.write(words3[j]);
                        myWriter.write(", ");
                    }
                }
                
                myWriter.write("\n");
            }
            myWriter.flush();
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
        

    }

    public static void writeToFile(double[][] matrix){
        try {
            FileWriter myWriter = new FileWriter("tfidf.txt");
            for(int i = 0; i < matrix.length; i++){
                for(int j = 0; j < matrix[0].length;j++){
                    myWriter.write(Double.toString(matrix[i][j]));
                    myWriter.write(",");
                }
                myWriter.write("\n");
            }
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

    }

    public static double[][] generateMatrix(){
        
        for(ArrayList<String> list:fileArrays){
            for(String word:list){
                if(wordList.contains(word)){
                    countList.set(wordList.indexOf(word), countList.get(wordList.indexOf(word)) + 1);
                } else {
                    wordList.add(word);
                    countList.add(1);
                }
            }
        }
        for(String word:wordList){
            if(countList.get(wordList.indexOf(word)) > 5){
                indexes.add(wordList.indexOf(word));
                //System.out.println(word);
            }
        }
        //System.out.println("size");
       // System.out.println(indexes.size());
        indexes.remove(wordList.indexOf("a"));

        int sizer = 0;
        final String words[] = new String[indexes.size()];
        for(Integer i:indexes){
            words[sizer] = wordList.get(i);
            sizer++;
        }
        ArrayList<String> wordArrayList = new ArrayList<String>(Arrays.asList(words));
        //System.out.println("Word array list size :");
        //System.out.println(wordArrayList.size());

        int finalMatrix[][] = new int[24][indexes.size()];
        //System.out.println("Indexes size :");
        //System.out.println(indexes.size());

        int indexForFile = 0;

        for(ArrayList<String> list:fileArrays){
            for(String word:list){
                if(wordArrayList.contains(word)){
                    finalMatrix[indexForFile][wordArrayList.indexOf(word)] += 1;
                } 
            }
            indexForFile++;
        }
        //printMyMatrix(finalMatrix);
        //idf(wordArrayList);
        double[][] returnMatrix = tdidf(tf(wordArrayList),idf(wordArrayList), wordArrayList);
        writeFile("tfidf.txt");
        writeToFile(returnMatrix);
        generateKeyWords(returnMatrix, indexes, wordList);
        return returnMatrix;
    }


    public static double[][] tf(ArrayList<String> wordArrayList){
        double newMatrix[][] = new double[24][wordArrayList.size()];
        int goodOldCounter = 0;
        for(String keyword:wordArrayList){
            
            int indexForFile = 0;
            for(ArrayList<String> list:fileArrays){
                int fileWordCount = list.size();
                for(String word:list){
                    if(word.equals(keyword)){
                        goodOldCounter++;
                    }
                }
                if(fileArrays.indexOf(list) == 0 & keyword.equals("aviat")){
                    //System.out.print("This is the count of aviat");
                    //System.out.println(goodOldCounter);

                }
                newMatrix[indexForFile][wordArrayList.indexOf(keyword)] = goodOldCounter / (double) fileWordCount;
                indexForFile++;
                goodOldCounter = 0;
            }
            
        }
        //System.out.println(wordArrayList.get(0));
        //printMyMatrix(newMatrix, wordArrayList);
        return newMatrix;
    }

    public static double[] idf(ArrayList<String> wordArrayList){
        double newMatrix[] = new double[wordArrayList.size()];
        for(String keyword:wordArrayList){
            int currentCount = 0;
            for(ArrayList<String> list:fileArrays){
                int fileChecker = 0;
                for(String word:list){
                    if(word.equals(keyword) & fileChecker == 0){
                        fileChecker = 1;
                        currentCount++;
                    }
                }
                fileChecker = 0;
            }
            newMatrix[wordArrayList.indexOf(keyword)] = Math.log(24/(double)currentCount);
        }
        return newMatrix;
    }

    public static double[][] tdidf(double[][] matrix, double[] row,ArrayList<String> wordArrayList){
        double newMatrix[][] = new double[24][wordArrayList.size()];
        for(int i = 0; i<matrix.length; i++){
            for(int j = 0; j<matrix[i].length; j++){
                newMatrix[i][j] = matrix[i][j] * row[j];
            }
        }
        //printMyMatrix(newMatrix, wordArrayList);
        return newMatrix;
    }


  
    public static void printMyMatrix(int matrix[][]){ 
        
        for (int i = 0; i < matrix.length; i++){
            
            for (int j = 0; j < matrix[i].length; j++){ 
                System.out.print(matrix[i][j] + " "); 
            }
            System.out.println(" ");
        }
    } 
    public static void printMyMatrix(double matrix[][]){ 
        
        for (int i = 0; i < matrix.length; i++){
            System.out.println(i);
            
            for (int j = 0; j < matrix[i].length; j++){ 
                System.out.print(matrix[i][j] + " "); 
            }
            System.out.println(" ");
        }
    } 
}