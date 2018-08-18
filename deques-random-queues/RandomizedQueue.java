import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private final int minSize = 2;
  private int size;
  private Item[] array;

  public RandomizedQueue() {
    array = (Item[]) new Object[minSize];
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Error: Trying to enqueue a null item.");
    }

    if (size == array.length) {
      resizeArray(size * 2);
    }

    array[size++] = item;
  }

  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException("Error: Queue is empty.");
    }

    int randomPosition = StdRandom.uniform(0, size);

    return array[randomPosition];
  }

  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("Error: Queue is empty.");
    }

    int randomPosition = StdRandom.uniform(0, size);
    Item item = array[randomPosition];
    size--;
    array[randomPosition] = array[size];
    array[size] = null;

    if (array.length > minSize && size <= array.length / 4) {
      resizeArray(array.length / 2);
    }

    return item;
  }

  private void resizeArray(int newSize) {
    Item[] newArray = (Item[]) new Object[newSize];
    // copy items
    for (int i = 0; i < size; i++) {
      newArray[i] = array[i];
    }

    array = newArray;
  }

  @Override
  public Iterator<Item> iterator() {
    return new RandomIterator();
  }

  private class RandomIterator implements Iterator<Item> {
    private Item[] itArray;
    private int index;

    public RandomIterator() {
      index = 0;

      itArray = createArrayCopy();
      StdRandom.shuffle(itArray);
    }

    @Override
    public boolean hasNext() {
      return index < itArray.length;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Error: Remove operation is not supported.");
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException("Error: No more items in random array.");
      }

      return itArray[index++];
    }

    private Item[] createArrayCopy() {
      Item[] newItArray = (Item[]) new Object[size];

      for (int i = 0; i < size; i++) {
        newItArray[i] = array[i];
      }

      return newItArray;
    }
  }
}
