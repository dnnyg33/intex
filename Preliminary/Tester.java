


public class Tester {

	public static void main(String args[]){
		
//////////Tests for the following
//		1  Create BO using DAO.create
//		2  Get BO using DAO.read
//		3  DAO.read again after clearing cache
//		4  Saving new object (insert)
//		5  Saving retrieved object (update)
//		6  All fields are changed and checked
//		7  getAll testing (check # BOs returned)
//		8  Other search method testing
//		
		try {
			CreateDB.main(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		testEmployee();
		testProduct();
		testStore();
		testProductStore();
	}
	public static void testEmployee(){
		
		EmployeeDAO e = EmployeeDAO.getInstance();//shortcut for empDAO
	//1
		Employee p1 = e.create();
		System.out.println("1. Print the GUID that is created when creating a new employee object: "+p1.getId());
		p1.setFirstName("Billy");
		
	//2	
		Employee p2 = null;
		try {
			p2 = e.read(p1.getId());
		} catch (DataException e1) {
			
			System.out.println("caught in catch 2");
			e1.printStackTrace();
		}//p2 should now be the same as p1
	//	p1.id="employee 1";
		System.out.println("2. Print first name after reading the cache: "+p2.getFirstName());
	//	System.out.println("2. Print the GUID (should be 'employee 1'): "+e.read("employee 1"));
	
	//4
		p2.setMiddleName("Bob");
		p2.setLastName("Thornton");
		p2.setPhone("2345");
		p2.setSalary("1");
		p2.setStore("sandy");
		try {
			e.save(p2);
		} catch (DataException e1) {
			
			System.out.println("caught in catch 4");
			e1.printStackTrace();
		}//inserts, because objectAlreadyInDB is false by default
		System.out.println("4. saved the object to the DB if true: "+ p2.objectAlreadyInDB);
		
	//3	
		Cache.getInstance().clear();
		System.out.println("3. Print first name after clearing cache: " + p2.getFirstName());
		
	//5
		p2.setHireDate("changedTORay");
		try {
			e.save(p2);
		} catch (DataException e1) {
			
			System.out.println("caught in catch 5");
			e1.printStackTrace();
		}
		try {
			System.out.println("5. prints the newly updated object: "+ e.read(p2.id));
		} catch (DataException e1) {
			
			System.out.println("caught in catch 5");
			e1.printStackTrace();
		}//get from database using the objects id
	
	//6
		p2.setFirstName("changed fN");
		p2.setMiddleName("changed mN");
		p2.setLastName("changed lN");
		p2.setHireDate("changed hD");
		p2.setPhone("changed Ph");
		p2.setSalary("changed Sal");
		p2.setStore("changed Sto");
		p2.id=("jon");//TODO create a setter for the ID?
		
	
			try {
				e.save(p2);
			} catch (DataException e1) {
				System.out.println("caught in catch 6");
				e1.printStackTrace();
			}
		
			Employee p3=null;
			try {
			 p3 =	e.read("jon");
			} catch (DataException e2) {
				System.out.println("caught in 6b");
				e2.printStackTrace();
			}
		System.out.println("6. this will print all attributes with the words changed: "+ p3.getFirstName()+" "+ p3.getMiddleName()+" "+p3.getLastName()+" "+ p3.getHireDate()+" "+ p3.getPhone()+" "+p3.getSalary()+ " "+ p3.getStore());
		
	//7
			try {
				System.out.println("7. Prints all employees in the database: " +e.getAll());
			} catch (DataException e1) {
				
				System.out.println("caught in catch 7");
				e1.printStackTrace();
			}
		
	//8
		System.out.println("8. prints a list of every object with the lastname 'changed lN': "+e.searchByLastName("changed lN"));
		System.out.println("8. should only print one object's memory address");
		
		
		
	}
	public static void testStore(){
		
		StoreDAO e = StoreDAO.getInstance();//shortcut for storeDAO
		
		
	//1
		Store p1 = e.create();
		System.out.println("1. Print the GUID that is created when creating a new store object: "+p1.getId());
		p1.setLocation("SANDY");
		
	//2	
		Store p2 = null;
		try {
			p2 = e.read(p1.getId());
		} catch (DataException e1) {
			
			System.out.println("caught in catch 2");
			e1.printStackTrace();
		}//p2 should now be the same as p1
	//	p1.id="store 1";
		System.out.println("2. Print first name after reading the cache: "+p2.getLocation());
	//	System.out.println("2. Print the GUID (should be 'store 1'): "+e.read("store 1"));
	
	//4
		p2.setAddress("address");
		p2.setCity("City");
		p2.setPhone("2345");
		p2.setState("STATE");
		p2.setZipcode("Zipcode");
		try {
			e.save(p2);
		} catch (DataException e1) {
			
			System.out.println("caught in catch 4");
			e1.printStackTrace();
		}//inserts, because objectAlreadyInDB is false by default
		System.out.println("4. saved the object to the DB if true: "+ p2.objectAlreadyInDB);
		
	//3	
		Cache.getInstance().clear();
		System.out.println("3. Print first name after clearing cache: " + p2.getLocation());
		
	//5
		p2.setAddress("changedTORay");
		try {
			e.save(p2);
		} catch (DataException e1) {
			
			System.out.println("caught in catch 5");
			e1.printStackTrace();
		}
		try {
			System.out.println("5. prints the newly updated object: "+ e.read(p2.id));
		} catch (DataException e1) {
			
			System.out.println("caught in catch 5");
			e1.printStackTrace();
		}//get from database using the objects id
	
	//6
		p2.setAddress("changed Add");
		p2.setCity("changed City");
		p2.setPhone("changed Phone");
		p2.setState("Changed state");
		p2.setZipcode("changed zipcode");
		p2.setLocation("changed location");
		
			p2.id=("jon");//TODO create a setter for the ID?
		
	
			try {
				e.save(p2);
			} catch (DataException e1) {
				System.out.println("caught in catch 6");
				e1.printStackTrace();
			}
		
			Store p3=null;
			try {
			 p3 =	e.read("jon");
			} catch (DataException e2) {
				System.out.println("caught in 6b");
				e2.printStackTrace();
			}
		System.out.println("6. this will print all attributes with the words changed: "+ p3.getAddress()+" "+ p3.getCity()+" "+p3.getId()+" "+ p3.getLocation()+" "+ p3.getManager()+" "+p3.getPhone()+ " "+ p3.getState()+" "+p3.getZipcode());
		
	//7
			try {
				System.out.println("7. Prints all stores in the database: " +e.getAll());
			} catch (DataException e1) {
				
				System.out.println("caught in catch 7");
				e1.printStackTrace();
			}
		
	//8
		System.out.println("8. prints a list of every object with the lastname 'changed lN': "+e.searchByLocation("changed location"));
		System.out.println("8. should only print one object's memory address");
	}
	public static void testProductStore(){
		
		Product_StoreDAO e = Product_StoreDAO.getInstance();//shortcut for empDAO
		
		
	//1
		Product_Store p1 = e.create();
		System.out.println("1. Print the GUID that is created when creating a new Product_Store object: "+p1.getId());
		p1.setProduct("Billy");
		p1.setStore("BillyStore");
		
	//2	
		Product_Store p2 = null;
		try {
			p2 = e.read(p1.getId());
		} catch (DataException e1) {
			
			System.out.println("caught in catch 2");
			e1.printStackTrace();
		}//p2 should now be the same as p1
	//	p1.id="Product_Store 1";
		System.out.println("2. Print productStore after reading the cache: "+p2.getProduct()+p2.getStore());
	//	System.out.println("2. Print the GUID (should be 'Product_Store 1'): "+e.read("Product_Store 1"));
	
	//4
		p2.setQuantity("quantity");
		
		p2.setStore("sandy");
		try {
			e.save(p2);
		} catch (DataException e1) {
			
			System.out.println("caught in catch 4");
			e1.printStackTrace();
		}//inserts, because objectAlreadyInDB is false by default
		System.out.println("4. saved the object to the DB if true: "+ p2.objectAlreadyInDB);
		
	//3	
		Cache.getInstance().clear();
		System.out.println("3. Print first name after clearing cache: " + p2.getProduct()+p2.getStore());
		
	//5
		p2.setQuantity("changedTORay");
		try {
			e.save(p2);
		} catch (DataException e1) {
			
			System.out.println("caught in catch 5");
			e1.printStackTrace();
		}
		try {
			System.out.println("5. prints the newly updated object: "+ e.read(p2.id));
		} catch (DataException e1) {
			
			System.out.println("caught in catch 5");
			e1.printStackTrace();
		}//get from database using the objects id
	
	//6
		p2.setQuantity("changed quantity");
		p2.setStore("changed store");
		p2.setProduct("changed product");
		p2.id=("jon");//TODO create a setter for the ID?
		
	
			try {
				e.save(p2);
			} catch (DataException e1) {
				System.out.println("caught in catch 6");
				e1.printStackTrace();
			}
		
			Product_Store p3=null;
			try {
			 p3 =	e.read("jon");
			} catch (DataException e2) {
				System.out.println("caught in 6b");
				e2.printStackTrace();
			}
		System.out.println("6. this will print all attributes with the words changed: "+ p3.getStore()+" "+ p3.getProduct()+" "+p3.getQuantity());
		
	//7
			try {
				System.out.println("7. Prints all Product_Stores in the database: " +e.getAll());
			} catch (DataException e1) {
				
				System.out.println("caught in catch 7");
				e1.printStackTrace();
			}
		
	//8
		System.out.println("8. prints a list of every object with the lastname 'changed lN': "+e.searchByProductAndStore("changed product", "changed store"));
		System.out.println("8. should only print one object's memory address");
	}
	
	public static void testProduct(){
		/////////TEST Product
		ProductDAO e = ProductDAO.getInstance();//shortcut for empDAO
		
		
	//1
		Product p1 = e.create();
		System.out.println("1. Print the GUID that is created when creating a new Product object: "+p1.getId());
		p1.setProductName("Billy");
		
	//2	
		Product p2 = null;
		try {
			p2 = e.read(p1.getId());
		} catch (DataException e1) {
			
			System.out.println("caught in catch 2");
			e1.printStackTrace();
		}//p2 should now be the same as p1
	//	p1.id="Product 1";
		System.out.println("2. Print first name after reading the cache: "+p2.getProductName());
	//	System.out.println("2. Print the GUID (should be 'Product 1'): "+e.read("Product 1"));
	
	//4
		p2.setAvgCost("avg cost");
		p2.setDescription("description");
		p2.setManufacturer("manufacturer");
		try {
			e.save(p2);
		} catch (DataException e1) {
			
			System.out.println("caught in catch 4");
			e1.printStackTrace();
		}//inserts, because objectAlreadyInDB is false by default
		System.out.println("4. saved the object to the DB if true: "+ p2.objectAlreadyInDB);
		
	//3	
		Cache.getInstance().clear();
		System.out.println("3. Print first name after clearing cache: " + p2.getProductName());
		
	//5
		p2.setAvgCost("changedTORay");
		try {
			e.save(p2);
		} catch (DataException e1) {
			
			System.out.println("caught in catch 5");
			e1.printStackTrace();
		}
		try {
			System.out.println("5. prints the newly updated object: "+ e.read(p2.id));
		} catch (DataException e1) {
			
			System.out.println("caught in catch 5");
			e1.printStackTrace();
		}//get from database using the objects id
	
	//6
		p2.setAvgCost("changed avg cost");
		p2.setDescription("changed description");
		p2.setManufacturer("changed manufacturer");
		p2.setProductName("changed productName");
		p2.id=("jon");//TODO create a setter for the ID?
		
	
			try {
				e.save(p2);
			} catch (DataException e1) {
				System.out.println("caught in catch 6");
				e1.printStackTrace();
			}
		
			Product p3=null;
			try {
			 p3 =	e.read("jon");
			} catch (DataException e2) {
				System.out.println("caught in 6b");
				e2.printStackTrace();
			}
		System.out.println("6. this will print all attributes with the words changed: "+ p3.getAvgCost()+" "+ p3.getDescription()+" "+p3.getId()+" "+ p3.getManufacturer()+" "+" "+p3.getProductName());
		
	//7
			try {
				System.out.println("7. Prints all Products in the database: " +e.getAll());
			} catch (DataException e1) {
				
				System.out.println("caught in catch 7");
				e1.printStackTrace();
			}
		
	//8
		System.out.println("8. prints a list of every object with the lastname 'changed lN': "+e.searchByProductName("changed productName"));
		System.out.println("8. should only print one object's memory address");
	}
}
