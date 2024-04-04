public class Association<K, V> {
    private final K key;
    private final V english;
    private final V spanish;
    private final V french;

    public Association(K key, V english, V spanish, V french) {
        this.key = key;
        this.english = english;
        this.spanish = spanish;
        this.french = french;
    }

    public K getKey() {
        return key;
    }

    public V getEnglish() {
        return english;
    }

    public V getSpanish() {
        return spanish;
    }

    public V getFrench() {
        return french;
    }

    public String toString() {
        return key + " -> " + english + ", " + spanish + ", " + french;
    }

 

    
}