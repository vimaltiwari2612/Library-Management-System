package com.library.exception;
public class DAOException extends Exception
{
private String message;
public DAOException()
{
this.message=null;
}

public DAOException(String message)
{
this.message=message;
}

public void setMessage(String message)
{
this.message=message;
}

public String getMessage()
{
return this.message;
}

public String toString()
{
if(this.message==null)
{
return "DAOException";
}
else
{
return "DAOException"+getMessage();
}
}
}