package de.womc.data.configurator;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;

@Data
public class BeanDataConfig {

  private Class<?> beanClass;

  private Collection<FieldConfig> fieldConfigs = new ArrayList<>();

  private BeanDataConfig parent;
}
