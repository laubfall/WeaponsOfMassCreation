package de.womc;

import de.womc.Womc.WomcCtx;
import de.womc.data.configurator.BeanDataConfig;

public final class WomcBuilder<C> {

  Class<C> clazz;

  private Long count = 1L;

  private boolean initiateViaConstructor = false;

  BeanDataConfig beanDataConfig;

  private WomcCtx ctx;

  private WomcBuilder() {
  }

  public static <C> WomcBuilder<C> of(Class<C> clazz, WomcCtx ctx) {
    var builder = new WomcBuilder<C>();
    builder.clazz = clazz;
    builder.ctx = ctx;
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

  public Womc finish() {
    return ctx.getWomc();
  }

  Class<C> getClazz() {
    return clazz;
  }

  Long getCount() {
    return count;
  }

  boolean isInitiateViaConstructor() {
    return initiateViaConstructor;
  }

  BeanDataConfig getBeanDataConfig() {
    return beanDataConfig;
  }

  public BeanDataConfigBuilder configure() {
    return BeanDataConfigBuilder.beanDataConfig(this);
  }
}
