package woowacourse.support.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema-test.sql", "classpath:data-test.sql"})
public @interface ExtendedTest {
}
