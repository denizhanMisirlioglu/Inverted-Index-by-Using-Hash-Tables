public class Node<V> {
    private String name;
    private V count;
    private String fileName;

    public Node(String name, V count, String fileName) {
        this.name = name;
        this.count = count;
        this.fileName=fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public V getCount() {
        return count;
    }

    public void setCount(V count) {
        this.count = count;
    }

    public String getInfo(){
        return count+"->"+name+"->"+fileName;
    }
}
