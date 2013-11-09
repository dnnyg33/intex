package intex.GUI;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import intex.DataException;
import intex.SearchCriteria;

import intex.BusinessObjects.*;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.*;

import swing2swt.layout.*;

import org.eclipse.swt.widgets.*;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.program.Program;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

import edu.byu.isys413.swt.manager.WindowManager;

/**This is the main window of the application. All functions and windows can be opened from this window.
 * In order to access this class, an employee must log in first
 * 
 * @author Group 2-3
 * */
public class TransactionWindow {//TODO change Manager tools to be disabled unless manager signs in

	public  Shell shell;
	static Shell loginShell= new Shell();
	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());
	private Text txtSubtotal;
	private Text txtTax;
	private Text txtTotal;
	private Text txtTaxrate;
	private double subtotal=0;
	private double taxrate=0;
	private double tax=0;
	private double total=0;

	private Text txtSubtotalLabel;
	private Text txtTaxratelabel;
	private Text txtTaxLabel;
	private Text txtTotalLabel;
	private Text lblSerial;
	private Text txtSerialin;
	private Text txtSku;
	private Text txtSkuin;
	private java.util.List<RevenueSource>saleItems = new ArrayList<RevenueSource>();
	private TableViewerColumn colSku;
	private TableViewerColumn colSerial;
	private TableViewerColumn colProduct;
	private TableViewerColumn colPrice;
	private TableViewerColumn colQuantity;
	//private TableViewerColumn colExt;// not sure what to do with this.
//	 TransactionWindow window = new TransactionWindow();
//	 LoginDialog l = new LoginDialog(loginShell, 0);
	private TableViewer tableViewer;
	private Table rsTable;
	private Trans t=null;

	private double rentalPrice=123;
	protected Employee employee;
	
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			
//			window.open();
//			
////			if (!l.isValid()){
////				shell.close();
////				}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

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
		Display display = Display.getDefault();
		
		this.employee=employee;
		
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
		
		
		try {
			 t = BusinessObjectDAO.getInstance().create("Trans");
		} catch (DataException e2) {
			e2.printStackTrace();
		}
		String macAddress = getMacAddress();
		System.out.println(macAddress);
		Store s = null;
		try{
			 s = BusinessObjectDAO.getInstance().searchForBO("Store", new SearchCriteria("subnetid", macAddress));
			 t.setStore(s.getId());//this is determined by mac address
			 t.setStoreId(s.getId());
		
		}catch(Exception e){
			t.setStore("store1");//Sandy is default store
			t.setStoreId("store1");
		}
		
		
		t.setCustId("cust1");
		try{
			t.setEmpId(employee.getId());//get the employee who logged in	
		}
		catch(Exception e){
			System.out.println("Login not performed. Running from main. logging in manager.");
			try {
				employee = BusinessObjectDAO.getInstance().read(t.getStore().getManagerId());
			} catch (DataException e1) {
				e1.printStackTrace();
			}
		}
		
		
		
		shell = new Shell();
		shell.setSize(1007, 601);
		shell.setText("MyStuff POS - "+s.getLocation());
		shell.setLayout(new BorderLayout(0, 0));

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmNewCustomer = new MenuItem(menu_1, SWT.NONE);
		mntmNewCustomer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NewCustomer window = new NewCustomer();
				
				window.open();
			}
		});
		mntmNewCustomer.setText("New Customer");

		MenuItem mntmLookUpCustomer = new MenuItem(menu_1, SWT.NONE);
		mntmLookUpCustomer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			LookupCustomer window = new LookupCustomer();
			window.open();
			}
		});
		mntmLookUpCustomer.setText("Look Up Customer");

		MenuItem mntmProfile = new MenuItem(menu, SWT.CASCADE);
		mntmProfile.setText("Profile");

		Menu menu_2 = new Menu(mntmProfile);
		mntmProfile.setMenu(menu_2);

		MenuItem mntmChangeUsername = new MenuItem(menu_2, SWT.NONE);
		mntmChangeUsername.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//change username or password clicked
				new ChangeUsernameOrPassword().open(employee);
			}
		});
		mntmChangeUsername.setText("Change Username or Password");

		MenuItem mntmCommissions_1 = new MenuItem(menu_2, SWT.NONE);
		mntmCommissions_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				printCommissions(false);
			}
		});
		mntmCommissions_1.setText("Print My Commissions");

		MenuItem mntmManagerTools = new MenuItem(menu, SWT.CASCADE);
		mntmManagerTools.setText("Tools");

		Menu menu_3 = new Menu(mntmManagerTools);
		mntmManagerTools.setMenu(menu_3);
		
		MenuItem mntmAccounting = new MenuItem(menu_3, SWT.NONE);
		mntmAccounting.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GeneralLedgerWindow window = new GeneralLedgerWindow();
				window.open();
			}
		});
		mntmAccounting.setText("Accounting");

		MenuItem mntmCommissions = new MenuItem(menu_3, SWT.NONE);
		mntmCommissions.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				CommissionsWindow window = new CommissionsWindow();
