db <- ideb_repasse[order(ideb_repasse$MUNICIPIO),][1:12,]

db %>% group_by(ANO_IDEB, IDH_LONGEVIDADE, UF, ANO_IDH, IDHM, IDH_RENDA, POPULACAO, VALOR_REPASSADO_EDUCACAO, ANO_REPASSE, IDH_EDUCACAO, MUNICIPIO, REDE) %>% summarise_at(vars(IDEB_NOTA), list(ideb_media = mean))

library("PerformanceAnalytics")

data <- ideb_media[, c(2,5,6,8,10,13)]

data_scale <- scale(data)
chart.Correlation(data_scale, histogram=TRUE)

cor(data)

class(data)

par(mfrow=c(1,6))
for(i in 1:6) {
  boxplot(data[,i], main=names(data)[i])
}

boxplot(data$VALOR_REPASSADO_EDUCACAO, main="VALOR_REPASSADO_EDUCACAO")


