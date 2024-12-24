import java.util.Stack;

class Solution {

    // bruteforce TC:O(n*n^2) SC:O(n^2) (for every row we have n^2 algorithm)
    public int maximalRectangle(char[][] matrix) {
        // bruteforce will be to have naive solution for next greater/smaller and prev
        // greater/smaller
        return optimal(matrix);
    }

    // optimal TC:O(n^2) SC:O(n^2) (for every row we have linear(n) algorithm)
    int optimal(char[][] matrix) {
        // prerequisite is largest area in histogram
        // what if for every row we create largest histogram?
        // how?
        // from top-down we we add row above us into current row. if 0 exists then we
        // will not do it.
        // then for every row we apply largest area in histogram. (for every row
        // element-i represent how many 1s are above it.)
        // so we can try to find largest area of 1s.

        int n = matrix.length, m = matrix[0].length;
        int mat[][] = new int[n][m];
        for (int i = 0; i < m; i++) {
            mat[0][i] = matrix[0][i] - '0';
            // 1st row only
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int curr = matrix[i][j] - '0';
                if (curr == 0) {
                    // if 0 then it will be zero
                    mat[i][j] = 0;
                } else {
                    // if current element is 1 then we add row above us.
                    mat[i][j] = mat[i - 1][j] + curr;
                }
            }
        }
        int prevSmall[][] = new int[n][m]; // prevSmall for every row
        int nextSmall[][] = new int[n][m]; // next small for every row
        for (int i = 0; i < n; i++) {
            prevSmall[i] = prevSmaller(mat[i]);
        }
        for (int i = 0; i < n; i++) {
            nextSmall[i] = nextSmaller(mat[i]);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int prev = prevSmall[i][j] + 1; // prev element is boundary. so we add one (n=>n-1)
                int next = nextSmall[i][j] - 1; // next element is boundary. so we subtract one (-1=>0)
                // (total elements from prev to next)*(curr element's size)
                ans = Math.max(ans, (next - prev + 1) * mat[i][j]);

            }
        }

        return ans;

    }

    // strict order => no same element allowed <
    // non-strict order => same element allowed <=
    // we follow non-strict order here
    // reason is that if bar of same element exists then we have to consider it too
    // to make our area larger
    int[] nextSmaller(int[] h) {
        int n = h.length;
        int ans[] = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                st.add(i);
                ans[i] = n; // if no next greater exists then we go till end
            } else {
                int curr = h[i];

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

    int[] prevSmaller(int[] h) {
        int n = h.length;
        int ans[] = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (st.isEmpty()) {
                ans[i] = -1; // if no prev greater exists then we go till start
                st.add(i);
            } else {
                int curr = h[i];
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