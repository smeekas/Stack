import java.util.Stack;

class Solution {
    public int minSwaps(String s) {
        // return linear(s);
        return optimal(s);
    }

    // TC O(n)
    // SC O(n)
    int linear(String s) {
        // we have equal number of ] and [. so we always can make parentheses valid
        // idea is that we will remove valid parentheses
        // stack will have invalid entries
        // for every ']' if '[' exists in stack we pop it (valid para)
        // for ']' if '[' do not exists in stack, we do nothing. we don't push ']'
        // at the end stack will have all [s, we have not pushed same number of ]s
        // in the string after removing valid paras, string looks like ]]][[[
        // from this type of looking string we have [[[ in stack
        // how many swaps needed?
        // one swap can make two para valid
        // for 3 paras(total 3 ']' and 3 '['), we need 2 swaps (one swap can valid two para,
        // 1 extra swap to make 1 para valid)
        // for 4 paras, we need 2 paras (one swap can valid two para)
        // so (stack-size+1)/2 swaps we will need
        Stack<Character> st = new Stack<>(); // for '['s
        for (char c : s.toCharArray()) {
            if (c == '[') {
                st.push(c);
            } else {
                if (!st.isEmpty() && st.peek() == '[')
                    st.pop();

            }
        }
        return (int) Math.floor((st.size() + 1) / 2);
    }

    // TC O(n)
    // SC O(1)
    int optimal(String s) {
        // in linear we are just tracking stack size
        // we can do that with variable
        int size = 0;
        for (char c : s.toCharArray()) {
            if (c == '[')
                size++;
            // size counts [s
            else {
                // in else means ]
                // if [ count is > 0 then we have valid para.
                // so we decrease [
                // in the end we will have count of [ just like in stack solution.
                if (size > 0)
                    size--;
            }
        }
        return (int) Math.floor((float) (size + 1) / 2.0);
    }
}