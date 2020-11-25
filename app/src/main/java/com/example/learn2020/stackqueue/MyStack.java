import java.util.LinkedList;

class MyStack {

    private LinkedList<Integer> queue1;
    private LinkedList<Integer> queue2;
    /** Initialize your data structure here. */
    public MyStack() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        queue1.add(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        if (empty()) {
            return -1;
        }
        if (queue2.isEmpty()) {
            while (queue1.size() > 1) {
                queue2.add(queue1.removeFirst());
            }
            return queue1.removeFirst();
        } else {
            while (queue2.size() > 1) {
                queue1.add(queue2.removeFirst());
            }
            return queue2.removeFirst();
        }
    }

    /** Get the top element. */
    public int top() {
        return queue1.isEmpty() ? queue2.getLast() : queue1.getLast();
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue1.size() + queue2.size() == 0;
    }
}