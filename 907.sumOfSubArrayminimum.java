import java.util.Stack;

class Solution {
    public int sumSubarrayMins(int[] arr) {
        // return bruteforce(arr);
        return optimal(arr);

    }

    // TC:O(n^2) SC:O(1)
    int bruteforce(int[] arr) {
        int n = arr.length;
        int MOD = (int) 1e9 + 7;
        int ans = 0;
        // two loops. i..j all possible combination of sub-arrays
        // to check minimum in subarray we can use one more loop.
        // but we can do it without it too.
        // we can keep track of minimum so far from i in the variable.
        for (int i = 0; i < n; i++) {
            int curr = arr[i];
            int min = curr;
            ans = (ans + min) % MOD;
            for (int j = i + 1; j < n; j++) {
                min = Math.min(min, arr[j]);
                ans = (ans + min) % MOD;
            }
        }
        return ans;
    }

    // TC:O(n) SC:O(n)
    int optimal(int[] arr) {
        int n = arr.length;
        long MOD = (long) (1e9 + 7);
        long ans = 0;

        // in bruteforce we have inner loop dependent on outer loop.
        // we are calculating minimum multiple times for overlapping subarrays. element
        // can be minimum for multiple subarrays
        // what will be reverse thinking?
        // element-i is minimum in how many subarrays?
        // if we can find it for every element then we can get answer by following.
        // element-i is minimum in  x subarrays. ele[i]*x
        // sum above multiplication of all elements.
        // we get our answer!!
        // how to find element-i is minimum in how many subarrays ?
        // if element-i is minimum then we won't have any other minimum.
        // so we can first find subarray's boundary? how? we can find element-i's prev
        // and next smaller
        // our subarray spans from element-i's prev smaller to next smaller.

        int nse[] = nextSmaller(arr, n); // if no smaller exists then it goes all the way till end
        int pse[] = prevSmaller(arr, n); // if no smaller exists then it goes all the way till start
        for (int i = 0; i < n; i++) {

            if (nse[i] == pse[i]) {
                // both same means subarray is of size-1. no prev and next smaller exists.
                // arr[i] is minimum in only one subarray (of size 1)
                ans = (ans + 1 * arr[i]) % MOD;
                continue;
            }
            int left = i - pse[i]; // number of elements on left of element-i
            int right = nse[i] - i; // number of elements on right of element-i

            // ith element smallest with all left possible arr
            // ex. 4 5 6 1(min) subarrays=> [4 5 6 1],[5 6 1],[6 1]
            int leftArr = left;
            // ith element smallest with all right possible arr
            // ex. 1(min) 8 9 7 subarrays=> [1 8 9 7],[1 8 9],[1 8]
            int rightArr = right;
            // ith element smaller for itself. (array of size 1)
            int self = 1;
            // ith element smaller for all combination of left & right(for every array start
            // at any of the index of left, it will end at any of the index of right)
            // ex. 4 5 1(min) 7 8 subarrays=> [4,5,1,7],[4,5,1,7,8],[5,1,7],[5,1,7,8]
            int combine = left * right;
            long total = (leftArr + rightArr + self + combine) % MOD;
            ans = (ans + (total * arr[i] % MOD) % MOD) % MOD;

        }
        return (int) ans;

        // how to handle duplicates??
        // if we have duplicate number in array then few of the subarrays also will be
        // same
        // Example: [3, 1, 2, 1]
        // For both 1's, if we don't handle properly, we'll count subarray [1,2,1] twice
        // we can try one strict ordering and one non-strict ordering
        // how this will help?
        // assume for NSE, strict and for PSE non-strict
        // - for 1st 1(index-1) // NSE <, PSE <=
        // pse:0(no one) nse:3(second 1) (due to < we will not pop second 1)
        // for 2nd 1(index-2)
        // pse:0(no one) (due to <= we will pop first 1) nse: 3(no one)

        // conclusion
        // duplicate of right side will take of subarrays of duplicate elements on left
        // side.
        // duplicates on left side will consider duplicate on right as smaller element.
        // they won't include this duplicate

    }

    int[] prevSmaller(int[] arr, int n) {
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (st.isEmpty()) {
                ans[i] = 0;
                st.add(i);
            } else {
                while (!st.isEmpty() && arr[i] <= arr[st.peek()]) {
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

    int[] nextSmaller(int[] arr, int n) {
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                ans[i] = n - 1;
                st.add(i);
            } else {
                while (!st.isEmpty() && arr[i] < arr[st.peek()]) {
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