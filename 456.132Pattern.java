import java.util.Stack;

class Solution {
    public boolean find132pattern(int[] nums) {

        // well, well, well
        // bruteforce will use 3 loops (n^3)
        // one loop is dependent on other.
        // may be stack????
        // largest element will be second(j).
        // second largest element will be last(k)
        // if we traverse from back, then when we arrive at index-x, we will have all
        // right side element already processed, which can help in finding largest.
        // well we can use two variable. largest and second largest.
        // but we cannot use them and we have to use stack. why?.
        // using two variables will give us largest, secondLargest from currIndex to end
        // of array.
        // for element j, we want to find smaller elements on right. means if we assume
        // num[j] to be largest, then we want to find second largest on right.
        // by using 2 variables we only get largest and second largest.it is static not
        // dynamic.
        // if curr element is larger than prev element(stack's top), means we found pair of
        // j<k and nums[k]<nums[j]
        // so we update secondLargest and pop from stack. we keep doing it.
        // why keep popping them??
        // well, we also need to find smaller element than secondLargest (13 pattern)
        // as long as our curr element is larger we pop from stack and update
        // secondLargest
        // this way we are maximizing value of secondLargest.
        // maximum value of secondLargest will help use finding 13 pattern
        // (curr<secondLargest)
        // stack will be decreasing from bottom to top.(increasing from top to bottom)
        // why?
        // we first popped element from stack until it was greater than curr element
        // then we pushed curr element. so we pushed smaller element on stack's top

        // we maintain largest element in stack
        // whenever we get new larger element, we store curr largest into one
        // variable(secondLargest)
        // new larger is j(new index)
        // secondLargest is k(index on right of curr index)
        // j<k maintained.
        // nums[k]<nums[j] maintained
        // now if we find any element smaller than secondLargest (ith ) we have founded
        // the answer
        Stack<Integer> st = new Stack<>();
        int n = nums.length;
        int secondLargest = Integer.MIN_VALUE;
        for (int x = n - 1; x >= 0; x--) {
            int curr = nums[x];
            if (curr < secondLargest) {
                return true;
            }
            while (!st.isEmpty() && curr > st.peek()) {
                secondLargest = st.pop();
            }
            st.push(curr);
        }

        return false;
    }
}