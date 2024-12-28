import java.util.Stack;

class Solution {
    public int maxSumMinProduct(int[] nums) {
        // return bruteforce(nums) ;
        return optimal(nums);

    }

    // TC: O(n^2)
    // SC: O(1)
    int bruteforce(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            int min = Integer.MAX_VALUE;
            for (int j = i; j < n; j++) {
                // we will keep sum of i..j
                // we will keep track of min of array [i..j]
                min = Math.min(min, nums[j]);
                sum += nums[j];
                // if min*sum is maximum then update the answer
                ans = Math.max(ans, min * sum);
            }
        }
        return ans;
    }

    // TC: O(n)
    // SC: O(n)
    int optimal(int[] nums) {
        // prerequisite is sum of subarray minimum (907)
        // we need two things for every subarray.
        // minimum of subarray and sum of that subarray
        // we need to maximize the result.
        // what we can do is
        // we can find prev/next min of every element
        // this will give use maximum size of array where current element is minimum
        // but how to find sum of elements from element i to j?
        // we can use prefix sum!
        // prefix[j]-prefix[i-1] == sum of elements from element-i to element-j
        int n = nums.length;
        long prefix[] = new long[n]; // due to big constraints we need to sum in long
        prefix[0] = nums[0];
        long MOD = (long) 1e9 + 7;
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }
        int prevMin[] = prevSmaller(nums, n); // instead of index of prevSmaller elements, it will give is index of
                                              // boundary element
        int nextMin[] = nextSmaller(nums, n); // instead of index of nextSmaller elements, it will give is index of
                                              // boundary element
        long ans = 0;
        for (int i = 0; i < n; i++) {

            if (prevMin[i] == 0) {
                ans = Math.max(ans, (long) (prefix[nextMin[i]]) * (long) nums[i]);
                // we have to include current element.
                // but we cannot subtract prefix[min-1] here min is 0
                // prefix[-1] will throw error
                // prefix[nextMin] includes value of prefix[0]. so we don't have to subtract
                // anything here
            } else {
                // current element is minimum in subarray from prevMin to nextMin.
                // sum of element from prevMin to nextMin is prefix[nextMin]-prefix[prevMin-1];
                // to sum prevMin to nextMin, we have to subtract prevMin-1
                ans = Math.max(ans, (long) (prefix[nextMin[i]] - prefix[prevMin[i] - 1]) * (long) nums[i]);
            }

        }
        ans = ans % MOD;
        return (int) ans;

    }

    // nextSmaller and prevSmaller needs to follow either strict or non-strict
    // ordering to avoid duplicate counts
    int[] prevSmaller(int nums[], int n) {
        int ans[] = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (st.isEmpty()) {
                st.add(i);
                ans[i] = 0;
            } else {
                int curr = nums[i];
                while (!st.isEmpty() && curr < nums[st.peek()]) {
                    st.pop();
                }
                if (st.isEmpty()) {
                    ans[i] = 0;
                } else {
                    ans[i] = st.peek() + 1;
                }
                st.add(i);
            }
        }
        return ans;
    }

    int[] nextSmaller(int nums[], int n) {
        int ans[] = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                st.add(i);
                ans[i] = n - 1;
            } else {
                int curr = nums[i];
                while (!st.isEmpty() && curr <= nums[st.peek()]) {
                    st.pop();
                }
                if (st.isEmpty()) {
                    ans[i] = n - 1;
                } else {
                    ans[i] = st.peek() - 1;
                }
                st.add(i);
            }
        }
        return ans;
    }
}
