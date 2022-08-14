load(file = "../../data/ideb_repasse/fundeb/anos_finais.RData")

library(ggplot2)
library(viridis)
library(plotly)

######### Cor

col_num <- c(7:11, 18, 20)
data_num_fin <- anos_finais[, col_num]

correlations_fin <- cor(data_num_fin)
corrplot(correlations_fin, method = "circle")

######### Tratamento

anos_finais_a <- anos_finais %>% dplyr::filter(FAIXA_POP == "A")
anos_finais_b <- anos_finais %>% dplyr::filter(FAIXA_POP == "B")
anos_finais_c <- anos_finais %>% dplyr::filter(FAIXA_POP == "C")
anos_finais_d <- anos_finais %>% dplyr::filter(FAIXA_POP == "D")
anos_finais_e <- anos_finais %>% dplyr::filter(FAIXA_POP == "E")
anos_finais_f <- anos_finais %>% dplyr::filter(FAIXA_POP == "F")
anos_finais_g <- anos_finais %>% dplyr::filter(FAIXA_POP == "G")

# Médias

ideb_faixa_fin <- anos_finais %>% 
  group_by(FAIXA_POP, ANO_IDEB) %>% 
  summarise_at(vars(IDEB_NOTA), list(IDEB_MEDIA = mean));

valor_faixa_fin <- anos_finais %>% 
  group_by(FAIXA_POP, ANO_REPASSE) %>% 
  summarise_at(vars(REPASSE_MATRICULA), list(RPM_MEDIA = mean));

tudo_fin <- data.frame(FAIXA_POP  = ideb_faixa_fin$FAIXA_POP, 
                       IDEB_MEDIA = ideb_faixa_fin$IDEB_MEDIA, 
                       ANO_IDEB = ideb_faixa_fin$ANO_IDEB, 
                       ANO_REPASSE = valor_faixa_fin$ANO_REPASSE,
                       RPM_MEDIA = valor_faixa_fin$RPM_MEDIA)

######### Primeira análise

summary(anos_finais)

pop_total <- dplyr::n_distinct(anos_finais$CODIGO_MUNICIPIO)

pop_a <- dplyr::n_distinct(anos_finais_a$CODIGO_MUNICIPIO)
pop_b <- dplyr::n_distinct(anos_finais_b$CODIGO_MUNICIPIO)
pop_c <- dplyr::n_distinct(anos_finais_c$CODIGO_MUNICIPIO)
pop_d <- dplyr::n_distinct(anos_finais_d$CODIGO_MUNICIPIO)
pop_e <- dplyr::n_distinct(anos_finais_e$CODIGO_MUNICIPIO)
pop_f <- dplyr::n_distinct(anos_finais_f$CODIGO_MUNICIPIO)
pop_g <- dplyr::n_distinct(anos_finais_g$CODIGO_MUNICIPIO)

porcentagem(pop_total, pop_a)
porcentagem(pop_total, pop_b)
porcentagem(pop_total, pop_c)
porcentagem(pop_total, pop_d)
porcentagem(pop_total, pop_e)
porcentagem(pop_total, pop_f)
porcentagem(pop_total, pop_g)


######### Correlação

# Todos

cor.test(anos_finais$REPASSE_MATRICULA, anos_finais$IDEB_NOTA, method = "pearson")

# Por Faixa sem média

cor.test(anos_finais_a$REPASSE_MATRICULA, anos_finais_a$IDEB_NOTA, method = "pearson")
cor.test(anos_finais_b$REPASSE_MATRICULA, anos_finais_b$IDEB_NOTA, method = "pearson")
cor.test(anos_finais_c$REPASSE_MATRICULA, anos_finais_c$IDEB_NOTA, method = "pearson")
cor.test(anos_finais_d$REPASSE_MATRICULA, anos_finais_d$IDEB_NOTA, method = "pearson")
cor.test(anos_finais_e$REPASSE_MATRICULA, anos_finais_e$IDEB_NOTA, method = "pearson")
cor.test(anos_finais_f$REPASSE_MATRICULA, anos_finais_f$IDEB_NOTA, method = "pearson")
cor.test(anos_finais_g$REPASSE_MATRICULA, anos_finais_g$IDEB_NOTA, method = "pearson")

# Todos Media
cor.test(tudo_fin$RPM_MEDIA, tudo_fin$IDEB_MEDIA, method = "pearson")

# Por Faixa média

faixa_a_fin <- tudo_fin %>% dplyr::filter(FAIXA_POP == "A")
faixa_b_fin <- tudo_fin %>% dplyr::filter(FAIXA_POP == "B")
faixa_c_fin <- tudo_fin %>% dplyr::filter(FAIXA_POP == "C")
faixa_d_fin <- tudo_fin %>% dplyr::filter(FAIXA_POP == "D")
faixa_e_fin <- tudo_fin %>% dplyr::filter(FAIXA_POP == "E")
faixa_f_fin <- tudo_fin %>% dplyr::filter(FAIXA_POP == "F")
faixa_g_fin <- tudo_fin %>% dplyr::filter(FAIXA_POP == "G")

cor.test(faixa_a_fin$RPM_MEDIA, faixa_a_fin$IDEB_MEDIA, method = "pearson")
cor.test(faixa_b_fin$RPM_MEDIA, faixa_b_fin$IDEB_MEDIA, method = "pearson")
cor.test(faixa_c_fin$RPM_MEDIA, faixa_c_fin$IDEB_MEDIA, method = "pearson")
cor.test(faixa_d_fin$RPM_MEDIA, faixa_d_fin$IDEB_MEDIA, method = "pearson")
cor.test(faixa_e_fin$RPM_MEDIA, faixa_e_fin$IDEB_MEDIA, method = "pearson")
cor.test(faixa_f_fin$RPM_MEDIA, faixa_f_fin$IDEB_MEDIA, method = "pearson")
cor.test(faixa_g_fin$RPM_MEDIA, faixa_g_fin$IDEB_MEDIA, method = "pearson")

######### Graficos


ggplotly(
  ideb_faixa_fin %>%
    ggplot(aes(x=ANO_IDEB, y=IDEB_MEDIA, group=FAIXA_POP, color=FAIXA_POP)) +
    geom_line() + 
    scale_color_viridis(discrete = TRUE) +
    ggtitle("Ideb médio de 2013 a 2019", 
            subtitle = "Para Anos Finais do Fundamental por Faixa População") +
    ylab("Notas do Ideb") + 
    xlab("Ano") + 
    theme_bw()
)

ggplotly(
  valor_faixa_fin %>%
    ggplot(aes(x=ANO_REPASSE, y=RPM_MEDIA, group=FAIXA_POP, color=FAIXA_POP)) +
    geom_line() + 
    scale_color_viridis(discrete = TRUE) +
    ggtitle("Repasse médio por matricula de 2012 a 2018", 
            subtitle = "Para Anos Finais do Fundamental por Faixa População") +
    ylab("Valor médio do repasse") + 
    xlab("Ano") + 
    theme_bw()
)


ggscatter(anos_finais, x = "IDEB_NOTA", y = "REPASSE_MATRICULA", 
          add = "reg.line", conf.int = TRUE, 
          cor.coef = TRUE, cor.method = "pearson",
          xlab = "Notas médias do Ideb", ylab = "Valor repassado por matricula médio")

ggscatter(tudo_fin, x = "IDEB_MEDIA", y = "RPM_MEDIA", 
          add = "reg.line", conf.int = TRUE, 
          cor.coef = TRUE, cor.method = "pearson",
          xlab = "Notas médias do Ideb", ylab = "Valor repassado por matricula médio")

