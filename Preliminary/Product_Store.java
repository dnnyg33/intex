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
public class Product_Store extends BusinessObject{

  @BusinessObjectField
  private String quantity = null;
  private String product = null;
  private String store = null;
  
  

  /** Creates a new instance of this object */
  public Product_Store(String id) {
      super(id);
  }//constructor



/**
 * @return the quantity
 */
public String getQuantity() {
	return quantity;
}



/**
 * @param quantity the quantity to set
 */
public void setQuantity(String quantity) {
	this.quantity = quantity;
	setDirty();
}



/**
 * @return the product
 */
public String getProduct() {
	return product;
}



/**
 * @param product the product to set
 */
public void setProduct(String product) {
	this.product = product;
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