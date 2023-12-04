default:
  @just --list

# Python
day01 PART:
    python3 day-01-python.py {{PART}}

# C#
day02 PART:
    csc day-02-csharp.cs && mono day-02-csharp.exe {{PART}}

# Go
day03 PART:
    go run ./day-03-go.go {{PART}}

# D
day04:
    rdmd day-04-dlang.d

# Crystal
day05:
    crystal day-05-crystal.cr

# JavaScript
day06:
    node day-06-javascript.js

# Rust
day07:
    ./day-07-rust.rs

# Java
day08:
    javac day-08-java.java && java Day08

# Bash
day09:
    bash day-09-bash.sh

# C++
day10:
    g++ day-10-cpp.cpp && ./a.out

# Julia
day11:
    julia day-11-julia.jl