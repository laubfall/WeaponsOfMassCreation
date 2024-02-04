package de.womc;

import static java.util.Arrays.stream;

import de.womc.data.configurator.BeanDataConfig;
import de.womc.data.configurator.FieldConfig;
import de.womc.data.provider.IFieldDataProvider;
import java.lang.reflect.Field;
import java.util.Optional;

public class BeanDataConfigBuilder {

  private final Womc womc;
  private BeanDataConfig beanDataConfig = new BeanDataConfig();

  private BeanDataConfigBuilder(Womc womc) {
    this.womc = womc;
    beanDataConfig = new BeanDataConfig();
  }

  public static BeanDataConfigBuilder beanDataConfig(Class<?> beanClass, Womc womc) {
    var builder = new BeanDataConfigBuilder(womc);
    builder.beanDataConfig.setBeanClass(beanClass);
    return builder;
  }

  public BeanDataConfigBuilder fieldConfig(String fieldName, IFieldDataProvider<?> config) {
    Optional<Field> fieldToConfigure = stream(beanDataConfig.getBeanClass().getDeclaredFields()).filter(f -> f.getName().equals(fieldName)).findFirst();
    if (fieldToConfigure.isEmpty()) {
      throw new IllegalArgumentException("Field " + fieldName + " not found in " + beanDataConfig.getBeanClass());
    }

    beanDataConfig.getFieldConfigs().add(new FieldConfig(fieldToConfigure.get(), config));

    return this;
  }

  public Womc build() {
    womc.addToRootBeanDataConfigs(beanDataConfig);
    return womc;
  }
}
