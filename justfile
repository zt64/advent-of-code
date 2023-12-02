default:
  @just --list

# Python
day01 PART:
    python3 day-01-python.py {{PART}}

# C#
day02 PART:
    csc day-02-csharp.cs && mono day-02-csharp.exe {{PART}}

# Ruby
day03 PART:
    ruby day-03-ruby.rb {{PART}}

# C++
day04 PART:
    g++ day-04-cpp.cpp -o day-04-cpp && ./day-04-cpp {{PART}}

# JavaScript
day05 PART:
    node day-05-javascript.js {{PART}}

# Lua
day06 PART:
    lua day-06-lua.lua {{PART}}

# Rust
day07 PART:
    rustc day-07-rust.rs && ./day-07-rust {{PART}}

# Go
day08 PART:
    go run day-08-go.go {{PART}}

# Java
day09 PART:
    javac day-09-java.java && java day-09-java {{PART}}

# Bash
day10 PART:
    bash day-10-bash.sh {{PART}}

# V
day11 PART:
    v day-11-v.v {{PART}}

# C
day12 PART:
    gcc day-12-c.c -o day-12-c && ./day-12-c {{PART}}

# Swift
day13 PART:
    swift day-13-swift.swift {{PART}}

# Kotlin
day14 PART:
    kotlinc -script day-14-kotlin.kts {{PART}}

# Zig
day15 PART:
    zig build-exe day-15-zig.zig && ./day-15-zig {{PART}}

# Julia
day16 PART:
    julia day-16-julia.jl {{PART}}

# D
day17 PART:
    dmd day-17-d.d && ./day-17-d {{PART}}