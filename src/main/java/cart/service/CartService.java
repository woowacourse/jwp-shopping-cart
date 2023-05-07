package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.cart.CartId;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.CartRepository;
import cart.repository.MemberRepository;
import cart.repository.ProductRepository;
import cart.service.response.CartResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public CartId insert(final String email, final ProductId productId) {
		final Member member = memberRepository.findByEmail(email);
		return cartRepository.insert(member.getId(), productId);
	}

	@Transactional
	public List<CartResponse> findAllByEmail(final String email) {
		return cartRepository.findAllByEmail(email)
			.stream()
			.map(cart -> {
				final Product product = productRepository.findByProductId(cart.getProductId());
				return new CartResponse(cart.getId().getId(),
					product.getImage(),
					product.getName(),
					product.getPrice()
				);
			})
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteById(final MemberId memberId, final ProductId productId){
		final boolean isDelete = cartRepository.deleteById(memberId, productId);

		if (!isDelete) {
			throw new IllegalStateException("상품 삭제에 실패했습니다.");
		}
	}
}
