m = []

open("./input/day-11.txt", "r") do f
    global m = readlines(f)
    close(f)
end

println(m)