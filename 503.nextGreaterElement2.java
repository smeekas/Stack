import java.util.Stack;

class Solution {
    public int[] nextGreaterElements(int[] nums) {
        // logic is same as next greater.
        // how to handle circular array??
        // when we can concat same array (double the size)
        // ex. 1,2,3...n,1,2,3...n
        // this way even last element can find it's next greater in same array.
        int n = nums.length;
        int n2 = n + n;
        int num[] = new int[n2];
        for (int i = 0; i < (n2); i++) {
            num[i] = nums[i % n]; // copy element into new array of double size
        }
        Stack<Integer> st = new Stack<>();
        int ans[] = new int[n];
        for (int i = n2 - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                st.push(i);
                ans[i % n] = -1; // ans array is of n only. so we have to %n of current index(which is from array of double size)
            } else {
                while (!st.isEmpty() && num[i] >= num[st.peek()]) {
                    st.pop();
                }
                if (st.isEmpty()) {
                    ans[i % n] = -1;
                } else {
                    ans[i % n] = num[st.peek()];
                }
                st.push(i);
            }
        }
        return ans;
    }
}