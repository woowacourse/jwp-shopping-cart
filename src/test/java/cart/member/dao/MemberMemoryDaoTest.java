package cart.member.dao;

import cart.config.DBTransactionExecutor;
import cart.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
class MemberMemoryDaoTest {
    @RegisterExtension
    private DBTransactionExecutor dbTransactionExecutor;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private MemberDao memberDao;
    
    @Autowired
    public MemberMemoryDaoTest(final JdbcTemplate jdbcTemplate) {
        this.dbTransactionExecutor = new DBTransactionExecutor(jdbcTemplate);
    }
    
    @BeforeEach
    void setUp() {
        memberDao = new MemberMemoryDao(namedParameterJdbcTemplate);
    }
    
    @Test
    void Member를_전달하면_회원_정보를_저장한다() {
        // given
        final Member member = new Member("a@a.com", "password1");
        
        // when
        final Long memberId = memberDao.save(member);
        
        // then
        assertThat(memberId).isOne();
    }
    
    @Test
    void 모든_Member_정보를_가져온다() {
        // given
        final Member firstMember = new Member("a@a.com", "password1");
        final Member secondMember = new Member("b@b.com", "password2");
        memberDao.save(firstMember);
        memberDao.save(secondMember);
        
        // when
        final List<Member> members = memberDao.findAll();
        
        // then
        final Member expectedFirstMember = new Member(1L, "a@a.com", "password1");
        final Member expectedSecondMember = new Member(2L, "b@b.com", "password2");
        assertThat(members).containsExactly(expectedFirstMember, expectedSecondMember);
    }
    
    @Test
    void email과_password를_전달하면_해당하는_Member_정보를_가져온다() {
        // given
        final Member firstMember = new Member("a@a.com", "password1");
        final Member secondMember = new Member("b@b.com", "password2");
        memberDao.save(firstMember);
        memberDao.save(secondMember);
        
        // when
        final Member member = memberDao.findByEmailAndPassword("b@b.com", "password2");
        
        // then
        assertThat(member).isEqualTo(new Member(2L, "b@b.com", "password2"));
    }
}
