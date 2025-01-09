package shoppingmall.domainrdb.order;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressDomain {
    private final String zipCode;
    private final String detailAddress;

    @Builder
    private AddressDomain(String zipCode, String detailAddress) {
        this.zipCode = zipCode;
        this.detailAddress = detailAddress;
    }


}
