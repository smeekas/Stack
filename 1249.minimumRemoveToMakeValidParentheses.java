import java.util.Stack;

class Solution {
    public String minRemoveToMakeValid(String s) {
        return optimal(s);
    }

    String linear(String s) {

        // if parentheses is not valid we will replace it with -
        // at last we will replace all invalids as ""

        StringBuilder sb = new StringBuilder(s);
        Stack<Integer> st = new Stack<>(); // keep track of indices of '('s
        // we keep track of indices since we need them for string operation
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == '(') {
                st.push(i);
            } else if (c == ')') {
                if (!st.isEmpty()) {
                    st.pop();
                } else {
                    // stack is empty means ')' in invalid
                    sb.setCharAt(i, '-');
                }
            }
        }
        // all invalid ')' are marked as -.
        // stack contain invalid '('s

        while (!st.isEmpty())
            sb.setCharAt(st.pop(), '-');
        String ans = sb.toString().replaceAll("-", "");
        return ans;
    }

    String optimal(String s) {
        // MIK
        // idea is
        // from left to right, matching ) for (, we can find extra )s
        // from right to left, matching ( for ), we can find extra (s
        // first from left to right and then right to left
        // for left to right
        // we track opening para and match closing para
        // if we get extra ')' (when pointer is<=0), we ignore extra ')'

        // now we process, processed string
        // from right to left, we track closing para and match opening para
        // if we get extra '(' (when pointer is<=0), we ignore extra '('
        int opening = 0;
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                opening++;
                sb.append(c);
            } else if (c == ')') {
                if (opening > 0) {
                    opening--;
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }
        int len = sb.length();
        StringBuilder ans = new StringBuilder();
        int closing = 0;
        for (int i = len - 1; i >= 0; i--) {
            char c = sb.charAt(i);
            if (c == ')') {
                closing++;
                ans.append(c);
            } else if (c == '(') {
                if (closing > 0) {
                    closing--;
                    ans.append(c);
                }
            } else {
                ans.append(c);
            }
        }
        return ans.reverse().toString();
    }
}