pacotes <- c("MASS","neuralnet","ISLR","mlbench","rpart", "dplyr")

if(sum(as.numeric(!pacotes %in% installed.packages())) != 0){
  instalador <- pacotes[!pacotes %in% installed.packages()]
  for(i in 1:length(instalador)) {
    install.packages(instalador, dependencies = T)
    break()}
  sapply(pacotes, require, character = T) 
} else {
  sapply(pacotes, require, character = T) 
}

library("rpart")


set.seed(0)

ideb_repasse <- read.csv(file = "../../data/ideb_repasse/ideb_repasse.csv")
#ideb_repasse[is.na(ideb_repasse) == TRUE]
ideb_repasse <- na.omit(ideb_repasse)

head(ideb_repasse)

data <- dplyr::select(ideb_repasse, 
                      c('VALOR_REPASSADO_EDUCACAO', 'IDEB_NOTA', 'IDH_EDUCACAO', 'IDHM', 
                        'IDH_LONGEVIDADE', 'IDH_RENDA'))

#Train-Test Split
train_test_split_index <- 0.8 * nrow(data)

train <- data.frame(data[1:train_test_split_index,])
test <- data.frame(data[(train_test_split_index+1): nrow(data),])

#CART

# árvore
fit_tree <- rpart(VALOR_REPASSADO_EDUCACAO ~.,method="anova", data=train)
tree_predict <- predict(fit_tree,test)

mse_tree <- mean((tree_predict - test$VALOR_REPASSADO_EDUCACAO)^2)

df_tree <- data.frame(tree_predict, test$VALOR_REPASSADO_EDUCACAO)

##############################

library("dplyr")

ideb_repasse <- read.csv(file = "../../data/ideb_repasse/ideb_repasse.csv")
#ideb_repasse[is.na(ideb_repasse) == TRUE]
ideb_repasse <- na.omit(ideb_repasse)

head(ideb_repasse)

data <- dplyr::select(ideb_repasse, 
                      c('VALOR_REPASSADO_EDUCACAO', 'IDEB_NOTA', 'IDH_EDUCACAO', 'IDHM', 
                        'IDH_LONGEVIDADE', 'IDH_RENDA'))

max_data <- apply(data, 2, max)
min_data <- apply(data, 2, min)

max_data
min_data

scaled <- data.frame(scale(data,center = min_data, scale = max_data - min_data))

set.seed(0)

#train test split
index = sample(1:nrow(data),round(0.70*nrow(data)))
index

train_data <- as.data.frame(scaled[index,])
test_data <- as.data.frame(scaled[-index,])

#Utiliza o neuralnet
set.seed(0)
n = names(train_data)
n

f <- as.formula(paste("VALOR_REPASSADO_EDUCACAO ~", paste(n[!n %in% "VALOR_REPASSADO_EDUCACAO"], collapse = " + ")))
f

nn <- neuralnet(f,data=train_data,hidden=c(5,4),linear.output=F)
plot(nn)

pr.nn <- compute(nn,test_data[,2:6])

pr.nn_ <- pr.nn$net.result*(max(data$VALOR_REPASSADO_EDUCACAO)-min(data$VALOR_REPASSADO_EDUCACAO))+min(data$VALOR_REPASSADO_EDUCACAO)
test.r <- (test_data$VALOR_REPASSADO_EDUCACAO)*(max(data$VALOR_REPASSADO_EDUCACAO)-min(data$VALOR_REPASSADO_EDUCACAO))+min(data$VALOR_REPASSADO_EDUCACAO)


MSE_nn <- mean((pr.nn_ - test.r)^2) #Erro Quadratico Médio

##########################3

install.packages("ggpubr")
library("ggpubr")

cor.test(ideb_repasse$VALOR_REPASSADO_EDUCACAO, 
         ideb_repasse$IDEB_NOTA,
         ideb_repasse$IDH_EDUCACAO,
         method = "pearson")

# mpg
ggqqplot(ideb_repasse$VALOR_REPASSADO_EDUCACAO, ylab = "VALOR_REPASSADO_EDUCACAO")

# wt
ggqqplot(ideb_repasse$IDEB_NOTA, ylab = "IDEB_NOTA")


ggscatter(ideb_repasse, x = "VALOR_REPASSADO_EDUCACAO", y = "IDEB_NOTA", 
          add = "reg.line", conf.int = TRUE, 
          cor.coef = TRUE, cor.method = "pearson",
          xlab = "$", ylab = "Nota")
