package intex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import intex.BusinessObjects.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;


public class Sprint2Tester{
	
	public Sprint2Tester() throws Exception{
		CreateDB.main(null);
	}

	@Test
	/**Test the ForSale BO*/
	public void TestForSale() throws Exception{
	    

	      ForSale s = BusinessObjectDAO.getInstance().create("ForSale", "ForSale44");
	      s.construct("type44", "newUsed44", "serial");
	      
	      s.save();

	      // since emp1 is in the Cache, this tests reading from the cache
	      ForSale s2 = BusinessObjectDAO.getInstance().read("ForSale44");
	      assertSame(s, s2);

	      // now clear the cache (you'd never do this in the real world)
	      // then we can test reading from the database
	      Cache.getInstance().clear();
	      ForSale s3 = BusinessObjectDAO.getInstance().read("ForSale44");
	      assertEquals(s.getId().trim(), s3.getId().trim());
	      assertEquals(s.getNewUsed(), s3.getNewUsed());
	      assertEquals(s.getSerial(), s3.getSerial());
	      assertEquals(s.getTypeof(), s3.getTypeOf());//these have different names
	         
	      // test deleting
	      BusinessObjectDAO.getInstance().delete(s);

	      // create another one with the same id (the other should be deleted)
	      ForSale s4 = BusinessObjectDAO.getInstance().create("ForSale", "ForSale44");
	      s4.construct("type45", "newUsed45", "serial45");
	      s4.save();

	      // test the search methods
	      List<ForSale> emps = BusinessObjectDAO.getInstance().searchForAll("ForSale");
	      assertEquals(emps.size(), 4);  // 3 from CreateDB, Maggie above
	      ForSale emp1 = BusinessObjectDAO.getInstance().searchForBO("ForSale", new SearchCriteria("id", "ForSale44"));
	      assertEquals(emp1.getId().trim(), "ForSale44");
	      List<ForSale> emps2 = BusinessObjectDAO.getInstance().searchForList("ForSale", new SearchCriteria("serial", "%4%", SearchCriteria.LIKE));
	      assertEquals(emps2.size(), 1);
	}
	
	/**Test the ForRent BO*/
	@Test
	public void TestForRent() throws Exception{
	    

	      ForRent s = BusinessObjectDAO.getInstance().create("ForRent", "ForRent44");
	      s.construct(44, "serial44", "typeOf44");
	      
	      s.save();

	      // since emp1 is in the Cache, this tests reading from the cache
	      ForRent s2 = BusinessObjectDAO.getInstance().read("ForRent44");
	      assertSame(s, s2);

	      // now clear the cache (you'd never do this in the real world)
	      // then we can test reading from the database
	      Cache.getInstance().clear();
	      ForRent s3 = BusinessObjectDAO.getInstance().read("ForRent44");
	      assertEquals(s.getId().trim(), s3.getId().trim());
	      assertEquals(s.getTimesRented(), s3.getTimesRented());
	      assertEquals(s.getSerial(), s3.getSerial());
	      assertEquals(s.getTypeof(), s3.getTypeOf());//these have different names
	         
	      // test deleting
	      BusinessObjectDAO.getInstance().delete(s);

	      // create another one with the same id (the other should be deleted)
	      ForRent s4 = BusinessObjectDAO.getInstance().create("ForRent", "ForRent44");
	      s4.construct(45, "serial45", "tyepof45");
	      s4.save();

	}

	/**Test the Membership BO*/
	@Test
	public void TestMembership() throws Exception{
	    

	      Membership s = BusinessObjectDAO.getInstance().create("Membership", "Membership44");
	      s.construct("creditCard44", new Date(), new Date(), "securityCode44", false, "cust44");
	      
	      s.save();

	      // since emp1 is in the Cache, this tests reading from the cache
	      Membership s2 = BusinessObjectDAO.getInstance().read("Membership44");
	      assertSame(s, s2);

	      // now clear the cache (you'd never do this in the real world)
	      // then we can test reading from the database
	      Cache.getInstance().clear();
	      Membership s3 = BusinessObjectDAO.getInstance().read("Membership44");
	      assertEquals(s.getId().trim(), s3.getId().trim());
	      assertEquals(s.getCreditCard(), s3.getCreditCard());
	      assertEquals(s.getCustId(), s3.getCustId());
	      assertEquals(s.getSecurityCode(), s3.getSecurityCode());
	      assertSame(s.getExpirationDate(), s3.getExpirationDate());
	      assertEquals(s.getStartDate(), s3.getStartDate());
	         
	      // test deleting
	      BusinessObjectDAO.getInstance().delete(s);

	      // create another one with the same id (the other should be deleted)
	      Membership s4 = BusinessObjectDAO.getInstance().create("Membership", "Membership44");
	      s4.construct("creditCard", new Date(), new Date(), "securityCode45", false, "custId45");

	}
	/**Test the Rental BO*/
	@Test
	public void TestRental() throws Exception{
	    

	      Rental s = BusinessObjectDAO.getInstance().create("Rental", "Rental44");
	      s.construct(new Date(), new Date(), new Date(), false, "forRentId44");
	      
	      s.save();

	      // since emp1 is in the Cache, this tests reading from the cache
	      Rental s2 = BusinessObjectDAO.getInstance().read("Rental44");
	      assertSame(s, s2);

	      // now clear the cache (you'd never do this in the real world)
	      // then we can test reading from the database
	      Cache.getInstance().clear();
	      Rental s3 = BusinessObjectDAO.getInstance().read("Rental44");
	      assertEquals(s.getId().trim(), s3.getId().trim());
	      assertEquals(s.getDateDue(), s3.getDateDue());
	      assertEquals(s.getDateIn(), s3.getDateIn());
	      assertEquals(s.getDateOut(), s3.getDateOut());
	      assertEquals(s.getForRentId(), s3.getForRentId());
	      assertEquals(s.isReminderSent(), s3.isReminderSent());
	         
	      // test deleting
	      BusinessObjectDAO.getInstance().delete(s);

	      // create another one with the same id (the other should be deleted)
	      Rental s4 = BusinessObjectDAO.getInstance().create("Rental", "Rental44");
	     

	}
	/**Test the CRental BO*/
	@Test
	public void TestCRental() throws Exception{
	    

	      CRental s = BusinessObjectDAO.getInstance().create("CRental", "CRental44");
	      s.construct(44, 444);
	      
	      s.save();

	      // since emp1 is in the Cache, this tests reading from the cache
	      CRental s2 = BusinessObjectDAO.getInstance().read("CRental44");
	      assertSame(s, s2);

	      // now clear the cache (you'd never do this in the real world)
	      // then we can test reading from the database
	      Cache.getInstance().clear();
	      CRental s3 = BusinessObjectDAO.getInstance().read("CRental44");
	      assertEquals(s.getId().trim(), s3.getId().trim());
	      assertTrue(s.getPricePerDay()-s3.getPricePerDay()<.1);
	      assertTrue(s.getReplacementPrice()- s3.getReplacementPrice()<.1);
	    
	         
	      // test deleting
	      BusinessObjectDAO.getInstance().delete(s);

	      // create another one with the same id (the other should be deleted)
	      CRental s4 = BusinessObjectDAO.getInstance().create("CRental", "CRental44");
	     

	}

}
