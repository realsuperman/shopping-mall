package com.bit.shoppingmall.global;

import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.service.AdminService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/main.bit")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, HttpServlet> urlMapper = new HashMap<>();

	public DispatcherServlet() {
        super();
		CargoDao cargoDao = new CargoDao();
		AdminService adminService = new AdminService(cargoDao);
		//urlMapper("/cargo/list",new AdminController(adminService));
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		request.setCharacterEncoding("UTF-8");
		String path = uri.substring(uri.lastIndexOf("/"));
		path = path.substring(1, path.lastIndexOf("."));
		String next = "main.jsp";
		if(path != null) {
			next = path;		
		}
		//RequestDispatcher rd = request.getRequestDispatcher(next);
		//rd.forward(request, response);
		RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
		rd.forward(request, response);
	}
	
	
	

}





