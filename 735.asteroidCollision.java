import java.util.Stack;

class Solution {
    // TC O(n)=> maximum n pushing, n popping is possible
    // SC O(n)=> stack and array
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> st = new Stack();
        for (Integer curr : asteroids) {
            if (st.isEmpty()) {
                st.push(curr);
                continue;
            }
            if (st.peek() > 0 && curr < 0) {
                // st.peek()==>> <<==curr
                // only case where collision can happen
                // we delete until collisions stop happening
                while (!st.isEmpty() && st.peek() > 0 && curr < 0) {
                    int deleted = st.pop();
                    int sum = deleted + curr;
                    if (sum == 0) {
                        curr = 0; // this will break the loop
                        // in this case we will not add element
                    }
                    // sum>0 left element won(positive)
                    // sum<0 right element won (negative)
                    int ele = sum > 0 ? deleted : curr;
                    curr = ele; // after collision, we will check with whoever left.
                    // from this collision, whoever is left, we will check with next stack element
                }
                if (curr != 0)
                    st.push(curr); // if we haven't encountered same weighted asteroid, then push whoever left
            } else {
                st.push(curr);
            }
        }

        // save stack answer in array
        int n = st.size();
        int ans[] = new int[n];
        n--;
        while (!st.isEmpty()) {
            ans[n] = st.pop();
            n--;
        }
        return ans;
    }
}