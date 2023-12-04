package main

import (
	"log"
	"os"
	"regexp"
	"strconv"
	"strings"
	"unicode/utf8"
)

func main() {
	args := os.Args[1:]

	if len(args) != 1 {
		log.Fatal("Expected one argument (part number)")
	}

	part := args[0]

	if part == "1" {
		part1()
	} else if part == "2" {
		part2()
	} else {
		log.Fatal("Invalid part number")
	}
}

func get_input() []string {
	content, err := os.ReadFile("./input/day-03.txt")
	if err != nil {
		log.Fatal(err)
	}

	lines := strings.Split(string(content), "\n")
	return lines
}

func part1() {
	lines := get_input()
	r := regexp.MustCompile(`\d+`)

	line_len := len(lines[0])
	blank_line := strings.Repeat(".", line_len)
	special_chars := []string{"#", "*", "$", "+", "/", "=", "@", "&", "-", "%"}
	sum := 0

	for i, line := range lines {
		// if line above
		line_above := blank_line
		if i > 0 {
			line_above = lines[i-1]
		}

		// if line below
		line_below := blank_line
		if i < len(lines)-1 {
			line_below = lines[i+1]
		}

		matches := r.FindAllStringSubmatchIndex(line, -1)

		if len(matches) == 0 {
			continue
		}

		for _, match := range matches {
			start := utf8.RuneCountInString(line[:match[0]])
			end := utf8.RuneCountInString(line[:match[1]])
			num, err := strconv.Atoi(line[start:end])

			if err != nil {
				log.Fatal(err)
			}

			padded_start := max(start-1, 0)
			padded_end := min(end+1, line_len)

			padded_above := line_above[padded_start:padded_end]
			padded_str := line[padded_start:padded_end]
			padded_below := line_below[padded_start:padded_end]

			for _, char := range special_chars {
				if strings.ContainsAny(padded_above, char) {
					sum += num
					break
				}

				if strings.ContainsAny(padded_str, char) {
					sum += num
					break
				}

				if strings.ContainsAny(padded_below, char) {
					sum += num
					break
				}
			}
		}
	}

	println("Sum:", sum)
}

func part2() {
	lines := get_input()
	r := regexp.MustCompile(`\d+`)

	line_len := len(lines[0])
	blank_line := strings.Repeat(".", line_len)
	special_chars := []string{"#", "*", "$", "+", "/", "=", "@", "&", "-", "%"}
	sum := 0

	for i, line := range lines {
		// if line above
		line_above := blank_line
		if i > 0 {
			line_above = lines[i-1]
		}

		// if line below
		line_below := blank_line
		if i < len(lines)-1 {
			line_below = lines[i+1]
		}

		matches := r.FindAllStringSubmatchIndex(line, -1)

		if len(matches) == 0 {
			continue
		}

		for _, match := range matches {
			start := utf8.RuneCountInString(line[:match[0]])
			end := utf8.RuneCountInString(line[:match[1]])
			num, err := strconv.Atoi(line[start:end])

			if err != nil {
				log.Fatal(err)
			}

			padded_start := max(start-1, 0)
			padded_end := min(end+1, line_len)

			padded_above := line_above[padded_start:padded_end]
			padded_str := line[padded_start:padded_end]
			padded_below := line_below[padded_start:padded_end]

			for _, char := range special_chars {
				if strings.ContainsAny(padded_above, char) {
					sum += num
					break
				}

				if strings.ContainsAny(padded_str, char) {
					sum += num
					break
				}

				if strings.ContainsAny(padded_below, char) {
					sum += num
					break
				}
			}
		}
	}

	println("Sum:", sum)
}
