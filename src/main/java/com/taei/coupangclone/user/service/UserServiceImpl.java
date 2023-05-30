package com.taei.coupangclone.user.service;

import com.taei.coupangclone.address.dto.ResponseAddress;
import com.taei.coupangclone.address.entity.Address;
import com.taei.coupangclone.address.repository.AddressRepository;
import com.taei.coupangclone.common.jwt.JwtUtil;
import com.taei.coupangclone.user.dto.UpdateUserInfo;
import com.taei.coupangclone.user.dto.UpdateUserPassword;
import com.taei.coupangclone.user.dto.UserLogin;
import com.taei.coupangclone.user.dto.UserMyPage;
import com.taei.coupangclone.user.dto.UserSignUp;
import com.taei.coupangclone.user.entity.User;
import com.taei.coupangclone.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void signup(UserSignUp userSignUpDto) {
        Optional<User> foundUser = userRepository.findByEmail(userSignUpDto.getEmail());

        if (foundUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
            .email(userSignUpDto.getEmail())
            .userName(userSignUpDto.getUserName())
            .password(passwordEncoder.encode(userSignUpDto.getPassword()))
            .phone(userSignUpDto.getPhone())
            .build();

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void login(UserLogin loginDto, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 아이디입니다.")
        );

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        addTokenToHeader(response, user);
    }

    @Override
    @Transactional(readOnly = true)
    // TODO : 개선하기
    public UserMyPage selectMyPage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 회원 정보가 없습니다.")
        );

        List<Address> addresses = addressRepository.findByUserId(user.getId());

        return UserMyPage.builder()
            .id(user.getId())
            .email(user.getEmail())
            .userName(user.getUserName())
            .phone(user.getPhone())
            .addresses(ResponseAddress.of(addresses))
            .build();
    }

    @Override
    @Transactional
    public void updatePassword(UpdateUserPassword updatePasswordDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 회원 정보가 없습니다.")
        );

        if (!passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserInfo(UpdateUserInfo updateUserInfoDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 회원 정보가 없습니다.")
        );

        user.updateUserInfo(updateUserInfoDto.getUserName(), updateUserInfoDto.getPhone());

        userRepository.save(user);
    }

    private void addTokenToHeader(HttpServletResponse response, User user) {
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,
            jwtUtil.createAccessToken(user.getEmail(), user.getUserRole()));
        response.addHeader(JwtUtil.REFRESH_HEADER, jwtUtil.createRefreshToken(user.getEmail()));
    }
}
