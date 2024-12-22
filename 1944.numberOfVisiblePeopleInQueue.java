import java.util.Arrays;
import java.util.Stack;

class Solution {
    public int[] canSeePersonsCount(int[] heights) {
        // same to same as next greater element
        int n = heights.length;
        int ans[] = new int[n];
        Arrays.fill(ans, 0);
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            int curr = heights[i];
            if (st.isEmpty()) {
                // stack empty
                // no greater element on right
                // 0 people visible to person-i.
                ans[i] = 0;
                st.push(i);
            } else {
                while (!st.isEmpty() && curr > heights[st.peek()]) {
                    st.pop();
                    // curr person is taller than person on right side.
                    // curr person can definitely see this person
                    // why pop?
                    // well, any taller person on left side can see curr person[i] only. as person
                    // on stack's top is shorter than curr person.
                    ans[i] += 1;
                }

                if (!st.isEmpty()) {
                    // if stack is not empty means for curr person, we have popped all person
                    // shorter than curr person.
                    // or curr person is smaller and we were not able to pop person on right
                    // in both case curr person can see taller person on right side.
                    // so we are adding 1 here
                    ans[i] += 1;
                }
                // else{
                // if stack is empty means, current person is tall and it popped all persons
                // in that case we already added answer in ans[i] while popping persons.
                // }
                st.push(i);
            }
            // what about case? 5 3 2
            // 5 can see 3 but not 2.
            // well in this case 3 has already popped 2 and added it as his answer.
            // so for 5, only 3 is visible
        }
        return ans;
    }
}