package com.study.Boardify.config.security;

import com.study.Boardify.domain.User;
import com.study.Boardify.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    private final UserRepository userRepository;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = null;
        if (auth != null) {
            loginUser = userRepository.findByLoginId(auth.getName()).get();
        }
        String requestURI = request.getRequestURI();

        if(requestURI.contains("/users/login") ||requestURI.contains("/users/join")){
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println("<script>alert('이미 로그인 되어있습니다!'); location.href='/';</script>");
            pw.flush();
        }
        else if (requestURI.contains("gold")){
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println("<script>alert('골드 등급 이상의 유저만 접근 가능합니다!'); location.href='/';</script>");
            pw.flush();
        }
        else if (requestURI.contains("free/write")){
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println("<script>alert('가입인사 작성 후 작성 가능합니다!'); location.href='/boards/greeting';</script>");
            pw.flush();
        }
        else if (requestURI.contains("greeting")){
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println("<script>alert('가입인사는 한 번만 작성 가능합니다!'); location.href='/boards/greeting';</script>");
            pw.flush();
        }
        else if (requestURI.contains("admin")){
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println("<script>alert('관리자만 접속 가능합니다!'); location.href='/';</script>");
            pw.flush();
        }
    }
}
