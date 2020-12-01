package com.library.pl;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;
import javax.swing.text.*;
import javax.swing.border.*;
import com.library.interfaces.*;
import com.library.dao.*;
import com.library.exception.*;
import com.library.model.*;
import com.library.pl.*;

public class BookIssue extends JFrame implements ActionListener,ItemListener,DocumentListener{
private JTextField b1,b2,b3,b4,b5,personIDBox,statusBox;
private JLabel bookID,personID,heading1,heading2,close,subHeading,error1,error2,list,listEr,b11,b22,b33,b44,b55,pEr,status,result;
private List qty;
private CheckboxGroup person;
private Checkbox std,fc;
private JButton save,clear,find;
private BookModel bm;
private ArrayList<BookInterface> books=null;
private LibraryMenu lm;
public BookIssue(LibraryMenu lm)
{
try{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}catch(Exception e)
{JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}
this.lm=lm;
lm.setVisible(false);
this.setLayout(null);
this.setBackground(new Color(225,176,98));
person=new CheckboxGroup();
std=new Checkbox("Student",person,true);
fc=new Checkbox("Faculty",person,false);
bm=new BookModel();
this.setUndecorated(false);
statusBox=new JTextField("");
status=new JLabel("Book Status");
result=new JLabel("");
find=new JButton("Find");
save=new JButton("Save");
clear=new JButton("Clear");
close=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BACK_ICON)));
this.addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent me)
{
BookIssue.this.lm.setVisible(true);
BookIssue.this.dispose();
}
});

personID=new JLabel("Person ID");
bookID=new JLabel("Book ID");
heading1=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BOOK_ICON)));
heading2=new JLabel("IT Departmental Library");
subHeading=new JLabel("Fill the Details for Issue Books");
subHeading.setFont(new Font("Times New Roman",Font.BOLD,16));
heading2.setFont(new Font("Times New Roman",Font.BOLD,28));
error2=new JLabel("");
error2.setForeground(Color.red);
error1=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b11=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b22=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b33=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b44=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b55=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
listEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
pEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));

list=new JLabel("Quantity");
this.setLayout(null);
b1=new JTextField("IT-");
b2=new JTextField("IT-");
b3=new JTextField("IT-");
b4=new JTextField("IT-");
b5=new JTextField("IT-");
personIDBox=new JTextField("");
qty=new List(3,true);
heading1.setBounds(10,3,64,64);
heading2.setBounds(80,10,500,30);
status.setBounds(200,50,80,30);
statusBox.setBounds(280,50,100,30);
find.setBounds(380,55,60,20);
result.setBounds(440,50,80,30);
subHeading.setBounds(30,90,500,30);
personID.setBounds(30,120,100,30);

bookID.setBounds(200,120,80,30);
b1.setBounds(300,120,100,30);
b11.setBounds(400,120,30,30);
b2.setBounds(200,160,100,30);
b22.setBounds(300,160,30,30);
b3.setBounds(350,160,100,30);
b33.setBounds(450,160,30,30);
b4.setBounds(200,200,100,30);
b44.setBounds(300,200,30,30);
b5.setBounds(350,200,100,30);
b55.setBounds(450,200,30,30);
personIDBox.setBounds(30,150,100,30);
pEr.setBounds(130,150,30,30);
std.setBounds(30,190,80,20);
fc.setBounds(120,190,60,20);
list.setBounds(30,210,100,30);
listEr.setBounds(130,210,30,30);
qty.setBounds(30,240,150,50);
save.setBounds (340,250,60,25);
clear.setBounds (400,250,60,25);
close.setBounds(460,3,32,32);
error1.setBounds(30,300,32,32);
error2.setBounds(60,300,500,30);
qty.add("1");
qty.add("2");
qty.add("3");
qty.add("4");
qty.add("5");
this.add(clear);
this.add(find);
this.add(result);
this.add(status);
this.add(statusBox);
this.add(save);
this.add(pEr);
this.add(b11);
this.add(b22);
this.add(b33);
this.add(b44);
this.add(b55);
//this.add(error1);
//this.add(error2);
this.add(heading2);
this.add(heading1);
this.add(list);
this.add(listEr);
//this.add(close);
this.add(subHeading);
this.add(qty);
this.add(b1);
this.add(b2);
this.add(b3);
this.add(b4);
this.add(b5);
this.add(bookID);
this.add(personID);
this.add(list);
this.add(fc);
this.add(std);
this.add(personIDBox);
b11.setVisible(false);
b22.setVisible(false);
b33.setVisible(false);
b44.setVisible(false);
b55.setVisible(false);
b2.setVisible(false);
b3.setVisible(false);
b4.setVisible(false);
b5.setVisible(false);
pEr.setVisible(false);
listEr.setVisible(false);
this.setSize(500,350);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setLocation(d.width/2 -this.getWidth()/2,d.height/2 - this.getHeight()/2);
this.setResizable(false);
this.setTitle("BOOK ISSUE");
error1.setVisible(false);
addListeners();
qty.setMultipleMode(false);

