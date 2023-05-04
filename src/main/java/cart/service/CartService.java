package cart.service;

import cart.dto.ProductDto;
import cart.entity.Cart;
import cart.entity.Member;
import cart.entity.Product;
import cart.exception.customExceptions.DataNotFoundException;
import cart.repository.dao.cartDao.CartDao;
import cart.repository.dao.memberDao.MemberDao;
import cart.repository.dao.productDao.ProductDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final MemberDao memberDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public List<ProductDto> findAllCartProductByEmail(final String email) {
        final Optional<Member> findMember = memberDao.findByEmail(email);
        if (findMember.isEmpty()) {
            throw new DataNotFoundException("해당 사용자가 존재하지 않습니다.");
        }
        final Member member = findMember.get();

        final List<Long> productIds = cartDao.findAllProductIdByMemberId(member.getId());
        if (productIds.isEmpty()) {
            return Collections.emptyList();
        }

        final List<ProductDto> products = new ArrayList<>();
        for (Long id : productIds) {
            final Optional<Product> findProduct = productDao.findById(id);
            if (findProduct.isEmpty()) {
                throw new DataNotFoundException("해당 상품을 찾을 수 없습니다.");
            }
            final Product product = findProduct.get();
            products.add(new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getImageUrl(),
                    product.getPrice()));
        }

        return products;
    }

    public Long addProductInCart(final Long productId, final String email) {
        final Optional<Member> findMember = memberDao.findByEmail(email);
        if (findMember.isEmpty()) {
            throw new DataNotFoundException("해당 사용자가 존재하지 않습니다.");
        }
        final Member member = findMember.get();
        final Cart cart = new Cart(member.getId(), productId);
        final Long id = cartDao.save(cart);
        return id;
    }

    public void deleteProductInCart(final Long productId, final String email) {
        final Optional<Member> findMember = memberDao.findByEmail(email);
        if (findMember.isEmpty()) {
            throw new DataNotFoundException("해당 사용자가 존재하지 않습니다.");
        }
        final Member member = findMember.get();
        final int amountOfDeletedCart = cartDao.delete(member.getId(), productId);
        if (amountOfDeletedCart == 0) {
            throw new DataNotFoundException("해당 상품을 찾을 수 없습니다.");
        }
    }
}
