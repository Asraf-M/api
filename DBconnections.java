
import java.sql.*;
import com.google.gson.*;
import java.util.*;



 class User {
	 
    private int id;
    private String name;
    private String state;
    private String country;
  
    public int getId() { return id; }
  
    public void setId(int id) { this.id = id; }
  
    public String getName() { return name; }
  
    public void setName(String name)
    {
        this.name = name;
    }
  
    public String getState() { return state; }
  
    public void setState(String state)
    {
        this.state = state;
    }

    public String getCountry() { return country; }
  
    public void setCountry(String country)
    {
        this.country = country;
    }

}


class UserData {  

    

   public static Connection connectDB(){
	   
        Connection connection = null; 
        try
		{  
           Class.forName("org.postgresql.Driver");  
           connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/projectDB","postgres","12345"); 
		}  

        catch(Exception e)
		{
            e.printStackTrace();
        }         
                         
        return connection;
		
    }  	
	
	public static int Insert(User user){  //Insert
	
        int status = 0;
        try
		{  
            Connection connect = UserData.connectDB(); 
            PreparedStatement ps = connect.prepareStatement("insert into usertable(name,state,country) values (?,?,?)");  
            ps.setString(1, user.getName());
			ps.setString(2, user.getState());
			ps.setString(3, user.getCountry());
			status = ps.executeUpdate();
			System.out.print("\n"+status+" insert-->status");
			connect.close();  
        }
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}  
		
		return status;
                
    } 
	
	
	public static int Delete(int id)
	{  
		int status = 0;
		try
		{  	  
			Connection connect = UserData.connectDB();  
			PreparedStatement ps = connect.prepareStatement("delete from usertable where id=?");  
			ps.setInt(1,id);  
			status = ps.executeUpdate();
			System.out.print("\n"+status+" delete-->status");				
			connect.close();  		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}  
		
		return status;        
    } 
	
	
	
	public static int Update(User user)
	{         			
		int status = 0;
		try
		{  
			Connection connect = UserData.connectDB();  
			PreparedStatement ps = connect.prepareStatement( "update usertable set  name=?,state=?,country=? where id=?");             			
			ps.setString(1,user.getName());  
			ps.setString(2,user.getState());  
			ps.setString(3,user.getCountry());   
			ps.setInt(4,user.getId()); 	
			status = ps.executeUpdate(); 
			System.out.print("\n"+status+" update-->status");				
			connect.close();  
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		} 
			
		return status;
			 
    }
	
    public static List<User> GetAllUsers()//GetAllUsers
	{
		List <User> list = new ArrayList<User>();		
		ResultSet rs = null;
		
	    try
		{  
						   
			Connection connect = UserData.connectDB();			
			PreparedStatement ps = connect.prepareStatement("select * from usertable");          
			rs = ps.executeQuery();
			while(rs.next())
			{  		
				User user = new User();             				
				user.setId(rs.getInt("id"));  			
				user.setName((rs.getString("name")).trim());  				
				user.setState((rs.getString("state")).trim());  			
				user.setCountry((rs.getString("country")).trim());  	
				list.add(user); 					
			}   
	    
			connect.close();
		}	
		catch (Exception e) 
		{
			e.printStackTrace();	   
		}
		
		return list;
		
				
    }
	
}



