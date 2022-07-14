load(file = "../../data/ideb_repasse/ensino_medio.RData")

load(file = "../../data/ideb_repasse/ensino_medio_out.RData")
ensino_medio = data.frame(ensino_medio_out)

library(ggplot2)
library(viridis)
library(plotly)

######### Cor

col_num <- c(6:10, 17, 19)
data_num_em <- ensino_medio[, col_num]

correlations_em <- cor(data_num_em)
corrplot(correlations_em, method = "circle")

######### Tratamento

ensino_medio_a <- ensino_medio %>% dplyr::filter(FAIXA_POP == "A")
ensino_medio_b <- ensino_medio %>% dplyr::filter(FAIXA_POP == "B")
ensino_medio_c <- ensino_medio %>% dplyr::filter(FAIXA_POP == "C")
ensino_medio_d <- ensino_medio %>% dplyr::filter(FAIXA_POP == "D")
ensino_medio_e <- ensino_medio %>% dplyr::filter(FAIXA_POP == "E")
ensino_medio_f <- ensino_medio %>% dplyr::filter(FAIXA_POP == "F")
ensino_medio_g <- ensino_medio %>% dplyr::filter(FAIXA_POP == "G")

# Médias

ideb_faixa_em <- ensino_medio %>% 
  group_by(FAIXA_POP, ANO_IDEB) %>% 
  summarise_at(vars(IDEB_NOTA), list(IDEB_MEDIA = mean));

valor_faixa_em <- ensino_medio %>% 
  group_by(FAIXA_POP, ANO_REPASSE) %>% 
  summarise_at(vars(REPASSE_MATRICULA), list(RPM_MEDIA = mean));

tudo_em <- data.frame(FAIXA_POP  = ideb_faixa_em$FAIXA_POP, 
                       IDEB_MEDIA = ideb_faixa_em$IDEB_MEDIA, 
                       ANO_IDEB = ideb_faixa_em$ANO_IDEB, 
                       ANO_REPASSE = valor_faixa_em$ANO_REPASSE,
                       RPM_MEDIA = valor_faixa_em$RPM_MEDIA)

######### Primeira análise

summary(ensino_medio)

pop_total <- dplyr::n_distinct(ensino_medio$CODIGO_MUNICIPIO)

pop_a <- dplyr::n_distinct(ensino_medio_a$CODIGO_MUNICIPIO)
pop_b <- dplyr::n_distinct(ensino_medio_b$CODIGO_MUNICIPIO)
pop_c <- dplyr::n_distinct(ensino_medio_c$CODIGO_MUNICIPIO)
pop_d <- dplyr::n_distinct(ensino_medio_d$CODIGO_MUNICIPIO)
pop_e <- dplyr::n_distinct(ensino_medio_e$CODIGO_MUNICIPIO)
pop_f <- dplyr::n_distinct(ensino_medio_f$CODIGO_MUNICIPIO)
pop_g <- dplyr::n_distinct(ensino_medio_g$CODIGO_MUNICIPIO)

porcentagem(pop_total, pop_a)
porcentagem(pop_total, pop_b)
porcentagem(pop_total, pop_c)
porcentagem(pop_total, pop_d)
porcentagem(pop_total, pop_e)
porcentagem(pop_total, pop_f)
porcentagem(pop_total, pop_g)


######### Correlação

# Todos

cor.test(ensino_medio$REPASSE_MATRICULA, ensino_medio$IDEB_NOTA, method = "pearson")

# Por Faixa sem média

