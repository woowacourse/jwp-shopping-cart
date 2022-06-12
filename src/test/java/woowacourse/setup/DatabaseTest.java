package woowacourse.setup;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(Lifecycle.PER_CLASS)
@Sql(scripts = "classpath:test_schema.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class DatabaseTest {
}
