### INI 

load(file = "../../data/ideb_repasse/todos_anos_out.RData")

set.seed(0)

f <- as.formula("IDEB_MEDIA ~ REPASSE_MATRICULA")

modelo_poisson <- glm(formula = f,
                      data = sp,
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
lmtest::lrtest(modelo_poisson) #no caso, comparação com modelo nulo (somente com intercepto)

#Todas as variáveis preditoras se mostraram estatisticamente diferentes de zero,
#considerando-se um nível de significância de 5%, ceteris paribus. Porém, já se
#pode afirmar que a estimação Poisson é a mais adequada?


predicted <- predict(object = modelo_poisson, 
                     newdata = data.frame(REPASSE_MATRICULA = 3714.663),
                     type = "response")

predicted

#############################################################################

ideb_repasse <- read.csv(file = "../../data/ideb_repasse/ideb_repasse.csv")
#ideb_repasse[is.na(ideb_repasse) == TRUE]
ideb_repasse <- na.omit(ideb_repasse)

data <- data.frame(ideb_repasse[1:300,])

#Train-Test Split
train_test_split_index <- 0.8 * nrow(data)

train <- data.frame(data[1:train_test_split_index,])
test <- data.frame(data[(train_test_split_index+1): nrow(data),])

set.seed(0)

f <- as.formula("IDEB_NOTA ~ IDH_EDUCACAO + IDHM + IDH_LONGEVIDADE + IDH_RENDA")

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
        newdata = test[1,5:8],
        type = "response")

real <- test[1,4]


