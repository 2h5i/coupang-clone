package com.taei.coupangclone.order.dto;

import com.taei.coupangclone.orderitem.dto.CreateOrderItem;
import com.taei.coupangclone.payment.entity.PaymentMethod;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateOrder {

    @NotNull
    private List<CreateOrderItem> orderItems;

    @NotBlank
    @Size(max = 100, message = "주소지는 최대 100자 입니다.")
    private String address;

    @NotBlank
    @Size(max = 10, message = "우편번호는 최대 10자입니다.")
    private String zipcode;

    @NotBlank
    @Size(max = 15, message = "전화번호는 최대 15자 입니다.")
    private String phone;

    @NotBlank
    @Size(max = 15, message = "수령인 이름은 최대 15자 입니다.")
    private String receiver;

    @NotBlank
    private PaymentMethod paymentMethod;

    @NotBlank
    @Size(max = 20, message = "결제사 이름은 최대 20자 입니다.")
    private String payCompany;
}

