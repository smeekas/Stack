import java.util.Stack;

class Solution {
    public int minLength(String s) {

        Stack<Character> st = new Stack<>();
        st.add(s.charAt(0));
        int i = 1;
        // if stack's-top+curr-element == "AB" or "CD"
        // pop current element
        for (; i < s.length(); i++) {
            char c = s.charAt(i);
            if (st.isEmpty()) {
                st.push(c);
                continue;
            }
            String newS = new String(st.peek() + "" + c);
            if ("AB".equals(newS) || "CD".equals(newS)) {
                st.pop();
                // pop A or C
                // we will not push B or D
            } else {
                st.push(c);
            }
        }
        return st.size();
    }
}