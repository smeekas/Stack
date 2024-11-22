import java.util.Stack;

class Solution {
    public int evalRPN(String[] tokens) {
        // expression do not contain braces
        Stack<Integer> st = new Stack<>();
        for (String c : tokens) {
            switch (c) {
                case "+": {
                    int second = st.pop();
                    st.push(st.pop() + second);
                    break;
                }
                case "/": {
                    int second = st.pop();
                    st.push(st.pop() / second);
                    break;
                }
                case "*": {
                    int second = st.pop();
                    st.push(st.pop() * second);
                    break;
                }
                case "-": {
                    int second = st.pop();
                    st.push(st.pop() - second);
                    break;
                }
                default:
                    st.push(Integer.parseInt(c));
                    break;
            }
        }
        return st.peek();
    }
}