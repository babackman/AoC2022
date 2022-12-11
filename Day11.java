import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day11 {
    public static void Run(List<String>input) {
        Monkey[] monkeys = processInput(input);
        p1(monkeys);
    }
    
    private  static Monkey[] processInput(List<String>input){
        // 7 input lines per monkey, except for the last which is 6
        int monkeyCount=((input.size()-6) / 7)+1;
        var monkeys = new Monkey[monkeyCount];
        for(int i=0; i < monkeyCount; i++){
            monkeys[i]=new Monkey();
        }
        
        for(int i=0; i < monkeyCount; i++){
            int startLine = i*7; // "Monkey 0:"
            // +1 "  Starting items: 79, 98"
            var items = input.get(startLine+1)
                        .substring("  Starting items: ".length())
                        .split(", ");
            for(var item: items)
                monkeys[i].addItem(Long.parseLong(item));

            // +2 "  Operation: new = old * 19"
            var operation = input.get(startLine+2)
                        .substring("  Operation: new = old ".length());
            monkeys[i].setOperator(operation.charAt(0));
            if (!operation.substring(2).equals("old"))
                monkeys[i].setConstOperand(Long.parseLong(operation.substring(2)));

            // +3 "  Test: divisible by 23"
            monkeys[i].setTestDivisor(Long.parseLong(
                input.get(startLine+3) .substring("  Test: divisible by ".length())));

            // +4 "    If true: throw to monkey 2"
            int targetIndex = Integer.parseInt(input.get(startLine+4)
                .substring("    If true: throw to monkey ".length()));
            monkeys[i].setTrueTarget(monkeys[targetIndex]);

            // +5 "    If false: throw to monkey 3"
            targetIndex = Integer.parseInt(input.get(startLine+5)
                .substring("    If false: throw to monkey ".length()));
            monkeys[i].setFalseTarget(monkeys[targetIndex]);
        }
        return monkeys;
    }

    private static void p1(Monkey[] monkeys){
        // run 20 rounds 
        for (int round = 0; round < 20; round++){
            for(var monkey : monkeys)
                monkey.inspectAndThrowAll();
        }

        // get all inspection counts:
        var inspectionCount = new int[monkeys.length];
        for (int i=0; i < monkeys.length; i++)
            inspectionCount[i]=monkeys[i].getInspectionCount();

        // multiply top 2 inspection counts:
        Arrays.sort(inspectionCount);
        long monkeyBusiness = inspectionCount[inspectionCount.length-1] *inspectionCount[inspectionCount.length-2];
        System.out.println("Day 11 p1: "+ monkeyBusiness);
    }

    private static class Monkey{
        // worry levels of items held by monkey
        private ArrayList<Long> _items = new ArrayList<Long>();
        // operator of the monkey's worry operation
        private char _operator;
        // true if the opearand of the worry operation is _constOperand, if false, use old value
        private boolean _useConstOperand=false;
        // constant operand to use if _useConstOperand is true
        private long  _constOperand;
        // value use din divisible-by test for where to toss item
        private long _testDivisor;
        // monkey to toss to if test evalueates to true
        private Monkey _trueTarget;
        // monkey to toss to if test evalueates to false
        private Monkey _falseTarget;
        // number of items inspected by this monkey
        private int _inspectionCount=0;

        public void addItem(long item) {
            _items.add(item);
        }
        public void setOperator(char operator){
            _operator = operator;
        }
        public void setConstOperand(long operand)
        {
            _constOperand = operand;
            _useConstOperand = true;
        }
        public void setTestDivisor(long divisor){
            _testDivisor=divisor;
        }
        public void setTrueTarget(Monkey target) {
            _trueTarget = target;
        }
        public void setFalseTarget(Monkey target) {
            _falseTarget = target;
        }
        public int getInspectionCount(){
            return _inspectionCount;
        }

        public void inspectAndThrowAll()
        {
            for(var item : _items)
            {
                _inspectionCount++;
                var newValue = calculateNewItemValue(item);
                var throwTo = ((newValue % _testDivisor) == 0)
                    ? _trueTarget
                    : _falseTarget;
                throwTo.addItem(newValue);
            }

            // all items have been thrown:
            _items.clear();
        }
        private long  calculateNewItemValue(long oldValue)
        {long  operand = _useConstOperand ? _constOperand : oldValue;
            long newValue;
            if (_operator == '*')
                newValue =  oldValue * operand;
            else
                newValue = oldValue + operand;

            newValue =newValue / 3;
            return newValue;
        }
    }
}
