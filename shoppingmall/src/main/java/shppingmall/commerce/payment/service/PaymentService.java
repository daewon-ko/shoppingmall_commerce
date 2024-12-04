package shppingmall.commerce.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shppingmall.commerce.global.exception.ApiException;
import shppingmall.commerce.global.exception.domain.OrderErrorCode;
import shppingmall.commerce.order.entity.Order;
import shppingmall.commerce.order.repository.OrderRepository;
import shppingmall.commerce.payment.dto.PaymentConfirmRequest;
import shppingmall.commerce.payment.dto.PaymentConfirmResponse;
import shppingmall.commerce.payment.entity.TossPayment;
import shppingmall.commerce.payment.feign.PaymentClient;
import shppingmall.commerce.payment.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    //TODO : 결제에서의 트랜잭션 처리는 어떻게?
    // 외부 API를 연동하는데 트랜잭션 처리를 어떻게 해주는게 적절할까?
    @Transactional
    public PaymentConfirmResponse confirm(final PaymentConfirmRequest request, final Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new ApiException(OrderErrorCode.NOT_EXIST_ORDER));

        final PaymentConfirmResponse paymentConfirmResponse = paymentClient.confirmPayment(request);
        TossPayment tossPayment = TossPayment.of(paymentConfirmResponse, findOrder);
        paymentRepository.save(tossPayment);

        return paymentConfirmResponse;

    }


}
