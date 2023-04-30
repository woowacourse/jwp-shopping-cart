package cart.persistence.repository;

import cart.exception.ForbiddenException;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.MemberCartEntity;
import cart.persistence.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberCartRepository.class)
class MemberCartRepositoryTest {

    @MockBean
    private MemberDao memberDao;

    @MockBean
    private CartDao cartDao;

    @Autowired
    private MemberCartRepository memberCartRepository;

    @Test
    @DisplayName("특정 사용자의 장바구니에 상품을 저장한다.")
    void save() {
        // given
        final MemberEntity memberEntity = new MemberEntity(1L, "journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        final long cartId = 1L;
        final long productId = 1L;
        final MemberCartEntity memberCartEntity = new MemberCartEntity(cartId, memberEntity.getId(),
                productId, "치킨", "chicken_image_url", 20000, "KOREAN");

        when(memberDao.findByEmail(any())).thenReturn(Optional.of(memberEntity));
        when(memberDao.findById(any())).thenReturn(Optional.of(memberEntity));
        when(cartDao.getProductsByMemberId(any())).thenReturn(List.of(memberCartEntity));
        when(cartDao.insert(any())).thenReturn(cartId);

        // when
        final long savedMemberId = memberCartRepository.save(memberEntity.getEmail(), memberCartEntity.getProductId());

        // then
        final List<MemberCartEntity> memberProductEntities = memberCartRepository.findByMemberEmail(memberEntity.getEmail());
        assertThat(memberProductEntities).hasSize(1);
        assertThat(memberProductEntities)
                .extracting("cartId", "memberId", "productId", "productName", "productImageUrl", "productPrice", "productCategory")
                .containsExactly(
                        tuple(cartId, savedMemberId, productId, "치킨", "chicken_image_url", 20000, "KOREAN"));
    }

    @Test
    @DisplayName("특정 사용자의 장바구니 리스트를 조회한다.")
    void findByMemberEmail() {
        // given
        final MemberEntity memberEntity = new MemberEntity(1L, "journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        final long cartId = 1L;
        final long productId = 1L;
        final MemberCartEntity memberCartEntity = new MemberCartEntity(cartId, memberEntity.getId(),
                productId, "치킨", "chicken_image_url", 20000, "KOREAN");

        when(memberDao.findByEmail(any())).thenReturn(Optional.of(memberEntity));
        when(memberDao.findById(any())).thenReturn(Optional.of(memberEntity));
        when(cartDao.getProductsByMemberId(any())).thenReturn(List.of(memberCartEntity));
        when(cartDao.insert(any())).thenReturn(cartId);
        long savedMemberId = memberCartRepository.save(memberEntity.getEmail(), productId);

        // when
        final List<MemberCartEntity> memberProductEntities = memberCartRepository.findByMemberEmail(memberEntity.getEmail());

        // then
        assertThat(memberProductEntities).hasSize(1);
        assertThat(memberProductEntities)
                .extracting("cartId", "memberId", "productId", "productName", "productImageUrl", "productPrice", "productCategory")
                .containsExactly(
                        tuple(cartId, savedMemberId, productId, "치킨", "chicken_image_url", 20000, "KOREAN"));

    }

    @Test
    @DisplayName("특정 사용자의 특정 상품에 대한 장바구니 정보를 제거한다.")
    void deleteByMemberEmail_success() {
        // given
        final MemberEntity memberEntity = new MemberEntity(1L, "journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        when(memberDao.findByEmail(any())).thenReturn(Optional.of(memberEntity));
        when(cartDao.deleteByMemberId(any(), any())).thenReturn(1);

        // when
        final int deletedCount = memberCartRepository.deleteByMemberEmail(memberEntity.getId(), memberEntity.getEmail(), 1L);

        // then
        assertThat(deletedCount).isSameAs(1);
    }

    @Test
    @DisplayName("해당 장바구니 상품에 대해 권한이 없는 사용자가 제거하려고 하면 예외가 발생한다.")
    void deleteByMemberEmail_fail() {
        // given
        final MemberEntity memberEntity = new MemberEntity(1L, "journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        when(memberDao.findByEmail(any())).thenReturn(Optional.of(memberEntity));
        when(cartDao.deleteByMemberId(any(), any())).thenReturn(1);

        // when, then
        assertThatThrownBy(() -> memberCartRepository.deleteByMemberEmail(2L, memberEntity.getEmail(), 1L))
                .isInstanceOf(ForbiddenException.class);
    }
}
