package cart.service;

import cart.controller.dto.CartDto;
import cart.persistence.entity.MemberProductEntity;
import cart.persistence.repository.MemberProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final MemberProductRepository memberCartRepository;

    public CartService(final MemberProductRepository memberCartRepository) {
        this.memberCartRepository = memberCartRepository;
    }

    public long addCart(final String memberEmail, final Long productId) {
        return memberCartRepository.save(memberEmail, productId);
    }

    public List<CartDto> getProductsByMemberEmail(final String memberEmail) {
        final List<MemberProductEntity> memberProductEntities = memberCartRepository.findByMemberEmail(memberEmail);
        return memberProductEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toUnmodifiableList());
    }

    private CartDto convertToDto(final MemberProductEntity memberProductEntity) {
        return new CartDto(memberProductEntity.getMemberId(), memberProductEntity.getProductId(),
                memberProductEntity.getProductName(), memberProductEntity.getProductImageUrl(),
                memberProductEntity.getProductPrice(), memberProductEntity.getProductCategory());
    }
}
