package com.bit.shoppingmall.global;

import com.bit.shoppingmall.controller.AdminController;
import com.bit.shoppingmall.controller.CategoryController;
import com.bit.shoppingmall.controller.ItemController;
import com.bit.shoppingmall.controller.StatusController;
import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.CategoryDao;
import com.bit.shoppingmall.dao.StatusDao;
import com.bit.shoppingmall.exception.FormatException;
import com.bit.shoppingmall.exception.RangeException;
import com.bit.shoppingmall.exception.RedirectionException;
import com.bit.shoppingmall.exception.SizeException;
import com.bit.shoppingmall.service.AdminService;
import com.bit.shoppingmall.service.CategoryService;
import com.bit.shoppingmall.service.StatusService;
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
	private Map<String, HttpServlet> urlMapper = new HashMap<>();
	private Logger log = Logger.getLogger("work");

	public DispatcherServlet() {
        super();
		urlMapper.put("/admin",new AdminController(new AdminService(new CargoDao())));
		urlMapper.put("/categories", new CategoryController(new CategoryService(new CategoryDao())));
		urlMapper.put("/status", new StatusController(new StatusService(new StatusDao())));
		urlMapper.put("/upload",new FileUploadServlet());
		urlMapper.put("/item", new ItemController());
		urlMapper.put("/pageNotFound",new PageException());
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
			}else{
				goNotFoundPage(request,response);
			}
		}catch(IllegalArgumentException e){
			writeErrorMessage(response, e);
		}catch(RedirectionException e){ // 리다이렉션 예외
			goNotFoundPage(request,response);
		}catch(RangeException | SizeException | FormatException e){
			writeErrorMessage(response, e);
		} catch(Exception e){ // 등록되지 않은 모든 예외들은 에러페이지 이동
			goNotFoundPage(request,response);
		}
	}

	private void goNotFoundPage(HttpServletRequest request, HttpServletResponse response) {
		HttpServlet httpServlet = urlMapper.get("/pageNotFound");
		try {
			invokeAppropriateMethod(httpServlet, "GET", request,response);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex);
		}catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void writeErrorMessage(HttpServletResponse response, RuntimeException e){
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
		}catch(InvocationTargetException e){
			Throwable cause = e.getCause(); // 원인 예외 얻기
			String errorMessage = cause.getMessage();

			if (cause instanceof IllegalArgumentException) {
				throw new IllegalArgumentException(errorMessage);
			}else if(cause instanceof RangeException){
				throw new RangeException(errorMessage);
			}else if(cause instanceof SizeException){
				throw new SizeException(errorMessage);
			}else if(cause instanceof FormatException){
				throw new FormatException(errorMessage);
			}
		}catch (Exception e){
			throw new RuntimeException();
		}
	}
}