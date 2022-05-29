package woowacourse.member.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.TMember.MARU;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.member.domain.Member;

@SpringBootTest
@Transactional
public class MemberDaoTest {

    @Autowired
    private MemberDao memberDao;

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        Member member = MARU.toEntity();

        Long id = memberDao.save(member);
        assertThat(id).isNotNull();
    }
}
