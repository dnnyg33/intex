/////////////////////////////////////////////////////////////////
///   This file is an example of an Object Relational Mapping in
///   the ISys Core at Brigham Young University.  Students
///   may use the code as part of the 413 course in their
///   milestones following this one, but no permission is given
///   to use this code is any other way.  Since we will likely
///   use this code again in a future year, please DO NOT post
///   the code to a web site, share it with others, or pass
///   it on in any way.


package intex.BusinessObjects;

import java.util.Date;
import java.util.List;

import intex.DataException;
import intex.SearchCriteria;


/**
 * Employee BO
 *
 * @author Group 2-3
 * @version 1.1
 */
public class Employee extends BusinessObject{
	

	public void construct(String firstName, String lastName,
			Date hiredate, String phone, String storeId, String username,
			String password, double salary, String address, String city,
			String state, String zip, String position) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.hiredate = hiredate;
		this.phone = phone;
		this.storeId = storeId;
		this.username = username;
		this.password = password;
		this.salary = salary;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.position = position;
		
		
		
	}



	@BusinessObjectField
	private String firstName;
	@BusinessObjectField
	private String lastName;
	@BusinessObjectField
	private java.util.Date hiredate;
	@BusinessObjectField
	private String phone;
	@BusinessObjectField
	private String storeId;
    @BusinessObjectField
    private String username = null;
    @BusinessObjectField
    private String password = null;
    @BusinessObjectField
    private double salary = 0.0;
    @BusinessObjectField
    private String address;
    @BusinessObjectField
    private String city;
    @BusinessObjectField
    private String state;
    @BusinessObjectField
    private String zip;
    @BusinessObjectField
    private String position;
    private Store store;
    
   

    /** Creates a new instance of BusinessObject */
    public Employee(String id) {
        super(id);
    }//constructor

//    public Employee construct(String f, String l, java.util.Date h, String p, String s, String u, String pa, String sa, String a, String c, String st, String z, String pos){
//    	
//    }


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
	setDirty();}



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
	setDirty();}



	/**
	 * @return the hiredate
	 */
	public java.util.Date getHireDate() {
		return hiredate;
	}



	/**
	 * @param hiredate the hiredate to set
	 */
	public void setHireDate(java.util.Date hiredate) {
		this.hiredate = hiredate;
	setDirty();}



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
	setDirty();}



	/**
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}



	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	
	//setStore(storeId);
		setDirty();
	}



	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}



	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	setDirty();}



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
	setDirty();}



	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}



	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	setDirty();}



	/**
	 * @return the hiredate
	 */
	public java.util.Date getHiredate() {
		return hiredate;
	}



	/**
	 * @param hiredate the hiredate to set
	 */
	public void setHiredate(java.util.Date hiredate) {
		this.hiredate = hiredate;
		setDirty();
	}



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
		setDirty();
	}



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
		setDirty();
	}



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
		setDirty();
	}



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
		setDirty();
	}



	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}



	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
		setDirty();
	}



	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}



	/**
	 * @param store the store to set
	 */
	public void setStore(String storeId) {
		
		try {
			this.store = BusinessObjectDAO.getInstance().searchForBO("Store", new SearchCriteria("id", storeId));
		} catch (DataException e) {
			System.out.println("couldn't find the storeID");
			e.printStackTrace();
		}
		setDirty();
	}
	/** Returns a list of commissions that are connected to this employee. */
	public List<Commission> getCommissions(String empId){
		List<Commission> comms=null;
		try {
			comms = BusinessObjectDAO.getInstance().searchForList("Commission", new SearchCriteria("empid", empId));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return comms;
	}
	/** Returns a list of transactions that this employee was involved in.*/
	public List<intex.BusinessObjects.Trans> getTrans(String employeeId) {
		List<Trans> trans=null;
		try {
			trans = BusinessObjectDAO.getInstance().searchForList("Trans", new SearchCriteria("empid", employeeId));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return trans;
	}
	
	
	
}//class
