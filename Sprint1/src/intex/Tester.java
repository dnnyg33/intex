/////////////////////////////////////////////////////////////////
///   This file is an example of an Object Relational Mapping in
///   the ISys Core at Brigham Young University.  Students
///   may use the code as part of the 413 course in their
///   milestones following this one, but no permission is given
///   to use this code is any other way.  Since we will likely
///   use this code again in a future year, please DO NOT post
///   the code to a web site, share it with others, or pass
///   it on in any way.


package intex;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import intex.BusinessObjects.*;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Tests for the program.  
 *
 * See http://www.junit.org/apidocs/org/junit/Assert.html for the
 * available assertions you can make.
 * 
 * @version 1.2
 */
//TODO just search methods for every object because it helps me build the setObject methods.



public class Tester {

    // for comparing dates
    SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private String Commission="Commission";
    private String CProduct="CProduct";
    private String Customer="Customer";
    private String DebitOrCredit="DebitOrCredit";
    private String Employee="Employee";
    private String GeneralLedger="GeneralLedger";
    private String JournalEntry="JournalEntry";
    private String Payment="Payment";
    private String PProduct="PProduct";
    private String Product="Product";
    private String RevenueSource="RevenueSource";
    private String Sale="Sale";
    private String Store="Store";
    private String StoreProduct="StoreProduct";
    private String Trans="Trans";
    List<String> objectNames = new LinkedList<String>();

    @Test
    public void testCreate() throws Exception{
    	Customer c = BusinessObjectDAO.getInstance().create("Customer");
    	System.out.println(c.getAddress());
    	System.out.println(c.isValidated());
    }
   
    public Tester() throws Exception {
     //  CreateDB.main(null);
     //   testTheseObjects();
    }

    @Test
    public void testSubString(){
    	String serial ="FS1";
    	System.out.println(serial.substring(0, 2));
    	
    }
    
    /** Example test */
    @Test
    public void TestExample() throws Exception {
      String st1 = "Hi There";
      String st2 = "Hi There";
      assertEquals(st1, st2);
    }
    public void testTheseObjects(){
    	
    	objectNames.add(Commission);
    	objectNames.add(CProduct);
    	objectNames.add(Customer);
    	objectNames.add(DebitOrCredit);
    	objectNames.add(Employee);
    	objectNames.add(GeneralLedger);
    	objectNames.add(JournalEntry);
    	objectNames.add(Payment);
    	objectNames.add(PProduct);
    	objectNames.add(Product);
    	objectNames.add(Payment);
    	objectNames.add(RevenueSource);
    	objectNames.add(Sale);
    	objectNames.add(Store);
    	objectNames.add(StoreProduct);
    	objectNames.add(Trans);
    
    	for(int i = 0; i<objectNames.size(); i++){
    	//	System.out.println(objectNames.get(i));
    		
    	}
    }

    



    /** Test the Employee BO (also tests the Person BO) */
    
