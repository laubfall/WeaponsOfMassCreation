package de.womc;

import de.womc.data.configurator.BeanDataConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Womc {

  private List<BeanDataConfig> rootBeanDataConfigs = new ArrayList<>();

  private Womc() {
  }

  public static Womc builder() {
    return new Womc();
  }

  public BeanDataConfigBuilder beanDataConfig(Class<?> beanClass) {
    return BeanDataConfigBuilder.beanDataConfig(beanClass, this);
  }

  public WomcBuilder<?> of(Class<?> clazz) {
    return WomcBuilder.of(clazz, this);
  }

  void addToRootBeanDataConfigs(BeanDataConfig beanDataConfig) {
    rootBeanDataConfigs.add(beanDataConfig);
  }

  BeanDataConfig findBeanDataConfig(Class<?> clazz) {
    Optional<BeanDataConfig> first = rootBeanDataConfigs.stream().filter(bdc -> bdc.getBeanClass().equals(clazz)).findFirst();
    return first.orElse(null);
  }
}
