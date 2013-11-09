package intex.GUI;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;

import intex.BatchEmail;
import intex.DataException;
import intex.MailSettings;
import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Commission;
import intex.BusinessObjects.Customer;
import intex.BusinessObjects.Rental;
import intex.BusinessObjects.Trans;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import edu.byu.isys413.swt.manager.WindowManager;

/**This is the window that employees can execute manually to send email reminders to those customers who have overdue items.
 * This function runs on a batch daily as well.
 * @author Group 2-3
 *
 */
public class RentalEmailBatchGUI {//TODO add datedue column

	protected Shell shell;
	private TableViewer tableViewer;
	private List<Rental> autoSale = new LinkedList<Rental>();
	private List<Rental> reminder = new LinkedList<Rental>();
	private Table table_2;
	private String which;
	private Label lblSent;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try { 
			RentalEmailBatchGUI window = new RentalEmailBatchGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(750, 320);
		shell.setText("Email Batch");
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.WEST);
		composite.setLayout(new GridLayout(2, false));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		//lblSent.setVisible(false);
		
		Button btnSendNow = new Button(composite, SWT.NONE);
		btnSendNow.addSelectionListener(new SelectionAdapter() {//send now button pressed
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (autoSale.isEmpty()&&reminder.isEmpty()){
					try {
						WindowManager.getInstance().showMessageDialog(shell, "Add recipients first");
					} catch (edu.byu.isys413.swt.manager.DataException e1) {
						e1.printStackTrace();
					}
				}
				else{
				lblSent.setVisible(true);
				if(which.equalsIgnoreCase("autoSale")){
				for(Rental r:autoSale){
					r.setTrans(r.getTransId());
					r.getTrans().setCustomer(r.getTrans().getCustId());
					Customer c = r.getTrans().getCustomer();
					String fromAddress = MailSettings.smtpUserName;
					String fromName = "MyStuff Company";
					
					String toAddress = c.getEmail();
					
					r.setForRent(r.getForRentId());
					String subject = "Notice for your recent rental";
					r.getForRent().setCProduct(r.getForRent().getCProductId());
					r.getForRent().setCRental(r.getForRent().getCProductId());
					double ppd = r.getForRent().getCRental().getPricePerDay();
					double replaceCost = r.getForRent().getCRental().getReplacementPrice();
					String msg = "Dear "+ c.getFirstName()+" "+c.getLastName()+", \n \n Our records indicate that you" +
							" rented an item ("+r.getForRent().getModel()+") from us on "+r.getDateOut()+" and have not returned it. " +
									"The item was due "+ r.getDateDue()+". This message is to inform you that you have now been charged the replacement cost ("+replaceCost+") as well" +
											"as any applicable late fees ("+ppd+" per day) for this item." +
													"\n \n If this message was sent in error, please contact us immediately."; 
					//send the message
					try {
						BatchEmail.send(fromAddress, fromName, toAddress, subject, msg);
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					} catch (MessagingException e1) {
						e1.printStackTrace();
					}
					
					//create transaction (with autocheckout), commission,  for this email
					Trans t= null;
					try {
						 t = BusinessObjectDAO.getInstance().create("Trans");
					} catch (DataException e1) {
						e1.printStackTrace();
					}
					t.setCustId(c.getId());
					t.setDateOf(new Date());
					t.setEmpId(r.getTrans().getEmpId());
					t.setStoreId(r.getTrans().getStoreId());
					t.setStore(r.getTrans().getStoreId());
					
					//calculate total, subtotal, and tax
					double subtotal = replaceCost+(ppd*10);//replacement cost + ppd*10
					double tax = t.getStore().getSalesTaxRate()*subtotal;
					double total = subtotal+tax;
					t.setTax(tax);
					System.out.println(tax);
					t.setTotal(total);
					t.setSubtotal(subtotal);
					try {
						t.save();
					} catch (DataException e1) {
						e1.printStackTrace();
					}
					//create and save commission
					Commission comm = null;
					try {
						comm=BusinessObjectDAO.getInstance().create("Commission");
						double commission = r.getForRent().getFullCommissionRate()*subtotal;
					comm.construct(r.getTrans().getEmpId(), commission, new Date(), t.getId());
					comm.save();
					} catch (DataException e1) {
						e1.printStackTrace();
					}
					
					
					
					}//repeat
				}else{//send note to those needing a reminder.
					for(Rental r:reminder){
						r.setTrans(r.getTransId());
						r.getTrans().setCustomer(r.getTrans().getCustId());
						Customer c = r.getTrans().getCustomer();
						String fromAddress = MailSettings.smtpUserName;
						String fromName = "MyStuff Company";
						
						String toAddress = c.getEmail();
						
						r.setForRent(r.getForRentId());
						String subject = "Reminder about your recent rental";
						r.getForRent().setCProduct(r.getForRent().getCProductId());
						r.getForRent().setCRental(r.getForRent().getCProductId());
						double ppd = r.getForRent().getCRental().getPricePerDay();
						String msg = "Dear "+ c.getFirstName()+" "+c.getLastName()+", \n \n Our records indicate that you" +
								" rented an item ("+r.getForRent().getModel()+") from us on "+r.getDateOut()+" and have not returned it. " +
										"Please return it as soon as possible to avoid late charges ("+ppd+" per day). Your rental will be sold to your account if " +
												"not returned within 10 days of the due date ("+ new DateTime(r.getDateDue()).minusDays(10).toDate()+"). " +
														"\n \n If this message was sent in error, please contact us immediately."; 
						//send the message
						try {
							BatchEmail.send(fromAddress, fromName, toAddress, subject, msg);
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						} catch (MessagingException e1) {
							e1.printStackTrace();
						}
						
						
						
						}//repeat
					
				
			}
				
			}}
		});
		GridData gd_btnSendNow = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnSendNow.widthHint = 192;
		btnSendNow.setLayoutData(gd_btnSendNow);
		btnSendNow.setText("Send Now");
		
		Button btnReminders = new Button(composite, SWT.NONE);
		btnReminders.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//reminder button clicked
				
				reminder.clear();
				searchForReminder();
				tableViewer.setInput(reminder);
			}
		});
		GridData gd_btnReminders = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnReminders.widthHint = 185;
		btnReminders.setLayoutData(gd_btnReminders);
		btnReminders.setText("Load Reminders Queue");
		
		Button btnAutoSale = new Button(composite, SWT.NONE);
		GridData gd_btnAutoSale = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnAutoSale.widthHint = 189;
		btnAutoSale.setLayoutData(gd_btnAutoSale);
		btnAutoSale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//autoSale button clicked
				
				autoSale.clear();
				searchForAutoSale();
				tableViewer.setInput(autoSale);
			}
		});
		btnAutoSale.setText("Load AutoSale Queue");
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton.widthHint = 192;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Cancel");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblSent = new Label(composite, SWT.NONE);
		lblSent.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblSent.setText("Sent!");
		lblSent.setVisible(false);
		new Label(composite, SWT.NONE);
		
		Label lblEmpName = new Label(composite, SWT.WRAP);
		lblEmpName.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ));
		lblEmpName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
