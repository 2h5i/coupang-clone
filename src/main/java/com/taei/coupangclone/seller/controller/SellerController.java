package com.taei.coupangclone.seller.controller;

import com.taei.coupangclone.common.security.CommonDetailsImpl;
import com.taei.coupangclone.seller.dto.SellerMyPage;
import com.taei.coupangclone.seller.dto.SellerSignUp;
import com.taei.coupangclone.seller.dto.UpdateSellerInfo;
import com.taei.coupangclone.seller.dto.UpdateSellerPassword;
import com.taei.coupangclone.seller.service.SellerService;
import com.taei.coupangclone.user.dto.UserLogin;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid SellerSignUp sellerSignUpDto) {

        sellerService.signup(sellerSignUpDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody @Valid UserLogin loginDto, HttpServletResponse response) {

        sellerService.login(loginDto, response);
    }

    @GetMapping("/my-page")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public SellerMyPage selectMyPage(@AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        return sellerService.selectMyPage(commonDetails.getId());
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public void updatePassword(@RequestBody @Valid UpdateSellerPassword updateSellerPasswordDto,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        sellerService.updatePassword(updateSellerPasswordDto, commonDetails.getId());
    }

    @PutMapping("/seller-info")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public void updateSellerInfo(@RequestBody @Valid UpdateSellerInfo updateSellerInfoDto,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        sellerService.updateSellerInfo(updateSellerInfoDto, commonDetails.getId());
    }
}
