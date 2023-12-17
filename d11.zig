const std = @import("std");
const ArrayList = std.ArrayList;
const data = @embedFile("./input/d11.txt");

pub fn main() !void {
    var it = std.mem.split(u8, data, "\n");
    var m = ArrayList(ArrayList(u8)).init(std.heap.page_allocator);

    defer m.deinit();

    while (it.next()) |line| {
        var guh = ArrayList(u8).init(std.heap.page_allocator);
        try guh.appendSlice(line);
        try m.append(guh);
    }
    var n = try m.clone();
    // loop through rows
    var insertedRow: usize = 0;
    for (m.items, 0..) |row, i| {
        if (std.mem.allEqual(u8, row.items, '.')) {
            try n.insert(i + insertedRow, row);
            insertedRow += 2;
        }
    }

    // loop through columns
    var insertedCol: usize = 0;
    for (0..m.items[0].items.len) |char| {
        var allEmpty = true;
        for (m.items) |row| {
            if (row.items[char] != '.') {
                allEmpty = false;
                break;
            }
        }
        if (allEmpty) {
            for (n.items) |row| {
                try row.insert(char + insertedCol, '.');
            }
            insertedCol += 2;
        }
    }

    for (n.items) |row| {
        std.debug.print("{s}\n", .{row});
    }
}
