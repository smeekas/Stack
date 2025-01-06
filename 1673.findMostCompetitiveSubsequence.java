import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

class Solution {
    public int[] mostCompetitive(int[] nums, int k) {
        // size of subsequence must be of k
        // so we need to remove n-k elements
        return optimal(nums, nums.length - k);
    }

    // TC: O(n+n+n+n) max n pushes, n popes, reverse, converting into array
    // SC: O(n) arraylist, stack, array answer
    int[] optimal(int[] nums, int remove) {
        int n = nums.length;
        if (remove == 0)
            return nums; // if we remove 0 elements, we can return original array
        if (remove == n)
            return new int[] {}; // if we remove all elements then we will have empty array

        /*
         * idea is very similar to remove k digits.
         * here it is array instead of string
         * 
         */
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (remove == 0) {
                // we cannot remove any numbers. so we can just add current number
                st.add(nums[i]);
                continue;
            }
            if (st.isEmpty()) {
                // if stack is empty, then we don't have anything to compare
                // so we can just add the number
                st.add(nums[i]);
            } else {
                int curr = nums[i];
                while (!st.isEmpty() && remove > 0 && curr < st.peek()) {
                    // remove > 0 => we can still remove elements
                    // curr < st.peek() =>current number is smaller? then we can remove stack's top
                    // as removing bigger numbers from stack and adding current smaller number will
                    // help us create most competitive subsequence(smallest number)
                    // we will remove till stack is empty
                    // till we can remove elements
                    // till current element is smaller than stack's top
                    st.pop();
                    remove--;
                }
                st.add(nums[i]);
            }
        }
        // if remove is > 0 then we still have to remove elements
        // we cannot remove element from anywhere in the array as it might create bigger
        // number
        // we will remove elements from back until we cannot
        while (remove-- > 0 && !st.isEmpty()) {

            st.pop();
        }
        ArrayList<Integer> ansList = new ArrayList<>();
        while (!st.isEmpty()) {
            // collect answer
            ansList.add(st.pop());
        }
        // reverse as we have collected answer in opposite order due to stack
        Collections.reverse(ansList);
        // convert it in array from arraylist
        return ansList.stream().mapToInt(i -> i).toArray();

    }
}