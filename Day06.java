import java.util.List;

public class Day06 {
    public static void Run(List<String> input)
    {
        for(int i=0,n=input.size();i<n;i++){
            String line =input.get(i);
            System.out.println("Line "
                +(i+1)
                +" 06.1: "
                +findMarker(line,4)
                +"  06.2: "
                +findMarker(line,14));
        }
    }

    public static int findMarker(String input, int keyLength)
    {
        String keyBuffer ="";
        int syncPosition,length;
        for (syncPosition=0,length=input.length();syncPosition<length;syncPosition++)
        {
            char nextChar = input.charAt(syncPosition);
            int matchPos = keyBuffer.indexOf(nextChar);
            if (matchPos > -1)
            {
                // remove up to and including match.
                // can only be one match because key contains no duplicates
                keyBuffer = keyBuffer.substring(matchPos+1);
            }
            keyBuffer=keyBuffer+nextChar;
            if (keyBuffer.length() == keyLength)
                break;
        }
        //return 1-based position
        return syncPosition+1;
    }
}
