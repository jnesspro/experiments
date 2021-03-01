package pro.jness.experiments.resttemplate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {TargetServerClientAutoConfiguration.class})
@ConditionalOnBean(DefaultTargetServerClient.class)
public @interface EnableTargetServerClient {
}