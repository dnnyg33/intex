

package actions;

import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;

import javax.servlet.http.*;

import web.Action;

import java.util.*;

/**
 * An example action for a number game.
 * @author conan
 */
public class RegisterValidate implements Action {
  
  Random random = new Random();
  
  /** Constructor */
  public RegisterValidate() {
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
    
    Customer c1 = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("validationcode", request.getParameter("vid")));
    
    
    System.out.println("We validate here.");
    //String url = request.getRequestURL().toString();
    //String code = url.substring(63);
   // Customer c1 = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("validationcode", code));
    c1.setValidated(true);
    c1.save();
    System.out.println(request.getParameter("vid"));
    
    // log the user in
    session.setAttribute("Customer", c1);
    //session.setAttribute("userid", c1.getId());
    
    
    // forward to the correct JSP file
    return "index.jsp";
    
  }//process
  
  
  
}//class














