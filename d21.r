f <- read.delim("./input/d21.txt", sep="", header=FALSE)

print(grepl('S', f, fixed=TRUE, value=FALSE))
print(f)