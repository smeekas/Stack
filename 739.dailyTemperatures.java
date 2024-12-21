import java.util.Stack;

class Solution {
    public int[] dailyTemperatures(int[] tems) {
        // bruteforce => two loops
        // optimal
        // NGE
        // for element, if we have knowledge of right side of element.....
        // we can use stack
        // but stack is lifo
        // so if we traverse from back, for any element, we will have right side already
        // processed
        // also for any element, we want greater element (next greater)
        // while traversing from back,
        // for two element in array ex. 80 60
        // if we have 60 in stack, and current element is 80,
        // for any element left to 80, 80 will be answer. 60 cannot be answer.
        // so in stack element should be in decreasing order from bottom to top
        // why decreasing order?
        // ex. 60 80
        // in stack we have 80.
        // so now we cannot have bigger element in stack(on top of 80 as we discussed,
        // if exists then we have to remove 80)
        // but we can have smaller element. as it might be NGE for elements on left.
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[tems.length];

        for (int i = tems.length - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                // stack empty means no element greater exists on right.
                ans[i] = 0;
                st.push(i); // push curr ele index
                continue;
            }
            while (!st.isEmpty() && tems[i] >= tems[st.peek()]) {
                // if curr element is bigger than stack's top, then we have to remove them.
                // >= because same element do not mean greater.
                st.pop();
            }
            if (st.isEmpty()) {
                // after popping, stack is empty. so no next greater exists for element-i
                ans[i] = 0;
                st.push(i);
            } else {
                ans[i] = st.peek() - i;
                // distance from stack's top and current element
                st.push(i); // push curr index as it is smaller than stack's top
            }
        }
        return ans;
    }
}