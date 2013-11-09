package intex.GUI;


import intex.DataException;
import intex.GUID;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import swing2swt.layout.FlowLayout;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import edu.byu.isys413.swt.manager.WindowManager;


/**Employees can create new customer accounts from within the store, including password. These customers can then log on to the website
 * 
 * @author Group 2-3
 *
 */
public class NewCustomer {

	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Customer c = null;
	private Text txtPassword;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			NewCustomer window = new NewCustomer();
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
	protected void createContents() {//TODO after create customer, send email with validation link
		shell = new Shell();
		shell.setSize(475, 330);
		shell.setText("Create New Customer");
		shell.setLayout(new BorderLayout(0, 0));
		
		Label lblPleaseEnterThe = new Label(shell, SWT.NONE);
		lblPleaseEnterThe.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblPleaseEnterThe.setAlignment(SWT.CENTER);
		lblPleaseEnterThe.setLayoutData(BorderLayout.NORTH);
		lblPleaseEnterThe.setText("Please enter the customer's information");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblFirstName = new Label(composite, SWT.NONE);
		lblFirstName.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblFirstName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFirstName.setText("First Name:");
		
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLastName = new Label(composite, SWT.NONE);
		lblLastName.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLastName.setText("Last Name:");
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Phone:");
		
		text_2 = new Text(composite, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblEmail = new Label(composite, SWT.NONE);//TODO Have an arrow highlight if the customer's email is validated
		lblEmail.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmail.setText("Email:");
		
		text_3 = new Text(composite, SWT.BORDER);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAddress = new Label(composite, SWT.NONE);
		lblAddress.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAddress.setText("Address:");
		
		text_4 = new Text(composite, SWT.BORDER);
		text_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCity = new Label(composite, SWT.NONE);
		lblCity.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblCity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCity.setText("City:");
		
		text_5 = new Text(composite, SWT.BORDER);
		text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblState = new Label(composite, SWT.NONE);
		lblState.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblState.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblState.setText("State:");
		
		text_6 = new Text(composite, SWT.BORDER);
		text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblZipCode = new Label(composite, SWT.NONE);
		lblZipCode.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblZipCode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblZipCode.setText("Zip Code:");
		
		text_7 = new Text(composite, SWT.BORDER);
		text_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPassword = new Label(composite, SWT.NONE);
		lblPassword.setText("Password");
		lblPassword.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txtPassword = new Text(composite, SWT.BORDER);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtPassword.setEchoChar('*');
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Composite bottom = new Composite(shell, SWT.BORDER);
		bottom.setLayoutData(BorderLayout.SOUTH);
		bottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Button btnCreateMembership = new Button(bottom, SWT.NONE);
		btnCreateMembership.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(text.getText().isEmpty()||text_1.getText().isEmpty()||text_2.getText().isEmpty()||text_3.getText().isEmpty()||text_4.getText().isEmpty()||text_5.getText().isEmpty()||text_6.getText().isEmpty()||text_7.getText().isEmpty()){
					try {
						WindowManager.getInstance().showMessageDialog(shell, "Please fill in all fields");
					} catch (edu.byu.isys413.swt.manager.DataException e1) {
						e1.printStackTrace();
					}
				}else{
				createCustomer();
				
					try {
						new MembershipWindow().open(c);
					} catch (edu.byu.isys413.swt.manager.DataException e1) {
						e1.printStackTrace();
					}
			
				
				}
			}
		});
		btnCreateMembership.setText("Create Membership");
		
		Button btnSave = new Button(bottom, SWT.BORDER);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(txtPassword.getText().isEmpty()||text.getText().isEmpty()||text_1.getText().isEmpty()||text_2.getText().isEmpty()||text_3.getText().isEmpty()||text_4.getText().isEmpty()||text_5.getText().isEmpty()||text_6.getText().isEmpty()||text_7.getText().isEmpty()){
					try {
						WindowManager.getInstance().showMessageDialog(shell, "Please fill in all fields");
					} catch (edu.byu.isys413.swt.manager.DataException e1) {
						e1.printStackTrace();
					}
				}else{
				
				createCustomer();
				}
			}
		});
		btnSave.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnSave.setText("Done");

	}

	protected void createCustomer() {
		
		try {
			c = BusinessObjectDAO.getInstance().create("Customer");
		} catch (DataException e1) {
			e1.printStackTrace();
		}
		c.setFirstName(text.getText());
		c.setLastName(text_1.getText());
		c.setPhone(text_2.getText());
		c.setEmail(text_3.getText());
		c.setAddress(text_4.getText());
		c.setCity(text_5.getText());
		c.setState(text_6.getText());
	    c.setZip(text_7.getText());
	    c.setPassword(txtPassword.getText());
	    c.setValidationCode(GUID.generate());
	    try {
			BusinessObjectDAO.getInstance().save(c);
		} catch (DataException e1) {
			e1.printStackTrace();
			System.out.println("NewCustomer.createCustomer-->couldn't save the customer object to the DB");
		}
	    
	    shell.close();
		
	}


}
