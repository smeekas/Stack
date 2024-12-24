import java.util.Stack;

class Solution {
    public double[] getCollisionTimes(int[][] cars) {
        return optimal(cars);
    }

    // TC: O(n^2)
    // SC: O(n) (ans and stack)
    double[] bruteforce(int[][] cars) {
        // bruteforce solution is of two loops.
        // for every car we will try to find next slower car where current car will
        // collide
        int n = cars.length;
        double[] ans = new double[n];
        ans[n - 1] = -1;
        for (int i = n - 2; i >= 0; i--) {
            int iPos = cars[i][0];
            int iSpeed = cars[i][1];
            for (int j = i + 1; j < n; j++) {
                int jPos = cars[j][0];
                int jSpeed = cars[j][1];
                if (iPos < jPos) {
                    // ith car must of on left side of jth car
                    if (iSpeed > jSpeed) {
                        // speed of ith car must be greater than jth car (then only ith car can collide
                        // with jth car)
                        double calc = (double) (jPos - iPos) / (double) (iSpeed - jSpeed);
                        // distance between both car / speed at which distance is shrinking
                        if (calc < ans[j] || ans[j] == -1) {
                            // ith car can reach jth car before jth car gets merged.
                            // or
                            // jth car is last so it can not collide with any next car.(ans[j]==-1)
                            ans[i] = calc;
                            break; // we found ans. so break the loop.
                        } else {
                            // ith car reaches jth car after, jth car is merged. (calc>=ans[j])
                            // so we can ignore jth car. we can look into right of jth car
                        }

                    } else {
                        // ith car is slower than jth car. so as per current iteration, answer is -1.
                        // we will still look of answer in later iteration
                        ans[i] = -1;
                    }

                } else {
                    // ith car is ahead of jth car. so ith car cannot collide with jth car.
                    // answer is -1 as per current iteration.
                    // we will still look for answer in later iteration
                    ans[i] = -1;
                }
            }
        }
        return ans;
    }

    // TC O(n) (max n pushes & popes)
    // SC O(n) (stack and ans array)
    double[] optimal(int[][] cars) {
        // in bruteforce inner loop depends on outer loop.
        // monotonic stack can help here
        // for every car we want to find car next to it which follow below conditions
        // 1. current car faster than next car
        // 2. current car on left side of next car
        // 3. next car is not merged before current car reaches next car
        int n = cars.length;
        double[] ans = new double[n];
        ans[n - 1] = -1;
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                ans[i] = -1;
                // no next car exists so answer is -1
                st.add(i);
            } else {
                int iPos = cars[i][0];
                int iSpeed = cars[i][1];
                // while at current car, we have data of next cars available
                while (!st.isEmpty()) {
                    int jPos = cars[st.peek()][0];
                    int jSpeed = cars[st.peek()][1];
                    if (iPos < jPos) {
                        if (iSpeed > jSpeed) {
                            double calc = (double) (jPos - iPos) / (double) (iSpeed - jSpeed);
                            if (calc < ans[st.peek()] || ans[st.peek()] == -1) {
                                // st.peek() contain data of next car
                                // ith car can reach jth car before jth car gets merged.
                                ans[i] = calc;
                                break;
                            } else {
                                // ith car reaches jth car after, jth car is merged.
                                // so we can ignore jth car. we can look into right of jth car
                                st.pop(); // to ignore jth car we will remove it from stack too.
                                // if jth car merges before ith car reaches it then for cars left of i, i can be
                                // answer, but j will never be answer.
                            }
                        } else {
                            // ith car is slower.so we assume answer -1 and look for more car on right. so
                            // pop the top car
                            // if ith car is slower than jth car, then for the cars on left of ith car, i
                            // can be answer and j cannot be answer. as ith is slower than j. left cars will
                            // merge into i. (that the reason of popping and also if we pop then we can
                            // check next car)
                            ans[i] = -1;
                            st.pop();
                        }
                    } else {
                        // jth car is on left.
                        // ith car cannot collide
                        ans[i] = -1;
                        st.pop();
                    }
                }
                st.push(i);
            }
        }
        return ans;
    }
}
