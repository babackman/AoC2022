import java.util.ArrayList;
import java.util.List;

public class Day25 {
    public static void Run(List<String>input) throws ArithmeticException
    {
        // read fuel values
        var fuelValues = readFuelValues(input);
        long totalFuel = 0;
        for (long f: fuelValues){
            totalFuel=Math.addExact(f, totalFuel);
        }
        var totalSnafu = integerToSnafu(totalFuel);
        System.out.println("Day 25 pt 1, total fuel: "+ totalSnafu);
    }

    public static List<Long> readFuelValues(List<String> input) throws ArithmeticException {
        var fuelValues = new ArrayList<Long>(input.size());
        for(var line: input)
        {
            fuelValues.add(snafuToInteger(line));
        }
        return fuelValues;
    }

    public static long snafuToInteger(String snafu) throws ArithmeticException {
        long placeValue=1;
        long result = 0;
        for(int i=snafu.length()-1; i >= 0; i--)
        {
            var c = snafu.charAt(i);
            switch (c){
                case '0':
                case '1':
                case '2':
                    var addition = Math.multiplyExact(placeValue, ((int)c-(int)'0'));
                    result = Math.addExact(result, addition);
                    break;
                case '-':
                    result = Math.addExact(result, -placeValue);
                    break;
                case '=':
                    result = Math.addExact(result, Math.multiplyExact(-2, placeValue));
                    break;
            }
            placeValue = Math.multiplyExact(placeValue, 5);
        }
        return result;
    }

    public static String integerToSnafu(long i)
    {
        var base5 = Long.toString(i,5);
        // find snafu place values:
        var places = new int[base5.length()+1];
        places[0]=0;
        for (int x = base5.length()-1; x>= 0; x--)
            places[x+1] = (int)base5.charAt(x) - (int)'0';
        for (int x=places.length-1; x > 0; x--)
        {
            if (places[x] > 2)
            {
                places[x-1]++;
                places[x] -= 5;
            }
        }

        var builder = new StringBuilder();
        for (int x=0; x < places.length; x++)
        {
            // skip leading 0
            if (x==0 && places[x]==0)
                continue;

            switch (places[x]){
                case -2:
                    builder.append('=');
                    break;
                case -1:
                    builder.append('-');
                    break;
                case 0:
                case 1:
                case 2:
                    char c = (char)(places[x]+'0');
                    builder.append(c);
                    break;
            }
        }
        return builder.toString();
    }
}
