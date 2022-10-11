load(file = "../../data/ideb_repasse/fundeb/anos_iniciais.RData")
load(file = "../../data/ideb_repasse/fundeb/anos_finais.RData")
fundamental <- rbind(anos_finais, anos_iniciais)

cols <- c("IDEB_NOTA", "IDHM", "IDH_EDUCACAO", "IDH_LONGEVIDADE", 
          "IDH_RENDA", "REPASSE_MATRICULA", "POPULACAO", "MATRICULA")

data_fundamental <- fundamental[, cols]

#CORS
cor_ideb_fundeb <- cor(data_fundamental[, c("IDEB_NOTA", "REPASSE_MATRICULA")], method = "pearson")
corrplot(cor_ideb_fundeb, method="number")
cor_ideb_idhs <- cor(data_fundamental[, c("IDEB_NOTA", "IDHM", "IDH_EDUCACAO", "IDH_LONGEVIDADE", 
                                          "IDH_RENDA")], method = "pearson")
corrplot(cor_ideb_idhs, method="number")
