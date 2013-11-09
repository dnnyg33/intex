package intex.GUI;


import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import intex.Cache;
import intex.ConnectionPool;
import intex.DataException;
import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.CProduct;
import intex.BusinessObjects.Employee;
import intex.BusinessObjects.PProduct;
import intex.BusinessObjects.Product;
import intex.BusinessObjects.Store;
import intex.BusinessObjects.StoreProduct;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**This window provides tools for managers to be able to add stores, products, and employees
 * 
 * @author Group 2-3
 *
 */
public class ManagerTools {


//TODO anytime there's a change, set it to dirty, or it won't go anywhere but cache.


	protected Shell shell;
	private Text txtStoreLocation;
	private Text txtStoreAddress;
	private Text txtSubnetId;
	private Text txtSalesTaxRate;
	private Text txtStorePhone;
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
	private Label lblerror;
	private Combo txtProductStore;
	private Text txtProductSku;
	private Text txtProductDescription;
	private Text txtProductModel;
	private Text txtProductSerial;
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
	private Button btnStoreSearch;
	private Button btnEmpSearch;
	private Button btnProductSearch;
	private Button btnResetAll;
	private Button btnTrackIndivdually;
	private boolean wantsPhysical=false;
	private java.util.List<Product> productMatches=new ArrayList<Product>();
	private java.util.List<Store> storeMatches=new ArrayList<Store>();
	private java.util.List<Employee> employeeMatches=new ArrayList<Employee>();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	//private String storeGuid;
	private boolean storeAlreadyInDb=false;
	//private String employeeGuid;
	private boolean employeeAlreadyInDb=false;
	//private String productGuid;
	private boolean productAlreadyInDb=false;
	Combo ddManagers=null;
	private Text txtProductcommrate;
	private Text txtTxtstorecity;
	private Text txtTxtstorestate;
	private Text txtTxtstorezip;
	private Label lblLocation;
	private Label label_5;
	private Label label_6;
	private Label lblSku;
	private Label lblDescription;
	private Label lblCommRate;
	private Label lblModel;
	private Label lblSerial;
	private Label lblAddress;
	private Label lblCity;
	private Label lblState;
	private Label lblZip;
	private Label lblPhone;
	private Label lblSalesTaxRate;
	private Label lblSubnetId;
	private Label lblManager;
	private Label label_7;
	private Label label_8;
	private Label label_9;
	private Label label_10;
	private Label label_11;
	private Label lblFirstName;
	private Label lblLastName;
	private Label lblPosition;
	private Label lblHireDate;
	private Label lblQuantityOnHand;
	private Label lblAvgCost;
	private Label lblSalePrice;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Button btnEmployeedelete;
	private Button btnProductdelete;
	private Button btnStoredelete;
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ManagerTools window = new ManagerTools();
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
		shell.setSize(794, 650);
		shell.setText("Manager Tools");
		
		txtStoreLocation = new Text(shell, SWT.BORDER);
		txtStoreLocation.setText("Location");
		txtStoreLocation.setBounds(75, 35, 139, 19);
		
		txtStoreAddress = new Text(shell, SWT.BORDER);
		txtStoreAddress.setText("Address");
		txtStoreAddress.setBounds(75, 60, 139, 19);
		
		txtSubnetId = new Text(shell, SWT.BORDER);
		txtSubnetId.setText("Subnet Id");
		txtSubnetId.setBounds(342, 85, 139, 19);
		
		txtSalesTaxRate = new Text(shell, SWT.BORDER);
		txtSalesTaxRate.setText("Sales Tax Rate");
		txtSalesTaxRate.setBounds(342, 60, 139, 19);
		
		txtStorePhone = new Text(shell, SWT.BORDER);
		txtStorePhone.setText("Phone");
		txtStorePhone.setBounds(342, 35, 139, 19);
		
		txtEmpHireDate = new Text(shell, SWT.BORDER);
		txtEmpHireDate.setText("Hire Date");
		txtEmpHireDate.setBounds(373, 336, 108, 19);
		
		Label lblStore = new Label(shell, SWT.NONE);
		lblStore.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		lblStore.setAlignment(SWT.CENTER);
		lblStore.setFont(SWTResourceManager.getFont("Lucida Grande", 22, SWT.BOLD));
		lblStore.setBounds(67, 2, 139, 27);
		lblStore.setText("Store");
		
