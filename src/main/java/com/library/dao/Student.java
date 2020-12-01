package com.library.dao;
import com.library.interfaces.*;
public class Student implements StudentInterface
{
private String enroll,name;
private int year;
private long contact;


public String getEnrollment() {
            return enroll;
        }

        public void setEnrollment(String enroll) {
            this.enroll = enroll;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public long getContact() {
            return contact;
        }

        public void setContact(long contact) {
            this.contact = contact;
        }
}