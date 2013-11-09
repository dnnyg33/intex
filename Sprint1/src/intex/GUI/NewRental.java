package intex.GUI;

import java.util.HashMap;

import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;
import intex.BusinessObjects.ForRent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.byu.isys413.swt.manager.DataException;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**This window is accessible when a ForRent item is scanned in the transaction window. Employees can find customers and 
 * make them members if necessary.
 * @author Section 2 Group 3
 *
 */
public class NewRental {

	protected Shell shell;
	private Text txtMemberId;
	private Text txtSerial;
	private Label lblName;
	private Label lblPricequote;
	private DateTime dateOut;
	private DateTime returnDate;
	private  ForRent forRent;
	private Customer c=null;
	private Button btnCreateNewMember;
	private double price;
	private double estimate;
	private HashMap<String, Object> returnMap = new HashMap<String, Object>();
	private boolean add=true;


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
			NewRental window = new NewRental();
			window.open();
		
	}

	public void open(){
		Display display = Display.getDefault();
		try {
			forRent = BusinessObjectDAO.getInstance().searchForBO("ForRent", new SearchCriteria("id", "fr1"));
			
			forRent.setCRental(forRent.getCProductId());//I think this is right
		} catch (intex.DataException e) {
			e.printStackTrace();
		}
		
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
	 * Open the window.
	 */
	public HashMap<String, Object> open(String serial) {
		Display display = Display.getDefault();
		
	
		try {
			forRent=BusinessObjectDAO.getInstance().searchForBO("ForRent", new SearchCriteria("serial", serial));
			
		} catch (intex.DataException e) {
			e.printStackTrace();
		}
		forRent.setCRental(forRent.getCProductId());
			//forRent.setCRental(forRent.getCProductId());//I think this is right
		
		
		createContents();
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		returnMap.put("price", price);
		returnMap.put("add", add);
		return returnMap;
	}
		


		/**
		 * Create contents of the window.
		 * @throws DataException 
		 */
		protected void createContents(){
			shell = new Shell();
			shell.setSize(515, 312);
			shell.setText("New Rental");
	
			
			shell.setLayout(new BorderLayout(0, 0));
			
			Composite center = new Composite(shell, SWT.NONE);
			center.setLayoutData(BorderLayout.NORTH);
			center.setLayout(new GridLayout(4, false));
			
			Label lblMemberId = new Label(center, SWT.NONE);
			lblMemberId.setText("Email");
			
			txtMemberId = new Text(center, SWT.BORDER);
			GridData gd_txtMemberId = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
			gd_txtMemberId.widthHint = 364;
			txtMemberId.setLayoutData(gd_txtMemberId);
//			try {
//				WindowManager.getInstance().setRequired(shell, txtMemberId);
//			} catch (DataException e2) {
//				e2.printStackTrace();
//			}
			
			Button btnFind = new Button(center, SWT.NONE);
			btnFind.addSelectionListener(new SelectionAdapter() {//find button pressed
				@Override
				public void widgetSelected(SelectionEvent e) {//make sure the email is registered to a member-customer in our DB
					
					try {
						System.out.println(txtMemberId.getText());
						
						 c = BusinessObjectDAO.getInstance().searchForBO("Customer", new SearchCriteria("email", txtMemberId.getText()));
						
					} catch (intex.DataException e1) {
						System.out.println("NewRental.btnFind-->problem getting customer");
					
						e1.printStackTrace();
					}
					if(c==null){lblName.setText("The email typed does not match any customer");}
					
					else if(c.isMember()){//if c is a member
					lblName.setText(c.getFirstName()+" "+c.getLastName()+" is a member.");
					btnCreateNewMember.setEnabled(false);}
					
					else{//not a member
					lblName.setText(c.getFirstName()+" "+c.getLastName()+" is not a member. Please add them before continuing.");
					// enable add membership button
					 btnCreateNewMember.setEnabled(true); 
					}
				}
			});
			btnFind.setText("Find");
			
			Label lblDate = new Label(center, SWT.NONE);
			lblDate.setText("Date");
			
			 dateOut = new DateTime(center, SWT.BORDER);
			dateOut.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			dateOut.setEnabled(false);
			
			 lblName = new Label(center, SWT.WRAP | SWT.NONE);
			GridData gd_lblName = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 2);
			gd_lblName.heightHint = 57;
			gd_lblName.widthHint = 304;
			lblName.setLayoutData(gd_lblName);
			
			Label lblReturnDate = new Label(center, SWT.NONE);
			lblReturnDate.setAlignment(SWT.RIGHT);
			lblReturnDate.setText("Return Date");
			
			 returnDate = new DateTime(center, SWT.BORDER);
			 returnDate.addSelectionListener(new SelectionAdapter() {
			 	@Override
			 	public void widgetSelected(SelectionEvent e) {//date picker pressed
			 		calculateEstimate();
			 	}
			 });
			Label lblSerial = new Label(center, SWT.NONE);
			lblSerial.setText("Serial #");
			
			txtSerial = new Text(center, SWT.BORDER);
			GridData gd_txtSerial = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
			gd_txtSerial.widthHint = 365;
			txtSerial.setLayoutData(gd_txtSerial);
			txtSerial.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
			new Label(center, SWT.NONE);
			txtSerial.setText(forRent.getSerial());
			
			Label lblPrice = new Label(center, SWT.NONE);
			lblPrice.setText("Price");
			
			lblPricequote = new Label(center, SWT.NONE);
			GridData gd_lblPricequote = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_lblPricequote.widthHint = 88;
			lblPricequote.setLayoutData(gd_lblPricequote);
			lblPricequote.setText("Quote");
			
			 btnCreateNewMember = new Button(center, SWT.NONE);
			 btnCreateNewMember.setEnabled(false);
			 btnCreateNewMember.addSelectionListener(new SelectionAdapter() {
			 	@Override
			 	public void widgetSelected(SelectionEvent e) {//Create New Member button clicked
			 		try {
						new MembershipWindow().open(c);
					} catch (DataException e1) {
						System.out.println("NewRental.btnCreateMember");
						e1.printStackTrace();
					}
			 	}
			 });
			 btnCreateNewMember.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			 btnCreateNewMember.setText("Create New Member");
			new Label(center, SWT.NONE);
			
			Composite composite = new Composite(shell, SWT.NONE);
			composite.setLayoutData(BorderLayout.SOUTH);
			composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			Button btnCancel = new Button(composite, SWT.BORDER);
			btnCancel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					add=false;
//					returnMap.put("add", add);
					shell.close();
				}
			});
			btnCancel.setText("Cancel");
			
			Button btnSubmit = new Button(composite, SWT.NONE);
			btnSubmit.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {//select button pressed
					
					price = estimate;
					shell.close();
				}
			});
			btnSubmit.setText("Submit");
	
		

		}

		protected void calculateEstimate() {
			
			 estimate = ((returnDate.getDay()-dateOut.getDay())*(forRent.getCRental().getPricePerDay()));
			lblPricequote.setText(estimate+"");//TODO change size of label so shows cents
		
			
		}

		/**
		 * @return the forRent
		 */
		public ForRent getForRent() {
			return forRent;
		}

		/**
		 * @param forRent the forRent to set
		 */
		public void setForRent(ForRent forRent) {
			this.forRent = forRent;
		}

}



