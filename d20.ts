import * as fs from "fs";

const file = fs.readFileSync("./input/d20.txt", "utf8");
const input = file.split("\n");

const modules = new Map<string, string>();

input.forEach((line) => {
  const [a, b] = line.split("->");

  modules.set(a.trim(), b.trim());
});

console.log(modules.get("broadcaster")!.split(", "));

interface Module {
  readonly value: number;
}

class BroadcastModule implements Module {
  private inputs: Module[];

  public get value(): number {
    return this.inputs[0].value;
  }
}

class FlipFlopModule implements Module {
  state: boolean;
  next: boolean;

  public get value(): number {
    return this.state ? 1 : 0;
  }
}

class ConjunctionModule implements Module {
  private inputs: Module[];

  public get value(): number {
    return this.inputs.reduce((acc, curr) => acc * curr.value, 1);
  }
}
