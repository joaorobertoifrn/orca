
package ifrn;

import java.util.Iterator;

public class IteratorWrapper<T> implements Iterable<T> {

    private final Iterator<T> iterator;

    public IteratorWrapper(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    public Iterator<T> iterator() {
        return iterator;
    }
}