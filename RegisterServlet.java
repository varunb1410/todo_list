import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	// JDBC objects
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		ServletContext context=getServletContext();
		HttpSession session=request.getSession();
		try {
			// JDBC code for connection establishment 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "pat", "pat");
			stmt=con.createStatement();
			pstmt=con.prepareStatement("insert into register values (?,?,?,?,?,?,?)");
			
			// pk generation
			int regid=0;
			rs=stmt.executeQuery("select max(regid) from register");
			if(rs.next()) {
				regid=rs.getInt(1);
			}
			regid++;
			
			// reading form data/request parameters
			String fname=request.getParameter("fname").trim();
			String lname=request.getParameter("lname").trim();
			String email=request.getParameter("email").trim();
			String pass=request.getParameter("pass").trim();
			long mobile=Long.parseLong(request.getParameter("mobile").trim());
			String address=request.getParameter("address").trim();
			
 			// record insertion
			pstmt.setInt(1, regid);
			pstmt.setString(2, fname);
			pstmt.setString(3, lname);
			pstmt.setString(4, email);
			pstmt.setString(5, pass);
			pstmt.setLong(6, mobile);
			pstmt.setString(7, address);
			int i=pstmt.executeUpdate();

			// output to browser
			if(i==1) {
				context.getRequestDispatcher("/index.jsp").forward(request, response);
			}	
			rs.close(); pstmt.close(); stmt.close(); con.close();
		} catch(Exception e) {
			out.println(e); // error goes to browser
			e.printStackTrace(); // error displays on server console
		}// catch()
	}// service()
}// class
