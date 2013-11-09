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

public class MobileLogin implements Action {

	public MobileLogin() {
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String status="false";
		String custId =null;
		List<Photo>picList = null;
		
			System.out.println("hitting the mobile login");
		
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Customer cust = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("email", username));
		if (cust!=null){
			//TODO check if they are a member
			
			if(cust.getPassword().contentEquals(password)){
				status ="true";
				custId=cust.getId();
				picList = BusinessObjectDAO.getInstance().searchForList("Photo", new SearchCriteria("custid", cust.getId()));
			}
		}
		
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
		return "loginresult.jsp";
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
