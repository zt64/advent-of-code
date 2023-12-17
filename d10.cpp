#include <fstream>
#include <iostream>
#include <map>
#include <queue>
#include <sstream>
#include <string>
#include <vector>

std::vector<std::string> split(const std::string& s, char delim) {
    std::vector<std::string> result;
    std::stringstream ss(s);
    std::string item;

    while (getline(ss, item, delim)) {
        result.push_back(item);
    }

    return result;
}

struct Tile {
    typedef enum {
        VerticalPipe = '|',
        HorizontalPipe = '-',
        NwBend = 'J',
        NeBend = 'L',
        SwBend = '7',
        SeBend = 'F',
        Ground = '.',
        Start = 'S'
    } TileType;

    TileType type;
    int x;
    int y;
    int dist = -1;
};

using namespace std;

// map of directions to their offsets
map<char, pair<int, int>> directions = {
    {'N', {-1, 0}}, {'S', {1, 0}}, {'E', {0, 1}}, {'W', {0, -1}}};

bool isConnecting(Tile tile1, Tile* tile2) {
    if (tile1.type == Tile::VerticalPipe &&
        (tile2->type == Tile::NwBend || tile2->type == Tile::NeBend)) {
        return true;
    }
    if (tile1.type == Tile::HorizontalPipe &&
        (tile2->type == Tile::NwBend || tile2->type == Tile::SwBend)) {
        return true;
    }
    if (tile1.type == Tile::VerticalPipe &&
        (tile2->type == Tile::SwBend || tile2->type == Tile::SeBend)) {
        return true;
    }
    if (tile1.type == Tile::HorizontalPipe &&
        (tile2->type == Tile::NeBend || tile2->type == Tile::SeBend)) {
        return true;
    }
    if (tile1.type == Tile::NwBend && (tile2->type == Tile::VerticalPipe ||
                                       tile2->type == Tile::HorizontalPipe))
        return true;
    if (tile1.type == Tile::NeBend && (tile2->type == Tile::VerticalPipe ||
                                       tile2->type == Tile::HorizontalPipe))
        return true;
    if (tile1.type == Tile::SwBend && (tile2->type == Tile::VerticalPipe ||
                                       tile2->type == Tile::HorizontalPipe))
        return true;
    if (tile1.type == Tile::SeBend && (tile2->type == Tile::VerticalPipe ||
                                       tile2->type == Tile::HorizontalPipe))
        return true;
    return false;
}

bool isValidPos(int i, int j, int n, int m) {
    return (i < 0 || j < 0 || i > n - 1 || j > m - 1) ? false : true;
}

