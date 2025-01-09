import java.util.List;
import java.util.Stack;

class Solution {

    // TC: O(n)
    // SC: O(n)
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] ans = new int[n]; // storing answers
        Stack<Integer> st = new Stack<>();
        int prevTime = 0;
        for (String log : logs) {
            String[] splitted = log.split(":");
            int id = Integer.parseInt(splitted[0]);
            String op = splitted[1];
            int time = Integer.parseInt(splitted[2]);
            if (op.equals("start")) {
                /*
                 * if command is of start then,
                 * if stack is not empty means there is
                 * element that is on stack's top executed
                 * so we will store its duration of execution
                 * also stack contain element that are started at time-x
                 * current time is y
                 * ----x---------y
                 * 2 3 4 5 6 7 8 9 10
                 * element on stack's top executed for total duration of 9-4=5 (4,5,6,7,8)
                 * current element is going to start at 9 so that won't be included in stack's
                 * top's answer
                 * so now we will store current time in prevTime variable which is 9
                 */
                if (!st.isEmpty()) {
                    ans[st.peek()] += time - prevTime;
                }
                st.add(id);
                prevTime = time;
            } else {

                /*
                 * operator is end
                 * let's say prevTime is 9(x) and current time is 14(y)
                 * it can be from start(above if) or prev end of function
                 * for prev start, x to y both are inclusive. so we have to add 1 to x-y
                 * 14-9=5. 5+1=6 (9,10,11,12,13,14)
                 * ----x-------------y
                 * 7 8 9 10 11 12 13 14 15 16 17 18
                 * since our element is finished execution at 14, it next element can start
                 * executing at 15
                 * so we do time+1
                 * if x is of prevEnd time then also x is inclusive since above we have done
                 * time+1 for end
                 * 
                 * now what if this newPRevTime is used for next element's start?
                 * in start both x & y are not inclusive, only x is inclusive
                 * so it will work
                 */
                ans[id] += (time - prevTime + 1);
                st.pop();

                prevTime = time + 1;
            }
        }
        return ans;
    }
}
