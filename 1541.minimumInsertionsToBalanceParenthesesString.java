import java.util.Stack;

class Solution {
    public int minInsertions(String s) {
        // return linear(s);
        // return linear_understandable(s);
        return optimal(s);

    }

    // hard to understand

    int linear(String s) {
        int ans = 0;
        // for char c=='('
        // if stack is empty,
        // for every '(' we push 2. 2 means we expect two )s
        // else stack is not empty,
        // if stack's top is 1 means we encountered ()(. stack's top ) and curr char (.
        // we are missing one closing bracket.
        // increase our answer, remove previous ')' (1) and add current (s 2. (we are
        // expecting 2 losing brackets

        // for char c==')'
        // if stack is is empty means we do not have (.
        // so increase ans ('(' is our missing para)
        // add 1 on stack (we may encounter another closing bracket).
        // if stack's top is 2.
        // decrease stack's top by 1. (we need 2 closing bracket and we found 1)
        // if stack's top is 1.
        // just pop. we found valid para ()).
        // after iteration, stack contain number which indicates number of parenthesis
        // we want.
        // since iteration is finished, we need to add additional parenthesis which are
        // in stack.
        // add all of them and return answer
        Stack<Integer> st = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                if (st.isEmpty()) {
                    st.push(2);
                } else {
                    if (st.peek() == 1) {
                        // we found ()
                        // one ) missing.
                        ans++;
                        st.pop();
                        st.push(2);
                    } else {
                        st.push(2);
                    }
                }
            } else {
                if (st.isEmpty()) {
                    st.push(1);
                    ans++;
                } else if (st.peek() == 2) {
                    // for ( we are expecting two ))
                    st.pop(); // we got one )
                    // so update top of stack.
                    st.push(1);
                } else {
                    st.pop();
                }
            }
        }

        while (!st.isEmpty())
            ans += st.pop();
        return ans;
    }

    int linear_understandable(String s) {
        Stack<Character> st = new Stack<>();
        int missing_close = 0;
        int missing_open = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                st.push(c);
            } else {
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    // we we found ))
                    if (st.isEmpty()) {
                        // but stack do not have open bracket.
                        missing_open++;
                        i++; // we are skipping next character. from i to i+2. (another skip will be done by
                             // for loop/)
                    } else {
                        // we have open bracket!
                        i++;
                        st.pop();
                        // skip next ) as we found )) and stack also has (.
                    }
                } else {
                    // means after curr ')', ')' do not exists
                    // do '(' exists?
                    if (st.isEmpty()) {
                        // no. '(' do not exists.
                        missing_open++;
                        missing_close++;
                    } else {
                        // yes '(' exists
                        missing_close++;
                        st.pop(); // pop '(' from stack
                    }
                }
            }
        }
        // stack is made of '('s. every '(' need two ')'.
        return missing_open + missing_close + st.size() * 2;
    }

    int optimal(String s) {
        // well we are using stack just for tracking '('s. we can do it via variable
        // too!.
        // rest all thing are same as linear_understandable.
        int opening = 0;
        int missing_open = 0;
        int missing_close = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(')
                opening++;
            else {
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    if (opening <= 0) {
                        // we have )) but no (
                        missing_open++;
                        i++;
                    } else {
                        // we have equivalent (. so pop it
                        opening--;
                        i++;
                    }
                    // we do i++ because we want to skip next ).
                } else {
                    // we do not have consecutive )).
                    if (opening <= 0) {
                        // no ). and no (
                        missing_open++;
                        missing_close++;
                    } else {
                        // we have ( but no )
                        missing_close++;
                        opening--;
                    }
                }
            }
        }

        return missing_open + missing_close + opening * 2;

    }
}