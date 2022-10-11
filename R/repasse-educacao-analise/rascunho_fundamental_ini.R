load(file = "../../data/ideb_repasse/fundeb/anos_iniciais.RData")
#load(file = "../../data/ideb_repasse/anos_iniciais_out.RData")
#anos_iniciais = data.frame(anos_iniciais_out)

library(ggplot2)
library(viridis)
library(plotly)

###### ANOS INICIAIS ######

summary(anos_iniciais)

col_num <- c("IDEB_NOTA", "IDHM", "IDH_EDUCACAO", "IDH_LONGEVIDADE", 
             "IDH_RENDA", "REPASSE_MATRICULA", "POPULACAO", "MATRICULA")
data_num_ini <- anos_iniciais[, col_num]

correlations_ini <- cor(data_num_ini[, c("IDEB_NOTA", "REPASSE_MATRICULA")])
corrplot(correlations_ini, method = "number")

######### Tratamento

anos_iniciais_a <- anos_iniciais %>% dplyr::filter(FAIXA_POP == "A")
anos_iniciais_b <- anos_iniciais %>% dplyr::filter(FAIXA_POP == "B")
anos_iniciais_c <- anos_iniciais %>% dplyr::filter(FAIXA_POP == "C")
anos_iniciais_d <- anos_iniciais %>% dplyr::filter(FAIXA_POP == "D")
anos_iniciais_e <- anos_iniciais %>% dplyr::filter(FAIXA_POP == "E")
anos_iniciais_f <- anos_iniciais %>% dplyr::filter(FAIXA_POP == "F")
anos_iniciais_g <- anos_iniciais %>% dplyr::filter(FAIXA_POP == "G")

# Médias

ideb_faixa_ini <- anos_iniciais %>% 
  group_by(FAIXA_POP, ANO_IDEB) %>% 
  summarise_at(vars(IDEB_NOTA), list(IDEB_MEDIA = mean));

valor_faixa_ini <- anos_iniciais %>% 
  group_by(FAIXA_POP, ANO_REPASSE) %>% 
  summarise_at(vars(REPASSE_MATRICULA), list(RPM_MEDIA = mean));

tudo_ini <- data.frame(FAIXA_POP  = ideb_faixa_ini$FAIXA_POP, 
                       IDEB_MEDIA = ideb_faixa_ini$IDEB_MEDIA, 
                       ANO_IDEB = ideb_faixa_ini$ANO_IDEB, 
                       ANO_REPASSE = valor_faixa_ini$ANO_REPASSE,
                       RPM_MEDIA = valor_faixa_ini$RPM_MEDIA)

######### Primeira análise

summary(anos_iniciais)
dplyr::n_distinct(anos_iniciais$CODIGO_MUNICIPIO)

dplyr::n_distinct(anos_iniciais_a$CODIGO_MUNICIPIO)
dplyr::n_distinct(anos_iniciais_b$CODIGO_MUNICIPIO)
dplyr::n_distinct(anos_iniciais_c$CODIGO_MUNICIPIO)
dplyr::n_distinct(anos_iniciais_d$CODIGO_MUNICIPIO)
dplyr::n_distinct(anos_iniciais_e$CODIGO_MUNICIPIO)
dplyr::n_distinct(anos_iniciais_f$CODIGO_MUNICIPIO)
dplyr::n_distinct(anos_iniciais_g$CODIGO_MUNICIPIO)

######### Correlação

# Todos

cor.test(anos_iniciais$REPASSE_MATRICULA, anos_iniciais$IDEB_NOTA, method = "pearson")

# Por Faixa sem média

