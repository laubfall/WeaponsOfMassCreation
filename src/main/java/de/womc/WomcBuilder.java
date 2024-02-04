package de.womc;

import de.womc.data.configurator.BeanDataConfig;
import de.womc.data.configurator.FieldConfig;
import de.womc.data.provider.IFieldDataProvider;
import de.womc.data.provider.ProviderStore;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

public final class WomcBuilder<C> {

  // Actually not used but maybe required in case we want to create data for more than one type.
  private Womc womc;

  Class<C> clazz;

  private Long count = 1L;

  private boolean initiateViaConstructor = false;

  private ProviderStore providerStore = new ProviderStore();

  private WomcBuilder() {
  }

  public static <C> WomcBuilder<C> of(Class<C> clazz, Womc womc) {
    var builder = new WomcBuilder<C>();
    builder.clazz = clazz;
    builder.womc = womc;
    return builder;
  }

  public WomcBuilder<C> count(Long count) {
    this.count = count;
    return this;
  }

  public WomcBuilder<C> initiateViaConstructor() {
    this.initiateViaConstructor = true;
    return this;
  }

  public <C> C createOne() {
    if (count > 1) {
      throw new IllegalArgumentException("Builder for class " + clazz + " has a count > 1");
    }

    BeanDataConfig beanDataConfig = womc.findBeanDataConfig(clazz);
    return initiate(clazz, beanDataConfig);
  }

  public <C> Collection<C> create() {
    var result = new ArrayList<C>();

    BeanDataConfig beanDataConfig = womc.findBeanDataConfig(clazz);
    for (int i = 0; i < count; i++) {
      result.add(initiate(clazz, beanDataConfig));
    }

    return result;
  }

  private <C> C initiate(Class<?> beanClass, BeanDataConfig configForBeanClass) {
    Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

    try {
      C result = (C) declaredConstructors[0].newInstance();
      fill(result, configForBeanClass);
      return result;
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private void fill(Object someBean, BeanDataConfig beanDataConfig) {

    // use reflection to fill the bean
    Field[] declaredFields = someBean.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      field.setAccessible(true);
      try {
        var value = provider(field, beanDataConfig).generate(null);
        field.set(someBean, value);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private IFieldDataProvider<?> provider(Field fieldToFill, BeanDataConfig beanDataConfig) {

    if(beanDataConfig == null){
      return providerStore.getProvider(fieldToFill.getType());
    }

    FieldConfig fieldConfig = beanDataConfig.getFieldConfigs().stream().filter(fc -> fc.field().equals(fieldToFill)).findFirst().orElse(null);
    if (fieldConfig != null) {
      return fieldConfig.provider();
    }

    return providerStore.getProvider(fieldToFill.getType());
  }

}
