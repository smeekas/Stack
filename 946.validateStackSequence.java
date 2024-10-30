import java.util.Stack;

class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        return linear(pushed, popped);
    }

    boolean linear(int[] pushed, int[] popped) {
        int i = 0, j = 0;
        int n = pushed.length, m = popped.length;
        Stack<Integer> st = new Stack<>(); // to track pushed element
        // if we want to pop any element then that element also must be pushed first.

        while (j < m) {
            int pop = popped[j]; // element which we want to pop
            if (!st.isEmpty() && st.peek() == pop) { // if popped element is pushed and is on top of stack that means we
                                                     // can delete it
                st.pop();
                j++;
            } else {
                // if element is not present on top of stack

                if (i == n) {
                    // if we have pushed all element on stack and pushed array is over means given sequence is wrong
                    return false;
                }
                for (; i < n; i++) {
                    //from current element we go to right side until we found element which we wanted to pop

                    if (pushed[i] != pop) {
                        // element not same to push onto stack
                        st.add(pushed[i]);
                    } else {
                        // element matched.
                        // assume we pushed and popped(since matching)
                        // sp increase i because we just pushed
                        // and increase j because we just popped
                        j++;
                        i++;
                        break;
                    }
                }
            }
        }
        return true;
    }
}