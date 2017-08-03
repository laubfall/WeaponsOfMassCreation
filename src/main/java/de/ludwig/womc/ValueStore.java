package com.dpdhl.vls.ebay.txgroup.dat;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class ValueStore<V> implements Iterator<V>
{
  private final String name;

  private Set<V> values = new HashSet<>();

  private int maxValueCount = 1000;

  private int currentValueCount = 0;
  
  public ValueStore(String name)
  {
    super();
    this.name = name;
  }

  public ValueStore(String name, int maxValueCount)
  {
    this(name);
    this.maxValueCount = maxValueCount;
  }

  protected abstract V nextValue();

  @Override
  public boolean hasNext()
  {
    return currentValueCount < maxValueCount;
  }

  @Override
  public V next()
  {
    V nextValue = nextValue();
    currentValueCount++;
    return nextValue;
  }

  public String getName()
  {
    return name;
  }
}