//		lblEmpName.setText("employee.getFirstName()+employee.getLastName()+");
		lblEmpName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		new Label(composite, SWT.NONE);
		
		//TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		 tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		 tableViewer.setContentProvider(new ArrayContentProvider());
		table_2 = tableViewer.getTable();
		table_2.setLayoutData(BorderLayout.CENTER);
		table_2.setHeaderVisible(true);
		table_2.setLinesVisible(true);
		
		TableViewerColumn colName = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnName = colName.getColumn();
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");
		
		TableViewerColumn colItem = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnItem = colItem.getColumn();
		tblclmnItem.setWidth(100);
		tblclmnItem.setText("Item");
		
		TableViewerColumn colEmail = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnEmail = colEmail.getColumn();
		tblclmnEmail.setWidth(200);
		tblclmnEmail.setText("Email");
		
		TableViewerColumn colDateDue = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnDateDue = colDateDue.getColumn();
		tblclmnDateDue.setWidth(200);
		tblclmnDateDue.setText("DateDue");
	
		colDateDue.setLabelProvider(new ColumnLabelProvider() {//name column
			public String getText(Object element) {
			Rental p = (Rental) element;//TODO this will also need and else if, for RS type FEE
			
			
			
			
			 String str = p.getDateDue()+"";
			
				return str;
		
			}
			});
		
		
		
		colName.setLabelProvider(new ColumnLabelProvider() {//name column
			public String getText(Object element) {
			Rental p = (Rental) element;//TODO this will also need and else if, for RS type FEE
			
			
				p.setTrans(p.getTransId());//obtain trans
				p.getTrans().setCustomer(p.getTrans().getCustId());//obtain cust
				Customer c= p.getTrans().getCustomer();
				
				return c.getFirstName()+" "+c.getLastName();// should be getting the charge amount from the revenue source		
		
			}
			});
		
		
		colEmail.setLabelProvider(new ColumnLabelProvider() {//Email column
		public String getText(Object element) {
			Rental p = (Rental) element;//TODO this will also need and else if, for RS type FEE
			
			p.setTrans(p.getTransId());//obtain trans
			p.getTrans().setCustomer(p.getTrans().getCustId());//obtain cust
			Customer c= p.getTrans().getCustomer();
			
			return c.getEmail();
		}
		});
		
		colItem.setLabelProvider(new ColumnLabelProvider() {//Item column
		public String getText(Object element) {
			Rental p = (Rental) element;//TODO this will also need and else if, for RS type FEE
			
			p.setForRent(p.getForRentId());
			return p.getForRent().getModel();
		}
		});
	

	}
	
	public void searchForReminder(){//builds the list of those who will receive a reminder email that their rental is overdue, but less than 10 days
	//method called at a certain time
		
		Date d = new Date();
		Date daysAgo = new DateTime(d).minusDays(10).toDate();
		
		
		java.util.List<Rental> needsLoop = new ArrayList<Rental>();
		
		try {
			needsLoop = BusinessObjectDAO.getInstance().searchForList("Rental",
					new SearchCriteria("datedue", new Date(), SearchCriteria.LESS_THAN), 
					new SearchCriteria("datedue", daysAgo, SearchCriteria.GREATER_THAN) );//returns all rentals where they have been turned in or are 10 days late and need to be charged.
		
		} catch (DataException e) {
			e.printStackTrace();
		}
		
		//return list of due people but less than 10 days.
		for(Rental r : needsLoop)
		{
			if (r.getDateIn()==null){
				reminder.add(r);
				
			}
		}
		which = "reminder";
		
	}
	public void searchForAutoSale(){//builds the list of those who will receive an email receipt notifying them that their item was sold after 10 days
		//method called at a certain time
		
		Date d = new Date();
		Date day9 = new DateTime(d).minusDays(9).toDate();
		Date day11 = new DateTime(d).minusDays(11).toDate();
		
		java.util.List<Rental> needsLoop = new ArrayList<Rental>();
		
		try {
			needsLoop = BusinessObjectDAO.getInstance().searchForList("Rental",
					new SearchCriteria("datedue", day9, SearchCriteria.LESS_THAN),
					new SearchCriteria("datedue", day11, SearchCriteria.GREATER_THAN));//returns all rentals where they have been turned in or are 10 days late and need to be charged.
			
			System.out.println("RentalEmailBatchGUI.searchForAutoSale--needsLoop.size-->"+needsLoop.size());
		} catch (DataException e) {
			e.printStackTrace();
		}
		//return list of due people but less than 10 days.
		for(Rental r : needsLoop)
		{
			if (r.getDateIn()==null){
				
				autoSale.add(r);
				System.out.println("r.getid"+r.getId());
				System.out.println("r.getdatedue"+r.getDateDue());
			}
		}
		
		which = "autoSale";
		
	}
}
