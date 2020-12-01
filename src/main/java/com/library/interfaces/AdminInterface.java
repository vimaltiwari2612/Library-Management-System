package com.library.interfaces;
import com.library.exception.*;
public interface AdminInterface 
{
public void setUsername(String username);
public void setPassword(String password);
public String getUsername();
public String getPassword();
public void updatePassword(String password) throws DAOException;
public void updateUsername(String username) throws DAOException;
}