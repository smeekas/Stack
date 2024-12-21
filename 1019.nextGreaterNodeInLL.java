import java.util.Stack;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Solution {
    // same as next greater.
    // we first extract all values from LinkedList and then apply next greater algorithm
    public int[] nextLargerNodes(ListNode head) {
        int length = 0;
        ListNode temp = head;
        while (temp != null) {
            temp = temp.next;
            length++;
        }
        int nodes[] = new int[length];
        temp = head;
        int i = 0;
        while (temp != null) {
            nodes[i] = temp.val;
            temp = temp.next;
            i++;
        }
        return nextGreaterElement(nodes);
    }

    int[] nextGreaterElement(int[] arr) {
        int n = arr.length;
        int ans[] = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            if (st.isEmpty()) {
                st.add(i);
                ans[i] = 0;
            } else {
                while (!st.isEmpty() && arr[i] >= arr[st.peek()]) {
                    st.pop();
                }
                if (st.isEmpty()) {
                    st.push(i);
                    ans[i] = 0;
                } else {
                    ans[i] = arr[st.peek()];
                    st.push(i);
                }
            }
        }
        return ans;
    }
}