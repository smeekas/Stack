import java.util.Stack;

class Solution {
    public long subArrayRanges(int[] nums) {
        // return bruteforce(nums);
        return optimal(nums);

    }

    long bruteforce(int[] num) {
        long ans = 0;
        int n = num.length;
        for (int i = 0; i < n; i++) {
            int min = num[i], max = num[i];
            for (int j = i; j < n; j++) {
                // for every subarray i..j find min, max and do operation
                min = Math.min(min, num[j]);
                max = Math.max(max, num[j]);
                ans += (max - min);
            }
        }
        return ans;
    }

    // prev non-strict
    // next strict
    long optimal(int[] nums) {
        // prerequisite is sum of subarray minimum
        // reverse thinking.
        // element i is minimum and maximum in how many subarrays?
        // to find element i is minimum in how many subarrays.find prev and next min
        // to find element i is maximum in how many subarrays.find prev and next maximum
        int n = nums.length;
        int[] prevMin = prevSmaller(nums, n);
        int[] nextMin = nextSmaller(nums, n);

        int[] prevMax = prevGreater(nums, n);
        int[] nextMax = nextGreater(nums, n);
        long ans = 0;
        for (int i = 0; i < n; i++) {

            // i-(min or max) => left elements => every subarray starts from left elements,
            // end at i
            // 1 for self (subarray of size-1 )
            // (min or max)-i => right elements => every subarray starts at i, ends at right
            // elements.
            // (i-(min or max))*((max or min)-i) => every subarray that starts from left
            // elements, ending at right elements.
            int minRange = (i - prevMin[i]) + 1 + (nextMin[i] - i) + ((i - prevMin[i]) * (nextMin[i] - i));
            int maxRange = (i - prevMax[i]) + 1 + (nextMax[i] - i) + ((i - prevMax[i]) * (nextMax[i] - i));

            // minRange and maxRange gives number of subarrays.
            // for minRange number of subarrays, nums[i] is minimum
            // for maxRange number of subarrays, nums[i] is maximum
            // so overall we will add nums[i] maxRange times and subtract nums[i] minRange
            // times.
            
            ans = ans + (long) (maxRange - minRange) * (long) nums[i];
        }
        return ans;
    }

    int[] prevSmaller(int[] nums, int n) {
        Stack<Integer> st = new Stack<Integer>();
        int ans[] = new int[n];
        for (int i = 0; i < n; i++) {
            if (st.isEmpty()) {
                st.add(i);
                ans[i] = 0;
            } else {
                int curr = nums[i];
                while (!st.isEmpty() && curr <= nums[st.peek()]) {
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

    int[] nextSmaller(int[] nums, int n) {
        Stack<Integer> st = new Stack<Integer>();
        int ans[] = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                st.add(i);
                ans[i] = n - 1;
            } else {
                int curr = nums[i];
                while (!st.isEmpty() && curr < nums[st.peek()]) {
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

    int[] prevGreater(int[] nums, int n) {
        Stack<Integer> st = new Stack<Integer>();
        int ans[] = new int[n];
        for (int i = 0; i < n; i++) {
            if (st.isEmpty()) {
                st.add(i);
                ans[i] = 0;
            } else {
                int curr = nums[i];
                while (!st.isEmpty() && curr >= nums[st.peek()]) {
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

    int[] nextGreater(int[] nums, int n) {
        Stack<Integer> st = new Stack<Integer>();
        int ans[] = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                st.add(i);
                ans[i] = n - 1;
            } else {
                int curr = nums[i];
                while (!st.isEmpty() && curr > nums[st.peek()]) {
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