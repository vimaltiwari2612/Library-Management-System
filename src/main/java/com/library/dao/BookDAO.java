package com.library.dao;
import com.library.exception.*;
import com.library.connection.*;
import com.library.interfaces.*;
import java.util.*;
import java.sql.*;
public class BookDAO implements BookDAOInterface
{
Connection  connection=null;
TreeSet<BookInterface> treeSet=null;
HashMap<String,BookInterface> booksLibrary=null;
Set<String> ids=null;

public void addBook(BookInterface book) throws DAOException{
try{
connection=DAOConnection.getDAOConnection();
Statement statement=connection.createStatement();
String query1="insert into Book(name,id,publisher,isbn,remark,entryDate,author,price,pages,year,status) values('"+book.getName()+"','"+book.getId()+"','"+book.getPublisher()+"','"+book.getISBN()+"','"+book.getRemark()+"','"+book.getEntryDate()+"','"+book.getAuthor()+"','"+book.getPrice()+"','"+book.getTotalPages()+"','"+book.getYear()+"','"+book.getStatus()+"')";

statement.executeUpdate(query1);
}catch(Exception e)
{
throw new DAOException("Can't add Book - "+e.getMessage());
}

}

public void updateBook(BookInterface book) throws DAOException{
try{
connection=DAOConnection.getDAOConnection();
Statement statement=connection.createStatement();
String query="update Book set name = '"+book.getName()+"',publisher = '"+book.getPublisher()+"',isbn = '"+book.getISBN()+"',remark = '"+book.getRemark()+"',entryDate = '"+book.getEntryDate()+"',author = '"+book.getAuthor()+"', price = '"+book.getPrice()+"', pages = '"+book.getTotalPages()+"',year = '"+book.getYear()+"',  status = '"+book.getStatus()+"' where id = '"+book.getId()+"'";

statement.executeUpdate(query);
}catch(Exception e)
{
throw new DAOException("Can't Update Book - "+e.getMessage());
}
}

public void deleteBook(BookInterface book) throws DAOException{
try{
connection=DAOConnection.getDAOConnection();
Statement statement=connection.createStatement();
String query3="delete from Book where id = '"+book.getId()+"'";

statement.executeUpdate(query3);
}catch(Exception e)
{
throw new DAOException("Can't delete Book - "+e.getMessage());
}
}


public TreeSet<BookInterface> getAllBooks() throws DAOException{
BookInterface book;
try{
connection=DAOConnection.getDAOConnection();
Statement statement=connection.createStatement();
String query="select * from Book order by id";
ResultSet resultSet=statement.executeQuery(query);
if(resultSet!=null)
{
treeSet=new TreeSet<BookInterface>();
while(resultSet.next())
{
book=new Book();
book.setEntryDate(resultSet.getString("entryDate"));
book.setId(resultSet.getString("id"));

book.setISBN(resultSet.getString("isbn"));
book.setAuthor(resultSet.getString("author"));
book.setName(resultSet.getString("name"));
book.setYear(resultSet.getString("year"));
book.setPublisher(resultSet.getString("publisher"));
book.setTotalPages(resultSet.getString("pages"));
book.setPrice(resultSet.getString("price"));
book.setStatus(resultSet.getString("status"));
book.setRemark(resultSet.getString("remark"));
treeSet.add(book);
}
}
}catch(Exception e)
{
throw new DAOException("Error in Fetching Books data - "+e.getMessage());
}
return treeSet;
}


 
public HashMap<String,BookInterface> buildBooksLibrary(TreeSet books) throws DAOException{
try{
if(books==null) books=getAllBooks();
Iterator<BookInterface> iterator=books.iterator();
booksLibrary=new HashMap<String,BookInterface>();
while(iterator.hasNext())
{
BookInterface book=iterator.next();
booksLibrary.put(book.getId(),book);
}
}catch(Exception e)
{
throw new DAOException("Can't build DS For Book - "+e.getMessage());
}
return booksLibrary;
}


public BookInterface getBook(String id) throws DAOException{
try{
if(treeSet==null || booksLibrary==null || ids==null)
{
treeSet=this.getAllBooks();
booksLibrary=buildBooksLibrary(treeSet);
ids=getBooksIds(booksLibrary);
}
if(checkIDExistance(id,ids))
return booksLibrary.get(id);
else
throw new Exception ("Book id "+id+" not valid");
}catch(Exception e)
{
throw new DAOException("Can't get Book - "+e.getMessage());
}

}


public boolean checkIDExistance(String id,Set<String> ids) throws DAOException
{
if(ids.contains(id)) return true;
return false;
}


public Set<String> getBooksIds(HashMap<String,BookInterface> booksLibrary) throws DAOException
{
try{
if(treeSet==null || booksLibrary==null)
{
treeSet=this.getAllBooks();
booksLibrary=buildBooksLibrary(treeSet);
}
ids=booksLibrary.keySet();
}catch(Exception e)
{
throw new DAOException("can't for DS for IDS - "+e.getMessage());
}
return ids;
}


}