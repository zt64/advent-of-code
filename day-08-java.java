import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day08 {
    public static void main(final String[] args) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of("./input/day-08.txt"));
        } catch (final Exception e) {
            System.out.println("Error reading file");
            return;
        }

        final var instructions = lines.get(0);
        final Map<String, Node> nodes = new HashMap<>();

        lines.subList(2, lines.size()).forEach(line -> {
            final var node = line.substring(0, 3);
            final var left = line.substring(7, 10);
            final var right = line.substring(12, 15);

            nodes.computeIfAbsent(node, key -> new Node(node, left, right));
        });

        var count = 0;
        var node = nodes.values().iterator().next();

        for (final var c : instructions.toCharArray()) {

            switch (c) {
                case 'L' -> {
                    node = nodes.get(node.left);
                    System.out.println(node.name);
                }
                case 'R' -> {
                    node = nodes.get(node.right);
                }
            }
            if (node.name == "ZZZ") {
                System.out.println("Found ZZZ " + count);
                break;
            }
        }
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