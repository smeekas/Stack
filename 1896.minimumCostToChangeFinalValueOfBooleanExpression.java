import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

class Solution {
    public int minOperationsToFlip(String expression) {

        // we can first build expression tree from given expression
        // where every leaf node will be operand( 0 | 1 ) and parent will be operator( &
        // or | )
        // after building expression tree, we can do dfs on expression tree to find
        // minimum cost
        // Exp root=buildTree(expression);
        Exp root = buildTreeOptimally(expression);
        return cost(root);
    }

    // Time complexity
    // we visit every node once. O(n)
    // space complexity O(n) call stack. in worst case tree is skewed
    int cost(Exp root) {
        if (root.left == null && root.right == null)
            return 1; // leaf node
        int leftCost = cost(root.left);
        int rightCost = cost(root.right);
        char lval = root.left.val;
        char rval = root.right.val;
        if (root.val == '&') {
            if (lval == '1' && rval == '1') {
                // we started by evaluating children first. so when parent are processing we
                // need what is the current value of child expression and cost to flip it
                // so we update root.val to current expressions result. so this root's parent
                // can also utilise current value of this child expression
                // , and we return cost to flip it
                root.val = '1'; // 1&1=1
                return Math.min(leftCost, rightCost); // we need to flip either left or right value. we pick which cost
                                                      // less
            } else if (lval == '0' && rval == '0') {
                root.val = '0'; // 0&0=0
                return Math.min(leftCost + rightCost, Math.min(leftCost, rightCost) + 1); // to make result '1'. we need
                                                                                          // to flip any one (either
                                                                                          // left or right) value and
                                                                                          // flip the sign(&->|) or flip
                                                                                          // both value
                // this will make result '1'
            } else {
                // one input is '1' & one input is '0'
                root.val = '0'; // 0&1=0 or 1&0=0
                return 1; // we need to flip sign to |
            }
        } else if (root.val == '|') {
            if (lval == '1' && rval == '1') {
                root.val = '1'; // 1|1=1;
                return Math.min(leftCost + rightCost, Math.min(leftCost + 1, rightCost + 1)); // flip one input & flip
                                                                                              // sign OR flip both
                                                                                              // inputs whichever is
                                                                                              // less
            } else if (lval == '0' && rval == '0') {
                root.val = '0'; // 0|0=0;
                return Math.min(leftCost, rightCost); // just flip one input
            } else {
                root.val = '1'; // 0|1=1 or 1|0=1
                return 1; // flip the sign
            }
        } else {
            // operand(0 or 1)
            // it cost 1 unit to flip it
            return 1;
        }
    }

    // Time Complexity
    // worst case n/2 operator ex. (0&1&0|2....)
    // for each scan we find last operator's index O(n)
    // recursive call are made O(n/2) times.for each operand, left and right tree's
    // recursive call
    // overall time complexity O(n^2)
    // if we try to find operator from last then, can we reduce complexity??
    // no. then we may encounter expression which is more tending toward left.
    // Space complexity
    // O(n+n) n for stack and n for tree
    Exp buildTree(String expression) {
        // this algorithm builds expression tree. where leaf nodes are operands. and
        // parent is operator(&,|)
        int opIndex = -1; // index which track operator's index
        int depth = 0; // we first want to find operand on root level, not in deep level. so we use
                       // depth variable to keep track of ('s depth
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(')
                depth++;
            if (c == ')')
                depth--;
            if ((c == '&' || c == '|') && depth == 0) {
                // if character is operator and depth is 0 means we are at root level
                opIndex = i; // store current index
                // if we use break, then we will stop as soon as we find first operator
                // this will make tree right skewed
                // more in depth operator is, more priority it has(it will run first)
                // since we want to parse expression from left to right, we try to find last
                // operator's index
                // this way we are making tree more left skewed.
                // ex. 0&1|2
                // right skewed === left skewed
                // -----&----------------|
                // ---0---|------------&---2
                // -----1---2--------0---1-----
                // if left skewed tree, 0&1 will run first. which we want

                // break;
                // break which decides left to right or right to left behaviour
            }
        }
        if (opIndex != -1) {
            // operator found!
            Exp exp = new Exp(expression.charAt(opIndex), null, null);
            String leftPart = expression.substring(0, opIndex);
            String rightPart = expression.substring(opIndex + 1, expression.length());
            // System.out.println(expression+" -- "+ leftPart+" "+rightPart+" -->
            // "+opIndex+" "+exp.val);
            Exp left = buildTree(leftPart);
            Exp right = buildTree(rightPart);
            exp.left = left;
            exp.right = right;
            // System.out.println("exp: "+ exp.val);
            return exp;
        } else if (expression.charAt(0) == '(') {
            // no operator found at root level and expression start with '('
            // means expression is wrapped in ()
            // if expression is not wrapped in () but first char is '(' then we would have
            // found any operator. which we didn't ex. (0&1)&0
            // extract expression from () and build tree
            Exp node = buildTree(expression.substring(1, expression.length() - 1));
            return node;

        } else {
            // no operator found at root level and expression do not start with '('
            // means this is operand!
            // create leaf node
            Exp leave = new Exp(expression.charAt(0), null, null);
            return leave;
        }
    }

