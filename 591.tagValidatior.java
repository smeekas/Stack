import java.util.Stack;

class Solution {
    public boolean isValid(String code) {
        /*
         * Idea
         * we find first tag which starts from <
         * after that we find > starting from <
         * whenever we find TAG_NAME, we validate it (<=9 character, all uppercase)
         * we also add TAG_NAME in stack, which helps in validating nested tags, their
         * order
         * 
         * we also find CDATA_CONTENT which starts with <![CDATA[
         * find ending of CDATA_CONTENT which ends with ]]> from <![CDATA[
         * 
         * same with ending of TAG_NAME, which start with </
         * 
         * < is also prefix in </ and <![CDATA[
         * 
         * so we first check, </ then <![CDATA[ and then <
         * if string starts with any three of given string from index-i(not from start,
         * but from where we left off), then we process further accordingly
         */
        int i = 0;
        int n = code.length();
        Stack<String> st = new Stack<>();
        while (i < n) {
            if (i > 0 && st.isEmpty())
                return false; // if i is >0 means we must have tag inside stack. if we do not have means we
                              // got tag_content without tag
            if (code.startsWith("</", i)) {

                i += 2; // "</" not included in tag_name
                int endIndex = code.indexOf(">", i);
                endIndex--; // '>' not included in tag_name
                if (endIndex == -2 || i > endIndex || endIndex - i + 1 > 9 || !areUpperCase(code, i, endIndex)) {

                    // endIndex -1 incase we do not find >. due to endIndex-- it will become -2.
                    // i>endIndex will happen when we receive empty tag (ex. </>)
                    // in that case i will point to > and endIndex will point to /
                    // if they both are same means we have tag with one char which is allowed. so we
                    // only do i>endIndex
                    // '>' must be after "</"
                    // endIndex-i+1 gives length of TAG_NAME
                    return false; // validate tag_name length
                }
                // we want to include char at endIndex. substring exclude ending index. so we
                // must do +1
                String tagName = code.substring(i, endIndex + 1);

                // since we found end tag, start tag must be present in stack
                if (st.isEmpty() || !st.pop().equals(tagName))
                    return false; // starting and ending tag_name do not match
                i = endIndex + 2; // endIndex was one less than >. we want one more than >. so we do +2
            } else if (code.startsWith("<![CDATA[", i)) {

                i += 9; // <![CDATA[ is not included in CDATA_CONTENT
                int endIndex = code.indexOf("]]>", i);

                if (endIndex == -1)
                    return false;
                // we must have ]]> (endIndex!=-1)

                i = endIndex + 3;

            } else if (code.startsWith("<", i)) {

                i++; // < not included in tag_name
                int endIndex = code.indexOf(">", i);

                endIndex--; // > not included in tag_name
                if (endIndex == -2 || i > endIndex || endIndex - i + 1 > 9 || !areUpperCase(code, i, endIndex))
                    return false;
                String tagName = code.substring(i, endIndex + 1);

                st.push(tagName);

                i = endIndex + 2; // one more than >
            }

            else {
                i++;
            }
        }

        return st.size() == 0;
    }

    boolean areUpperCase(String code, int i, int j) {
        for (int k = i; k <= j; k++)
            if (!(code.charAt(k) >= 'A' && code.charAt(k) <= 'Z'))
                return false;
        return true;
    }
}