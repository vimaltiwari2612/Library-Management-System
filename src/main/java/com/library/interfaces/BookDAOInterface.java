package com.library.interfaces;
import com.library.exception.*;
import com.library.interfaces.*;
import java.util.*;
public interface BookDAOInterface
{
public void addBook(BookInterface book) throws DAOException;
public void updateBook(BookInterface book) throws DAOException;
public void deleteBook(BookInterface book) throws DAOException;
public TreeSet<BookInterface> getAllBooks() throws DAOException; 
public HashMap<String,BookInterface> buildBooksLibrary(TreeSet books) throws DAOException;
public boolean checkIDExistance(String id,Set<String> ids) throws DAOException;
public BookInterface getBook(String id) throws DAOException;
public Set<String> getBooksIds(HashMap<String,BookInterface> bookLibrary) throws DAOException;
}