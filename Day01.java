import java.util.List;
import java.util.ArrayList;

class Day01{
  public static double p1(List<String> input){
    int elf=0;
    int max=0;
    for(String line: input){
      if (!line.isEmpty()){
        elf+= Integer.parseInt(line);
      }
      else{
        if(elf > max){
          max=elf;
        }
      elf=0;
      }
    }
    if (elf>max){
      max=elf;
    }
    return max;
  }

  public static double p2(List<String> input){
    int elf=0;
    
    ArrayList<Integer> topElves = new ArrayList<Integer>();
    for(String line: input){
      if (!line.isEmpty()){
        elf+= Integer.parseInt(line);
      }
      else{
        updateLeaderboard(topElves,elf,3);
        elf=0;
      }
    }
    updateLeaderboard(topElves,elf,3);

    int leaderTotal=0;
    for(int leader: topElves){
      leaderTotal += leader;
    }
    return leaderTotal;
  }

  public static void updateLeaderboard(ArrayList<Integer> leaders, int newScore, int leaderboardLength){
    for (int i=0;i<leaderboardLength;i++){
      if ((i >= leaders.size()) || (newScore>leaders.get(i))){
        leaders.add(i,newScore);
        break;
      }
    }
    while (leaders.size() > leaderboardLength)
    {
      leaders.remove(leaders.size()-1);
    }
  }
}