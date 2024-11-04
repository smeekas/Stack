import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
    return  linear(s);
    }
    boolean linear(String s){
        Stack<Character> st=new Stack<>();
        // from the answer we will try to remove all 'abc'.
        // for char 'a' and 'b', we push the char
        // for char 'c', we pop 'b' and 'a'
        // if we cannot pop 'b' and 'a' then string is not valid
        for(char c:s.toCharArray()){
            if(c=='c'){
                int poppedCount=0;
                if(!st.isEmpty() && st.peek()=='b'){
                    st.pop();
                    poppedCount++;
                }

                if(!st.isEmpty() && st.peek()=='a'){
                    st.pop();
                    poppedCount++;
                }
                // if poppedCount is not 2 means we were not able to pop 'b' and then 'a'
                // return false.
                if(poppedCount!=2)return false;

            }else {
                // c is 'a' or 'b'.
                st.add(c);
            }
        }
        // after iteration, if stack is empty means we were able to remove all "abc" and string is empty now. return true
        return st.size()==0;
    }
}