int main() {
    ifstream t("./input/d10.txt");
    string str((istreambuf_iterator<char>(t)), istreambuf_iterator<char>());

    auto lines = split(str, '\n');

    int width = lines[0].size();
    int height = lines.size();

    vector<vector<Tile>> map = vector<vector<Tile>>(height);

    for (int i = 0; i < height; i++) {
        auto vec = vector<Tile>(width);
        for (int j = 0; j < width; j++) {
            map[i].push_back(
                Tile{.type = static_cast<Tile::TileType>(lines[i][j]),
                     .x = j,
                     .y = i});
        }
    }

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            auto m = map[i][j];
            cout << static_cast<char>(map[i][j].type);
        }
        cout << endl;
    }

    cout << endl;

    queue<Tile*> tile_queue;

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (map[i][j].type == Tile::Start) {
                tile_queue.push(&map[i][j]);
                break;
            }
        }
    }

    if (tile_queue.empty()) {
        cout << "No start found" << endl;
        return 1;
    }

    while (!tile_queue.empty()) {
        auto current_tile = tile_queue.front();
        tile_queue.pop();

        int x = current_tile->x;
        int y = current_tile->y;

        switch (current_tile->type) {
            case Tile::VerticalPipe:
                // check if we can move up, or down
                if (isValidPos(x, y - 1, width, height) &&
                    isConnecting(map[y - 1][x], current_tile)) {
                    auto tile = &map[y - 1][x];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                if (isValidPos(x, y + 1, width, height) &&
                    isConnecting(map[y + 1][x], current_tile)) {
                    auto tile = &map[y + 1][x];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                break;
            case Tile::HorizontalPipe:
                // check if we can move left, or right
                if (isValidPos(x - 1, y, width, height) &&
                    map[y][x - 1].dist == -1 &&
                    map[y][x - 1].type != Tile::Ground) {
                    auto tile = &map[y][x - 1];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                if (isValidPos(x + 1, y, width, height) &&
                    map[y][x + 1].dist == -1 &&
                    map[y][x + 1].type != Tile::Ground) {
                    auto tile = &map[y][x + 1];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                break;
            case Tile::NwBend:  // J
                if (isValidPos(x - 1, y, width, height) &&
                    map[y][x - 1].dist == -1 &&
                    map[y][x - 1].type != Tile::Ground) {
                    auto tile = &map[y][x - 1];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                if (isValidPos(x, y - 1, width, height) &&
                    map[y - 1][x].dist == -1 &&
                    map[y - 1][x].type != Tile::Ground) {
                    auto tile = &map[y - 1][x];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                break;
            case Tile::NeBend:  // L
                if (isValidPos(x + 1, y, width, height) &&
                    map[y][x + 1].dist == -1 &&
                    map[y][x + 1].type != Tile::Ground) {
                    auto tile = &map[y][x + 1];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                if (isValidPos(x, y - 1, width, height) &&
                    map[y - 1][x].dist == -1 &&
                    map[y - 1][x].type != Tile::Ground) {
                    auto tile = &map[y - 1][x];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                break;
            case Tile::SwBend:  // 7
                if (isValidPos(x - 1, y, width, height) &&
                    map[y][x - 1].dist == -1 &&
                    map[y][x - 1].type != Tile::Ground) {
                    auto tile = &map[y][x - 1];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                if (isValidPos(x, y + 1, width, height) &&
                    map[y + 1][x].dist == -1 &&
                    map[y + 1][x].type != Tile::Ground) {
                    auto tile = &map[y + 1][x];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                break;
            case Tile::SeBend:  // F
                if (isValidPos(x + 1, y, width, height) &&
                    map[y][x + 1].dist == -1 &&
                    map[y][x + 1].type != Tile::Ground) {
                    auto tile = &map[y][x + 1];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                if (isValidPos(x, y + 1, width, height) &&
                    map[y + 1][x].dist == -1 &&
                    map[y + 1][x].type != Tile::Ground) {
                    auto tile = &map[y + 1][x];
                    tile->dist = current_tile->dist + 1;
                    tile_queue.push(tile);
                }
                break;
            case Tile::Ground:
                break;
            case Tile::Start:
                current_tile->dist = 0;

                vector<Tile*> v;

                if (isValidPos(x - 1, y, width, height))
                    v.push_back(&map[y][x - 1]);
                if (isValidPos(x, y - 1, width, height))
                    v.push_back(&map[y - 1][x]);
                if (isValidPos(x, y + 1, width, height))
                    v.push_back(&map[y + 1][x]);
                if (isValidPos(x + 1, y, width, height))
                    v.push_back(&map[y][x + 1]);

                for (auto tile : v) {
                    if (tile->type == Tile::Ground) continue;
                    tile->dist = 0;
                    tile_queue.push(tile);
                }
                continue;
        }

        current_tile->dist++;
    }

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            auto value = map[i][j].dist;
            if (value == -1) {
                cout << "~";
            } else {
                cout << value;
            }
        }
        cout << endl;
    }

    // find tile with highest distance
    int max_dist = 0;

    for (auto row : map) {
        for (auto tile : row) {
            if (tile.dist > max_dist) {
                max_dist = tile.dist;
            }
        }
    }

    cout << "Max distance: " << max_dist << endl;

    return 0;
}
