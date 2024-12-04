package shppingmall.commerce.payment.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TossPaymentMethod {

    카드, 가상계좌, 간편결제, 휴대폰, 계좌이체, 문화상품권, 도서문화상품권, 게임문화상품권;
//    CARD("카드"), VIRTUAL_ACCOUNT("가상계좌"), EASY_PAYMNET("간편결제"),MOBILE_PHONE("휴대폰"), BANK_TRANSFER("계좌이체")
//    , CULTURAL_GIFT_CERTIFICATE("문화상품권"), BOOK_AND_CULTURAL_GIFT_CERTIFICATE("도서문화상품권"), GAME_CULTURAL_GIFT_CERTIFICATE("게임문화상품권");
//
//    private final String method;
}
