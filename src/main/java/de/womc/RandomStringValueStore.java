package de.womc;

import org.apache.commons.lang.RandomStringUtils;

public class RandomStringValueStore extends ValueStore<String>
{
  private int valueLength;
  
  
  public RandomStringValueStore(String name, int valueLength)
  {
    super(name);
    this.valueLength = valueLength;
  }

  @Override
  protected String nextValue()
  {
    String random = RandomStringUtils.random(valueLength);
    return random;
  }

}
