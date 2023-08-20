package com.bit.shoppingmall.global;

import com.bit.shoppingmall.controller.AdminController;
import com.bit.shoppingmall.controller.CategoryController;
import com.bit.shoppingmall.controller.StatusController;
import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.dao.CategoryDao;
import com.bit.shoppingmall.dao.StatusDao;
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
			}
		}catch(Exception e){ // TODO 여기서 모든 예외를 처리할 수 있음(알아서 하셈)
			throw new RuntimeException(e.getMessage());
		}
		//TODO 매핑 핸들러 없으면 예외 던지는거 관련 생각
	}

	private void invokeAppropriateMethod(HttpServlet controller, String method, HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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

		// 메소드 호출
		targetMethod.invoke(controller, request, response);
	}
}