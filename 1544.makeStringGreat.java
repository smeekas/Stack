import java.util.Stack;

class Solution {
    public String makeGood(String s) {
    return  linear(s);
    }
    String linear(String s) {
        Stack<Character> st=new Stack();
        // for every character we see that it's equivalent character is present on top of stack or not
        // if present then we pop that equivalent and do not push current char
        // finally we collect characters from stack and construct string
        for(char c:s.toCharArray()){
            if(st.isEmpty()){
                st.add(c);
            }else{
                //'A'+(c-'a') => char c's capital letter equivalent
                //'a'+(c-'A') =>char c's small letter equivalent
                // if from any two above, we get true means we have to pop.
                if(st.peek()== 'A'+(c-'a')||st.peek()== 'a'+(c-'A') ){
                    st.pop();
                }else{
                    st.add(c);
                }
            }
        }
        StringBuffer sb=new StringBuffer();
        while(!st.isEmpty()){
            sb.append(st.pop());
        }
        sb.reverse();
        return sb.toString();
    }
}