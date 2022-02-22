package com.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dbsrv extends HttpServlet{

	Connection con;
	PreparedStatement ps;
	
	public void init() {
		
		try {
			
			//get Access to ServletConfig obj
			ServletConfig cfg = getServletConfig();
			//read init param values from web.xml
			String s1 = cfg.getInitParameter("driver");
			String s2 = cfg.getInitParameter("dburl");
			String s3 = cfg.getInitParameter("dbuser");
			String s4 = cfg.getInitParameter("dbpass");
			
			//create a jdbc con object
			Class.forName(s1);
			con =DriverManager.getConnection(s2,s3,s4);
			
			//create a jdbc prepared statement object
			ps = con.prepareStatement("select name,address from student1 where sno = ?" );
			
		    	
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	} //init
	public void doGet(HttpServletRequest req,HttpServletResponse res) {
		try {
			//read data from page
		int no =Integer.parseInt(req.getParameter("tno"));
	//	 int no =req.getParameter("tno");	
			// get Printwriter object
			PrintWriter pw = res.getWriter();
			res.setContentType("text/html");
			
			//write jdbc code
			  // set value to parameter of the query
			ps.setInt(1, no);
			
			//execute the query
			ResultSet rs = ps.executeQuery();
			
			// process the result set object and display student details..
			if(rs.next()) {
				pw.println("<br><br><i>student name is :</i></br></br>"+rs.getString(1));
				pw.println("<br><br><i> Student address is :</i></br></br>"+rs.getString(2));
			}
			
			rs.close();
			pw.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void doPost(HttpServletRequest request ,HttpServletResponse response) {
		doGet(request,response);
		
	}
	
}
