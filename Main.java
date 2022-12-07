// Advent of Code 2022
// https://adventofcode.com/2022

// Imports for file processing and arrays
import java.nio.file.*;
import java.util.*;

class Main {

    public static void main(String[] args) throws java.io.IOException {

    List<String> lines = Files.readAllLines(Paths.get("07_data.txt"));
    //System.out.println("03.1: " + Day03.p1(lines));
    //System.out.println("03.2: " + Day03.p2(lines));
    Day07.Run(lines);

  }

}