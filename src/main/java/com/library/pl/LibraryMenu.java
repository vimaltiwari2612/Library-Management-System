package com.library.pl;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.border.*;
import com.library.interfaces.*;
import com.library.dao.*;
import com.library.exception.*;
import com.library.model.*;
import com.library.pl.*;

public class LibraryMenu extends JFrame implements ActionListener{
public JButton books,issueBook,returnBook,person,settings,about;
public JLabel heading,logo;
public Container c;
public LibraryMenu()
{
heading=new JLabel("SGSITS IT - Departmental Library");
heading.setFont(new Font("Times New Roman",Font.BOLD,24));
this.setLayout(null);
books=new JButton("Books");
issueBook=new JButton("Issue");
returnBook=new JButton("Return");
person=new JButton("Person");
settings=new JButton("Settings");
about=new JButton("Help & Support");
c=getContentPane();
logo=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BOOK_ICON)));
logo.setBounds(5,0,60,60);
heading.setBounds(70,0,400,60);
books.setBounds(0,60,200,200);
issueBook.setBounds(200,60,200,200);
returnBook.setBounds(400,60,200,200);
person.setBounds(0,260,200,200);
settings.setBounds(200,260,200,200);
about.setBounds(400,260,200,200);
c.add(logo);
c.add(settings);
c.add(about);
c.add(books);
c.add(issueBook);
c.add(returnBook);
c.add(person);
c.add(heading);
c.setBackground(new Color(225,176,98));
this.addListeners();
this.setSize(605,488);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setLocation(d.width/2 -this.getWidth()/2,d.height/2 - this.getHeight()/2);
this.setVisible(true);
this.setResizable(false);
this.setTitle("SGSITS IT - Departmental Library");
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.COLLAGE_LOGO)).getImage());
try{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}catch(Exception e)
{
}
}


public void addListeners()
{
books.addActionListener(this);
issueBook.addActionListener(this);
returnBook.addActionListener(this);
person.addActionListener(this);
settings.addActionListener(this);
about.addActionListener(this);

}

public void actionPerformed(ActionEvent ae)
{
Object o=ae.getSource();
if(o==books)
new BookOperations(this);

if(o==issueBook)
new BookIssue(this);

if(o==returnBook)
new BookReturn(this);

if(o==settings)
new Settings(this);

if(o==about)
JOptionPane.showMessageDialog(this,"Developer - Vimal tiwari \n Contact - 9179352885 \n E-mail - vimaltiwari2612@gmail.com","Help & Support",JOptionPane.INFORMATION_MESSAGE);

}

}