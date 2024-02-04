package de.womc.data.provider;

public interface IFieldDataProvider<D> {

	D generate(ProviderConfig config);
}