    // Time complexity O(n). one iteration on loop. and overall maximum n pushes and
    // n popes
    // Space complexity O(n) stacks
    Exp buildTreeOptimally(String expression) {
        Stack<Exp> st = new Stack<>(); // store nodes and operands
        Stack<Character> op = new Stack<>(); // store operators. (,&,| etc...

        int n = expression.length();
        // what we do is...
        /*
         * we push (,& and |
         * we also pushes 0 & 1
         * when we encounter ), we pop two operands and st, we pop one operator, create
         * tree and push into stack(it is very similar to postfix evaluation. pop
         * 2,5.pop+. add them and push to stack. so we pushed 7)
         */
        for (int i = 0; i < n; i++) {
            char c = expression.charAt(i);
            if (c == '(')
                op.push(c);
            else if (c == ')') {
                while (!op.isEmpty() && op.peek() != '(') {
                    // we loop till, operator's stack do not have ( on stack's top
                    Exp right = st.pop(); // first value will be right side's value
                    Exp left = st.pop(); // second value will be left side's value
                    st.push(new Exp(op.peek(), left, right)); // create tree node and push into stack
                    op.pop(); // pop operator
                }
                op.pop(); // pop '('
            } else if (c == '&' || c == '|') {
                // if we just do push then, tree will become right skewed (like parsing
                // expression from right to left)
                // this is because if we do just push, stack will collect all operators and when
                // we encounter ), stack will start popping and create tree. rightmost operators
                // will be operated first. which will make tree right skewed
                // what we should do ?
                // before pushing current operator, we should process current tree and then we
                // should push current operator
                while (!op.isEmpty() && op.peek() != '(') {
                    // we process, current tree
                    Exp right = st.pop();
                    Exp left = st.pop();
                    st.push(new Exp(op.peek(), left, right));
                    op.pop();
                }
                // as we iterate, for current expression inside (...) as we iterate we will
                // process expression as we go
                // remember, we will not pop '('. because we need it when we encounter ')'
                // at current point, we have processed only expression's left part(from current
                // char) (partial expression)
                op.push(c);

            } else {
                // 0 or 1
                // create leaf node
                st.push(new Exp(c, null, null));
            }
        }
        // it is also possible that at root we didn't have any ()
        // stacks might still have some values, which we need to process
        // ex. 0|1&0&1 => In this case we will not go in any ( or ) case in above loop
        while (!op.isEmpty()) {
            Exp right = st.pop();
            Exp left = st.pop();
            st.push(new Exp(op.peek(), left, right));
            op.pop();
        }
        // stack's top will have our current tree!
        return st.pop();
    }
}

class Exp {
    char val;
    Exp left;
    Exp right;

    Exp(char val, Exp left, Exp right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}