import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MainGUI {





	protected Shell shell;
	private Text txtStoreLocation;
	private Text txtStoreAddress;
	private Text txtStoreCity;
	private Text txtStoreState;
	private Text txtStoreZip;
	private Text txtStorePhone;
	private Text txtStoreManager;
	private Text txtEmpHireDate;
	private Text txtLastName;
	private Text txtFirstName;
	private Text txtEmpAddress;
	private Text txtEmpCity;
	private Text txtEmpState;
	private Text txtEmpZip;
	private Text txtEmpPhone;
	private Text txtEmpPosition;
	private Button btnNewStore;
	private Button btnStoreSave;
	private Label label_2;
	private Button btnNewEmployee;
	private Button btnEmpSave;
	private Text txtEmpLocation;
	private Label label_3;
	private Label label_4;
	private Label lblProduct;
	private Text txtProductStore;
	private Text txtProductSku;
	private Text txtProductName;
	private Text txtProductManufacturer;
	private Text txtProductCategory;
	private Text txtProductCost;
	private Text txtProductSalePrice;
	private Text txtProductCount;
	private List productList;
	private Button btnNewProduct;
	private Button btnProductSave;
	private List storeList;
	private List empList;
	private Text txtStoreSearch;
	private Text txtEmployeeSearch;
	private Text txtProductSearch;
	private Button btnSearch;
	private Button button;
	private Button button_1;
	private Button btnResetAll;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainGUI window = new MainGUI();
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
		shell.setSize(650, 486);
		shell.setText("SWT Application");
		
		txtStoreLocation = new Text(shell, SWT.BORDER);
		txtStoreLocation.setText("Location");
		txtStoreLocation.setBounds(214, 10, 118, 19);
		
		txtStoreAddress = new Text(shell, SWT.BORDER);
		txtStoreAddress.setText("Address");
		txtStoreAddress.setBounds(10, 35, 322, 19);
		
		txtStoreCity = new Text(shell, SWT.BORDER);
		txtStoreCity.setText("City");
		txtStoreCity.setBounds(10, 60, 174, 19);
		
		txtStoreState = new Text(shell, SWT.BORDER);
		txtStoreState.setText("State");
		txtStoreState.setBounds(190, 60, 42, 19);
		
		txtStoreZip = new Text(shell, SWT.BORDER);
		txtStoreZip.setText("Zip");
		txtStoreZip.setBounds(238, 60, 94, 19);
		
		txtStorePhone = new Text(shell, SWT.BORDER);
		txtStorePhone.setText("Phone");
		txtStorePhone.setBounds(10, 85, 139, 19);
		
		txtStoreManager = new Text(shell, SWT.BORDER);
		txtStoreManager.setText("Manager");
		txtStoreManager.setBounds(155, 85, 177, 19);
		
		txtEmpHireDate = new Text(shell, SWT.BORDER);
		txtEmpHireDate.setText("Hire Date");
		txtEmpHireDate.setBounds(238, 261, 94, 19);
		
		Label lblStore = new Label(shell, SWT.NONE);
		lblStore.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblStore.setAlignment(SWT.CENTER);
		lblStore.setFont(SWTResourceManager.getFont("Lucida Grande", 22, SWT.BOLD));
		lblStore.setBounds(45, 2, 139, 27);
		lblStore.setText("Store");
		
		 storeList = new List(shell, SWT.BORDER);
		 storeList.addSelectionListener(new SelectionAdapter() {
		 	@Override
		 	public void widgetSelected(SelectionEvent e) {//populate store fields when list entry clicked
		 		int index = storeList.getSelectionIndex();
		 		String location = storeList.getItem(index);
		 		String id = StoreDAO.getInstance().getIdFromLocation(location);
		 		Store s =null;
		 		try {
					s = StoreDAO.getInstance().read(id);
				} catch (DataException e1) {
					
					e1.printStackTrace();
				}
		 		txtStoreLocation.setText(s.getLocation());
				txtStoreAddress.setText(s.getAddress());
				txtStoreCity.setText(s.getCity());
				txtStoreState.setText(s.getState());
				txtStorePhone.setText(s.getPhone());
				txtStoreZip.setText(s.getZipcode());
				txtStoreManager.setText(s.getManager());
		 		
		 		
		 		
		 	}
		 });
		storeList.setBounds(351, 10, 166, 128);
		populateStoreList();
		
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 145, 322, 2);
		
		Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setBounds(343, 10, 2, 446);
		
		Label lblEmployee = new Label(shell, SWT.NONE);
		lblEmployee.setText("Employee");
		lblEmployee.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblEmployee.setFont(SWTResourceManager.getFont("Lucida Grande", 22, SWT.BOLD));
		lblEmployee.setAlignment(SWT.CENTER);
		lblEmployee.setBounds(45, 153, 139, 27);
		
		txtLastName = new Text(shell, SWT.BORDER);
		txtLastName.setText("Last Name");
		txtLastName.setBounds(10, 186, 174, 19);
		
		txtFirstName = new Text(shell, SWT.BORDER);
		txtFirstName.setText("First Name");
		txtFirstName.setBounds(190, 186, 142, 19);
		
		txtEmpAddress = new Text(shell, SWT.BORDER);
		txtEmpAddress.setText("Address");
		txtEmpAddress.setBounds(10, 211, 322, 19);
		
		txtEmpCity = new Text(shell, SWT.BORDER);
		txtEmpCity.setText("City");
		txtEmpCity.setBounds(10, 236, 174, 19);
		
		txtEmpState = new Text(shell, SWT.BORDER);
		txtEmpState.setText("State");
		txtEmpState.setBounds(190, 236, 42, 19);
		
		txtEmpZip = new Text(shell, SWT.BORDER);
		txtEmpZip.setText("Zip");
		txtEmpZip.setBounds(238, 236, 94, 19);
		
		txtEmpPhone = new Text(shell, SWT.BORDER);
		txtEmpPhone.setText("Phone");
		txtEmpPhone.setBounds(10, 261, 108, 19);
		
		txtEmpPosition = new Text(shell, SWT.BORDER);
		txtEmpPosition.setText("Position");
		txtEmpPosition.setBounds(124, 261, 108, 19);
		
		 empList = new List(shell, SWT.BORDER);
		 empList.addSelectionListener(new SelectionAdapter() {//populate employee fields when list item clicked
		 	@Override
		 	public void widgetSelected(SelectionEvent e) {
		 		int index = empList.getSelectionIndex();
		 		String person = empList.getItem(index);
		 		String[] splits =person.split(",");
		 		String lastName = splits[0].toString();
		 		String id = EmployeeDAO.getInstance().getIdFromLastName(lastName);
		 		Employee s =null;
		 		try {
					s = EmployeeDAO.getInstance().read(id);
				} catch (DataException e1) {
					// 
					e1.printStackTrace();
				}
		 		txtFirstName.setText(s.getFirstName());
				txtLastName.setText(s.getLastName());
				txtEmpPhone.setText(s.getPhone());
				txtEmpAddress.setText(s.getSalary());
				txtEmpCity.setText(s.getSalary());
				txtEmpHireDate.setText(s.getHireDate());
				txtEmpLocation.setText(s.getStore());
				
		 		
		 	}
		 });
		empList.setBounds(351, 153, 166, 161);
		populateEmpList();
		
		
		btnNewStore = new Button(shell, SWT.NONE);
		btnNewStore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//New store button clicked
				StoreDAO.getInstance().create();//create new store GUID
				//set all fields to blank
				String ty ="";
				txtStoreLocation.setText(ty);
				txtStoreAddress.setText(ty);
				txtStoreCity.setText(ty);
				txtStoreState.setText(ty);
				txtStorePhone.setText(ty);
				txtStoreZip.setText(ty);
				txtStoreManager.setText(ty);
				
				
			}
		});
		btnNewStore.setBounds(10, 110, 163, 28);
		btnNewStore.setText("New Store");
		
		btnStoreSave = new Button(shell, SWT.NONE);
		btnStoreSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//grab info from user
				String location=txtStoreLocation.getText();
				String address=txtStoreAddress.getText();
				String city=txtStoreCity.getText();
				String state=txtStoreState.getText();
				String phone=txtStorePhone.getText();
				String zipcode=txtStoreZip.getText();
				String manager=txtStoreManager.getText();
				
				//create new store object using info
				Store s = new Store(GUID.generate());
				s.setAddress(address);
				s.setCity(city);
				s.setLocation(location);
				s.setManager(manager);
				s.setPhone(phone);
				s.setState(state);
				s.setZipcode(zipcode);
				
				//save the store object to the database
				try {
					StoreDAO.getInstance().save(s);
				} catch (DataException e1) {
					// 
					e1.printStackTrace();
				}
				
				//show the object as first in the list
				storeList.add(s.getLocation(), 0);
				storeList.remove(storeList.getItem(storeList.getSelectionIndex()));//and remove the dud
				
			}
		});
		btnStoreSave.setBounds(179, 110, 94, 28);
		btnStoreSave.setText("Save");
		
		label_2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(351, 145, 166, 2);
		
		btnNewEmployee = new Button(shell, SWT.NONE);
		btnNewEmployee.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//new Employee button clicked
				EmployeeDAO.getInstance().create();//create new store GUID
				//set all fields to blank
				String ty ="";
				txtEmpLocation.setText(ty);
				txtLastName.setText(ty);
				txtFirstName.setText(ty);
				txtEmpState.setText(ty);
				txtEmpPhone.setText(ty);
				txtEmpZip.setText(ty);
				txtEmpHireDate.setText(ty);
				txtEmpAddress.setText(ty);
				txtEmpPosition.setText(ty);
				txtEmpCity.setText(ty);
			}
		});
		btnNewEmployee.setText("New Employee");
		btnNewEmployee.setBounds(10, 286, 163, 28);
		
		btnEmpSave = new Button(shell, SWT.NONE);
		btnEmpSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//the employee save button is clicked
				//grab info from user
				String store=txtEmpLocation.getText();
				String firstName=txtFirstName.getText();
				String lastName=txtLastName.getText();
				String hireDate=txtEmpHireDate.getText();
				String phone=txtEmpPhone.getText();
				
				//create new store object using info
				Employee s = new Employee(GUID.generate());
				s.setFirstName(firstName);
				s.setLastName(lastName);
				s.setHireDate(hireDate);
				s.setStore(store);
				s.setPhone(phone);
				
				//save the store object to the database
				try {
					EmployeeDAO.getInstance().save(s);
				} catch (DataException e1) {
					// 
					e1.printStackTrace();
				}
				
				//show the object as first in the list
				empList.add(s.getLastName(), 0);
				empList.remove(empList.getItem(empList.getSelectionIndex()));//and remove the dud
				
			}
		});
		btnEmpSave.setText("Save ");
		btnEmpSave.setBounds(179, 286, 94, 28);
		
		txtEmpLocation = new Text(shell, SWT.BORDER);
		txtEmpLocation.setText("Location");
		txtEmpLocation.setBounds(214, 161, 118, 19);
		
		label_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(10, 320, 322, 2);
		
		label_4 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_4.setBounds(351, 320, 166, 2);
		
		lblProduct = new Label(shell, SWT.NONE);
		lblProduct.setText("Product");
		lblProduct.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblProduct.setFont(SWTResourceManager.getFont("Lucida Grande", 22, SWT.BOLD));
		lblProduct.setAlignment(SWT.CENTER);
		lblProduct.setBounds(45, 330, 139, 27);
		
		txtProductStore = new Text(shell, SWT.BORDER);
		txtProductStore.setText("Store");
		txtProductStore.setBounds(214, 338, 118, 19);
		
		txtProductSku = new Text(shell, SWT.BORDER);
		txtProductSku.setText("SKU");
		txtProductSku.setBounds(10, 363, 101, 19);
		
		txtProductName = new Text(shell, SWT.BORDER);
		txtProductName.setText("Name");
		txtProductName.setBounds(117, 363, 215, 19);
		
		txtProductManufacturer = new Text(shell, SWT.BORDER);
		txtProductManufacturer.setText("Manufacturer");
		txtProductManufacturer.setBounds(117, 388, 215, 19);
		
		txtProductCategory = new Text(shell, SWT.BORDER);
		txtProductCategory.setText("Category");
		txtProductCategory.setBounds(10, 388, 101, 19);
		
		txtProductCost = new Text(shell, SWT.BORDER);
		txtProductCost.setText("Avg Cost");
		txtProductCost.setBounds(10, 413, 101, 19);
		
		txtProductSalePrice = new Text(shell, SWT.BORDER);
		txtProductSalePrice.setText("Sale Price");
		txtProductSalePrice.setBounds(117, 413, 93, 19);
		
		txtProductCount = new Text(shell, SWT.BORDER);
		txtProductCount.setText("Quantity On Hand");
		txtProductCount.setBounds(214, 413, 118, 19);
		
		productList = new List(shell, SWT.BORDER);
		productList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//populate product items when list item clicked
				int index = productList.getSelectionIndex();
		 		String productName = productList.getItem(index);
		 		String id = ProductDAO.getInstance().getIdFromProductName(productName);
		 		Product s =null;
		 		try {
					s = ProductDAO.getInstance().read(id);
				} catch (DataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 	//	txtProductStore.setText(s.getDescription());
				txtProductSku.setText(s.getDescription());
			//	txtProductCount.setText(s);//product_store quantity on hand
				txtProductName.setText(s.getProductName());
				txtProductCost.setText(s.getAvgCost());
				txtProductManufacturer.setText(s.getManufacturer());
				
		 		
			}
		});
		productList.setBounds(351, 328, 166, 128);
		populateProductList();
		
		
		btnNewProduct = new Button(shell, SWT.NONE);
		btnNewProduct.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ProductDAO.getInstance().create();//create new store GUID
				//set all fields to blank
				String ty ="";
				txtProductSku.setText(ty);
				txtProductStore.setText(ty);
				txtProductName.setText(ty);
				txtProductManufacturer.setText(ty);
				txtProductCost.setText(ty);
				txtProductCount.setText(ty);
