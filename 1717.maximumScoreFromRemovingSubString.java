import java.util.Stack;

class Solution {
    public int maximumGain(String s, int x, int y) {
        // return linear(s,x,y);
        return optimal(s, x, y);

    }

    int remove(StringBuffer s, StringBuffer t, int score) {
        Stack<Character> st = new Stack<>();
        int curr = 0;
        String ts = t.toString();
        for (char c : s.toString().toCharArray()) {
            if (st.isEmpty()) {
                st.add(c);
                continue;
            }
            String currCombined = new String(st.peek() + "" + c); // combination of stack's peek and curr char matches
                                                                  // then we will not push curr char and pop from stack
            if (ts.equals(currCombined) == true) {
                // since matched we will increase score
                curr += score;
                st.pop();
            } else {
                st.push(c);
            }
        }
        // reset stringBuffer
        s.setLength(0);
        while (!st.isEmpty()) {
            // push new string which is in stack into stringBuffer
            // this will also modify original stringBuffer.
            s.append(st.pop());
        }
        s.reverse();
        return curr;
    }

    int linear(String s, int x, int y) {
        // if x is higher than first we remove "ab" and then "ba"
        // else we first remove "ba" and then "ab";

        // if both x==y then we can remove any one first. as score will remain same. x
        // then y or y then x.
        int total = 0;
        int max = x >= y ? x : y; // max score
        int min = x > y ? y : x; // min score
        StringBuffer maa = new StringBuffer(x >= y ? "ab" : "ba"); // max str
        StringBuffer mia = new StringBuffer(x < y ? "ab" : "ba"); // min str
        StringBuffer bufferedS = new StringBuffer(s);
        // remove string one by one.
        total += remove(bufferedS, maa, max);
        total += remove(bufferedS, mia, min);
        return total;
    }

    int optimal(String s, int x, int y) {
        // optimal for other languages (string is immutable in JAVA)
        // our remove function from linear uses O(n);
        // we can use two pointers and do operations in string.
        int total = 0;
        int max = x >= y ? x : y;
        int min = x > y ? y : x;
        StringBuffer maa = new StringBuffer(x >= y ? "ab" : "ba");
        StringBuffer mia = new StringBuffer(x < y ? "ab" : "ba");
        StringBuffer bufferedS = new StringBuffer(s);
        total += removeOptimal(bufferedS, maa, max);
        total += removeOptimal(bufferedS, mia, min);
        return total;
    }

    int removeOptimal(StringBuffer s, StringBuffer str, int score) {

        int i = 0; // read
        int j = 0; // write
        int inc = 0;
        // we will do operation on string (stringBuffer)
        // idea is we copy char from j into i
        // we increase both of them
        // now if char from i-2 and i-1 are equal to str
        // we decrease i by 2 (str is of length 2) (so kind of deleted) (next time char
        // from j will override char on i)
        // at last after finishing it, we take only 0,i. so we remove (i,len) string
        String tt = str.toString();
        while (j < s.length()) {
            s.setCharAt(i, s.charAt(j));
            i++;
            j++;
            if (i > 1) {
                if (new String(s.charAt(i - 2) + "" + s.charAt(i - 1)).equals(tt)) {
                    i -= 2;
                    inc++;
                }

            }
        }
        s.replace(i, s.length(), "");
        return inc * score;
    }

}