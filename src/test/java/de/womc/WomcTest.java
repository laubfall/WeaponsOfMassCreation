package de.womc;

import de.womc.data.provider.ProviderConfig;
import de.womc.data.provider.RandomStringProvider;
import de.womc.data.provider.StringProvider;
import de.womc.types.BeanNoArgsConstructor;
import de.womc.types.NoArgsConstructorType;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WomcTest {
  @Test
  void no_args_constructor(){
    Collection<NoArgsConstructorType> result = Womc.start()
        .of(NoArgsConstructorType.class)
        .count(10L)
        .finish()
        .create(NoArgsConstructorType.class);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(10, result.size());
  }

  @Test
  void simple_bean_no_args_constructor_default_value(){
    BeanNoArgsConstructor bean = Womc.start()
        .createOne(BeanNoArgsConstructor.class);

    Assertions.assertNotNull(bean);
    Assertions.assertNotNull(bean.getId());
  }

  @Test
  void simple_bean_no_args_constructor_configured_value(){

    Collection<BeanNoArgsConstructor> result =
        Womc.start()
          .of(BeanNoArgsConstructor.class)
            .count(10L)
            .configure()
            .fieldConfig("id", RandomStringProvider.builder().maxLength(10).build()) // A builder for provider config would be appreciated here.
            .fieldConfig(String.class, StringProvider.builder().value("womcTest").build()) // for similar value shapes for all string fields
            .finish()
          .finish()
        .create(BeanNoArgsConstructor.class);
    Assertions.assertNotNull(result);
  }
}
