package cart.service;

import cart.dao.CartProductDao;
import cart.domain.CartProduct;
import cart.dto.CartProductRequest;
import cart.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartProductService {
    private final CartProductDao cartProductDao;

    public long save(long memberId, CartProductRequest cartProductRequest) {
        CartProduct cartProduct = new CartProduct(null, memberId, cartProductRequest.getProductId());
        return cartProductDao.save(cartProduct);
    }

    public List<ProductResponse> findAll(long memberId) {
        return cartProductDao.findAllProductByMemberId(memberId).stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }
}
