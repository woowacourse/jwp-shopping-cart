package cart;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudyTest {

    @Test
    @DisplayName("")
    public void testOptionalFindFirstNull() {
        //given
        List<String> list = List.of("a", "b");

        //when
        Optional<String> result = list.stream()
            .filter(x -> x.equals("c"))
            .findFirst();

        //then
        Assertions.assertThat(result.isEmpty()).isTrue();
    }
}
