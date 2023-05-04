package cart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final List<Product> products;

    public Cart() {
        this(Collections.emptyList());
    }

    public Cart(List<Product> products) {
        if (products.stream().distinct().count() != products.size()) {
            throw new IllegalArgumentException();
        }
        this.products = new ArrayList<>(products);
    }

    public void add(Product product) {
        products.add(product);
    }

    /*
        별도 식별자가 필요한 이유
            도메인 객체 인스턴스의 생명주기는 데이터의 생명주기와 일치하지 않을 수 있다.
            예를 들어, 카트 도메인 객체를 저장했다가 꺼낸다고 해보자.
            조회를 통해 다시 도메인 객체를 꺼내면, 해당 객체 및 하위 객체의 인스턴스는 바뀐다.
            이 경우 동일한 데이터지만 다른 상품 객체, 다른 카트 객체가 된다.

            따라서 객체를 동일성으로 식별하는 경우, 데이터를 올바르게 식별하려면 동일성 외 다른 방법이 필요하다.
            하지만, 별도로 정의된 동등성이 있다면 필요하지 않다.
            위 이유로 별도의 식별자를 사용하며, 이 식별자의 값은 데이터를 저장하는 측에서 관리한다.

        결론
            (대상이 DB가 아니더라도) 저장 및 조회의 대상이 되는 객체를 식별하기 위해서는 식별자가 따로 필요하다.
                (+ 이 식별자를 식별 외 다른 곳에 사용하면 오용이다)
                (+ 식별이 필요 없다면 Id도 필요없다)
            엔티티는 별도 식별자를 가지는 도메인 객체이고, 그 별도 식별자의 목적은 저장과 조회다.

        의문점
            - 그렇다면 상품의 Id를 식별에 어떻게 활용할 것인가?
                - Id를 상품 객체의 동등성 비교(equals)에 사용해도 되는가?
                    - 중복 허용/방지등의 요구사항이 변경되면, 어떻게 대응해야 하는가?
                        - 처음에는 장바구니에 중복 상품을 허용했다고 가정해보자.
                        - 같은 상품이 여러 번 담길 수 있다.
                        - 하지만 중복에 대해 예외를 던진다는 요구사항이 추가된다면?
                        - 저장한 장바구니를 다시 구성할 때 예외가 발생한다.
                        - 이 때 식별자가 id였다면, 이 요구사항을 반영하기 위해 객체가 아니라 기존 테이블의 데이터를 변경해야 한다.
                        - 이 때 식별자가 id가 아니라,
                    - Id로 객체를 식별하는 것은 객체의 식별을 외부 저장 기술에 의존하는 것이다.
                    - 객체의 동등성이 저장 방식에 따라 달라진다는 문제가 존재한다.
                    - DB 테이블의 제약 조건(ex, UNIQUE)이 객체지향 세계까지 영향을 미친다.
                        - 그럼 이것을 끊어낼 필요가 있나?
                        - 어떻게 끊어낼 수 있나?
                - 아직 저장되지 않은 객체끼리는 어떻게 식별할 것인가? (Id가 null이므로)

            - Id를 갖는 것 자체가 객체를 특정 저장 기술(DB)에 종속시키는 걸까? 아니면 DB가 관리하는 Id를 객체에 삽입할 때 종속시키는 걸까?

            - Id를 사용하는 기준은 '상태가 완전 동일해도 다른 데이터일 수 있는가?'일 수 있을 것 같다.
                -> 상품끼리 모든 상태가 동일해도, 다른 데이터일 수 있다. -> Id 사용
                -> 모든 상태가 같으면 같은 데이터로 봐야한다. -> Id 사용 X

            - 객체 자체를 저장하는 방법은 없나? (완료)
                -> 직렬화/역직렬화. 하지만 이 경우도 메모리 주소가 변하므로 통제 가능한 별도 식별자가 필요하다.
                    -> DB에 저장하고, 불러오는 것도 직렬화/역직렬화의 일종인 것 같다. (실제로 BeanPropertySqlParameterSource는 JavaBean 정의를 기반으로 직렬화한다)
                    다른 점은 '정보를 원하는 형태로 저장'하도록 하는 것이 목적이라는 점이다. (테이블은 성능, 명확성, 유연성 등을 위해 객체의 형태와 다를 수도 있다)
    */
    public void delete(Product product) {
        products.stream()
                .filter(it -> it.equals(product))
                .findFirst()
                .ifPresent(products::remove);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
