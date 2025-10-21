public class DummyHeadLinkedList<T> implements List<T> {

    private static class Node<T>{
        T value;
        Node<T> next;
    }

    private final Node<T> head;
    private int size;

    public DummyHeadLinkedList(){
        head = new Node<>();
        size = 0;
    }

    @Override
    public boolean add (T element){
        add(size, element);
        return true;
    }

    @Override
    public void add(int index, T element){
        if(index < 0 || index > size) index = size;

        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;

        Node<T> node = new Node<>();
        node.value = element;
        node.next = current.next;
        current.next = node;

        size++;
    }

    @Override
    public T get(int index){
        if (index < 0 || index >= size) return null;

        Node<T> current = head.next;
        for (int i = 0; i < index; i++) current = current.next;

        return current.value;
    }

    @Override
    public T remove(int index){
        if (index < 0 || index >= size) return null;

        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;

        T removed = current.next.value;
        current.next = current.next.next;

        size--;
        return removed;
    }

    @Override
    public int size(){
        return size;
    }



}
