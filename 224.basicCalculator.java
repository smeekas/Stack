import java.util.Stack;

class Solution {
    // only +, -,(,),' '
    public int calculate(String s) {
        // understandable is to get array of tokens. convert to postfix and evaluate it.
        // here expression can contain only '+' or '-'
        Stack<Integer> st = new Stack<>();
        int number = 0; // to track current number (number can be of >1 characters)
        int sign = 1; // to track sign
        int result = 0; // to track result
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') {
                number = (number * 10) + (int) (c - '0');
            } else if (c == '+') {
                result += (number * sign); // number is over. so add formed number with it's sign
                number = 0; // number is over
                sign = 1; // current sign is +
            } else if (c == '-') {
                result += (number * sign); // number is over. so add formed number with it's sign
                sign = -1; // number is over
                number = 0; // current sign is -
            } else if (c == '(') {
                // push accumulated answer into stack and also push sign (we must have
                // encountered sign just before '(').
                // we will now process internal expression of ().
                st.push(result);
                st.push(sign);
                result = 0; // we will process internal expression. so reset result
                sign = 1; // default sign (+)
            } else if (c == ')') {
                // ')' means () expression finished.
                result += (sign * number); // add parsed number with it's sign into result of ().
                number = 0;
                result *= st.pop(); // sign before (
                result += st.pop(); // prev result;
            }
        }

        // number contain last number which we were processing.
        return result + number * sign;
    }
}