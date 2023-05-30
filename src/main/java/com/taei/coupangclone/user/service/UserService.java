package com.taei.coupangclone.user.service;

import com.taei.coupangclone.user.dto.UpdateUserInfo;
import com.taei.coupangclone.user.dto.UpdateUserPassword;
import com.taei.coupangclone.user.dto.UserLogin;
import com.taei.coupangclone.user.dto.UserMyPage;
import com.taei.coupangclone.user.dto.UserSignUp;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    void signup(UserSignUp userSignUpDto);

    void login(UserLogin loginDto, HttpServletResponse response);

    UserMyPage selectMyPage(Long userId);

    void updatePassword(UpdateUserPassword updatePasswordDto, Long userId);

    void updateUserInfo(UpdateUserInfo updateUserInfoDto, Long userId);
}
