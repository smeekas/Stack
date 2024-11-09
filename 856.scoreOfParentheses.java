import java.util.Stack;

class Solution {
    public int scoreOfParentheses(String s) {
        // return with_two_loop(s);
        // return without_two_loop(s);
        return optimal(s);
    }

    // O(n+n)=> max n pushes and n popes
    int with_two_loop(String s) {
        // parentheses are always balanced.
        // idea is that
        // for '(' we push 0
        // for ')',
        // if peek is 0 means we got ')' just after '('. so remove 0 of '(' and push 1 for "()"
        // if peek is not 0 means
        // it means that stack contain data of inner parentheses
        // from curr char ')' and start char '(' every value will be doubled.
        // we pop until we find 0 and double every value
        // finally we remove 0 of '(' and push newvalue for "(...)"
        // for the answer we collect score of every root leveled (). ex.()(()())() 1.() 2.(()()) 3.()
        Stack<Integer> st = new Stack<>();
        int ans = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                st.push(0);
            } else {
                if (st.peek() == 0) {
                    st.pop();
                    st.push(1);
                } else {
                    int local = 0;
                    while (!st.isEmpty() && st.peek() != 0) {
                        local += (st.pop() * 2);
                    }
                    st.pop();
                    st.push(local);
                }
            }
        }
        while (!st.isEmpty())
            ans += st.pop();
        return ans;
    }

    // O(n)
    int without_two_loop(String s) {
        // idea is that for every parentheses score we update it's parent's score

        Stack<Integer> st = new Stack<>();
        st.push(0); // main 0 which is responsible for final answer

        for (char c : s.toCharArray()) {
            if (c == '(') {
                st.push(0); // 0 for opening para.
            } else {
                int curr_value = st.pop(); // value of closing para.
                int parent_value = st.pop(); // value of curr para's parent
                // we remove parent's data because we will update it's value and push new value

                if (curr_value == 0) {
                    // if curr para's value is 0 means no inner para exists.
                    st.push(parent_value + 1); // so add 1 to parent.
                    // why not double it?
                    // we just report child para's score into parent
                    // when parent will be popped, parent's score(which is sum of all inner para's
                    // score) will be doubled.
                } else {
                    // current para has inner paras. so current para include score of child para's
                    // value
                    // we double value of child para's score and update current para's parent.
                    st.push(parent_value + curr_value * 2);
                }
            }
        }
        return st.peek();
    }

    int optimal(String s) {
        /*
         * ()() => 1+1
         * (()()) => 2*(1+1)
         * ((()())) => 2*2*(1+1)
         * ((()(()))) => 2*2*(1+2*1)
         */
        // idea is that,
        // even outer most para will multiply value of inner para by 2
        // inner para will be multiplied by 2 how many times? depth times (all (((
        // excluding own para's '(')
        // it will be multiplied only if it is pair of (). we need to check
        // string[i-1]=='('
        // because other parentheses don't have their own value. they are there for
        // multiplying inner para's value
        int depth = 0;
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                depth++;
            } else {
                depth--;
                if (s.charAt(i - 1) == '(') {
                    ans += Math.pow(2, depth);
                }
            }
        }
        return ans;
    }
}