package cn.tech.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import cn.tech.connection.*;
import cn.tech.model.*;

public class CancelOrderServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()) {
			String id = request.getParameter("id");
			if(id != null) {
				DBConnection.cancleOrder(Integer.parseInt(id));
			}
			response.sendRedirect("orders.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
