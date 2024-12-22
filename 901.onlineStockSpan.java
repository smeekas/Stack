import java.util.Stack;


// bruteforce TC:O(n^2) (one loop for every next() function call.)  SC:(1)
// optimal TC:O(n) (overall maximum n pushed & n popped SC:(n) (stack)
class StockSpanner {
    // this is prev greater problem.
    // we will get data as we progress
    // so either we have to store that data in array.
    // or we can store in stack itself.
    // in prev greater, if curr element is bigger that stack's top then we remove
    // that element & push curr element.
    // in later iteration for element-x if we have to find it's span, then actually
    // we have to find on array of all received elements(without alteration)
    // so what we do is that when we are popping elements, we collect theirs spans.
    // so while pushing current element, we also add collected span (number of
    // smaller elements deleted by elements)
    Stack<Stock> st;

    public StockSpanner() {
        st = new Stack<>();
    }

    public int next(int price) {
        if (st.isEmpty()) {
            // empty stack so span is only 1(itself)
            st.add(new Stock(price, 1));
            return 1;
        } else {
            int popped = 0;
            while (!st.isEmpty() && price >= st.peek().price) {
                // collect span of element which we are deleting
                popped += st.pop().span;

            }
            int span = popped + 1; // 1 for curr element
            st.add(new Stock(price, span));
            return span; // return calculated span of current element

        }
    }
}

class Stock {
    int price, span;

    Stock(int price, int span) {
        this.price = price;
        this.span = span;
    }
}
/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */