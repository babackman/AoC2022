import java.util.List;

class Day03{
  public static int p1(List<String> input)
  {
    int totalPriority = 0;
    for(String backpack: input)
    {
      char duplicate = findDuplicate(backpack);
      int priority = getPriority(duplicate);
      totalPriority += priority;
    }
   
      return totalPriority;
  }

    
  public static int p2(List<String> input)
  {
    int totalPriority=0;
    for(int offset=0;offset <= input.size()-3; offset+=3)
    {
      String backpack1=input.get(offset);
      String backpack2=input.get(offset+1);
      String backpack3=input.get(offset+2);
      char badge = findCommonItem(backpack1, backpack2, backpack3);
      totalPriority+=getPriority(badge);
    }

    return totalPriority;
  }

  public static char findDuplicate(String backpack)
  {
    int midpoint = backpack.length() /2;
    for(int i =0;i<midpoint;i++)
    {
      // look for each character from first half, in the back half
      if (backpack.indexOf(backpack.charAt(i),midpoint) >=0)
        return backpack.charAt(i);
    }
    return '-';
  }
  public static char findCommonItem(String backpack1, String backpack2, String backpack3)
  {
    for (int i = 0, n = backpack1.length(); i < n; i++) 
    {
      char item = backpack1.charAt(i);
      if ((backpack2.indexOf(item) >= 0)
          && (backpack3.indexOf(item) >= 0))
      {
        return item;
      }
    }
    return '-';
  }
  
  public static int getPriority(char c)
  {
    if (c >='a' && c <= 'z')
      return c - 'a' + 1;
    if (c >='A' && c <= 'Z')
      return c - 'A' + 27;
    return 0;
  }
}