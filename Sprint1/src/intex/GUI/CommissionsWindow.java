package intex.GUI;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import intex.DataException;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.Commission;
import intex.BusinessObjects.Employee;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.program.Program;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class CommissionsWindow {

	protected Shell shell;
	private  CommissionsWindow wind;
	public int hour=12;//TODO make these persistent
	private int minute=0;
	private int ampm;
	Label lblam;
	
	
	private List<Employee> empList;
	private List<Commission> comList;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CommissionsWindow window = new CommissionsWindow();
			window.setWindow(window);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setWindow(CommissionsWindow window) {
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
		shell.setSize(289, 190);
		shell.setText("Commissions");
		shell.setLayout(new FormLayout());
		
		Label lblSerialNumber = new Label(shell, SWT.NONE);
		FormData fd_lblSerialNumber = new FormData();
		fd_lblSerialNumber.bottom = new FormAttachment(0, 98);
		fd_lblSerialNumber.right = new FormAttachment(0, 217);
		fd_lblSerialNumber.top = new FormAttachment(0, 84);
		fd_lblSerialNumber.left = new FormAttachment(0, 72);
		lblSerialNumber.setLayoutData(fd_lblSerialNumber);
		lblSerialNumber.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.BOLD));
		lblSerialNumber.setText("Last Successful Update");
		
		Label lblCommissionRate = new Label(shell, SWT.NONE);
		FormData fd_lblCommissionRate = new FormData();
		fd_lblCommissionRate.bottom = new FormAttachment(0, 138);
		fd_lblCommissionRate.right = new FormAttachment(0, 217);
		fd_lblCommissionRate.top = new FormAttachment(0, 124);
		fd_lblCommissionRate.left = new FormAttachment(0, 72);
		lblCommissionRate.setLayoutData(fd_lblCommissionRate);
		lblCommissionRate.setFont(SWTResourceManager.getFont("Lucida Grande", 12, SWT.BOLD));
		lblCommissionRate.setText("Next Scheduled Update");
		
		Button btnRevert = new Button(shell, SWT.NONE);
		btnRevert.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				printCommissions();
				
				shell.close();
			}
		});
		FormData fd_btnRevert = new FormData();
		fd_btnRevert.left = new FormAttachment(0, 79);
		btnRevert.setLayoutData(fd_btnRevert);
		btnRevert.setText("Get Commissions");
		
		Button btnSubmit = new Button(shell, SWT.NONE);
		fd_btnRevert.bottom = new FormAttachment(btnSubmit, -6);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		FormData fd_btnSubmit = new FormData();
		fd_btnSubmit.top = new FormAttachment(0, 35);
		fd_btnSubmit.left = new FormAttachment(0, 72);
		btnSubmit.setLayoutData(fd_btnSubmit);
		btnSubmit.setText("Update Commissions");
		
		Label lblSku = new Label(shell, SWT.NONE);
		FormData fd_lblSku = new FormData();
		fd_lblSku.top = new FormAttachment(0, 104);
		fd_lblSku.left = new FormAttachment(0, 97);
		lblSku.setLayoutData(fd_lblSku);
		lblSku.setText("12:34am 2/1/13");
		
		Label lblam = new Label(shell, SWT.NONE);
		FormData fd_lblam = new FormData();
		fd_lblam.top = new FormAttachment(0, 144);
		fd_lblam.left = new FormAttachment(0, 97);
		lblam.setLayoutData(fd_lblam);
		lblam.setText("12:05am 3/1/13");

	}
	protected void printCommissions() {
		
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
						/*					"<tr>"+
												"<td>"+b.getAccountNum()+"</td>"+
												"<td>"+b.getBalance()+"</td>"+
											"</tr>"+
						*/				"</table>"+
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
		lblam.setText(time);
		return time;
		
	}

	public void setAmpm(int mAmpm) {
		this.ampm=mAmpm;
		showTime();
	}
}
