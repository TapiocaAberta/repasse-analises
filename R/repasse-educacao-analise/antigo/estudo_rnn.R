library("rnn")
library("dplyr")
library("ggplot2")
library("viridis")
library("plotly")
library("corrplot")
library("PerformanceAnalytics")

repasseAno <- read.csv(file = "../../data/repasse_ano.csv")
repasseAno$DATA <- as.Date(repasseAno$DATA);
repasseAno <- na.omit(repasseAno)
summary(repasseAno)

repasseAno <-repasseAno[order(repasseAno$DATA, decreasing = TRUE),]

repasse <- repasseAno$VALOR
repasse_anterior <- lead(repasse,n=1L)

data_analise <- data.frame(repasse)
data_analise$repasse_anterior <- repasse_anterior

#exclui NA
data_analise <- na.omit(data_analise)

x <- data_analise[,2]
y <- data_analise[,1]

X <- matrix(x, nrow = 23)
Y <- matrix(y, nrow = 23)

# t1 <- s.x * attr(s.x, 'scaled:scale') + attr(s.x, 'scaled:center')

Yscaled <- scale(Y)
Xscaled <- scale(X)

Y <- Yscaled
X <- Xscaled

train=1:3
test=4:5

set.seed(13)

model <- trainr(Y = Y[,train],
                X = X[,train],
                learningrate = 0.05,
                hidden_dim = 20,
                numepochs = 1000,
                network_type = "lstm"
)

#no conjunto de treinamento
Ytrain <- t(matrix(predictr(model, X[,train]),nrow=1))
Yreal <- t(matrix(Y[,train],nrow=1))

#Percentual de variação em uma variável explicada por outra
rsq <- function(y_actual,y_predict){
  cor(y_actual,y_predict)^2
}

rsq(Yreal,Ytrain)

plot(Ytrain, type = "l", col = "darkred")
lines(Yreal, col = "darkblue", type = "l")

#no conjunto de teste
Ytest=matrix(Y[,test], nrow = 1)
Ytest = t(Ytest)
Yp <- predictr(model, Y[,test])
Ypredicted=matrix(Yp, nrow = 1)
Ypredicted=t(Ypredicted)

result_data <- data.frame(Ytest)
result_data$Ypredicted <- Ypredicted    

ep_1000_hd_20 <- rsq(result_data$Ytest,result_data$Ypredicted)

# t1 <- s.x * attr(s.x, 'scaled:scale') + attr(s.x, 'scaled:center')

mean(result_data$Ytest)
mean(result_data$Ypredicted)

plot(colMeans(model$error),type='l',xlab='epoch',ylab='errors')