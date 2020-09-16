package com.koreait.matzip.restaurant;

import java.io.File;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.CommonDAO;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.db.RestaurantDAO;
import com.koreait.matzip.vo.RestaurantDomain;
import com.koreait.matzip.vo.RestaurantRecommendMenuVO;
import com.koreait.matzip.vo.RestaurantVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class RestaurantController {
	
	private RestaurantService service;
	
	public RestaurantController() {
		service = new RestaurantService();
	}
	
	public String restMap(HttpServletRequest request) {
		// 메뉴가 있는 템플릿, restMap.jsp가 화면에 열리도록
		request.setAttribute(Const.TITLE, "지도  보기");
		request.setAttribute(Const.VIEW, "restaurant/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String restReg(HttpServletRequest request) {
		final int I_M = 1;
		request.setAttribute("categoryList", CommonDAO.selCodeList(I_M));
		
		request.setAttribute(Const.TITLE, "가게 등록");
		request.setAttribute(Const.VIEW, "restaurant/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String restRegProc(HttpServletRequest request) {
		String nm = request.getParameter("nm");
		String addr = request.getParameter("addr");
		double lat = CommonUtils.getDoubleParameter("lat", request);
		double lng = CommonUtils.getDoubleParameter("lng", request);
		int cd_category = CommonUtils.getIntParameter("cd_category", request);
		int i_user = SecurityUtils.getLoginUser(request).getI_user();
		
		RestaurantVO param = new RestaurantVO();
		
		param.setNm(nm);
		param.setAddr(addr);
		param.setLat(lat);
		param.setLng(lng);
		param.setCd_category(cd_category);
		param.setI_user(i_user);
		
		int result = service.restReg(param);
		
		return "redirect:/restaurant/restMap";
		
	}
	
	public String ajaxGetList(HttpServletRequest request) {
		return "ajax:" + service.getRestList();
	}
	
	public String restDetail(HttpServletRequest request) {		
		int i_rest = CommonUtils.getIntParameter("i_rest", request);
				
		RestaurantVO param = new RestaurantVO();
		param.setI_rest(i_rest);
		
		request.setAttribute("css", new String[] {"restaurant"});
		
		request.setAttribute("recommendMenuList", service.getRecommendMenuList(i_rest));
		request.setAttribute("data", service.getRest(param));
		request.setAttribute("menuList", service.getMenuList(i_rest));
		request.setAttribute(Const.TITLE, "디테일");
		request.setAttribute(Const.VIEW, "restaurant/restDetail");
		
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	public String addMenusProc(HttpServletRequest request) {
		int i_rest = service.addMenus(request);
		
		return "redirect:/restaurant/restDetail?i_rest=" + i_rest;
	}
	
	public String addRecMenusProc(HttpServletRequest request) {
		int i_rest = service.addRecMenus(request);
		
		return "redirect:/restaurant/restDetail?i_rest=" + i_rest;
	}
		
	public String ajaxDelRecMenu(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter("i_rest", request);
		int seq = CommonUtils.getIntParameter("seq", request);
		int i_user = SecurityUtils.getLoginUserPk(request);
		
		System.out.println("하하 i_rest : " + i_rest);
		System.out.println("하하 seq : " + seq);
		
		RestaurantRecommendMenuVO param = new RestaurantRecommendMenuVO();
		param.setI_rest(i_rest);
		param.setSeq(seq);
		param.setI_user(i_user);
		
		int result = service.delRecMenu(param);
		
		// DB 삭제하면서 directory에 있는 파일도 같이 삭제됨
		String savePath = request.getServletContext().getRealPath("/res/img/restaurant");
		String menu_pic = request.getParameter("menu_pic");
		String path = String.format("%s/%d/%s", savePath, i_rest, menu_pic);
		FileUtils.delFile(path);
		
		return "ajax:" + result;
	}
}
