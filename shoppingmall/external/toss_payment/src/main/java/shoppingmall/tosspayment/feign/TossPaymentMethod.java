package shoppingmall.tosspayment.feign;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TossPaymentMethod {

    카드, 가상계좌, 간편결제, 휴대폰, 계좌이체, 문화상품권, 도서문화상품권, 게임문화상품권;

}
