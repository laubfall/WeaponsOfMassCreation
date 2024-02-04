package de.womc.data.provider;

import lombok.Builder;

@Builder
public class StringProvider implements IFieldDataProvider<String> {

  private String value;

  @Override
  public String generate(ProviderConfig config) {
    return value;
  }

}
