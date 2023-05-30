package com.taei.coupangclone.common.jwt;

import static com.taei.coupangclone.common.jwt.JwtUtil.AUTHORIZATION_KEY;

import com.taei.coupangclone.common.entity.UserRole;
import com.taei.coupangclone.common.security.UserDetailsServiceType;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        try {
            String token = jwtUtil.resolveAccessToken((HttpServletRequest) request);

            if (token != null && jwtUtil.validateTokenExceptExpiration(token)) {
                Claims info = jwtUtil.getUserInfoFromToken(token);

                String userRoleStr = (String) info.get(AUTHORIZATION_KEY);
                UserRole userRole = UserRole.valueOf(userRoleStr);
                Authentication authentication = null;

                switch (userRole) {
                    case USER:
                        authentication = jwtUtil.createAuthentication(info.getSubject(),
                            UserDetailsServiceType.USER);
                        break;
                    case SELLER:
                        authentication = jwtUtil.createAuthentication(info.getSubject(),
                            UserDetailsServiceType.SELLER);
                        break;
                }

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (UsernameNotFoundException exception) {
            HttpServletResponse realResponse = (HttpServletResponse) response;
            log.error("e = {}", exception.getMessage());
            realResponse.setContentType("application/json;charset=UTF-8");
            realResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            JSONObject responseJson = new JSONObject();
            responseJson.put("message", exception.getMessage());
            responseJson.put("code", 400);

            realResponse.getWriter().print(responseJson);
        }
    }
}
