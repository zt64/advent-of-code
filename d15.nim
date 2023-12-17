import std/tables
import options
import strutils

let line = readFile("./input/d15.txt")

echo line.split({','})

func hash(str: string): int =
    var curr = 0
    for c in str:
        curr += int(c)
        curr *= 17
        curr = curr mod 256
    curr

var boxes = initTable[int, option[string]]()

for i in 0..256:
    boxes[i] =


var t = 0

for m in line.split({','}):
    t += hash(m)

echo t