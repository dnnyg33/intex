import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class StoreGUI {

	protected Shell shell;
	private Text txtLocation;
	private Text txtAddress;
	private Text txtCity;
	private Text txtState;
	private Text txtZipcode;
	private Text txtPhone;
	private Text txtManager;
	private Text txtFirstname;
	private Text txtMiddleName;
	private Text txtLastName;
	private Text txtHireDate;
	private Text txtPhone_1;
	private Text txtSalary;
	private Text txtSearch;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StoreGUI window = new StoreGUI();
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
		shell.setSize(450, 300);
		shell.setText("Store");
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblSearch = new Label(composite, SWT.NONE);
		lblSearch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSearch.setText("Search");
		
		txtSearch = new Text(composite, SWT.BORDER);
		txtSearch.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
	
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(composite_1, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Location");
		
		txtLocation = new Text(composite_1, SWT.BORDER);
		txtLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAddress = new Label(composite_1, SWT.NONE);
		lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAddress.setText("Address");
		
		txtAddress = new Text(composite_1, SWT.BORDER);
		txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCity = new Label(composite_1, SWT.NONE);
		lblCity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCity.setText("City");
		
		txtCity = new Text(composite_1, SWT.BORDER);
		txtCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblState = new Label(composite_1, SWT.NONE);
		lblState.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblState.setText("State");
		
		txtState = new Text(composite_1, SWT.BORDER);
		txtState.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblZipcode = new Label(composite_1, SWT.NONE);
		lblZipcode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblZipcode.setText("ZipCode");
		
		txtZipcode = new Text(composite_1, SWT.BORDER);
		txtZipcode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPhone = new Label(composite_1, SWT.NONE);
		lblPhone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPhone.setText("Phone");
		
		txtPhone = new Text(composite_1, SWT.BORDER);
		txtPhone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblManager = new Label(composite_1, SWT.NONE);
		lblManager.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblManager.setText("Manager");
		
		txtManager = new Text(composite_1, SWT.BORDER);
		txtManager.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.EAST);
		composite_2.setLayout(new GridLayout(2, false));
		new Label(composite_2, SWT.NONE);
		
		Label lblEmployees = new Label(composite_2, SWT.NONE);
		lblEmployees.setText("Employees");
		new Label(composite_2, SWT.NONE);
		
		List storeList = new List(composite_2, SWT.BORDER);
		storeList.setBounds(10, 10, 3, 66);
		int storeSize = 0;
		try {
			storeSize = StoreDAO.getInstance().getAll().size();
		for (int i=0; i<storeSize; i++){
			String location = StoreDAO.getInstance().getAll().get(i).getLocation();
			 storeList.add(location);
		}} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Composite composite_3 = new Composite(shell, SWT.NONE);
		composite_3.setLayoutData(BorderLayout.WEST);
		
		List empList = new List(composite_3, SWT.BORDER);
		empList.setBounds(10, 10, 3, 66);
		
		int employeeSize = 0;
		try {
			employeeSize = EmployeeDAO.getInstance().getAll().size();
		for (int i=0; i<employeeSize; i++){
			String firstName = EmployeeDAO.getInstance().getAll().get(i).getFirstName();
			String lastName = EmployeeDAO.getInstance().getAll().get(i).getLastName();
			 storeList.add(firstName +" "+ lastName);
		}} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Composite composite_4 = new Composite(shell, SWT.NONE);
		composite_4.setLayoutData(BorderLayout.SOUTH);
		composite_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Button btnNew = new Button(composite_4, SWT.NONE);
//		btnNew.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnNew.setText("New Store");
		
		Button btnSave = new Button(composite_4, SWT.NONE);
	
//		btnNew.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnSave.setText("Save Store");
		
		
		
		
		
	}
}
