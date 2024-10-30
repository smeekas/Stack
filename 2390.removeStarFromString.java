import java.util.Stack;

class Solution {
    public String removeStars(String s) {
//return  linear(s);
return  constant(s);
    }
    String linear(String s){

        Stack<Character> st=new Stack<>();
        // for every * if stack is not empty pop element else push current element into stack
        for(char c:s.toCharArray()){
            if(c=='*' && !st.isEmpty())st.pop();
            else st.push(c);
        }
        // collect element from stack
        StringBuffer sb=new StringBuffer();
        while (!st.isEmpty())sb.append(st.pop());
        return  sb.reverse().toString();
    }
    String constant(String s){
        StringBuffer ans=new StringBuffer();
            // two pointer
            // one point to current character
            // second point to end of actual string after deletion
        int j=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c=='*'){
                // reduce j. so on next character we will over-write the character which is on j.
                    j--;
            }else{
                ans.insert(j,c);
                // insert character at j
                j++;
            }
        }
        // our string is present at 0..j
        return ans.substring(0,j).toString();
    }

}