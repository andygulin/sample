package examples.showcase.impl;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface ImplementBy {

    Class<?> clazz();
}
