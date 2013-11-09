package actions;

import java.util.Date;

import intex.BatchEmail;
import intex.MailSettings;
import intex.BusinessObjects.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.Action;

public class Checkout implements Action {

	public Checkout() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session= request.getSession();
		
		//create transaction using session customer, store, amount (product)
		Customer c = (Customer) session.getAttribute("Customer");
		Store s= (Store) session.getAttribute("Store");
		CProduct cp = (CProduct) session.getAttribute("CProduct");
		
		Trans t = BusinessObjectDAO.getInstance().create("Trans");
		Commission com = BusinessObjectDAO.getInstance().create("Commission");
		t.setCustId(c.getId());
		t.setDateOf(new Date());
		t.setEmpId(s.getManagerId());
		t.setStoreId(s.getId());
		double subtotal = cp.getPrice();
		double tax = subtotal*s.getSalesTaxRate();
		double total = subtotal+tax;
		t.setTax(tax);
		t.setTotal(total);
		t.setSubtotal(subtotal);
		t.save();
		
		double commission = cp.getFullCommissionRate()*subtotal;
		com.setAmount(commission);
		com.setDateOf(new Date());
		com.setEmpId(s.getManagerId());
		com.setTransId(t.getId());
		com.save();
		//delete the physical product
		System.out.println("checkout.java--about to go to homepage.jsp");
		String message = "Congratulations" + c.getFirstName() + " " + c.getLastName()+", you have successfully purchased a(n) "+cp.getModel()+" for "+t.getTotal()+
				". This total includes "+ t.getSubtotal()+ " for the product and "+ t.getTax()+" for tax. Thank you for shopping with MyStuff and please come again soon";
				
		BatchEmail.send(MailSettings.smtpUserName, "MyStuff Company", c.getEmail(), "Recent Order", message);
		
		
		return "index.jsp";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
