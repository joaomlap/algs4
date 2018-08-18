import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private Node<Item> first;
  private Node<Item> last;
  private int size;

  public Deque() {
    first = null;
    last = null;
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Error: Item cannot be null.");
    }

    if (isEmpty()) {
      Node<Item> newNode = new Node<>();
      newNode.item = item;
      first = newNode;
      last = newNode;
    } else {
      Node<Item> oldFirst = first;
      first = new Node<Item>();
      first.item = item;
      first.next = oldFirst;
      oldFirst.prev = first;
    }

    size++;
  }

  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Error: Item cannot be null.");
    }

    if (isEmpty()) {
      Node<Item> newNode = new Node<>();
      newNode.item = item;
      last = newNode;
      first = newNode;
    } else {
      Node<Item> oldLast = last;
      last = new Node<Item>();
      last.item = item;
      last.prev = oldLast;
      oldLast.next = last;
    }

    size++;
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException("Error: Deque is empty!");
    }

    Item item = first.item;
    first = first.next;

    if (first == null) {
      last = null;
    } else {
      first.prev = null;
    }

    size--;

    return item;
  }

  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException("Error: Deque is empty!");
    }

    Item item = last.item;
    last = last.prev;

    if (last == null) {
      first = null;
    } else {
      last.next = null;
    }

    size--;

    return item;
  }

  private class Node<Item> {
    private Node<Item> next;
    private Node<Item> prev;
    private Item item;
  }

  @Override
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private Node<Item> current = first;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException("Error: There are no more elements in deque.");
      }

      Item item = current.item;
      current = current.next;

      return item;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Error: Remove is not supported.");
    }
  }
}
