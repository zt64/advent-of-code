import Enum
import String

total_load =
  File.read!("./input/d14.txt")
  |> String.split("\n")
  |> map(&graphemes/1)
  |> zip_with(fn a ->
    a
    |> chunk_by(&(&1 == "#"))
    |> map(&Enum.sort(&1, :desc))
    |> join()
    |> graphemes()
    |> Enum.reverse()
  end)
  |> zip_with(&Function.identity/1)
  |> with_index()
  |> reduce(0, fn {a, i}, acc -> acc + count(a, &(&1 == "O")) * (i + 1) end)

IO.puts(total_load)
