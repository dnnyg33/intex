package intex.BusinessObjects;

import intex.DataException;
import intex.SearchCriteria;

import java.util.LinkedList;
import java.util.List;


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
 * Product BO
 * 
 * @author Group 2-3
 * @version 1.1
 */
public abstract class Product extends BusinessObject {

  public void construct(double price) {
		this.price = price;
		//this.typeOf = typeOf;
	}

  @BusinessObjectField
  private double price;
  @BusinessObjectField
  private String typeOf = null;
  @BusinessObjectField
  private String model;
  
  

  /** Creates a new instance of this object */
  public Product(String id) {
      super(id);
  }//constructor

  /**abstract method
 * @return */
  public abstract String getSku();
  
  /**abstract method*/
  public abstract String getSerialNum();
  
  /**abstract method*/
  public abstract String getDescription(); 
  
  /**abstract method*/
  public abstract void setDescription(String description);


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
setDirty();}


//
///**
// * @return the typeOf
// */
//public String gettypeOf() {
//	return typeOf;
//}
//
//
//
///**
// * @param typeOf the typeOf to set
// */
//public void typeOf(String typeOf) {
//	this.typeOf = typeOf;
//setDirty();}
//


/** 
 * Returns the StoreProduct relationship objects that describe the stores that sell this particular product.
 */
public List<StoreProduct> getStoreProduct() throws DataException {
  return BusinessObjectDAO.getInstance().searchForList("StoreProduct", new SearchCriteria("productid", id));
}

/**
 * Retrieves the actual store objects that sell this product.
 * This is a convenience method to traverse the intermediary table.
 */
public List<Store> getDealers() throws DataException {
  List<Store> dealers = new LinkedList<Store>();
  for (StoreProduct sp: this.getStoreProduct()){
    dealers.add(sp.getDealer());
  }
  return dealers;    
}

//
//
///**
// * @return the saleId
// */
//public String getSaleId() {
//	return saleId;
//}
//
//
//
///**
// * @param saleId the saleId to set
// */
//public void setSaleId(String saleId) {
//	setSale(saleId);
//	setDirty();
//}



///**
// * @return the sale
// */
//public Sale getSale() {
//	
//	return sale;
//}
//
//
//
///**
// * @param sale the sale to set
// */
//public void setSale(String saleId) {
//	try {
//		this.sale = BusinessObjectDAO.getInstance().searchForBO("Sale", new SearchCriteria("saleid", saleId));
//	} catch (DataException e) {
//		System.out.println("couldn't find the saleId");
//		e.printStackTrace();
//	}
//	setDirty();
//}


/**
 * Retrieves a list of sales that include this product.
 * This is a convenience method.
 */
public List<intex.BusinessObjects.Sale> getSales(String productId) {
	List<Sale> sales=null;
	try {
		sales = BusinessObjectDAO.getInstance().searchForList("Sale", new SearchCriteria("productid", productId));
	} catch (DataException e) {
		e.printStackTrace();
	}
return sales;
}



/**
 * @return the typeOf
 */
public  String getTypeOf(){
	return typeOf;
}



/**
 * @param typeOf the typeOf to set
 */
public void setTypeOf(String typeOf) {
	this.typeOf = typeOf;
}



public String getModel() {
	return model;
}



/**
 * @param description the description to set
 */
public void setModel(String model) {
	this.model = model;
}
/**abstract method*/
public abstract double getAverageCost();
/**abstract method*/
public abstract double getFullCommissionRate();
/**abstract method*/
public abstract void setCProduct(String CProductId);
/**abstract method*/
public abstract String getCProductId();
/**abstract method*/
public abstract void setAverageCost(double valueOf);
/**abstract method*/
public abstract void setCommissionRate(double valueOf);
/**abstract method*/
public abstract void setSerialNum(String text);
/**abstract method*/
public abstract void setCost(double valueOf);
/**abstract method*/
public abstract double getCost();
/**abstract method*/
public abstract String getManufacturer();




}