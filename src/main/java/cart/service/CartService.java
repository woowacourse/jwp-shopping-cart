package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dto.MemberAuthDto;
import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.product.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;

    public CartService(final CartDao cartDao, final ProductDao productDao, final MemberDao memberDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    public Long putInCart(final MemberAuthDto memberAuthDto, final Long productId) {
        final Optional<ProductEntity> product = productDao.findById(productId);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        final Optional<MemberEntity> member =
                memberDao.findByEmailAndPassword(memberAuthDto.getEmail(), memberAuthDto.getPassword());
        if (member.isEmpty()) {
            throw new IllegalArgumentException("등록되지 않은 회원입니다.");
        }
        return cartDao.save(new CartEntity(member.get().getId(), product.get().getId()));
    }
}
