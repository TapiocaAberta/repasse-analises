library("tidyverse") #pacote para manipulacao de dados
library("cluster") #algoritmo de cluster
library("dendextend") #compara dendogramas
library("factoextra") #algoritmo de cluster e visualizacao
library("fpc") #algoritmo de cluster e visualizacao
library("gridExtra") #para a funcao grid arrange
##################################################################################

set.seed(42)
load(file = "../../data/ideb_repasse/fundeb/anos_iniciais.RData")
load(file = "../../data/ideb_repasse/fundeb/anos_finais.RData")

anos_iniciais$REGIAO = as.factor(anos_iniciais$REGIAO)
anos_finais$REGIAO = as.factor(anos_finais$REGIAO)

##################################################################################

cols <- c("ANO_IDEB", "IDEB_NOTA", "IDHM", "IDH_EDUCACAO", "IDH_LONGEVIDADE", "CODIGO_MUNICIPIO",
          "IDH_RENDA", "REPASSE_MATRICULA", "POPULACAO", "MATRICULA")

## Anos Iniciais ##

data_ini <- anos_iniciais[, cols]
rownames(data_ini) <- paste(data_ini$ANO_IDEB, data_ini$CODIGO_MUNICIPIO, sep = "-")
data_ini <- data_ini[,-c(1,6)]

data.padronized <- (scale(data_ini))
fviz_nbclust(data.padronized, kmeans, method = "wss") +
  geom_vline(xintercept = 3, linetype = 2)

k4_ini <- kmeans(data.padronized, centers = 3)
fviz_cluster(k4_ini, data = data.padronized, main = "Cluster K3", geom = c("point"))

datafit_ini <- data.frame(k4_ini$cluster)
anos_iniciais <-  cbind(anos_iniciais, datafit_ini)

plotcluster(data.padronized, k4_ini$cluster)

##################################################################################
anos_iniciais_k1 <- anos_iniciais %>% dplyr::filter(k4_ini.cluster == "1")
summary(anos_iniciais_k1)

cor_k1_ini <- cor(anos_iniciais_k1[, c("IDEB_NOTA", "REPASSE_MATRICULA")], method = "pearson")
corrplot(cor_k1_ini, method="number")
cor.test(anos_iniciais_k1$REPASSE_MATRICULA, anos_iniciais_k1$IDEB_NOTA, method = "pearson")
##################################################################################
anos_iniciais_k2 <- anos_iniciais %>% dplyr::filter(k4_ini.cluster == "2")
summary(anos_iniciais_k2)

cor_k2_ini <- cor(anos_iniciais_k2[, c("IDEB_NOTA", "REPASSE_MATRICULA")], method = "pearson")
corrplot(cor_k2_ini, method="number")
cor.test(anos_iniciais_k2$REPASSE_MATRICULA, anos_iniciais_k2$IDEB_NOTA, method = "pearson")
##################################################################################
anos_iniciais_k3 <- anos_iniciais %>% dplyr::filter(k4_ini.cluster == "3")
summary(anos_iniciais_k3)

cor_k3_ini <- cor(anos_iniciais_k3[, c("IDEB_NOTA", "REPASSE_MATRICULA")], method = "pearson")
corrplot(cor_k3_ini, method="number")
cor.test(anos_iniciais_k3$REPASSE_MATRICULA, anos_iniciais_k3$IDEB_NOTA, method = "pearson")
##################################################################################

## Anos Finais ##

data_fin <- anos_finais[, cols]
rownames(data_fin) <- paste(data_fin$ANO_IDEB, data_fin$CODIGO_MUNICIPIO, sep = "-")
data_fin <- data_fin[,-c(1,6)]

data.padronized <- (scale(data_fin))

k3_fin <- kmeans(data.padronized, centers = 3)
fviz_cluster(k3_fin, data = data.padronized, main = "Cluster K3", geom = c("point"))

datafit_fin <- data.frame(k3_fin$cluster)
anos_finais <-  cbind(anos_finais, datafit_fin)

plotcluster(data.padronized, k3_fin$cluster)

anos_finais$REGIAO = as.factor(anos_finais$REGIAO)

##################################################################################
anos_finais_k1 <- anos_finais %>% dplyr::filter(k3_fin.cluster == "1")
summary(anos_finais_k1)

##################################################################################
anos_finais_k2 <- anos_finais %>% dplyr::filter(k3_fin.cluster == "2")
summary(anos_finais_k2)

##################################################################################
anos_finais_k3 <- anos_finais %>% dplyr::filter(k3_fin.cluster == "3")
summary(anos_finais_k3)

##################################################################################