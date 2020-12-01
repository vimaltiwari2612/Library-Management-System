package com.library.model;
import com.library.interfaces.*;
import com.library.exception.*;
import com.library.dao.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
public class BookIssueModel extends AbstractTableModel
{
private HashMap<String,String> booksIssued;
ArrayList<String> personIDs,bookIDs;
private BookModel bookModel=null;
public PersonDAO personDAO=null;
private String title[]={"Person Id ","Book ID's "};
String[] students,faculties;
public BookIssueModel()
{
try
{
personDAO=new PersonDAO();
bookModel=new BookModel();
booksIssued=personDAO.booksIssued();
if(booksIssued!=null)
{
personIDs=new ArrayList<String>();
bookIDs=new ArrayList<String>();
for(Object k: booksIssued.keySet())
personIDs.add((String)k);
for(Object k: booksIssued.values())
bookIDs.add((String)k);
}
}catch(DAOException daoException)
{
booksIssued=new HashMap<String,String>();
}
}

public int getColumnCount()
{
return title.length;
}

public int getRowCount()
{
return booksIssued.size();
}

public String getColoumnName(int c)
{
return title[c];
}

public Object getValueAt(int row,int col)
{
if(col==0)
{
return personIDs.get(row).trim();
}
if(col==1)
{
return bookIDs.get(row).substring(1).trim();
}
return null;
}

public Class getColoumnClass(int c)
{
try
{
if(c==0) return Class.forName("java.lang.String");
if(c==1) return Class.forName("java.lang.String");


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


public StudentInterface getStudent(String id) throws DAOException
{
PersonDAO p=new PersonDAO();
return p.getStudent(id);
} 

public FacultyInterface getFaculty(String id) throws DAOException
{
PersonDAO p=new PersonDAO();
return p.getFaculty(id);
} 


public BookInterface getBook(String id) throws DAOException{
BookModel b=new BookModel();
return b.getBook(id);
}

}