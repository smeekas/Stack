import java.util.Stack;

class Solution {

    // TC: O(n) max n pushes and n popes
    // SC: O(n) answer and stack
    public String removeDuplicateLetters(String s) {

        // what we can do??
        // approach like remove k digit will not work here..
        // we want to remove duplicates.
        // so we can store their frequency first
        // we also want string to be lexicographically smallest
        // every char will taken once.

        // Count frequency of each character in string
        // This helps us know if we can safely remove a character
        // when we encounter it multiple times
        int count[] = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        int n = s.length();
        // Stack maintains characters in our result
        // We'll pop characters if we find a smaller char that should come before them
        Stack<Character> st = new Stack<>();

        // Keep track of characters we've already added to our result
        // Prevents adding same character twice
        boolean visited[] = new boolean[26];
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            count[c - 'a']--; // Decrease frequency as we process this char

            // Skip if we've already used this character in our result
            if (visited[c - 'a'])
                continue;
            if (st.isEmpty()) {
                // if stack is empty then we have nothing to compare current character
                // so we simply add current character
                st.add(c);

                visited[c - 'a'] = true;

            } else {
                // While stack is not empty and:
                // 1. Current char is smaller than top of stack (lexicographically smaller)
                // 2. The char at top of stack still appears later (count > 0)
                // Then we can safely pop and try to find a better position for that char
                while (!st.isEmpty() && c <= st.peek() && count[st.peek() - 'a'] > 0) {

                    visited[st.peek() - 'a'] = false;
                    // we mark current character as not visited, since we will be looking for
                    // st.peek() character later in the string
                    st.pop();
                }
                // Add current char to result and mark as visited
                st.add(c);

                visited[c - 'a'] = true;

            }
        }
        // build string
        StringBuffer sb = new StringBuffer();
        while (!st.isEmpty())
            sb.append(st.pop());
        return sb.reverse().toString();
    }
}
