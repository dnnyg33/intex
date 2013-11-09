package intex;

import intex.GUI.LogIn;
import intex.GUI.TransactionWindow;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MyStuffSystem {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	

	public static void main(String[] args){
		MyStuffSystem main = new MyStuffSystem();
		main.run(main);
		
	}

	private void run(MyStuffSystem main) {
		//create mainWindow which in turn creates its own login dialog screen
		try {
			CreateDB.main(null);
			LogIn login = new LogIn();
			login.open(true);
			if (login.isCorrect()) {
				TransactionWindow window = new TransactionWindow();
				window.open();
			}else{
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	//a bunch of event handlers that take care of function and run other things



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
		shell.setText("SWT Application");

	}

}

