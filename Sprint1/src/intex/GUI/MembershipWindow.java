package intex.GUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;
import intex.BusinessObjects.Membership;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import edu.byu.isys413.swt.manager.DataException;
import edu.byu.isys413.swt.manager.WindowManager;

/**This window is nagivable from the create customer window. If customer wants to create a membership while in store, the employee hits the create membership button and this window opens
 * 
 * @author Group 2-3
 *
 */
public class MembershipWindow {

	protected Shell shell;
	private Text txtEmail;
	private Text txtCCNumber;
	private Text txtSecurityCode;
	private Text txtExpirationDate;
	private Button btnTrial;
	private Button btnCancel;
	private Button btnCreate;
	private DateTime dateTime;
	private Customer customer;
	protected Membership membership;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MembershipWindow window = new MembershipWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws DataException 
	 */
	public void open() throws DataException {
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
	 * Open the window.
	 * @throws DataException 
	 */
	public void open(Customer c) throws DataException {
		Display display = Display.getDefault();
		this.customer=c;
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
	 * @throws DataException 
	 */
	protected void createContents() throws DataException {
		shell = new Shell();
		shell.setSize(451, 195);
		shell.setText("Membership");
		shell.setLayout(new GridLayout(5, false));
		new Label(shell, SWT.NONE);
		
		WindowManager.getInstance().centerWindow(shell);
		
		Label lblEmail = new Label(shell, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmail.setText("Email");
		
		txtEmail = new Text(shell, SWT.BORDER);
		if(customer!=null){
		txtEmail.setText(customer.getEmail());}
		GridData gd_txtEmail = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtEmail.widthHint = 144;
		txtEmail.setLayoutData(gd_txtEmail);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblCcNumber = new Label(shell, SWT.NONE);
		lblCcNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCcNumber.setText("CC Number");
		
		txtCCNumber = new Text(shell, SWT.BORDER);
		txtCCNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblStartDate = new Label(shell, SWT.NONE);
		lblStartDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStartDate.setText("Start Date");
		
		dateTime = new DateTime(shell, SWT.DATE);
		new Label(shell, SWT.NONE);
		
		Label lblSecurityCod = new Label(shell, SWT.NONE);
		lblSecurityCod.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSecurityCod.setText("Security Code");
		
		txtSecurityCode = new Text(shell, SWT.BORDER);
		txtSecurityCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblExpirationDat = new Label(shell, SWT.NONE);
		lblExpirationDat.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblExpirationDat.setText("Expiration Date");
		
		txtExpirationDate = new Text(shell, SWT.BORDER);
		txtExpirationDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		
		btnTrial = new Button(shell, SWT.CHECK);
		btnTrial.setText("Trial");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnCancel.setText("Cancel");
		
		btnCreate = new Button(shell, SWT.NONE);
		btnCreate.addSelectionListener(new SelectionAdapter() {//Create Button pressed
			@Override
			public void widgetSelected(SelectionEvent e) {//grabs all fields and creates membership
				if(txtEmail.getText().isEmpty()||txtCCNumber.getText().isEmpty()||txtExpirationDate.getText().isEmpty()||txtSecurityCode.getText().isEmpty()){
					try {
						WindowManager.getInstance().showMessageDialog(shell, "Please fill in all fields");
					} catch (DataException e1) {
						e1.printStackTrace();
					}
				}else{
				
				
				Membership m=null;
				try {
					 m = BusinessObjectDAO.getInstance().create("Membership");
				} catch (intex.DataException e2) {
					e2.printStackTrace();
				}
				SimpleDateFormat sdf= new SimpleDateFormat("MM/yy");
				
				try {
					m.setExpirationDate(sdf.parse(txtExpirationDate.getText()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				m.setCreditCard(txtCCNumber.getText());
				m.setSecurityCode(txtSecurityCode.getText());
				m.setTrial(btnTrial.getSelection());
				m.setCustId(customer.getId());
				customer.setMember(true);
				
				int day = dateTime.getDay();
				int month = dateTime.getMonth()+1;//TODO why is this wrong?
				int year = dateTime.getYear();
				System.out.println(month);
				try {
					m.setStartDate(new SimpleDateFormat("MM/dd/yyyy").parse((month)+"/"+day+"/"+year));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				try {
					m.save();
					customer.save();
				} catch (intex.DataException e1) {
					e1.printStackTrace();
				}
				shell.close();}
			}
		});
		btnCreate.setText("Create");
		new Label(shell, SWT.NONE);

	}

}
