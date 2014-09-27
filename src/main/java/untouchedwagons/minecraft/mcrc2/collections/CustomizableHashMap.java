package untouchedwagons.minecraft.mcrc2.collections;

import java.util.HashMap;

public class CustomizableHashMap<K, V> extends HashMap<K, V> {
    private Equatable comparer;

    public CustomizableHashMap(Equatable comparer)
    {
        this.comparer = comparer;
    }

    public Equatable getComparer()
    {
        return this.comparer;
    }

    @Override
    public boolean containsKey(Object key)
    {
        for (K o : super.keySet())
        {
            if (this.comparer.objectsAreEqual(o, key))
                return true;
        }

        return false;
    }

    @Override
    public V get(Object key)
    {
        for (K o : super.keySet())
        {
            if (this.comparer.objectsAreEqual(o, key))
                return super.get(o);
        }

        return null;
    }

    @Override
    public V put (K key, V value)
    {
        for (K o : super.keySet())
        {
            if (this.comparer.objectsAreEqual(o, key))
                return super.put(o, value);
        }

        return super.put(key, value);
    }
}
