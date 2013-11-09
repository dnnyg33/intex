package actions;

import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;
import intex.BusinessObjects.Photo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import web.Action;

public class MobilePictureString implements Action {

	public MobilePictureString() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//TODO get their same session using cookie
		
		HttpSession session = request.getSession();//verify the session is the same
		Customer cust = (Customer) session.getAttribute("Customer");
		
		
		String guid = request.getParameter("guid");
		
		Photo photo = BusinessObjectDAO.getInstance().searchForBO("Photo", new SearchCriteria("id", guid));
	
		
		JSONObject obj = new JSONObject();
		obj.put("picas64", photo.getPicas64());
		
		request.setAttribute("rjo", obj.toJSONString());
		
//		Log.printLines(obj.toString());
		return "loginresult.jsp";
	}

}
