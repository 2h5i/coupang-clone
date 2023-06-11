package com.taei.coupangclone.order.service;

import com.taei.coupangclone.common.core.PageWrapper;
import com.taei.coupangclone.delivery.entity.Delivery;
import com.taei.coupangclone.delivery.entity.DeliveryStatus;
import com.taei.coupangclone.delivery.repository.DeliveryRepository;
import com.taei.coupangclone.order.dto.CreateOrder;
import com.taei.coupangclone.order.dto.ResponseUserOrders;
import com.taei.coupangclone.order.entity.Order;
import com.taei.coupangclone.order.repository.OrderRepository;
import com.taei.coupangclone.orderitem.dto.CreateOrderItem;
import com.taei.coupangclone.orderitem.entity.OrderItem;
import com.taei.coupangclone.payment.entity.Payment;
import com.taei.coupangclone.payment.repository.PaymentRepository;
import com.taei.coupangclone.product.entity.Product;
import com.taei.coupangclone.product.repository.ProductRepository;
import com.taei.coupangclone.user.entity.User;
import com.taei.coupangclone.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void createOrder(CreateOrder createOrder, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("해당 회원이 존재하지 않습니다.")
        );

        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderItem orderItem : createOrder.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 존재하지 않습니다.")
            );

            if (product.isSoldOut()) {
                throw new IllegalArgumentException(product.getName() + "상품이 품절되었습니다.");
            }

            OrderItem createdOrderItem = OrderItem.createOrderItem(product, orderItem.getCount(),
                orderItem.getOrderPrice());

            orderItems.add(createdOrderItem);

            Delivery delivery = Delivery.builder()
                .address(createOrder.getAddress())
                .zipcode(createOrder.getZipcode())
                .phone(createOrder.getPhone())
                .receiver(createOrder.getReceiver())
                .statuts(DeliveryStatus.NONE)
                .orderItem(createdOrderItem)
                .build();

            deliveryRepository.save(delivery);
        }

        Order order = Order.createOrder(user, orderItems);
        Payment payment = Payment.builder()
            .totalPrice(order.getTotalPrice())
            .payMethod(createOrder.getPaymentMethod())
            .payCompany(createOrder.getPayCompany())
            .build();

        orderRepository.save(order);
        paymentRepository.save(payment);
    }

    @Override
    public PageWrapper<ResponseUserOrders> selectUserOrders(Long userId, Pageable pageable) {

        return PageWrapper.of(orderRepository.selectUserOrders(userId, pageable));
    }
}
