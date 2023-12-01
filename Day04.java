import java.util.List;
import java.util.ArrayList;

class Day04{
  public static void Run(List<String> input){
    ArrayList<ElfPair> elfPairs=new ArrayList<ElfPair>(input.size());
    for(String line: input){
      String[] pairs = line.split(",");
      elfPairs.add(new ElfPair(
        new SectionRange(pairs[0]),
        new SectionRange(pairs[1])
        ));
    }

    System.out.println("04.1: "+P1(elfPairs));
    System.out.println("04.2: "+P2(elfPairs));
  } // Run

  private static int P1(List<ElfPair> elfPairs){
    int count=0;
    for(ElfPair pair: elfPairs)
      {
        if (pair.getRange1().CompletelyContains(pair.getRange2())
           || pair.getRange2().CompletelyContains(pair.getRange1()))
        {
          count++;
        }
      }
    return count;
  }

  private static int P2(List<ElfPair> elfPairs){
    int count=0;
    for(ElfPair pair: elfPairs)
      {
        if (pair.getRange1().Overlaps(pair.getRange2()))
        {
          count++;
        }
      }
    return count;
  }

}


class SectionRange{
  private int _start;
  private int _end;
  
  public SectionRange(String rangeDescription)
  {
    // input string is in the form {start}-{end}
    String[] startEnd=rangeDescription.split("-");
    _start=Integer.parseInt(startEnd[0]);
    _end=Integer.parseInt(startEnd[1]);
  }
  
  public SectionRange(int start, int end)
  {
    _start=start;
    _end=end;
  }
  
  public boolean Contains(int sectionNumber)
  {
    return ((sectionNumber>=_start) && (sectionNumber <= _end));
  }
  
  public boolean CompletelyContains(SectionRange other)
  {
    return Contains(other._start) && Contains(other._end);
  }

  public boolean Overlaps(SectionRange other)
  {
    // this probably has some redundancy that could be optimized out
    return Contains(other._start)
      || Contains(other._end)
      || other.Contains(_start)
      || other.Contains(_end);
  }
}

class ElfPair{
  SectionRange _range1;
  SectionRange _range2;
  
  public ElfPair(SectionRange range1, SectionRange range2)
  {
    _range1=range1;
    _range2=range2;
  }

  public SectionRange getRange1(){return _range1;}
  
  public SectionRange getRange2(){return _range2;}
}  
