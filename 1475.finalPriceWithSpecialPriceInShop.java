import java.util.Stack;

class Solution {
    // same to same as Next Greater Element
    // for every element we subtract next great element from current price
    public int[] finalPrices(int[] prices) {
        int n = prices.length;
        int ans[] = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                ans[i] = prices[i];
                st.push(i);
                continue;
            }
            while (!st.isEmpty() && prices[i] < prices[st.peek()]) {
                st.pop();
            }
            if (st.isEmpty()) {
                ans[i] = prices[i];
            } else {
                ans[i] = prices[i] - prices[st.peek()];
            }
            st.push(i);
        }
        return ans;
    }
}