//				window.open();
				printCommissions(true);
			}
		});
		mntmCommissions.setText("Print All Commissions");
		mntmCommissions.setEnabled(false);
		if(employee.getId().equalsIgnoreCase(s.getManagerId())){
			mntmCommissions.setEnabled(true);
		}
	
		
		MenuItem mntmManagerSummary = new MenuItem(menu_3, SWT.NONE);
		mntmManagerSummary.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				ManagerTools window = new ManagerTools();
				window.open();
			}
		});
		mntmManagerSummary.setText("Manager Summary");
		mntmManagerSummary.setEnabled(false);
		if(employee.getId().equalsIgnoreCase(s.getManagerId())){
			mntmManagerSummary.setEnabled(true);
		}
		
		MenuItem mntmRentalEmailBatch = new MenuItem(menu_3, SWT.NONE);
		mntmRentalEmailBatch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new RentalEmailBatchGUI().open();
			}
		});
		mntmRentalEmailBatch.setText("Rental Email Batch");
		
		MenuItem mntmRentalReturn = new MenuItem(menu_3, SWT.NONE);
		mntmRentalReturn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Fee fee = new ReturnRentalWindow().open();
				if(fee!=null){
				
				t.setCustId(fee.getRental().getTrans().getCustId());
				t.setEmpId(fee.getRental().getTrans().getEmpId());
				
				
				fee.setEmpId(t.getEmpId());//whoever the employee logged in is
				fee.setTransId(t.getId());//whatever the transId is for this trans
				
				try {
					fee.save();
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				
				saleItems.add(fee);
				tableViewer.setInput(saleItems);
				calculateTotals();
				
				}
			}
		});
		mntmRentalReturn.setText("Rental Return");
		TableLayout tLayout = new TableLayout();
		tLayout.addColumnData(new ColumnWeightData(100));

		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.SOUTH);
		composite_1.setLayout(new FormLayout());

		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.EAST);

		Composite composite_3 = new Composite(shell, SWT.NONE);
		composite_3.setLayoutData(BorderLayout.WEST);

		Composite composite = new Composite(shell, SWT.NONE);//center comp
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new FormLayout());
		
	//	composite.setLayout(new GridLayout(2, false));

		Composite composite_4 = new Composite(composite, SWT.NONE);//comp inside center comp
		TableColumnLayout tcl_composite_4 = new TableColumnLayout();
		composite_4.setLayout(tcl_composite_4);
		FormData fd_composite_4 = new FormData();
		fd_composite_4.top = new FormAttachment(0, 57);
		fd_composite_4.left = new FormAttachment(0, 10);
		fd_composite_4.right = new FormAttachment(0, 869);
		composite_4.setLayoutData(fd_composite_4);

		tableViewer = new TableViewer(composite_4, SWT.BORDER | SWT.FULL_SELECTION);
		rsTable = tableViewer.getTable();
//		
		GridData gd_table_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_table_1.heightHint = 130;
		gd_table_1.widthHint = 448;
		rsTable.setLayoutData(gd_table_1);
