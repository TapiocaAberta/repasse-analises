load(file = "../../data/ideb_repasse/ideb_media.RData")

### IDH

quantile(ideb_fundeb_media$IDHM)

### Corplot

col_num <- c(6:9, 14, 16:18)
data_num_ini <- ideb_fundeb_fundamental_media[, col_num]

correlations_ini <- cor(data_num_ini)
corrplot(correlations_ini, method = "circle")

cor.test(ideb_fundeb_fundamental_media$REPASSE_MATRICULA, ideb_fundeb_fundamental_media$IDEB_MEDIA, method = "pearson")

## SP

sp <- ideb_fundeb_fundamental_media %>% dplyr::filter(UF == "SP")
cor.test(sp$REPASSE_MATRICULA, sp$IDEB_MEDIA, method = "pearson")

sp_num_ini <- sp[, col_num]

sp_ini <- cor(sp_num_ini)
corrplot(sp_ini, method = "circle")

## + 100.000 hab

hab_100 <- ideb_fundeb_fundamental_media %>% dplyr::filter(FAIXA_POP == "F" | FAIXA_POP == "G")
cor.test(hab_100$REPASSE_MATRICULA, hab_100$IDEB_MEDIA, method = "pearson")

hab_100_num_ini <- hab_100[, col_num]

hab100_ini <- cor(hab_100_num_ini)
corrplot(hab100_ini, method = "circle")
``