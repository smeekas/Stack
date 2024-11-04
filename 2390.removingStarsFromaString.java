import java.util.Stack;

class Solution {
    public String removeStars(String s) {
        // return linear(s);
        return constant(s);
    }

    String linear(String s) {

        Stack<Character> st = new Stack<>();
        // for every * if stack is not empty pop element else push current element into
        // stack
        for (char c : s.toCharArray()) {
            if (c == '*' && !st.isEmpty())
                st.pop();
            else
                st.push(c);
        }
        // collect element from stack
        StringBuffer sb = new StringBuffer();
        while (!st.isEmpty())
            sb.append(st.pop());
        return sb.reverse().toString();
    }

    String constant(String s) {
        StringBuffer ans = new StringBuffer();

        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '*') {
                // if char is * means one character must be deleted from string
                // char at j must be deleted, we will decrement j ptr.
                // so next character will be inserted before j.
                // and for the final answer we will discard anything beyond j.
                j--;
            } else {
                // if char is not * then push char c and increment pointer.
                ans.insert(j, c);
                j++;
            }
        }

        return ans.substring(0, j).toString();
    }

}