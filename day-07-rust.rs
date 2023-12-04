//bin/true; rustc -o "/tmp/$0.bin" 1>&2 "$0" && "/tmp/$0.bin" "$@"; exit $?

use std::fs::read_to_string;

fn main() {
    let m = read_to_string("./input/day-07.txt").unwrap();

    println!("{}", m);
}
