import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> buildArray(int[] target, int n) {
        List<String> ans=new ArrayList<>();
           // loop from 1 to n.  
           // if number is present in target then add "Push"
                // increase target array pointer and number pointer
           //else
                // number is not in target. so we will add "Push" & then "Pop".
                // we will not increase target pointer, as in later iterations we still need to compare it with number pointer
        int i=1;
        int k=0;
        while(i<=n && k <target.length){
            
                if(i==target[k]){
                    ans.add("Push");
                     k++;
                }else{
                    ans.add("Push");
                    ans.add("Pop");
                }
               
                i++;
                
        }
        return ans;
    }
}