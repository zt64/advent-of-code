import sys


def get_num(text):
    num = 0

    for line in text.splitlines():
        numbers = []
        for i in range(len(line)):
            if line[i].isdigit():
                numbers.append(line[i])

        if numbers:
            i = int(numbers[0] + numbers[-1])
            print(line, i)
            num += i

    return num


def part_1():
    with open("input/d01.txt", "r") as file:
        text = file.read()

    print(get_num(text))


def part_2():
    with open("input/d01.txt", "r") as file:
        text = file.read()

    text = text.replace("nine", "9")
    text = text.replace("one", "1")
    text = text.replace("two", "2")
    text = text.replace("eight", "8")
    text = text.replace("seven", "7")
    text = text.replace("six", "6")
    text = text.replace("five", "5")
    text = text.replace("four", "4")
    text = text.replace("three", "3")
    text = text.replace("zero", "0")

    print(get_num(text))


# run part1 if argument is 1, otherwise run part2
if sys.argv[1] == "1":
    part_1()
else:
    part_2()
