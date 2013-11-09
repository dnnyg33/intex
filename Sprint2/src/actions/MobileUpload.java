package actions;

import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;
import intex.BusinessObjects.DebitOrCredit;
import intex.BusinessObjects.JournalEntry;
import intex.BusinessObjects.Photo;
import intex.BusinessObjects.PrintOrder;
import intex.BusinessObjects.Trans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import web.Action;

public class MobileUpload implements Action {

	/**This method does the following things: 
    //get customer from session
    //create new photo object for customer
	//save
		//return result of save
	**/
@SuppressWarnings("unchecked")
@Override
public String process(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	
	HttpSession session = request.getSession();
	
	Customer cust = (Customer) session.getAttribute("Customer");
	
	String picas64 = request.getParameter("picas64");
	Photo p = BusinessObjectDAO.getInstance().create("Photo");
	p.setCaption(request.getParameter("caption"));
	p.setCustId(cust.getId());
	p.setPicas64(picas64);
	p.setDateof(new Date());
	
	p.save();
	
	String simpledate = new SimpleDateFormat("hh:mm:ss").format(new Date());
//	System.out.println("simpledate"+simpledate);
	
	String picId = p.getId();
	String toast = "Your upload was successfully saved at "+ simpledate+".";
	
	
	JSONObject obj = new JSONObject();
	obj.put("picid", picId);
	obj.put("toast", toast);
	
	request.setAttribute("rjo", obj.toJSONString());
	
	return "loginresult.jsp";
	}

}
