library("rnn")
library("dplyr")
library("ggplot2")
library("viridis")
library("plotly")
library(corrplot)
library("PerformanceAnalytics")

ideb_repasse <- read.csv(file = "../../data/ideb_repasse/ideb_repasse.csv")
ideb_repasse <- na.omit(ideb_repasse)

head(ideb_repasse, n = 5)
summary(ideb_repasse)
glimpse(ideb_repasse)

anos_iniciais_SP <- ideb_repasse %>% 
  dplyr::filter((PERIODO=="FUNDAMENTAL_ANOS_INICIAIS" & UF=="SP" & IDH_EDUCACAO > 0))

# Graph

ggplotly(
  ggplot(anos_iniciais_SP[1:50,], aes(fill=MUNICIPIO, y=VALOR_REPASSADO_EDUCACAO, x=ANO_REPASSE)) +  
    geom_bar(position="dodge", stat="identity") + 
    scale_color_viridis() + theme_bw()
)

### Correlação

data <- anos_iniciais_SP[,c(4:8, 14)];
cor(data)

library(GGally)
ggpairs(data, title="correlogram with ggpairs()") 
ggcorr(data, method = c("everything", "pearson"))

### Regressão Linear

#Train-Test Split
train_test_split_index <- 0.8 * nrow(data)

train <- data.frame(data[1:train_test_split_index,])
test <- data.frame(data[(train_test_split_index+1): nrow(data),])

set.seed(0)

f <- as.formula("VALOR_REPASSADO_EDUCACAO ~ IDH_EDUCACAO + IDHM + IDH_LONGEVIDADE + IDH_RENDA + IDEB_NOTA")

modelo_poisson <- glm(formula = f,
                      data = train,
                      family = "poisson")

#Parâmetros do modelo_poisson
summary(modelo_poisson)

#Extração do valor de Log-Likelihood (LL)
logLik(modelo_poisson)

#Outra forma de visualização dos parâmetros - função summ do pacote jtools
summ(modelo_poisson, digits = 4, confint = T, ci.width = 0.95)
export_summs(modelo_poisson, scale = F, digits = 4)

#LR Test - função lrtest do pacote lmtest
#(likelihood ratio test para comparação de LL's entre modelos)
lrtest(modelo_poisson) #no caso, comparação com modelo nulo (somente com intercepto)

#Todas as variáveis preditoras se mostraram estatisticamente diferentes de zero,
#considerando-se um nível de significância de 5%, ceteris paribus. Porém, já se
#pode afirmar que a estimação Poisson é a mais adequada?


predicted <- predict(object = modelo_poisson, 
                     newdata = test[1,1:5],
                     type = "response")

real <- test[1,6]


mse <- function(predicted, real) {
  mean((predicted - real)^2)
}

mse(predicted = predicted, real = real)

####### NN

set.seed(0)

data_500 <- as.data.frame(data[1:500,])

max_data <- apply(data_500, 2, max)
min_data <- apply(data_500, 2, min)

max_data
min_data

scaled <- data.frame(scale(data_500,center = min_data, scale = max_data - min_data))

set.seed(0)

#train test split
index = sample(1:nrow(data_500),round(0.80*nrow(data_500)))
index

train_data <- as.data.frame(scaled[index,])
test_data <- as.data.frame(scaled[-index,])

#Utiliza o neuralnet
set.seed(0)
n = names(train_data)
n

f <- as.formula(paste("VALOR_REPASSADO_EDUCACAO ~", paste(n[!n %in% "VALOR_REPASSADO_EDUCACAO"], collapse = " + ")))
f

nn <- neuralnet::neuralnet(f,data=train_data,hidden=c(5,4,3),linear.output=F, rep = 1000)
plot(nn)

pr.nn <- neuralnet::compute(nn,test_data[,1:5])

pr.nn_ <- pr.nn$net.result*(max(data$VALOR_REPASSADO_EDUCACAO)-min(data$VALOR_REPASSADO_EDUCACAO))+min(data$VALOR_REPASSADO_EDUCACAO)
test.r <- (test_data$VALOR_REPASSADO_EDUCACAO)*(max(data$VALOR_REPASSADO_EDUCACAO)-min(data$VALOR_REPASSADO_EDUCACAO))+min(data$VALOR_REPASSADO_EDUCACAO)

mse(pr.nn_, test.r) #Erro Quadratico Médio

df_final <- data.frame(pr.nn_, test.r)
