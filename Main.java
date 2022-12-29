// Advent of Code 2022
// https://adventofcode.com/2022

// Imports for file processing and arrays
import java.nio.file.*;
import java.util.*;

class Main {

  public static void main(String[] args) throws java.io.IOException {

    List<String> lines = Files.readAllLines(Paths.get("15_data.txt"));

    //Day15.Run(lines, 10, 20); // day 15 test
    Day15.Run(lines, 2000000, 4000000); // day 15 problem
  }

}