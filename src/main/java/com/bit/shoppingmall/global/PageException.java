package com.bit.shoppingmall.global;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageException extends HttpServlet {
    private final String fileName = "404";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(LabelFormat.PREFIX.label()+"/common/"+fileName+LabelFormat.SUFFIX.label());
        rd.forward(request, response);
    }
}
