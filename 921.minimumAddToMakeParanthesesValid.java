import java.util.Stack;

class Solution {
    public int minAddToMakeValid(String s) {
        // return linear(s);
        return optimal(s);

    }

    int linear(String s) {
        // we find invalid cases
        // we need same number of paras to make it valid

        // code is almost same as validate paras

        Stack<Character> st = new Stack<>(); // stack of '('s
        int invalid = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                st.push(c);

            } else if (c == ')') {
                if (st.isEmpty()) {
                    // this is invalid case

                    invalid++;
                } else {
                    // para is valid. so pop '('
                    st.pop();
                }
            }
        }
        // final ans is all invalid ')'s from invalid variable and remaining '('s from
        // stack
        return invalid + st.size();
    }

    int optimal(String s) {
        // we try to validate stack
        // we can validate stack by single variable too
        int invalid = 0;
        int open = 0; // variable for validating stack
        // if '(' the open++:
        // if ')' and open>0 then decrease '(' count
        // if open<=0 means we do not have '('s. that will be counted as invalid case
        for (char c : s.toCharArray()) {
            if (c == '(') {
                open++;
            } else {
                if (open > 0) {
                    open--;
                } else {
                    invalid++;
                }
            }
        }
        return invalid + open;
    }
}