
This is the clustering algorithm of Duncan Lamont. NYU net Id: DL3833. N#19122868. 

This program uses the latest version of java, I've encountered some errors running it on other machines that do not
have the latest version of java, I'm not sure why.

I used the official Porter stemmer to stem the text files. Website:https://tartarus.org/martin/PorterStemmer/java.txt

For the data visualization part, I couldn't figure out how to do it in java, so I did it in R.
I got help from other student Ben Tatsuka, and from my brother because I am really not well
versed in R. There are only a few lines of R code though.
 

HOW TO RUN THE PROGRAM:
Go to the folder where the project is contained in terminal. Type:
javac headquarters.java
Then:
java headquarters

The results vary from time to time, however it is usually very accurate. I couldn't find a great way to compare the
computed cluster to the actual cluster, so sometimes the confusion matrix is wrong, but it's usually right. 

The report file contains the clusters made, and the confusion matricies. The graph.pdf is the graph of the pca of the
tfidf matrix. Topics is a file with 5 keywords from each cluster using the tfidf matrix, and stopwords is the list of
stopwords I found on the internet labeled "SQL stopwords" . Everytime you run the code, it will rewrite report, tfidf,
and topics. I left in a copy of them just in case it doesn't run. Thank You.

