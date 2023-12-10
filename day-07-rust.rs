//bin/true; rustc -o "/tmp/$0.bin" 1>&2 "$0" && "/tmp/$0.bin" "$@"; exit $?

use std::fmt;
use std::fs::read_to_string;

fn main() {
    let ar = ['A'];
    let binding = read_to_string("./input/day-07.txt").unwrap();
    let lines = binding.lines();
    let a = lines
        .map(|line| line.split_at(line.find(" ").unwrap()))
        .map(|(a, b)| (a, b.trim().parse::<i32>().unwrap()));

    let m = a.map(|(hand, bid)| {
        let card_type = match hand {
            "AAAAA" => Type::FiveOfAKind,
            _ => Type::FiveOfAKind,
        };
        (card_type, bid)
    });

    m.for_each(|(a, b)| println!("Hand: {:?} Bid: {}", a, b));
}

#[derive(Debug)]
enum Type {
    FiveOfAKind,
    FourOfAKind,
    ThreeOfAKind,
    FullHouse,
    TwoPair,
    OnePair,
    HighCard,
}
