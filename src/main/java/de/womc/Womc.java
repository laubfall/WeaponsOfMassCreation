package de.womc;

import de.womc.data.provider.ProviderStore;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Womc {

  private final WomcCtx ctx;

  private ProviderStore providerStore = new ProviderStore();

  private Womc() {
    ctx = new WomcCtx();
    ctx.womc = this;
  }

  public static Womc start() {
    return new Womc();
  }

  public <C> WomcBuilder<C> of(Class<C> clazz) {
    var womcBuilder = WomcBuilder.of(clazz, ctx);
    ctx.add(womcBuilder);
    return womcBuilder;
  }


  public <C> Collection<C> create(Class<C> buildThis) {
    var result = new ArrayList<C>();

    WomcBuilder<C> builder = ctx.findBuilder(buildThis);

    for (int i = 0; i < builder.getCount(); i++) {
      result.add(initiate(builder));
    }

    return result;
  }

  public <C> C createOne(Class<C> buildThis) {
    WomcBuilder<C> builder = ctx.findBuilder(buildThis);
    if(builder != null && builder.getCount() > 1) {
      throw new IllegalArgumentException("Builder for class " + buildThis + " has a count > 1");
    }

    return initiate(WomcBuilder.of(buildThis, ctx));
  }

  private <C> C initiate(WomcBuilder<C> builder) {
    Constructor<?>[] declaredConstructors = builder.clazz.getDeclaredConstructors();

    try {
      C result = (C) declaredConstructors[0].newInstance();
      fill(result, builder);
      return result;
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private void fill(Object someBean, WomcBuilder<?> builder) {
    // use reflection to fill the bean
    Field[] declaredFields = someBean.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      field.setAccessible(true);
      try {
        var value = providerStore.getProvider(field.getType()).generate(null);
        field.set(someBean, value);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

  // Womc
  //	.of(My.class)
  //	.count(100)
  //  .sample()
  //		.field("homeGoals")
  //		.with(3)
  //		.count(10)
  //  .config()
  //  	.field(...)
  //  	.max(...)
  //    .export(JsonExporter.class)
  //		.build()
  //	.persist(MyRepository.class)
  //  .build();

  // Womc
  //	.of(My.class)
  //	.count(100)
  //  .sample()
  //		.with(myClass -> myClass.setHomeGoals(Womc.range(1, 3).count(10).value()))
  //  .config()
  //  	.field(myClass.getAwayGoals())
  //  	.max(...)
  //    .export(...)
  //		.build()
  //	.persist(MyRepository.class)
  //  .build();

  // Womc
  //	.of(My.class)
  //	.count(100)
  //  .sample()
  //		.with(myClass.getHomeGoals(), 3)
  //		.count(10)
  //  .config()
  //    .load(...)
  //  .build()
  public static class WomcCtx {

    private Womc womc;

    // This is maybe not the best approach when it comes to handle
    // object trees.
    private Set<WomcBuilder<?>> builders = new HashSet<>();

    void add(WomcBuilder<?> womcBuilder) {
      // lookup if builder already exists
      Optional<WomcBuilder<?>> existingBuilderOpt = builders.stream().filter(b -> b.clazz.equals(womcBuilder.clazz)).findFirst();
      if (existingBuilderOpt.isPresent()) {
        throw new IllegalArgumentException("Builder for class " + womcBuilder.clazz + " already exists");
      }
      builders.add(womcBuilder);
    }

    <C> WomcBuilder<C> findBuilder(Class<C> clazz) {
      var first = builders.stream().filter(b -> b.clazz.equals(clazz)).findFirst();
      if(first.isPresent()){
        return (WomcBuilder<C>) first.get();
      }

      return null;
    }

    public Womc getWomc() {
      return womc;
    }
  }
}
