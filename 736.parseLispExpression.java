import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {

    public int evaluate(String expression) {
        /*
         * first of all, we have to maintain outer variables inside too
         * we can use hashMap.
         * for any let expression, we can add them in map.
         * when we execute child expression, in recursion, we can pass parent's map.
         * in child, we first all values from parent map to current child's map
         * (if again let comes, then we add values or override in child's map)
         * 
         * parsing any expression...
         * first of all we want to create tokens for current level.
         * refer to parse function
         * if expression starts with (let, we collect variable and values into map
         * if expression starts with (add, we recursively call function to evaluate two
         * operands of add (they can be just values, variable from map or entire
         * expression.)
         * if expression starts with (mult, same as (add
         * for (add and (mult, after evaluating two child operand we perform + and * on
         * them respectively and return the result
         */
        return helper(expression, new HashMap());
    }

    /*
     * Time complexity O(n^2)
     * parsing takes O(l) time where l is length of expression
     * if n is total number of tokens and depth is d, at each depth we do O(l) work
     * to find tokens
     * in worst case scenario, every expression evaluates it's children, leading to
     * O(n) work at each depth (remember, n is total token at current depth)
     * so total O(n^2)
     * 
     * Space complexity O(n^2)
     * aggregately, all HashMap will have max n entries.
     * at each level we use O(n) for finding tokens
     * depth can go n. so overall O(n^2)
     */
    int helper(String exp, HashMap<String, Integer> hm) {
        if (exp.charAt(0) != '(') {
            // if expression do not start with ( means that it can be integer value or
            // variable name
            if (exp.charAt(0) >= '0' && exp.charAt(0) <= '9' || exp.charAt(0) == '-')
                return Integer.parseInt(exp);
            // if it starts with integer or '-'(negative value) we return it's integer value
            // if not means we received variable name
            // we find it in scoped map and return it
            return hm.get(exp);
        }
        HashMap<String, Integer> local = new HashMap(); // local scoped map
        local.putAll(hm); // add all parent's values. child can access parent's map values.(like scope)
        int startIndex = 0;
        if (exp.startsWith("(let")) {
            startIndex = 5; // ignoring (let
        } else if (exp.startsWith("(add")) {
            startIndex = 5; // ignoring (add
        } else if (exp.startsWith("(mult")) {
            startIndex = 6; // ignoring (mult
        }
        List<String> tokens = parse(exp.substring(startIndex, exp.length() - 1)); // generating tokens. we will ignore
                                                                                  // last ) so we do
                                                                                  // expression.length()-1

        if (exp.startsWith("(let")) {
            int i = 0, n = tokens.size();
            // for ith token, it's value will be (i+1)th token
            while (i < n - 2) {
                // (n-1)th token will be expression which we will return so we go till (n-3)th
                // token, whose value will be (n-2)th token
                // store token, and it's value in local scope (value can be expression or just
                // value, so we call helper to evaluate it)
                local.put(tokens.get(i), helper(tokens.get(i + 1), local));
                i += 2; // i will jump two place. one for token key and one for token value
            }
            // we evaluate last token
            return helper(tokens.get(n - 1), local);

        } else if (exp.startsWith("(add")) {
            // both operand can be another expression or value, so we call helper again to
            // evaluate oit.
            // incase they are expression, they must have parent's scoped variables. so we
            // also pass current expression's scope
            return helper(tokens.get(0), local) + helper(tokens.get(1), local);
        } else if (exp.startsWith("(mult")) {
            return helper(tokens.get(0), local) * helper(tokens.get(1), local);
        }
        return 0;
    }

    // TC: O(n)
    // SC: O(n)
    List<String> parse(String exp) {
        // we want to create tokens for depth-0
        StringBuilder sb = new StringBuilder(); // maintaining current token
        List<String> tokens = new ArrayList<>(); // creating list of tokens
        int par = 0; // depth tracker
        for (char c : exp.toCharArray()) {
            if (c == '(')
                par++; // depth will increase due to (
            else if (c == ')')
                par--; // depth will decrease due to )

            if (par == 0 && c == ' ') {
                // if depth is 0 and current character is empty space
                // then we store whatever we have collected into list and empty string to
                // collect next string
                tokens.add(sb.toString());
                sb = new StringBuilder();
            } else {
                // if depth is not 0 or current character is not something else, we keep
                // building our string
                sb.append(c);
            }
        }
        // if string is not empty then add leftover string
        if (sb.length() > 0)
            tokens.add(sb.toString());
        /*
         * ex. add x y => "add", "x", "y"
         * let x 3 y 4 (add x y) => "let", "x", "3", "y", "4", "(add x y)"
         */

        return tokens;
    }
}