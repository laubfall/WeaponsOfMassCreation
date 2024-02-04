package de.womc.legacy;

import java.util.function.BiFunction;

public class DataBlock<V, S extends ValueStore<V>, R, T>
{
  private DataBlock<V, ?, BiFunction<R, T, ?>, R> child = null;

  private final LinkToValueStore valueStoreLink;

  private final String name;

  private BiFunction<V, T, R> valueConsumer;

  public DataBlock(String name, LinkToValueStore valueStoreLink)
  {
    super();
    this.name = name;
    this.valueStoreLink = valueStoreLink;
  }

  public S getValueStore()
  {
    return ValueStoreRepository.get().get(valueStoreLink);
  }

  public void createValues(T resulting)
  {
    S valueStore = getValueStore();
    while (valueStore.hasNext()) {
      V value = valueStore.next();
      R result = valueConsumer.apply(value, resulting);

      if (child != null) {
        child.createValues(result);
      }
    }
  }

  public void setChild(DataBlock<V, ?, BiFunction<R, T, ?>, R> child)
  {
    this.child = child;
  }

  public void setValueConsumer(BiFunction<V, T, R> valueConsumer)
  {
    this.valueConsumer = valueConsumer;
  }
}
