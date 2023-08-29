package com.bit.shoppingmall.global;

import com.bit.shoppingmall.controller.*;
import com.bit.shoppingmall.dao.*;
import com.bit.shoppingmall.exception.MessageException;
import com.bit.shoppingmall.service.*;
import com.bit.shoppingmall.validation.ItemValidation;
import com.bit.shoppingmall.validation.UserValidation;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@WebServlet("*.bit")
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Map<String, HttpServlet> urlMapper = new HashMap<>();
    private final Logger log = Logger.getLogger("work");

    public DispatcherServlet() {
        // TODO 중복되는거 위에다 선언 후 하기
        super();
        urlMapper.put("/categories", new CategoryController(new CategoryService(new CategoryDao())));
        urlMapper.put("/status", new StatusController(new StatusService(new StatusDao())));
        urlMapper.put("/item-validation", new ItemValidation());
        urlMapper.put("/user-validation/sign-up", new UserValidation());
        urlMapper.put("/user-validation/my-page-info/pass", new UserValidation());
        urlMapper.put("/not-found", new PageException());

        urlMapper.put("/user", new UserController(new UserService(new ConsumerDao(), new OrderDetailDao(), new MembershipDao())));
        urlMapper.put("/user/login", new UserController(new UserService(new ConsumerDao(), new OrderDetailDao(), new MembershipDao())));
        urlMapper.put("/user/sign-up", new UserController(new UserService(new ConsumerDao(), new OrderDetailDao(), new MembershipDao())));

        urlMapper.put("/logout", new UserController(new UserService(new ConsumerDao(), new OrderDetailDao(), new MembershipDao())));
        urlMapper.put("/my-page", new UserInfoController(new UserService(new ConsumerDao(), new OrderDetailDao(), new MembershipDao())));
        urlMapper.put("/my-page-info", new UserInfoController(new UserService(new ConsumerDao(), new OrderDetailDao(), new MembershipDao())));
        urlMapper.put("/my-page-info/pass", new UserInfoController(new UserService(new ConsumerDao(), new OrderDetailDao(), new MembershipDao())));
        urlMapper.put("/cart", new CartController(new CartService(new CartDao()), new ItemService(new ItemDao(), new CargoDao())));
        urlMapper.put("/itemJson", new ItemJsonController(new ItemService(new ItemDao(), new CargoDao())));
        urlMapper.put("/home", new HomeController(new ItemService(new ItemDao(), new CargoDao())));
        urlMapper.put("/pageNotFound", new PageException());
        urlMapper.put("/orderSetList", new OrderSetController(new OrderSetService(new OrderSetDao())));
        urlMapper.put("/orderDetail", new OrderDetailController(new OrderDetailService(new OrderDetailDao())));
        urlMapper.put("/order", new OrderController());
        urlMapper.put("/payment", new PaymentController(new OrderService(new OrderDetailDao(), new OrderSetDao(), new CartDao(), new CargoDao())));
        urlMapper.put("/cart-ajax", new CartRestController(new CartService(new CartDao()), new ItemService(new ItemDao(), new CargoDao())));
        urlMapper.put("/itemDetail", new ItemDetailController(new ItemService(new ItemDao(), new CargoDao()), new CategoryService(new CategoryDao()), new CargoDao()));
      
        urlMapper.put("/cart-ajax/checked", new CartRestController(new CartService(new CartDao())));
        urlMapper.put("/cart-ajax/unchecked", new CartRestController(new CartService(new CartDao())));
        urlMapper.put("/admin", new AdminController());
        urlMapper.put("/upload", new FileUploadServlet());
        urlMapper.put("/item", new ItemController(new ItemService(new ItemDao(), new CargoDao()), new CategoryService(new CategoryDao())));

        CargoDao cargoDao = new CargoDao();
        CargoService cargoService = new CargoService(cargoDao);
        StockController stockController = new StockController(cargoService);
        urlMapper.put("/stock/list", stockController);
        urlMapper.put("/stock/stat", stockController);

        KakaoServlet kakaoServlet = new KakaoServlet();
        kakaoProcessServlet kakaoProcessServlet = new kakaoProcessServlet();
        urlMapper.put("/kakao", kakaoServlet);
        urlMapper.put("/kakao/success", kakaoProcessServlet);
        urlMapper.put("/kakao/fail", kakaoProcessServlet);
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        request.setCharacterEncoding("UTF-8");
        String path = uri.substring(0, uri.lastIndexOf("."));

        try {
            if (urlMapper.containsKey(path)) {
                HttpServlet controller = urlMapper.get(path);
                invokeAppropriateMethod(controller, method, request, response);
            } else {
                goNotFoundPage(request, response);
            }
        } catch (MessageException e) {
            System.out.println("writeErrorMessage");
            writeErrorMessage(response, e);
        } catch (Exception e) { // 등록되지 않은 모든 예외들은 에러페이지 이동
            goNotFoundPage(request, response);
        }
    }

    private void goNotFoundPage(HttpServletRequest request, HttpServletResponse response) {
        HttpServlet httpServlet = urlMapper.get("/not-found");
        try {
            invokeAppropriateMethod(httpServlet, "GET", request, response);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void writeErrorMessage(HttpServletResponse response, RuntimeException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8"); // UTF-8 인코딩 설정
        try {
            response.getWriter().write(e.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void invokeAppropriateMethod(HttpServlet controller, String method, HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, IllegalAccessException {
        Method targetMethod;

        if (method.equalsIgnoreCase("GET")) {
            targetMethod = HttpServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        } else if (method.equalsIgnoreCase("POST")) {
            targetMethod = HttpServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        } else if (method.equalsIgnoreCase("PUT")) {
            targetMethod = HttpServlet.class.getDeclaredMethod("doPut", HttpServletRequest.class, HttpServletResponse.class);
        } else if (method.equalsIgnoreCase("DELETE")) {
            targetMethod = HttpServlet.class.getDeclaredMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class);
        } else {
            throw new UnsupportedOperationException("Unsupported HTTP method: " + method);
        }

        // 접근성 확장
        targetMethod.setAccessible(true);

        try {
            targetMethod.invoke(controller, request, response);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause(); // 원인 예외 얻기
            String errorMessage = cause.getMessage();

            log.error(errorMessage);
            if (cause instanceof MessageException) {
                throw new MessageException(errorMessage);
            } else { // 여러가지 잡다한 예외들 발생시 404 화면으로 이동
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}