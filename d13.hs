import Control.Monad
import System.IO

main = do
  let list = []
  handle <- openFile "./input/d13.txt" ReadMode
  contents <- hGetContents handle
  let singlewords = words contents
      list = f singlewords
  print list
  hClose handle

f :: [String] -> [Int]
f = map read
