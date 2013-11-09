package intex.BusinessObjects;

import intex.DataException;

/**
 * Fee BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class PrintOrder extends RevenueSource {
	
	public final static String Size_5x7 = "print5x7";//TODO the DB must reflect these values as the BO id
	public final static String Size_3x5 = "print3x5";
	public final static String Size_4x6 = "print4x6";
	public final static String Size_8x10 = "print8x10";
	
	
	@BusinessObjectField
	private int quantity;
	@BusinessObjectField
	private String photoId;
	@BusinessObjectField
	private String printId;
	private Print print;

	
	/**creates a new instance of this object*/
	public PrintOrder(String id) {
		super(id);
	}

	@Override
	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public String getSkuOrRevenueType() {
		return "Print Order";
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity=quantity;
		setDirty();

	}

	/**
	 * @return the photoId
	 */
	public String getPhotoId() {
		return photoId;
	}

	/**
	 * @param photoId the photoId to set
	 */
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
		setDirty();
	}

	/**
	 * @return the printId
	 */
	public String getPrintId() {
		return printId;
	}

	/**
	 * @param printId the printId to set
	 */
	public void setPrintId(String printId) {
		this.printId = printId;
		setDirty();
	}
	
	/**sets print*/
	public void setPrint(String printId2) {
		Print p=null;
		try {
			p = BusinessObjectDAO.getInstance().read(printId2);
		} catch (DataException e) {
			e.printStackTrace();
		}
		this.print=p;
	}
	public Print getPrint(){
		return this.print;
	}

}
