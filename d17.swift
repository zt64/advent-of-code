import Foundation

enum Direction: Int, CaseIterable {
    case up = 0
    case down = 1
    case left = 2
    case right = 3
}

enum Turn: CaseIterable {
    case right, left
}

struct Crucible: Comparable {
    var x: Int
    var y: Int
    var direction: Direction
    var totalHeatLoss: Int = 0
    var path: [(Int, Int)] = []

    mutating func move() {
        switch direction {
            case .up: y += 1
            case .down: y -= 1
            case .left: x -= 1
            case .right: x += 1
        }
        path.append((y, x))
    }

    mutating func turn(_ turn: Turn) {
        direction = switch (direction, turn) {
            case (.up, .right): .right
            case (.up, .left): .left
            case (.down, .right): .left
            case (.down, .left): .right
            case (.left, .right): .up
            case (.left, .left): .down
            case (.right, .right): .down
            case (.right, .left): .up
        }
    }

    // assumes array is arranged [y][x] and is rectangular (all rows equal length)
    func isWithin<T>(_ grid: [[T]]) -> Bool {
        return y >= 0 && y < grid.count && x >= 0 && x < grid[0].count
    }

    static func < (lhs: Crucible, rhs: Crucible) -> Bool {
        return lhs.totalHeatLoss < rhs.totalHeatLoss
    }

    static func == (lhs: Crucible, rhs: Crucible) -> Bool {
        return lhs.x == rhs.x && lhs.y == rhs.y && lhs.direction == rhs.direction
    }
}

struct Point: Hashable {
    var x: Int
    var y: Int
    var heat: Int

    static func == (lhs: Point, rhs: Point) -> Bool {
        return lhs.x == rhs.x && lhs.y == rhs.y && lhs.heat == rhs.heat
    }

    func hash(into hasher: inout Hasher) {
        hasher.combine(x)
        hasher.combine(y)
    }
}

// https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Pseudocode
func shortest_path(_ city: [[Int]], _ startX: Int, _ startY: Int) -> Int {
    let source = Point(x: startX, y: startY, heat: city[startY][startX])
    // var dist: [Point: Int] = [:]
    // var prev: [Point: Point]  = [:]

    // for y in 0..<map.count {
    //     for x in 0..<map[y].count {
    //         let point = Point(x: x, y: y, heat: map[y][x])

    //         dist[point] = Int.max
    //         prev[point] = nil

    //         q.insert(point)
    //     }
    // }

    // dist[source] = 0

    // while !q.isEmpty {
    //     let u = dist.min { a, b in a.value < b.value }!.key

    //     print("u: \(u)")
    //     q.remove(u)

    //     for v in q {
    //         let alt = dist[u]! + 1
    //         if alt < dist[v]! {
    //             dist[v] = alt
    //             prev[v] = u
    //         }
    //     }
    // }
    var result: Int = Int.max
    let destY = 0
    let destX = city[0].count - 1
    var queue = Heap<Crucible> { l, r in l.totalHeatLoss < r.totalHeatLoss }
    queue.insert(Crucible(x: 0, y: city.count - 1, direction: .right))
    queue.insert(Crucible(x: 0, y: city.count - 1, direction: .down))

    var bestHeatLoss: [[[Int]]] = []
    for y in 0..<city.count {
        bestHeatLoss.append(Array(repeating: Array(repeating: Int.max, count: Direction.allCases.count), count: city[y].count))
    }

    while let currentPoint = queue.popTop() {

        // did we reach the destination? and if so, is this a better answer than the previous ones we've seen?
        if currentPoint.y == destY &&
           currentPoint.x == destX {
            result = min(result, currentPoint.totalHeatLoss)
            break
        }

        // where can we go from here? we have to turn right or left.
        for direction in Turn.allCases {
            var next = currentPoint
            next.turn(direction)
            var turnRange = 0..<3  // default for part 1
            // if puzzle == .partTwo {
            //     for _ in 0..<3 {
            //         next.move()
            //         if !next.isWithin(city) {
            //             break
            //         }
            //         next.totalHeatLoss += city[next.y][next.x]
            //     }
            //     turnRange = 3..<10
            // }
            if next.isWithin(city) {
                for _ in turnRange {
                    next.move()
                    if !next.isWithin(city) {
                        break
                    }
                    next.totalHeatLoss += city[next.y][next.x]
                    if next.totalHeatLoss < bestHeatLoss[next.y][next.x][next.direction.rawValue] {
                        bestHeatLoss[next.y][next.x][next.direction.rawValue] = next.totalHeatLoss
                        queue.insert(next)
                    }
                }
            }
        }
    }
    return result
}

let path = "./input/d17.txt"

do {
    let data = try NSString(contentsOfFile: path, encoding: String.Encoding.ascii.rawValue)
    let city = data.components(separatedBy: "\n").map { Array($0).map { $0.hexDigitValue! } }
    let startX = 0
    let startY = 0

    print("Part 1: \(shortest_path(city, startX, startY))")
} catch {
    print("Error reading file: \(error)")
}

class Heap<T:Comparable> {
    typealias HeapComparator<T:Comparable> = (_ l:T,_ r:T) -> Bool
    var heap = [T]()
    var count:Int {
        get {
            heap.count
        }
    }

    var comparator:HeapComparator<T>


    /// bubbleUp is called after appending the item to the end of the queue.  Depending on the comparator,
    /// it will bubbleUp to its approriate spot
    /// - Parameter idx: Index to bubble up.  This starts after insert with last index being passed in.
    private func bubbleUp(idx:Int) {
        let parent = (idx - 1) / 2

        if idx <= 0 {
            return
        }

        if comparator(heap[idx], heap[parent]) {
            heap.swapAt(parent, idx)
            bubbleUp(idx: parent)
        }
    }


    /// Heapify the current heap.  This method walks down the children and rearranges them in comparator order.
    /// - Parameter idx: index to heapify.
    private func heapify(_ idx:Int) {
        var left = idx * 2 + 1
        var right = idx * 2 + 2

        var comp = idx

        if count > left && comparator(heap[left], heap[comp]) {
            comp = left
        }
        if count > right && comparator(heap[right], heap[comp]) {
            comp = right
        }
        if comp != idx {
            heap.swapAt(comp, idx)
            heapify(comp)
        }
    }

    init(comparator:@escaping HeapComparator<T>) {
        self.comparator = comparator
    }


    /// Insert item into the heap.  This walks up the parents. This is a O(log n) operation
    /// - Parameter item: item that is comparable.
    func insert(_ item:T) {
        heap.append(item)
        bubbleUp(idx: count-1)
    }


    /// Get the top item in the heap based on comparator. This is a 0(1) operation
    /// - Returns: top item or nil if empty.
    func getTop() -> T? {
        return heap.first
    }


    /// Remove the top item.  This is a O(log n) operation
    /// - Returns: returns top item based on comparator or nil if empty.
    func popTop() -> T? {
        var item:T? = heap.first
        if count > 1 {
            // set the top to the last element and heapify
            // this means we can remove the last after "poping" the first.
            heap[0] = heap[count-1]
            heap.removeLast()
            heapify(0)
        }
        else if count == 1{
            heap.removeLast()
        }
        else {
            return nil
        }

        return item
    }
}