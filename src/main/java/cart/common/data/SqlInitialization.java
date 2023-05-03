package cart.common.data;

import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.repository.member.MemberRepository;
import cart.repository.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SqlInitialization implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public SqlInitialization(final MemberRepository memberRepository, final ProductRepository productRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(final String... args) throws Exception {
        memberRepository.save(new Member("헤나", "hyena@hyena.com", "hyena"));
        memberRepository.save(new Member("토니", "tony@tony.com", "tony"));

        productRepository.save(new Product("치킨", 20000, "https://pelicana.co.kr/resources/images/menu/original_menu10_210705.png"));
    }
}
