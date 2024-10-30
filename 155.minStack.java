import java.util.Stack;

class MinStack {
        Stack<Pair> st;
        int currMin=Integer.MAX_VALUE;
        // we can either use two stacks or stack of Pair class
        //   idea is for every element we keep track of min element for current element
    public MinStack() {
            st=new Stack<>();
    }

    public void push(int val) {
        if(val<currMin){
            // new value is lesser then current element
            currMin=val;
        }
        //add curr value and min value
        st.push(new Pair(val,currMin));
    }

    public void pop() {
        st.pop();
        // if stack is empty then reset current Min value
        // else new min value will be top's min.
        if(st.isEmpty())currMin=Integer.MAX_VALUE;
        else currMin=st.peek().min;

    }

    public int top() {
        return  st.peek().data;
    }

    public int getMin() {
        return  st.peek().min;
    }
}
class  Pair{
    int  data; int min;
    Pair(int data,int  min){
        this.data=data;
        this.min=min;
    }
}
/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */