package com.koreait.matzip;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/")
public class Container extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("uri : " + request.getRequestURI()); // URI 값으로 분기시킴
		String[] uriArr = request.getRequestURI().split("/");


		for(int i=0; i<uriArr.length; i++) {
			System.out.printf("uriArr[%d] : %s\n", i, uriArr[i]);
		}
		
		if(uriArr.length > 1) {
			request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
