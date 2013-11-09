package intex.GUI;

import intex.DataException;
import intex.SearchCriteria;
import intex.BusinessObjects.BusinessObjectDAO;
import intex.BusinessObjects.DebitOrCredit;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;

/**This window gives a view of journal entry history unique to one GL account
 * 
 * @author Group 2-3
 *
 */
public class JournalEntryView {

	protected Shell shell;
	private Table table;
	private List<DebitOrCredit>dc;// = new ArrayList<DebitOrCredit>();
	private String accountName="Account Name";
	private String selected=null;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JournalEntryView window = new JournalEntryView();
			window.open(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @param selected 
	 */
	public void open(String selected) {
		this.accountName=selected;
		Display display = Display.getDefault();
		this.selected = selected;
		createContents();
		
		accountName=selected;
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
		System.out.println(selected);
		
		try {
			dc = BusinessObjectDAO.getInstance().searchForList("DebitOrCredit", new SearchCriteria("glname", selected));
		} catch (DataException e) {
			e.printStackTrace();
		}
		for(DebitOrCredit d: dc){
			d.setJournalEntry(d.getJournalEntryId());
		}
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText(accountName);
		shell.setLayout(new FormLayout());
		
		TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(0, 10);
		fd_table.left = new FormAttachment(0);
		fd_table.bottom = new FormAttachment(0, 268);
		fd_table.right = new FormAttachment(0, 440);
		table.setLayoutData(fd_table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnTransId = tableViewerColumn.getColumn();
		tblclmnTransId.setWidth(106);
		tblclmnTransId.setText("Transaction ID");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnDate = tableViewerColumn_3.getColumn();
		tblclmnDate.setWidth(227);
		tblclmnDate.setText("Date");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnDebitAmount = tableViewerColumn_2.getColumn();
		tblclmnDebitAmount.setWidth(100);
		tblclmnDebitAmount.setText("Amount");
		
		
		
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
		public String getText(Object element) {
		DebitOrCredit p = (DebitOrCredit) element;
		System.out.println("p.getJournalEntry());"+p.getJournalEntry());
		System.out.println("p.getJournalEntryID());"+p.getJournalEntryId());
		//JournalEntry j = BusinessObjectDAO.getInstance().searchForBO("JournalEntry", new SearchCriteria("id", p.getJournalEntryId()));
		return p.getJournalEntry().getTransId();
		}
		});
		
		tableViewerColumn_3.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
			DebitOrCredit p = (DebitOrCredit) element;
			return p.getJournalEntry().getDateOf()+"";
			}
			});
		
		tableViewerColumn_2.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
			DebitOrCredit p = (DebitOrCredit) element;
			return p.getAmount()+"";
			}
			});
		

		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(dc);
	}
}