//		
		rsTable.setHeaderVisible(true);
		rsTable.setLinesVisible(true);
		
		
		colSku = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnSku = colSku.getColumn();
		tblclmnSku.setWidth(100);
		tcl_composite_4.setColumnData(tblclmnSku, new ColumnPixelData(150, true, true));
		tblclmnSku.setText("SKU/Revenue Type");
		
		colSerial = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnSerial = colSerial.getColumn();
		tblclmnSerial.setWidth(100);
		tcl_composite_4.setColumnData(tblclmnSerial, new ColumnPixelData(150, true, true));
		tblclmnSerial.setText("Serial #");
		
		colProduct= new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnProduct = colProduct.getColumn();
		tblclmnProduct.setWidth(100);
		tcl_composite_4.setColumnData(tblclmnProduct, new ColumnPixelData(150, true, true));
		tblclmnProduct.setText("Model");
		
		colPrice= new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPrice = colPrice.getColumn();
		tblclmnPrice.setWidth(200);
		tcl_composite_4.setColumnData(tblclmnPrice, new ColumnPixelData(150, true, true));
		tblclmnPrice.setText("Price");
		
		colQuantity= new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnQuantity = colQuantity.getColumn();
		tblclmnQuantity.setWidth(200);
		tcl_composite_4.setColumnData(tblclmnQuantity, new ColumnPixelData(150, true, true));
		tblclmnQuantity.setText("Quantity");
	
		
		
		colPrice.setLabelProvider(new ColumnLabelProvider() {//Price column
			public String getText(Object element) {
			RevenueSource p = (RevenueSource) element;
				return p.getChargeAmt()+"";
			
			}
			});
		
		
		colSku.setLabelProvider(new ColumnLabelProvider() {//SKU column
		public String getText(Object element) {
			RevenueSource p = (RevenueSource) element;
		//	Sale s = (Sale) p;
		//	return	p.getSkuOrRevenueType();
			if (p.getTypeOf().equalsIgnoreCase("sale")){
				Sale s = (Sale) p;
				return s.getProduct().getSku();
			}else{
				//Rental s = (Rental) p;
				return p.getSkuOrRevenueType();
			}
			
		}
		});
		
		colSerial.setLabelProvider(new ColumnLabelProvider() {//Serial column
		public String getText(Object element) {
			RevenueSource p = (RevenueSource) element;
			if (p.getTypeOf().equalsIgnoreCase("sale")){
				Sale s = (Sale) p;
				return s.getProduct().getSerialNum();
			}else if(p.getTypeOf().equalsIgnoreCase("fee")){
				Fee f = (Fee) p;
				return f.getRental().getForRent().getSerialNum();
			}else{
				Rental s = (Rental) p;
				return s.getForRent().getSerial();
			}
		}
		
		});
		
		colProduct.setLabelProvider(new ColumnLabelProvider() {//Product/model column
			public String getText(Object element) {
				RevenueSource p = (RevenueSource) element;
				if (p.getTypeOf().equalsIgnoreCase("sale")){
					Sale s = (Sale) p;
					return s.getProduct().getModel();
				}else if(p.getTypeOf().equalsIgnoreCase("rental")){
					Rental s = (Rental) p;
					return s.getForRent().getModel();
				}else{
					Fee f = (Fee) p;
					return f.getRental().getForRent().getModel();
				}
				
				
			}
			});
		
		colQuantity.setLabelProvider(new ColumnLabelProvider() {//Quantity Column
			public String getText(Object element) {
				RevenueSource p = (RevenueSource) element;
				if (p.getTypeOf().equalsIgnoreCase("sale")){
					Sale s = (Sale) p;
					return s.getQuantity()+"";
				}else 
					if (p.getTypeOf().equalsIgnoreCase("fee")){
					
						Fee f = (Fee) p;
						if(f.isWaived()){
							return 0+" - Waived";
						}else{
							return 1+" - Not waived";
						}
					}
				else if (p.getTypeOf().equalsIgnoreCase("rental")){
					return 1+"";
				}else{
					throw new RuntimeException("TransWindow -->what else is there");
				}
			}
			});
		

		
		

		Composite composite_5 = new Composite(composite, SWT.BORDER);
		fd_composite_4.bottom = new FormAttachment(composite_5, -6);
		FormData fd_composite_5 = new FormData();
		fd_composite_5.top = new FormAttachment(0, 428);
		fd_composite_5.bottom = new FormAttachment(100, -10);
		fd_composite_5.left = new FormAttachment(0, 713);
		fd_composite_5.right = new FormAttachment(100, -10);
		composite_5.setLayoutData(fd_composite_5);
		formToolkit.adapt(composite_5);
		formToolkit.paintBordersFor(composite_5);

		txtSubtotal = new Text(composite_5, SWT.BORDER);
		txtSubtotal.setBounds(80, 10, 64, 19);
		formToolkit.adapt(txtSubtotal, true, true);

		txtTaxrate = new Text(composite_5, SWT.BORDER);
		txtTaxrate.setBounds(80, 35, 64, 19);
		formToolkit.adapt(txtTaxrate, true, true);

		txtTax = new Text(composite_5, SWT.BORDER);
		txtTax.setBounds(80, 60, 64, 19);
		formToolkit.adapt(txtTax, true, true);

		txtTotal = new Text(composite_5, SWT.BORDER);
		txtTotal.setBounds(80, 107, 64, 19);
		formToolkit.adapt(txtTotal, true, true);

		txtSubtotalLabel = new Text(composite_5, SWT.RIGHT);
		txtSubtotalLabel.setEditable(false);
		txtSubtotalLabel.setText("Subtotal");
		txtSubtotalLabel.setBounds(10, 13, 64, 19);
		formToolkit.adapt(txtSubtotalLabel, true, true);

		txtTaxratelabel = new Text(composite_5, SWT.RIGHT);
		txtTaxratelabel.setEditable(false);
		txtTaxratelabel.setText("Tax Rate %");
		txtTaxratelabel.setBounds(10, 38, 64, 19);
		formToolkit.adapt(txtTaxratelabel, true, true);

		txtTaxLabel = new Text(composite_5, SWT.RIGHT);
		txtTaxLabel.setEditable(false);
		txtTaxLabel.setText("Tax");
		txtTaxLabel.setBounds(10, 63, 64, 19);
		formToolkit.adapt(txtTaxLabel, true, true);

		txtTotalLabel = new Text(composite_5, SWT.RIGHT);
		txtTotalLabel.setEditable(false);
		txtTotalLabel.setText("Total");
		txtTotalLabel.setBounds(10, 110, 64, 19);
		formToolkit.adapt(txtTotalLabel, true, true);

		Label lblTotalbar = new Label(composite_5, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		lblTotalbar.setText("totalbar");
		lblTotalbar.setBounds(10, 85, 134, 16);
		formToolkit.adapt(lblTotalbar, true, true);
		
		Composite composite_6 = new Composite(composite, SWT.BORDER);
		FormData fd_composite_6 = new FormData();
		fd_composite_6.right = new FormAttachment(composite_4, 0, SWT.RIGHT);
		fd_composite_6.bottom = new FormAttachment(composite_4, -6);
		fd_composite_6.left = new FormAttachment(0, 10);
		fd_composite_6.top = new FormAttachment(0, 10);
		composite_6.setLayoutData(fd_composite_6);
		formToolkit.adapt(composite_6);
		formToolkit.paintBordersFor(composite_6);
		
		lblSerial = new Text(composite_6, SWT.RIGHT);
		lblSerial.setText("Serial");
		lblSerial.setBounds(10, 17, 38, 14);
		formToolkit.adapt(lblSerial, true, true);
		
		txtSerialin = new Text(composite_6, SWT.BORDER);
		txtSerialin.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(txtSerialin.getText().isEmpty()){
					txtSkuin.setEditable(true);
					txtSkuin.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}else{
					txtSkuin.setEditable(false);
					txtSkuin.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
				}
			}
		});
		txtSerialin.setBounds(54, 14, 322, 19);
		formToolkit.adapt(txtSerialin, true, true);
		
		txtSku = new Text(composite_6, SWT.RIGHT);
		txtSku.setText("SKU:");
		txtSku.setBounds(406, 14, 38, 14);
		formToolkit.adapt(txtSku, true, true);
		
		txtSkuin = new Text(composite_6, SWT.BORDER);
		txtSkuin.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtSkuin.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(txtSkuin.getText().isEmpty()){
					txtSerialin.setEditable(true);
					txtSerialin.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}else{
				txtSerialin.setEditable(false);
				txtSerialin.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
				}
			}
		});
		txtSkuin.setBounds(450, 14, 322, 19);
		formToolkit.adapt(txtSkuin, true, true);
		
		
		
		Button btnAdd = new Button(composite_6, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//add button is clicked
			addButtonClicked();
			calculateTotals();
			}
		});
		btnAdd.setBounds(778, 9, 69, 28);
		formToolkit.adapt(btnAdd, true, true);
		btnAdd.setText("Add");
		
		Button btnCheckout= new Button(composite, SWT.NONE);
		btnCheckout.setFont(SWTResourceManager.getFont("Lucida Grande", 36, SWT.NORMAL));
		btnCheckout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				CheckoutDialog wind = new CheckoutDialog(shell, 0);
				wind.setAmountDue(total);
			 wind.open(TransactionWindow.this);
