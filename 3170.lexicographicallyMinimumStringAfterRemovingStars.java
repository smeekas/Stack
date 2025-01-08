import java.util.ArrayList;
import java.util.HashSet;

class Solution {
    public String clearStars(String s) {
        // return bruteforce(s);
        return optimal(s);

    }

    // TC O(n*26)
    // SC O(n)
    String optimal(String s) {

        /*
         * when we are at some index,
         * we want to know smallest character on left and mark it as deleted
         * we only have 26 characters
         * what we can do is as we iterate, we can add store every character's index
         * ex. a->1,3,7 | b->0,2,5 etc... as we will be adding them as we go, they will
         * be in sorted order
         * so when we want to delete any character, we can simply loop through all
         * alphabets and find the first alphabet that still exists
         * we will add them as we go. so when we are at current index, we have
         * information of character that are on left
         */

        ArrayList<ArrayList<Integer>> ar = new ArrayList<>();
        int n = s.length();
        // for every character we will have arraylist of indexes
        for (int i = 0; i < 26; i++)
            ar.add(new ArrayList<>());

        HashSet<Integer> hs = new HashSet(); // HashSet to maintain deleted indexes
        for (int k = 0; k < n; k++) {
            char c = s.charAt(k);
            if (c == '*') {
                hs.add(k); // add * in deleted set
                for (int i = 0; i < 26; i++) {

                    if (ar.get(i).size() > 0) {
                        // we will only check if element ('a'+i) exists (size>0)
                        int size = ar.get(i).size();
                        // since we want lexicographically minimum string, we will delete smallest
                        // character ('a'+i)th from last.
                        // deleting it from front can make string bigger(bigger character's position
                        // will be reduced by 1.)
                        // so we delete it from back
                        hs.add(ar.get(i).get(size - 1)); // add deleted char's index in delete set
                        ar.get(i).remove(size - 1);
                        break; // we want to delete only one. so once deleted we will break the loop
                    }
                }
            } else {
                // add current character's index in appropriate arrayList
                ar.get(c - 'a').add(k);
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            // collect characters
            if (!hs.contains(i))
                sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    // TC: O(n^2)
    // SC: O(n)
    String bruteforce(String s) {
        // simulation
        HashSet<Integer> hs = new HashSet<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '*') {
                // from current index to start we will try to find smallest alphabet
                int smallest = -1; // assume we don't have smallest
                for (int j = i - 1; j >= 0; j--) {
                    if (s.charAt(j) != '*' && !hs.contains(j)) {
                        // character cannot be * and it must not be taken
                        if (smallest == -1)
                            smallest = j; // no smallest so current index can be taken as smallest
                        else if (s.charAt(j) < s.charAt(smallest)) {
                            // if we found small character than current smallest
                            smallest = j;

                        }
                    }

                }
                // add smallest character's index in set so that we can avoid this smallest
                // character in future iteration.
                hs.add(smallest);
                hs.add(i); // also add *
            }
            // if char is not * then we don't have process anything
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            if (!hs.contains(i)) {
                // create string out of character that are still remaining and not part of set
                // set contain character that we have deleted
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}