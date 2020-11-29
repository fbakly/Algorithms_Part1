import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] vals;
    private int size;
    private int capacity;

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] valsCopy;
        private int sizeCopy;

        public RandomizedQueueIterator() {
            sizeCopy = size();
            valsCopy = (Item[]) new Object[capacity];

            for (int index = 0; index < sizeCopy; index++)
                valsCopy[index] = vals[index];
        }
        @Override
        public boolean hasNext() {
            return sizeCopy > 0;
        }

        @Override
        public Item next() {
            if (isEmpty())
                throw new NoSuchElementException();
            int rand = StdRandom.uniform(sizeCopy);
            Item ret = valsCopy[rand];
            if (rand != --sizeCopy)
                valsCopy[rand] = valsCopy[sizeCopy];
            valsCopy[sizeCopy] = null;
            return ret;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    // construct an empty randomized queue
	public RandomizedQueue() {
        this.size = 0;
        this.capacity = 1;
		this.vals = (Item []) new Object[this.capacity];
	}

    // is the randomized queue empty?
    public boolean isEmpty() { return this.size == 0; }

    // return the number of items on the randomized queue
    public int size() { return this.size; }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (size == capacity) {
            capacity *= 2;
            resize(capacity);
        }
        vals[size++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        
        int rand = StdRandom.uniform(size);
        Item ret = vals[rand];
        if (rand != --size)
            vals[rand] = vals[size];
        vals[size] = null;
        if (size <= capacity/4) {
            capacity /= 2;
            resize(capacity);
        }
        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return vals[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

    private void resize(int cap) {
        Item[] copy = (Item []) new Object[cap];
        for (int index = 0; index < size; index++)
            copy[index] = vals[index];
        vals = copy;

    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> randq = new RandomizedQueue<Integer>();

        for (int i = 0; i < 5; i++)
            randq.enqueue(i);

        for (int i : randq)
            StdOut.print(i + " ");
        StdOut.println();
        
        StdOut.println("Dequed " + randq.dequeue());
        StdOut.println("Dequed " + randq.dequeue());
        StdOut.println("Here is a sample: " + randq.sample());

        Iterator<Integer> iter = randq.iterator();

        StdOut.println("Going to iterate over the randomized queue");
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }

        try {
            iter.remove();
        } catch (UnsupportedOperationException e) {
            StdOut.println("Caught remove exception");
        }

        StdOut.println("Size of the queue is " + randq.size());

        while (!randq.isEmpty())
            StdOut.println("Dequed " + randq.dequeue());

        StdOut.println("Size of the queue is " + randq.size());

    }

}
