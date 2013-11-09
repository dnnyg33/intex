package intex.GUI;

import java.util.HashMap;

import intex.DataException;
import intex.SearchCriteria;
import intex.BusinessObjects.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import swing2swt.layout.FlowLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import edu.byu.isys413.swt.manager.WindowManager;

/**First class the employee sees upon opening the program. Upon successful log in, they are taken to transaction window
 * 
 * @author Group 2-3
 */
public class LogIn {

	protected Shell shlMystuffEmployeeLogin;
	private Text txtPassword;
	private static boolean isCorrect = false;
	private Label lblError;
	private boolean keepOpen;
	private boolean firstTime;
	private HashMap<String, Object> map = new HashMap<String, Object>();
	
	private Text txtUsername;
	private Button btnSubmit;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			LogIn window = new LogIn();
			window.open(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Open the window.
	 */
	public HashMap<String, Object> open(boolean firstTime) {
		this.firstTime=firstTime;
		Display display = Display.getDefault();
		createContents();
		shlMystuffEmployeeLogin.open();
		shlMystuffEmployeeLogin.layout();
		while (!shlMystuffEmployeeLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		
		
		return map;

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlMystuffEmployeeLogin = new Shell();
		shlMystuffEmployeeLogin.setSize(290, 206);
		shlMystuffEmployeeLogin.setText("MyStuff Employee Login\n");
		shlMystuffEmployeeLogin.setLayout(new BorderLayout(0, 0));
		
		
		try {
			WindowManager.getInstance().centerWindow(shlMystuffEmployeeLogin);
			WindowManager.getInstance().registerWindowEvents(shlMystuffEmployeeLogin, btnSubmit);// make enter default
		} catch (edu.byu.isys413.swt.manager.DataException e2) {
			e2.printStackTrace();
		}

		Composite composite = new Composite(shlMystuffEmployeeLogin, SWT.NONE);
		composite.setLayoutData(BorderLayout.SOUTH);
		composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Composite composite_1 = new Composite(shlMystuffEmployeeLogin, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.NORTH);
		composite_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Label lblLogin = new Label(composite_1, SWT.NONE);
		lblLogin.setFont(SWTResourceManager.getFont("Lucida Grande", 26,
				SWT.NORMAL));
		lblLogin.setText("Login");

		Composite composite_2 = new Composite(shlMystuffEmployeeLogin, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.CENTER);
		composite_2.setLayout(new GridLayout(2, false));
		
		Label lblUsername = new Label(composite_2, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername.setText("Username");

		txtUsername = new Text(composite_2, SWT.BORDER);
		txtUsername.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		Label lblPassword = new Label(composite_2, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPassword.setText("Password");

		txtPassword = new Text(composite_2, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		
		 lblError = new Label(composite_2, SWT.NONE);
		lblError.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		GridData gd_lblError = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_lblError.widthHint = 279;
		lblError.setLayoutData(gd_lblError);
		lblError.setText("Please Retry");
		lblError.setVisible(false);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
			keepOpen = true;
			map.put("keepOpen", keepOpen);
				shlMystuffEmployeeLogin.close();
			}
		});
		btnCancel.setText("Cancel");
		
				btnSubmit = new Button(composite, SWT.NONE);
				btnSubmit.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						
						keepOpen=false;
						
//				LDAP ldap = new LDAP();
//				if(ldap.authenticate("dnnyg33", txtPassword.getText())){
//					setCorrect(true);
//					shlMystuffEmployeeLogin.close();
//					
//				}else{
//					System.out.println("failure");
//					setCorrect(false);
//					txtUsername.setText("wrong, try again loser");
//				}
						
						
						try {
							String username = txtUsername.getText();
							String password = txtPassword.getText();
							
							if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
								lblError.setText("Username or password not entered");
								lblError.setEnabled(true);
							} else {

								Employee emp = BusinessObjectDAO.getInstance().searchForBO("Employee",
												new SearchCriteria("username", username),
												new SearchCriteria("password", password));

								if (emp == null) {
									lblError.setText("Username or password not found");
									lblError.setVisible(true);
								} else {
									if(firstTime){
									//setCorrect(true);
										shlMystuffEmployeeLogin.close();
										System.out.println(" first time");
									new TransactionWindow().open(emp);
									
									}else{
										map.put("employee", emp);
										map.put("keepOpen", keepOpen);
										shlMystuffEmployeeLogin.close();
										System.out.println("not first time");
									}
								}
							}
						} catch (DataException e1) {
							e1.printStackTrace();
						}
						
					}
				
				});
				btnSubmit.setText("Submit");
				shlMystuffEmployeeLogin.setDefaultButton(btnSubmit);
				

	}

	/**
	 * @return the shlMystuffEmployeeLogin
	 */
	public Shell getShlMystuffEmployeeLogin() {
		return shlMystuffEmployeeLogin;
	}

	/**
	 * @param shlMystuffEmployeeLogin
	 *            the shlMystuffEmployeeLogin to set
	 */
	public void setShlMystuffEmployeeLogin(Shell shlMystuffEmployeeLogin) {
		this.shlMystuffEmployeeLogin = shlMystuffEmployeeLogin;

	}


	/**
	 * @return the txtPassword
	 */
	public Text getTxtPassword() {
		return txtPassword;
	}

	/**
	 * @param txtPassword
	 *            the txtPassword to set
	 */
	public void setTxtPassword(Text txtPassword) {
		this.txtPassword = txtPassword;

	}

	/**
	 * @return the isCorrect
	 */
	public boolean isCorrect() {
		return isCorrect;
	}

	/**
	 * @param isCorrect
	 *            the isCorrect to set
	 */
	public static void setCorrect(boolean isCorrect) {
		LogIn.isCorrect = isCorrect;

	}
}
