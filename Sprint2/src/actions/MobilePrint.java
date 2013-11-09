package actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;
import intex.BusinessObjects.DebitOrCredit;
import intex.BusinessObjects.JournalEntry;
import intex.BusinessObjects.PrintOrder;
import intex.BusinessObjects.Trans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import web.Action;

public class MobilePrint implements Action {

	public MobilePrint() {
	}

	/**This method does the following things: 
	    //get customer
	    //create transaction 
		//create print orders
			//using list of pictures' ids
		//create debitCredits and journal entry
		//save all
		//create receipt for toast**/
	@SuppressWarnings("unchecked")
	@Override
	public String process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		Customer cust = (Customer) session.getAttribute("Customer");
		
		int size = Integer.valueOf(request.getParameter("size"));
		ArrayList<PrintOrder> printList = new ArrayList<PrintOrder>();
		
		Trans t = BusinessObjectDAO.getInstance().create("Trans");
		
		for (int i =0; i<size; i++ ){
//			String guid = (String) request.getAttribute("guid"+i);
			String guid = request.getParameter("guid"+i);
			PrintOrder po = BusinessObjectDAO.getInstance().create("PrintOrder");
			po.setPhotoId(guid);
			String printId = PrintOrder.Size_5x7;//TODO hard coded for now. shared preferences later
			po.setPrintId(printId);
			po.setPrint(printId);
			po.setQuantity(1);//TODO shared preferences determines the quantity. get this value from nameValue pair
//			po.setQuantity((Integer) request.getParameter("quantity"));
			
			double chargeAmt = po.getQuantity()*(po.getPrint().getPrice());
			po.construct(chargeAmt, t.getId(), "PrintOrder");//revenue source fields
			printList.add(po);
			po.save();
			
		}
		//create matching journal entry
		JournalEntry je = BusinessObjectDAO.getInstance().create("JournalEntry");
		je.construct(new Date());
		je.setTransId(t.getId());
		je.save();
		
		
		
		
		//create 5 of these
		DebitOrCredit dc1 = BusinessObjectDAO.getInstance().create("DebitOrCredit");
		double amount =0;
		for(PrintOrder po : printList){
			amount = amount+po.getChargeAmt();
		}
		dc1.construct(true, "Cash", je.getId(), amount, "gl1");//cash
		dc1.save();
		//TODO for the other debits/credits, make amount a portion of amount as necessary
		
		
		t.construct(new Date(), amount, 0, cust.getId(), "Mobile App", amount, null);
		t.save();
		
		String simpledate = new SimpleDateFormat("hh:mm:ss").format(new Date());
//		System.out.println("simpledate"+simpledate);
		
		
		String toast = "Thank you for your purchase at "+ simpledate+". Your account will be charged "+ amount;
		
		
		JSONObject obj = new JSONObject();
		obj.put("receipt", toast);
		
		request.setAttribute("rjo", obj.toJSONString());
		
		return "loginresult.jsp";
	}

}

