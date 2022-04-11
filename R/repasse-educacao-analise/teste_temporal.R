library("rnn")
library("dplyr")

ideb_repasse <- read.csv(file = "../../data/ideb_repasse/ideb_repasse.csv")
ideb_repasse <- na.omit(ideb_repasse)

summary(ideb_repasse)
