package cart.business;

import cart.business.domain.Cart;
import cart.business.domain.Carts;
import cart.business.domain.Member;
import cart.business.domain.Product;
import cart.business.domain.Products;
import cart.persistence.CartProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartProductService {

    private CartProductDao cartProductDao;
    private ProductService productService;
    private Carts carts;
    private Products products;

    public CartProductService(CartProductDao cartProductDao, ProductService productService) {
        initSetting();
        this.cartProductDao = cartProductDao;
        this.products = productService.getProducts();
    }

    //data.sql에 INSERT...해서 데이터를 미리 넣어주지 않았다면 Cart, Member...등을 Create()할 때 아래와 같은 코드들이 수행될 수 있을 것 같습니다!
    //하지만 지금은 data.sql INSERT...문을 미리 작성해두고 domain을 후에 추가했기에 이렇게 넣어주었습니다!
    private void initSetting() {
        Member judy = new Member("coding_judith@gmail.com", "judy123");
        Cart cartJudy = new Cart(judy.getId(), new Products(new ArrayList<>()));

        Member teo = new Member("coding_teo@gmail.com", "teo123");
        Cart cartTeo = new Cart(teo.getId(), new Products(new ArrayList<>()));

        carts = new Carts(new ArrayList<>(List.of(cartJudy, cartTeo)));
    }

    /*
     * 객체지향적으로 cart라는 객체가 product를 담는다
     * 담는 행위는 cart 스스로가 할 수 있다
     * cart는 product담을 method가 필요하다
     */
    @Transactional
    public Integer create(Integer productId, Integer memberId) {
        Cart cart = carts.findCartByMemberId(memberId);
        Product product = products.findProductById(productId);
        cart.addProduct(product);

        return cartProductDao.insert(cart, product);
    }

    @Transactional
    public Integer delete(Integer memberId, Integer id) {
        Product product = products.findProductById(id);
        Cart cart = carts.findCartByMemberId(memberId);
        cart.removeProduct(product);

        return cartProductDao.remove(product);
    }
}
