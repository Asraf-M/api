import java.sql.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

import javax.servlet.ServletException;  
import javax.servlet.annotation.WebServlet;  
import javax.servlet.http.*;
import javax.servlet.*; 


 
public class AllCrud extends HttpServlet
{  

	public static Gson gson = new Gson();   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{  		
		try
		{
			List <User> list = UserData.GetAllUsers();			
			if(list.isEmpty())
			{			
				response.getWriter().write(gson.toJson("Sorry! Record Not Found"));
			}			
			else
			{
				String userJSON = gson.toJson(list);		
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(userJSON);
			}
				
		}
		catch(Exception e)
		{
			response.getWriter().write(gson.toJson("Sorry! unable to fetch records")); 
			e.printStackTrace();
		}
	}
	
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{  
		response.setContentType("application/json");
		
		try
		{					
			String StoreJsonData = AllCrud.GetJson(request.getReader(),request, response);//function call (function name : GetJson)	
			User user = gson.fromJson(StoreJsonData, User.class);                         //convert json to java object
			AllCrud.ExceptionHandle(user,"post", request, response);                      //function call (function name : ExceptionHandle)
									
		}		 
		catch(Exception e)
		{
			response.getWriter().write(gson.toJson("Sorry! unable to save record ,Enter the name, state and country properly")); 
			e.printStackTrace();
		}
		 
	}
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 	
    {  
		response.setContentType("application/json");
		
		try
		{			
			String StoreJsonData = AllCrud.GetJson(request.getReader(),request, response);//function call (function name : GetJson)
			User user = gson.fromJson(StoreJsonData, User.class);
			AllCrud.ExceptionHandle(user,"put", request, response);	                     //function call (function name : ExceptionHandle)
		}	 
		catch(Exception e)
		{
			
			e.printStackTrace();
			response.getWriter().write(gson.toJson("Sorry! unable to update record, Enter the parameters properly"));
		}
		 	 
	}
	
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{  	 	 
		response.setContentType("application/json");  
		 	 	
		try
		{	
		
			String StoreJsonData = AllCrud.GetJson(request.getReader(),request, response);//function call (function name : GetJson)
			User user = gson.fromJson(StoreJsonData, User.class);		
			AllCrud.ExceptionHandle(user,"delete", request, response);	                  //function call (function name : ExceptionHandle)			
		
		}				         			 	
		catch(Exception e)
		{
			response.getWriter().write(gson.toJson("Sorry! unable to delete record , enter the id properly")); 
			e.printStackTrace();
		}
				 		 	 
	}
	
	
	
	
	//validate the json data and exception handling 
	
	//GetJson function
	protected static String GetJson(BufferedReader RawJson,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{

		StringBuilder StoreJsonData = new StringBuilder();
		String GetonebyoneJsondata;
		try
		{
			while((GetonebyoneJsondata = RawJson.readLine()) != null)
			{
				StoreJsonData.append(GetonebyoneJsondata);
			}
			
		}
		catch(Exception e)
		{
			response.getWriter().write(gson.toJson("Sorry! unable to insert record , enter the proper parameters")); 
			e.printStackTrace();		
		}
			
		return StoreJsonData.toString();
	
	}
	
	
	//ExceptionHandle function
	protected static void ExceptionHandle(User user,String method,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{	

		if(method == "post")
		{
			if(user.getName()!=null && user.getState()!=null && user.getCountry()!=null)
			{
				try
				{
					int checkerror =  UserData.Insert(user);
					if(checkerror>0)
					{
						response.getWriter().write(gson.toJson("Record saved successfully")); 
					}
					else
					{
						response.getWriter().write(gson.toJson("Sorry! unable to save record check the given input values correctly")); 
					}				
				}
				catch(Exception e)
				{
					response.getWriter().write(gson.toJson("Sorry! unable to save record in Database")); 
					e.printStackTrace();
				}
							
			}
			else
			{
				response.getWriter().write(gson.toJson("Sorry! unable to save record don't skip any one of the parameters")); 
			}
		}
		
		else if(method == "put")
		{	 
			if(user.getId()==(int)user.getId() && user.getName()!=null && user.getState()!=null && user.getCountry()!=null)
			{	
				try
				{				
					int checkerror = UserData.Update(user);
					if(checkerror>0)
					{
						response.getWriter().write(gson.toJson("Record updated successfully")); 
					}
					else
					{
						response.getWriter().write(gson.toJson("Sorry! unable to update record ,Enter the id correctly")); 
					}					
				}
				catch(Exception e)
				{
					response.getWriter().write(gson.toJson("Sorry! unable to update record in Database")); 
					e.printStackTrace();
				}
					
			}
			else
			{
				response.getWriter().write(gson.toJson("Sorry! unable to update record don't skip any one of the parameters or enter the key properly"));
			}
		}
		else if(method == "delete")
		{
			try
			{
				int checkerror = UserData.Delete(user.getId());
				if(checkerror>0)
				{
					response.getWriter().write(gson.toJson("Record deleted successfully")); 
				}
				else
				{
					response.getWriter().write(gson.toJson("Sorry! unable to delete record ,The given input not exist in Database or type the id correctly")); 
				}			
			}
			catch(Exception e)
			{
				response.getWriter().write(gson.toJson("Sorry! unable to delete record in Database")); 
				e.printStackTrace();
			}
	
		}
		

	}

}









	
 