package de.womc.data.provider;

public class StringProvider implements IFieldDataProvider<String> {


	@Override
	public String generate() {
		return "test";
	}

}
