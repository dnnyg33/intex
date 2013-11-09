

package actions;

import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;

import javax.servlet.http.*;

import web.*;


/**
 * An example action for a number game.
 * @author conan
 */
public class Login implements Action {
  
  static int i=0;
  /** Constructor */
  public Login() {
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
  //  session.setAttribute("Customer", null);
  //  boolean wrongPassword=true;
    if(request.getParameter("username")!=null){//if they entered a username
    	Customer c = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("email", request.getParameter("username")));
    	if(c!=null){//if we got a customer
    		if(c.getPassword().contentEquals(request.getParameter("password"))){//if the password is right
    			session.setAttribute("Customer",c);
    			if(c.isValidated()){
    				return "index.jsp";//redirect
    				}//validated
    			else{//not validated
    				request.setAttribute("retry", "You have not yet validated your account.");
    				}//not validated
    		}//password correct
    		else{//wrong password
    			request.setAttribute("retry", "Wrong username or password");	
    		}
    	}//got customer
    	else{//didn't get a customer
    		request.setAttribute("retry", "Couldn't get account");//TODO change this to the above text
    	}
    }
//    if(i>0){
//    	if(request.getParameter("username").isEmpty()){//blank username
//    		request.setAttribute("retry", "Please enter a valid username or create an account");
//    		}
//    }
//    i++;
    	return "Login.jsp";//only if not correct.
    
  //process
  
  }
  
}//class
