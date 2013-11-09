package intex.GUI;


import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SetGeneralLedgerUpdateScheduleDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private int ampm=1;
	private int hour=0;
	private int minute=0;
	
	private GeneralLedgerWindow parent;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SetGeneralLedgerUpdateScheduleDialog(Shell parent, int style, GeneralLedgerWindow window) {
		super(parent, style);
		this.parent=window;
		setText("Start Time");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
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
		shell.setSize(250, 150);
		shell.setText(getText());
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.SOUTH);
		composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.CENTER);
		composite_1.setLayout(new GridLayout(4, false));
		new Label(composite_1, SWT.NONE);
		
		Label lblHour = new Label(composite_1, SWT.NONE);
		lblHour.setSize(0, 0);
		lblHour.setText("Hour");
		
		Label lblMin = new Label(composite_1, SWT.NONE);
		lblMin.setSize(0, 0);
		lblMin.setText("Min");
		
		Button btnAm = new Button(composite_1, SWT.RADIO);
		btnAm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ampm=0;
			}
		});
		btnAm.setSize(0, 0);
		btnAm.setText("AM");
		
		Label lblStartTime = new Label(composite_1, SWT.NONE);
		lblStartTime.setSize(0, 0);
		lblStartTime.setText("Start Time:");
		
		final Spinner hourSpinner = new Spinner(composite_1, SWT.BORDER);
		hourSpinner.setSize(0, 0);
		hourSpinner.setMaximum(12);
		hourSpinner.setMinimum(1);
		
		final Spinner minSpinner = new Spinner(composite_1, SWT.BORDER);
		minSpinner.setSize(0, 0);
		minSpinner.setMaximum(59);
		minSpinner.setMinimum(0);
		
		
		Button btnPm = new Button(composite_1, SWT.RADIO);
		btnPm.setSize(0, 0);
		btnPm.setText("PM");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnCancel.setText("Cancel");
		
		Button btnOk = new Button(composite, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hour = Integer.valueOf(hourSpinner.getText());
				minute = Integer.valueOf(minSpinner.getText());
				parent.setHour(hour);
				System.out.println("print hour-->"+hour+". print parent.hour-->"+parent.hour);
				System.out.println(parent.hour);
				parent.setMinute(minute);
				parent.setAmpm(ampm);
				shell.close();
				
			}
		});
		btnOk.setText("OK");
		
		

	}
}
