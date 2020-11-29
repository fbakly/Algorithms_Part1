import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	private Node head, tail;
	private int size;

	private class Node {
		Item value;
		Node next, prev;
	}

	private class DequeIterator implements Iterator<Item> {
		private Node current = head;

        @Override
		public boolean hasNext() { 
			return current != null; 
		}

		public void remove() {
			throw new UnsupportedOperationException("Exception Deque: remove operation is not supported");
		}

        @Override
		public Item next() {
			if (current == null)
				throw new NoSuchElementException("Exception Deque: has no next element");
			Item item = current.value;
			current = current.next;
			return item;
		}
	}

    // construct an empty deque
	public Deque() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

    // is the deque empty?
    public boolean isEmpty() { return size == 0; }

    // return the number of items on the deque
    public int size() { return size; }

    // add the item to the front
	public void addFirst(Item item) {
		if (item == null)
			throw new IllegalArgumentException("exception deque: cannot insert null");
		Node newHead = new Node();
		newHead.value = item;
		newHead.next = head;
		newHead.prev = null;
		if (!isEmpty())
			head.prev = newHead;
		else
			tail = newHead;
		head = newHead;
		++size;
	}

    // add the item to the back
	public void addLast(Item item) {
		if (item == null)
			throw new IllegalArgumentException("exception deque: cannot insert null");
		Node newTail = new Node();
		newTail.value = item;
		newTail.next = null;
		newTail.prev = tail;
		if (!isEmpty())
			tail.next = newTail;
		else
			head = newTail;
		tail = newTail;
		++size;
	}

    // remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException("Exception Deque: no element in list");
		Item ret = head.value;
		Node newHead = head.next;
		head.next = null;
		head = newHead;
		if (head != null)
			head.prev = null;
		--size;
		return ret;
	}

    // remove and return the item from the back
	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException("Exception Deque: no element in list");
		Item ret = tail.value;
		Node newTail = tail.prev;
		tail.prev = null;
		tail = newTail;
		if (tail != null)
			tail.next = null;
		--size;
		return ret;
	}

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
		return new DequeIterator();
	}

    // unit testing (required)
	public static void main(String[] args) {
		Deque<Integer> dq = new Deque<Integer>();
		dq.addFirst(2);
		dq.addFirst(1);
		dq.addLast(3);
		dq.addLast(4);
		dq.addLast(5);
		dq.addLast(6);

		dq.removeFirst();
		dq.removeFirst();
		dq.removeLast();
		dq.removeLast();

		StdOut.printf("Deque size is %d\n", dq.size());

		Iterator<Integer> iter = dq.iterator();
		while(iter.hasNext()) {
			StdOut.printf("Iterator is at %d\n", iter.next());
		}
		try { 
			dq.iterator().remove();
		} catch(UnsupportedOperationException e) {
			StdOut.println(e);
		}

		while (!dq.isEmpty()) {
			int item = dq.removeFirst();
			StdOut.printf("Removed %d\n", item);
		}
		StdOut.println("The Deque is empty");
	}
}