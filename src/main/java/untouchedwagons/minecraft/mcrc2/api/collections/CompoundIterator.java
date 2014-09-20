package untouchedwagons.minecraft.mcrc2.api.collections;

import java.util.Iterator;

public class CompoundIterator<T> implements Iterator<T> {
    private final Iterator<T>[] iterators;
    private int pos = 0;

    public CompoundIterator(Iterator<T>... iterators) {
        this.iterators = iterators;
    }

    @Override
    public boolean hasNext() {
        // Does the current one have more?
        if (this.iterators[this.pos].hasNext())
            return true;

        // If there are no more iterators
        if (++this.pos == this.iterators.length)
            return false;

        return this.hasNext();
    }

    @Override
    public T next() {
        return this.iterators[this.pos].next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
