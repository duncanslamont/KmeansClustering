df = read.csv("tfidf.txt", header = F)
df <- df[,0:223]
df.pca <- prcomp(df)
df.pc <- predict(df.pca)
plot(df.pc[,c(1,2)], type = "n", xlab = "X", ylab = "Y", main = "PCA visualization of TDIDF matrix")
text(df.pc[,c(1,2)], labels=row.names(df), col=c(rep("blue", 8), rep("red", 8), rep("black",8)))
View(df)