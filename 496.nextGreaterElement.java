import java.util.HashMap;
import java.util.Stack;

class Solution {
    // bruteforce
    // TC: O(n^2) two loops
    // SC: O(n) answer
    // optimal
    // TC:O(n) max n pushed and n pops
    // SC: O(n) answer
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // same idea as daily temperature.
        // for next greater(also for prev greater),
        // stack will be in decreasing order.
        // reason is, if curr element is bigger tha stack's top, then we pop elements
        // from stack until stack become empty or we find any bigger element on stack.
        // reason is if curr element is bigger than stack's top then element on stack's
        // top cannot be answer.
        // answer always will be current element. that why we pop elements from stack

        // we we push smaller element? well we are finding next greater. if current
        // element is smaller than stack's top then may be current element can be next
        // greater of some element on left. that is the reason we push smaller element.
        HashMap<Integer, Integer> hm = new HashMap<>();
        Stack<Integer> st = new Stack<>();
        int n2 = nums2.length;
        for (int i = n2 - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                hm.put(nums2[i], -1);
                st.push(i);
            } else {
                while (!st.isEmpty() && nums2[i] > nums2[st.peek()]) {
                    st.pop();
                }
                if (st.isEmpty()) {
                    hm.put(nums2[i], -1);
                } else {
                    hm.put(nums2[i], nums2[st.peek()]);
                }
                st.push(i);
            }
        }
        int ans[] = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = hm.get(nums1[i]);
        }
        return ans;
    }
}