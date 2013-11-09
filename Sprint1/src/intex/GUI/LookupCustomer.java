package intex.GUI;

import java.util.List;

import intex.DataException;
import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Customer;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import edu.byu.isys413.swt.manager.WindowManager;

/**Allows employee to search for customer account information in DB using either first and last name, email or phone
 * 
 * @author Group 2-3
 *
 */
public class LookupCustomer {

	protected Shell shell;
	private Text txtFirstName;
	private Text txtLastName;
	private Text txtPhone;
	private Text txtEmail;
	private Table table_1;
	private TableViewer tableViewer;
	private List<Customer> custs;
	private TableViewerColumn colFirstName;
	private TableViewerColumn colLastName;
	private TableViewerColumn colEmail;
	private TableViewerColumn colPhone;
	private Label lblPhone;
	private Label lblEmail;
	private Label lblFirstName;
	private Label lblLastName;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LookupCustomer window = new LookupCustomer();
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
		shell.setSize(500, 400);
		shell.setText("Lookup Customer");
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		
		Label lblEnterTheCustomers = new Label(composite, SWT.NONE);
		lblEnterTheCustomers.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblEnterTheCustomers.setAlignment(SWT.CENTER);
		lblEnterTheCustomers.setBounds(10, 0, 430, 24);
		lblEnterTheCustomers.setText("Enter the customer's information");
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new GridLayout(4, false));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		final Label lblPleaseFillIn = new Label(composite_1, SWT.NONE);
		lblPleaseFillIn.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblPleaseFillIn.setText("Please fill in missing search parameter(s)");
		lblPleaseFillIn.setVisible(false);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		lblFirstName = new Label(composite_1, SWT.NONE);
		lblFirstName.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblFirstName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFirstName.setText("First Name:");
		
		txtFirstName = new Text(composite_1, SWT.BORDER);
		
		
		GridData gd_txtFirstName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtFirstName.widthHint = 142;
		txtFirstName.setLayoutData(gd_txtFirstName);
		
		Button btnSearch_3 = new Button(composite_1, SWT.NONE);btnSearch_3.setText("Search");
		new Label(composite_1, SWT.NONE);
		
		 lblLastName = new Label(composite_1, SWT.NONE);
		 lblLastName.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		 lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblLastName.setText("Last Name:");
		btnSearch_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//search by name
				//txtFirstName.setText("");
				//txtLastName.setText("");
				txtEmail.setText("");
				txtPhone.setText("");
				List<Customer> cust =null;
				if(txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty()){
					//set the error message up top 
					lblPleaseFillIn.setVisible(true);
					lblFirstName.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
					lblLastName.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
					
				}else{
					lblPleaseFillIn.setVisible(false);
					lblPhone.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					lblFirstName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					lblLastName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					lblEmail.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					try {
						cust = BusinessObjectDAO.getInstance().searchForList("Customer", new SearchCriteria("firstname", txtFirstName.getText()));
						
						if(cust.isEmpty()){
							try {
								WindowManager.getInstance().showMessageDialog(shell, "No results found");
							} catch (edu.byu.isys413.swt.manager.DataException e2) {
								e2.printStackTrace();
							}
						}else{
							tableViewer.setInput(cust);
						}
					} catch (DataException e1) {
						e1.printStackTrace();
						
					
					
				}}
				
				
				
				//set custs to display in the tableviewer
			}
		});
		
		
		
		
		
		txtLastName = new Text(composite_1, SWT.BORDER);
		GridData gd_txtLastName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtLastName.widthHint = 159;
		txtLastName.setLayoutData(gd_txtLastName);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		lblPhone = new Label(composite_1, SWT.NONE);
		lblPhone.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblPhone.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPhone.setText("Phone:");
		
		txtPhone = new Text(composite_1, SWT.BORDER);
		txtPhone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnSearch_1 = new Button(composite_1, SWT.NONE);
		btnSearch_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//Search by phone number
				txtFirstName.setText("");
				txtLastName.setText("");
				txtEmail.setText("");
