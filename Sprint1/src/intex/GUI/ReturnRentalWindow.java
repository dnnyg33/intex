package intex.GUI;

import java.util.Date;
import intex.DataException;
import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.CRental;
import intex.BusinessObjects.Fee;
import intex.BusinessObjects.ForRent;
import intex.BusinessObjects.Rental;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.byu.isys413.swt.manager.WindowManager;

/**This is the window employees use to return rented items, when a customer
 *  drops them off/brings them back.
 *  @author Group 2-3*/
public class ReturnRentalWindow {

	protected Shell shell;
	private Text txtSerialNumber;
	private Text txtFee;
	private double feeAmount;
	private Composite composite_1;
	private Button btnCancel;
	private Button btnNext;
	private DateTime dateTimeOut;
	private DateTime dateTimeDue;
	private DateTime dateTimeIn;
	private Button btnFind;
	
	private Fee fee;
	ForRent fr= null;
	Rental r = null;
	CRental cr = null;
	private Button btnWaiveFee;
	private Label lblHowlate;
	private Label lblRenter;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ReturnRentalWindow window = new ReturnRentalWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public Fee open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return fee;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Rental Return");
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new GridLayout(3, false));
		
		Label lblSerialNumber = new Label(composite, SWT.NONE);
		lblSerialNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSerialNumber.setText("Serial Number");
		
		txtSerialNumber = new Text(composite, SWT.BORDER);
		txtSerialNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnFind = new Button(composite, SWT.NONE);
		btnFind.addSelectionListener(new SelectionAdapter() {//find button clicked
			@Override
			public void widgetSelected(SelectionEvent e) {
				String serial = txtSerialNumber.getText();
				
				try {
					 fr = BusinessObjectDAO.getInstance().searchForBO("ForRent", new SearchCriteria("serial", serial)); 
					} catch (DataException e1) {
					
					e1.printStackTrace();
					}		
			if(fr==null){
								try {
									WindowManager.getInstance().showMessageDialog(shell, "Please enter a valid serial number. This product does not need to be returned.");
								} catch (edu.byu.isys413.swt.manager.DataException e2) {
									e2.printStackTrace();
								}
			}else{
				try{
					 r = BusinessObjectDAO.getInstance().searchForBO("Rental", new SearchCriteria("forrentid",fr.getId()),
							 new SearchCriteria("datein", null, SearchCriteria.NULL));
					 r.setForRent(fr);
					cr= BusinessObjectDAO.getInstance().searchForBO("CRental", new SearchCriteria("id", fr.getCProductId()));
				} catch (DataException e1) {
					
					e1.printStackTrace();
				}
				//set dates
				r.setDateIn(new Date());
				try {
					r.save();
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				
				org.joda.time.DateTime dateIn = new org.joda.time.DateTime(r.getDateIn());
				org.joda.time.DateTime dateOut = new org.joda.time.DateTime(r.getDateOut());
				org.joda.time.DateTime dateDue = new org.joda.time.DateTime(r.getDateDue());
				
				
				dateTimeIn.setDate(dateIn.getYear(), dateIn.getMonthOfYear()-1, dateIn.getDayOfMonth());
				dateTimeOut.setDate(dateOut.getYear(), dateOut.getMonthOfYear()-1, dateOut.getDayOfMonth());
				dateTimeDue.setDate(dateDue.getYear(), dateDue.getMonthOfYear()-1, dateDue.getDayOfMonth());
				
				//calculate fee
				//dateIn-dateDue*ppd
				
			int daysLate = 0;
				double ppd = cr.getPricePerDay();
				if(dateIn.getYearOfCentury()==dateDue.getYearOfCentury()){
				 daysLate = dateIn.getDayOfYear()-dateDue.getDayOfYear();
				feeAmount = ppd*daysLate;
				}
				else{
					 //TODO handle different years
				}
				r.setTrans(r.getTransId());
				r.getTrans().setCustomer(r.getTrans().getCustId());
				
				txtFee.setText(feeAmount+"");
				lblHowlate.setVisible(true);
				lblHowlate.setText("This product is "+daysLate+" day(s) late");
				lblRenter.setText("Renter: "+ r.getTrans().getCustomer().getFirstName()+ " "+ r.getTrans().getCustomer().getLastName());
				lblRenter.setVisible(true);
				}
			}
		});
		btnFind.setText("Find");
		
		Label lblDateOut = new Label(composite, SWT.NONE);
		lblDateOut.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDateOut.setText("Date Out");
		
		dateTimeOut = new DateTime(composite, SWT.BORDER);
		dateTimeOut.setEnabled(false);
		dateTimeOut.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		new Label(composite, SWT.NONE);
		
		Label lblDateDue = new Label(composite, SWT.NONE);
		lblDateDue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDateDue.setText("Date Due");
		
		dateTimeDue = new DateTime(composite, SWT.BORDER);
		dateTimeDue.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		dateTimeDue.setEnabled(false);
		new Label(composite, SWT.NONE);
		
		Label lblDateIn = new Label(composite, SWT.NONE);
		lblDateIn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDateIn.setText("Date In");
		
		dateTimeIn = new DateTime(composite, SWT.BORDER);
		new Label(composite, SWT.NONE);
		
		
		Label lblFee = new Label(composite, SWT.NONE);
		lblFee.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFee.setText("Fee");
		
		
		txtFee = new Text(composite, SWT.BORDER);
		GridData gd_txtFee = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtFee.widthHint = 104;
		txtFee.setLayoutData(gd_txtFee);
		txtFee.setEditable(false);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnWaiveFee = new Button(composite, SWT.CHECK);
		btnWaiveFee.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				feeAmount=0;
			}
		});
		btnWaiveFee.setText("Waive Fee");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblHowlate = new Label(composite, SWT.NONE);
		GridData gd_lblHowlate = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblHowlate.widthHint = 224;
		lblHowlate.setLayoutData(gd_lblHowlate);
		lblHowlate.setText("This product is _ day(s) late");//
		lblHowlate.setVisible(false);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblRenter = new Label(composite, SWT.NONE);
		GridData gd_lblRenter = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblRenter.widthHint = 215;
		lblRenter.setLayoutData(gd_lblRenter);
		lblRenter.setText("Renter:");
		lblRenter.setVisible(false);
		new Label(composite, SWT.NONE);
		
		composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.SOUTH);
		composite_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnCancel = new Button(composite_1, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				r.setDateIn(null);
				try {
					r.save();
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				shell.close();
			}
		});
		btnCancel.setText("Cancel");
		
		btnNext = new Button(composite_1, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("returnrental.fee--"+feeAmount);
				//next button to create fee object and put in trans window
				try {
					 fee = BusinessObjectDAO.getInstance().create("Fee");
					 //fee.getRental().setTrans(fee.getRental().getTransId());
					fee.setAmount(feeAmount);
					fee.setChargeAmt(feeAmount);
					fee.setTypeOf("Fee");
					fee.setRentalId(r.getId());
					fee.setWaived(btnWaiveFee.getSelection());
					fee.setRental(r);
					fee.save();
					
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				shell.close();
				
				
			}
		});
		btnNext.setText("Next");

	}
}
