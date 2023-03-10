import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 {
    public static void Run(List<String> input) {

        p1(input);
        //p2(input);
        
    }

    private static Monkey[] processInput(List<String> input) {
        // 7 input lines per monkey, except for the last which is 6
        int monkeyCount = ((input.size() - 6) / 7) + 1;
        var monkeys = new Monkey[monkeyCount];
        for (int i = 0; i < monkeyCount; i++) {
            monkeys[i] = new Monkey();
        }

        for (int i = 0; i < monkeyCount; i++) {
            int startLine = i * 7; // "Monkey 0:"
            // +1 " Starting items: 79, 98"
            var items = input.get(startLine + 1)
                    .substring("  Starting items: ".length())
                    .split(", ");
            for (var item : items)
                monkeys[i].addItem(Long.parseLong(item));

            // +2 " Operation: new = old * 19"
            var operation = input.get(startLine + 2)
                    .substring("  Operation: new = old ".length());
            monkeys[i].setOperator(operation.charAt(0));
            if (!operation.substring(2).equals("old"))
                monkeys[i].setConstOperand(Integer.parseInt(operation.substring(2)));

            // +3 " Test: divisible by 23"
            monkeys[i].setTestDivisor(Integer.parseInt(
                    input.get(startLine + 3).substring("  Test: divisible by ".length())));

            // +4 " If true: throw to monkey 2"
            int targetIndex = Integer.parseInt(input.get(startLine + 4)
                    .substring("    If true: throw to monkey ".length()));
            monkeys[i].setTrueTarget(monkeys[targetIndex]);

            // +5 " If false: throw to monkey 3"
            targetIndex = Integer.parseInt(input.get(startLine + 5)
                    .substring("    If false: throw to monkey ".length()));
            monkeys[i].setFalseTarget(monkeys[targetIndex]);
            }

            long lcm=1;
            for(var monkey: monkeys)
                lcm *= monkey.getTestDivisor();
            for (var monkey: monkeys)
                monkey.setLcm(lcm);
        return monkeys;
    }

    private static void p1(List<String> input) {
        var monkeys = processInput(input);
        // run 20 rounds
        for (int round = 0; round < 20; round++) {
            for (var monkey : monkeys)
                monkey.inspectAndThrowAll();
        }

        // get all inspection counts:
        var inspectionCount = new long[monkeys.length];
        for (int i = 0; i < monkeys.length; i++)
            inspectionCount[i] = monkeys[i].getInspectionCount();

        // multiply top 2 inspection counts:
        Arrays.sort(inspectionCount);
        long  monkeyBusiness = (inspectionCount[inspectionCount.length - 1])
            * inspectionCount[inspectionCount.length - 2];
        System.out.println("Day 11 p1: " + monkeyBusiness);
    }

    private static void p2(List<String> input) {
        String backspaces="\b\b\b\b\b\b\b\b\b\b\b";
        var monkeys = processInput(input);
        for (var monkey : monkeys)
            monkey.setApplyReliefFactor(false);

        // run 10000 rounds
        for (int round = 1; round <= 10000; round++) {
            for (int m=0;m < monkeys.length;m++){
                System.out.print(backspaces + round +"."+m);
                monkeys[m].inspectAndThrowAll();
            }
            if ((round == 1) || (round==20) || ((round %1000)==0))
            {
                System.out.println(backspaces+"== After round "+ round + " ==");
                for (int j=0;j < monkeys.length; j++)
                    System.out.println("Monkey "+j+" inspected items "+monkeys[j].getInspectionCount()+" times.");
            }
        }

        // get all inspection counts:
        var inspectionCount = new long[monkeys.length];
        for (int i = 0; i < monkeys.length; i++)
            inspectionCount[i] = monkeys[i].getInspectionCount();

        // multiply top 2 inspection counts:
        Arrays.sort(inspectionCount);

    }

    private static class Monkey {
        // worry levels of items held by monkey
        private ArrayList<Long> _items = new ArrayList<Long>();
        // operator of the monkey's worry operation
        private char _operator;
        // true if the opearand of the worry operation is _constOperand, if false, use
        // old value
        private boolean _useConstOperand = false;
        // constant operand to use if _useConstOperand is true
        private long  _constOperand;
        // value use din divisible-by test for where to toss item
        private long _testDivisor;
        // monkey to toss to if test evalueates to true
        private Monkey _trueTarget;
        // monkey to toss to if test evalueates to false
        private Monkey _falseTarget;
        private static final long _reliefFactor = 3;
        // true if new item values should be divided by _reliefFactor
        private boolean _applyReliefFactor = true;
        // least common multiple of all monkeys' test divisors
        private long _lcm=1;
        // number of items inspected by this monkey
        private long  _inspectionCount = 0;

        public void addItem(long item) {
            _items.add(item);
        }

        public void setOperator(char operator) {
            _operator = operator;
        }

        public void setConstOperand(int operand) {
            _constOperand =operand;
            _useConstOperand = true;
        }

        public void setTestDivisor(int  divisor) {
            _testDivisor = divisor;
        }
        public long getTestDivisor(){
            return _testDivisor;
        }
        public void setTrueTarget(Monkey target) {
            _trueTarget = target;
        }

        public void setFalseTarget(Monkey target) {
            _falseTarget = target;
        }

        public void setApplyReliefFactor(boolean apply) {
            _applyReliefFactor = apply;
        }
        public void setLcm(long lcm){
            _lcm=lcm;
        }

        public long getInspectionCount() {
            return _inspectionCount;
        }

        public void inspectAndThrowAll() {
            // calculate all tosses in parallel
            var tosses = _items.parallelStream()
                .map(i -> calculateNewValue(i))
                .map(i -> mapDestinationPair(i))
                .toList();
            
                // perform tosses sequentially because ArrayList may not handle parallel access:
            for(var toss : tosses)
                toss.target.addItem(toss.item);
            
            // all items have been thrown:
            _inspectionCount += _items.size();
            _items.clear();
        }

        private long calculateNewValue(long item){
            long  newValue;
            
            newValue = item %= _lcm;
            if (newValue==0)
                newValue = _lcm;
                
            if (_operator == '*'){
                if(_useConstOperand)
                    newValue *= _constOperand;
                else
                    newValue *= item;
            }
            else{
                if (_useConstOperand)
                    newValue +=  _constOperand;
                else
                    newValue +=  item; 
            }
//            if (newValue < item)
//                System.out.println("possible overflow");

            if (_applyReliefFactor)
                newValue /=  _reliefFactor;


            return newValue;
        }
        private Destination mapDestinationPair(long item)
        {
            var throwTo = ((item % _testDivisor)==0)
                    ? _trueTarget
                    : _falseTarget;
            return new Destination(item,throwTo);
        }
        private class Destination{
            public long item;
            public Monkey target;
            public Destination(long i, Monkey t){
                item = i;
                target = t;
            }
            
        }
    }
}
