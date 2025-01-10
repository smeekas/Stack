import java.util.ArrayList;
import java.util.HashMap;

// push & pop TC: O(1) 
// overall space complexity O(n+n)
class FreqStack {
    /*
     * what we can do is
     * whenever we can any element, we will count it's frequency
     * we also maintain hashMap of frequency->list of element with that frequency
     * if we get element 5 we add it in key 1. 1->5
     * if we get element 4 we add it in key 1. 1->5,4 (this way insert order is also
     * maintained)
     * now if we get element 5 again we add it in key 2. 2->5
     * we also have to track maximum frequency at current moment. for that we
     * maintain MAX variable
     */
    HashMap<Integer, ArrayList<Integer>> hm;
    HashMap<Integer, Integer> freq;

    int max = 1; // default we need to be at frequency 1. we will not have element with 0
                 // frequency

    public FreqStack() {
        hm = new HashMap<>();
        freq = new HashMap<>();
    }

    public void push(int val) {
        if (freq.containsKey(val)) {
            // val element already exists
            int newFrequency = freq.get(val) + 1;
            // we we do not have key newFrequency in map then add new array for newFrequency
            hm.putIfAbsent(newFrequency, new ArrayList<>());
            hm.get(newFrequency).add(val); // add current value in new frequency array
            max = Math.max(max, newFrequency); // make newFrequency maximum if it is
            freq.put(val, newFrequency); // also update frequency of val
        } else {
            // if val do not exists in frequency map then it is new element
            // its frequency will be 1
            hm.putIfAbsent(1, new ArrayList<>());
            hm.get(1).add(val);
            freq.put(val, 1);
        }
    }

    public int pop() {
        int arrSize = hm.get(max).size(); // array's size of max frequency
        if (arrSize > 0) {
            // if element exists
            int popped = hm.get(max).remove(arrSize - 1); // get last element
            if (hm.get(max).size() == 0) {
                // if this max frequency array is now empty then new max will be max-1
                // we won't have frequency array of 1,4,7,8 like that
                // because we are popping from last only(last in the sense maximum at current
                // point)
                max--;
            }
            // also update frequency of popped element
            freq.put(popped, freq.get(popped) - 1);
            return popped;
        }
        // if we are here means, user is popped all elements and trying more.
        // so return -1
        return -1;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */