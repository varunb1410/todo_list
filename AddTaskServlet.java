//AddTaskServlet.java
import java.io.IOException;
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

@WebServlet("/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session=request.getSession();
		ServletContext context=getServletContext();
		
		try {
			String taskName=request.getParameter("taskName").trim();
			String taskDate=request.getParameter("taskDate").trim();
			int taskStatus=Integer.parseInt(request.getParameter("taskStatus").trim());
			
			// db conn
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "pat", "pat");
			Statement stmt=con.createStatement();
			PreparedStatement pstmt=con.prepareStatement("INSERT INTO tasks VALUES (?,?,?,?,?)");
			
			// pk generation
			ResultSet rs=stmt.executeQuery("select max(taskid) from tasks");
			int taskid=0;
			if(rs.next()) {
				taskid=rs.getInt(1);
			}
			taskid++;
			
			// get the email from sesson scope using email attribute
			String email=session.getAttribute("email").toString();
			
			// using email get the regid from the database
			rs=stmt.executeQuery("select regid from register where email='"+email+"'");
			int regid=0;
			if(rs.next()) {
				regid=rs.getInt(1);
			}
			
			// insert record
			pstmt.setInt(1, taskid);
			pstmt.setString(2, taskName);
			pstmt.setString(3, taskDate);
			pstmt.setInt(4, taskStatus);
			pstmt.setInt(5, regid);
			pstmt.executeUpdate();

			rs.close(); pstmt.close(); stmt.close(); con.close();
			// response
			context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		} // catch()
	} // service()
} // class
