package com.bit.shoppingmall.validation;

import com.bit.shoppingmall.global.Validation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemValidation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        Validation validation = Validation.validation.getValidation();

        validation.validateString("제품명", request.getParameter("itemName"), 127);
        validation.validateLong("가격", request.getParameter("itemPrice"), false, 0, 0);
        validation.validateLong("제품 갯수", request.getParameter("itemQuantity"), true, 1, 100);
        validation.validateString("제품 설명", request.getParameter("itemDesc"), 512);
        StringBuilder image = new StringBuilder();
        for (int i = 1; i <= 6; i++) {
            String name = "image" + i + "Name";
            String field = "";
            if (i == 1) {
                field = "썸네일";
            } else {
                field = "image" + (i - 1);
            }

            String imagePath = request.getParameter(name);
            validation.validateString(field, imagePath, 512);
            image.append(imagePath);

            if (i < 6) image.append(";");
        }
        validation.validateNull("소분류", request.getParameter("category"));


    }
}
