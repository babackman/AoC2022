import java.util.List;

class Day02{
  public static int p1(List<String> input){
    int totalScore=0;
    for(String round: input){
      totalScore+=ScoreRound(Decode1(round));
    }
    return totalScore;
  }

  public static int p2(List<String> input){
    int totalScore=0;
    for(String round: input){
      totalScore+=ScoreRound(Decode2(round));
    }
    return totalScore;
  }

  public static String Decode1(String round)
  {
    return round
      .replace('X','R')
      .replace('Y','P')
      .replace('Z','S')
      .replace('A','R')
      .replace('B','P')
      .replace('C','S');
  }
  
  public static String Decode2(String round)
  {
    switch (round)  {
      case "A X": return "R S";
      case "A Y": return "R R";
      case "A Z": return "R P";
      case "B X": return "P R";
      case "B Y": return "P P";
      case "B Z": return "P S";
      case "C X": return "S P";
      case "C Y": return "S S";
      case "C Z": return "S R";
      }
    return "";
  }
  
  public static int ScoreRound(String round)
  {
    switch (round)  {
      case "R R": return 1+3;
      case "R P": return 2+6;
      case "R S": return 3+0;
      case "P R": return 1+0;
      case "P P": return 2+3;
      case "P S": return 3+6;
      case "S R": return 1+6;
      case "S P": return 2+0;
      case "S S": return 3+3;
      }
    return 0;
  }
}