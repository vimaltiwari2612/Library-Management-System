package com.library.interfaces;
import com.library.interfaces.*;
import java.util.*;
import com.library.exception.*;
public interface PersonDAOInterface
{
public void addStudent(StudentInterface student) throws DAOException;
public void addFaculty(FacultyInterface faculty) throws DAOException;
public void issueBook(StudentInterface student,ArrayList<BookInterface> books) throws DAOException;
public void issueBook(FacultyInterface faculty,ArrayList<BookInterface> books) throws DAOException;
public void returnBook(StudentInterface student,ArrayList<BookInterface> books) throws DAOException;
public void returnBook(FacultyInterface faculty,ArrayList<BookInterface> books) throws DAOException;
public ArrayList<String> getIssueBooksDetails(StudentInterface student) throws DAOException;
public ArrayList<String> getIssueBooksDetails(FacultyInterface faculty) throws DAOException;
public StudentInterface getStudent(String id) throws DAOException;
public FacultyInterface getFaculty(String id) throws DAOException;
public HashMap<String,String> booksIssued() throws DAOException;
}