package intex;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;

public class EditSerial extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text txtSerial;
	private Button btnSubmit;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public EditSerial(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
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
		shell.setSize(293, 136);
		shell.setText(getText());
		shell.setLayout(new FormLayout());
		
		Label lblScanOrEnter = new Label(shell, SWT.NONE);
		lblScanOrEnter.setFont(SWTResourceManager.getFont("Lucida Grande", 18, SWT.NORMAL));
		FormData fd_lblScanOrEnter = new FormData();
		fd_lblScanOrEnter.top = new FormAttachment(0, 10);
		fd_lblScanOrEnter.left = new FormAttachment(0, 51);
		lblScanOrEnter.setLayoutData(fd_lblScanOrEnter);
		lblScanOrEnter.setText("Scan or Enter Serial #");
		
		txtSerial = new Text(shell, SWT.BORDER);
		FormData fd_txtSerial = new FormData();
		fd_txtSerial.top = new FormAttachment(lblScanOrEnter, 17);
		fd_txtSerial.left = new FormAttachment(0, 33);
		fd_txtSerial.right = new FormAttachment(0, 259);
		txtSerial.setLayoutData(fd_txtSerial);
		
		btnSubmit = new Button(shell, SWT.NONE);
		FormData fd_btnSubmit = new FormData();
		fd_btnSubmit.top = new FormAttachment(txtSerial, 8);
		fd_btnSubmit.left = new FormAttachment(0, 111);
		btnSubmit.setLayoutData(fd_btnSubmit);
		btnSubmit.setText("Submit");

	}
}