//				txtPhone.setText("");
				List<Customer> custs =null;
				if(txtPhone.getText().isEmpty()){
					//set the error message up top 
					lblPleaseFillIn.setVisible(true);
					lblPhone.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
					
				
				}else{
					lblPleaseFillIn.setVisible(false);
					lblPhone.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					lblFirstName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					lblLastName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					lblEmail.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					try {
						custs = BusinessObjectDAO.getInstance().searchForList("Customer", new SearchCriteria("phone", txtPhone.getText()));
						if(custs.isEmpty()){
							try {
								WindowManager.getInstance().showMessageDialog(shell, "No results found");
							} catch (edu.byu.isys413.swt.manager.DataException e2) {
								e2.printStackTrace();
							}
						}else{
							tableViewer.setInput(custs);
						}
					} catch (DataException e1) {
						e1.printStackTrace();
					
				}}
				
				
				tableViewer.setInput(custs);
			}
		});
		btnSearch_1.setText("Search");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		lblEmail = new Label(composite_1, SWT.NONE);
		lblEmail.setFont(SWTResourceManager.getFont("Garamond", 14, SWT.BOLD));
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmail.setText("Email:");
		
		txtEmail = new Text(composite_1, SWT.BORDER);
		txtEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnSearch_2 = new Button(composite_1, SWT.NONE);
		btnSearch_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//search by email
				txtFirstName.setText("");
				txtLastName.setText("");
//				txtEmail.setText("");
				txtPhone.setText("");
				List<Customer> custs =null;
				if(txtEmail.getText().isEmpty()){
					//set the error message up top 
					lblPleaseFillIn.setVisible(true);
					lblEmail.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
					
				
				}else{
					lblPleaseFillIn.setVisible(false);
					lblPhone.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					lblFirstName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					lblLastName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					lblEmail.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
					try {
						custs = BusinessObjectDAO.getInstance().searchForList("Customer", new SearchCriteria("email", txtEmail.getText()));
						if(custs.isEmpty()){
							try {
								WindowManager.getInstance().showMessageDialog(shell, "No results found");
							} catch (edu.byu.isys413.swt.manager.DataException e2) {
								e2.printStackTrace();
							}
						}else{
							tableViewer.setInput(custs);
						}
					} catch (DataException e1) {
						e1.printStackTrace();
					
				}}
				
				
				
				// set custs to display in the tableviewer
				tableViewer.setInput(custs);
			}
		});
		btnSearch_2.setText("Search");
		new Label(composite_1, SWT.NONE);
		
		tableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_1 = tableViewer.getTable();
		GridData gd_table_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_table_1.heightHint = 130;
		gd_table_1.widthHint = 448;
		table_1.setLayoutData(gd_table_1);
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		
		colFirstName = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnFirstName = colFirstName.getColumn();
		tblclmnFirstName.setWidth(100);
		tblclmnFirstName.setText("First Name");
		
		colLastName = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnLastName = colLastName.getColumn();
		tblclmnLastName.setWidth(100);
		tblclmnLastName.setText("Last Name");
		
		colPhone= new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPhone = colPhone.getColumn();
		tblclmnPhone.setWidth(100);
		tblclmnPhone.setText("Phone");
		
		colEmail= new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnEmail = colEmail.getColumn();
		tblclmnEmail.setWidth(200);
		tblclmnEmail.setText("Email");
		
		colFirstName.setLabelProvider(new ColumnLabelProvider() {
		public String getText(Object element) {
		Customer p = (Customer) element;
		return p.getFirstName();
		}
		});
		
		colLastName.setLabelProvider(new ColumnLabelProvider() {
		public String getText(Object element) {
		Customer p = (Customer) element;
		return p.getLastName();
		}
		});
		
		colPhone.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
			Customer p = (Customer) element;
			return p.getPhone();
			}
			});
		
		colEmail.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
			Customer p = (Customer) element;
			return p.getEmail();
			}
			});
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(custs);
		}// createContents
		
		
		
	
	
}
