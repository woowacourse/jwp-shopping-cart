package cart.dao;

import cart.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberDaoTest {

    @Autowired
    MemberDao memberDao;

    @Test
    void save() {
        //when
        Member member = new Member(1L, "name", "password");
        memberDao.save(member);

        //then
        List<Member> members = memberDao.findAll();
        assertThat(members).containsExactly(member);
    }
}
