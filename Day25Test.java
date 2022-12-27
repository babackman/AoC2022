import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Day25Test {
    @Test
    public void roundTrip0To100()    {
        for(int i=0;i <=100;i++){
            var snafu = Day25.integerToSnafu(i);
            var j = Day25.snafuToInteger(snafu);
            assertEquals(i, j);
        }

    }
}
