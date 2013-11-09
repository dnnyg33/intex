package intex.BusinessObjects;

import intex.DataException;
import intex.SearchCriteria;

/**
 * Fee BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class ForRent extends PProduct {
	
	public void construct(int timeRented, String serial, String typeOf){
		this.timesRented=timeRented;
		this.serial=serial;
		this.typeOf=typeOf;
	}

	@BusinessObjectField
	private int timesRented;
	@BusinessObjectField
	private String serial;
	private String typeOf="ForRent";
	private CRental CRental;
	
	/** creates a new instance of this object*/
	public ForRent(String id) {
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
		setDirty()
		;
	}

	/**
	 * @return the timesRented
	 */
	public int getTimesRented() {
		return timesRented;
	}

	/**
	 * @param timesRented the timesRented to set
	 */
	public void setTimesRented(int timesRented) {
		this.timesRented = timesRented;
	}
	/**Convenience method, sets CRental within ForRent */
	public void setCRental(String Id) {
		try {
			CRental=BusinessObjectDAO.getInstance().searchForBO("CRental", new SearchCriteria("id", Id));
		} catch (DataException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @return the cRental
	 */
	public CRental getCRental() {
		return CRental;
	}

	/**
	 * @param cRental the cRental to set
	 */
	public void setCRental(CRental cRental) {
		CRental = cRental;
	}

	

}
