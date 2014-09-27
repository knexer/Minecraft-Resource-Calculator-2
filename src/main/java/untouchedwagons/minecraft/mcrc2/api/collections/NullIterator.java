package untouchedwagons.minecraft.mcrc2.api.collections;

import java.util.Iterator;

/**
 * A NullIterator never has anything, could be useful
 * @param <T>
 */
public class NullIterator<T> implements Iterator<T> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
