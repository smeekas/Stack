import java.util.List;
import java.util.Stack;

class Solution {
    public long maximumSumOfHeights(List<Integer> maxHeights) {
        // basically ....i....
        // element at index-i is peak of mountain (max element)
        // left side of index-i is increasing till index-i
        // right side of index-i is decreasing starting from index-i.
        // we have to maximize mountain area
        // return bruteforce(maxHeights);
        return optimal(maxHeights);
    }

    // SC (n*6)==(n)
    // TC O(n)
    long optimal(List<Integer> maxH) {
        // in loop, inner loop is dependent on outer loop.
        // also we do not want to compare with all element.
        // monotonic stack can help here!!!
        // left side of mountain is decreasing from peak
        // so may be prev smaller?
        // right side of mountain is also decreasing from peak
        // so may be next smaller?
        // we still have to try all peaks.
        // we can utilize pre-processing done via monotonic stacks!
        // more on that below
        int n = maxH.size();
        int[] prevS = new int[n]; // prev smaller elements
        int[] nextS = new int[n]; // next smaller elements
        Stack<Integer> stn = new Stack<>();
        Stack<Integer> stp = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            // nse
            if (stn.isEmpty()) {
                nextS[i] = -1;
                stn.push(i);
            } else {
                while (!stn.isEmpty() && maxH.get(i) <= maxH.get(stn.peek())) {
                    stn.pop();
                }
                if (stn.isEmpty()) {
                    nextS[i] = -1;
                } else {
                    nextS[i] = stn.peek();
                }
                stn.push(i);
            }
        }
        for (int i = 0; i < n; i++) {
            // pse
            if (stp.isEmpty()) {
                prevS[i] = -1;
                stp.push(i);
            } else {
                while (!stp.isEmpty() && maxH.get(i) <= maxH.get(stp.peek())) {
                    stp.pop();
                }
                if (stp.isEmpty()) {
                    prevS[i] = -1;
                } else {
                    prevS[i] = stp.peek();
                }
                stp.push(i);
            }
        }
        // if we are at index-i(assuming peak), it would be great if we can get sum of
        // left and right side in O(1)
        // how prev smaller and next smaller help us pre-compute it?
        // ex for previous smaller. (same logic for next smaller)
        // assume we are at index-k
        // if no previous smaller exists for index-k means from 0 to k, for all elements
        // we must take height equal to arr[k]. so k+1*(arr[k])==(total sum of elements
        // from 0 to k), as for elements on left of index-k, they are restricted by curr
        // element(they are high in value).
        long maxAns = 0;
        long prevAns[] = new long[n];
        prevAns[0] = maxH.get(0); // for left most element, only itself exists.
        for (int i = 1; i < n; i++) {
            long curr = maxH.get(i);
            if (prevS[i] == -1) {
                // no prev smaller exists. so height of all i+1 elements will be taken as arr[i]
                prevAns[i] = (i + 1) * curr;
            } else {
                // if prev smaller exists, then till from 0 to curr element's prev smaller, we
                // take sum as it is. from prev smaller to curr element, height will be
                // restricted to curr element's height.
                prevAns[i] = prevAns[prevS[i]] + (i - prevS[i]) * curr;
            }
        }
        long nextAns[] = new long[n];
        nextAns[n - 1] = maxH.get(n - 1);
        for (int i = n - 2; i >= 0; i--) {
            long curr = maxH.get(i);
            if (nextS[i] == -1) {
                // n-i == from i to end of array, all elements
                nextAns[i] = (n - i) * curr;
            } else {
                nextAns[i] = nextAns[nextS[i]] + (nextS[i] - i) * curr;
            }
        }
        // NOTE: for prevAns[i] or nextAns[i], ith element is included in both of them
        // in above loops, we are also adding ith element in ans[i] regardless of
        // smaller exists or not.

        for (int i = 0; i < n; i++) {
            // in sum of prevAns[i] & nextAns[i], ith element included twice. so we are
            // subtracting once.
            // we assume that, ith element is peak here.
            maxAns = Math.max(maxAns, prevAns[i] + nextAns[i] - maxH.get(i));
        }

        return maxAns;
    }

    // TC (n^2)
    // SC O(1)
    long bruteforce(List<Integer> maxHeights) {
        int n = maxHeights.size();
        long maxAns = -1;
        for (int i = 0; i < n; i++) {
            // we assume every index to be peak and try to find mountain area
            long curr = maxHeights.get(i);
            long prevMin = 0;
            long prevSum = 0;

            long nextMin = 0;
            long nextSum = 0;

            // i-1 to 0.
            // left side of mountain
            // idea is that from peak we try to get left.
            // height must be less than peak and must be decreasing.
            // if we find minimum value, then for next heights we have to take curr minimum
            // until we find new minimum. (height must be in decreasing order.)
            // also we want sum of heights.
            // ex. 2,2,4,3,5....
            // assume peak is 5.
            // heights taken for left side of 5 will be 2,2,3,3,5....
            for (int j = i - 1; j >= 0; j--) {
                if (prevMin == 0) {
                    // no min
                    // find new min
                    prevMin = Math.min(curr, maxHeights.get(j));
                    prevSum = prevMin; // also part of sum
                } else {
                    // which height we have to take?
                    // minimum of peak(curr), current element and minimum so far
                    prevMin = Math.min(curr, Math.min(prevMin, maxHeights.get(j)));
                    prevSum += prevMin; // height found for current element will be part of answer.
                }
            }
            // just like left side, we have to do for right side of the mountain
            for (int k = i + 1; k < n; k++) {
                if (nextMin == 0) {
                    nextMin = Math.min(curr, maxHeights.get(k));
                    nextSum = nextMin;
                } else {
                    nextMin = Math.min(curr, Math.min(nextMin, maxHeights.get(k)));
                    nextSum += nextMin;
                }
            }
            long total = 0;
            total += prevSum;
            total += nextSum;
            total += curr; // sum definitely includes peak
            maxAns = Math.max(maxAns, total);
        }
        return maxAns;
    }
}