package be.ordina.msdashboard.security.config.strategies;

import be.ordina.msdashboard.security.config.strategies.ForwardOAuth2TokenStrategyConfiguration.Condition;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andreas Evers
 */
public class ForwardOAuth2TokenStrategyConfigurationTest {

    @Test
    public void neither() throws Exception {
        AnnotationConfigApplicationContext context = load(Config.class);
        assertThat(context.containsBean("myBean")).isFalse();
        context.close();
    }

    @Test
    public void propertiesStyledProperty() throws Exception {
        AnnotationConfigApplicationContext context = load(Config.class, "msdashboard.security.strategies.forward-oauth2-token:mappings,pacts");
        assertThat(context.containsBean("myBean")).isTrue();
        context.close();
    }

    @Test
    public void yamlStyledProperty() throws Exception {
        AnnotationConfigApplicationContext context = load(Config.class, "msdashboard.security.strategies.forward-oauth2-token[0]:mappings");
        assertThat(context.containsBean("myBean")).isTrue();
        context.close();
    }

    @Configuration
    @Conditional(Condition.class)
    public static class Config {

        @Bean
        public String myBean() {
            return "myBean";
        }

    }

    private AnnotationConfigApplicationContext load(Class<?> config, String... env) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        EnvironmentTestUtils.addEnvironment(context, env);
        context.register(config);
        context.refresh();
        return context;
    }
}
