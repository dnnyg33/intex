package intex.GUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import intex.DataException;
import intex.BusinessObjects.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import edu.byu.isys413.swt.manager.WindowManager;


/**Allows an employee while logged in, to change their username or password*/
public class ChangeUsernameOrPassword {

	protected Shell shell;
	private Employee employee;
	private Text txtUsername;
	private Text txtPassword;
	private Text txtVerifyPassword;
	private Button btnSubmit;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ChangeUsernameOrPassword window = new ChangeUsernameOrPassword();
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
	 * Open the window.
	 */
	public void open(Employee employee) {
		this.employee=employee;
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
		shell.setSize(450, 157);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(2, false));
		shell.setDefaultButton(btnSubmit);
		
		Label lblUsername = new Label(shell, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername.setText("New Username");
		
		txtUsername = new Text(shell, SWT.BORDER);
		txtUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword.setText("New Password");
		
		txtPassword = new Text(shell, SWT.BORDER);
		txtPassword.setEchoChar('*');
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblVerifyPassword = new Label(shell, SWT.NONE);
		lblVerifyPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVerifyPassword.setText("Verify Password");
		
		txtVerifyPassword = new Text(shell, SWT.BORDER);
		txtVerifyPassword.setEchoChar('*');
		txtVerifyPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		 btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//clicked submit
				if(txtPassword.getText().isEmpty()||txtUsername.getText().isEmpty()||txtVerifyPassword.getText().isEmpty()){
					try {
						WindowManager.getInstance().showMessageDialog(shell, "Please fill in all fields");
					} catch (edu.byu.isys413.swt.manager.DataException e1) {
						e1.printStackTrace();
					}
				}
				
				else{
				if(txtPassword.getText().contains(txtVerifyPassword.getText())){
				employee.setUsername(txtUsername.getText());
				employee.setPassword(txtPassword.getText());
				shell.close();
				
				try {
					employee.save();
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				}else{
					try {
						WindowManager.getInstance().showMessageDialog(shell, "Passwords do not match");
					} catch (edu.byu.isys413.swt.manager.DataException e1) {
						e1.printStackTrace();
					}
				}
				
				}
			}
		});
		btnSubmit.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnSubmit.setText("Submit");

	}
}
