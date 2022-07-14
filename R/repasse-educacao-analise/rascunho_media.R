load(file = "../../data/ideb_repasse/ideb_media.RData")

### IDH

quantile(ideb_fundeb_media$IDHM)

### Corplot

col_num <- c(6:9, 14, 16:18)
data_num_ini <- ideb_repasse_out[, col_num]

correlations_ini <- cor(data_num_ini)
corrplot(correlations_ini, method = "circle")


cor.test(ideb_repasse_out$REPASSE_MATRICULA, ideb_repasse_out$IDEB_MEDIA, method = "pearson")

sp <- ideb_repasse_out %>% dplyr::filter(UF == "SP")
cor.test(sp$REPASSE_MATRICULA, sp$IDEB_MEDIA, method = "pearson")
