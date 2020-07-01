import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class matrix extends preprocessing {
    
    
    public static void writeFile(){
        File myObj = new File("tfidf.txt");
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
            System.out.println("Successfully wrote to the file.");
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
        System.out.println("size");
        System.out.println(indexes.size());
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
        writeFile();
        writeToFile(returnMatrix);
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