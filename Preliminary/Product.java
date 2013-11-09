



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
public class Product extends BusinessObject {

  @BusinessObjectField
  private String productName  = null;
  @BusinessObjectField
  private String description = null;
  @BusinessObjectField
  private String manufacturer = null;
  private String avgCost = null;
  
  

  /** Creates a new instance of this object */
  public Product(String id) {
      super(id);
  }//constructor



/**
 * @return the productName
 */
public String getProductName() {
	return productName;
}



/**
 * @param productName the productName to set
 */
public void setProductName(String productName) {
	this.productName = productName;
	setDirty();
}



/**
 * @return the description
 */
public String getDescription() {
	return description;
}



/**
 * @param description the description to set
 */
public void setDescription(String description) {
	this.description = description;
	setDirty();
}



/**
 * @return the manufacturer
 */
public String getManufacturer() {
	return manufacturer;
}



/**
 * @param manufacturer the manufacturer to set
 */
public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
	setDirty();
}



/**
 * @return the avgCost
 */
public String getAvgCost() {
	return avgCost;
}



/**
 * @param avgCost the avgCost to set
 */
public void setAvgCost(String avgCost) {
	this.avgCost = avgCost;
	setDirty();
}



}