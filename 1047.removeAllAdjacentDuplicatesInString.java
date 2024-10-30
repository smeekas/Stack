import java.util.Stack;

class Solution {
  public String removeDuplicates(String s) {
    Stack<Character> st = new Stack<>();
    // if current char and stack's top matches then remove stack's top
    // just like 2696
    for (char c : s.toCharArray()) {
      if (st.isEmpty()) {
        st.add(c);
      } else {

        if (st.peek() == c) {
          st.pop();
        } else {
          st.push(c);
        }

      }
    }
    StringBuffer sb = new StringBuffer();
    while (!st.isEmpty()) {
      sb.append(st.pop());
    }
    sb.reverse();
    return sb.toString();
  }

}