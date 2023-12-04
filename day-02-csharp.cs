using System;
using System.IO;
using System.Linq;

public class HelloWorld
{
    private static Game[] games = ParseInput();

    public static void Main(string[] args)
    {
        switch (args[0])
        {
            case "1":
                Part1();
                break;
            case "2":
                Part2();
                break;
            default:
                Console.WriteLine("Invalid part specified");
                break;
        }
    }

    private static Game[] ParseInput()
    {
        var lines = File.ReadLines("./input/day-02.txt").ToArray();
        var lineCount = lines.Length;

        var games = new Game[lineCount];
        for (var i = 0; i < lineCount; i++)
        {
            var line = lines[i];
            var s = line.Substring(line.IndexOf(":") + 2);

            var bagStrs = s.Split(";");
            var bags = new Game.Bag[bagStrs.Length];
            for (var j = 0; j < bagStrs.Length; j++)
            {
                var bag = bagStrs[j];
                var colors = bag.Split(",").Select(x => x.Trim()).ToArray();

                int r = 0;
                int g = 0;
                int b = 0;

                foreach (var color in colors)
                {
                    var spacer = color.IndexOf(" ");
                    var count = Int32.Parse(color.Substring(0, color.IndexOf(" ")));
                    var colorStr = color.Substring(color.IndexOf(" ") + 1);

                    switch (colorStr)
                    {
                        case "red":
                            r = count;
                            break;
                        case "green":
                            g = count;
                            break;
                        case "blue":
                            b = count;
                            break;
                    }
                }
                bags[j] = new Game.Bag(r, g, b);
            }

            games[i] = new Game(bags);
        }

        return games;
    }

    private static void Part1()
    {
        var total = 0;

        for (var i = 0; i < games.Length; i++)
        {
            var game = games[i];

            if (!game.bags.All(x => x.red <= 12 && x.green <= 13 && x.blue <= 14)) continue;

            Console.WriteLine($"Adding {i + 1} to total");
            total += i + 1;
        }

        Console.WriteLine($"Total: {total}");
    }

    private static void Part2()
    {
        var total = 0;

        foreach (var game in games)
        {
            var red = game.bags.Max(x => x.red);
            var green = game.bags.Max(x => x.green);
            var blue = game.bags.Max(x => x.blue);

            total += red * green * blue;
        }

        Console.WriteLine($"Total: {total}");
    }
}

public class Game
{
    public Bag[] bags;
    public Game(Bag[] b)
    {
        bags = b;
    }

    public class Bag
    {
        public int red;
        public int green;
        public int blue;

        public Bag(int r, int g, int b)
        {
            red = r;
            green = g;
            blue = b;
        }
    }
}
