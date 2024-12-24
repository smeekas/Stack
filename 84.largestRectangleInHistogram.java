import java.util.Arrays;
import java.util.Stack;

class Solution {

    // Bruteforce TC:O(n^2) SC:O(n)
    // Optimal TC:O(n) SC:O(n)
    public int largestRectangleArea(int[] heights) {
        // idea is that for element i, we can look take it's previous-small(ps) and
        // next-small(ns).
        // all elements between ps...i and i...ns are larger than ith element.
        // now we can take total elements between ps and ns and take height as current
        // ith element.
        // for ith element, ans is (total elements between ps & ns)*arr[i]
        // we can't go beyond ps(ps-1) and ns(ns+1) as they are smaller than current ith
        // element, so we can't take elements height as arr[i].

        // we can find previous smaller and next smaller in O(n^2)(nested loops). it
        // will be brute-force.
        // we can also find it in O(n) with monotonic stack
        int ns[] = nextSmallerOptimal(heights);
        int ps[] = prevSmallerOptimal(heights);
        int ans = 0;
        int n = heights.length;
        for (int i = 0; i < n; i++) {
            // ns[i] and ps[i] gives index of smaller element. we will not consider it. so
            // we will subtract 1 from next smaller and add 1 to prev smaller
            int nsi = ns[i] - 1;
            int psi = ps[i] + 1;
            /** alternate and clean below */
            // int nsi = Math.max(i,ns[i]-1);
            // int psi = Math.min(i,ps[i]+1);
            ans = Math.max(ans, (nsi - psi + 1) * heights[i]);

        }
        return ans;
    }

    int[] nextSmaller(int[] h) {
        int n = h.length;
        int ans[] = new int[n];
        Arrays.fill(ans, -1);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (h[j] < h[i]) {
                    ans[i] = j;
                    break;
                }
            }
            if (ans[i] == -1)
                ans[i] = n;
        }
        return ans;
    }

    int[] prevSmaller(int[] h) {
        int n = h.length;
        int ans[] = new int[n];
        Arrays.fill(ans, -1);
        for (int i = 0; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (h[j] < h[i]) {
                    ans[i] = j;
                    break;
                }
            }
            if (ans[i] == -1)
                ans[i] = -1;
        }
        return ans;
    }

    // we used non-strict ordering <=
    // because we want to maximize histogram area.
    // so bar height is < current bar's height then we cannot take that bar.
    // but if bar height is == current bar's height then we can take it.
    // so we are finding strictly lesser element
    int[] nextSmallerOptimal(int[] h) {
        int n = h.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            int curr = h[i];
            if (st.isEmpty()) {
                ans[i] = n; // no next smaller means we will go till last element.
                st.add(i);
            } else {
                while (!st.isEmpty() && curr <= h[st.peek()]) {
                    st.pop();
                }
                if (st.isEmpty()) {
                    ans[i] = n;
                } else {
                    ans[i] = st.peek();
                }
                st.add(i);
            }
        }
        return ans;
    }

    int[] prevSmallerOptimal(int[] h) {
        int n = h.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            int curr = h[i];
            if (st.isEmpty()) {
                ans[i] = -1; // no prev smaller means we will go till first element
                st.add(i);
            } else {
                while (!st.isEmpty() && curr <= h[st.peek()]) {
                    st.pop();
                }
                if (st.isEmpty()) {
                    ans[i] = -1;
                } else {
                    ans[i] = st.peek();
                }
                st.add(i);
            }
        }
        return ans;
    }
}