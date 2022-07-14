load(file = "../../data/ideb_repasse/todos_anos.RData")

### IDH

quantile(ideb_repasse$IDHM)

### Corplot

col_num <- c(6:10, 17, 19)
data_num_ini <- ideb_repasse[, col_num]

correlations_ini <- cor(data_num_ini)
corrplot(correlations_ini, method = "circle")


######### Tratamento

faixa_a <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "A")
faixa_b <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "B")
faixa_c <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "C")
faixa_d <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "D")
faixa_e <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "E")
faixa_f <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "F")
faixa_g <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "G")

######### Primeira análise

summary(ideb_repasse)
pop_total <- dplyr::n_distinct(ideb_repasse$CODIGO_MUNICIPIO)

pop_a <- dplyr::n_distinct(faixa_a$CODIGO_MUNICIPIO)
pop_b <- dplyr::n_distinct(faixa_b$CODIGO_MUNICIPIO)
pop_c <- dplyr::n_distinct(faixa_c$CODIGO_MUNICIPIO)
pop_d <- dplyr::n_distinct(faixa_d$CODIGO_MUNICIPIO)
pop_e <- dplyr::n_distinct(faixa_e$CODIGO_MUNICIPIO)
pop_f <- dplyr::n_distinct(faixa_f$CODIGO_MUNICIPIO)
pop_g <- dplyr::n_distinct(faixa_g$CODIGO_MUNICIPIO)

porcentagem(pop_total, pop_a)
porcentagem(pop_total, pop_b)
porcentagem(pop_total, pop_c)
porcentagem(pop_total, pop_d)
porcentagem(pop_total, pop_e)
porcentagem(pop_total, pop_f)
porcentagem(pop_total, pop_g)

######### Correlação

# Todos

cor.test(ideb_repasse$REPASSE_MATRICULA, ideb_repasse$IDEB_NOTA, method = "pearson")

# Por Faixa sem média

cor.test(faixa_a$REPASSE_MATRICULA, faixa_a$IDEB_NOTA, method = "pearson")
cor.test(faixa_b$REPASSE_MATRICULA, faixa_b$IDEB_NOTA, method = "pearson")
cor.test(faixa_c$REPASSE_MATRICULA, faixa_c$IDEB_NOTA, method = "pearson")
cor.test(faixa_d$REPASSE_MATRICULA, faixa_d$IDEB_NOTA, method = "pearson")
cor.test(faixa_e$REPASSE_MATRICULA, faixa_e$IDEB_NOTA, method = "pearson")
cor.test(faixa_f$REPASSE_MATRICULA, faixa_f$IDEB_NOTA, method = "pearson")
cor.test(faixa_g$REPASSE_MATRICULA, faixa_g$IDEB_NOTA, method = "pearson")


######################3333
a2013 <- dplyr::filter(ensino_medio, ensino_medio$ANO_IDEB == 2013)
summary(a2013)

a2015 <- dplyr::filter(ensino_medio, ensino_medio$ANO_IDEB == 2015)
summary(a2015)

a2017 <- dplyr::filter(ensino_medio, ensino_medio$ANO_IDEB == 2017)
summary(a2017)

a2019 <- dplyr::filter(ensino_medio, ensino_medio$ANO_IDEB == 2019)
summary(a2019)
###################

r2012 <- dplyr::filter(anos_finais, anos_finais$ANO_REPASSE == 2012)
summary(r2012)

r2014 <- dplyr::filter(anos_finais, anos_finais$ANO_REPASSE == 2014)
summary(r2014)

r2016 <- dplyr::filter(anos_finais, anos_finais$ANO_REPASSE == 2016)
summary(r2016)

r2018 <- dplyr::filter(anos_finais, anos_finais$ANO_REPASSE == 2018)
summary(r2018)

#####################################

r2012 <- dplyr::filter(ensino_medio, ensino_medio$ANO_MATRICULA == 2015)
summary(r2012)

r2014 <- dplyr::filter(ensino_medio, ensino_medio$ANO_MATRICULA == 2017)
summary(r2014)

r2016 <- dplyr::filter(anos_finais, anos_finais$ANO_MATRICULA == 2015)
summary(r2016)

r2018 <- dplyr::filter(anos_finais, anos_finais$ANO_MATRICULA == 2017)
summary(r2018)




