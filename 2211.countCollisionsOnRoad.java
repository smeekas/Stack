import java.util.Stack;

class Solution {
    public int countCollisions(String directions) {
        return optimal(directions);
    }

    // TC O(n+n) max n pushes and max n popes
    // SC O(n)
    int linear(String directions) {

        // we will simulate it
        Stack<Character> st = new Stack();
        int collision = 0;
        for (char c : directions.toCharArray()) {
            if (st.isEmpty()) {
                st.push(c);
                continue;
            }
            if (st.peek() == 'S') {
                if (c == 'L') {
                    // if config is SL
                    // left car will collide and turn into S (SS)
                    collision++;
                    st.push('S');
                } else {
                    // config is SR
                    // nothing will happen
                    st.push(c);
                }
            } else if (st.peek() == 'R') {

                if (c == 'L') {
                    // config is RL
                    // it can be RRRRRRRL
                    // we need to pop every R. last R and L will collide and turn into SS.
                    // rest of the R will also collide into S.

                    int count = 1; // 1 for L
                    collision++; // for L
                    while (!st.isEmpty() && st.peek() == 'R') {
                        count++;
                        collision++;
                        st.pop();
                    }
                    // push S for all popped elements.
                    while (count-- > 0)
                        st.push('S');
                } else if (c == 'S') {
                    // same happens with S
                    // config is RS.
                    // it can be RRRRRRS
                    int count = 1; // for S
                    // no increase of collision variable as S will not be counted in collision
                    while (!st.isEmpty() && st.peek() == 'R') {
                        count++;
                        collision++;
                        st.pop();
                    }
                    while (count-- > 0)
                        st.push('S');
                } else {
                    // config is RRR
                    st.push(c);
                }
            } else {
                // top of stack is L
                // since it is moving left side. we push just push current element into stack
                // LR or LS cannot cause collision
                st.push(c);
            }
        }
        return collision;
    }

    int optimal(String directions) {
        // left most car going left will never cause collision
        // right most car going right will never cause collision
        // every other car in-between will cause collision
        // why?
        // left most R car will try to go in right direction. so it will cause
        // collisions
        // same for right most L car.
        // they will collide or will endup colliding in S

        int rightMostLFromLeft = -1, leftMostRFromRight = directions.length();
        for (int i = 0; i < directions.length(); i++) {
            if (directions.charAt(i) == 'L') {
                rightMostLFromLeft = i;
            } else {
                break;
            }
        }
        for (int i = directions.length() - 1; i >= 0; i--) {
            if (directions.charAt(i) == 'R') {
                leftMostRFromRight = i;
            } else {
                break;
            }
        }
        int ans = 0;
        for (int i = rightMostLFromLeft + 1; i < leftMostRFromRight; i++) {
            if (directions.charAt(i) != 'S')
                ans++;
        }
        return ans;
    }
}