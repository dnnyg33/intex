package actions;

import java.sql.ResultSet;

import intex.SearchCriteria;
import intex.BusinessObjects.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.Action;

public class SummaryPage implements Action {

	public SummaryPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//show the serial number of the item that they want, use the cproductid to search 
		/////for any ForSale items where the cproductid =c.id. then check the status and select the first one
				
		
		HttpSession session = request.getSession();
		String cproductId = (String) session.getAttribute("cproductid");
		//ForSale fs = BusinessObjectDAO.getInstance().searchForBO("ForSale", new SearchCriteria("cproductid", cproductId));
		ResultSet rs = BusinessObjectDAO.getInstance().searchForRS("PProduct", new SearchCriteria ("cproductid", cproductId));
		String serial=null;
		while (rs.next()){//grabs the last serialnum returned
			serial =rs.getString("serialnum");
		}
		session.setAttribute("serial", serial);
		
		//get the store they selected for checkoit
		String storeId= (String) request.getAttribute("storeid");//TODO fix the store1 to GUID problem
		Store s = BusinessObjectDAO.getInstance().searchForBO("Store", new SearchCriteria("id", storeId));
		session.setAttribute("Store", s);
		
		return "SummaryPage.jsp";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
