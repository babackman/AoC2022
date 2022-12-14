import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.DataFormatException;

public class Day13 {
    public static void Run(List<String> input)
    {
        var packets = new ArrayList<List<Object>>();
        for (var line: input) 
        {
            if(line.isBlank())
             continue;
             packets.add(new ListReader(line).getList());
        }

        p1(packets);
        p2(packets);
    }

    private static void p1(List<List<Object>> packets)
    {
        int sum=0;
        var packetComparator = new PacketComparator();
        for (int pair=1, pairs = packets.size()/2; pair<=pairs; pair++)
        {
            int index = (pair-1)*2;
            if (packetComparator.compare(packets.get(index), packets.get(index+1)) < 1)
            {
                // add 1-based index
                sum += (pair);
            }
        }

        System.out.println("Day 13.1: " + sum);
    }

    private static void p2(List<List<Object>> packets)
    {
        var marker1=new ListReader("[[2]]").getList();
        var marker2=new ListReader("[[6]]").getList();
        packets.add(marker1);
        packets.add(marker2);
        packets.sort(new PacketComparator());
        
        var index1 = packets.indexOf(marker1);
        var index2 = packets.indexOf(marker2);
        int decoderKey = (index1+1) * (index2+1);
        System.out.println("Day 13.2: " + decoderKey);
    }
    private static class ListReader{
        private int _index;
        private String _input;

        public ListReader(String input)
        {
            _input=input;
        }

        public List<Object> getList()
        {
            _index=0;
            try {
                return readList();
            } catch (DataFormatException e) {
                System.out.println("bad input: "+ e.getMessage());
                return null;
            }
        }

        private List<Object> readList() throws DataFormatException
        {
            if (_input.charAt(_index) != '[')
                throw new DataFormatException("List didn't start with [");
            _index++;
            var list = new ArrayList<Object>();
            while((_index < _input.length()) && (nextChar() != ']'))
            {
                list.add(readElement());
            }
            if (_index < _input.length())
                _index++; // skip ']'
            return list;
        }

        private int readInteger(){
            char c=nextChar();
            int i=0;
            while(Character.isDigit(c))
            {
                i= (i*10 )+(c-'0');
                _index++;
                c=nextChar();
            }
            return i;
        }
        private Object readElement() throws DataFormatException
        {
            Object element;
            if (nextChar()=='[')
                element = readList();
            else
                element = readInteger();
            if (nextChar()==',')
                _index++;
            return element;
        }

        private char nextChar()
        {
            return _input.charAt(_index);
        }
    }

    private static class PacketComparator implements Comparator<List<Object>>
    {
        
        @Override
        public int compare(List<Object> left, List<Object> right) {
            int rsize = right.size();
            for (int i = 0, n = left.size(); i < n; i++)
            {   
                if (i>=rsize)
                    return +1; //right ran out first, left is bigger
                var leftElement = left.get(i);
                var rightElement = right.get(i);
                int compare = CompareElements(leftElement, rightElement);
                if (compare != 0)
                    return compare;
            }
            if (right.size() > left.size())
                return -1; // left ran out first, left is smaller
    
            return 0; // equal
        }

        private int CompareElements(Object left, Object right)
        {
            if (left instanceof Integer)
            {
                if (right instanceof Integer)
                {
                    return ((Integer)left).compareTo((Integer)right);
                }
                // integer and list
                return compare(List.of(left), (List<Object>)right);
            }
            var leftList = (List<Object>)left;
            if (right instanceof Integer)
            {
                return compare(leftList, List.of(right));
            }
            return compare(leftList, (List<Object>)right);
        }
        }
}
