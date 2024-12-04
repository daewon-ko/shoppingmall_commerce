package shppingmall.commerce.payment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TossPaymentStatus {
    READY("결제 준비"), IN_PROGRESS("결제 진행 중"), WAITING_FOR_DEPOSIT("가상계좌 입금 전"), DONE("결제 승인"),
    CANCELED("결제 취소"), PARTIAL_CANCELED("결제 부분 취소"), ABORTED("결제 승인 실패"), EXPIRED("유효시간 만료");


    private final String message;
}
