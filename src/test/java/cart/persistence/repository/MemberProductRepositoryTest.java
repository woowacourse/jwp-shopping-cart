package cart.persistence.repository;

import cart.persistence.dao.CartDao;
import cart.persistence.dao.MemberDao;
import cart.persistence.entity.MemberEntity;
import cart.persistence.entity.MemberProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberProductRepository.class)
class MemberProductRepositoryTest {

    @MockBean
    private MemberDao memberDao;

    @MockBean
    private CartDao cartDao;

    @Autowired
    private MemberProductRepository memberCartRepository;

    @Test
    @DisplayName("특정 사용자의 장바구니에 상품을 저장한다.")
    void save() {
        // given
        final MemberEntity memberEntity = new MemberEntity(1L, "journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        final long cartId = 1L;
        final long productId = 1L;
        final MemberProductEntity memberProductEntity = new MemberProductEntity(cartId, memberEntity.getId(),
                productId, "치킨", "chicken_image_url", 20000, "KOREAN");

        when(memberDao.findByEmail(any())).thenReturn(Optional.of(memberEntity));
        when(memberDao.findById(any())).thenReturn(Optional.of(memberEntity));
        when(cartDao.getProductByMemberId(any())).thenReturn(List.of(memberProductEntity));
        when(cartDao.insert(any())).thenReturn(cartId);

        // when
        final long savedMemberId = memberCartRepository.save(memberEntity.getEmail(), memberProductEntity.getProductId());

        // then
        final List<MemberProductEntity> memberProductEntities = memberCartRepository.findByMemberEmail(memberEntity.getEmail());
        assertThat(memberProductEntities).hasSize(1);
        assertThat(memberProductEntities)
                .extracting("cartId", "memberId", "productId", "productName", "productImageUrl", "productPrice", "productCategory")
                .containsExactly(
                        tuple(cartId, savedMemberId, productId, "치킨", "chicken_image_url", 20000, "KOREAN"));
    }

    @Test
    @DisplayName("특정 사용자의 장바구니 리스트를 조회한다.")
    void findByMemberId() {
        // given
        final MemberEntity memberEntity = new MemberEntity(1L, "journey@gmail.com", "cGFzc3dvcmQ=", "져니", "010-1234-5678");
        final long cartId = 1L;
        final long productId = 1L;
        final MemberProductEntity memberProductEntity = new MemberProductEntity(cartId, memberEntity.getId(),
                productId, "치킨", "chicken_image_url", 20000, "KOREAN");

        when(memberDao.findByEmail(any())).thenReturn(Optional.of(memberEntity));
        when(memberDao.findById(any())).thenReturn(Optional.of(memberEntity));
        when(cartDao.getProductByMemberId(any())).thenReturn(List.of(memberProductEntity));
        when(cartDao.insert(any())).thenReturn(cartId);
        long savedMemberId = memberCartRepository.save(memberEntity.getEmail(), productId);

        // when
        final List<MemberProductEntity> memberProductEntities = memberCartRepository.findByMemberEmail(memberEntity.getEmail());

        // then
        assertThat(memberProductEntities).hasSize(1);
        assertThat(memberProductEntities)
                .extracting("cartId", "memberId", "productId", "productName", "productImageUrl", "productPrice", "productCategory")
                .containsExactly(
                        tuple(cartId, savedMemberId, productId, "치킨", "chicken_image_url", 20000, "KOREAN"));

    }
}
