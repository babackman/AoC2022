import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

import java.util.regex.Matcher;

class Day05{
  public static void Run(List<String> input){
    int listPosition;
    Stack<Move> moves = new Stack<Move>();
    Pattern movePattern = Pattern.compile("move (?<count>\\d+) from (?<source>\\d+) to (?<dest>\\d+)");
    Matcher matcher;
    for(listPosition=input.size()-1;listPosition>=0;listPosition--)
      {
        String line = input.get(listPosition);
        if (line.isEmpty())
          break; 
        matcher = movePattern.matcher(line);
        matcher.find();
        Move newMove=new Move(
          Integer.parseInt(matcher.group("count")), 
          Integer.parseInt(matcher.group("source")),
          Integer.parseInt(matcher.group("dest")));
        moves.push(newMove);
      }
    listPosition--; // skip blank line
    String stackLabels = input.get(listPosition--);
    // shortcut: input only has 9 stacks, so only handling single digit labels
    int stackCount=1;
    char content;
    while(true)
      {
        char label = GetStackContent(stackLabels,stackCount);
        if (label != ' ')
          stackCount++;
        else {
          stackCount--; // back up to real end
          break;
        }
      }

    // create the stacks
    ArrayList<Stack<Character>> stacks = new ArrayList<Stack<Character>>(stackCount);
    for (int stackIndex=0;stackIndex<stackCount;stackIndex++)
      stacks.add(new Stack<Character>());
    //  read initial stacks
    boolean[] stackDone = new boolean[stackCount];
    for ( ; listPosition >=0; listPosition--)
    {
      String line= input.get(listPosition);
      for (int stackNumber=1; stackNumber<=stackCount; stackNumber++)
      {
        int stackIndex=stackNumber-1;
        if (stackDone[stackIndex]==true)
          continue;
        char crate=GetStackContent(line,stackNumber);
        if (crate == ' ')
          stackDone[stackIndex]=true;
        else
          stacks.get(stackIndex).push(crate);
      }
    }

/*    
    // Part 1: Move one at a time
    // perform moves
    while (!moves.isEmpty())
    {
      (moves.pop()).MoveOneByOne(stacks);
    }
    // combine tops and display
    String topCrates=new String();
    for(int stackIndex=0;stackIndex<stacks.size();stackIndex++)
      topCrates =topCrates + stacks.get(stackIndex).peek();
    System.out.println("05.1: "+topCrates);
*/

    // Part 2: Move all at once
    // perform moves
    while (!moves.isEmpty())
    {
      (moves.pop()).MoveAllAtOnce(stacks);
    }
    // combine tops and display
    String topCrates=new String();
    for(int stackIndex=0;stackIndex<stacks.size();stackIndex++)
      topCrates =topCrates + stacks.get(stackIndex).peek();
    System.out.println("05.2: "+topCrates);
    } // Run

  private static int P1(){
    return 0;
  }

  private static char GetStackContent(String stackLine, int stackNumber)
  {
    int stackPosition = 1 + (stackNumber-1)*4;
    if (stackPosition >= stackLine.length())
      return ' ';
    return stackLine.charAt(stackPosition);
  }
}

  
class Move{
  private int _count;
  private int _source;
  private int _dest;

  Move(int count, int source, int dest){
    _count =count;
    _source=source;
    _dest=dest;
  }

  public void MoveOneByOne(ArrayList<Stack<Character>>stacks)
  {
    Stack<Character> source=stacks.get(_source-1);
    Stack<Character> dest=stacks.get(_dest-1);
    for (int i=0;i<_count;i++)
      dest.push(source.pop());
  }

  public void MoveAllAtOnce(ArrayList<Stack<Character>>stacks)
  {
    // quick n dirty; reverse then reverse again to move in same order
    Stack<Character> source=stacks.get(_source-1);
    Stack<Character> dest=stacks.get(_dest-1);
    Stack<Character>crane=new Stack<Character>();
    for (int i=0;i<_count;i++)
      crane.push(source.pop());
    for (int i=0;i<_count;i++)
      dest.push(crane.pop());
  }

}
