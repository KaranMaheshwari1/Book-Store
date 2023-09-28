package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String query ="INSERT INTO BOOKDATA(BOOKNAME,BOOKEDITION,BOOKPRICE) VALUES(?,?,?)";	

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	// get PrintWriter
	PrintWriter pw = res. getWriter();
	pw.println("<style>body\r\n"
			+ "{\r\n"
			+ "	background-image: url('book.jpg');\r\n"
			+ "	background-repeat: no-repeat;\r\n"
			+ "	background-size: cover;\r\n"
			+ "	font-size: 24px;\r\n"
			+ "}</style>");
	// set content type
	res.setContentType("text/html");
	String bookName =req.getParameter("bookname");
	String bookEdition =req.getParameter("bookEdition");
	Float bookPrice =Float.parseFloat (req.getParameter("bookprice"));
	// load jdbc deriver
	try {
	Class.forName("com.mysql.cj.jdbc.Driver");
	}catch(ClassNotFoundException cnf) {
		cnf.printStackTrace();
	}
	// generate the connection
	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", "root", "root");) {
		PreparedStatement ps = con.prepareStatement(query);{
			ps.setString(1, bookName);	
			ps.setString(2, bookEdition);
			ps.setFloat(3, bookPrice);
			int count = ps.executeUpdate();
			if(count==1) {
				pw.println("<table align ='center' border ='1'  height ='50%' width='50%' >");

				pw.println("<td><center><font size=\"24\" color=red'>Record is Registred Successfully!!!</center></td>");
			}else {
				pw.println("<h2>Record is not Registred Successfully</h2>");

			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
		pw.println("<h1>"+e.getMessage()+"</h2>");
	}
	pw.println("<a href='Home.html'>Home</a>");	pw.println("<br>");
	pw.println("<a href='booklist'>Book List</a>");
}
@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	doGet(req,res);
	}
}
