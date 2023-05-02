package cart;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(value = "classpath:/dataTruncator.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public @interface JdbcMySqlDialectTest {
}
