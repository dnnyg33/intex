/////////////////////////////////////////////////////////////////
///   This file is an example of an Object Relational Mapping in
///   the ISys Core at Brigham Young University.  Students
///   may use the code as part of the 413 course in their
///   milestones following this one, but no permission is given
///   to use this code is any other way.  Since we will likely
///   use this code again in a future year, please DO NOT post
///   the code to a web site, share it with others, or pass
///   it on in any way.


/**
 * Example BO
 * 
 * @author conan
 * @version 1.1
 */
public class Employee extends BusinessObject {

  @BusinessObjectField
  private String firstName = null;
  @BusinessObjectField
  private String middleName = null;
  @BusinessObjectField
  private String lastName = null;
  private String hireDate = null;
  private String phone = null;
  private String salary = null;
  private String store = null;
  

  /** Creates a new instance of this object */
  public Employee(String id) {
      super(id);
  }//constructor

 


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
   * @return the Middle Name
   */
  public String getMiddleName() {
    return middleName;
  }
  
  /**
   * @param middleName the middleName to set
   */
  public void setMiddleName(String middleName) {
  	this.middleName = middleName;
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
 * @return the hireDate
 */
public String getHireDate() {
	return hireDate;
}





/**
 * @param hireDate the hireDate to set
 */
public void setHireDate(String hireDate) {
	this.hireDate = hireDate;
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
 * @return the salary
 */
public String getSalary() {
	return salary;
}





/**
 * @param salary the salary to set
 */
public void setSalary(String salary) {
	this.salary = salary;
	setDirty();
}




/**
 * @return the store
 */
public String getStore() {
	return store;
}




/**
 * @param store the store to set
 */
public void setStore(String store) {
	this.store = store;
}




}
