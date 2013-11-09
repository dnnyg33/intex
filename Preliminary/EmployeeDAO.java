import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */

/**
 * @author dnnyg33
 *
 */
public class EmployeeDAO {

	List<Employee> results;

	/**Singleton Code*/
	private static EmployeeDAO instance = null;
	
	/**Creates a new instance of EmployeeDAO*/
	private EmployeeDAO(){
		
	
	}
	
	/**Returns the singleton instance of the EmployeeDAO*/
	public static synchronized EmployeeDAO getInstance(){
		if(instance == null){
		 instance = new EmployeeDAO();
	}
		return instance;
		
	}
	/** ////////////////////
	///Create*/
	public Employee create(){
		String id = GUID.generate();
		Employee d = new Employee(id);
		
		Cache.getInstance().put(d.getId(), d);
		
		return d;
	}
	
	/** ////////////////////
	///Read*/
	public Employee read(String id) throws DataException{
	//check the cache
		Employee d =  (Employee)Cache.getInstance().get(id);
		if (d != null){
			return d;
		}
		
	//get connection from the pool
		Connection conn = null;
		conn = ConnectionPool.getInstance().get();
	//prepared statement
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try {
		 pstmt = conn.prepareStatement("SELECT * FROM employee WHERE id=?");
	
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
		
	if (rs.next()){
	 d = new Employee(id);
	d.setFirstName(rs.getString("firstname"));//column names from the data base
	d.setMiddleName(rs.getString("middlename"));
	d.setLastName(rs.getString("lastname"));
	d.setHireDate(rs.getString("hiredate"));
	d.setPhone(rs.getString("phone"));
	d.setSalary(rs.getString("salary"));
	d.setStore("store");
	d.setObjectAlreadyInDB(true);
	d.setDirty(false);
	
	}else{//no object!!;
	throw new DataException("No product found with the given id");}//forces the caller to have a try catch block
	//TODO should i do?: return null;
	
	}catch (SQLException e) {
		//  Auto-generated catch block
		e.printStackTrace();}
	
	finally {
		//close connectionTODO
		ConnectionPool.getInstance().release(conn);
		}
		return d;
	}
	/** ////////////////////
	///Save*/
	public void save(Employee e) throws DataException{
		//is it dirty?
		if(!e.isDirty()){//if not
			return;//don't bother saving
		}
		//otherwise
		Connection conn = ConnectionPool.getInstance().get();
		if(e.isObjectAlreadyInDB()){
			//TODO just update
			try {
				String sql = "UPDATE employee SET id = ?, firstname = ?, middlename = ?, lastname = ?, hiredate = ?, phone = ?, salary = ?, store = ? WHERE id = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setString(2, e.getFirstName());
				ps.setString(3, e.getMiddleName());
				ps.setString(4, e.getLastName());
				ps.setString(5, e.getHireDate());
				ps.setString(6, e.getPhone());
				ps.setString(7, e.getSalary());
				ps.setString(8, e.getStore());
				ps.setString(1, e.getId());
				ps.setString(9, e.getId());
				ps.executeUpdate();
			//	e.setObjectAlreadyInDB(true);
				e.setDirty(false);
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			finally {
				ConnectionPool.getInstance().release(conn);
			}//end try/catch
			//release connection
		}
		else{//insert
			//prepare the statement
			try {
				String sql = "INSERT INTO employee SET id = ?, firstname = ?, middlename = ?, lastname = ?, hiredate = ?, phone = ?, salary = ?, store = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setString(2, e.getFirstName());
				ps.setString(3, e.getMiddleName());
				ps.setString(4, e.getLastName());
				ps.setString(5, e.getHireDate());
				ps.setString(6, e.getPhone());
				ps.setString(7, e.getSalary());
				ps.setString(8, e.getStore());
				ps.setString(1, e.getId());
				ps.executeUpdate();
				e.setObjectAlreadyInDB(true);
				e.setDirty(false);
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			finally {
				ConnectionPool.getInstance().release(conn);
			}//end try/catch
		}//else
		//update the cache
		Cache a = Cache.getInstance();
		a.put(e.getId(), e);//TODO is this right?
	}
	
	/** ////////////////////
	///Delete*/
	public void delete(Employee d){
	throw new RuntimeException("We don't delete. RTFM");
	}
	
	private Employee createFromRS(ResultSet rs){
		
		Employee e=null;
		try {
			e = new Employee(rs.getString("id"));
			e.setFirstName(rs.getString("firstname"));
			e.setMiddleName(rs.getString("middlename"));//TODO does this work better than a number
			e.setLastName(rs.getString("lastname"));
			e.setHireDate(rs.getString("hiredate"));
			e.setPhone(rs.getString("phone"));
			e.setSalary(rs.getString("salary"));
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		//put in cache
		return e;
		}
	
	/** ////////////////////
	///SEARCH
	 * Populate the employee list when GUI loads. Also gets called by the searchByLastName method*/
	public List<Employee> getAll() throws DataException{
		 List<Employee> results = new LinkedList<Employee>();
		//get a connection
		Connection conn = null;
		try {
			conn = ConnectionPool.getInstance().get();
			//setup a prepared statement "SELECT * FROM dog"
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM employee");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
		Employee e = createFromRS(rs);
		results.add(e);
		}
		//TODO put the whole list in cache? or every object?
		} catch (DataException e1) {
			
			e1.printStackTrace();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		finally{//finally release connection
		ConnectionPool.getInstance().release(conn);}
		return results;
		}	
		
	/**Responds to a search bar. It then calls the getAll method to 
	 * retrieve all employee objects and searches through that list to find a match*/
		public List<Employee> searchByLastName(String lastName){
			List<Employee> all = new LinkedList<Employee>();
			try {
				all = getAll();
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Iterator<Employee> iterator = all.iterator();
		List<Employee> matches = new LinkedList<Employee>();
		while(iterator.hasNext()){
				Employee result = iterator.next();//get each object in the list
				if(result.getLastName().contains(lastName));//logic test to match argument
				{matches.add(result);}//there might be more than one with the same last name
		}
		Cache.getInstance().put("searchEmp", matches);
				return matches;
		}

	public String getIdFromLastName(String lastName) {
		List<Employee> all = new LinkedList<Employee>();
		try {
			all = getAll();
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id = null;
		for(Employee temp : all){
			if (temp.getLastName().contains(lastName)){
				id = temp.getId();
			}
		}
		return id;
	
	
	}
	
	
	
	
	

}//EmployeeDAO