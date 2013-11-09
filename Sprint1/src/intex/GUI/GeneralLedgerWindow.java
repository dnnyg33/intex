package intex.GUI;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**This window shows all GL accounts and allows employee to see journal entries. There is also a 
 * batch program that updates the GL accounts nightly.
 * @author Group 2-3
 *
 */
public class GeneralLedgerWindow {

	protected Shell shell;
	public int hour=12;//TODO make these persistent
	private int minute=0;
	private int ampm;
	private  GeneralLedgerWindow wind;
	Label lblNextScheduledUpdate;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GeneralLedgerWindow window = new GeneralLedgerWindow();
			window.setWindow(window);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setWindow(GeneralLedgerWindow window){
		wind=window;
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
		shell.setText("General Ledgers");
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new GridLayout(1, false));
		
		Label lblAccounts = new Label(composite, SWT.NONE);
		lblAccounts.setFont(SWTResourceManager.getFont("Lucida Grande", 15, SWT.NORMAL));
		lblAccounts.setText("Accounts");
		
		final Button btnCash = new Button(composite, SWT.RADIO);
		btnCash.setText("Cash");
		
		final Button btnCommissionPayable = new Button(composite, SWT.RADIO);
		btnCommissionPayable.setText("Commission Payable");
		
		final Button btnCommissionExpense = new Button(composite, SWT.RADIO);
		btnCommissionExpense.setText("Commission Expense");
		
		final Button btnTaxesPayable = new Button(composite, SWT.RADIO);
		btnTaxesPayable.setText("Taxes Payable");
		
		final Button btnSalesRevenue = new Button(composite, SWT.RADIO);
		btnSalesRevenue.setText("Sales Revenue");
		
		final Button btnRentalRevenue = new Button(composite, SWT.RADIO);
		btnRentalRevenue.setText("Rental Revenue");
		
		final Button btnFeeRevenue = new Button(composite, SWT.RADIO);
		btnFeeRevenue.setText("Fee Revenue");
		
		Button btnViewJournalEntries = new Button(composite, SWT.NONE);
		btnViewJournalEntries.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JournalEntryView window = new JournalEntryView();
				String selected = "Cash";	
			 if(btnCash.getSelection()){
				 selected = "Cash";}
			 else if(btnCommissionPayable.getSelection()){
					selected = btnCommissionPayable.getText();}
			 else if(btnCommissionExpense.getSelection()){
				 selected = btnCommissionExpense.getText();}
			 else if(btnTaxesPayable.getSelection()){
				 selected = btnTaxesPayable.getText();}
			 else if(btnSalesRevenue.getSelection()){
				 selected = btnSalesRevenue.getText();}
			 else if(btnRentalRevenue.getSelection()){
				 selected = btnRentalRevenue.getText();}
			 else if(btnFeeRevenue.getSelection()){
				 selected = btnFeeRevenue.getText();}
			 
			 
				window.open(selected);
			}
		});
		btnViewJournalEntries.setText("View Journal Entries");
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.WEST);
		composite_1.setLayout(new GridLayout(1, false));
		
		Button btnSetSchedule = new Button(composite_1, SWT.NONE);
		btnSetSchedule.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SetGeneralLedgerUpdateScheduleDialog window = new SetGeneralLedgerUpdateScheduleDialog(shell, 0, wind);
				window.open();
				
			}
		});
		btnSetSchedule.setText("Set General Ledger Update Schedule");
		
		Button btnUpdateNow = new Button(composite_1, SWT.NONE);
		btnUpdateNow.setText("Update Now");
		
		Label lblLastSuccessfulUpdate = new Label(composite_1, SWT.NONE);
		lblLastSuccessfulUpdate.setFont(SWTResourceManager.getFont("Lucida Grande", 14, SWT.NORMAL));
		lblLastSuccessfulUpdate.setText("Last Successful Update");
		
		Label lblLastSuccessfulUpdate_1 = new Label(composite_1, SWT.NONE);
		lblLastSuccessfulUpdate_1.setText("2:03am 1/31/13");
		
		Label lblNextScheduledUpdate_1 = new Label(composite_1, SWT.NONE);
		lblNextScheduledUpdate_1.setFont(SWTResourceManager.getFont("Lucida Grande", 14, SWT.NORMAL));
		lblNextScheduledUpdate_1.setText("Next Scheduled Update");
		
		 lblNextScheduledUpdate = new Label(composite_1, SWT.NONE);
		 lblNextScheduledUpdate.setText(showTime()+"       ");

	}

	public void setHour(int mHour) {
		this.hour = mHour;
		
	//	showTime();
		
		}

	public void setMinute(int mMinute) {
		
		this.minute=mMinute;
		showTime();
		
	}

	private String showTime() {
		String min = minute+"";
		if(minute<10){
			 min ="0"+minute;
		}String AMPM;
		if(ampm==0){
			AMPM="AM";
		}else
			AMPM="PM";
		String time=hour+":"+min+" "+AMPM;
		lblNextScheduledUpdate.setText(time);
		return time;
		
	}

	public void setAmpm(int mAmpm) {
		this.ampm=mAmpm;
		showTime();
	}
}