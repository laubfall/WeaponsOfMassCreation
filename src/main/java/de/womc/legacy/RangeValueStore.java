package de.womc.legacy;

public abstract class RangeValueStore<V extends Number> extends ValueStore<V>
{
  protected V min;
  
  protected V max;
  
  public RangeValueStore(String name, V min, V max)
  {
    super(name);    
    this.min = min;
    this.max = max;
  }

}
