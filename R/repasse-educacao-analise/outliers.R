load(file = "../../data/ideb_repasse/todos_anos.RData")

ideb_repasse <- ideb_fundeb_media;

outliers <- boxplot(ideb_repasse$REPASSE_MATRICULA, plot=FALSE)$out

length(outliers)
length(ideb_repasse$REPASSE_MATRICULA)

outliers

ideb_repasse_out <-ideb_repasse
ideb_repasse_out <- ideb_repasse_out[-which(ideb_repasse_out$REPASSE_MATRICULA %in% outliers),]

anos_iniciais_out <- ideb_repasse_out %>% dplyr::filter(PERIODO =="FUNDAMENTAL_ANOS_INICIAIS" & IDH_EDUCACAO > 0)
anos_finais_out   <- ideb_repasse_out %>% dplyr::filter(PERIODO=="FUNDAMENTAL_ANOS_FINAIS" & IDH_EDUCACAO > 0)
ensino_medio_out  <- ideb_repasse_out %>% dplyr::filter(PERIODO=="ENSINO_MEDIO" & IDH_EDUCACAO > 0)

save(ideb_repasse_out, file = "../../data/ideb_repasse/todos_anos_out.RData")
save(anos_iniciais_out, file = "../../data/ideb_repasse/anos_iniciais_out.RData")
save(anos_finais_out,  file = "../../data/ideb_repasse/anos_finais_out.RData")
save(ensino_medio_out, file = "../../data/ideb_repasse/ensino_medio_out.RData")