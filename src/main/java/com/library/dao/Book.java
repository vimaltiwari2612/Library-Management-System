package com.library.dao;
import com.library.interfaces.*;
import java.util.*;
public class Book implements BookInterface,Comparable{

String name,id,isbn,author,publisher,remark,status;
String pages,year;
String price;
String date;

public boolean equals(Object object)
{
if(!(object instanceof Book)) return false;
Book book=(Book)object;
return this.id.equalsIgnoreCase(book.id);
}

public String toString()
{
return this.id;
}

public int compareTo(Object object)
{
BookInterface book=(Book)object;
return this.id.toUpperCase().compareTo(book.getId().toUpperCase());
}


 public String getName() {
            return name;
        }

public void setStatus(String status)
{
this.status=status;
}

public String getStatus()
{
return this.status;
}
public String getYear(){return this.year;}
public void setYear(String year){this.year=year;}
        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getISBN() {
            return isbn;
        }

        public void setISBN(String isbn) {
            this.isbn = isbn;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTotalPages() {
            return pages;
        }

        public void setTotalPages(String pages) {
            this.pages = pages;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getEntryDate() {
            return date;
        }

        public void setEntryDate(String date) {
            this.date = date;
        }

}