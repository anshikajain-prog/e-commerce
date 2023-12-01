package cn.tech.controllers;
import cn.tech.connection.*;
import cn.tech.model.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;

import cn.tech.connection.DBConnection;
import cn.tech.model.*;
public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            Date date = new Date();
            UserModel auth = (UserModel) request.getSession().getAttribute("auth");

            if (auth != null) {
                String productId = request.getParameter("id");
                int productQuantity = Integer.parseInt(request.getParameter("quantity"));
                if (productQuantity <= 0) {
                	productQuantity = 1;
                }
                Order orderModel = new Order();
                orderModel.setId(Integer.parseInt(productId));
                orderModel.setUid(auth.getId());
                orderModel.setQuantity(productQuantity);
                orderModel.setDate(formatter.format(date));
                boolean result =DBConnection.insertOrder(orderModel);
                if (result){
                    ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                    if (cart_list != null) {
                        for (Cart c : cart_list) {
                            if (c.getId() == Integer.parseInt(productId)) {
                                cart_list.remove(cart_list.indexOf(c));
                                break;
                            }
                        }
                    }
                    response.sendRedirect("orders.jsp");
                }else{
                    out.println("order failed");
                }
            } else {
                response.sendRedirect("login.jsp");
            }

        } catch (Exception e) {
			e.printStackTrace();
		} 
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request,response);
	}
	}

