package com.library.dao;
import com.library.exception.*;
import com.library.connection.*;
import com.library.interfaces.*;
import java.util.*;
import java.sql.*;

public class PersonDAO implements PersonDAOInterface
{
Connection connection=null;
Statement statement=null;
ResultSet rs=null,resultSet=null;
HashMap<String,String> booksIssued=null;
public void addStudent(StudentInterface student) throws DAOException
{
try{
connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();
String query="insert into Student(name,id,year,contact) values('"+student.getName()+"','"+student.getEnrollment()+"',"+student.getYear()+","+student.getContact()+")";
statement.executeUpdate(query);
}catch(Exception e)
{
throw new DAOException("Can't add Student - "+e.getMessage());
}
finally
{
try{
statement.close();
connection.close();
}catch(Exception e)
{

}
}

}


public void addFaculty(FacultyInterface faculty) throws DAOException{
try{
connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();
String query="insert into Student(name,id) values('"+faculty.getName()+"','"+faculty.getId()+"')";
statement.executeUpdate(query);
}catch(Exception e)
{
throw new DAOException("Can't add Faculty - "+e.getMessage());
}
finally
{
try{
statement.close();
connection.close();
}catch(Exception e)
{

}
}

}


public void issueBook(StudentInterface student,ArrayList<BookInterface> books) throws DAOException{
ArrayList<String> ids=null;
String bookIDs="";
try{

if(isContainDuplicateValue(books))
{
throw new DAOException("Duplication not Allowed In Book ID's");
}

ids=this.getIssueBooksDetails(student);
if(ids!=null)
{
if(ids.size()==5)
throw new Exception("Already Issued 5 books on ID : "+student.getEnrollment());

if((ids.size()+books.size()) > 5)
throw new Exception("Already Issued "+ids.size()+" books ,only "+(5 - ids.size())+" can be issued");

for(int i=0;i<ids.size();i++)
bookIDs=bookIDs+","+ids.get(i).trim();
}

connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();
for(int i=0;i<books.size();i++)
bookIDs=bookIDs+","+books.get(i).getId().trim();
String query="delete from BookIssue where personID = '"+student.getEnrollment()+"'";

statement.executeUpdate(query);
query="";
query="insert into BookIssue(bookID,personID) values('"+bookIDs+"','"+student.getEnrollment()+"')";

statement.executeUpdate(query);

}catch(Exception e)
{
throw new DAOException("Error  - "+e.getMessage());
}
finally
{
try{
statement.close();
connection.close();
}catch(Exception e)
{

}
}
}


public void issueBook(FacultyInterface faculty,ArrayList<BookInterface> books) throws DAOException{
ArrayList<String> ids=null;
String bookIDs="";
try{

if(isContainDuplicateValue(books))
{
throw new DAOException("Duplication not Allowed In Book ID's");
}

ids=this.getIssueBooksDetails(faculty);
if(ids!=null)
{
if(ids.size()==5)
throw new Exception("Already Issued 5 books on ID : "+faculty.getId());

if((ids.size()+books.size()) > 5)
throw new Exception("Already Issued "+ids.size()+" books ,only "+(5 - ids.size())+" can be issued");

for(int i=0;i<ids.size();i++)
bookIDs=bookIDs+","+ids.get(i).trim();
}


connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();
for(int i=0;i<books.size();i++)
bookIDs=bookIDs+","+books.get(i).getId().trim();
String query="delete from BookIssue where personID = '"+faculty.getId()+"'";
statement.executeUpdate(query);
query="";
query="insert into BookIssue(bookID,personID) values('"+bookIDs+"','"+faculty.getId()+"')";
statement.executeUpdate(query);
}catch(Exception e)
{
throw new DAOException("Error  - "+e.getMessage());
}
finally
{
try{
statement.close();
connection.close();
}catch(Exception e)
{

}
}

}

public void returnBook(StudentInterface student,ArrayList<BookInterface> books) throws DAOException{
ArrayList<String> ids=null;
String bookIDs="";
ArrayList<String> remainingBooks=null;
try{

if(isContainDuplicateValue(books))
{
throw new DAOException("Duplication not Allowed In Book ID's");
}

ids=this.getIssueBooksDetails(student);
if(ids==null)
throw new DAOException("no book issued to "+student.getEnrollment());
if(ids.size()<books.size())
throw new DAOException(ids.size()+" books issued to "+student.getEnrollment());

for(int i=0;i<books.size();i++)
{
if(ids.contains(books.get(i).getId())==false)
throw new DAOException("Book "+ books.get(i).getId()+" is not Issued to "+student.getEnrollment());
}
connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();

if(books.size()==ids.size())
{
String query="delete from BookIssue where personID = '"+student.getEnrollment()+"'";
statement.executeUpdate(query);
return;
}

remainingBooks=new ArrayList<String>();

for(int i=0;i<books.size();i++)
{
remainingBooks.add(books.get(i).getId());
}

for(int i=0;i<ids.size();i++)
{
if(remainingBooks.contains(ids.get(i))==false)
bookIDs=bookIDs+","+ids.get(i).trim();
}

String query="update BookIssue set bookID = '"+bookIDs+"' where personID = '"+student.getEnrollment()+"'";
statement.executeUpdate(query);
}catch(Exception e)
{
throw new DAOException("Error in returning Book  student - "+e.getMessage());
}
finally
{
try{
statement.close();
connection.close();
}catch(Exception e)
{

}
}


}


public void returnBook(FacultyInterface faculty,ArrayList<BookInterface> books) throws DAOException{
ArrayList<String> ids=null;
String bookIDs="";
ArrayList<String> remainingBooks=null;
try{


if(isContainDuplicateValue(books))
{
throw new DAOException("Duplication not Allowed In Book ID's");
}

ids=this.getIssueBooksDetails(faculty);
if(ids==null)
throw new DAOException("no book issued to "+faculty.getId());
if(ids.size()<books.size())
throw new DAOException(ids.size()+" books issued to "+faculty.getId());

for(int i=0;i<books.size();i++)
{
if(ids.contains(books.get(i).getId())==false)
throw new DAOException("Book "+ books.get(i).getId()+" is not Issued to "+faculty.getId());
}
connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();

if(books.size()==ids.size())
{
String query="delete from BookIssue where personID = '"+faculty.getId()+"'";
statement.executeUpdate(query);
return;
}

remainingBooks=new ArrayList<String>();

for(int i=0;i<books.size();i++)
{
remainingBooks.add(books.get(i).getId());
}

for(int i=0;i<ids.size();i++)
{
if(remainingBooks.contains(ids.get(i))==false)
bookIDs=bookIDs+","+ids.get(i).trim();
}

String query="update BookIssue set bookID = '"+bookIDs+"' where personID = '"+faculty.getId()+"'";
statement.executeUpdate(query);
}catch(Exception e)
{
throw new DAOException("Error in returning Book Faculty - "+e.getMessage());
}
finally
{
try{
statement.close();
connection.close();
}catch(Exception e)
{

}
}


}

public ArrayList<String> getIssueBooksDetails(StudentInterface student) throws DAOException{

ArrayList<String> books=new ArrayList<String>();
try{
connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();
String query="Select bookID from BookIssue where personID = '"+student.getEnrollment()+"'";
String b="";
 rs=statement.executeQuery(query);
if(rs.next()){
b=rs.getString("bookID");
String k[]=b.substring(1).split(",");
for(int i=0;i<k.length;i++)
books.add(k[i]);
return books;
}
return null;
}catch(Exception e)
{
throw new DAOException(" Student - "+e.getMessage());
}
finally
{
try{
rs.close();
statement.close();
connection.close();
}catch(Exception e)
{

}
}

}

public ArrayList<String> getIssueBooksDetails(FacultyInterface faculty) throws DAOException{

ArrayList<String> books=new ArrayList<String>();
try{
connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();
String query="Select bookID from BookIssue where personID = '"+faculty.getId()+"'";
String b="";
rs=statement.executeQuery(query);
if(rs.next()){
b=rs.getString("bookID");
String k[]=b.substring(1).split(",");
for(int i=0;i<k.length;i++)
books.add(k[i]);
return books;
}
return null;
}catch(Exception e)
{
throw new DAOException(" Faculty - "+e.getMessage());
}
finally
{try{

rs.close();
statement.close();
connection.close();
}catch(Exception e)
{

}
}

}



public StudentInterface getStudent(String id) throws DAOException
{
try{
connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();
String query="select * from Student where enroll = '"+id+"'";
resultSet=statement.executeQuery(query);
if(resultSet.next()){
StudentInterface s=new Student();
s.setEnrollment(resultSet.getString("enroll"));
s.setName(resultSet.getString("name"));
s.setYear(resultSet.getInt("year"));
s.setContact(resultSet.getLong("contact"));
return s;
}
throw new DAOException("Student ID not Valid ");

}catch(Exception e)
{
throw new DAOException("Error in getting Student Info - "+e.getMessage());
}finally
{try{
resultSet.close();
statement.close();
connection.close();
}catch(Exception e)
{

}
}

}

public FacultyInterface getFaculty(String id) throws DAOException
{
try{
connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();
String query="select * from Faculty where id = '"+id+"'";
resultSet=statement.executeQuery(query);
if(resultSet.next()){
FacultyInterface s=new Faculty();
s.setId(resultSet.getString("id"));
s.setName(resultSet.getString("name"));
return s;
}
throw new DAOException("Faculty ID not Valid ");
}catch(Exception e)
{
throw new DAOException("Error in getting Faculty Info - "+e.getMessage());
}
finally
{try{
resultSet.close();
statement.close();
connection.close();
}catch(Exception e)
{

}
}

}




public HashMap<String,String> booksIssued() throws DAOException
{
try{
connection=DAOConnection.getDAOConnection();
statement=connection.createStatement();
String query="select * from BookIssue";
resultSet=statement.executeQuery(query);
if(resultSet!=null){
booksIssued=new HashMap<String,String>();
while(resultSet.next()){
booksIssued.put(resultSet.getString("personID"),resultSet.getString("bookID"));
}
return booksIssued;
}
}catch(Exception e)
{
throw new DAOException("Error in fetching Issue Info - "+e.getMessage());
}finally
{try{
resultSet.close();
statement.close();
connection.close();

}catch(Exception e)
{

}
}

return null;

}


public boolean isContainDuplicateValue(ArrayList<BookInterface> books)
{
int count=books.size();
if(count==1)
return false;
if(count==2)
return books.get(0).getId().trim().equals(books.get(1).getId().trim());
if(count==3)
{
String v1=books.get(0).getId().trim();
String v2=books.get(1).getId().trim();
String v3=books.get(2).getId().trim();
if(v1.equals(v2) || v1.equals(v3) ||  v2.equals(v3))
return true;
}

if(count==4)
{
String v1=books.get(0).getId().trim();
String v2=books.get(1).getId().trim();
String v3=books.get(2).getId().trim();
String v4=books.get(3).getId().trim();
if(v1.equals(v2) || v1.equals(v3) ||  v1.equals(v4) || v2.equals(v4) || v2.equals(v3) || v3.equals(v4) )
return true;
}

if(count==5)
{
String v1=books.get(0).getId().trim();
String v2=books.get(1).getId().trim();
String v3=books.get(2).getId().trim();
String v4=books.get(3).getId().trim();
String v5=books.get(4).getId().trim();

if(v1.equals(v2) || v1.equals(v3) ||  v1.equals(v4) ||v1.equals(v5) || v2.equals(v3) || v2.equals(v4) || v2.equals(v5)|| v3.equals(v4) || v3.equals(v5) || v4.equals(v5))
return true;
}

return false;

} 

}