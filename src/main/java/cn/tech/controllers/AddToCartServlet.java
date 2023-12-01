package cn.tech.controllers;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import cn.tech.model.*;
import java.util.*;
public class AddToCartServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("type/html");
		PrintWriter out = response.getWriter();
		 try {
	            ArrayList<Cart> cartList = new ArrayList<>();
	            int id = Integer.parseInt(request.getParameter("id"));
	            Cart cm = new Cart();
	            cm.setId(id);
	            cm.setQuantity(1);
	            HttpSession session = request.getSession();
	            ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
	            if (cart_list == null) {
	                cartList.add(cm);
	                session.setAttribute("cart-list", cartList);
	                response.sendRedirect("index.jsp");
	            } else {
	                cartList = cart_list;

	                boolean exist = false;
	                for (Cart c : cart_list) {
	                    if (c.getId() == id) {
	                        exist = true;
	                        out.println("<h3 style='color:crimson; text-align: center'>Item Already in Cart.</h3> <a href='cart.jsp'>GO to Cart Page</a>");
	                    }
	                }

	                if (!exist) {
	                    cartList.add(cm);
	                    response.sendRedirect("index.jsp");
	                }
	            }
	        }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		}
	
	}
