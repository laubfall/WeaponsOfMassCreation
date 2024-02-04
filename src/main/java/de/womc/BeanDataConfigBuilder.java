package de.womc;

import static java.util.Arrays.stream;

import de.womc.data.configurator.BeanDataConfig;
import de.womc.data.configurator.FieldConfig;
import de.womc.data.provider.IFieldDataProvider;
import de.womc.data.provider.ProviderConfig;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Optional;

public class BeanDataConfigBuilder {

  private BeanDataConfig beanDataConfig;

  private WomcBuilder<?> parent;

  private BeanDataConfigBuilder() {
    beanDataConfig = new BeanDataConfig();
  }

  public static BeanDataConfigBuilder beanDataConfig(WomcBuilder<?> parent) {
    var builder = new BeanDataConfigBuilder();
    builder.parent = parent;
    return builder;
  }

  public BeanDataConfigBuilder fieldConfig(String fieldName, IFieldDataProvider<?> config) {
    Optional<Field> fieldToConfigure = stream(parent.clazz.getDeclaredFields()).filter(f -> f.getName().equals(fieldName)).findFirst();
    if (fieldToConfigure.isEmpty()) {
      throw new IllegalArgumentException("Field " + fieldName + " not found in " + parent.clazz);
    }

    beanDataConfig.fieldConfigs.add(new FieldConfig(fieldToConfigure.get(), config));

    return this;
  }

  public BeanDataConfigBuilder fieldConfig(Type fieldType, IFieldDataProvider<?> provider){
    return this;
  }

  public WomcBuilder<?> finish() {
    parent.beanDataConfig = beanDataConfig;
    return parent;
  }
}
