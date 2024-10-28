class Solution {
    public boolean backspaceCompare(String s, String t) {
        // O(n) time & space approach is to use stack, simulate it, get string from it and then compare them
        // O(1) space approach is given below

        // idea is that we encounter "#", we need to go back in string
        // we will go from end of the string to start of the string
        // if we encounter "#" then we keep count of it. after finishing them we will skip that many characters in string

        // we will process both string s and string t
        int i=s.length()-1;
        int j=t.length()-1;

        int count1=0;
        int count2=0;

        while (i>=0 || j>=0){
// if any one them is not finished yet, we will continue the loop as we need to process both the strings

            while (i>=0){
            // if char is "#", increase count and go to next char
            // if char is not "#" and #-count is >0 then skip current char and decrease the count.
            // if above two are false then break the loop
                    if(s.charAt(i)=='#'){
                        count1++;i--;
                }else if(count1>0){
                        count1--;i--;
                    }else{
                        break;
                    }
            }

            while (j>=0){
                if(t.charAt(j)=='#'){
                    count2++;j--;
                }else if(count2>0){
                    count2--;j--;
                }else{
                    break;
                }
            }
            // now we are at the index where we can compare string
            // if any string's index is out of bound then assume some dummy char else we will get error.
            char sc=i>=0?s.charAt(i):'!';
            char st=j>=0?t.charAt(j):'!';
            // if both character matches then reduces both string pointer
            if( sc==st) {
                i--;
                j--;
            }else{
                // character do not match, so just return false
                return  false;
            }
        }
        // after all iteration, both string pointer are same? means string is same.
        // after iteration is finished we will be at where string should have been started.
        // 0, -1, -2 etc... (-ve if we have tons of #)
        return i==j;

    }
}