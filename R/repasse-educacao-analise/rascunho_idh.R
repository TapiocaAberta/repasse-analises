load(file = "../../data/ideb_repasse/fundeb/todos_anos.RData")

library(ggplot2)
library(viridis)
library(plotly)

######### Tratamento

faixa_a <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "A")
faixa_b <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "B")
faixa_c <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "C")
faixa_d <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "D")
faixa_e <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "E")
faixa_f <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "F")
faixa_g <- ideb_repasse %>% dplyr::filter(FAIXA_POP == "G")

# Todos

cor.test(ideb_repasse$IDEB_NOTA, ideb_repasse$IDHM, method = "pearson")
cor.test(ideb_repasse$IDEB_NOTA, ideb_repasse$IDH_EDUCACAO, method = "pearson")
cor.test(ideb_repasse$IDEB_NOTA, ideb_repasse$IDH_RENDA, method = "pearson")
cor.test(ideb_repasse$IDEB_NOTA, ideb_repasse$IDH_LONGEVIDADE, method = "pearson")

# Por Faixa sem média

cor.test(faixa_a$IDHM, faixa_a$IDEB_NOTA, method = "pearson")
cor.test(faixa_a$IDH_EDUCACAO, faixa_a$IDEB_NOTA, method = "pearson")
cor.test(faixa_a$IDH_RENDA, faixa_a$IDEB_NOTA, method = "pearson")
cor.test(faixa_a$IDH_LONGEVIDADE, faixa_a$IDEB_NOTA, method = "pearson")


cor.test(faixa_b$IDHM, faixa_b$IDEB_NOTA, method = "pearson")
cor.test(faixa_b$IDH_EDUCACAO, faixa_b$IDEB_NOTA, method = "pearson")
cor.test(faixa_b$IDH_RENDA, faixa_b$IDEB_NOTA, method = "pearson")
cor.test(faixa_b$IDH_LONGEVIDADE, faixa_b$IDEB_NOTA, method = "pearson")

cor.test(faixa_c$IDHM, faixa_c$IDEB_NOTA, method = "pearson")
cor.test(faixa_c$IDH_EDUCACAO, faixa_c$IDEB_NOTA, method = "pearson")
cor.test(faixa_c$IDH_RENDA, faixa_c$IDEB_NOTA, method = "pearson")
cor.test(faixa_c$IDH_LONGEVIDADE, faixa_c$IDEB_NOTA, method = "pearson")

cor.test(faixa_d$IDHM, faixa_d$IDEB_NOTA, method = "pearson")
cor.test(faixa_d$IDH_EDUCACAO, faixa_d$IDEB_NOTA, method = "pearson")
cor.test(faixa_d$IDH_RENDA, faixa_d$IDEB_NOTA, method = "pearson")
cor.test(faixa_d$IDH_LONGEVIDADE, faixa_d$IDEB_NOTA, method = "pearson")

cor.test(faixa_e$IDHM, faixa_e$IDEB_NOTA, method = "pearson")
cor.test(faixa_e$IDH_EDUCACAO, faixa_e$IDEB_NOTA, method = "pearson")
cor.test(faixa_e$IDH_RENDA, faixa_e$IDEB_NOTA, method = "pearson")
cor.test(faixa_e$IDH_LONGEVIDADE, faixa_e$IDEB_NOTA, method = "pearson")

cor.test(faixa_f$IDHM, faixa_f$IDEB_NOTA, method = "pearson")
cor.test(faixa_f$IDH_EDUCACAO, faixa_f$IDEB_NOTA, method = "pearson")
cor.test(faixa_f$IDH_RENDA, faixa_f$IDEB_NOTA, method = "pearson")
cor.test(faixa_f$IDH_LONGEVIDADE, faixa_f$IDEB_NOTA, method = "pearson")

cor.test(faixa_g$IDHM, faixa_g$IDEB_NOTA, method = "pearson")
cor.test(faixa_g$IDH_EDUCACAO, faixa_g$IDEB_NOTA, method = "pearson")
cor.test(faixa_g$IDH_RENDA, faixa_g$IDEB_NOTA, method = "pearson")
cor.test(faixa_g$IDH_LONGEVIDADE, faixa_g$IDEB_NOTA, method = "pearson")

### GRATIFO

col_num <- c(7:11, 18, 20)
data_num_fin <- ideb_repasse[, col_num]

correlations_fin <- cor(data_num_fin)
corrplot(correlations_fin, method = "circle")
