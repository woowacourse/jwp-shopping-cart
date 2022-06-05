package woowacourse.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DiTest {

    @Qualifier("hi")
    @Autowired
    private HelloOrHi helloOrHi;

    @Test
    void helloOrHi() {
        assertThat(helloOrHi.print()).isEqualTo("hi");
    }
}