qty.select(0);
qty.setBackground(new Color(225,176,98));
this.setVisible(true);

}


public void addListeners()
{
save.addActionListener(this);
clear.addActionListener(this);
qty.addItemListener(this);
find.addActionListener(this);
statusBox.getDocument().addDocumentListener(this);
}

public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==clear)
{
b11.setVisible(false);
b22.setVisible(false);
b33.setVisible(false);
b44.setVisible(false);
b55.setVisible(false);
b2.setVisible(false);
b3.setVisible(false);
b4.setVisible(false);
b5.setVisible(false);
pEr.setVisible(false);
listEr.setVisible(false);
error1.setVisible(false);
error2.setVisible(false);
listSelect();
b1.setVisible(true);
qty.setMultipleMode(false);
qty.select(0);
personIDBox.setText("");
b1.setText("IT-");
statusBox.setText("");
result.setText("");
}


if(ae.getSource()==save)
{
int flag=0;
try{
if(personIDBox.getText().trim().equals(""))
{
pEr.setVisible(true);
throw new Exception("Person ID Required");
}
else
{
error1.setVisible(false);
error2.setText("");
pEr.setVisible(false);
}

PersonDAO p=new PersonDAO();
int num=qty.getSelectedIndex()+1;

if(num==1)
{
if(b1.getText().trim().equals(""))
{
flag++;
b11.setVisible(true);
}
else
{
if(flag>0)
flag--;
b11.setVisible(false);
}
if(flag!=0)
throw new Exception("Entries required");
if(flag==0)
{

if(!(bm.getBook(b1.getText().trim()).getStatus().trim().equals("A") || bm.getBook(b1.getText().trim()).getStatus().trim().equals("Available") || bm.getBook(b1.getText().trim()).getStatus().trim().equals("AVAILABLE")))
throw new Exception (b1.getText().trim()+" is not available currently");
books=new ArrayList<BookInterface>();
books.add(bm.getBook(b1.getText().trim()));
}
}
if(num==2)
{
if(b1.getText().trim().equals(""))
{
flag++;
b11.setVisible(true);
}
else
{
if(flag>0)
flag--;
b11.setVisible(false);
}
if(b2.getText().trim().equals(""))
{
flag++;
b22.setVisible(true);
}
else
{
if(flag>0)
flag--;
b22.setVisible(false);
}

if(flag!=0)
throw new Exception("Entries required");
if(flag==0)
{
if(!(bm.getBook(b1.getText().trim()).getStatus().equals("A") || bm.getBook(b1.getText().trim()).getStatus().equals("Available") || bm.getBook(b1.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b1.getText().trim()+" is not available currently");
if(!(bm.getBook(b2.getText().trim()).getStatus().equals("A") || bm.getBook(b2.getText().trim()).getStatus().equals("Available") || bm.getBook(b2.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b2.getText().trim()+" is not available currently");
books=new ArrayList<BookInterface>();
books.add(bm.getBook(b1.getText().trim()));
books.add(bm.getBook(b2.getText().trim()));
}
}
if(num==3)
{
if(b1.getText().trim().equals(""))
{
flag++;
b11.setVisible(true);
}
else
{
if(flag>0)
flag--;
b11.setVisible(false);
}
if(b2.getText().trim().equals(""))
{
flag++;
b22.setVisible(true);
}
else
{
if(flag>0)
flag--;
b22.setVisible(false);
}
if(b3.getText().trim().equals(""))
{
flag++;
b33.setVisible(true);
}
else
{
if(flag>0)
flag--;
b33.setVisible(false);
}

if(flag!=0)
throw new Exception("Entries required");
if(flag==0)
{
if(!(bm.getBook(b1.getText().trim()).getStatus().equals("A") || bm.getBook(b1.getText().trim()).getStatus().equals("Available") || bm.getBook(b1.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b1.getText().trim()+" is not available currently");
if(!(bm.getBook(b2.getText().trim()).getStatus().equals("A") || bm.getBook(b2.getText().trim()).getStatus().equals("Available") || bm.getBook(b2.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b2.getText().trim()+" is not available currently");
if(!(bm.getBook(b3.getText().trim()).getStatus().equals("A") || bm.getBook(b3.getText().trim()).getStatus().equals("Available") || bm.getBook(b3.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b3.getText().trim()+" is not available currently");
books=new ArrayList<BookInterface>();
books.add(bm.getBook(b1.getText().trim()));
books.add(bm.getBook(b2.getText().trim()));
books.add(bm.getBook(b3.getText().trim()));

}
}
if(num==4)
{
if(b1.getText().trim().equals(""))
{
flag++;
b11.setVisible(true);
}
else
{
if(flag>0)
flag--;
b11.setVisible(false);
}
if(b2.getText().trim().equals(""))
{
flag++;
b22.setVisible(true);
}
else
{
if(flag>0)
flag--;
b22.setVisible(false);
}
if(b3.getText().trim().equals(""))
{
flag++;
b33.setVisible(true);
}
else
{
if(flag>0)
flag--;
b33.setVisible(false);
}
if(b4.getText().trim().equals(""))
{
flag++;
b44.setVisible(true);
}
else
{
if(flag>0)
flag--;
b44.setVisible(false);
}



if(flag!=0)
throw new Exception("Entries required");

if(flag==0)
{
if(!(bm.getBook(b1.getText().trim()).getStatus().equals("A") || bm.getBook(b1.getText().trim()).getStatus().equals("Available") || bm.getBook(b1.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b1.getText().trim()+" is not available currently");

if(!(bm.getBook(b2.getText().trim()).getStatus().equals("A") || bm.getBook(b2.getText().trim()).getStatus().equals("Available") || bm.getBook(b2.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b2.getText().trim()+" is not available currently");
if(!(bm.getBook(b3.getText().trim()).getStatus().equals("A") || bm.getBook(b3.getText().trim()).getStatus().equals("Available") || bm.getBook(b3.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b3.getText().trim()+" is not available currently");
if(!(bm.getBook(b3.getText().trim()).getStatus().equals("A") || bm.getBook(b3.getText().trim()).getStatus().equals("Available") || bm.getBook(b3.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b3.getText().trim()+" is not available currently");
if(!(bm.getBook(b4.getText().trim()).getStatus().equals("A") || bm.getBook(b4.getText().trim()).getStatus().equals("Available") || bm.getBook(b4.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b4.getText().trim()+"is not available currently");
books=new ArrayList<BookInterface>();
books.add(bm.getBook(b1.getText().trim()));
books.add(bm.getBook(b2.getText().trim()));
books.add(bm.getBook(b3.getText().trim()));
books.add(bm.getBook(b4.getText().trim()));

}
}
if(num==5)
{
if(b1.getText().trim().equals(""))
{
flag++;
b11.setVisible(true);
}
else
{
if(flag>0)
flag--;
b11.setVisible(false);
}
if(b2.getText().trim().equals(""))
{
flag++;
b22.setVisible(true);
}
else
{
if(flag>0)
flag--;
b22.setVisible(false);
}
if(b3.getText().trim().equals(""))
{
flag++;
b33.setVisible(true);
}
else
{
if(flag>0)
flag--;
b33.setVisible(false);
}
if(b4.getText().trim().equals(""))
{
flag++;
b44.setVisible(true);
}
else
{
if(flag>0)
flag--;
b44.setVisible(false);
}
if(b5.getText().trim().equals(""))
{
flag++;
b55.setVisible(true);
}
else
{
if(flag>0)
flag--;
b55.setVisible(false);
}

if(flag!=0)
throw new Exception("Entries required");
if(flag==0)
{
if(!(bm.getBook(b1.getText().trim()).getStatus().equals("A") || bm.getBook(b1.getText().trim()).getStatus().equals("Available") || bm.getBook(b1.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b1.getText().trim()+" is not available currently");
if(!(bm.getBook(b2.getText().trim()).getStatus().equals("A") || bm.getBook(b2.getText().trim()).getStatus().equals("Available") || bm.getBook(b2.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b2.getText().trim()+" is not available currently");
if(!(bm.getBook(b3.getText().trim()).getStatus().equals("A") || bm.getBook(b3.getText().trim()).getStatus().equals("Available") || bm.getBook(b3.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b3.getText().trim()+" is not available currently");
if(!(bm.getBook(b3.getText().trim()).getStatus().equals("A") || bm.getBook(b3.getText().trim()).getStatus().equals("Available") || bm.getBook(b3.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b3.getText().trim()+" is not available currently");
if(!(bm.getBook(b4.getText().trim()).getStatus().equals("A") || bm.getBook(b4.getText().trim()).getStatus().equals("Available") || bm.getBook(b4.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b4.getText().trim()+" is not available currently");
if(!(bm.getBook(b5.getText().trim()).getStatus().equals("A") || bm.getBook(b5.getText().trim()).getStatus().equals("Available") || bm.getBook(b5.getText().trim()).getStatus().equals("AVAILABLE")))
throw new Exception (b5.getText().trim()+ " is not available currently");


books=new ArrayList<BookInterface>();

books.add(bm.getBook(b1.getText().trim()));

books.add(bm.getBook(b2.getText().trim()));
books.add(bm.getBook(b3.getText().trim()));
books.add(bm.getBook(b4.getText().trim()));
books.add(bm.getBook(b5.getText().trim()));


}
}

if(std.getState())
{

p.issueBook(p.getStudent(personIDBox.getText().trim()),books);

}
else
{
p.issueBook(p.getFaculty(personIDBox.getText().trim()),books);
}

for(int i=0;i<books.size();i++)
{
BookInterface bk=books.get(i);
bk.setStatus("Issued");
bm.updateBook(bk);
}

b11.setVisible(false);
b22.setVisible(false);
b33.setVisible(false);
b44.setVisible(false);
b55.setVisible(false);
b2.setVisible(false);
b3.setVisible(false);
b4.setVisible(false);
b5.setVisible(false);
pEr.setVisible(false);
listEr.setVisible(false);
error1.setVisible(false);
error2.setVisible(false);
listSelect();
b1.setVisible(true);
qty.setMultipleMode(false);
qty.select(0);
b1.setText("");
statusBox.setText("");
result.setText("");
error1.setVisible(false);
error2.setVisible(true);
error2.setText("Books Issued to "+personIDBox.getText().trim()+" .");
personIDBox.setText("");

}catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
error1.setVisible(true);
error2.setVisible(true);
error2.setText(e.getMessage());

}
}

if(ae.getSource()==find)
{
try{
if(statusBox.getText().trim().equals(""))
throw new Exception("Book ID required");
result.setText(bm.getBook(statusBox.getText().trim()).getStatus());
error1.setVisible(false);

error2.setText("");
}
catch(Exception e)
{JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
error1.setVisible(true);
error2.setVisible(true);
error2.setText(e.getMessage());

}
}

}

public void itemStateChanged(ItemEvent ie)
{
int value=qty.getSelectedIndex();

listSelect();
if(value==0)
{
b1.setVisible(true);
}
if(value==1)
{
b1.setVisible(true);
b2.setVisible(true);
}
if(value==2)
{
b1.setVisible(true);
b2.setVisible(true);
b3.setVisible(true);


}
if(value==3)
{
b1.setVisible(true);
b2.setVisible(true);
b3.setVisible(true);
b4.setVisible(true);

}
if(value==4)
{
b1.setVisible(true);
b2.setVisible(true);
b3.setVisible(true);
b4.setVisible(true);
b5.setVisible(true);

}



}

public void listSelect()
{
b11.setVisible(false);
b22.setVisible(false);
b33.setVisible(false);
b44.setVisible(false);
b55.setVisible(false);
b2.setVisible(false);
b3.setVisible(false);
b4.setVisible(false);
b5.setVisible(false);
b1.setVisible(false);
b1.setText("IT-");
b2.setText("IT-");
b3.setText("IT-");
b4.setText("IT-");
b5.setText("IT-");
}


public void insertUpdate(DocumentEvent documentEvent)
{ // gets called when something gets inserted into the document
result.setText("");

}
public void removeUpdate(DocumentEvent documentEvent)
{ // gets called when something gets removed from the document
result.setText("");

}
public void changedUpdate(DocumentEvent documentEvent)
{ // gets called when a
//set or set of attributes change
// In our application we don't want to do anything in this case
}

}