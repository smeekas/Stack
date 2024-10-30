import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {
    public String simplifyPath(String path) {
        StringBuffer sb = new StringBuffer();

        // first we clean string. remove duplicate /
        char currChar = path.charAt(0);
        sb.append(path.charAt(0));
        for (int i = 1; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c == '/') {
                if (currChar == '/') {
                    // if prev char is / and curr character is / then ignore current /
                    continue;
                } else {
                    // add character into string
                    sb.append(c);
                    // update current character
                    currChar = c;
                }
            } else {
                // if current character is not / then add character into string
                // update current character
                sb.append(c);
                currChar = c;
            }
        }
        if (sb.charAt(sb.length() - 1) == '/')
            sb.deleteCharAt(sb.length() - 1); // delete last / if it exists
        // it will not going to help us in any way

        String folders[] = sb.toString().split("/");
        Stack<String> st = new Stack<>();

        for (String folder : folders) {
            if (folder.equals(".")) {
                // curr dir
                // do nothing
                continue;
            } else if (folder.equals("..")) {
                // we have to go to prev dir
                //current dir is at st.peek()

                // if stack is not empty then pop curr dir
                if (!st.isEmpty())
                    st.pop();
            } else if (folder.isEmpty()) {
                // if string is empty, don't do anything. mostly because of first slash. 
                continue;
            } else {
                // if folder name is received. push it
                st.push(folder);
            }
        }
        // convert stack into list (reverse to maintain original order)
        List<String> ans = new ArrayList<>();
        while (!st.isEmpty())
            ans.add(0, st.pop());

        sb = new StringBuffer();
        int n = ans.size();
        // for every folder item push /{folder-name}
        for (int i = 0; i < n; i++) {
            sb.append("/");
            sb.append(ans.get(i));

        }
        // if stack was empty then return empty / (curr dir)
        if (ans.size() == 0)
            return "/";
        return sb.toString();
    }
}