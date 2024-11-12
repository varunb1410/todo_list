import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		ServletContext context=getServletContext();
		HttpSession session=request.getSession();
		String email=request.getParameter("email").trim();
		String pass=request.getParameter("pass").trim();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "pat", "pat");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from register where email='"+email+"' and pass='"+pass+"'");
			if(rs.next()) {
				session.setAttribute("email", email);
				context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "<font color='red'>Username/Password is wrong</font>");
				context.getRequestDispatcher("/index.jsp").forward(request, response);
			} // else
		} // try
		catch(Exception e) {
			e.printStackTrace();
		} // catch
	}// service()
}// class
