package com.study.Boardify.config.security;

import com.study.Boardify.domain.User;
import com.study.Boardify.domain.UserRole;
import com.study.Boardify.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.io.PrintWriter;

public class MyLogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('로그아웃 되었습니다.'); location.href='/';</script>");
        out.flush();
    }
}
