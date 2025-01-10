import java.util.Stack;

class Solution {
    public String robotWithString(String s) {
        Stack<Character> st = new Stack<>();
        int n = s.length();
        // smallest letter from string can be from anywhere.
        // our priority is to bring it forward.
        // one approach can be to,
        // for current character c from string s, if it is bigger than top of
        // stack(stack is t)
        // then we pop character from t(stack) until stack is empty or, curr character
        // is smaller than top of stack
        // but this way we will miss smallest character if it exists on right side of
        // current character

        // better approach
        /*
         * stack is our "t"
         * since we want to minimize the string, somehow we have to take smallest
         * character first
         * 
         * we can calculate frequency of every character
         * we can remove characters from 's' from push to "t" until we reach smallest
         * character
         * if stack('t') contain smallest character than string('s') then we can remove
         * character from stack-t and print it
         * 
         * 
         */
        StringBuilder sb = new StringBuilder();
        int[] freq = new int[26];

        for (char c : s.toCharArray())
            freq[c - 'a']++;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            st.push(c); // push current character into stack-t
            freq[c - 'a']--; // since we have pushed current character on stack-t. it do not exists on
                             // string-s so decrease frequency

            // Check the stack's top and decide if it can be popped (added to the result).
            // We can pop the top character from the stack if it's smaller or equal to the
            // smallest character still remaining in the string `s`.
            while (!st.isEmpty() && st.peek() <= lowest(freq)) {
                // Pop from the stack and append to the result.

                sb.append(st.pop());
            }
        }
        while (!st.isEmpty())
            sb.append(st.pop());

        return sb.toString();
    }

    char lowest(int[] freq) {
        // Iterate through the frequency array to find the first non-zero frequency.
        // This corresponds to the smallest character still present in the remaining
        // string `s`.
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] != 0) {
                // if frequency is not zero then return that character.
                // this character will be minimum
                return (char) ('a' + i);
            }
        }
        // If all frequencies are zero (should not happen in normal flow),
        // return 'a' as a fallback. This case will not affect the result because
        // no character in the stack will be smaller than 'a'.
        return 'a';
    }
}