cor.test(ensino_medio_a$REPASSE_MATRICULA, ensino_medio_a$IDEB_NOTA, method = "pearson")
cor.test(ensino_medio_b$REPASSE_MATRICULA, ensino_medio_b$IDEB_NOTA, method = "pearson")
cor.test(ensino_medio_c$REPASSE_MATRICULA, ensino_medio_c$IDEB_NOTA, method = "pearson")
cor.test(ensino_medio_d$REPASSE_MATRICULA, ensino_medio_d$IDEB_NOTA, method = "pearson")
cor.test(ensino_medio_e$REPASSE_MATRICULA, ensino_medio_e$IDEB_NOTA, method = "pearson")
cor.test(ensino_medio_f$REPASSE_MATRICULA, ensino_medio_f$IDEB_NOTA, method = "pearson")
cor.test(ensino_medio_g$REPASSE_MATRICULA, ensino_medio_g$IDEB_NOTA, method = "pearson")

# Todos Media
cor.test(tudo_em$RPM_MEDIA, tudo_em$IDEB_MEDIA, method = "pearson")

# Por Faixa média

faixa_a_em <- tudo_em %>% dplyr::filter(FAIXA_POP == "A")
faixa_b_em <- tudo_em %>% dplyr::filter(FAIXA_POP == "B")
faixa_c_em <- tudo_em %>% dplyr::filter(FAIXA_POP == "C")
faixa_d_em <- tudo_em %>% dplyr::filter(FAIXA_POP == "D")
faixa_e_em <- tudo_em %>% dplyr::filter(FAIXA_POP == "E")
faixa_f_em <- tudo_em %>% dplyr::filter(FAIXA_POP == "F")
faixa_g_em <- tudo_em %>% dplyr::filter(FAIXA_POP == "G")

cor.test(faixa_a_em$RPM_MEDIA, faixa_a_em$IDEB_MEDIA, method = "pearson")
cor.test(faixa_b_em$RPM_MEDIA, faixa_b_em$IDEB_MEDIA, method = "pearson")
cor.test(faixa_c_em$RPM_MEDIA, faixa_c_em$IDEB_MEDIA, method = "pearson")
cor.test(faixa_d_em$RPM_MEDIA, faixa_d_em$IDEB_MEDIA, method = "pearson")
cor.test(faixa_e_em$RPM_MEDIA, faixa_e_em$IDEB_MEDIA, method = "pearson")
cor.test(faixa_f_em$RPM_MEDIA, faixa_f_em$IDEB_MEDIA, method = "pearson")
cor.test(faixa_g_em$RPM_MEDIA, faixa_g_em$IDEB_MEDIA, method = "pearson")

######### Graficos


ggplotly(
  ideb_faixa_em %>%
    ggplot(aes(x=ANO_IDEB, y=IDEB_MEDIA, group=FAIXA_POP, color=FAIXA_POP)) +
    geom_line() + 
    scale_color_viridis(discrete = TRUE) +
    ggtitle("Ideb médio de 2017 e 2019", 
            subtitle = "Para Ensino Médio por Faixa População") +
    ylab("Notas do Ideb") + 
    xlab("Ano") + 
    theme_bw()
)

ggplotly(
  valor_faixa_em %>%
    ggplot(aes(x=ANO_REPASSE, y=RPM_MEDIA, group=FAIXA_POP, color=FAIXA_POP)) +
    geom_line() + 
    scale_color_viridis(discrete = TRUE) +
    ggtitle("Repasse médio por matricula em 2017 e 2019", 
            subtitle = "Para Ensino Médio por Faixa População") +
    ylab("Valor médio do repasse") + 
    xlab("Ano") + 
    theme_bw()
)


ggscatter(ensino_medio, x = "IDEB_NOTA", y = "REPASSE_MATRICULA", 
          add = "reg.line", conf.int = TRUE, 
          cor.coef = TRUE, cor.method = "pearson",
          xlab = "Notas médias do Ideb", ylab = "Valor repassado por matricula médio")

ggscatter(tudo_em, x = "IDEB_MEDIA", y = "RPM_MEDIA", 
          add = "reg.line", conf.int = TRUE, 
          cor.coef = TRUE, cor.method = "pearson",
          xlab = "Notas médias do Ideb", ylab = "Valor repassado por matricula médio")

