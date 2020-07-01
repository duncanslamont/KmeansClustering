import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

// In this project I use the official porter stemmer, https://tartarus.org/martin/PorterStemmer/java.txt
// I did not create this stemmer.
public class preprocessing extends headquarters {
    public static ArrayList<String> stopWords(){
        // For stop words, I used a list I found labeled SQL standard stop words.
        ArrayList<String> stopWords = new ArrayList<String>();
        try {
            File stopObj = new File("stopwords.txt");
            Scanner myReader = new Scanner(stopObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              stopWords.addAll(Arrays.asList(data.split("\t")));
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          return stopWords;
    }
    public static void dataPreparation(int i){
        try {
            File fileObject = new File(fileNames[i]);
            Scanner myReader = new Scanner(fileObject);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ArrayList<String> line = new ArrayList<String>(Arrays.asList(data.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"))); 
                // Removal of stop words
                line.removeAll(stopWords); 
                // Stemming and lemmatizatoin (using the porter stemmer)
                int k = 0;
                for(String word:line){
                    char[] ch = new char[word.length()]; 
                    for (int j = 0; j < word.length(); j++) { 
                        ch[j] = word.charAt(j); 
                    } 
                    Stemmer stemmer = new Stemmer();
                    stemmer.add(ch,word.length());
                    stemmer.stem();
                    String str = stemmer.toString();
                    line.set(k, str);
                    k++;
                }
                fileArrays.get(i).addAll(line);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    }
}