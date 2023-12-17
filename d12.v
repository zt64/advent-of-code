module main

import os

fn main() {
	lines := os.read_lines('./input/d12.txt')!

	arrangements := 0
	for line in lines {
		a, b := line.split_once(' ')?
		c := b.split(',').map(it.int())
		println(a)
		println(c)
	}

	println('arrangements: ${arrangements}')
}
