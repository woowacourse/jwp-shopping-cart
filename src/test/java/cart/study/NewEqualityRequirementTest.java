package cart.study;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cart.study.materials.Box;
import cart.study.materials.FieldCompareProduct;
import cart.study.materials.IdCompareProduct;
import cart.study.materials.NoDuplicateBox;

@DisplayName("동등성에 관련한 비즈니스 룰이 바뀌었을 때, 어떻게 반영하는가?")
// 객체지향 세계에서 반영해도 괜찮은가? 이 행위가 데이터 자체에 영향을 미치지는 않는가?
// EQ & HC는 무엇을 기준으로 재정의해야 하는가?

// 상황 1. 컨테이너에 중복 방지가 추가된다.
//      컨테이너에 중복이 생기면 예외를 던지도록 요구사항이 변경됐다.
// 상황 2. 객체의 동등성 정의가 바뀐다.
//      객체의 이름이 같아도, 가격이 다르면 다른 객체다 -> 이름만 같으면 같은 객체다.
public class NewEqualityRequirementTest {

    @DisplayName("요구사항에 중복 방지가 추가된다")
    @Test
    void test_container_changes() {
        // DB에 다음과 같은 데이터가 있다고 가정하자
        final var 새우깡_500원_가격은_비교안함 = new FieldCompareProduct("새우깡", 500L, false);
        final var 새우깡_1000원_가격은_비교안함 = new FieldCompareProduct("새우깡", 1000L, false);

        // 두 상품은 논리적으로 같다.
        assertThat(새우깡_500원_가격은_비교안함).isEqualTo(새우깡_1000원_가격은_비교안함);

        // 처음에는 문제가 없다.
        assertThatNoException().isThrownBy(() -> new Box(새우깡_500원_가격은_비교안함, 새우깡_1000원_가격은_비교안함));

        // 하지만 요구사항을 반영한 컨테이너 객체를 사용하면, 기존 데이터를 사용할 때 오류가 발생한다.
        assertThatIllegalArgumentException().isThrownBy(
                () -> new NoDuplicateBox(새우깡_500원_가격은_비교안함, 새우깡_1000원_가격은_비교안함));

        // 결론: 요구사항은 객체 세계 안에서 잘 반영됐다! 하지만 기존 데이터들을 수정해줘야 한다. (피할 수 없다)
        // 이걸 DB 단에서 적용한다면?
        // INDEX 및 UNIQUE를 걸어주면 된다. 하지만 마찬가지로 기존 데이터들을 수정해줘야 한다.
        // 따라서 객체지향 세계에서 비즈니스 룰을 적용한 것이 DB의 수정을 불러온 것은 아니다.
        // 오히려 도메인 객체에 적용하므로 이해하기 쉽고 유지보수가 쉬운 장점이 있다.
    }

    @DisplayName("객체의 동등성 정의가 바뀐다. - Id 없이 필드로 비교하는 객체의 경우")
    @Test
    void test_equalityRuleChanges_compare_fields() {
        // DB에 다음과 같은 데이터가 있다고 가정하자.
        final var 새우깡_500원_이름_가격_다비교함 = new FieldCompareProduct("새우깡", 500L, true);
        final var 새우깡_1000원_이름_가격_다비교함 = new FieldCompareProduct("새우깡", 1000L, true);

        // 두 데이터는 논리적으로 다르다.
        assertThat(새우깡_500원_이름_가격_다비교함).isNotEqualTo(새우깡_1000원_이름_가격_다비교함);

        // 그래서 박스 생성에도 문제가 없다.
        assertThatNoException().isThrownBy(() -> new NoDuplicateBox(새우깡_500원_이름_가격_다비교함, 새우깡_1000원_이름_가격_다비교함));

        // 이름만 같으면 같은 객체로 보기로 비즈니스 룰이 변경됐다.
        final var 새우깡_500원_가격은_비교안함 = new FieldCompareProduct("새우깡", 500L, false);
        final var 새우깡_1000원_가격은_비교안함 = new FieldCompareProduct("새우깡", 1000L, false);

        // 이제 논리적으로 같아졌다.
        assertThat(새우깡_500원_가격은_비교안함).isEqualTo(새우깡_1000원_가격은_비교안함);

        // 따라서 박스를 생성할 때 문제가 된다.
        assertThatIllegalArgumentException().isThrownBy(
                () -> new NoDuplicateBox(새우깡_500원_가격은_비교안함, 새우깡_1000원_가격은_비교안함));

        // 데이터가 동일하지만, 더 이상 인스턴스를 만들 수 없는 문제가 생겼다.
        // 이걸 DB단에서 적용하려면? INDEX 및 UNIQUE를 걸어주면 된다.
        // 하지만 역시 기존 데이터들을 수정해주어야 한다.
        // 따라서 객체지향 세계에서의 비즈니스 룰의 적용이 DB 수정을 불러온 것은 아니다.
    }