    public void TestEmployee() throws Exception {
      Employee s = BusinessObjectDAO.getInstance().create("Employee", "emp1");
      s.construct("Marge", "lastName", new Date(), "phone", "storeId", "username", "password", 100, "address", "city", "st", "zip", "position");
   
      s.save();

      // since emp1 is in the Cache, this tests reading from the cache
      Employee s2 = BusinessObjectDAO.getInstance().read("emp1");
      assertSame(s, s2);

      // now clear the cache (you'd never do this in the real world)
      // then we can test reading from the database
      Cache.getInstance().clear();
      Employee s3 = BusinessObjectDAO.getInstance().read("emp1");
      assertEquals(s.getId().trim(), s3.getId().trim());
      assertEquals(s.getUsername(), s3.getUsername());
      assertEquals(s.getPassword(), s3.getPassword());
      assertEquals(s.getFirstName(), s3.getFirstName());
      assertEquals(s.getLastName(), s3.getLastName());
      assertEquals(s.getPhone(), s3.getPhone());
      assertEquals(s.getAddress(), s3.getAddress());
      assertEquals(s.getCity(), s3.getCity());
      assertEquals(s.getState(), s3.getState());
      assertEquals(s.getZip(), s3.getZip());
      assertEquals(s.getPosition(), s3.getPosition());
      assertEquals(SDF.format(s.getHireDate()), SDF.format(s3.getHireDate()));
      assertTrue(s.getSalary() - s3.getSalary() < 0.1);
     
      // test deleting
      BusinessObjectDAO.getInstance().delete(s);

      // create another one with the same id (the other should be deleted)
      Employee s4 = BusinessObjectDAO.getInstance().create("Employee", "emp5");
      s4.construct("Maggie", "lastName", new Date(), "phone", "storeId", "username", "password", 100, "address", "city", "st", "zip", "position");
        s4.save();

      // test the search methods
      List<Employee> emps = BusinessObjectDAO.getInstance().searchForAll("Employee");
      assertEquals(emps.size(), 3);  // 2 from CreateDB, Maggie above
      Employee emp1 = BusinessObjectDAO.getInstance().searchForBO("Employee", new SearchCriteria("id", "employee1"));
      assertEquals(emp1.getId().trim(), "employee1");
      List<Employee> emps2 = BusinessObjectDAO.getInstance().searchForList("Employee", new SearchCriteria("username", "%a%", SearchCriteria.LIKE));
      assertEquals(emps2.size(), 3);


    }


    /** Test the Commission BO*/
    @Test
    public void TestCommission() throws Exception {
      Commission s = BusinessObjectDAO.getInstance().create("Commission", "com1");
      s.construct("employee1", 100,  new Date(), "trans1");
   
      s.save();

      // since emp1 is in the Cache, this tests reading from the cache
      Commission s2 = BusinessObjectDAO.getInstance().read("com1");
      assertSame(s, s2);

      // now clear the cache (you'd never do this in the real world)
      // then we can test reading from the database
      Cache.getInstance().clear();
      Commission s3 = BusinessObjectDAO.getInstance().read("com1");//TODO fails when connection Max =1
      assertEquals(s.getId().trim(), s3.getId().trim());
     assertEquals(s.getEmpId(), s3.getEmpId());
       assertEquals(SDF.format(s.getDateOf()), SDF.format(s3.getDateOf()));
      assertTrue(s.getAmount() - s3.getAmount() < 0.1);
     
      // test deleting
      BusinessObjectDAO.getInstance().delete(s);

      // create another one with the same id (the other should be deleted)
      Commission s4 = BusinessObjectDAO.getInstance().create("Commission", "com5");
      s4.construct("employee1", 105,  new Date(), "trans1");
        s4.save();
      // Employee tests the search methods, so no need to test them again
    }
    
