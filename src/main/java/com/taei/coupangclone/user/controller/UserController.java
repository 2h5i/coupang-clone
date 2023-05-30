package com.taei.coupangclone.user.controller;

import com.taei.coupangclone.common.security.CommonDetailsImpl;
import com.taei.coupangclone.user.dto.UpdateUserInfo;
import com.taei.coupangclone.user.dto.UpdateUserPassword;
import com.taei.coupangclone.user.dto.UserLogin;
import com.taei.coupangclone.user.dto.UserMyPage;
import com.taei.coupangclone.user.dto.UserSignUp;
import com.taei.coupangclone.user.service.UserService;
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
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid UserSignUp userSignUpDto) {

        userService.signup(userSignUpDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody @Valid UserLogin loginDto,
        HttpServletResponse response) {

        userService.login(loginDto, response);
    }

    @GetMapping("/my-page")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserMyPage selectMyPage(@AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        return userService.selectMyPage(commonDetails.getId());
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updatePassword(@RequestBody @Valid UpdateUserPassword updatePasswordDto,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        userService.updatePassword(updatePasswordDto, commonDetails.getId());
    }

    @PutMapping("/user-info")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateUserInfo(@RequestBody @Valid UpdateUserInfo updateUserInfoDto,
        @AuthenticationPrincipal CommonDetailsImpl commonDetails) {

        userService.updateUserInfo(updateUserInfoDto, commonDetails.getId());
    }

}
