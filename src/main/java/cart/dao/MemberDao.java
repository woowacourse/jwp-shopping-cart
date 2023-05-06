package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Component;

import java.util.List;
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
=======
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)

@Component
public class MemberDao {

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
    private static final RowMapper<MemberEntity> rowMapper =
            (rs, rowNum) -> new MemberEntity(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3)
            );

<<<<<<< HEAD
=======
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
=======
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(final MemberEntity entity) {
<<<<<<< HEAD
<<<<<<< HEAD
        String sql = "INSERT INTO MEMBER (email, password) VALUES (?, ?)";
=======
        String sql = "INSERT INTO MEMEBER (email, password) VALUES (?, ?)";
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
=======
        String sql = "INSERT INTO MEMBER (email, password) VALUES (?, ?)";
>>>>>>> db0c1803 (feat: CartDao save 테스트)
        jdbcTemplate.update(sql, entity.getEmail(), entity.getPassword());
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(sql, (rs, rowNum)
                        -> new MemberEntity(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password")
                )
        );
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
    public Optional<MemberEntity> findByEmail(String email) {
        String sql = "SELECT * FROM MEMBER WHERE email = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, email));
    }
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
=======
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
=======

>>>>>>> 339fefaa (feat: findAllByMemberId 테스트 및 테스트 전용 sql 파일 설정)
}
