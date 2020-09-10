package com.koreait.matzip.restaurant;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;

public class RestaurantController {
	public String restMap(HttpServletRequest request) {
		// 메뉴가 있는 템플릿, restMap.jsp가 화면에 열리도록
		request.setAttribute(Const.TITLE, "식당");
		request.setAttribute(Const.VIEW, "restaurant/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
}
