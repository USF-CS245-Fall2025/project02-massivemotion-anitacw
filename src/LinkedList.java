public class LinkedList<T> implements List<T> {
    private static class Node<T> {
        T value;
        Node<T> next;
    }

    private Node<T> head;
    private int size;

    @Override
    public boolean add(T element) {
        Node<T> node = new Node<>();
        node.value = element;

        if(head == null) {
            head = node;
        } else {
            Node<T> current = head;
            while (current.next != null){
                current = current.next;
            }
            current.next = node;
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

        LinkedList.Node<T> node = new LinkedList.Node<>();
        node.value = element;

        if (index == 0) {
            node.next = head;
            head = node;
        } else {
            LinkedList.Node<T> current = head;
            for(int i = 0;  i < index -1; i++){
                current = current.next;
            }
            node.next = current.next;
            current.next = node;
        }
        size++;
    }

    @Override
    public T get (int index){
      if (index < 0 || index >= size) return null;
      Node<T> current = head;
      for (int i =  0; i < index; i++) {
          current = current.next;
      }
        return current.value;
    }

    @Override
    public T remove (int index){
        if (index < 0 || index >= size){
            return null;
        }

        T removed = null;

        if (index == 0){
            removed = head.value;
            head = head.next;
        }else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removed = current.next.value;
            current.next = current.next.next;
        }
        size--;
        return removed;
    }

    @Override
    public int size (){
        return size;
    }
}
