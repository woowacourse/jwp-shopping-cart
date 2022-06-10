package woowacourse.member.dto;

public class OrderSaveServiceRequest {

    private Long memberId;

    public OrderSaveServiceRequest(final Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
