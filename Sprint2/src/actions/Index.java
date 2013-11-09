

package actions;

import web.*;
import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObject;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.CProduct;
import intex.BusinessObjects.ForSale;
import intex.BusinessObjects.Product;

import javax.servlet.http.*;

import java.sql.ResultSet;
import java.util.*;

/**
 * An example action for a number game.
 * @author conan
 */
public class Index implements Action {
  
  Random random = new Random();
  List<Product> list = new ArrayList<Product>();
  List<String> stringList = new ArrayList<String>();
  /** Constructor */
  public Index() {
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
    // NOTE that this file is almost exactly the same as the "regular"
    // one (NumberGame.java).  The only difference is it sends the result
    // back to ajaxgame_json.jsp, which creates JSON rather than a new
    // HTML page.
	  HttpSession session = request.getSession();
	  
	  if (session.getAttribute("results") == null) {//if products (what you send back) is null
		  session.setAttribute("results", new ArrayList<String>());
	  }
	  System.out.println("index-query-->"+request.getParameter("search"));
	  if(request.getParameter("search")!=null){//if they entered a search as long there's content, get the strings that we need and add them to the results list
		  
		 // List<Product> bos = new ArrayList<Product>();//this list is empty
		  
		  String query=(String) request.getParameter("search");
		  
		  List<CProduct> cproducts = BusinessObjectDAO.getInstance().searchForList("CProduct", 
				  new SearchCriteria("description", query+"%", SearchCriteria.LIKE));//TODO make searching better
		 System.out.println(cproducts.size());
		  String productLink = null;
		  if(cproducts.size()!=0);{
			  session.setAttribute("results", new ArrayList<String>());
			  	for(CProduct c:cproducts){
			List<String>temp= (List<String>)session.getAttribute("results");
				productLink = "<a href=http://localhost:8080/WebCode/actions.ProductFeature.action?vid="
					+ c.getId()
					+ ">"
					+ c.getManufacturer()
					+ " " + c.getModel() 
					+ "-" + c.getPrice() + "</a>";
			temp.add(productLink);
			System.out.println("index.java-productLink-->"+productLink);
			  	}
			  	
		  }
	  }else{
		  System.out.println("no search term entered");
	  }
	   
    // wrap up the history into a nice string
    StringBuilder builder = new StringBuilder();
    for (String guess: (List<String>)session.getAttribute("results")) {
      if (builder.length() > 0) {
    	  builder.append(", ");
      		}
      builder.append("\"" + guess + "\"");
    }
    System.out.println("indexjava.builder.toString-->"+builder.toString());
    session.setAttribute("string", builder.toString());
    request.setAttribute("string", builder.toString());

    // next jsp page to go to
    return "index_json.jsp";
  }//process
  
  
  
}//class
