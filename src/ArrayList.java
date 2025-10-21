public class ArrayList<T> implements List<T>{
    private T[] data;
    private int size;

    public ArrayList() {
        data = (T[]) new Object[10];
        size = 0;
    }

    @Override
    public boolean add(T element){
        if (size == data.length){
            T[] newData = (T[]) new Object[data.length * 2];
            for(int i = 0; i < size; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }

        data[size] = element;
        size++;
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size){
            add(element);
            return;
        }

        if (size == data.length){
            T[] newData = (T[]) new Object [data.length * 2];
            for (int i = 0; i <size; i++){
                newData[i] = data[i];
            }
            data = newData;
        }

        for (int i = size; i > index; i--){
            data[i] = data[i-1];
        }

        data[index] = element;
        size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        return data[index];
    }

    @Override
    public T remove(int index){
        if (index < 0 || index >= size){
            return null;
        }

        T removed = data[index];
        for (int i = index; i < size; i++){
            data[i] = data [i + 1];
        }
        data[size - 1] = null;
        size--;
        return removed;
    }

    @Override
    public int size(){
        return size;
    }

}
