package com.library.model;
import com.library.interfaces.*;
import com.library.exception.*;
import com.library.dao.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
public class BookModel extends AbstractTableModel
{
private HashMap<String,BookInterface> booksLibrary=null;
private List<String> bookIDs=null;
private BookDAO bookDAO=null;
private TreeSet<BookInterface> books=null;
private String title[]={"ID","Name","Author","Publisher"};

public BookModel()
{
try
{
bookDAO=new BookDAO();
booksLibrary=bookDAO.buildBooksLibrary(bookDAO.getAllBooks());
bookIDs=new ArrayList<String>(booksLibrary.keySet());
}catch(DAOException daoException)
{
booksLibrary=new HashMap<String,BookInterface>();
System.out.println(daoException.getMessage());
}
}

public int getColumnCount()
{
return title.length;
}

public int getRowCount()
{
return booksLibrary.size();
}

public String getColoumnName(int c)
{
return title[c];
}

public Object getValueAt(int row,int col)
{
BookInterface book=booksLibrary.get(bookIDs.get(row));

if(col==0)
{
return book.getId();
}
if(col==1)
{
return book.getName();
}
if(col==2)
{
return book.getAuthor();
}
if(col==3)
{
return book.getPublisher();
}
return null;
}

public Class getColoumnClass(int c)
{
try
{
if(c==0) return Class.forName("java.lang.String");
if(c==1) return Class.forName("java.lang.String");
if(c==2) return Class.forName("java.lang.String");
if(c==3) return Class.forName("java.lang.String");

}catch(Exception exception)
{
System.out.println("model.BookModel : Class getColoumnClass(int c)" +exception.getMessage());
//remove after testing
}
return null;
}

public boolean isCellEditable(int r,int c)
{
return false;
}





public void addBook(BookInterface book) throws DAOException{
bookDAO=new BookDAO();
bookDAO.addBook(book);
}


public void updateBook(BookInterface book) throws DAOException{
bookDAO=new BookDAO();
bookDAO.updateBook(book);
}

public void deleteBook(BookInterface book) throws DAOException{
bookDAO=new BookDAO();
bookDAO.deleteBook(book);
}

public TreeSet<BookInterface> getAllBooks() throws DAOException{
bookDAO=new BookDAO();
books=bookDAO.getAllBooks();
return books;
}

public HashMap<String,BookInterface> buildBooksLibrary(TreeSet books) throws DAOException{
if(books==null) 
{
booksLibrary=new HashMap<String,BookInterface>();
}
else
{
bookDAO=new BookDAO();
booksLibrary=bookDAO.buildBooksLibrary(books);
}
return booksLibrary;
}


public boolean checkIDExistance(String id,Set<String> ids) throws DAOException
{
bookDAO=new BookDAO();
return bookDAO.checkIDExistance(id,ids);
}


public BookInterface getBook(String id) throws DAOException
{
bookDAO=new BookDAO();
return bookDAO.getBook(id);
}


}