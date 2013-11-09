package actions;


import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.*;

import javax.servlet.http.*;

import web.Action;

import java.util.*;

/**
 * An example action for a number game.
 * 
 * @author conan
 */
public class ProductFeature implements Action {

	Random random = new Random();

	/** Constructor */
	public ProductFeature() {
		// no op
	}

	/**
	 * Responds to an action call from the Controller.java file. This method
	 * should perform any work required, then return a new JSP page to call.
	 * 
	 * @param request
	 *            The HttpServletRequest object that represents information sent
	 *            from the browser. The getParameter() method is particularly
	 *            useful.
	 * 
	 * @returns A string giving the path of the JSP file to call after this
	 *          action is performed. If you need this to be dynamic, use hidden
	 *          form field to send a parameter giving the page to go to after
	 *          the action.
	 * 
	 *          If the path starts with "/", the path is absolute to the
	 *          application context. If the path doesn't start with "/", it is
	 *          relative to the current page (dangerous!).
	 */
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// ensure we have a number to guess for
		HttpSession session = request.getSession();
		//get the cproduct
		//get the stores that this product is sold at
		//use the product and the store to get the store product
		//use the storeproduct to get the quantity.
		
		

		CProduct c1 = BusinessObjectDAO.getInstance().searchForBO("CProduct",
				new SearchCriteria("id", request.getParameter("vid")));
		
		List<Store> dealers = c1.getDealers();//get the stores this product is sold at
		List<StoreProduct> storeProducts = new ArrayList<StoreProduct>();
		
		for(Store s: dealers){//use the CProduct and the storeId from each dealer to get the storeproduct
			StoreProduct sp = BusinessObjectDAO.getInstance().searchForBO("StoreProduct", 
					new SearchCriteria("storeid",s.getId()), new SearchCriteria("productid", c1.getId())); 
			storeProducts.add(sp);
		}
		//put objects into the session so they can be used later by checkout button
		//price, store
		
		session.setAttribute("CProduct", c1);
		session.setAttribute("cproductid", c1.getId());
		request.setAttribute("model", c1.getManufacturer() + " " + c1.getModel());
		System.out.println(c1.getManufacturer()+c1.getModel()+".jpg");
		request.setAttribute("image", "<img width='300' height='300' src=" + c1.getManufacturer()
						+ c1.getModel() + ".jpg>");
		request.setAttribute("manufacturer", c1.getManufacturer());
		request.setAttribute("description", c1.getDescription());
		request.setAttribute("price", c1.getPrice());
		int counter=0;
		for(int i=0; i<dealers.size();i++){
			request.setAttribute("store"+i, dealers.get(i).getLocation());//store1, store2
			request.setAttribute("quantity"+i, storeProducts.get(i).getQuantityOnHand());//
		counter=i;
		}
		request.setAttribute("counter", counter);

		// forward to the correct JSP file
		return "ProductFeature.jsp";

	}// process

}// class