    @DisplayName("객체의 동등성 정의가 바뀐다 - Id로만 비교하는 경우")
    @Test
    void test_equalityDefinitionChanges_when_equality_depends_on_id() {
        // 객체에서는 Id만 비교하고, 요구사항 반영을 위해 DB 테이블에 이름과 가격으로 INDEX 및 UNIQUE가 적용되어 있다.
        final var 새우깡_500원_id만비교 = new IdCompareProduct(1, "새우깡", 500L);
        final var 새우깡_1000원_id만비교 = new IdCompareProduct(2, "새우깡", 1000L);

        // 둘은 다른 데이터이므로 다르다.
        assertThat(새우깡_500원_id만비교).isNotEqualTo(새우깡_1000원_id만비교);
        assertThatNoException().isThrownBy(() -> new NoDuplicateBox(새우깡_500원_id만비교, 새우깡_1000원_id만비교));

        // 이름만 같으면 같은 객체로 보기로 비즈니스 룰이 변경됐다.
        // DB INDEX & UNIQUE를 수정해야 하고, 기존 데이터의 수정은 마찬가지로 피할 수 없다. (비즈니스 룰 자체가 변경된 것이므로)

        // 이제 문제가 생겼다.
        // 두 새우깡은 이제 비즈니스적 관점에서 같은 데이터지만, 객체 세상에서는 여전히 다른 객체로 취급된다.
        assertThat(새우깡_500원_id만비교).isNotEqualTo(새우깡_1000원_id만비교);

        // 이 객체로 비즈니스 로직을 수행해야 하는 것은 Java 어플리케이션인데, 제약 조건은 DB에 걸려있다.
        // 따라서 영속화해서 DB Consistency 관련 예외를 볼 때까지 문제를 알 수 없으며, 제약이 없는 상태로 로직을 수행할 것이다.
        assertThatNoException().isThrownBy(() -> new NoDuplicateBox(새우깡_500원_id만비교, 새우깡_1000원_id만비교));

        // 이 제약조건을 위해 객체가 변할 때 마다 DB를 들락날락 해서 구현할 수는 있지만, 자원의 낭비가 심해진다.
        // 또한 변화가 생길 때 마다 영속화를 통해 예외를 확인해야 하므로, 복잡한 로직 구현은 꿈도 꿀 수 없을것이다.

        // 결론: 제약 조건은 객체 세상에, 비즈니스 로직과 함께 존재해야 한다.
    }

    @DisplayName("객체의 동등성 정의가 바뀐다 - Id, 논리적 동등성 둘 다 사용한 경우")
    @Test
    void test_equalityDefinitionChanges_when_equality_has_dependency_on_id_and_fields() {
        // 그렇다면 Id와 논리적 동등성을 동시에 사용해야 하는건가?
        // 얕게 고민해보면, Id가 없을 때는 필드간 비교, Id가 있을 때는 Id + 필드 간 비교를 해야할 것 같다.
        // 결론은 README를 참고하자.

        // final var 새우깡_500원_Id = new IdAndFieldsCompareProduct(1, "새우깡", 1800L, true);
        // final var 새우깡_이미지B = new IdAndFieldsCompareProduct(2, "새우깡", 1800L, true);
        //
        // assertThat(새우깡_500원_Id).isNotEqualTo(새우깡_이미지B);
        //
        // assertThatNoException().isThrownBy(() -> new NoDuplicateBox(새우깡_500원_Id, 새우깡_이미지B));
        //
        // final var 새우깡_이미지A_이미지_제외 = new IdAndFieldsCompareProduct(1, "새우깡", 1800L, false);
        // final var 새우깡_이미지B_이미지_제외 = new IdAndFieldsCompareProduct(2, "새우깡", 1800L, false);
        //
        // assertThat(새우깡_이미지A_이미지_제외).isNotEqualTo(새우깡_이미지B_이미지_제외);
        //
        // assertThatNoException().isThrownBy(() -> new NoDuplicateBox(새우깡_이미지A_이미지_제외, 새우깡_이미지B_이미지_제외));

    }

    /*
        엔티티의 EQ & HC 오버라이딩 기준
        Id로 비교해야 하는 경우
             DB가 없다고 가정했을 때, 상품을 동일성(메모리 주소)으로 비교하도록 설계했다면?
             -> 동일한 필드를 가지는 도메인 객체를 허용하고, 동일한 인스턴스를 추가할 수 없게 막는 것이 비즈니스적 결정사항이다.
             따라서 영속화 맥락을 고려했을 땐 Id로 비교를 이어나가야 한다.
             -> Id의 목적은 '데이터 자체'의 식별이기 때문이다. (ex) 테이블이라면 한 행을 식별하는 것이 목적이다)
                (인스턴스의 생명 주기와 데이터의 생명 주기는 다르다. 영속화 후 인스턴스화 했을 때는 메모리 주소가 달라진다. 따라서 '데이터 자체'를 식별하기 위해서는 Id를 사용해야 한다.)

        Id를 빼고 비교해야 하는 경우
            비즈니스 로직 안에서 결정해버리면 잘못된 데이터가 DB까지 내려갈 일이 없다. (정말 불안하다면, DB에다가도 동일한 제약조건을 추가하면 오히려 가장 안전한 것 아닌가?)
            이 경우에도 Id로 식별하거나 Id를 포함해 식별한다면, '도메인의 저장 방식'을 도메인이 신경쓰는 꼴이므로,
            비즈니스 룰이 있는 경우에 한해서 Id를 도메인의 식별에 활용하는 것은 책임을 훨씬 벗어난 행위다.

        이렇게 직접 판단을 끝내고 찾아보니 이런 Stackoverflow 답변이 있다.
        누군가와 의견을 나눈 셈 쳐야겠다.
        미리 찾아봤으면 시간을 아꼈겠지만, 과연 이렇게 직접 판단해봤을까 싶긴 하다.
        https://stackoverflow.com/a/4388453
    */
}
