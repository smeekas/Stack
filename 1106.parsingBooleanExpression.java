import java.util.ArrayList;
import java.util.Stack;

class Solution {
    public boolean parseBoolExpr(String expression) {
        // we can push all the tokens in the stack
        // when we encounter ')' we pop everything till '(' and push calculated result
        // between '(' & ')' we will have only boolean expressions. we already would
        // have been evaluated inner brackets.
        Stack<Character> st = new Stack<>();
        boolean currResult = false;
        for (char c : expression.toCharArray()) {
            if (c == ')') {
                ArrayList<Boolean> sub = new ArrayList<>(); // collect booleans from bracket.
                while (!st.isEmpty() && st.peek() != '(') {

                    if (st.pop() == 't')
                        sub.add(true);
                    else
                        sub.add(false);
                }
                st.pop(); // pop '('
                char exp = st.pop(); // boolean operator in front of (). ex. &,|,!
                currResult = sub.get(0);
                for (int i = 1; i < sub.size(); i++) {
                    if (exp == '&') {
                        currResult &= sub.get(i);
                    } else if (exp == '|') {
                        currResult |= sub.get(i);
                    }
                }
                // if we have ! then inside bracket after ! we will have only one boolean
                // expression ex. !(t), !(f), !(&(t,f))
                //
                if (exp == '!')
                    currResult = !currResult;
                if (currResult)
                    st.push('t');
                else
                    st.push('f');
            } else if (c == ',') {

            } else {
                st.push(c);
            }
        }
        // true if 't'
        return st.pop() == 't';
    }
}