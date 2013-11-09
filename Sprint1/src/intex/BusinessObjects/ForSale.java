package intex.BusinessObjects;

/**
 * ForSale BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class ForSale extends PProduct {
	
	public void construct(String type, String newUsed, String serial){
		this.typeOf=type;
		this.newUsed=newUsed;
		this.serial=serial;
	}
	
	@BusinessObjectField
	private String typeOf="ForSale";
	@BusinessObjectField
	private String newUsed;
	@BusinessObjectField
	private String serial;
	
	/**Creates new instance of this object*/
	public ForSale(String id) {
		super(id);
	}

	@Override
	public String getTypeof() {
		return typeOf;
	}

	@Override
	public void setTypeof(String typeOf) {
		this.typeOf=typeOf;
		setDirty();
	}

	/**
	 * @return the typeOf
	 */
	public String getTypeOf() {
		return typeOf;
	}

	/**
	 * @param typeOf the typeOf to set
	 */
	public void setTypeOf(String typeOf) {
		this.typeOf = typeOf;
		setDirty();
	}

	/**
	 * @return the newUsed
	 */
	public String getNewUsed() {
		return newUsed;
	}

	/**
	 * @param newUsed the newUsed to set
	 */
	public void setNewUsed(String newUsed) {
		this.newUsed = newUsed;
		setDirty();
	}

	/**
	 * @return the serial
	 */
	public String getSerial() {
		return super.getSerialNum();
	}

	/**
	 * @param serial the serial to set
	 */
	public void setSerial(String serial) {
		super.setSerialNum(serial);
		setDirty();
	}

	

}