		 storeList = new List(shell, SWT.V_SCROLL);
		 storeList.addSelectionListener(new SelectionAdapter() {
		 	@Override
		 	public void widgetSelected(SelectionEvent e) {//populate store fields when list item entry clicked
		 		
		 		showStoreInfo();
		 			
		 		
		 	}
		 });
		storeList.setBounds(495, 10, 166, 170);
	//	populateStoreList();
		
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 195, 471, 2);
		
		Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label_1.setBounds(487, 10, 2, 566);
		
		Label lblEmployee = new Label(shell, SWT.NONE);
		lblEmployee.setText("Employee");
		lblEmployee.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		lblEmployee.setFont(SWTResourceManager.getFont("Lucida Grande", 22, SWT.BOLD));
		lblEmployee.setAlignment(SWT.CENTER);
		lblEmployee.setBounds(67, 203, 139, 27);
		
		txtLastName = new Text(shell, SWT.BORDER);
		txtLastName.setText("Last Name");
		txtLastName.setBounds(77, 286, 166, 19);
		
		txtFirstName = new Text(shell, SWT.BORDER);
		txtFirstName.setText("First Name");
		txtFirstName.setBounds(77, 261, 166, 19);
		
		txtEmpAddress = new Text(shell, SWT.BORDER);
		txtEmpAddress.setText("Address");
		txtEmpAddress.setBounds(77, 311, 166, 19);
		
		txtEmpCity = new Text(shell, SWT.BORDER);
		txtEmpCity.setText("City");
		txtEmpCity.setBounds(77, 336, 166, 19);
		
		txtEmpState = new Text(shell, SWT.BORDER);
		txtEmpState.setText("State");
		txtEmpState.setBounds(373, 236, 108, 19);
		
		txtEmpZip = new Text(shell, SWT.BORDER);
		txtEmpZip.setText("Zip");
		txtEmpZip.setBounds(373, 261, 108, 19);
		
		txtEmpPhone = new Text(shell, SWT.BORDER);
		txtEmpPhone.setText("Phone");
		txtEmpPhone.setBounds(373, 286, 108, 19);
		
		txtEmpPosition = new Text(shell, SWT.BORDER);
		txtEmpPosition.setText("Position");
		txtEmpPosition.setBounds(373, 311, 108, 19);
		
		 empList = new List(shell, SWT.V_SCROLL);
		 empList.addSelectionListener(new SelectionAdapter() {//populate employee fields when list item clicked
		 	@Override
		 	public void widgetSelected(SelectionEvent e) {
		 		employeeAlreadyInDb=true;
		 		showEmployeeInfo();
		 			 	}
		 });
		empList.setBounds(495, 212, 166, 179);
	//	populateEmpList();
		
		
		btnNewStore = new Button(shell, SWT.NONE);
		btnNewStore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//New store button clicked
				
				//set all fields to blank
				txtStoreLocation.setText("Location");
				txtStoreAddress.setText("Address");
				txtSubnetId.setText("Subnet ID");
				txtSalesTaxRate.setText("Sales Tax Rate");
				txtStorePhone.setText("Phone");
				
				storeAlreadyInDb=false;
			//	storeGuid=null;
				ddManagers.setText(""); 
				
				
			}
		});
		btnNewStore.setBounds(82, 161, 101, 28);
		btnNewStore.setText("New Store");
		
		btnStoreSave = new Button(shell, SWT.NONE);
		btnStoreSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//store save button clicked
				Store s=null;
			if(storeAlreadyInDb==true){//get the item out of the list
				 s = storeMatches.get(storeList.getSelectionIndex());
				
			}else{//or create a new store object
				 try {
					s = BusinessObjectDAO.getInstance().create("Store");
				} catch (DataException e1) {
					System.out.println("Couldn't create store object");
					e1.printStackTrace();
				}
			}
			
			//grab info from GUI
			s.setDirty();
			s.setLocation(txtStoreLocation.getText());
			
			s.setAddress(txtStoreAddress.getText());
			s.setSubnetId(txtSubnetId.getText());
			try{
			s.setSalesTaxRate(Double.valueOf(txtSalesTaxRate.getText()));//TODO fix here for new stores (for nonNumber argument)
			}
			catch(Exception f){
				s.setSalesTaxRate(0);
			}
			s.setPhone(txtStorePhone.getText());
			String[] split = ddManagers.getText().split(" ");
			//System.out.println(split[1]);
			String fn = split[0];
			String ln = split[1];
			Employee manager=null;
			try {
				manager = BusinessObjectDAO.getInstance().searchForBO("Employee", new SearchCriteria("firstname", fn), new SearchCriteria("lastname",ln));
			} catch (DataException e2) {
				e2.printStackTrace();
			}
			
			
			s.setManagerId(manager.getId());
					
				//save the store object to the database
			try {
				s.save();
				System.out.println("save the store object to the DB");
			} catch (DataException e1) {
				
				e1.printStackTrace();
			}
					
				
				//show the object as first in the list
			//	storeList.add(s.getLocation(), 0);
			//	storeList.remove(storeList.getItem(storeList.getSelectionIndex()));//and remove the dud
				
				
				populateStoreList();
			}
		});
		btnStoreSave.setBounds(200, 161, 99, 28);
		btnStoreSave.setText("Save");
		
		label_2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(495, 195, 166, 2);
		
		btnNewEmployee = new Button(shell, SWT.NONE);
		btnNewEmployee.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//new Employee button clicked
				
				
				//set all fields to blank
				txtEmpLocation.setText("Location");
				txtLastName.setText("Last");
				txtFirstName.setText("First");
				txtEmpState.setText("State");
				txtEmpPhone.setText("Phone");
				txtEmpZip.setText("Zip");
				txtEmpHireDate.setText("Hire Date");
				txtEmpAddress.setText("Address");
				txtEmpPosition.setText("Position");
				txtEmpCity.setText("City");
				
				employeeAlreadyInDb=false;
			//	employeeGuid=null;
			//	ddManagers.setText(""); 
			}
		});
		btnNewEmployee.setText("New Employee");
		btnNewEmployee.setBounds(75, 371, 108, 28);
		
		btnEmpSave = new Button(shell, SWT.NONE);
		btnEmpSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e1) {//the employee save button is clicked
				
		
				Employee e=null;
				if(employeeAlreadyInDb==true){ //do an update with the same Guid
					if(empList.getSelectionCount()==0||empList.getSelectionCount()>1){
			lblerror.setText( "Please select one employee");
			lblerror.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					}else{
			
					e = employeeMatches.get(empList.getSelectionIndex());
					}
				}else{
					 try {
						e = BusinessObjectDAO.getInstance().create("Employee");
					} catch (DataException e11) {
						e11.printStackTrace();
					}
				}
				
				//grab info from GUI
			//	e.construct(firstName, lastName, hiredate, phone, storeId, username, password, salary, address, city, state, zip, position)
				e.setDirty();
				e.setFirstName(txtFirstName.getText()+"");
				e.setLastName(txtLastName.getText()+"");
				e.setAddress(txtEmpAddress.getText()+"");
				e.setPhone(txtEmpPhone.getText()+"");
				e.setPosition(txtEmpPosition.getText()+"");
				e.setCity(txtEmpCity.getText()+"");
				e.setAddress(txtEmpAddress.getText()+"");
				e.setState(txtEmpState.getText()+"");
				e.setZip(txtEmpZip.getText()+"");
				
				try {
					e.setHiredate(sdf.parse(txtEmpHireDate.getText()));
				} catch (ParseException e3) {
					
					e3.printStackTrace();
				}//TODO double check format
				
				
				Store s = null;
				try {
					s = BusinessObjectDAO.getInstance().searchForBO("Store", new SearchCriteria("location", txtEmpLocation.getText()));
				} catch (DataException e3) {
					
					e3.printStackTrace();
				}
				if(s!=null){
				e.setStoreId(s.getId());
				}
				
				
				try {
					e.save();
				} catch (DataException e2) {
					
					e2.printStackTrace();
				}
				empList.removeAll();
				populateEmpList();
		
			}
		});
		btnEmpSave.setText("Save ");
		btnEmpSave.setBounds(200, 371, 99, 28);
		
		txtEmpLocation = new Text(shell, SWT.BORDER);
		txtEmpLocation.setText("Location");
		txtEmpLocation.setBounds(77, 236, 166, 19);
		
		label_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setBounds(10, 405, 471, 2);
		
		label_4 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_4.setBounds(495, 405, 166, 2);
		
		lblProduct = new Label(shell, SWT.NONE);
		lblProduct.setText("Product");
		lblProduct.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		lblProduct.setFont(SWTResourceManager.getFont("Lucida Grande", 22, SWT.BOLD));
		lblProduct.setAlignment(SWT.CENTER);
		lblProduct.setBounds(67, 413, 139, 27);
		
		txtProductStore = new Combo(shell, SWT.BORDER);
		txtProductStore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//selected a store from the products panel. populates the QOH field.
		 		if(productAlreadyInDb==true){
		 		String storeLocation  = txtProductStore.getItem(txtProductStore.getSelectionIndex());
		 		Store s=null;
				try {
					s = BusinessObjectDAO.getInstance().searchForBO("Store", new SearchCriteria("location", storeLocation));
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				StoreProduct sp=null;
				Product p = productMatches.get(productList.getSelectionIndex());
				try {
					sp = BusinessObjectDAO.getInstance().searchForBO("StoreProduct", new SearchCriteria("storeid", 
							s.getId()), new SearchCriteria("productid", p.getId()));
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				txtProductCount.setText(sp.getQuantityOnHand()+"");
		 		}
			}
		});
		txtProductStore.setText("Store");
		txtProductStore.setBounds(75, 446, 118, 22);
		
		txtProductSku = new Text(shell, SWT.BORDER);
		txtProductSku.setText("SKU");
		txtProductSku.setBounds(75, 474, 118, 19);
		
		txtProductDescription = new Text(shell, SWT.BORDER);
		txtProductDescription.setText("Description");
		txtProductDescription.setBounds(289, 524, 192, 44);
		
		txtProductModel = new Text(shell, SWT.BORDER);
		txtProductModel.setText("Model");
		txtProductModel.setBounds(355, 449, 126, 19);
		
		txtProductSerial = new Text(shell, SWT.BORDER);
		txtProductSerial.setText("Serial");
		txtProductSerial.setBounds(75, 499, 118, 19);
		
		txtProductCost = new Text(shell, SWT.BORDER);
		txtProductCost.setText("Avg Cost");
		txtProductCost.setBounds(75, 524, 118, 19);
		
		txtProductSalePrice = new Text(shell, SWT.BORDER);
		txtProductSalePrice.setText("Sale Price");
		txtProductSalePrice.setBounds(75, 549, 118, 19);
		
		txtProductCount = new Text(shell, SWT.BORDER);
		txtProductCount.setText("Quantity On Hand");
		txtProductCount.setBounds(355, 499, 126, 19);
		
		productList = new List(shell, SWT.V_SCROLL);
		productList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//populate product fields when list item clicked
				productAlreadyInDb=true;
				btnTrackIndivdually.setEnabled(false);
				showProductInfo();
				
		}
		});
		productList.setBounds(495, 421, 166, 147);
		
		btnNewProduct = new Button(shell, SWT.NONE);
		btnNewProduct.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//new Product Button pushed
				//TODO last part
				productAlreadyInDb=false;
				btnTrackIndivdually.setEnabled(true);
				
				
				//set all fields to blank
				String ty ="";
				txtProductSku.setText(ty);
				txtProductStore.setText(ty);
				txtProductDescription.setText(ty);
				txtProductModel.setText(ty);
				txtProductCost.setText(ty);
				txtProductCount.setText(ty);
				txtProductSalePrice.setText(ty);
				txtProductcommrate.setText(ty);
				
				java.util.List<Store> stores=null;
				try {
					stores = BusinessObjectDAO.getInstance().searchForList("Store");
				} catch (DataException e1) {
					System.out.println("couldn't get store list");
					e1.printStackTrace();
				}
				Connection conn =null;
				try {
					 conn = ConnectionPool.getInstance().get();
				} catch (DataException e1) {
					
					e1.printStackTrace();
				}
				
				for(Store s: stores){
					try {
						BusinessObjectDAO.getInstance().initialize(s, Store.class, conn);
					} catch (DataException e1) {
						
						e1.printStackTrace();
					} catch (Exception e1) {
						
						e1.printStackTrace();
					}
				txtProductStore.add(s.getLocation());
				}
				try {
					ConnectionPool.getInstance().release(conn);
				} catch (DataException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewProduct.setText("New Product");
		btnNewProduct.setBounds(77, 574, 106, 28);
		
		btnProductSave = new Button(shell, SWT.NONE);
		btnProductSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//the product save button is clicked
				
				Product p=null;
				PProduct pp = null;
				CProduct cp = null;
				if(productAlreadyInDb==true){//search for the object instead of creating it new
					 p=productMatches.get(productList.getSelectionIndex());
					p.setDirty();
					p.setCProduct(p.getCProductId());
						p.setModel(txtProductModel.getText());
						p.setDescription(txtProductDescription.getText());
						try{p.setCost(Double.valueOf(txtProductCost.getText()));}catch(Exception g){p.setCost(0.0);}
						try{p.setCommissionRate(Double.valueOf(txtProductcommrate.getText()));}catch(Exception g){p.setCommissionRate(0.0);}
						try{p.setPrice(Double.valueOf(txtProductSalePrice.getText()));}catch(Exception g){p.setPrice(0);}
						
						p.setSerialNum(txtProductSerial.getText());
						try {
							p.save();
						} catch (DataException e1) {
							e1.printStackTrace();
						}
						populateProductList();
				}else{//create the object instead of searching for it
					if(wantsPhysical==false){//if it's new and they want a physical, then first search for a matching conceptual, else create.
					try {
					 pp =BusinessObjectDAO.getInstance().create("PProduct");
					  cp=BusinessObjectDAO.getInstance().searchForBO("CProduct", new SearchCriteria("sku", txtProductSku.getText()));//search from sku for already existing CP, else create CP
					 if(cp==null){
					 cp = BusinessObjectDAO.getInstance().create("CProduct");
					 }
					//setters on the cp
					 System.out.println("cp.getId-->"+cp.getId());
						cp.setModel(txtProductModel.getText()+"");
						cp.setDescription(txtProductDescription.getText()+"");
						try{cp.setCost(Double.valueOf(txtProductCost.getText()));}catch(Exception g){cp.setCost(0.0);}
						try{cp.setCommissionRate(Double.valueOf(txtProductcommrate.getText()));}catch(Exception g){cp.setCommissionRate(0.0);}
						try{cp.setPrice(Double.valueOf(txtProductSalePrice.getText()));}catch(Exception g){cp.setPrice(0);}
							
						cp.save();
					 
					 //setters on the pp
					pp.setModel(txtProductModel.getText());
					try{pp.setCost(Double.valueOf(txtProductCost.getText()));}catch(Exception g){pp.setCost(0);}
					pp.setSerialNum(txtProductSerial.getText());
					try{pp.setPrice(Double.valueOf(txtProductSalePrice.getText()));}catch(Exception g){pp.setPrice(0);}
					pp.setCProductId(cp.getId());
					pp.save();
					
					
					
					} catch (DataException e1) {
						e1.printStackTrace();
					}//create new store GUID 
					}else{
						try {
						 cp =	BusinessObjectDAO.getInstance().create("CProduct");
						 cp.setModel(txtProductModel.getText());
							cp.setDescription(txtProductDescription.getText());
							try{cp.setCost(Double.valueOf(txtProductCost.getText()));}catch(Exception g){cp.setCost(0.0);}
							try{cp.setCommissionRate(Double.valueOf(txtProductcommrate.getText()));}catch(Exception g){cp.setCommissionRate(0.0);}
							try{cp.setPrice(Double.valueOf(txtProductSalePrice.getText()));}catch(Exception g){cp.setPrice(0);}
							
						cp.save();
					//	productList.add(p.getModel(), 0);
						} catch (DataException e1) {
							
							e1.printStackTrace();
						}
					}
					
				}
				
		}
		});
		btnProductSave.setText("Save ");
		btnProductSave.setBounds(200, 574, 99, 28);
		
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
		txtStoreSearch.setBounds(667, 60, 117, 19);
		
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
		txtEmployeeSearch.setBounds(667, 240, 117, 19);
		
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
		txtProductSearch.setText("Model Name");
		txtProductSearch.setBounds(667, 437, 117, 19);
		
		btnStoreSearch = new Button(shell, SWT.NONE);
		btnStoreSearch.addSelectionListener(new SelectionAdapter() {//store search button clicked
			@Override
			public void widgetSelected(SelectionEvent e) {
				storeMatches.clear();
				try {
					storeMatches = BusinessObjectDAO.getInstance().searchForList("Store", new SearchCriteria("location", txtStoreSearch.getText()));
				} catch (DataException e1) {
					
					e1.printStackTrace();
				}
				
				storeList.removeAll();
				for(Store s: storeMatches){
				storeList.add(s.getLocation());
				}
				if(storeList.getItemCount()==1){
					storeList.select(0);
					showStoreInfo();
				}
				
			}
		});
		btnStoreSearch.setBounds(677, 81, 94, 28);
		btnStoreSearch.setText("Search");
		
		btnEmpSearch = new Button(shell, SWT.NONE);
		btnEmpSearch.addSelectionListener(new SelectionAdapter() {//employee search button clicked
			@Override
			public void widgetSelected(SelectionEvent e) {
				employeeMatches.clear();
				try {
					employeeMatches = BusinessObjectDAO.getInstance().searchForList
							("Employee", new SearchCriteria("lastname",txtEmployeeSearch.getText()));
				} catch (DataException e1) {
					System.out.println("coudn't find any employeeMatches");
					e1.printStackTrace();
				}
				
				empList.removeAll();
				for(Employee s: employeeMatches){
				empList.add(s.getFirstName()+" "+s.getLastName());
				}
				
				
				if(empList.getItemCount()==1){
					empList.select(0);
					showEmployeeInfo();
				}
				
			}
		});
		btnEmpSearch.setText("Search");
		btnEmpSearch.setBounds(677, 261, 94, 28);
		
		btnProductSearch = new Button(shell, SWT.NONE);
		btnProductSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {// product search button is clicked.
				
				productMatches.clear();
				try {
					productMatches = BusinessObjectDAO.getInstance().searchForList("Product", new SearchCriteria("model", txtProductSearch.getText()));//this might cause a bunch of null pointers.
				} catch (DataException e1) {
					
					e1.printStackTrace();
				}
				
				productList.removeAll();
				for(Product p: productMatches){
				productList.add(p.getModel());
				}
				
				if(productList.getItemCount()==1){
					productList.select(0);
					showProductInfo();
				}
				
			}
		});
		btnProductSearch.setText("Search");
		btnProductSearch.setBounds(677, 462, 94, 28);
		
		btnResetAll = new Button(shell, SWT.NONE);
		btnResetAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				populateStoreList();
				populateEmpList();
				populateProductList();
			}
		});
		btnResetAll.setBounds(677, 10, 94, 28);
		btnResetAll.setText("Reset All");
		ddManagers = new Combo(shell, SWT.NONE);
		 	java.util.List<Employee> managers=null;
			try {
				managers = BusinessObjectDAO.getInstance().searchForList("Employee",  new SearchCriteria("position", "Manager"));
				} catch (DataException e1) {
					System.out.println("can't get manager");
					e1.printStackTrace();
				}
			for(Employee emp: managers){
				
			ddManagers.add(emp.getFirstName()+" "+ emp.getLastName());
			}
		
		ddManagers.setBounds(342, 110, 139, 22);
		
		 btnTrackIndivdually = new Button(shell, SWT.CHECK);
		btnTrackIndivdually.setEnabled(false);
		btnTrackIndivdually.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(txtProductSerial.getText().isEmpty()){
					txtProductSerial.setText("Serial");
					wantsPhysical=false;
				}else{
				txtProductSerial.setText("");
				wantsPhysical = true;
				}
			}
		});
		btnTrackIndivdually.setBounds(320, 421, 161, 18);
		btnTrackIndivdually.setText("Track Indivdually");
		
		txtProductcommrate = new Text(shell, SWT.BORDER);
		txtProductcommrate.setText("Comm Rate");
		txtProductcommrate.setBounds(355, 474, 126, 19);
		
		Label lblYyyymmdd = new Label(shell, SWT.NONE);
		lblYyyymmdd.setFont(SWTResourceManager.getFont("Lucida Grande", 8, SWT.NORMAL));
		lblYyyymmdd.setBounds(422, 359, 59, 14);
		lblYyyymmdd.setText("yyyy-mm-dd");
		
		txtTxtstorecity = new Text(shell, SWT.BORDER);
		txtTxtstorecity.setText("City");
		txtTxtstorecity.setBounds(75, 85, 139, 19);
		
		txtTxtstorestate = new Text(shell, SWT.BORDER);
		txtTxtstorestate.setText("State");
		txtTxtstorestate.setBounds(75, 110, 64, 19);
		
		txtTxtstorezip = new Text(shell, SWT.BORDER);
		txtTxtstorezip.setText("Zip");
		txtTxtstorezip.setBounds(75, 135, 64, 19);
		
		lblLocation = new Label(shell, SWT.NONE);
		lblLocation.setAlignment(SWT.RIGHT);
		lblLocation.setBounds(10, 38, 59, 14);
		lblLocation.setText("Location");
		
		label_5 = new Label(shell, SWT.NONE);
		label_5.setAlignment(SWT.RIGHT);
		label_5.setText("Location");
		label_5.setBounds(10, 239, 59, 14);
		
		label_6 = new Label(shell, SWT.NONE);
		label_6.setAlignment(SWT.RIGHT);
		label_6.setText("Location");
		label_6.setBounds(10, 450, 59, 14);
		
		lblSku = new Label(shell, SWT.NONE);
		lblSku.setAlignment(SWT.RIGHT);
		lblSku.setBounds(10, 477, 59, 14);
		lblSku.setText("SKU");
		
		lblDescription = new Label(shell, SWT.NONE);
		lblDescription.setAlignment(SWT.RIGHT);
		lblDescription.setBounds(207, 527, 76, 14);
		lblDescription.setText("Description");
		
		lblCommRate = new Label(shell, SWT.NONE);
		lblCommRate.setAlignment(SWT.RIGHT);
		lblCommRate.setBounds(255, 477, 94, 14);
		lblCommRate.setText("Comm Rate %");
		
		lblModel = new Label(shell, SWT.NONE);
		lblModel.setAlignment(SWT.RIGHT);
		lblModel.setBounds(312, 453, 37, 14);
		lblModel.setText("Model");
		
		lblSerial = new Label(shell, SWT.NONE);
		lblSerial.setAlignment(SWT.RIGHT);
		lblSerial.setBounds(32, 502, 37, 14);
		lblSerial.setText("Serial");
		
		lblAddress = new Label(shell, SWT.NONE);
		lblAddress.setAlignment(SWT.RIGHT);
		lblAddress.setBounds(10, 63, 59, 14);
		lblAddress.setText("Address");
		
		lblCity = new Label(shell, SWT.NONE);
		lblCity.setAlignment(SWT.RIGHT);
		lblCity.setBounds(10, 85, 59, 14);
		lblCity.setText("City");
		
		lblState = new Label(shell, SWT.NONE);
		lblState.setAlignment(SWT.RIGHT);
		lblState.setBounds(10, 110, 59, 14);
		lblState.setText("State");
		
		lblZip = new Label(shell, SWT.NONE);
		lblZip.setAlignment(SWT.RIGHT);
		lblZip.setBounds(15, 138, 59, 14);
		lblZip.setText("Zip");
		
		lblPhone = new Label(shell, SWT.NONE);
		lblPhone.setAlignment(SWT.RIGHT);
		lblPhone.setBounds(277, 38, 59, 14);
		lblPhone.setText("Phone");
		
		lblSalesTaxRate = new Label(shell, SWT.NONE);
		lblSalesTaxRate.setAlignment(SWT.RIGHT);
		lblSalesTaxRate.setBounds(230, 63, 108, 14);
		lblSalesTaxRate.setText("Sales Tax Rate %");
		
		lblSubnetId = new Label(shell, SWT.NONE);
		lblSubnetId.setAlignment(SWT.RIGHT);
		lblSubnetId.setBounds(250, 88, 86, 14);
		lblSubnetId.setText("Subnet ID");
		
		lblManager = new Label(shell, SWT.NONE);
		lblManager.setAlignment(SWT.RIGHT);
		lblManager.setBounds(277, 114, 59, 14);
		lblManager.setText("Manager");
		
		label_7 = new Label(shell, SWT.NONE);
		label_7.setText("Address");
		label_7.setAlignment(SWT.RIGHT);
		label_7.setBounds(10, 314, 59, 14);
		
		label_8 = new Label(shell, SWT.NONE);
		label_8.setText("City");
		label_8.setAlignment(SWT.RIGHT);
		label_8.setBounds(10, 339, 59, 14);
		
		label_9 = new Label(shell, SWT.NONE);
		label_9.setText("State");
		label_9.setAlignment(SWT.RIGHT);
		label_9.setBounds(308, 236, 59, 14);
		
		label_10 = new Label(shell, SWT.NONE);
		label_10.setText("Phone");
		label_10.setAlignment(SWT.RIGHT);
		label_10.setBounds(308, 289, 59, 14);
		
		label_11 = new Label(shell, SWT.NONE);
		label_11.setText("Zip");
		label_11.setAlignment(SWT.RIGHT);
		label_11.setBounds(308, 264, 59, 14);
		
		lblFirstName = new Label(shell, SWT.NONE);
		lblFirstName.setAlignment(SWT.RIGHT);
		lblFirstName.setBounds(0, 264, 69, 14);
		lblFirstName.setText("First Name");
		
		lblLastName = new Label(shell, SWT.NONE);
		lblLastName.setAlignment(SWT.RIGHT);
		lblLastName.setBounds(0, 289, 69, 14);
		lblLastName.setText("Last Name");
		
		lblPosition = new Label(shell, SWT.NONE);
		lblPosition.setAlignment(SWT.RIGHT);
		lblPosition.setBounds(308, 314, 59, 14);
		lblPosition.setText("Position");
		
		lblHireDate = new Label(shell, SWT.NONE);
		lblHireDate.setAlignment(SWT.RIGHT);
		lblHireDate.setBounds(308, 339, 59, 14);
		lblHireDate.setText("Hire Date");
		
		lblQuantityOnHand = new Label(shell, SWT.NONE);
		lblQuantityOnHand.setAlignment(SWT.RIGHT);
		lblQuantityOnHand.setBounds(241, 502, 108, 14);
		lblQuantityOnHand.setText("Quantity on Hand");
		
		lblAvgCost = new Label(shell, SWT.NONE);
		lblAvgCost.setAlignment(SWT.RIGHT);
		lblAvgCost.setBounds(10, 527, 59, 14);
		lblAvgCost.setText("Avg Cost");
		
		lblSalePrice = new Label(shell, SWT.NONE);
		lblSalePrice.setAlignment(SWT.RIGHT);
		lblSalePrice.setBounds(10, 552, 59, 14);
		lblSalePrice.setText("Sale Price");
		
		 lblerror = new Label(shell, SWT.NONE);
		 lblerror.setFont(SWTResourceManager.getFont("Lucida Grande", 16, SWT.NORMAL));
		 lblerror.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		 lblerror.setAlignment(SWT.CENTER);
		 lblerror.setBounds(422, 591, 347, 27);
		 
		 btnEmployeedelete = new Button(shell, SWT.NONE);
		 btnEmployeedelete.setBounds(322, 371, 94, 28);
		 formToolkit.adapt(btnEmployeedelete, true, true);
		 btnEmployeedelete.setText("Delete");
		 
		 btnProductdelete = new Button(shell, SWT.NONE);
		 btnProductdelete.addSelectionListener(new SelectionAdapter() {
		 	@Override
		 	public void widgetSelected(SelectionEvent e) {
		 		if(productList.getSelectionCount()==1){//if only one is selected..delete
		 			lblerror.setText("");
		 			lblerror.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		 		}else{
		 			lblerror.setText("Please select a Product from the List");
		 			lblerror.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		 		}
		 	}
		 });
		 btnProductdelete.setBounds(320, 574, 94, 28);
		 formToolkit.adapt(btnProductdelete, true, true);
		 btnProductdelete.setText("Delete");
		 
		  btnStoredelete = new Button(shell, SWT.NONE);
		  btnStoredelete.setText("Delete");
		 btnStoredelete.setBounds(320, 161, 96, 28);
		
	//	Cache.getInstance().clear();
		populateStoreList();
	//	Cache.getInstance().clear();
		populateEmpList();
	//	Cache.getInstance().clear();
		populateProductList();

	}
	//TODO take out useless println's
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////END CREATE CONTENTS
	
	private void showProductInfo(){
		productAlreadyInDb=true;//determines if hitting save button sends object through update or insert
 		
 		Product p = productMatches.get(productList.getSelectionIndex());//let's assume these are the same index
 		txtProductModel.setText(p.getModel()+"");
 		 
 		if(p.getTypeOf().equalsIgnoreCase("forRent")|| p.getTypeOf().equalsIgnoreCase("forsale")){//TODO this may be broken
 			p.setCProduct(p.getCProductId());
 			
 		}
 			//fill in GUI 
 		//SKU, Serial, description, model, avgCost, salePrice, QOH, commRate
 		
 		
 		//txtProductSku.setText(p.getSku()+"");
		txtProductSalePrice.setText(p.getPrice()+"");//product.getPrice()
		txtProductSerial.setText(p.getSerialNum()+"");//Physical.getSerial() || "null"
		txtProductDescription.setText(p.getDescription()+"");//conceptual.getDescription || physical.getCProduct.getDescription
		txtProductCost.setText(p.getCost()+"");//physical.getCost() ||conceptual.getCost()--returns avgCost
		txtProductcommrate.setText(p.getFullCommissionRate()+"");//both

 		
 		java.util.List<Store>stores = new ArrayList<Store>();
 		try {
			stores = p.getDealers();
		} catch (DataException e1) {
			System.out.println("ManagerTools.showProductInfo: couldn't find stores.");
			e1.printStackTrace();
		}
 		txtProductStore.removeAll();
 		for(int i=0; i<stores.size(); i++){
 			txtProductStore.add(stores.get(i).getLocation());//put the locations of the stores where this product is sold in the drop down box
 		}
 		if(txtProductStore.getItemCount()==1){//if there's only 1 item in the list, then show the quantity for that store
 			txtProductStore.select(0);
 		
 		
 		String storeLocation  = txtProductStore.getItem(txtProductStore.getSelectionIndex());
 		Store s=null;
		try {
			s = BusinessObjectDAO.getInstance().searchForBO("Store", new SearchCriteria("location", storeLocation));
		} catch (DataException e1) {
			e1.printStackTrace();
		}
		StoreProduct sp=null;
		try {
			sp = BusinessObjectDAO.getInstance().searchForBO("StoreProduct", new SearchCriteria("storeid", s.getId()), new SearchCriteria("productid", p.getId()));
		} catch (DataException e1) {
			e1.printStackTrace();
		}
		txtProductCount.setText(sp.getQuantityOnHand()+"");
		
 		}else{
 			txtProductCount.setText("Select A Store");
 		}
		
	}
	
	
	
	private void showEmployeeInfo(){
		employeeAlreadyInDb=true;
		
		String wholeName = empList.getItem(empList.getSelectionIndex());
		
 		String[] split = wholeName.split(" ");//string that is selected in the list
 		 
 		String firstName = split[0];
 		String lastName = split[1];
 		
 		
 		Employee emp=null;
		try {
			emp = BusinessObjectDAO.getInstance().searchForBO("Employee", new SearchCriteria("lastname", lastName), 
					new SearchCriteria("firstname", firstName));
		}
		 catch (DataException e1) {
			
			e1.printStackTrace();
		}
		//employeeGuid = emp.getId();//i don't need this because i'm just looking in the list later, instead of querying the DB
 		txtFirstName.setText(emp.getFirstName()+"");
 		txtEmpPosition.setText(emp.getPosition()+"");
		txtLastName.setText(emp.getLastName()+"");
		txtEmpPhone.setText(emp.getPhone()+"");
		txtEmpAddress.setText(emp.getAddress()+"");
		txtEmpCity.setText(emp.getCity()+"");
		txtEmpState.setText(emp.getState()+"");
		txtEmpZip.setText(emp.getZip()+"");
		try{
		txtEmpHireDate.setText(sdf.format(emp.getHireDate()));}
		catch (Exception e){
			txtEmpHireDate.setText("?");
		}
		if(emp.getStoreId()!=null){
		Store s=null;
		try {
			s = BusinessObjectDAO.getInstance().searchForBO("Store", new SearchCriteria("id", emp.getStoreId()));
			Connection conn = ConnectionPool.getInstance().get();
			BusinessObjectDAO.getInstance().initialize(s, Store.class, conn );
			ConnectionPool.getInstance().release(conn);
			
		} catch (DataException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		 
		 
		txtEmpLocation.setText(s.getLocation()+"");
		}else{txtEmpLocation.setText("null");}
		
	}
	private void showStoreInfo(){
		storeAlreadyInDb=true;//for use in determining whether clicking the save button will do an insert or update
 		Store s = storeMatches.get(storeList.getSelectionIndex());
 		
		//storeGuid=s.getId();//for use when clicking save so that the DAO updates the tuple instead of duplicates it
		
 		txtStoreLocation.setText(s.getLocation());
		txtStoreAddress.setText(s.getAddress());
		txtSubnetId.setText(s.getSubnetId());
		txtSalesTaxRate.setText(s.getSalesTaxRate()+"");
		txtStorePhone.setText(s.getPhone());
		Employee manager=null;
		try {
			manager = BusinessObjectDAO.getInstance().read(s.getManagerId());
		} catch (DataException e) {
			e.printStackTrace();
		}
		
				
		ddManagers.setText(manager.getFirstName()+" "+manager.getLastName());
		
	}

	private void populateProductList() {
		productList.removeAll();
		productMatches.clear();
		try {
			productMatches = BusinessObjectDAO.getInstance().searchForAll("Product");
		for (int i=0; i<productMatches.size(); i++){
			String productName = productMatches.get(i).getModel()+" "+productMatches.get(i).getSerialNum();
			 productList.add(productName);
		}} catch (DataException e) {
			// 
			e.printStackTrace();
		}
		
	}

	private void populateEmpList() {
		
		empList.removeAll();
			employeeMatches.clear();
		
		try {
			Cache.getInstance().clear();
			employeeMatches = BusinessObjectDAO.getInstance().searchForAll("Employee");
		for (int i=0; i<employeeMatches.size(); i++){
			String name = employeeMatches.get(i).getFirstName() +" "+ employeeMatches.get(i).getLastName();
			empList.add(name);
		}} catch (DataException e) {
			// 
			e.printStackTrace();
		}
	}

	protected void populateStoreList() {//populate store list
		storeList.removeAll();
		storeMatches.clear();
		try {
			Cache.getInstance().clear();
			storeMatches = BusinessObjectDAO.getInstance().searchForAll("Store");
			

		} catch (DataException e) {
			e.printStackTrace();
		}for (Store s: storeMatches){
			 
			 storeList.add(s.getLocation());}
		
	}
}