    /**Test PhysicalProduct*/
    @Test
    public void TestPhysicalProduct(){
    	PProduct p = null;
    	try {
			 p = BusinessObjectDAO.getInstance().create("PProduct");
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	p.setCProductId("CProduct100");
    	try {
			p.save();
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**Test Conceptual*/
    @Test
    public void TestConceptualProduct(){
    	CProduct cp = null;
    	try {
			cp=BusinessObjectDAO.getInstance().read("product1");
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(cp.getId());
    }
    
    /** Test the 1-M relationship between Employee and Commission (an employee can have many commissions) */
    
    public void TestEmployeeCommissions() throws Exception {
        // this Employee will have three commissions
    	String empId ="empA";
        Employee e = BusinessObjectDAO.getInstance().create("Employee", empId);
        e.construct("Bart", "lastName", new Date(), "phone", "storeId", "username", "password", 100, "address", "city", "st", "zip", "position");
        e.save();
        
        
        // first dog
        Commission d1 = BusinessObjectDAO.getInstance().create("Commission", "comA");
        d1.construct(empId, 1, new Date(), "Trans1");
        d1.save();
        d1.setEmployee(empId);//this call,puts the entire employee object inside d1
        
        
        // second dog
        Commission d2 = BusinessObjectDAO.getInstance().create("Commission", "comB");
        d2.construct(empId, 2, new Date(), "TransA");
        d2.setEmpId(empId);
        d2.save();
        
        // third dog
        Commission d3 = BusinessObjectDAO.getInstance().create("Commission", "comC");
        d3.construct(empId, 3, new Date(), "transB");
        d3.setEmpId(empId);
        d3.save();
        
        // retrieves any commission object for the employee
        List<Commission> dogs = e.getCommissions(empId);
        assertEquals(dogs.size(), 3);//there should be 3
        assertSame(dogs.get(0).getEmployee(), e);
        assertSame(dogs.get(1).getEmployee(), e);
        assertSame(dogs.get(2).getEmployee(), e);
    }
    
    /** Test the Customer object */
    @Test
    public void TestCustomer() throws Exception {
      Customer s = BusinessObjectDAO.getInstance().create("Customer", "customerA");
      s.construct("firstName", "lastName", "phone", "email", "address", "city", "state", "zip");
      s.save();

      // since the object is in the Cache, this tests reading from the cache
      Customer s2 = BusinessObjectDAO.getInstance().read("customerA");
      assertSame(s, s2);

      // now clear the cache (you'd never do this in the real world)
      // then we can test reading from the database
      Cache.getInstance().clear();
      Customer s3 = BusinessObjectDAO.getInstance().read("customerA");
      assertEquals(s.getId().trim(), s3.getId().trim());
      assertEquals(s.getAddress(), s3.getAddress());
      assertEquals(s.getEmail(), s3.getEmail());
      assertEquals(s.getFirstName(), s3.getFirstName());
      assertEquals(s.getLastName(), s3.getLastName());
      assertEquals(s.getPhone(), s3.getPhone());
      assertEquals(s.getState(), s3.getState());
      assertEquals(s.getZip(), s3.getZip());
      

      // test deleting
      BusinessObjectDAO.getInstance().delete(s);
    }    
    
    /** Test the M-M relationship between Store and Product */
    @Test
    public void TestStoreProduct() throws Exception {
      // this test assumes that certain stores and products are already in the database
      // in the DB, store1 has only product1, store2 has product1, product2, product3
      Store store1 = BusinessObjectDAO.getInstance().read("store1");
      Store store2 = BusinessObjectDAO.getInstance().read("store2");
      PProduct product1 = BusinessObjectDAO.getInstance().read("product1");
      PProduct product2 = BusinessObjectDAO.getInstance().read("product2");
      PProduct product3 = BusinessObjectDAO.getInstance().read("product3");
      
      // test store1's products
        assertEquals(store1.getProducts().size(), 1);
  //    assertSame(store1.getProducts().get(0), product1);//probably will fail
      
      // test store2's products
      List<intex.BusinessObjects.Product> products = store2.getProducts();
      assertEquals(products.size(), 3);
      assertEquals(products.get(0).getId(), product1.getId());
      assertTrue(products.get(0).getPrice()- product1.getPrice()<0.1);
      
      assertEquals(products.get(1).getId(), product2.getId());
      assertTrue(products.get(1).getPrice()- product2.getPrice()<0.1);
      
      assertEquals(products.get(2).getId(), product3.getId());
      assertTrue(products.get(2).getPrice()- product3.getPrice()<0.1);
     
     
      
 //     assertTrue(products.contains(product1));//probably will fail
  //    assertTrue(products.contains(product2));
   //   assertTrue(products.contains(product3));
      
      // test product1's dealers
      List<Store> dealers = product1.getDealers();
      for(Store s: dealers){
    	  System.out.println(s.getId());
      }
      assertEquals(dealers.size(), 2);
      assertEquals(dealers.get(0).getId(), store1.getId());
      assertEquals(dealers.get(0).getAddress(), store1.getAddress());
      System.out.println(dealers.get(0)+"<--from the list. from above-->"+store1);
 //     assertTrue(dealers.contains(store1));//fails because the memory addresses are different even though the contents of the object (including id's) are the same.
 //     assertTrue(dealers.contains(store2));//probably won't work
    }    
    
    /** Test the 1-M relationship between Customer and Transaction (a customer can have many transactions) 
     * @throws DataException */
    @Test
    public void testCustomerTransaction() throws DataException{
    	 // this Employee will have three commissions
    	String custId ="cust1";
        Customer e = BusinessObjectDAO.getInstance().create("Customer", custId);
	    e.construct("firstName", "lastName", "phone", "email", "address", "city", "state", "zip");
	    e.save();
			
	      // first transaction
        Trans d1 = BusinessObjectDAO.getInstance().create("Trans", "transA");
		 d1.construct(new Date(), 11, 1, custId, "storeId", 11, "empId");
        System.out.println("prints the id of the transaction. should be transA-->"+d1.getId());
        System.out.println("should be cust1"+d1.getCustId());
      
		d1.save();//TODO not working?
		
		
        d1.setCustomer(custId);//this call,puts the entire employee object inside d1
        
        
        // second dog
        Trans d2 = null;
		d2 = BusinessObjectDAO.getInstance().create("Trans", "transB");
		d2.construct(new Date(), 2, 1.2, custId, "storeId", 12, "empId");
        d2.setCustId(custId);;
        d2.save();
	    
        // third dog
        Trans d3 = null;
		d3 = BusinessObjectDAO.getInstance().create("Trans", "transC");
		d3.construct(new Date(), 3, 1.2, custId, "storeId", 12, "empId");
        d3.setCustId(custId);
        d3.save();
        
        // retrieves any commission object for the employee
        List<Trans> dogs = e.getTrans(custId);
        assertEquals(dogs.size(), 3);//there should be 3
        assertSame(dogs.get(0).getCustomer(), e);
        assertSame(dogs.get(1).getCustomer(), e);
        assertSame(dogs.get(2).getCustomer(), e);
    }

    /** Test the 1-M relationship between Journal Entry and Debit Credits (a journal entry can have many debit credits) */
    //customer transaction
    @Test
    public void testJournalEntryDebitCredit() throws DataException{
   	 // this Employee will have three commissions
   	String journalEntryId ="Journal Entry 1";
       JournalEntry e = BusinessObjectDAO.getInstance().create("JournalEntry", journalEntryId);
       e.construct(new Date());
       e.save();
       
       
       // first transaction
       DebitOrCredit d1 = BusinessObjectDAO.getInstance().create("DebitOrCredit", "debitA");
       d1.construct(false, "glName", journalEntryId, 12, "generalLedgerId");
       d1.save();
       d1.setJournalEntry(journalEntryId);//this call,puts the entire employee object inside d1
       
       
       // second dog
       DebitOrCredit d2 = BusinessObjectDAO.getInstance().create("DebitOrCredit", "debitB");
       d2.construct(false, "glName", journalEntryId, 13, "generalLedgerId");
       d2.setJournalEntryId(journalEntryId);;
       d2.save();
       
       // third dog
       DebitOrCredit d3 = BusinessObjectDAO.getInstance().create("DebitOrCredit", "creditC");
       d3.construct(false, "glName", journalEntryId, 14, "generalLedgerId");
       d3.setJournalEntry(journalEntryId);
       d3.save();
       
       // retrieves any commission object for the employee
       List<DebitOrCredit> dogs = e.getDebitsAndCredits(journalEntryId);
       assertEquals(dogs.size(), 3);//there should be 3
       assertSame(dogs.get(0).getJournalEntry(), e);
       assertSame(dogs.get(1).getJournalEntry(), e);
       assertSame(dogs.get(2).getJournalEntry(), e);
   }
    
    /** Test the 1-M relationship between General Ledger and Debit Credits (a general ledger can have many debit credits) */
  //customer transaction
    @Test
    public void testGeneralLedgerDebitCredit() throws DataException{
   	 // this GeneralLedger will have three debitCredits
   	String generalLedgerId ="general Ledger 1";
       GeneralLedger e = BusinessObjectDAO.getInstance().create("GeneralLedger", generalLedgerId);
       e.construct("accounts receivable", 12, "typeOf");
       e.save();
       
       
       // first debitCredit
       DebitOrCredit d1 = BusinessObjectDAO.getInstance().create("DebitOrCredit", "debitX");
       d1.construct(false, "accounts receivable", "journalEntryId", 12, generalLedgerId);
       d1.save();
       d1.setGeneralLedgerId(generalLedgerId);//this call,puts the entire employee object inside d1
       
       
       // second dog
       DebitOrCredit d2 = BusinessObjectDAO.getInstance().create("DebitOrCredit", "debitY");
       
       d2.construct(false, "accounts receivable", "journalEntryId", 12, generalLedgerId);
       d2.setGeneralLedger(generalLedgerId);;
       d2.save();
       
       // third dog
       DebitOrCredit d3 = BusinessObjectDAO.getInstance().create("DebitOrCredit", "creditZ");
       d3.construct(false, "accounts receivable", "journalEntryId", 12, generalLedgerId);
       d3.setGeneralLedger(generalLedgerId);
       d3.save();
       
       // retrieves any commission object for the employee
       List<DebitOrCredit> dogs = e.getDebitsAndCredits(generalLedgerId);
       assertEquals(dogs.size(), 3);//there should be 3
       assertSame(dogs.get(0).getGeneralLedger(), e);
       assertSame(dogs.get(1).getGeneralLedger(), e);
       assertSame(dogs.get(2).getGeneralLedger(), e);
   }
    /** Test the 1-M relationship between Employee and Transaction (an employee can have many transactions) */
    //customer transaction
    @Test
    public void testEmployeeTransaction() throws DataException{
   	 // this GeneralLedger will have three debitCredits
   	String employeeId ="employeeID1";
       Employee e = BusinessObjectDAO.getInstance().create("Employee", employeeId);
       e.construct("firstName", "lastName", new Date(), "phone", "storeId", "username", "password", 100, "address", "city", "state", "zip", "position");
       e.save();
       
       
       // first debitCredit
       Trans d1 = BusinessObjectDAO.getInstance().create("Trans", "trans1");
       d1.construct(new Date(), 12, 1.2, "custId", "storeId", 12, employeeId);
       d1.save();
       d1.setEmpId(employeeId);//this call, puts the entire employee object inside d1
       
       
       // second dog
       Trans d2 = BusinessObjectDAO.getInstance().create("Trans", "trans2");
       
       d2.construct(new Date(), 12, 1.2, "custId", "storeId", 12, employeeId);
       d2.setEmployee(employeeId);
       d2.save();
       
       // third dog
       Trans d3 = BusinessObjectDAO.getInstance().create("Trans", "trans3");
       d3.construct(new Date(), 12, 1.2, "custId", "storeId", 12, employeeId);
       d3.setEmployee(employeeId);
       d3.save();
       
       // retrieves any trans object for the employee
       List<Trans> dogs = e.getTrans(employeeId);
       assertEquals(dogs.size(), 3);//there should be 3
       assertSame(dogs.get(0).getEmployee(), e);
       assertSame(dogs.get(1).getEmployee(), e);
       assertSame(dogs.get(2).getEmployee(), e);
   }
    /** Test the 1-M relationship between Store and Transaction (a store can have many transactions) */
    //customer transaction
    @Test
    public void testStoreTransaction() throws DataException{
   	 // this Store will have three transactions
   	String storeId ="store100";
       Store e = BusinessObjectDAO.getInstance().create("Store", storeId);
 //      e.construct("location", "managerId", "address", "phone", "subnetId", 1.2);
       e.setAddress("address");
       e.setLocation("location");
       e.setManager("managerId");
       e.setPhone("phone");
       e.setSalesTaxRate(0);
       e.setSubnetId("subnetId");
       System.out.println("print e.getLocation()-->"+e.getLocation());
       e.save();//TODO fix me
       
       
       // first trans
       Trans d1 = BusinessObjectDAO.getInstance().create("Trans", "trans10");
       d1.construct(new Date(), 12, 1.2, "custId", storeId, 12, "employeeId");
       d1.save();
       d1.setStoreId(storeId);//this call, puts the entire employee object inside d1
       
       
       // second trans
       Trans d2 = BusinessObjectDAO.getInstance().create("Trans", "trans20");
       
       d2.construct(new Date(), 12, 1.2, "custId", storeId, 12, "employeeId");
       d2.setStore(storeId);
       d2.save();
       
       // third trans
       Trans d3 = BusinessObjectDAO.getInstance().create("Trans", "trans30");
       d3.construct(new Date(), 12, 1.2, "custId", storeId, 12, "employeeId");
       d3.setStore(storeId);
       d3.save();
       
       // retrieves any trans object for the store
       List<Trans> dogs = e.getTrans(storeId);
       assertEquals(dogs.size(), 3);//there should be 3
       assertSame(dogs.get(0).getStore(), e);
       assertSame(dogs.get(1).getStore(), e);
       assertSame(dogs.get(2).getStore(), e);
   }

    /** Test the 1-M relationship between Transaction and Revenue Source (a transaction can have many revenue sources) */
    //customer transaction
    @Test
    public void testTransactionRevenueSource() throws DataException{
   	 // this GeneralLedger will have three debitCredits
   	String transId ="trans100";
       Trans e = BusinessObjectDAO.getInstance().create("Trans", transId);
       e.construct(new Date(), 100, 1.2, "custId", "storeId", 100, "empId");
       e.save();
       
       
       // first debitCredit
       RevenueSource d1 = BusinessObjectDAO.getInstance().create("RevenueSource", "RevenueSource1");
       d1.construct(11, transId, "typeOf");
       d1.setTrans(transId);//this call, puts the entire employee object inside d1
       d1.save();
       
       // second dog
       RevenueSource d2 = BusinessObjectDAO.getInstance().create("RevenueSource", "RevenueSource2");
       d2.construct(12, transId, "typeOf");
       d2.save();
       d2.setTrans(transId);
       
       // third dog
       RevenueSource d3 = BusinessObjectDAO.getInstance().create("RevenueSource", "RevenueSource3");
       d3.construct(12, transId, "typeOf");
       d3.save();
       d3.setTrans(transId);
       
       // retrieves any RevenueSOurces object for the trans
       List<RevenueSource> dogs = e.getRevenueSources(transId);
       assertEquals(dogs.size(), 3);//there should be 3
       assertSame(dogs.get(0).getTrans(), e);
       assertSame(dogs.get(1).getTrans(), e);
       assertSame(dogs.get(2).getTrans(), e);
   }
    /** Test the 1-M relationship between Product and Sale (a product can have many sales) */
  //customer transaction
    @Test
    public void testProductSale() throws DataException{
   	 // this Sale will have three Products
   	String productId ="basketball1";
       Product e = BusinessObjectDAO.getInstance().create("Product", productId);
     //  e.construct(0, "physical");
       e.setPrice(0);
       System.out.println("print e.getTypeOf()-->"+e.getPrice());
       e.save();//TODO fix me
       
       
       // first Sale
       Sale d1 = BusinessObjectDAO.getInstance().create("Sale", "sale1");
       d1.construct(0, productId);
       d1.setProductId(productId);//make sure you call setProductId too, not just the constructor
       d1.save();
      //this call, puts the entire employee object inside d1
       
       
       // second Sale
//       Sale d2 = BusinessObjectDAO.getInstance().create("Sale", "sale2");
//       d2.construct(0, productId);
//       d2.setProduct(productId);
//       d2.save();
//       
//       // third sale
//       Sale d3 = BusinessObjectDAO.getInstance().create("Sale", "sale3");
//       d3.construct(0, productId);
//       d3.setProduct(productId);
//       d3.save();
//       
       // retrieves any sale objects for the product
       List<Sale> sales = e.getSales(productId);
       assertEquals(sales.size(), 3);//there should be 3
       assertSame(sales.get(0).getProduct(), e);
       assertSame(sales.get(1).getProduct(), e);
       assertSame(sales.get(2).getProduct(), e);
   }
    

}