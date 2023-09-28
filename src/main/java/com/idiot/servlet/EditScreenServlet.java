package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.xdevapi.Result;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	private static final String query = "SELECT BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA where ID=?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// get PrintWriter
		PrintWriter pw = res.getWriter();
		// set content type
		res.setContentType("text/html");
		// Get the ID
		int id = Integer.parseInt (req.getParameter("id")); // this id from booklist
		// load jdbc deriver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		// generate the connection
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "root"); 
			PreparedStatement ps = con.prepareStatement(query);){
			pw.println("<style>body\r\n"
					+ "{\r\n"
					+ "	background-image: url('book.jpg');\r\n"
					+ "	background-repeat: no-repeat;\r\n"
					+ "	background-size: cover;\r\n"
					+ "	font-size: 24px;\r\n"
					+ "}</style>");
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				rs.next();
				pw.println("<form action = 'editurl?id="+id+"' method = 'post'>");
				pw.println("<table align ='center' border ='1'   width='50%' >");
				pw.println("<tr>");
				pw.println("<td>Book Name</td>");
				pw.println("<td><input type ='text' name ='bookName' value ='"+rs.getString(1)+"'></td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<td>Book Edition</td>");
				pw.println("<td><input type ='text' name ='bookEdition' value ='"+ rs.getString(2)+"'></td>");
				pw.println("</tr>");
				pw.println("<tr>");
				pw.println("<td>Book Price</td>");
				pw.println("<td><input type ='text' name ='bookPrice' value ='"+rs.getFloat(3)+"'></td>");
				pw.println("<tr>");
				pw.println("<td><input ='text' type ='submit' value='Edit'></td>");
				pw.println("<td><input ='text' type ='reset' value='Cancel'></td>");
				pw.println("</tr>");
				pw.println("</tr>");
				pw.println("</table>");
				pw.println("</form>");

			
		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h2>");
		}
		pw.println("<a href='Home.html'>Home</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
