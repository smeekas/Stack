import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

class Solution {
    // TC: (n+ nlogn) (one iteration + sorting of keys)
    // SC: (n+n+n) n for stack, all hashMap at-max contain n element, another n of keysets
    public String countOfAtoms(String formula) {

        /*
         * Our idea
         * for every level we maintain hashmap.
         * hashmap contains element with its count
         * once we are done with level, we merge it with parent
         * few points
         * 1. when we encounter any Capital letter, we acquire entire element, and it's
         * count
         * 2. when we encounter any opening brace "(" we add new HashMap into stack
         * 3. when we encounter any closing brace ")", we acquire number next to it and
         * multiply all count of all element's on that level
         */
        Stack<HashMap<String, Integer>> st = new Stack<>();
        int n = formula.length();
        char carr[] = formula.toCharArray();

        HashMap<String, Integer> currMap = new HashMap<>();
        st.push(currMap);
        int i = 0;
        while (i < n) {
            char c = carr[i];
            if (c == '(') {
                st.push(new HashMap<>());
                i++;
            } else if (c == ')') {
                // look for number
                i++; // next index of ')'
                int number = 0; // variable which generate number
                while (i < n && carr[i] >= '0' && carr[i] <= '9') {
                    number = number * 10 + (carr[i] - '0');
                    i++;
                }
                HashMap<String, Integer> currentLevel = st.pop();
                Set<String> currKeyset = currentLevel.keySet();
                if (number != 0) {
                    // if number is 0 means we do not have any multiplier (ex. Mg2H(K)Fe => K do not
                    // have any multiplier)
                    for (String key : currKeyset)
                        currentLevel.put(key, currentLevel.get(key) * number);
                }
                HashMap<String, Integer> parentLevel = st.peek();
                for (String key : currKeyset) {
                    if (parentLevel.containsKey(key)) {
                        // if parent have same element then add their count
                        parentLevel.put(key, parentLevel.get(key) + currentLevel.get(key));
                    } else {
                        // if parent do not have element, add it
                        parentLevel.put(key, currentLevel.get(key));
                    }
                }
            } else if (c >= 'A' && c <= 'Z') {
                // we try to find entire element and its count
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                i++;

                while (i < n && carr[i] >= 'a' && carr[i] <= 'z') {
                    // collect element's name
                    sb.append(carr[i]);
                    i++;
                }

                int number = 0;
                while (i < n && carr[i] >= '0' && carr[i] <= '9') {
                    // collect it's count
                    number = number * 10 + (carr[i] - '0');
                    i++;
                }
                String element = sb.toString();
                st.peek().putIfAbsent(element, 0);
                // if element do not have count(0) we add one else we add count which we found
                st.peek().put(element, st.peek().get(element) + (number == 0 ? 1 : number));
            }
        }

        Set<String> keySet = st.peek().keySet();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> sortedKeys = new ArrayList<>(keySet);
        Collections.sort(sortedKeys); // we need to return answer in element's sorted order
        for (String key : sortedKeys) {
            sb.append(key);
            int numberOfAtoms = st.peek().get(key);
            if (numberOfAtoms != 1) {
                // if count is 1, we do not add suffix "1".
                sb.append(numberOfAtoms);
            }
        }
        return sb.toString();
    }
}