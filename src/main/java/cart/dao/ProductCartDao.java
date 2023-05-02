package cart.dao;

import cart.entity.Member;
import cart.entity.Product;
import cart.entity.ProductCart;
import java.util.List;
import java.util.Optional;

public interface ProductCartDao {

    ProductCart save(ProductCart productCart);

    Optional<ProductCart> findById(Long id);

    List<ProductCart> findAllByMember(Member member);

    void deleteByMemberAndProduct(Member member, Product product);
}
