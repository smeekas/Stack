
class Solution {
    // TC O(n)
    // SC O(1)
    public int minDeletion(int[] nums) {
        // when we delete, right number moves by 1 position.
        // we will simulate it.
        // right number moves by how much? by number of deletion we have did so far!
        // so to get actual index we have to remove deleted count from it's index.
        // but we cannot use actual index to compare values. as we have not deleted
        // anything
        int n = nums.length;
        int deletion = 0;

        for (int i = 0; i < n - 1; i++) {
            int actualIndex = i - deletion; // actual index
            // we use actual index to determine actual index due to deletion.
            // actualIndex will have whether index is even or odd.
            if (actualIndex % 2 == 0) {
                // we will use i for comparing values of i and i+1.
                if (nums[i] == nums[i + 1]) {
                    deletion++;
                }
            }
        }
        // if after all deletion, array length is odd then we need to delete any one
        // element(mostly last as deleting element from front can bring two same
        // elements together).
        return (nums.length - deletion) % 2 == 0 ? deletion : deletion + 1;
    }
}