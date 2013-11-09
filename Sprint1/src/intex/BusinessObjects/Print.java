package intex.BusinessObjects;

/**
 * Print BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Print extends BusinessObject {

	@BusinessObjectField
	private double price;
	@BusinessObjectField
	private String size;
	@BusinessObjectField
	private String typeof;

	/**creates a new instance of this object*/
	public Print(String id) {
		super(id);
	}


	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
		setDirty();
	}


	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}


	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
		setDirty();
	}


	/**
	 * @return the typeof
	 */
	public String getTypeof() {
		return typeof;
	}


	/**
	 * @param typeof the typeof to set
	 */
	public void setTypeof(String typeof) {
		this.typeof = typeof;
		setDirty();
	}

}
