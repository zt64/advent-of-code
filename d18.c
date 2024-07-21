#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
    bool **map;
    int x, y;
    int width, height;
} map;

map *init_map(int width, int height) {
    map *m = malloc(sizeof(map));
    m->width = width;
    m->height = height;
    m->x = 0;
    m->y = 0;

    m->map = malloc(width * sizeof(bool *));
    for (int i = 0; i < width; i++) {
        m->map[i] = malloc(height * sizeof(bool));
    }

    return m;
}

void assign(map *map, int x, int y, bool value) {
    // assign value to map allocating if necessary

    if (x + map->x > map->width) {
        // realloc
        map->width = map->width + 10;
        map->map = realloc(map->map, map->width * sizeof(bool *));
        for (int i = 0; i < map->width; i++) {
            map->map[i] = realloc(map->map[i], map->width * sizeof(bool));
        }
    }

    if (y + map->y > map->height) {
        // realloc
        map->height = map->height + 10;
        map->map = realloc(map->map, (map->width + y) * sizeof(bool *));
        // for (int i = 0; i < map->height; i++) {
        //     map->map[i] = realloc(map->map[i], map->height * sizeof(bool));
        // }
    }
}

void process_dig_plan(map *map, char **plan, int len) {
    int x = 0, y = 0;

    assign(map, 0, 0, true);
    assign(map, 0, 1, true);
    assign(map, 1, 0, true);

    for (int i = 0; i < len; ++i) {
    }

    printf("\n");

    printf("width: %d, height: %d\n", map->width, map->height);

    for (int i = 0; i < map->width; i++) {
        for (int j = 0; j < map->height; j++) {
            char c;

            if (map->map[i][j]) {
                c = '#';
            } else {
                c = '.';
            }

            printf("%s", &c);
        }
        printf("\n");
    }
}

void read_plan(char **array) {
    FILE *fp = fopen("./input/d18.txt", "r");

    char *line = NULL;
    size_t len = 0;

    int i = 0;
    while (getline(&line, &len, fp) != -1) {
        array[i] = malloc(128 * sizeof(char));
        strcpy(array[i], line);
        i++;
    }

    fclose(fp);
}

int main() {
    char **plan = malloc(20 * sizeof(char *));

    FILE *fp = fopen("./input/d18.txt", "r");

    char *line = NULL;
    size_t len = 0;

    int i = 0;
    while (getline(&line, &len, fp) != -1) {
        plan[i] = malloc(100 * sizeof(char));
        strcpy(plan[i], line);
        i++;
    }

    fclose(fp);

    // print plan
    for (int j = 0; j < i; j++) {
        printf("%s", plan[j]);
    }

    map *map = init_map(10, 10);
    process_dig_plan(map, plan, i);

    free(line);
    return 0;
}

enum Direction { UP = 'U', DOWN = 'D', LEFT = 'L', RIGHT = 'R' };