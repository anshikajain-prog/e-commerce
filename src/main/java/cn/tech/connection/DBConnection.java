package cn.tech.connection;
import cn.tech.model.*;
import java.util.*;
import java.sql.*;
public class DBConnection {
	private static Connection conn=null;
	   static{
	   try{
	   Class.forName("oracle.jdbc.OracleDriver");
	   conn=DriverManager.getConnection("jdbc:oracle:thin:@//LAPTOP-N1Q6KM14:1521/xe","advjavabatch","meanshika");
	   }
	   catch(ClassNotFoundException cnf)
	   {
	       System.out.println(cnf.getMessage());
	   }
	   catch(SQLException sqlex)
	   {
	       System.out.println(sqlex.getMessage());
	   }
	   }
	   public static UserModel getAllDetails(String email,String password)
	   {
		   UserModel b=null;
	       try{
	           PreparedStatement ps=conn.prepareStatement("select id,name from users where email=? and password=?");
	           ps.setString(1,email);
	           ps.setString(2, password);
	           ResultSet rs=ps.executeQuery();
	         
	           while(rs.next())
	           {
	               int id=rs.getInt(1);
	               String name=rs.getString(2);
	               b=new UserModel();
	               b.setId(id);
	               b.setName(name);
	               b.setEmail(email);
	           }
	       }
	       catch(SQLException sqlex)
	       {
	           System.out.println(sqlex.getMessage());
	       }
	       finally {
	    	   return b;
	       }
	      
	   }
	   public static List<Product>getAllProductDetails()
	   {
		   List<Product> products=new ArrayList<>();
		   try {
			   PreparedStatement ps=conn.prepareStatement("select id,name,category,price,image from products");
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				Product row=new Product();
				row.setId(rs.getInt(1));
				row.setName(rs.getString(2));
				row.setCategory(rs.getString(3));
				row.setPrice(rs.getDouble(4));
				row.setImage(rs.getString(5));
				   products.add(row);
			   }
		   }
		   catch(Exception e)
		   {
			  System.out.println(e.getStackTrace()); 
		   }
		   finally {
			   return products;
		   }
		   
	   }
	   public static List<Cart> getCartProducts(ArrayList<Cart> cartList)
	   {
		   List<Cart> products=new ArrayList<>();
		   try {
			   if(cartList.size()>0)
			   {
				   for(Cart item:cartList)
				   {
					   PreparedStatement ps=conn.prepareStatement("select id,name,category,price,image from products where id=?");
					   ps.setInt(1,item.getId());
					   ResultSet rs=ps.executeQuery();
					   while(rs.next())
					   {
						   Cart row=new Cart();
						   row.setId(rs.getInt("id"));
						   row.setName(rs.getString("name"));
						   row.setCategory(rs.getString("category"));
						   row.setPrice(rs.getDouble("price")*item.getQuantity());
						   row.setQuantity(item.getQuantity());
				           products.add(row);
					   }
				   }
			   }
		   }
		   catch(Exception e) {
		   System.out.println(e.getMessage());  
		   }
		   finally {
			   return products;
		   }
	   }
	   public static Double getTotalCartPrice(ArrayList<Cart> cartList)
	   {
		   double sum=0;
		   try {
			   if(cartList.size()>0)
			   {
				   for(Cart item:cartList)
				   {
					   PreparedStatement ps=conn.prepareStatement("select price from products where id=?");
					   ps.setInt(1,item.getId());
					   ResultSet rs=ps.executeQuery();
					   while(rs.next()) {
						   sum=sum+rs.getDouble("price")*item.getQuantity();
					   }
			
				   }
			   }
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }
		   finally {
			   return sum; 
		   }
	   }
	   public static boolean insertOrder(Order model)
	   {
		   boolean result=false;
		  try {
			  PreparedStatement ps=conn.prepareStatement("insert into orders(p_id,u_id,o_quantity,o_date,o_id) values(?,?,?,?,?)");
			  ps.setInt(1,model.getId());
			  ps.setInt(2, model.getUid());
			  ps.setInt(3, model.getQuantity());
			  ps.setString(4, model.getDate());
			  ps.setInt(5,model.getOrderId());
			  ps.executeUpdate();
			  result=true;
		  }
		  catch(SQLException e)
		  {
			  System.out.println(e.getMessage());
		  }
		  finally {
			  return result;
		  }
	   }
	   public static List<Order> userOrders(int id)
	   {
		   List<Order> list=new ArrayList<>();
		   try {
			   PreparedStatement ps=conn.prepareStatement("select o_id,p_id,u_id,o_quantity,o_date from orders where u_id=? order by orders.o_id desc");
			   ps.setInt(1, id);
			   ResultSet rs=ps.executeQuery();
			   while(rs.next())
			   {
				   Order order=new Order();
				   int pId=rs.getInt("p_id");
				   Product product=getSingleProduct(pId);
				   order.setOrderId(rs.getInt("o_id"));
				   order.setName(product.getName());
				   order.setCategory(product.getCategory());
				   order.setPrice(product.getPrice()*rs.getInt("o_quantity"));
				   order.setQuantity(rs.getInt("o_quantity"));
				   order.setDate(rs.getString("o_date"));
				   list.add(order);
				   
			   }
		   }
		   catch(Exception e)
		   {
			   e.getStackTrace();
			   }
		   finally {
			   return list;
		   }
	   }
		   public static Product getSingleProduct(int id)
		   {
			   Product row=null;
			   try {
				   PreparedStatement ps=conn.prepareStatement("select id,name,category,price,image from products where id=?");
				   ps.setInt(1, id);
				   ResultSet rs=ps.executeQuery();
				   while(rs.next())
				   {
					   row=new Product();
					   row.setId(rs.getInt("id"));
					   row.setName(rs.getString("name"));
					   row.setCategory(rs.getString("category"));
					   row.setPrice(rs.getDouble("price"));
					   row.setImage(rs.getString("image"));
				   }
			   }
		   catch(Exception e)
			   {
			   e.getStackTrace();			   }
			   finally {
				   return row;
			   }
	   }
		   public static void cancleOrder(int id)
		   {
			   try {
				   PreparedStatement ps=conn.prepareStatement("delete from orders where o_id=?");
				   ps.setInt(1, id);
				   ps.executeUpdate();
			   }
			   catch(Exception e)
			   {
				   e.getStackTrace();
			   }
		   }
	    public static void closeConnection(){
	       try{
	           conn.close();
	           System.out.println("DB Connection closed");
	       }
	       catch(SQLException ex){
	            System.out.println("Error while closing the conn!"+ex);
	        }
	       
	   }
}
