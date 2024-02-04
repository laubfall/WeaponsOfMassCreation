package de.womc.data.configurator;

import de.womc.data.provider.IFieldDataProvider;

public record Config<D>(boolean nullable, int minLenth, int maxLength, IFieldDataProvider<D> dataProvider) {

}
