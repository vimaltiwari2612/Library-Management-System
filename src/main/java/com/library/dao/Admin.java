package com.library.dao;
import com.library.interfaces.*;
import com.library.exception.*;
import com.library.connection.*;

import java.sql.*;

public class Admin implements AdminInterface
{

private String username,password,time;
public Admin()
{
}



public void setUsername(String username)
{
this.username=username;
}

public void setPassword(String password)
{
this.password=password;
}

public String getUsername()
{
return this.username;
}

public String getPassword()
{
return password;
}


public void updateUsername(String username) throws DAOException
{
try{
this.username=username;
Connection connection=DAOConnection.getDAOConnection();
Statement statement=connection.createStatement();
String stmt="update Admin set username='"+this.username+"' where number=1";
statement.executeUpdate(stmt);
statement.close();
connection.close();
}
catch(Exception exception)
{
throw new DAOException("Can't change Username!!"+exception.getMessage());
}
}


public void updatePassword(String password) throws DAOException
{
try{
this.password=password;
Connection connection=DAOConnection.getDAOConnection();
Statement statement=connection.createStatement();
String stmt="update Admin set password='"+this.password+"' where number=1";
statement.executeUpdate(stmt);
statement.close();
connection.close();
}
catch(Exception exception)
{
throw new DAOException("Can't change Password!!"+exception.getMessage());
}
}



}