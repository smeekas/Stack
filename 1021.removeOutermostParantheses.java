import java.util.Stack;

class Solution {
    public String removeOuterParentheses(String s) {
        // return linear(s);
        return optimal(s);

    }

    String linear(String s) {
        // idea is
        // we can track outer most parantheses by stack size
        // if size is 0 means '(' is outer most
        // if outermost then don't push it into answer
        // same for ')'
        Stack<Character> st = new Stack<>();
        StringBuffer sb = new StringBuffer();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (st.size() != 0) {
                    sb.append(c);
                }
                st.push(c);
            } else {
                if (st.size() == 1) {
                    st.pop();
                } else {
                    st.pop();
                    sb.append(')');
                }
            }
        }
        return sb.toString();
    }

    String optimal(String s) {

        // same we track and validate opening and closing para by variable instead of
        // stack
        StringBuffer sb = new StringBuffer();
        int open = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (open != 0)
                    sb.append(c);
                open++;
            } else {
                if (open == 1) {
                    open--;
                } else {
                    open--;
                    sb.append(')');
                }
            }
        }
        return sb.toString();
    }
}