import java.util.Stack;

class Solution {
    public int maxDepth(String s) {
        // just track stack size

        // return linear(s);
        return optimal(s);

    }

    int linear(String s) {
        Stack<Character> st = new Stack<>();
        int max = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                st.push(c);
                max = Math.max(st.size(), max);
            } else if (c == ')') {
                st.pop();
            }
        }
        return max;
    }

    int optimal(String s) {
        int open = 0;
        int max = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                open++;
                max = Math.max(max, open);
            } else if (c == ')') {
                open--;
            }
        }
        return max;
    }
}