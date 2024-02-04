package de.womc.data.provider;

import lombok.Builder;

@Builder
public class RandomStringProvider implements IFieldDataProvider<String> {

  private int maxLength;
  private boolean alphanumeric;

  @Override
  public String generate(ProviderConfig config) {
    return null;
  }
}
