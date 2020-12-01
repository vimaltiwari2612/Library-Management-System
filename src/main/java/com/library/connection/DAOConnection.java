package com.library.connection;
import java.sql.*;
import com.library.exception.*;
public class DAOConnection{

private static Connection connection=null;
private DAOConnection(){}

public static Connection getDAOConnection() throws DAOException{
try{
Class.forName("org.sqlite.JDBC");
connection=DriverManager.getConnection("jdbc:sqlite:IT_LIBRARY_DATABASE.db");
}catch(Exception e){
connection=null;
throw new DAOException("Can't Make connection - "+e.getMessage());
}

return connection;
} 


}