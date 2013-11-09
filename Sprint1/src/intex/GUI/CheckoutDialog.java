package intex.GUI;

import java.util.Date;

import intex.DataException;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.DebitOrCredit;
import intex.BusinessObjects.JournalEntry;
import intex.BusinessObjects.RevenueSource;
import intex.BusinessObjects.Sale;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

/**This dialog is opened when, from the transaction window, the employee clicks the checkout button. 
 * If provides accounting help (for change) and when complete is pressed, completes the transaction
 * 
 * @author Section 2-3
 *
 */
public class CheckoutDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text txtTotal;
	private Text txtAmountPaid;
	private Text txtChange;
	private double amountDue;
	private double change;
	private double amountPaid;
	private TransactionWindow window;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CheckoutDialog(Shell parent, int style) {
		super(parent, style);
		setText("Checkout");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open(TransactionWindow window) {
		this.window = window;
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(571, 491);
		shell.setText(getText());
		shell.setLayout(new FormLayout());
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Lucida Grande", 26, SWT.NORMAL));
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 10);
		fd_lblNewLabel.left = new FormAttachment(0, 224);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Checkout");
		
		txtTotal = new Text(shell, SWT.BORDER);
		txtTotal.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		txtTotal.setFont(SWTResourceManager.getFont("Lucida Grande", 26, SWT.NORMAL));
		FormData fd_txtTotal = new FormData();
		fd_txtTotal.right = new FormAttachment(100, -212);
		txtTotal.setLayoutData(fd_txtTotal);
		txtTotal.setText(Double.toString(amountDue));
		
		Label lblTotal = new Label(shell, SWT.NONE);
		fd_txtTotal.bottom = new FormAttachment(lblTotal, 9, SWT.BOTTOM);
		fd_txtTotal.top = new FormAttachment(lblTotal, -3, SWT.TOP);
		fd_txtTotal.left = new FormAttachment(lblTotal, 1);
		lblTotal.setFont(SWTResourceManager.getFont("Lucida Grande", 26, SWT.NORMAL));
		FormData fd_lblTotal = new FormData();
		fd_lblTotal.top = new FormAttachment(0, 59);
		fd_lblTotal.right = new FormAttachment(100, -382);
		lblTotal.setLayoutData(fd_lblTotal);
		lblTotal.setText("Amount Due: ");
		
		Button btnBack = new Button(shell, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {//complete button is pressed
				//create debit/credit
			double rRev=0;
			double sRev=0;
			double fRev=0;
			double cPay=0;
				for(RevenueSource rs: window.getSaleItems()){
					if(rs.getTypeOf().equalsIgnoreCase("rental")){
						rRev=rs.getChargeAmt()+rRev;
					}
//					if(rs.getTypeOf().equalsIgnoreCase("sale")){
//						sRev=rs.getChargeAmt()+sRev;
//					}
//					if(rs.getTypeOf().equalsIgnoreCase("conceptual")){
//						sRev=(rs.getChargeAmt()*rs.getQuantity())+(sRev);
//					}
					
					else if(rs.getTypeOf().equalsIgnoreCase("fee")){
						fRev=rs.getChargeAmt()+fRev;
					}
					else{
						sRev=(rs.getChargeAmt()*rs.getQuantity())+(sRev);
						Sale s = (Sale) rs;
						cPay=(s.getProduct().getFullCommissionRate()*rs.getChargeAmt())+(cPay);
					}
				
					
				}
				
				DebitOrCredit dc1=null;
				DebitOrCredit dc2=null;
				DebitOrCredit dc3=null;
				DebitOrCredit dc4=null;
				DebitOrCredit dc5=null;
				DebitOrCredit dc6=null;
				DebitOrCredit dc7=null;
				
				JournalEntry je = null;
				
				try {
					
					 dc1 = BusinessObjectDAO.getInstance().create("DebitOrCredit");
					 dc2 = BusinessObjectDAO.getInstance().create("DebitOrCredit");
					 dc3 = BusinessObjectDAO.getInstance().create("DebitOrCredit");
					 dc4 = BusinessObjectDAO.getInstance().create("DebitOrCredit");
					 dc5 = BusinessObjectDAO.getInstance().create("DebitOrCredit");
					 dc6 = BusinessObjectDAO.getInstance().create("DebitOrCredit");
					 dc7 = BusinessObjectDAO.getInstance().create("DebitOrCredit");
					 je = BusinessObjectDAO.getInstance().create("JournalEntry");
				} catch (DataException e1) {
					e1.printStackTrace();
				}
				dc1.construct(false, "Cash", je.getId(), amountDue, "gl1");
				dc2.construct(false, "Commission Payable", je.getId(), cPay, "gl2");
				dc3.construct(true, "Commission Expense", je.getId(), cPay, "gl3");
				dc4.construct(false, "Taxes Payable", je.getId(), amountDue/(window.getTaxrate()), "gl4");
				dc5.construct(true, "Sales Revenue", je.getId(), sRev, "gl5");
				dc6.construct(true, "Rental Revenue", je.getId(), rRev, "gl6");
				dc7.construct(true, "Fee Revenue", je.getId(), fRev, "gl7");
				
				je.construct(new Date());
				String transid = window.getT().getId();
				je.setTransId(window.getT().getId());
				
				window.getT().construct(new Date(), window.getSubtotal(), window.getTax(), window.getT().getCustId(), window.getT().getStoreId(), window.getTotal(), window.getT().getEmpId());
				
				try {
					dc1.save();
					dc2.save();
					//dc3.save();
					dc4.save();
					dc5.save();
					dc6.save();
					dc7.save();
					je.save();
					window.getT().save();
				} catch (DataException e1) {
					//   Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				//create journal entry
				//save transaction to DB
				//TODO save  to DB
				window.shell.dispose();
				TransactionWindow tw = new TransactionWindow();
				tw.open(window.employee);
				shell.close();
				
				
			}
		});
		btnBack.setText("Complete");
		FormData fd_btnBack = new FormData();
		fd_btnBack.bottom = new FormAttachment(100, -10);
		fd_btnBack.left = new FormAttachment(0, 451);
		fd_btnBack.right = new FormAttachment(100, -10);
		btnBack.setLayoutData(fd_btnBack);
		
		Label lblAmountPaid = new Label(shell, SWT.NONE);
		lblAmountPaid.setText("Amount Paid:");
		lblAmountPaid.setFont(SWTResourceManager.getFont("Lucida Grande", 26, SWT.NORMAL));
		FormData fd_lblAmountPaid = new FormData();
		fd_lblAmountPaid.left = new FormAttachment(lblTotal, 0, SWT.LEFT);
		lblAmountPaid.setLayoutData(fd_lblAmountPaid);
		
		txtAmountPaid = new Text(shell, SWT.BORDER);
		txtAmountPaid.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				amountPaid = Double.parseDouble(txtAmountPaid.getText());
				amountDue = Double.parseDouble(txtTotal.getText());
				change = Math.floor((amountPaid - amountDue) * 100) / 100;
				if(change>0){
				txtChange.setText(change+"");
				}
			}
		});
		
		fd_lblAmountPaid.top = new FormAttachment(txtAmountPaid, 3, SWT.TOP);
		txtAmountPaid.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		txtAmountPaid.setFont(SWTResourceManager.getFont("Lucida Grande", 26, SWT.NORMAL));
		FormData fd_txtAmountPaid = new FormData();
		fd_txtAmountPaid.bottom = new FormAttachment(txtTotal, 49, SWT.BOTTOM);
		fd_txtAmountPaid.right = new FormAttachment(txtTotal, 0, SWT.RIGHT);
		fd_txtAmountPaid.top = new FormAttachment(txtTotal, 6);
		fd_txtAmountPaid.left = new FormAttachment(txtTotal, 0, SWT.LEFT);
		txtAmountPaid.setLayoutData(fd_txtAmountPaid);
		
		txtChange = new Text(shell, SWT.BORDER);
		fd_btnBack.top = new FormAttachment(txtChange, 134);
		txtChange.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		txtChange.setFont(SWTResourceManager.getFont("Lucida Grande", 40, SWT.NORMAL));
		FormData fd_txtChange = new FormData();
		fd_txtChange.right = new FormAttachment(100, -77);
		fd_txtChange.top = new FormAttachment(txtAmountPaid, 63);
		txtChange.setLayoutData(fd_txtChange);
		txtChange.setEditable(false);
		
		Label lblChangeDue = new Label(shell, SWT.NONE);
		fd_txtChange.left = new FormAttachment(lblChangeDue, 7);
		lblChangeDue.setText("Change Due:");
		lblChangeDue.setFont(SWTResourceManager.getFont("Lucida Grande", 36, SWT.NORMAL));
		FormData fd_lblChangeDue = new FormData();
		fd_lblChangeDue.right = new FormAttachment(100, -327);
		fd_lblChangeDue.top = new FormAttachment(txtAmountPaid, 64);
		lblChangeDue.setLayoutData(fd_lblChangeDue);
		
		Button btnBack_1 = new Button(shell, SWT.NONE);
		btnBack_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnBack_1.setText("Back");
		FormData fd_btnBack_1 = new FormData();
		fd_btnBack_1.bottom = new FormAttachment(btnBack, 0, SWT.BOTTOM);
		fd_btnBack_1.top = new FormAttachment(btnBack, 0, SWT.TOP);
		fd_btnBack_1.right = new FormAttachment(btnBack, -6);
		fd_btnBack_1.left = new FormAttachment(0, 335);
		btnBack_1.setLayoutData(fd_btnBack_1);

	}

	
	/**
	 * @return the amountDue
	 */
	public double getAmountDue() {
		return amountDue;
	}

	/**
	 * @param amountDue the amountDue to set
	 */
	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}
}

