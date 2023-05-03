package cart.service;

import cart.entity.item.CartItem;
import cart.entity.item.CartItemDao;
import cart.entity.member.Member;
import cart.entity.member.MemberDao;
import cart.entity.product.Product;
import cart.entity.product.ProductDao;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    public static final String BASIC_TYPE_PREFIX = "Basic";
    private static final String DELIMITER = ":";

    private final CartItemDao cartItemDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final MemberDao memberDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public CartItem addItem(final String authorization, final Long productId) {
        final Member authenticatedMember = validateAuth(authorization);
        if (authenticatedMember == null) {
            return null;
        }
        final CartItem cartItem = new CartItem(authenticatedMember.getId(), productId);
        return cartItemDao.save(cartItem);
    }

    public List<Product> findCartItems(final String authorization) {
        final Member authenticatedMember = validateAuth(authorization);
        final List<CartItem> cartItems = cartItemDao.findByMemberId(authenticatedMember.getId());
        return cartItems.stream()
                .map(CartItem::getProductId)
                .map(productDao::findById)
                .collect(Collectors.toList());
    }

    private Member validateAuth(final String authorization) {
        final boolean isBasicAuthentication = authorization.toLowerCase().startsWith(BASIC_TYPE_PREFIX.toLowerCase());

        if (!isBasicAuthentication) {
            System.out.println("is not basic auth");
            return null;
        }

        String authHeaderValue = authorization.substring(BASIC_TYPE_PREFIX.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        return memberDao.findByEmailAndPassword(email, password).get();
    }

    public void deleteItem(final String authorization, final long productId) {
        final Member member = validateAuth(authorization);
        cartItemDao.delete(member.getId(), productId);
    }
}
