package cn.tech.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;
import cn.tech.model.*;
import cn.tech.connection.*;
import java.util.*;
public class CheckOutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			UserModel auth = (UserModel) request.getSession().getAttribute("auth");
			boolean result=false;
			if(cart_list != null && auth!=null) {
				for(Cart c:cart_list) {
					Order order = new Order();
					order.setId(c.getId());
					order.setUid(auth.getId());
					order.setQuantity(c.getQuantity());
					order.setDate(formatter.format(date));
			
					result = DBConnection.insertOrder(order);
					if(!result) {
						break;
					}
					}
				cart_list.clear();
				response.sendRedirect("orders.jsp");}
			else {
				if(auth==null) {
					response.sendRedirect("login.jsp");
				}
				response.sendRedirect("cart.jsp");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
