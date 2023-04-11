import java.util.LinkedList;

public class HashEntry<K, V> {

    private K key;
    private LinkedList value;


    HashEntry(K key) {
        this.key = key;
        this.value = new LinkedList();
    }
    HashEntry(K key, LinkedList value){
        this.key=key;
        this.value=value;
    }

    public K getKey() {
        return key;
    }

    public V getValue(K key) {
        return (V) value.get(value.indexOf(key));
    }

    public LinkedList getList(){
        return value;
    }

}
