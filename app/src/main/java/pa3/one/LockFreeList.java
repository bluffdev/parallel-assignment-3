package pa3.one;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeList<T> {
    class Node {
        T item;
        int key;
        boolean marked;
        AtomicMarkableReference<Node> next = new AtomicMarkableReference<Node>(null, false);

        public Node(int key) {
            this.key = key;
        }

        public Node(T item) {
            this.item = item;
            this.key = item.hashCode();
        }
    }

    class Window {
        public Node prev, curr;

        public Window(Node prev, Node curr) {
            this.prev = prev;
            this.curr = curr;
        }
    }

    Node head;
    Node tail;

    public LockFreeList() {
        this.head = new Node(Integer.MIN_VALUE);
        this.tail = new Node(Integer.MAX_VALUE);
        head.next.set(tail, false);
    }

    public Window find(Node head, int key) {
        Node prev = null;
        Node curr = null;
        Node succ = null;
        boolean[] marked = { false };
        boolean snip;

        retry: while (true) {
            prev = head;
            curr = prev.next.getReference();

            while (true) {
                succ = curr.next.get(marked);
                while (marked[0]) {
                    snip = prev.next.compareAndSet(curr, succ, false, false);
                    if (!snip) {
                        continue retry;
                    }
                    curr = succ;
                    succ = curr.next.get(marked);
                }
                if (curr.key >= key) {
                    return new Window(prev, curr);
                }
                prev = curr;
                curr = succ;
            }
        }
    }

    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Window window = find(head, key);
            Node prev = window.prev, curr = window.curr;

            if (curr.key == key) {
                return false;
            } else {
                Node node = new Node(item);
                node.next = new AtomicMarkableReference<Node>(curr, false);
                if (prev.next.compareAndSet(curr, node, false, false)) {
                    return true;
                }
            }

        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        boolean snip;
        while (true) {
            Window window = find(head, key);
            Node prev = window.prev, curr = window.curr;
            if (curr.key != key) {
                return false;
            } else {
                Node succ = curr.next.getReference();
                snip = curr.next.attemptMark(succ, true);
                if (!snip)
                    continue;
                prev.next.compareAndSet(curr, succ, false, false);
                return true;
            }
        }
    }

    public boolean contains(T item) {
        boolean[] marked = { false };
        int key = item.hashCode();
        Node curr = head;
        while (curr.key < key) {
            curr = curr.next.getReference();
            Node succ = curr.next.get(marked);
        }
        return (curr.key == key && !marked[0]);
    }
}