//				txtEmpHireDate.setText(ty);
//				txtEmpAddress.setText(ty);
//				txtEmpPosition.setText(ty);
//				txtEmpCity.setText(ty);
//				
			}
		});
		btnNewProduct.setText("New Product");
		btnNewProduct.setBounds(10, 438, 163, 28);
		
		btnProductSave = new Button(shell, SWT.NONE);
		btnProductSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//the product save button is clicked
				//grab info from user
				String store=txtProductStore.getText();
				String productName=txtProductName.getText();
				String cost=txtProductCost.getText();
				String manuf=txtProductManufacturer.getText();
				
				//create new store object using info
				Product s = new Product(GUID.generate());
				s.setAvgCost(cost);
				s.setDescription(manuf);
				s.setManufacturer(manuf);
				s.setProductName(productName);
				
				//save the store object to the database
				try {
					ProductDAO.getInstance().save(s);
				} catch (DataException e1) {
					// 
					e1.printStackTrace();
				}
				
				//show the object as first in the list
				productList.add(s.getProductName(), 0);
				productList.remove(productList.getItem(productList.getSelectionIndex()));//and remove the dud
				
			}
		});
		btnProductSave.setText("Save ");
		btnProductSave.setBounds(179, 438, 94, 28);
		
		txtStoreSearch = new Text(shell, SWT.BORDER);
		txtStoreSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				txtStoreSearch.setText("");
			}
		});
		txtStoreSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtStoreSearch.setText("");
				
				
			}
		});
		txtStoreSearch.setText("Store Location");
		txtStoreSearch.setBounds(523, 60, 117, 19);
		
		txtEmployeeSearch = new Text(shell, SWT.BORDER);
		txtEmployeeSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				txtEmployeeSearch.setText("");
			}
		});
		txtEmployeeSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtEmployeeSearch.setText("");
			}
		});
		txtEmployeeSearch.setText("Employee Last Name");
		txtEmployeeSearch.setBounds(523, 211, 117, 19);
		
		txtProductSearch = new Text(shell, SWT.BORDER);
		txtProductSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				txtProductSearch.setText("");
			}
		});
		txtProductSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtProductSearch.setText("");
			}
		});
		txtProductSearch.setText("Product Name");
		txtProductSearch.setBounds(523, 377, 117, 19);
		
		btnSearch = new Button(shell, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String arg = txtStoreSearch.getText();
				String id = StoreDAO.getInstance().getIdFromLocation(arg);
				Store s= null;
				try {
					 s = StoreDAO.getInstance().read(id);
				} catch (DataException e1) {
					// 
					e1.printStackTrace();
				}
				String location = s.getLocation();
				storeList.removeAll();
				storeList.add(location);
				
			}
		});
		btnSearch.setBounds(533, 81, 94, 28);
		btnSearch.setText("Search");
		
		button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String arg = txtEmployeeSearch.getText();
				String id = EmployeeDAO.getInstance().getIdFromLastName(arg);
				Employee s= null;
				try {
					 s = EmployeeDAO.getInstance().read(id);
				} catch (DataException e1) {
					// 
					e1.printStackTrace();
				}
				String lastName = s.getLastName();
				empList.removeAll();
				empList.add(lastName);
				
			}
		});
		button.setText("Search");
		button.setBounds(533, 232, 94, 28);
		
		button_1 = new Button(shell, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String arg = txtProductSearch.getText();
				String id = ProductDAO.getInstance().getIdFromProductName(arg);
				Product s= null;
				try {
					 s = ProductDAO.getInstance().read(id);
				} catch (DataException e1) {
					// 
					e1.printStackTrace();
				}
				String productName = s.getProductName();
				productList.removeAll();
				productList.add(productName);
			}
		});
		button_1.setText("Search");
		button_1.setBounds(533, 402, 94, 28);
		
		btnResetAll = new Button(shell, SWT.NONE);
		btnResetAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				populateStoreList();
				populateEmpList();
				populateProductList();
			}
		});
		btnResetAll.setBounds(533, 10, 94, 28);
		btnResetAll.setText("Reset All");

	}

	private void populateProductList() {
		int productSize = 0;
		try {
			productSize = ProductDAO.getInstance().getAll().size();
		for (int i=0; i<productSize; i++){
			String productName = ProductDAO.getInstance().getAll().get(i).getProductName();
			 productList.add(productName);
		}} catch (DataException e) {
			// 
			e.printStackTrace();
		}
		
	}

	private void populateEmpList() {
		int empSize = 0;
		try {
			empSize = EmployeeDAO.getInstance().getAll().size();
		for (int i=0; i<empSize; i++){
			String name = EmployeeDAO.getInstance().getAll().get(i).getLastName() +", "+ EmployeeDAO.getInstance().getAll().get(i).getFirstName();
			empList.add(name);
		}} catch (DataException e) {
			// 
			e.printStackTrace();
		}
		
	}

	protected void populateStoreList() {
		int storeSize = 0;
		try {
			storeSize = StoreDAO.getInstance().getAll().size();
		for (int i=0; i<storeSize; i++){
			String location = StoreDAO.getInstance().getAll().get(i).getLocation();
			 storeList.add(location);
		}} catch (DataException e) {
			// 
			e.printStackTrace();
		}
		
	}
}
