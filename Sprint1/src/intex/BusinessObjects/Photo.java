package intex.BusinessObjects;

import java.util.Date;

/**
 * Photo BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Photo extends BusinessObject {
	
	@BusinessObjectField
	String caption;
	@BusinessObjectField
	String picas64;
	@BusinessObjectField
	String custId;
	@BusinessObjectField
	private Date dateof;
	
	/**creates a new instance of this object*/
	public Photo(String id) {
		super(id);
	}


	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}


	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
		setDirty();
	}


	/**
	 * @return the pictureString
	 */
	public String getPicas64() {
		return picas64;
	}


	/**
	 * @param pictureString the pictureString to set
	 */
	public void setPicas64(String pictureString) {
		this.picas64 = pictureString;
		setDirty();
	}


	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}


	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
		setDirty();
	}

	/**sets date*/
	public void setDateof(Date date) {
		this.dateof = date;
		setDirty();
		
	}
	/**gets date*/
	public Date getDateof(){
		return dateof;
	}

}
