import java.util.Arrays;
import java.util.Stack;

class Solution {
    // bruteforce TC:O(n^2)(two loops) SC:(n)
    // optimal TC:(n+nlogn)(monotonic stack+sorting) SC:(n)(answer+stack+calc array)
    public int carFleet(int target, int[] position, int[] speed) {
        int n = speed.length;
        double calc[][] = new double[n][3];
        for (int i = 0; i < n; i++) {
            calc[i][0] = position[i];
            calc[i][1] = speed[i];
            calc[i][2] = (double) (target - position[i]) / (double) (speed[i]);
        }
        Arrays.sort(calc, (a, b) -> Double.compare(a[0], b[0]));
        // bruteforce will be that we use two loops on sorted array (on time remaining)
        // optimal solution is we use monotonic stack
        // how?
        // we need to create fleet based on when they meet. so positioning is important.
        // that why we sorted them
        // by looking at time remaining array of ex.1 (ans is 3)
        // 12 3 7 1 1 (secs) ([12],[3,7],[1,1])
        // car on index-0 takes 12 seconds.
        // car on index-1 takes 3 seconds.
        // car on index-2 takes 7 seconds.
        // car on index-1 can reach end faster but we have car ahead of index-1 car that
        // is index-2 car.(car on index-2 is slower(it takes 7 seconds))
        // so after reaching car on index-2, car on index-1 need to slow down.
        // so they form a fleet
        // so by looking at the time remaining array,
        // - we do not need to compare with all element
        // - involves element that need to be processed in certain order

        // LOOKS LIKE PREVIOUS SMALLER ELEMENT!
        // for 12 no one. so it is fleet itself
        // for 3 no one. so it is fleet
        // for 7, 3 exists. so part of fleet with 3
        // for 1 no one. so it is fleet
        // for 1, prev 1 exists. so part of fleet with 1.

        // I think previous smaller.
        // how to form logic???
        // just like next greater
        return prevSmaller(calc);
        // if we have loops like below
        /*
         * for(int i to n){
         * for(int j=i+1 to n){
         * this indicates that for element i we need to process i+1 to n
         * if we have this kind of loops then high possibility of monotonic stack (next
         * greater/smaller)
         * }
         * }
         * in this question we can maybe apply next greater/smaller but we also want how
         * many smaller we have after finishing algorithm
         * so we took prev smaller approach
         */
    }

    int prevSmaller(double[][] arr) {

        Stack<Integer> st = new Stack<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (st.isEmpty()) {
                st.push(i);

            } else {
                while (!st.isEmpty() && arr[i][2] >= arr[st.peek()][2]) {
                    st.pop();
                }
                st.push(i);
            }
        }

        return st.size();
    }
}