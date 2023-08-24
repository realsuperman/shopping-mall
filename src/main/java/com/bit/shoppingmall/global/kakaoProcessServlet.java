package com.bit.shoppingmall.global;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class kakaoProcessServlet extends HttpServlet {
    private final String fileName = "common/pay";
    private final KakaoPayProcess kakaoPayProcess;

    public kakaoProcessServlet(KakaoPayProcess kakaoPayProcess) {
        this.kakaoPayProcess = kakaoPayProcess;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label()+fileName+LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
