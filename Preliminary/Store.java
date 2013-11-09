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
public class Store extends BusinessObject {

  @BusinessObjectField
  private String location = null;
  @BusinessObjectField
  private String manager = null;
  @BusinessObjectField
  private String phone = null;
  private String address = null;
  private String city = null;
  private String state = null;
  private String zipcode = null;
  

  /** Creates a new instance of this object */
  public Store(String id) {
      super(id);
  }//constructor


/**
 * @return the location
 */
public String getLocation() {
	return location;
}


/**
 * @param location the location to set
 */
public void setLocation(String location) {
	this.location = location;
	setDirty();
}


/**
 * @return the manager
 */
public String getManager() {
	return manager;
}


/**
 * @param manager the manager to set
 */
public void setManager(String manager) {
	this.manager = manager;
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
 * @return the zipcode
 */
public String getZipcode() {
	return zipcode;
}


/**
 * @param zipcode the zipcode to set
 */
public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
	setDirty();
}

 


}