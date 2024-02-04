package de.womc.data.configurator;

import java.util.Collection;

public record BeanDataConfig(Class<?> bean, Collection<FieldConfig> fieldConfigs) {

}
