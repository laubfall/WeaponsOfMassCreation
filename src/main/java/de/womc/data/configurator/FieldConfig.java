package de.womc.data.configurator;

import de.womc.data.provider.IFieldDataProvider;
import java.lang.reflect.Field;

public record FieldConfig(Field field, IFieldDataProvider<?> provider) {

}
