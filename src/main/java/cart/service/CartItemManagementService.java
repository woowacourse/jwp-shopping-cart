package cart.service;

import cart.dao.CartItemDao;
import cart.dao.ProductDao;
import cart.domain.entity.CartItem;
import cart.domain.entity.Product;
import cart.dto.CartItemCreationDto;
import cart.dto.CartItemDetailsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemManagementService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemManagementService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartItemDetailsDto> findAllByMemberId(final Long memberId) {
        List<CartItem> cartItemEntities = cartItemDao.selectAllByMemberId(memberId);
        List<CartItemDetailsDto> cartItemDetailsDtos = new ArrayList<>();
        for (CartItem cartItem : cartItemEntities) {
            Product product = productDao.selectById(cartItem.getProductId());
            cartItemDetailsDtos.add(CartItemDetailsDto.from(cartItem.getId(), product));
        }
        return cartItemDetailsDtos;
    }

    public long save(final CartItemCreationDto cartItemCreationDto) {
        return cartItemDao.insert(CartItemCreationDto.toEntity(cartItemCreationDto));
    }

    public void deleteById(final Long id) {
        int deletedRowCount = cartItemDao.deleteById(id);
        if (deletedRowCount == 0) {
            throw new IllegalArgumentException("장바구니에 없는 상품입니다.");
        }
    }

}
