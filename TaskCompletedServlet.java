// TaskCompletedServlet.java
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TaskCompletedServlet")
public class TaskCompletedServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session=request.getSession();
		ServletContext context=getServletContext();
		
		int taskid=Integer.parseInt(request.getParameter("taskid"));
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "pat", "pat");
			Statement stmt=con.createStatement();
			int i=stmt.executeUpdate("delete from tasks where taskid="+taskid);
			System.out.println(taskid+" taskStatus updating");
			// int i=stmt.executeUpdate("UPDATE tasks SET taskStatus=3 WHERE taskid="+taskid);
			context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
			stmt.close(); con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
