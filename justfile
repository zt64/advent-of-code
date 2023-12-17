default:
  @just --list

# Python
day01 PART:
    python3 d01.py {{PART}}

# C#
day02 PART:
    csc d02.cs && mono d02.exe {{PART}}

# Go
day03 PART:
    go run ./d03.go {{PART}}

# D
day04:
    rdmd d04.d

# Crystal
day05:
    crystal d05.cr

# JavaScript
day06:
    node d06.js

# Rust
day07:
    ./d07.rs

# Java
day08:
    javac d08.java && java Day08

# Julia
day09:
    julia d09.jl

# C++
day10:
    g++ d10.cpp && ./a.out

# Zig
day11:
    zig run d11.zig

# V
day12:
    v run d12.v

# Gleam
day13:
    gleam run d13.gleam

# Elixir
day14:
    elixirc d14.ex

# Nim
day15:
    nim c -r --verbosity:0 d15.nim

# Fortran
day16:
    gfortran d16.f90 -o /tmp/d16 && /tmp/d16

# Swift
day17:
    swift d17.swift