import java.util.Stack;

class Solution {
    public int numSubmat(int[][] mat) {
        // return bruteforce(mat);
        return optimal(mat);

    }

    // TC: O((n^2)*(m^2))
    // SC: O(1)
    int bruteforce(int[][] mat) {
        int ans = 0;
        int n = mat.length, m = mat[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // let us check how many sub-matrices we can get that starts at [i][j]
                ans += subMatrix(mat, i, j, n, m);
            }
        }
        return ans;
    }

    int subMatrix(int[][] mat, int s, int t, int n, int m) {
        int count = 0; // local count
        int colBound = m; // initially we will assume that we can go till very end for the columns
        for (int i = s; i < n; i++) {
            for (int j = t; j < colBound; j++) {
                if (mat[i][j] == 1) {
                    // if we find 1 means we can create sub-matrices
                    // i- 0 1 2
                    // 0- 1 1 0
                    // 1- 1 1 1
                    // 2- 1 1 1
                    // we starts with [0][0]. sub-matrix of size 1x1
                    // i=0, j=1 => sub-matrix of size 1x2. so count++
                    // i=0, j=2 => mat[i][j]==0. since we want to form sub-matrix, and at index-2 we
                    // found 0. so for any other later sub-matrix, we cannot take index-2.
                    // for ex. sub-matrix ending at [1][2] of size 2x3 is not possible as this will
                    // include index [0][2]
                    // so we have to limit our j to this index(2)
                    // i=1, j=0. sub-matrix of size 2x1. so count++
                    // i=1, j=1. sub-matrix of size 2x2. so count++
                    // i=2, j=0, sub-matrix of size 3x1. so count++
                    // i=2, j=1. sub-matrix of size 3x2. so count++
                    // NOTE: we are only looking at sub-matrices starting at [s][t]
                    count++;
                } else {
                    colBound = j;
                }
            }
        }
        return count;
    }

    // TC: O(n*m)
    // SC: O(n*m)
    int optimal(int[][] mat) {
        // The optimal solution uses a histogram-based approach similar to "Maximum
        // Rectangle" problem
        // Key idea: For each row, we calculate the height of consecutive 1's above it
        // (including itself)
        // Then process each row to count sub-matrices that end at that row

        // Example matrix:
        // 1 1 0
        // 1 0 1
        // 1 1 1

        // After converting to histogram heights:
        // 1 1 0 (first row)
        // 2 0 1 (second row: counts 1's above including current)
        // 3 1 2 (third row: counts 1's above including current)

        int count = 0;
        int n = mat.length, m = mat[0].length;
        int[][] histo = new int[n][m];
        for (int i = 0; i < m; i++) {
            histo[0][i] = mat[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == 0) {
                    histo[i][j] = 0;
                } else {
                    histo[i][j] = histo[i - 1][j] + 1;
                }
            }
        }
        // Process each row of histogram to count sub-matrices
        // For each row, we find:
        // 1. How many sub-matrices can be formed using just that row
        // 2. How many sub-matrices can be formed extending upward from that row
        for (int i = 0; i < n; i++) {
            count += rowAns(histo[i], m);
        }
        return count;
    }

    int rowAns(int[] row, int n) {
        // how???
        // we just want to know how many sub-matrices can be formed that include
        // element-i from given row in the bottom row position
        // below are the steps given
        // we first find next and prev smaller
        // now assume just for current element, how many sub-matrices can be formed?
        // answer is element[i].
        // assume curr element is 3.
        // 1
        // 1
        // 1 ( we are here with sum 3)
        // 3 sub-matrices of sizes 1x1, 2x1, 3x1.
        // for range of numbers where current element is minimum, how many sub-matrices
        // can be formed where current element is in bottom row?
        // assume in left side 2 elements greater than current element and in right we
        // have 3 elements.
        // current element can form 2x3 sub-matrices with elements on left side where
        // current element will be at bottom-right corner
        // i- 0 1 2
        // 0- x
        // 1- x x
        // 2- x x x
        // 3- x x x
        // 4- x x x
        // 3 sub-matrices of width-2 and height of 1,2,3
        // x x
        // x x- x x
        // x x, x x, x x
        // same 3 sub-matrices of width-3 and height 1,2,3

        // same for right side
        // so far (# of elements on left + # of elements on right + 1)*element-idea
        // this many sub-matrices are possible
        // also sub-matrix starting from any of the column from left and ending at any
        // of the column of right
        // that will be left*right
        // for every count we can form element[i] sub-matrices (1,2,3... height)
        // so total below number of sub-matrices are possible
        // (left+ right+1+left*right)*element[i]

        // THIS LOGIC RUNS FOR EVERY ROW
        // this way we will find all sub-matrices
        int ans = 0;
        Stack<Integer> stn = new Stack<>();
        Stack<Integer> stp = new Stack<>();
        int prev_ans[] = new int[n];
        int next_ans[] = new int[n];

        // nextSmaller and prevSmaller needs to follow either strict or non-strict
        // ordering to avoid duplicate sub-matrix count
        for (int i = n - 1; i >= 0; i--) {
            // next smaller
            if (stn.isEmpty()) {
                stn.add(i);
                next_ans[i] = n - 1;
            } else {
                int curr = row[i];
                while (!stn.isEmpty() && curr <= row[stn.peek()]) {
                    stn.pop();
                }
                if (stn.isEmpty()) {

                    next_ans[i] = n - 1;
                } else {
                    next_ans[i] = stn.peek() - 1;
                }
                stn.add(i);

            }
        }
        for (int i = 0; i < n; i++) {
            // prev smaller
            if (stp.isEmpty()) {
                stp.add(i);
                prev_ans[i] = 0;
            } else {
                int curr = row[i];
                while (!stp.isEmpty() && curr < row[stp.peek()]) {
                    stp.pop();
                }
                if (stp.isEmpty()) {

                    prev_ans[i] = 0;
                } else {
                    prev_ans[i] = stp.peek() + 1;
                }
                stp.add(i);

            }
        }

        for (int i = 0; i < n; i++) {

            int prev = i - prev_ans[i];
            int next = next_ans[i] - i;

            ans += (prev + next + 1 + prev * next) * row[i];

        }

        return ans;
    }
}