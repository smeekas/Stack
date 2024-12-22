import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class Solution {
    public long numberOfSubarrays(int[] nums) {
        // return bruteforce(nums);
        return optimalWithBinarySearch(nums);
    }

    // TC: O(nlogn+n)(for every element we did binary search+ nge)
    // SC: O(n) (array + stack + map)
    long optimalWithBinarySearch(int[] nums) {
        // we need to find subArray(i,j) where nums[i]==nums[j]==max(i,j)
        // idea is we can find next greater element for element at index-i.
        // assume that number is at index-k.
        // all number from i to k-1 are smaller than nums[i].
        // for the subArray starting at index-i, j can exist only between i to k-1.
        // so to count subArray starting at index-i we need to find all possible index-j
        // where nums[j]==nums[i].
        // so we will find them. how?
        // linear search between index-i and index-(k-1) will be O(n). which makes
        // overall complexity O(n^2)
        // can be do better?
        // for every possible number we can maintain their indices. ex.
        // number-4-->{3,5,7} (3,5,7 are indices in nums)
        // we will iterate indices in ascending order. so list of indices of particular
        // number will be sorted.
        // so we can apply binary search!
        // more on binary search in the code.

        Stack<Integer> st = new Stack<>();
        int n = nums.length;
        HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
        // next greater element;
        int greater[] = new int[n];
        for (int i = 0; i < n; i++) {
            hm.putIfAbsent(nums[i], new ArrayList<>());
            hm.get(nums[i]).add(i);
            // map of number & {list of indexes(sorted)}
        }
        for (int i = n - 1; i >= 0; i--) {
            // next greater
            if (st.isEmpty()) {
                greater[i] = -1;
                st.push(i);
            } else {
                while (!st.isEmpty() && nums[i] >= nums[st.peek()]) {
                    st.pop();
                }
                if (st.isEmpty()) {
                    greater[i] = -1;
                } else {
                    greater[i] = st.peek();
                }
                st.push(i);
            }
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int ng = greater[i];
            // index-i definitely exists in hm[nums[i]] cause we are retrieving number at
            // index-i and querying HashMap.
            // we need to find biggest index-j which is less than index-(k) (index-(ng));
            long res = find(hm.get(nums[i]), i, ng);
            ans += res;
        }
        return ans;
    }

    int lower(ArrayList<Integer> list, int ele) {
        // algorithm to find just smaller number of given element
        int i = 0, j = list.size() - 1;
        if (i == j) {
            // only one element exists
            // ele is index of next greater element of nums[i] for all indexes given in
            // list.(list contain index of elements==nums[i])
            // element at index-i(first position, index-0) will be lesser than ele.
            return i;
        }
        int ans = -1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (list.get(mid) < ele) {
                // lower number than element!
                // we can store this as potential answer
                ans = mid;
                i = mid + 1;

            } else if (list.get(mid) > ele) {
                j = mid - 1;
            } else {
                // exact match!
                // prev number must be lower.
                return mid - 1;
            }
        }

        return ans;
    }

    int bsMatch(ArrayList<Integer> list, int ele) {
        // normal binary search
        int i = 0, j = list.size() - 1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            int midEle = list.get(mid);
            if (midEle == ele)
                return mid; // index
            else if (midEle < ele) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }
        return -1;
    }

    long find(ArrayList<Integer> list, int currIndex, int nextGreater) {
        int currIndexPosition = bsMatch(list, currIndex);
        // we first find proper index of curr element
        if (nextGreater == -1) {
            // no greater element exists for currElement.
            // so number of j's will be all numbers(indexes) starting from
            // currIndexPosition. (including currIndexPosition)
            return list.size() - currIndexPosition;
            // note that list is array of indexes containing nums[i]
            // from current index to beyond(till end)
        }

        int justSmallerIndexOfGreaterElement = lower(list, nextGreater);
        // number of elements between currIndexPosition &
        // justSmallerIndexOfGreaterElement
        return justSmallerIndexOfGreaterElement - currIndexPosition + 1;
    }

    // TC O(n^2)
    // SC O(1)
    long bruteforce(int[] nums) {
        long count = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int max = nums[i], first = nums[i];
            for (int j = i; j < n; j++) {
                max = Math.max(max, nums[j]);
                if (max == first && max == nums[j]) {
                    count++;
                }
            }
        }
        return count;
    }
}