import java.util.ArrayList;

class BrowserHistory {

    ArrayList<String> list;
    int ptr=0; // points to current page
    int max=0; // count of maximum page in our history
public BrowserHistory(String homepage) {
    list=new ArrayList<>();
    list.add(homepage);
    this.ptr=0; 
    this.max=0;
}

public void visit(String url) {
        list.add(this.ptr+1,url);
        // increment ptr and add value
        this.ptr++;
        this.max=this.ptr; // if after going back if we visit new page then we need to discard entries from ptr to max
        // so we are just moving max pointer to ptr.
}

public String back(int steps) {
        if(this.ptr<steps){
            // we need to go back more than our size
            // in this case ptr will point to first page
            this.ptr=0;
            return list.get(0);
        }
        // reduce ptr
        this.ptr-=steps;
        return list.get(this.ptr); // get element
}

public String forward(int steps) {
    if(this.ptr+steps> this.max){
           // if we need to go more then max
        // go till  max only
                    this.ptr=this.max;
                return list.get(this.max);
       }
       //else increse pointer by given steps
                   this.ptr+=steps;
                   return list.get(this.ptr);
}
}

/**
 * Your BrowserHistory object will be instantiated and called as such:
 * BrowserHistory obj = new BrowserHistory(homepage);
 * obj.visit(url);
 * String param_2 = obj.back(steps);
 * String param_3 = obj.forward(steps);
 */