//				if(completed){
//					shell.close();
//					
//				}
				
			}
		});
		FormData fd_btnCheckout = new FormData();
		fd_btnCheckout.bottom = new FormAttachment(composite_5, 0, SWT.BOTTOM);
		fd_btnCheckout.top = new FormAttachment(composite_4, 6);
		fd_btnCheckout.right = new FormAttachment(composite_5, -6);
		fd_btnCheckout.left = new FormAttachment(0, 521);
		btnCheckout.setLayoutData(fd_btnCheckout);
		formToolkit.adapt(btnCheckout, true, true);
		btnCheckout.setText("Checkout");
		
	
		
		Button btnLogout = new Button(composite, SWT.NONE);
		FormData fd_btnLogout = new FormData();
		fd_btnLogout.top = new FormAttachment(composite_4, 6);
		fd_btnLogout.left = new FormAttachment(composite_4, 0, SWT.LEFT);
		btnLogout.setLayoutData(fd_btnLogout);
		btnLogout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HashMap<String, Object> map = new LogIn().open(false);
				boolean keepOpen = (Boolean) map.get("keepOpen");
				Employee emp = (Employee) map.get("employee"); 
				if (keepOpen){//clicked cancel
				System.out.println("staying open");
					}
				else{
					System.out.println("closing");
					shell.close();
					new TransactionWindow().open(emp);
				}
				
			}
		});
		formToolkit.adapt(btnLogout, true, true);
		btnLogout.setText("Logout - "+employee.getFirstName()+" "+employee.getLastName());
		
		 
	
		
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(saleItems);
		
		
		
		
		
		
		

	}



	protected void addButtonClicked() {
		//  map sale object to display in list when ADD button is clicked
		
		
		
		if(txtSkuin.getText().isEmpty()){//they want pproduct
			String prefix = txtSerialin.getText().substring(0,2);
		
			if(prefix.equalsIgnoreCase("FR")){//it's a ForRent object
			//if it's ForRent then open the New Rental GUI and then add the ForRent to the Rental
					
			 
				ForRent fr=null;
				
				try {
					fr = BusinessObjectDAO.getInstance().searchForBO("ForRent", new SearchCriteria("serial", txtSerialin.getText()));
					if(fr==null){
						WindowManager.getInstance().showMessageDialog(shell, "This Product does not exist or was entered incorrectly");
						return;
					}
				} catch (DataException e2) {
					e2.printStackTrace();
				} catch (edu.byu.isys413.swt.manager.DataException e) {
					e.printStackTrace();
				}
				HashMap<String, Object> returnMap = null;
				returnMap = new NewRental().open(txtSerialin.getText());
				rentalPrice = (Double) returnMap.get("price");
				boolean add = (Boolean) returnMap.get("add");
				Rental r=null;
				try {
					r = BusinessObjectDAO.getInstance().create("Rental");
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				r.setChargeAmt(rentalPrice);
				r.setForRent(fr.getId());
				r.setForRentId(fr.getId());
				r.setTypeOf("rental");
				
				if(add){
				saleItems.add(r);
				}
				tableViewer.setInput(saleItems);
			
			}//if 2
		else if(prefix.equalsIgnoreCase("FS")){ 
			//check availability of the product using serial
			
			ForSale fs= null;
			Sale s = null;
				try {
					fs= BusinessObjectDAO.getInstance().searchForBO("ForSale", new SearchCriteria("serial", txtSerialin.getText()));
					if(fs==null){
						WindowManager.getInstance().showMessageDialog(shell, "This Product does not exist or was entered incorrectly");
					    return;
					}
					s = BusinessObjectDAO.getInstance().create("Sale");
				} catch (DataException e1) {
					e1.printStackTrace();
						} catch (edu.byu.isys413.swt.manager.DataException e) {
					e.printStackTrace();
				}
				if(fs.getStatus().equalsIgnoreCase("available")){
					fs.setStatus("not available");
					try {
						fs.save();
					} catch (DataException e) {
						e.printStackTrace();
					}
				s.setChargeAmt(fs.getPrice());
				fs.setCProduct(fs.getCProductId());
				s.setProduct(fs);
				s.setTypeOf("sale");
				
				s.setQuantity(1);//pproduct can only have quantity 1
				
				saleItems.add(s);
				tableViewer.setInput(saleItems);
				}
				else{
					 try {//prompt that the product is not available already been added or sold
						WindowManager.getInstance().showMessageDialog(shell, "The product is no longer available");
					} catch (edu.byu.isys413.swt.manager.DataException e) {
						e.printStackTrace();
					}
					
				}
				
			} else if(prefix.equalsIgnoreCase("Fe")){//short for Fee
				//TODO damage fee?
				try {
					WindowManager.getInstance().showMessageDialog(shell, "This Product does not exist or was entered incorrectly");
					return;
				} catch (edu.byu.isys413.swt.manager.DataException e) {
					e.printStackTrace();
				}
			}//end else 2
			else {
				try {
					WindowManager.getInstance().showMessageDialog(shell, "This Product does not exist or was entered incorrectly");
					return;
				} catch (edu.byu.isys413.swt.manager.DataException e) {
					e.printStackTrace();
				}
			}//else
			}//end if 1
		
		
		else if (txtSerialin.getText().isEmpty())
		{//if it's a sku number, get the conceptual product
			CProduct cp=null;
			
			try{
				cp = BusinessObjectDAO.getInstance().searchForBO("CProduct", new SearchCriteria("sku", txtSkuin.getText()));
				
				if(cp==null){
					WindowManager.getInstance().showMessageDialog(shell, "This Product does not exist or was entered incorrectly");
				}
				
				if(cp.getTypeOf().equalsIgnoreCase("conceptual")==false){//if cp is actually a physcial product and has a serial number
					try {
						WindowManager.getInstance().showMessageDialog(shell, "Please enter the product's serial number.");
					} catch (edu.byu.isys413.swt.manager.DataException e) {
						e.printStackTrace();
					}
				}
				else{
					Sale s =null;
					s = BusinessObjectDAO.getInstance().create("Sale");
					s.setQuantity(1);//set quantity to 1 for now, then check list later to add if necessary
					s.setProduct(cp);
					s.setTypeOf("sale");
					s.setChargeAmt(cp.getPrice());
				
					//check to see if cproduct already exists in table
					
						if(saleItems.size()==0){
							saleItems.add(s);
							tableViewer.setInput(saleItems);
						}else{
							boolean run=true;
							for(RevenueSource rs: saleItems){
								if(rs.getSkuOrRevenueType().equalsIgnoreCase(txtSkuin.getText())){//and the skuIn matches that rs.sku
					
									System.out.println(run);
									rs.setQuantity(rs.getQuantity()+1);//setQuantity++
									tableViewer.setInput(saleItems);//refresh view
									run=false;
									
								}
							}//end for loop
							if(run){
							saleItems.add(s);
							tableViewer.setInput(saleItems);
							}	
									
									}//end if
							
					
				}//end if
			}
		catch(DataException e4){
		e4.printStackTrace();
		} catch (edu.byu.isys413.swt.manager.DataException e) {
			System.out.println("why didn't you implement freaking try catches goodrich?");
			e.printStackTrace();
		}	
		}
		
		tableViewer.setInput(saleItems);
	}

	protected void calculateTotals() {// calculate from sale objects the commissions, taxes and totals and display them in the window
		double ext=0;
		for(RevenueSource rs: saleItems){
			  ext = ext + rs.getChargeAmt()*rs.getQuantity();
		
		}
		subtotal = ext;//calculate tax and total
		
		System.out.println(subtotal);
		System.out.println("transWindow--calculateTotals--t.getStore-->"+t.getStoreId());
		System.out.println("transWindow--calculateTotals--t.getStore-->"+t.getStore().getSalesTaxRate());
		
		taxrate = t.getStore().getSalesTaxRate();
		tax = (taxrate/100)*subtotal;
		total = Math.floor((tax+subtotal) * 100) / 100;
		
		//display them in the window
		txtSubtotal.setText(subtotal+"");
		txtTaxrate.setText(taxrate+"");
		txtTax.setText(new DecimalFormat("#.##").format(tax));
		txtTotal.setText(total+"");
		
		
	}
	protected void printCommissions(boolean all) {
		
		if(all){
		
		//	1.ask user where to save (what is the HTML file name)
			FileDialog fd = new FileDialog(shell, SWT.SAVE);
			fd.setText("Save");
			String[] filterExt = { "*.html", "*,*"};
			fd.setFilterExtensions(filterExt);
			String selected = fd.open();
			if (selected == null){
				return;
			}
			
		//	2. save the bank text to an html file
			
			try {
				PrintWriter out = new PrintWriter(new FileWriter(selected));
				out.println("<html>");
				out.println("<body>");
				
					String employeeName="";
					String commission="";
					 java.util.List<Employee> empList = null;
					 java.util.List<Commission> comList=null;
				
					
					try {
						
						empList = BusinessObjectDAO.getInstance().searchForAll("Employee");

					} catch (DataException e1) {
						e1.printStackTrace();
					}
					for(Employee emp : empList){
						System.out.println(empList.size());
						employeeName = "<p>"+ employeeName +""+ emp.getFirstName() + emp.getLastName() +"</p>";
						comList = emp.getCommissions(emp.getId());
						System.out.println(comList.size());
						System.out.println(employeeName);
						for(Commission c: comList){
							commission ="<p>"+ 
									"<table border='1'>"+
										"<tr>"+
											"<th>Commission ID</th>"+
											"<th>Amount</th>"+
											"<th>Date</th>"+
											"<th>Transaction ID</th>"+
										"</tr>"+
										"<tr>"+
											"<td>"+c.getId()+"</td>"+
											"<td>"+c.getAmount()+"</td>"+
											"<td>"+c.getDateOf()+"</td>"+
											"<td>"+c.getTransId()+"</td>"+
										"</tr>"+
									"</table>"+
									"<hr width = '200' align='left'>";
							employeeName=employeeName+""+commission+"\n";
							
						}
					
					
					}
					
				out.println(employeeName);
				
				out.println("</body>");
				out.println("</html>");
				out.flush();
				out.close();
					
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		//	3. open the html file the user's browser
			Program.launch("file:///" + selected);
			
		}else{
		//	1.ask user where to save (what is the HTML file name)
			FileDialog fd = new FileDialog(shell, SWT.SAVE);
			fd.setText("Save");
			String[] filterExt = { "*.html", "*,*"};
			fd.setFilterExtensions(filterExt);
			String selected = fd.open();
			if (selected == null){
				return;
			}
			
		//	2. save the bank text to an html file
			
			try {
				PrintWriter out = new PrintWriter(new FileWriter(selected));
				out.println("<html>");
				out.println("<body>");
				
					String allCommissions="";
					String commission="";
					 
					 java.util.List<Commission> comList= employee.getCommissions(employee.getId());
						
						for(Commission c: comList){
							commission ="<p>"+ 
									"<table border='1'>"+
										"<tr>"+
											"<th>Commission ID</th>"+
											"<th>Amount</th>"+
											"<th>Date</th>"+
											"<th>Transaction ID</th>"+
										"</tr>"+
										"<tr>"+
											"<td>"+c.getId()+"</td>"+
											"<td>"+c.getAmount()+"</td>"+
											"<td>"+c.getDateOf()+"</td>"+
											"<td>"+c.getTransId()+"</td>"+
										"</tr>"+
									"</table>"+
									"<hr width = '200' align='left'>";
							allCommissions =allCommissions+""+commission+"\n";
							
						
					
					
					}
					
				out.println(allCommissions);
				
				out.println("</body>");
				out.println("</html>");
				out.flush();
				out.close();
					
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		//	3. open the html file the user's browser
			Program.launch("file:///" + selected);
				
		}
	
}
	
	protected String getMacAddress(){
		String compName=null;
		String[] name = null;
		try {
			compName = InetAddress.getLocalHost().getHostName();
			System.out.println(compName);
			name = compName.split("-");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return name[0];
	}

	/**
	 * @return the subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}

	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * @return the taxrate
	 */
	public double getTaxrate() {
		return taxrate;
	}

	/**
	 * @param taxrate the taxrate to set
	 */
	public void setTaxrate(double taxrate) {
		this.taxrate = taxrate;
	}

	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @return the t
	 */
	public Trans getT() {
		return t;
	}

	/**
	 * @param t the t to set
	 */
	public void setT(Trans t) {
		this.t = t;
	}

	public java.util.List<RevenueSource> getSaleItems() {
		return saleItems;
	}
}
