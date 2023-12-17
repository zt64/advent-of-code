import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Day08 {
    private static final char[] instructions;
    private static final Map<String, Node> nodes = new HashMap<>();

    static {
        final List<String> lines;
        try {
            lines = Files.readAllLines(Path.of("./input/d08.txt"));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        instructions = lines.get(0).toCharArray();

        lines.subList(2, lines.size()).forEach(line -> {
            final var node = line.substring(0, 3);
            final var left = line.substring(7, 10);
            final var right = line.substring(12, 15);

            nodes.computeIfAbsent(node, key -> new Node(node, left, right));
        });
    }

    public static void main(final String[] args) {
        System.out.println("Part 1: " + part1() + " steps");
        System.out.println("Part 2: " + part2() + " steps");
    }

    private static int part1() {
        var count = 0;
        var node = nodes.get("AAA");
        var i = 0;

        while (true) {
            final var c = instructions[i];

            switch (c) {
                case 'L' -> {
                    node = nodes.get(node.left);
                }
                case 'R' -> {
                    node = nodes.get(node.right);
                }
            }

            count++;
            if (node.name.equals("ZZZ"))
                break;

            if (i == instructions.length - 1) {
                i = 0;
            } else {
                i++;
            }
        }

        return count;
    }

    private static int part2() {
        var startingNodes = (String[]) nodes.keySet().stream().filter(key -> key.endsWith("A")).toArray();
        var i = 0;
        var count = 0;

        while (true) {
            final var c = instructions[i];
            for (int j = 0; j < startingNodes.length; j++) {
                final var node = nodes.get(startingNodes[j]);
                switch (c) {
                    case 'L' -> {
                        startingNodes[j] = node.left;
                    }
                    case 'R' -> {
                        startingNodes[j] = node.right;
                    }
                }
            }

            count++;

            if (Arrays.stream(startingNodes).allMatch(node -> node.endsWith("Z")))
                break;

            if (i == instructions.length - 1) {
                i = 0;
            } else {
                i++;
            }
        }

        return count;
    }
}

class Node {
    final String name;
    String left;
    String right;

    public Node(final String name, final String left, final String right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }
}