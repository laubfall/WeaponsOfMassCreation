package de.womc.data.provider;

import java.util.HashMap;
import java.util.Map;

public class ProviderStore {
  Map<Class<?>, IFieldDataProvider<?>> providers = new HashMap<>();

  public ProviderStore(){
      providers.put(String.class, new StringProvider("na"));
  }

  public <C> IFieldDataProvider<C> getProvider(Class<C> clazz) {
    return (IFieldDataProvider<C>) providers.get(clazz);
  }
}
