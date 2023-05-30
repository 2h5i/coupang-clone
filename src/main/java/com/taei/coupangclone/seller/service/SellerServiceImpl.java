package com.taei.coupangclone.seller.service;

import com.taei.coupangclone.common.jwt.JwtUtil;
import com.taei.coupangclone.seller.dto.SellerMyPage;
import com.taei.coupangclone.seller.dto.SellerSignUp;
import com.taei.coupangclone.seller.dto.UpdateSellerInfo;
import com.taei.coupangclone.seller.dto.UpdateSellerPassword;
import com.taei.coupangclone.seller.entity.Seller;
import com.taei.coupangclone.seller.repository.SellerRepository;
import com.taei.coupangclone.user.dto.UserLogin;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void signup(SellerSignUp sellerSignUpDto) {
        Optional<Seller> foundSeller = sellerRepository.findByEmail(sellerSignUpDto.getEmail());

        if (foundSeller.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Seller seller = Seller.builder()
            .email(sellerSignUpDto.getEmail())
            .password(passwordEncoder.encode(sellerSignUpDto.getPassword()))
            .sellerName(sellerSignUpDto.getSellerName())
            .sellerNumber(sellerSignUpDto.getSellerNumber())
            .address(sellerSignUpDto.getAddress())
            .phone(sellerSignUpDto.getPhone())
            .build();

        sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public void login(UserLogin loginDto, HttpServletResponse response) {
        Seller seller = sellerRepository.findByEmail(loginDto.getEmail()).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 아이디입니다.")
        );

        if (!passwordEncoder.matches(loginDto.getPassword(), seller.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        addTokenToHeader(response, seller);
    }

    @Override
    @Transactional(readOnly = true)
    public SellerMyPage selectMyPage(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 판매자의 정보가 없습니다.")
        );

        return SellerMyPage.builder()
            .id(seller.getId())
            .email(seller.getEmail())
            .sellerName(seller.getSellerName())
            .sellerNumber(seller.getSellerNumber())
            .address(seller.getAddress())
            .phone(seller.getPhone())
            .build();
    }

    @Override
    @Transactional
    public void updatePassword(UpdateSellerPassword updateSellerPasswordDto, Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 판매자가 없습니다.")
        );

        if (!passwordEncoder.matches(updateSellerPasswordDto.getOldPassword(),
            seller.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        seller.updatePassword(passwordEncoder.encode(updateSellerPasswordDto.getNewPassword()));

        sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public void updateSellerInfo(UpdateSellerInfo updateSellerInfoDto, Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
            () -> new IllegalArgumentException("해당하는 판매자가 없습니다.")
        );

        seller.updateSellerInfo(updateSellerInfoDto.getSellerName(),
            updateSellerInfoDto.getAddress(),
            updateSellerInfoDto.getPhone());

        sellerRepository.save(seller);
    }

    private void addTokenToHeader(HttpServletResponse response, Seller seller) {
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER,
            jwtUtil.createAccessToken(seller.getEmail(), seller.getUserRole()));
        response.addHeader(JwtUtil.REFRESH_HEADER, jwtUtil.createRefreshToken(seller.getEmail()));
    }
}
