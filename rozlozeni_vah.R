data <- read.csv("edgeWeightDistribution.csv", sep=";", header=T)

postscript(file="ewd.ps", encoding="CP1250")

weight = data[[1]]
freq = data[[2]]

plot(weight, freq, col="blue", log="xy", lwd=1, type="l", xlab="váha", ylab=expression("počet výskytů"))

h = max(freq)
v = max(weight)

abline(	h=c(
		seq(0,h/1,h/10), 
		seq(0,h/10,h/100), 
		seq(0,h/100,h/1000), 
		seq(0,h/1000,h/10000), 
		seq(0,h/10000,h/100000)),
		lty=3, col=colors()[440])

abline(	v=c(
		seq(0,v/1,v/10), 
		seq(0,v/10,v/100)),
		lty=3, col=colors()[440])

points(weight, freq, col="blue", lwd=3, type="l")
box()

dev.off()
