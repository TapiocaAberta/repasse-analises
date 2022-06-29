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

###################################################

install.packages('ffbase')

require(ffbase)

path <- "/home/pesilva/Documents/micodados_educacao/microdados_censo_escolar_2020/microdados_educacao_basica_2020/DADOS" 

docentes <- read.csv(paste(path, "docentes_co.CSV", sep = "/"), sep = "|")
tail(docentes)

docentes_se <- read.csv2(paste(path, "docentes_sudeste.CSV", sep = "/"), sep = "|")
head(docentes_se)

matriculas_se <- read.csv2(paste(path, "matricula_sudeste.CSV", sep = "/"), sep = "|")

matriculas_co <- read.csv2(paste(path, "matricula_co.CSV", sep = "/"), sep = "|")
head(matriculas_co)


matricula_sinop <- matriculas_co %>% dplyr::filter(matriculas_co$CO_MUNICIPIO == 5107909)
head(matricula_sinop)
colnames(matricula_sinop)

matricula_sinope_creche <- matricula_sinop %>% dplyr::filter(matricula_sinop$TP_ETAPA_ENSINO == 1)
matricula_sinope_pre <- matricula_sinop %>% dplyr::filter(matricula_sinop$TP_ETAPA_ENSINO == 2)