cor.test(anos_iniciais_a$REPASSE_MATRICULA, anos_iniciais_a$IDEB_NOTA, method = "pearson")
cor.test(anos_iniciais_b$REPASSE_MATRICULA, anos_iniciais_b$IDEB_NOTA, method = "pearson")
cor.test(anos_iniciais_c$REPASSE_MATRICULA, anos_iniciais_c$IDEB_NOTA, method = "pearson")
cor.test(anos_iniciais_d$REPASSE_MATRICULA, anos_iniciais_d$IDEB_NOTA, method = "pearson")
cor.test(anos_iniciais_e$REPASSE_MATRICULA, anos_iniciais_e$IDEB_NOTA, method = "pearson")
cor.test(anos_iniciais_f$REPASSE_MATRICULA, anos_iniciais_f$IDEB_NOTA, method = "pearson")
cor.test(anos_iniciais_g$REPASSE_MATRICULA, anos_iniciais_g$IDEB_NOTA, method = "pearson")

data_scale <- scale(data_num_ini)
chart.Correlation(data_scale, histogram=TRUE)

# Todos Media
cor.test(tudo_ini$RPM_MEDIA, tudo_ini$IDEB_MEDIA, method = "pearson")

# Por Faixa média

faixa_a_ini <- tudo_ini %>% dplyr::filter(FAIXA_POP == "A")
faixa_b_ini <- tudo_ini %>% dplyr::filter(FAIXA_POP == "B")
faixa_c_ini <- tudo_ini %>% dplyr::filter(FAIXA_POP == "C")
faixa_d_ini <- tudo_ini %>% dplyr::filter(FAIXA_POP == "D")
faixa_e_ini <- tudo_ini %>% dplyr::filter(FAIXA_POP == "E")
faixa_f_ini <- tudo_ini %>% dplyr::filter(FAIXA_POP == "F")
faixa_g_ini <- tudo_ini %>% dplyr::filter(FAIXA_POP == "G")

cor.test(faixa_a_ini$RPM_MEDIA, faixa_a_ini$IDEB_MEDIA, method = "pearson")
cor.test(faixa_b_ini$RPM_MEDIA, faixa_b_ini$IDEB_MEDIA, method = "pearson")
cor.test(faixa_c_ini$RPM_MEDIA, faixa_c_ini$IDEB_MEDIA, method = "pearson")
cor.test(faixa_d_ini$RPM_MEDIA, faixa_d_ini$IDEB_MEDIA, method = "pearson")
cor.test(faixa_e_ini$RPM_MEDIA, faixa_e_ini$IDEB_MEDIA, method = "pearson")
cor.test(faixa_f_ini$RPM_MEDIA, faixa_f_ini$IDEB_MEDIA, method = "pearson")
cor.test(faixa_g_ini$RPM_MEDIA, faixa_g_ini$IDEB_MEDIA, method = "pearson")

######### Graficos


ggplotly(
  ideb_faixa_ini %>%
    ggplot(aes(x=ANO_IDEB, y=IDEB_MEDIA, group=FAIXA_POP, color=FAIXA_POP)) +
    geom_line() + 
    scale_color_viridis(discrete = TRUE) +
    ggtitle("Ideb de 2013 a 2019", 
            subtitle = "Para Anos Iniciais do Fundamental por Faixa População") +
    ylab("Notas do Ideb") + 
    xlab("Ano") + 
    theme_bw()
)

ggplotly(
  valor_faixa_ini %>%
    ggplot(aes(x=ANO_REPASSE, y=RPM_MEDIA, group=FAIXA_POP, color=FAIXA_POP)) +
    geom_line() + 
    scale_color_viridis(discrete = TRUE) +
    ggtitle("Repasse médio por matricula de 2012 a 2018", 
            subtitle = "Para Anos Iniciais do Fundamental por Faixa População") +
    ylab("Valor médio do repasse") + 
    xlab("Ano") + 
    theme_bw()
)

ggscatter(anos_iniciais, x = "IDEB_NOTA", y = "REPASSE_MATRICULA", 
          add = "reg.line", conf.int = TRUE, 
          cor.coef = TRUE, cor.method = "pearson",
          xlab = "Notas médias do Ideb", ylab = "Valor repassado por matricula médio")

ggscatter(tudo_ini, x = "IDEB_MEDIA", y = "RPM_MEDIA", 
          add = "reg.line", conf.int = TRUE, 
          cor.coef = TRUE, cor.method = "pearson",
          xlab = "Notas médias do Ideb", ylab = "Valor repassado por matricula médio")

