module minky;

import std.algorithm;
import std.array;
import std.conv;
import std.file;
import std.range;
import std.stdio;
import std.string;

void main() {
    auto lines = splitLines(readText("./input/d04.txt"));
    int points = 0;
    auto cards = new int[lines.length];
    cards[] = 1;

    foreach (i, line; lines) {
        long startIndex = indexOf(line, ":") + 2;
        long dividerIndex = indexOf(line, '|');

        auto winning = line[startIndex .. dividerIndex].slide(2, 3).map!(a => to!string(a))
            .array
            .sort!();
        auto hand = line[dividerIndex + 2 .. $].slide(2, 3).map!(a => to!string(a))
            .array
            .sort!();

        auto matches = setIntersection(winning, hand).walkLength;

        if (matches) {
            points += 1 << (matches - 1); // Part 1: earn 2^(matching-1) points
            cards[i + 1 .. i + matches + 1] += cards[i];
        }
    }

    writeln("Part 1: " ~ points.to!string ~ " points");
    writeln("Part 2: " ~ sum(cards).to!string ~ " cards");
}
