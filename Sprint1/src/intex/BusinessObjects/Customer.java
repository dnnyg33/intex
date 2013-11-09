package intex.BusinessObjects;

import intex.DataException;
import intex.SearchCriteria;

import java.util.List;

/**
 * Customer BO
 * 
 * @author Group 2-3
 * @version 1.1
 */

public class Customer extends BusinessObject {

	public void construct(String firstName, String lastName, String phone,
			String email, String address, String city, String state, String zip) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}


	@BusinessObjectField
	private String firstName;
	@BusinessObjectField
	private String lastName;
	@BusinessObjectField
	private String phone;
	@BusinessObjectField
	private String email;
	@BusinessObjectField
	private String address="123";
	@BusinessObjectField
	private String city;
	@BusinessObjectField
	private String state;
	@BusinessObjectField
	private String zip;
	@BusinessObjectField
	private String password;
	@BusinessObjectField
	private boolean validated;
	@BusinessObjectField
	private String validationCode;
	@BusinessObjectField
	private boolean member;
	

  /** Creates a new instance of this object */
	public Customer(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		setDirty();
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
		setDirty();
	}


	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}


	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
		setDirty();
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	setDirty();}


	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	setDirty();}


	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}


	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	setDirty();}


	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}


	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	setDirty();}


	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}


	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	setDirty();}

	/**
	* Returns a list of Transactions that this customer has been involved in
	*/
	public List<intex.BusinessObjects.Trans> getTrans(String custId) {
		List<Trans> trans=null;
		try {
			trans = BusinessObjectDAO.getInstance().searchForList("Trans", new SearchCriteria("custId", custId));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return trans;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
		setDirty();
	}


	/**
	 * @return the validationCode
	 */
	public String getValidationCode() {
		return validationCode;
	}


	/**
	 * @param validationCode the validationCode to set
	 */
	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
		setDirty();
	}


//	/**
//	 * @param isValidated the isValidated to set
//	 */
//	public void setIsValidated(boolean isValidated) {
//		this.isValidated = isValidated;
//	}


	/**
	 * @return the isMember
	 */
	public boolean isMember() {
		return member;
	}


	/**
	 * @param isMember the isMember to set
	 */
	public void setMember(boolean isMember) {
		this.member = isMember;
		setDirty();
	}


	/**
	 * @return the isValidated
	 */
	public boolean isValidated() {
		return validated;
	}


	/**
	 * @param isValidated the isValidated to set
	 */
	public void setValidated(boolean isValidated) {
		this.validated = isValidated;
		setDirty();
	}


}
