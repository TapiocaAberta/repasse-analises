library("rnn")
library("dplyr")

ideb_repasse <- read.csv(file = "../../data/ideb_repasse/ideb_repasse.csv")
ideb_repasse <- na.omit(ideb_repasse)

head(ideb_repasse, n = 5)
summary(ideb_repasse)

anos_iniciais <- dplyr::filter(ideb_repasse, PERIODO=="FUNDAMENTAL_ANOS_INICIAIS")

data <- dplyr::select(anos_iniciais, 
                      c('VALOR_REPASSADO_EDUCACAO', 'IDEB_NOTA', 
                        'IDH_EDUCACAO', 'IDHM', 
                        'IDH_LONGEVIDADE', 'IDH_RENDA'))


############ [COR] ###################

if(!require(devtools)) install.packages("devtools")
devtools::install_github("kassambara/ggpubr")
library("ggpubr")

ggscatter(ideb_repasse, x = "VALOR_REPASSADO_EDUCACAO", y = "IDEB_NOTA", 
          add = "reg.line", conf.int = TRUE, 
          cor.coef = TRUE, cor.method = "pearson",
          xlab = "Valor Repassado para Educação em Reais", ylab = "Nota ideb")

ggscatter(ideb_repasse, x = "VALOR_REPASSADO_EDUCACAO", y = "IDH_EDUCACAO", 
          add = "reg.line", conf.int = TRUE, 
          cor.coef = TRUE, cor.method = "pearson",
          xlab = "Valor Repassado para Educação em Reais", ylab = "Nota ideb")

shapiro.test(data$VALOR_REPASSADO_EDUCACAO)
shapiro.test(data$IDEB_NOTA)

# VALOR_REPASSADO_EDUCACAO
ggqqplot(ideb_repasse$VALOR_REPASSADO_EDUCACAO, ylab = "MPG")

# IDEB_NOTA
ggqqplot(ideb_repasse$IDEB_NOTA, ylab = "WT")

# Correlação
res <- cor.test(ideb_repasse,
                method = "pearson")
res

#####

install.packages("corrplot")
library(corrplot)

res2 <- cor(data)
round(res2, 2)

corrplot(res2, type = "upper", order = "hclust", 
         tl.col = "black", tl.srt = 45)

#######

install.packages("PerformanceAnalytics")
library("PerformanceAnalytics")

chart.Correlation(data, histogram=TRUE)
