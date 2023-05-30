package com.taei.coupangclone.seller.service;

import com.taei.coupangclone.seller.dto.SellerMyPage;
import com.taei.coupangclone.seller.dto.SellerSignUp;
import com.taei.coupangclone.seller.dto.UpdateSellerInfo;
import com.taei.coupangclone.seller.dto.UpdateSellerPassword;
import com.taei.coupangclone.user.dto.UserLogin;
import javax.servlet.http.HttpServletResponse;

public interface SellerService {

    public void signup(SellerSignUp sellerSignUpDto);

    void login(UserLogin loginDto, HttpServletResponse response);

    SellerMyPage selectMyPage(Long sellerId);

    void updatePassword(UpdateSellerPassword updateSellerPasswordDto, Long sellerId);

    void updateSellerInfo(UpdateSellerInfo updateSellerInfoDto, Long sellerId);
}
