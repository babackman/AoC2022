// Advent of Code 2022
// https://adventofcode.com/2022

// Imports for file processing and arrays
import java.nio.file.*;
import java.util.*;

class Main {

    public static void main(String[] args) throws java.io.IOException {

    List<String> lines = Files.readAllLines(Paths.get("08_data.txt"));

    Day08.Run(lines);
  }

}