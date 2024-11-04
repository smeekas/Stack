import java.util.Stack;

class Solution {
    public String removeDuplicates(String s, int k) {
        return un_optimised(s, k);
    }

    // TC (n)
    String optimised(String s, int k) {
        Stack<Pair> st = new Stack();
        // for consecutive same character we only have one entry, and we just update the
        // count
        // this way when we have to remove character whose count is matched to k, we
        // just have to pop one element.
        for (char c : s.toCharArray()) {
            if (st.isEmpty()) {
                st.push(new Pair(c, 1));
            } else {
                if (st.peek().freq == k) {
                    st.pop();
                } else {
                    st.peek().freq++;
                }
            }
        }
        StringBuffer sb = new StringBuffer();

        // total n characters.
        while (!st.isEmpty()) {
            Pair p = st.pop();
            int freq = p.freq;
            char c = p.c;
            while (freq-- > 0)
                sb.append(c);
        }
        sb.reverse();
        return sb.toString();
    }

    // O(N+N) max n pushed and n popes

    String un_optimised(String s, int k) {
        Stack<Character> st = new Stack(); // for char
        Stack<Integer> count = new Stack(); // for char count
        for (char c : s.toCharArray()) {
            if (st.isEmpty()) {
                st.push(c);
                count.push(1);
                continue;
            }
            if (st.peek() == c) {
                // both char are same
                // if count of char is reached k then delete all characters else push char with
                // count++;
                if ((count.peek() + 1) == k) {
                    while (!st.isEmpty() && st.peek() == c) {
                        st.pop();
                        count.pop();
                    }
                } else {
                    st.push(c);
                    count.push(count.peek() + 1);
                }
            } else {
                // new char
                st.push(c);
                count.push(1);
            }
        }
        StringBuffer sb = new StringBuffer();
        while (!st.isEmpty())
            sb.append(st.pop());
        sb.reverse();
        return sb.toString();
    }
}

class Pair {
    char c;
    int freq;

    Pair(char c, int freq) {
        this.c = c;
        this.freq = freq;
    }
}