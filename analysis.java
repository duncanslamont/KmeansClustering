import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.LinkedList;

public class analysis{
    public static void writeFile(String s){
        File myObj = new File(s);
    }
    public static void report(ArrayList<ArrayList<Integer>> clusters){
        writeFile("report.txt");

        try {
            FileWriter myWriter = new FileWriter("report.txt");
            myWriter.write("The program thought each cluster looked like... \n");
            myWriter.write("\n");
            int i = 0;
            for(ArrayList<Integer> cluster:clusters){
                myWriter.write("Cluster ");
                myWriter.write(Integer.toString(i));
                myWriter.write(":  \n");
                for(int index:cluster){
                    myWriter.write(Integer.toString(index));
                    myWriter.write(",");
                }
                i++;
                myWriter.write("\n");
            }
            myWriter.write("\n");
            myWriter.write("Where 0 = c1/article01.txt, 8 = c4/article01.txt and so on.");
            myWriter.write("\n");
            myWriter.write("\n");
            myWriter.write("\n");
            myWriter.write("\n");
            myWriter.flush();
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        writeConfunsionMatrix(clusters);

    }

    public static String padder(String s){
        int num = 20 - s.length();
        for(int i = 0; i < num; i++){
            s = s + " ";
        }
        return s;
    }

    public static void writeConfunsionMatrix(ArrayList<ArrayList<Integer>> clusters){
        ArrayList<Integer> control1 = new ArrayList<Integer>();
        ArrayList<Integer> control2 = new ArrayList<Integer>();
        ArrayList<Integer> control3 = new ArrayList<Integer>();
        for(int k = 0; k < 3; k++){
            for(int i = 0; i < 8; i++){
                if(k == 0){
                    control1.add(i);
                } else if (k == 1){
                    control2.add(i+k*8);
                } else {
                    control3.add(i+k*8);
                }
            }
        }
        ArrayList<ArrayList<Integer>> controlArray = new ArrayList<ArrayList<Integer>>();
        controlArray.add(control1);
        controlArray.add(control2);
        controlArray.add(control3);

        try {
            File f = new File("report.txt");
            PrintWriter out = new PrintWriter(new FileWriter(f, true));
            ArrayList<Integer> currentCluster = new ArrayList<Integer>();
            for(int i = 0; i < 3; i++){
                //System.out.println(i);
                out.append("Confusion Matrix for cluster " + Integer.toString(i) + ": \n");
                out.append("\n");
                out.append("\n");
                out.append(padder(" "));
                out.append(padder("True"));
                out.append(padder("False"));
                out.append(padder("Precision"));
                out.append("\n");
                out.append(padder("Tested True"));
                for(ArrayList<Integer> cluster:clusters){
                    if(cluster.contains(i*8)){
                        currentCluster = cluster;
                    }
                }
                int clusterLength = currentCluster.size();
                currentCluster.retainAll(controlArray.get(i));
                int correct = currentCluster.size();
                int truePositives = correct;
                int falsePositives = clusterLength - correct;
                int falseNegatives = 8 - correct;
                int trueNegatives = 24-clusterLength - falseNegatives;
                double truePositives1 = truePositives;
                double falsePositives1 = falsePositives;
                double falseNegatives1 = falseNegatives;
                double trueNegatives1 = trueNegatives;
                double precisionPositive = 100.0 * (truePositives1 / (truePositives1 + falsePositives1));
                double precisionNegative = 100.0 * (trueNegatives1 / (trueNegatives1 + falseNegatives));
                double recallPositive = 100.0 * (truePositives1 / (truePositives1 + falseNegatives1));
                double recallNegative = 100.0 * (trueNegatives1 / (trueNegatives1 + falsePositives1));
                out.append(padder(Integer.toString(truePositives)));
                out.append(padder(Integer.toString(falsePositives)));
                out.append(padder(Double.toString(precisionPositive)));
                out.append("\n");
                out.append(padder("Tested false"));
                out.append(padder(Integer.toString(falseNegatives)));
                out.append(padder(Integer.toString(trueNegatives)));
                out.append(padder(Double.toString(precisionNegative)));
                out.append("\n");
                out.append(padder("Recall"));
                out.append(padder(Double.toString(recallPositive)));
                out.append(padder(Double.toString(recallNegative)));
                out.append("\n");
                out.append("\n");
                out.append("\n");

            }
           out.close();
            //System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}


