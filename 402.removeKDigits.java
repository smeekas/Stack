import java.util.Stack;

class Solution {
    // TC: O(n) maximum n pushed and popes
    // SC: O(n)
    public String removeKdigits(String num, int k) {
        // optimal
        // we need to remove k digits.
        // we should try to remove them from front first to mak number as small as
        // possible
        // for current number, if it is smaller than previous numbers then we can delete
        // previous numbers until...
        // 1. there is no more number to delete (curr number will become first number)
        // 2. curr element become bigger than previous numbers.

        // NOTE: we can delete only k numbers
        // so in short we need to maintain increasing order of numbers
        // STACK can fulfill our requirement
        char[] c = num.toCharArray();
        int n = c.length;
        Stack<Integer> st = new Stack<>();
        if (k == c.length) {
            // we need to remove all numbers. so result will be 0
            return "0";
        }
        for (int i = 0; i < n; i++) {
            int cc = c[i] - '0'; // curr number
            if (k == 0) {
                // we cannot delete any numbers now as k is 0.
                // so we will just add curr number in stack
                st.add(cc);
                continue;
            }
            if (st.isEmpty()) {
                // stack is empty. so there is no previous number to delete
                st.add(cc);
            } else {
                while (!st.isEmpty() && k > 0 && cc < st.peek()) {
                    // if stack is not empty and curr number is smaller than previous number AND k>0
                    // (we can still delete numbers)
                    k--;
                    st.pop();
                }
                // add curr number
                st.add(cc);
            }
        }
        // if k is still greater than 0 means that we where not able to delete elements
        // due to cc<st.peek() means that elements are similar(11111) or
        // increasing(23456).
        // now we can delete elements from back only.
        // for similar elements it do not matter
        // for increasing elements,
        // if we delete from front then it will become 3456
        // so that means if we delete from front then first element will become bigger
        // so we need to delete from back
        while (k-- > 0 && !st.isEmpty())
            st.pop();
        StringBuffer ans = new StringBuffer();

        while (!st.isEmpty()) {
            // collect numbers
            ans.append(st.pop());
        }
        // due to stack, numbers will be in reverse order in String
        ans = ans.reverse(); // so reverse it
        // now we don't want leading zeros.
        // so delete them if they exists
        while (ans.length() > 0 && ans.charAt(0) == '0') {
            ans.deleteCharAt(0);
        }
        if (ans.length() == 0) {
            // if string is empty then we need to return "0"
            ans.append(0);
        }
        return ans.toString();
    }
}