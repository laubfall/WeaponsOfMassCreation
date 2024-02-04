package de.ludwig.womc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ValueStoreRepository
{
  private Map<LinkToValueStore, ValueStore<?>> repo = new HashMap<>();

  private static ValueStoreRepository INSTANCE = new ValueStoreRepository();
  
  private ValueStoreRepository() {
    
  }
  
  public static ValueStoreRepository get() {
    return INSTANCE;
  }
  
  public <S extends ValueStore<?>> LinkToValueStore addStore(S newStore)
  {
    final LinkToValueStore link = new LinkToValueStore(UUID.randomUUID().toString() + "-" + newStore.getName());
    repo.put(link, newStore);
    return link;
  }
  
  public <S extends ValueStore<?>> S get(LinkToValueStore link) {
    return (S) repo.get(link);
  }
}
