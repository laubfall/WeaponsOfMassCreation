package de.womc;

import de.womc.data.provider.RandomStringProvider;
import de.womc.types.BeanNoArgsConstructor;
import de.womc.types.NoArgsConstructorType;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WomcTest {

  @Test
  void no_args_constructor() {
    Collection<NoArgsConstructorType> result = Womc.builder()
        .of(NoArgsConstructorType.class)
        .count(10L)
        .create();
    Assertions.assertNotNull(result);
    Assertions.assertEquals(10, result.size());
  }

  @Test
  void simple_bean_no_args_constructor_default_value() {
    BeanNoArgsConstructor bean = Womc.builder()
        .of(BeanNoArgsConstructor.class)
        .createOne();

    Assertions.assertNotNull(bean);
    Assertions.assertNotNull(bean.getId());
  }

  @Test
  void simple_bean_no_args_constructor_configured_value() {

    Collection<BeanNoArgsConstructor> result =
        Womc.builder()
            .beanDataConfig(BeanNoArgsConstructor.class)
            .fieldConfig("id", RandomStringProvider.builder().maxLength(10).build()) // A builder for provider config would be appreciated here.
            .build()
            .of(BeanNoArgsConstructor.class)
            .count(10L)
            .create();
    Assertions.assertNotNull(result);
  }
}
