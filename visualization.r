df = read.csv("tfidf.txt", header = F)
df <- df[,0:223]
df.pca <- prcomp(df)
df.pc <- predict(df.pca)
plot(df.pc[,c(1,2)], type = "n", xlab = "PC1", ylab = "PC2", main = "TITLE")
text(df.pc[,c(1,2)], labels=row.names(df), col=c(rep("blue", 8), rep("red", 8), rep("green",8)))
View(df)