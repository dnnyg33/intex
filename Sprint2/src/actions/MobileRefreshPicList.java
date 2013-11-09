package actions;

import java.util.List;

import intex.SearchCriteria;
import intex.BusinessObjects.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import web.Action;

public class MobileRefreshPicList implements Action {

	public MobileRefreshPicList() {
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String status="false";
		String custId =null;
		List<Photo>picList = null;
		
		HttpSession session = request.getSession();
		Customer cust = (Customer) session.getAttribute("Customer");
				picList = BusinessObjectDAO.getInstance().searchForList("Photo", new SearchCriteria("custid", cust.getId()));
		
		
		JSONObject obj = new JSONObject();
		obj.put("status", status);
		obj.put("custid", custId);
	 
		JSONArray guids = new JSONArray();//the customer's picList
		JSONArray captions = new JSONArray();
		
		for(Photo p : picList){
			guids.add(p.getId());
		}
		for(Photo p : picList){
			captions.add(p.getCaption());
		}
		
	 
		obj.put("guids", guids);
		obj.put("captions", captions);
		
	 
		session.setAttribute("Customer", cust);
		request.setAttribute("rjo", obj.toJSONString());
//		System.out.println(obj.toString());
		
//		Log.printLines(obj.toString());
		return "loginresult.jsp";
	//	return obj.toJSONString();//TODO should i just be returning this String?
		
	}


}
