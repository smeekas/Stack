import java.util.*;

class Solution {

    /*
     * Time Complexity,
     * assume k nesting levels, each level have m elements
     * // {a{x,y,z},b{x,y,z},c{x,y,z}} => m=3, k=2 => total 9 => m^k
     * each level takes O(n) processing time (loop iteration)
     * total (n*m^k + m^k*log(m^k)) => processing + sorting
     * 
     * Space complexity
     * max size of queue/stack => O(m^k)
     * 
     */
    public List<String> braceExpansionII(String expression) {
        /*
         * Our idea is that, we will evaluate inner most expression({...}) first
         * after evaluating it we will create new expression for every element on
         * innermost expression based on it's surrounding
         * ex. {a,b,c{x,z},h} => {a,b,cx,h} & {a,b,cz,h}
         * we will push this new newly created strings into stack/queue and process it's
         * innermost expression
         * 
         */
        Deque<String> dq = new LinkedList<>(); // stack or queue doesn't matter
        dq.add(expression); // our main expression
        List<String> ans = new ArrayList<>();
        while (!dq.isEmpty()) {
            String curr = dq.poll();
            if (curr.indexOf("{") != -1) {
                // if { is present
                int innerBraceLeft = 0; // index of '{' of innermost expression
                int innerBraceRight = -1; // index of '}' of innermost expression
                /*
                 * how to find innermost expression
                 * if we encounter '{', we store that index as innermost expression's leftIndex,
                 * and we reset index of '}' aka rightIndex
                 * if we encounter '}', we store that index as rightIndex only if currently
                 * stored rightIndex is -1. -1 means that we have reset it when we encountered
                 * '{'
                 */
                for (int i = 0; i < curr.length(); i++) {
                    if (curr.charAt(i) == '{') {
                        innerBraceLeft = i;
                        innerBraceRight = -1;
                    } else if (curr.charAt(i) == '}') {
                        if (innerBraceRight == -1)
                            innerBraceRight = i;
                    }
                }
                // avoid "{" and '}' and get tokens by splitting them by ','
                String[] allElements = curr.substring(innerBraceLeft + 1, innerBraceRight).split(",");
                StringBuilder sbLeft = new StringBuilder(curr.substring(0, innerBraceLeft)); // from 0 to '{'(excluding)
                                                                                             // it will be left part
                StringBuilder sbRight = new StringBuilder(curr.substring(innerBraceRight + 1, curr.length())); // from
                                                                                                               // '}'(excluding)
                                                                                                               // to end
                                                                                                               // it
                                                                                                               // will
                                                                                                               // be
                                                                                                               // right
                                                                                                               // part
                for (String ele : allElements) {
                    // join leftpart, element and rightpart. and add it into stack/queue
                    dq.add(new String(sbLeft.toString().concat(ele).concat(sbRight.toString())));
                }
            } else {
                // if expression do not contain '{', means there is no inner-expression
                // we can push current expression as one of the answer
                ans.add(curr);
            }
        }

        // sort the answer

        HashSet<String> set = new HashSet<>(ans); // remove duplicates
        ans.clear();
        ans.addAll(set); // collect unique answers
        Collections.sort(ans); // sort them
        return ans;
    }
}