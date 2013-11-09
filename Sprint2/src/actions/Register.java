

package actions;

import intex.BatchEmail;
import intex.GUID;
import intex.MailSettings;
import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;
import intex.BusinessObjects.Membership;

import javax.servlet.http.*;

import web.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * An example action for a number game.
 * @author conan
 */
public class Register implements Action {
  
  Random random = new Random();
  
  /** Constructor */
  public Register() {
      // no op
  }
  
  /**
   *  Responds to an action call from the Controller.java file.
   *  This method should perform any work required, then return
   *  a new JSP page to call.
   *
   *  @param request  The HttpServletRequest object that represents
   *                  information sent from the browser.  The getParameter()
   *                  method is particularly useful.
   *
   *  @returns        A string giving the path of the JSP file to call
   *                  after this action is performed.  If you need this
   *                  to be dynamic, use hidden form field to send
   *                  a parameter giving the page to go to after the
   *                  action.  
   *
   *                  If the path starts with "/", the path is 
   *                  absolute to the application context.  If the 
   *                  path doesn't start with "/", it is relative
   *                  to the current page (dangerous!).
   */
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
	  
	  
	  // ensure we have a number to guess for
    HttpSession session = request.getSession();
    //validate input
    for (String name: new String[] {"username", "password" }){
    if(request.getParameter("email")==null || request.getParameter("email").equals("")){
    	request.setAttribute("message","Please fill in the all values ");
    	return "Register.jsp";
    }
    
    
    }
    Customer custAlready = BusinessObjectDAO.getInstance().searchForBO("Customer", 
    		new SearchCriteria("email", request.getParameter("email")));
    if(custAlready!=null){
    	
    	StringBuilder body = new StringBuilder();
    	body.append("Dear "+custAlready.getFirstName()+",\n \n Please click this link to activate your account.");
    	String vid = custAlready.getValidationCode();
    	body.append("http://tengentllc.com/actions.RegisterValidate.action?vid=" + vid + "\n\n");
    	body.append("Thanks.");
    	BatchEmail.send(MailSettings.smtpUserName, "MyStuff Company", custAlready.getEmail(), "Register Your New Account", body.toString());
       
    	request.setAttribute("checkEmailAlert", true);
    	
    	return "index.jsp";
    }else{
    
    System.out.println("hitting the register.java");
    //create the customer
    Customer cust= BusinessObjectDAO.getInstance().create("Customer");
    cust.setAddress(request.getParameter("address"));
    cust.setCity(request.getParameter("city"));
    cust.setEmail(request.getParameter("email"));
    cust.setFirstName(request.getParameter("firstName"));
    cust.setLastName(request.getParameter("lastName"));
    cust.setMember(true);
//    if(request.getParameter("password").contentEquals(request.getParameter("confirmPassword")));
    cust.setPassword(request.getParameter("password"));//TODO what to do with non-same passwords
    cust.setPhone(request.getParameter("phone"));
    cust.setState(request.getParameter("state"));
    cust.setZip(request.getParameter("zip"));
    cust.setValidationCode(GUID.generate());
    cust.setValidated(false);
    cust.save();
    
    Membership mem = BusinessObjectDAO.getInstance().create("Membership");
    
    String month = request.getParameter("exMonth");
    String year = request.getParameter("exYear");
    mem.setExpirationDate(new SimpleDateFormat("MM/yy").parse(month+"/"+year));
    
    mem.setCreditCard(request.getParameter("creditCardNumber"));
    mem.setCustId(cust.getId());
    mem.setSecurityCode(request.getParameter("securityCode"));
    mem.setStartDate(new Date());
    mem.setTrial(false);
    mem.save();
    
    
    //send email and direct them to that site
 // this goes in the action java file
	StringBuilder body = new StringBuilder();
	body.append("Dear "+cust.getFirstName()+",\n \n Please click this link to activate your account.");
	String vid = cust.getValidationCode();
	body.append("http://tengentllc.com/actions.RegisterValidate.action?vid=" + vid + "\n\n");
	body.append("Thanks.");
	
    
   
    
//    String msg = "Dear "+cust.getFirstName()+",\n \n Please click this link to activate your account." +
//    		"http://localhost:8080/WebCode/actions.RegisterValidate.action/"+cust.getValidationCode();
    BatchEmail.send(MailSettings.smtpUserName, "MyStuff Company", cust.getEmail(), "Register Your New Account", body.toString());
    
    
    //log the user in
   // session.setAttribute("userid", cust.getId());
    //session.setAttribute("Customer", cust);
    request.setAttribute("checkEmailAlert", true);
    
    return "index.jsp";
    }
  }//process
  
  
  
}//class
