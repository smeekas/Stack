import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

class Solution {
    public int totalSteps(int[] nums) {

        // bruteforce is to simulate it.

        // if curr element is smaller than stack's top then don't add curr element. and
        // increase local steps.
        // if local steps are greater than answer, update the answer.
        // if we do not remove curr element then reset local steps.
        // above solution has one flaw.
        // let's say curr element is smaller than stack's top.
        // so we do not add curr element into stack
        // but next element of curr element is also smaller so that means, in one step
        // curr element will also remove it's next element. but we have not added curr
        // element into stack. so our algorithm will count it as step-2, but it should
        // be step-1.
        // ex. 10 1 2 3 4 5 6 1 2 3

        return optimal(nums);

    }

    // TC: O(n^2)(while loop until element cannot be removed. it can be n. inner
    // loop for n times) SC: O(n)
    int linkedlist(int[] nums) {
        LinkedList<Integer> ll = new LinkedList<>();
        int ans = 0;
        for (int i : nums)
            ll.add(i);
        while (true) {
            int deleted = 0;
            int n = ll.size();
            ArrayList<Integer> hs = new ArrayList<>();
            // collect indexes of numbers which we want to delete
            for (int i = 0; i < n - 1; i++) {
                if (ll.get(i) > ll.get(i + 1)) {
                    hs.add(i + 1);

                }
            }
            int deleted_size = hs.size();
            // iterate over deleted indexes and remove them
            for (int i = deleted_size - 1; i >= 0; i--) {
                // int because Integer will be treated as value
                ll.remove((int) hs.get(i));
                deleted++;
            }
            if (deleted > 0)
                ans++;
            else {
                break;
            }
        }
        return ans;
    }


    // TC O(n) max n pushes & popes
    // SC O(n) stack
    int optimal(int[] nums) {
        // here idea is very similar, but we also add curr element into stack.
        Stack<Element> st = new Stack<>();
        int ans = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int curr = nums[i];
            if (st.isEmpty()) {
                st.add(new Element(curr, 0));
            } else {

                int newSteps = 0;
                // only if-else will not work.
                // reason is stack's top might have smaller number.
                // to search bigger number which actually can delete curr element, we need to
                // search the stack.
                // step's required will be...
                // maximum of steps of bigger to curr element.
                // because we have added element into stack, but they has to be removed first by
                // bigger element, if we want to remove curr element by bigger element.
                // if bigger element exists on stack's top..
                // means that we will not remove anything from stack, it can remove us in 1 step
                // only.
                // if smaller element exists on stack's top..
                // means we have to find bigger element who can remove us.
                // if there exists such big element (on right side of curr element), then it
                // will require steps equal to the [steps require to remove prev element + 1]
                while (!st.isEmpty() && st.peek().number <= curr) {
                    // prev smaller.
                    // stack contain bigger elements. so if current element is bigger then, current
                    // element will remove new smaller elements on left rather than current stack's
                    // element. so we don't need them.

                    // <= because bigger element will also remove it.
                    // ex. 10 4 4
                    // we take max because in top of stack, we will have max value of steps.
                    newSteps = Math.max(newSteps, st.pop().steps);
                }

                if (st.isEmpty()) {
                    // we have popped all smaller element from stack in order to find bigger
                    // element. but bigger element do not exists.
                    // so actually we cannot delete curr element.
                    st.push(new Element(curr, 0));
                } else {
                    // we found bigger element!
                    // this bigger element can remove current element.
                    // it takes bigger element to remove newSteps amount of steps to remove all
                    // elements in between.
                    // +1 step to remove curr element
                    // +1 because of curr element
                    ans = Math.max(ans, newSteps + 1);
                    st.push(new Element(curr, newSteps + 1));
                }
            }
        }
        return ans;
    }
}

class Element {
    int number, steps;

    Element(int number, int steps) {
        this.number = number;
        this.steps = steps;
    }
}