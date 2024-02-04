package com.dpdhl.vls.ebay.txgroup.dat;

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
