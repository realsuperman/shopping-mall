package com.bit.shoppingmall.global;

import com.bit.shoppingmall.domain.Consumer;
import com.bit.shoppingmall.exception.AccessDeniedException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class BadRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String uri = ((HttpServletRequest) request).getRequestURI();

        String path = uri.substring(uri.indexOf("/") + 1);
        String[] urlParts = path.split("/");
        AccessDeniedException accessDeniedException = new AccessDeniedException("제한된 페이지 입니다.");

        if (!urlParts[0].equals("static")) {
            RequestDispatcher rd;
            if (path.equals("")) {
                rd = request.getRequestDispatcher("/home.bit");
                if (isAdmin(request)) {
                    rd = request.getRequestDispatcher("/admin.bit");
                }
            } else {
                if (!isLogin(request)) {
                    if (!path.startsWith("user")) {
                        // 여기서 바로 로그인 페이지로 리다이렉트 할까?
                        throw accessDeniedException;
                    }
                } else {
                    /** ??
                     * 관리자 페이지 url 정리하기
                     * 같은 url에서 doGet, doPost 에 따라 관리자, 일반 유저 페이지 둘 다 있나?? */
                    boolean isAdminPath = path.startsWith("admin") || path.startsWith("item");
                    if (path.startsWith("user") || (!isAdmin(request) && isAdminPath) || (isAdmin(request) && !isAdminPath)) {
                        throw accessDeniedException;
                    }
                }
                rd = request.getRequestDispatcher("/" + path + ".bit");
            }
            rd.forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isLogin(ServletRequest request) {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        return session != null && session.getAttribute("login_user") != null;
    }

    private boolean isAdmin(ServletRequest request) {

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        Object loginUser = session.getAttribute("login_user");

        if (loginUser instanceof Consumer) {
            Consumer loginConsumer = (Consumer) loginUser;
            return loginConsumer.getIsAdmin() == 1;
        }
        return false;
    }
}