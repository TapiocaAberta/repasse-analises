library(dplyr)

### Tratamento de dados

ideb_fundeb <- read.csv(file = "../../data/ideb_repasse/fundeb/ideb_repasse-fundeb.csv")

ideb_fundeb <- ideb_fundeb %>% 
  dplyr::filter(ideb_fundeb$ANO_IDH > 0 & ideb_fundeb$REPASSE_MATRICULA > 0) %>% 
  na.omit()

### Convertendo para factor

ideb_fundeb$ANO_IDEB = as.factor(ideb_fundeb$ANO_IDEB)
ideb_fundeb$ANO_IDH = as.factor(ideb_fundeb$ANO_IDH)
ideb_fundeb$ANO_REPASSE = as.factor(ideb_fundeb$ANO_REPASSE)
ideb_fundeb$ANO_MATRICULA = as.factor(ideb_fundeb$ANO_MATRICULA)

ideb_fundeb$PERIODO = as.factor(ideb_fundeb$PERIODO)
ideb_fundeb$UF = as.factor(ideb_fundeb$UF)
ideb_fundeb$MUNICIPIO = as.factor(ideb_fundeb$MUNICIPIO)
ideb_fundeb$REDE = as.factor(ideb_fundeb$REDE)
ideb_fundeb$CODIGO_MUNICIPIO = as.factor(ideb_fundeb$CODIGO_MUNICIPIO)
ideb_fundeb$FAIXA_POP = as.factor(ideb_fundeb$FAIXA_POP)

### Separando os dados por Periodo

anos_iniciais <- ideb_fundeb %>% dplyr::filter(PERIODO =="FUNDAMENTAL_ANOS_INICIAIS" & IDH_EDUCACAO > 0)
anos_finais <- ideb_fundeb %>% dplyr::filter(PERIODO=="FUNDAMENTAL_ANOS_FINAIS" & IDH_EDUCACAO > 0)
ensino_medio <- ideb_fundeb %>% dplyr::filter(PERIODO=="ENSINO_MEDIO" & IDH_EDUCACAO > 0)
fundamental <- ideb_fundeb %>% dplyr::filter(PERIODO =="FUNDAMENTAL_ANOS_INICIAIS" | PERIODO=="FUNDAMENTAL_ANOS_FINAIS")

ideb_fundeb_fundamental_media <- fundamental %>% 
  group_by(ANO_IDEB,
           ANO_IDH,
           ANO_MATRICULA,
           ANO_REPASSE,
           CODIGO_MUNICIPIO,
           IDHM,
           IDH_EDUCACAO,
           IDH_LONGEVIDADE,
           IDH_RENDA, 
           MUNICIPIO,
           POPULACAO, 
           REGIAO, 
           UF, 
           VALOR_REPASSADO_EDUCACAO, 
           FAIXA_POP) %>% 
  summarise(MATRICULA_SUM = sum(MATRICULA), 
            REPASSE_MATRICULA = VALOR_REPASSADO_EDUCACAO/MATRICULA_SUM, 
            IDEB_MEDIA = mean(IDEB_NOTA)) %>%
  distinct()
