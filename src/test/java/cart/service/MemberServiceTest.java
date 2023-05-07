package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dto.MembetDto;
import cart.fixture.MemberFixture.BLACKCAT;
import cart.fixture.MemberFixture.HERB;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void 모든_유저를_조회한다() {
        // expect
        final List<MembetDto> results = memberService.findAll();
        assertThat(results).usingRecursiveComparison().isEqualTo(List.of(
                HERB.MEMBET_DTO,
                BLACKCAT.MEMBET_DTO
        ));
    }
}
