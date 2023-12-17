lines = []

open("./input/d09.txt", "r") do f
    global lines = map(x -> map(y -> parse(Int, y), split(x, " ")), readlines(f))
    close(f)
end

diffs_of_arr(arr) = map(x -> arr[x+1] - arr[x], 1:length(arr)-1)

total = 0

for arr in lines
    local diffs = diffs_of_arr(arr)
    local num = 0

    while true
        _diffs = diffs_of_arr(diffs)
        if all(diff -> diff == 0, _diffs)
            num += last(diffs) + last(arr)
            break
        else
            num += last(diffs)
            diffs = _diffs
        end
    end

    global total += num
end

println("Part 1: $total")

total = 0

for arr in map(reverse, lines)
    local diffs = diffs_of_arr(arr)
    local num = 0

    while true
        _diffs = diffs_of_arr(diffs)
        if all(diff -> diff == 0, _diffs)
            num += last(diffs) + last(arr)
            break
        else
            num += last(diffs)
            diffs = _diffs
        end
    end

    global total += num
end

println("Part 2: $total")