import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

class Solution {
    public int[] secondGreaterElement(int[] nums) {
        // bruteforce is
        // two loops to find next greater element
        // another nested loop to find next greater greater element.
        // TC O(n^3)
        // SC O(n)

        // optimal
        // first find next greater element
        // instead of storing answer that - for element i, element j is the next greater
        // element, we will store that- index-i is next greater of which elements?
        // why? how this can help?
        // later in iteration, if we sees that current element is greater for someone,
        // then we add all those elements into heap.
        // so in next iteration, first if we assign current element as answer to
        // elements of heap until we can. (current element> heap.peek())
        // we want element of heap in increasing order (smallest first). that is also
        // reason of using heap.
        ArrayList<ArrayList<Integer>> next = new ArrayList<>();
        int n = nums.length;
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++)
            next.add(new ArrayList<>());
        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                st.add(i);
            } else {
                while (!st.isEmpty() && nums[i] >= nums[st.peek()]) {
                    st.pop();

                }
                if (st.isEmpty()) {

                } else {
                    // stack's peek is next greater of ith element.
                    next.get(st.peek()).add(i);
                }
                st.push(i);
            }
        }
        // for every element, if current element is greater of someone, we add base
        // element.
        // ex. if ith element is next greater of jth. we add jth element into pq.
        // so in next iteration, if (i+1)th element is bigger than elements stored in
        // PriorityQueue(min heap), we will pop elements and make (i+1)th element as
        // next next greater element of pq's element. we will also remove from pq, until
        // we can.
        int ans[] = new int[n];
        Arrays.fill(ans, -1);
        PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.number - b.number);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> indexes = next.get(i);
            while (!pq.isEmpty() && pq.peek().number < nums[i]) {
                ans[pq.poll().index] = nums[i];
            }
            for (Integer index : indexes) {
                pq.add(new Element(nums[index], index));
            }
        }
        return ans;
    }
}

class Element {
    int number, index;

    Element(int number, int index) {
        this.number = number;
        this.index = index;
    }
}