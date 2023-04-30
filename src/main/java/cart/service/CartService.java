package cart.service;

import cart.controller.dto.CartDto;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.entity.MemberCartEntity;
import cart.persistence.repository.MemberCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final MemberCartRepository memberCartRepository;

    public CartService(final MemberCartRepository memberCartRepository) {
        this.memberCartRepository = memberCartRepository;
    }

    public long addCart(final String memberEmail, final Long productId) {
        return memberCartRepository.save(memberEmail, productId);
    }

    public List<CartDto> getProductsByMemberEmail(final String memberEmail) {
        final List<MemberCartEntity> memberProductEntities = memberCartRepository.findByMemberEmail(memberEmail);
        return memberProductEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void deleteCart(final String memberEmail, final Long productId) {
        int deletedCount = memberCartRepository.deleteByMemberEmail(memberEmail, productId);
        if (deletedCount != 1) {
            throw new GlobalException(ErrorCode.CART_INVALID_DELETE);
        }
    }

    private CartDto convertToDto(final MemberCartEntity memberCartEntity) {
        return new CartDto(memberCartEntity.getMemberId(), memberCartEntity.getProductId(),
                memberCartEntity.getProductName(), memberCartEntity.getProductImageUrl(),
                memberCartEntity.getProductPrice(), memberCartEntity.getProductCategory());
    }
}
