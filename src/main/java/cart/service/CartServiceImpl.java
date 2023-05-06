package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.cart.CartId;
import cart.domain.member.MemberId;
import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.CartRepository;
import cart.repository.ProductRepository;
import cart.service.response.CartResponse;

@Service
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;

	public CartServiceImpl(final CartRepository cartRepository, final ProductRepository productRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
	}

	@Transactional
	@Override
	public CartId insert(final MemberId memberId, final ProductId productId) {
		return cartRepository.insert(memberId, productId);
	}

	@Transactional
	@Override
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
}
