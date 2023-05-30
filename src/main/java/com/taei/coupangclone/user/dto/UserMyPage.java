package com.taei.coupangclone.user.dto;

import com.taei.coupangclone.address.dto.ResponseAddress;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMyPage {

    private Long id;
    private String email;
    private String userName;
    private String phone;
    private List<ResponseAddress> addresses;
}
