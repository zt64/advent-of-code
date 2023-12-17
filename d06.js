const fs = require("node:fs");

try {
  const file = fs.readFileSync("./input/d06.txt").toString().split("\n");

  console.log(`Part 1: ${part1(file)} ways to win`);
  console.log(`Part 2: ${part2(file)} ways to win`);
} catch (err) {
  console.error(err);
}

function part1(lines) {
  const [times, distances] = lines.map((line) =>
    line.split(/\s+/).slice(1).map(Number)
  );

  return possibilities(times, distances);
}

function part2(lines) {
  const [times, distances] = lines.map((line) => [
    Number(line.substring(10).split(" ").join("")),
  ]);

  return possibilities(times, distances);
}

function possibilities(times, distances) {
  return times.reduce((acc, time, i) => {
    const recordDistance = distances[i];
    let waysToWin = 0;

    for (let heldTime = 0; heldTime <= time; heldTime++) {
      const distanceTravelled = heldTime * (time - heldTime);

      if (distanceTravelled > recordDistance) waysToWin++;
    }

    return waysToWin * acc;
  }, 1);
}
