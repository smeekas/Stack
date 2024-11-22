import java.util.Stack;

class Solution {
    public int calculate(String s) {
        // supported chars, +,-,*,/,numbers,' '
        Stack<Integer> st = new Stack<>();
        // for * and / we must have numbers from both sides.
        // for + and - we can perform them at last.
        // for -, we can number just after - as -number.
        // how to process * and /??
        // we can try to track previous sign. that way we can perform operation after
        // having both numbers.
        int number = 0;
        char sign = '+'; // initially previous sign is +.
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') {
                number = number * 10 + (c - '0'); // this way we can build our number.
            } else if (c != ' ') {
                // current char c is some sign
                // sign variable represent previous sign.
                // c being some sign also means that number which we were building is also over.
                if (sign == '+') {
                    st.push(number); // prev number
                } else if (sign == '-') {
                    st.push(-number); // prev number
                } else if (sign == '*') {
                    // prev sign was *
                    st.push(st.pop() * number);
                } else if (sign == '/') {
                    // prev sign was /
                    st.push(st.pop() / number);
                }
                sign = c; // current char(sign) will be now previous sign.
                number = 0; // reset previous number.
            }

        }
        // after finishing iteration, we number have some number which were building at
        // last.
        if (sign == '+') {
            st.push(number); // prev number
        } else if (sign == '-') {
            st.push(-number); // prev number
        } else if (sign == '*') {
            // prev sign was *
            st.push(st.pop() * number);
        } else if (sign == '/') {
            // prev sign was /
            st.push(st.pop() / number);
        }
        // now stack contain all number( * and / are evaluated)
        // we now have to evaluate + and -
        int ans = 0;
        for (int i : st) {
            ans += i;
        }
        return ans;
    }
}