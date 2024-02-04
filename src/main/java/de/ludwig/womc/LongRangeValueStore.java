package de.ludwig.womc;

public class LongRangeValueStore extends RangeValueStore<Long>
{

  public LongRangeValueStore(String name, Long min, Long max)
  {
    super(name, min, max);
  }

  @Override
  protected Long nextValue()
  {
    Long nextVal = min;
    return null;
  }

}
