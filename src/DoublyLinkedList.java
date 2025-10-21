public class DoublyLinkedList<T> implements List<T> {

    private static class Node<T>{
        T value;
        Node<T> next;
        Node<T> prev;
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T element){
        Node<T> node = new Node<>();
        node.value = element;

        if (head == null){
            head = tail = node;
        } else{
            tail.next = node;
            node.prev = tail;
            tail = node;
        }

        size++;
        return true;
    }

    @Override
    public void add(int index, T element){
        if(index < 0 || index > size){
            add(element);
            return;
        }

        Node<T> node = new Node<>();
        node.value = element;

        if (index == 0) {
            node.next = head;
            if (head != null) head.prev = node;
            head = node;
            if (tail == null) tail = node;
        }else if (index == size){
            tail.next = node;
            node.prev = tail;
            tail = node;
        }else {
            Node<T> current = head;
            for(int i = 0;  i < index; i++){
                current = current.next;
            }
            node.prev = current.prev;
            node.next = current;
            current.prev.next = node;
            current.prev = node;
        }
        size++;
    }

    @Override
    public T get(int index){
        if (index < 0 || index >= size) return null;
        Node<T> current = head;
        if (index < size / 2){
            current = head;
            for (int i =  0; i < index; i++) {
                current = current.next;
            }
        }else{
            current = tail;
            for(int i = size - 1; i > index; i--){
                current = current.prev;
            }
        }
        return current.value;
    }

    @Override
    public T remove(int index){
        if (index < 0 || index >= size){
            return null;
        }

        T removed;

        if (index == 0){
            removed = head.value;
            head = head.next;
            if(head != null) head.prev = null;
            else tail = null;
        }else if (index == size - 1){
            removed = tail.value;
            tail = tail.prev;
            if (tail != null) tail.next = null;
            else head = null;
        }else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            removed = current.value;
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return removed;
    }
    @Override
    public int size(){
        return size;
    }
}

