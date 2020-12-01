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
public class BookReturn extends JFrame implements ActionListener,DocumentListener,ListSelectionListener
{
public enum Direction{FORWARD,BACKWARD};
private JButton delete,update;
private JTable booksTable;
private JTextField searchBox;
private JLabel headingIcon,heading,error,errorIcon,search,totalCount,list,close,found;
private JScrollPane scrollPane;
private BookIssueModel bim;
private IssueDetails issueDetails;
private Container c;
private ReturnBook rb;
private BookModel bo;
private LibraryMenu lm;
public BookReturn(LibraryMenu lm)
{
try{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}catch(Exception e)
{JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}
this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
this.lm=lm;
bo=new BookModel();
rb=new ReturnBook();
found=new JLabel("");
found.setForeground(Color.red);
found.setVisible(true);
issueDetails=new IssueDetails();
this.setUndecorated(false);
close=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BACK_ICON)));
this.addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent me)
{
BookReturn.this.dispose();
BookReturn.this.lm.setVisible(true);

}
});

heading=new JLabel("IT Departmental Library");
heading.setFont(new Font("Times New Roman",Font.BOLD,28));
headingIcon=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BOOK_ICON)));
this.setLayout(null);
error=new JLabel("");
errorIcon=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
c=this.getContentPane();
c.setBackground(new Color(225,176,98));
bim=new BookIssueModel();
search=new JLabel("Search Book");
totalCount=new JLabel("");
list=new JLabel("Total : ");
searchBox=new JTextField("");
booksTable=new JTable(bim);
booksTable.setBackground(Color.white);
this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(120);
this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bim.getColoumnName(0));
this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bim.getColoumnName(1));
this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(380);
this.booksTable.setFont(new Font("Times New Roman",Font.PLAIN,16));
this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
this.booksTable.getTableHeader().setFont(new Font("Times New Roman",Font.BOLD,16));
this.setAreaTypeTableRowHeight();
this.scrollPane=new JScrollPane(booksTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
scrollPane.setBackground(new Color(225,176,98));
search.setBounds(10,70,100,30);
searchBox.setBounds(100,70,410,30);
found.setBounds(310,100,80,10);
list.setBounds(10,100,60,30);
totalCount.setBounds(70,100,100,30);
scrollPane.setBounds(10,140,500,250);
issueDetails.setBounds(520,140,490,250);
rb.setBounds(10,430,990,150);
errorIcon.setBounds(10,590,32,32);
error.setBounds(40,590,800,32);
error.setForeground(Color.red);
error.setFont(new Font("Times New Roman",Font.BOLD,15));
headingIcon.setBounds(10,3,64,64);
heading.setBounds(80,10,500,40);
close.setBounds(980,3,32,32);
c.add(list);
c.add(search);
c.add(searchBox);
c.add(totalCount);
c.add(this.scrollPane);
c.add(heading);
c.add(headingIcon);
//c.add(error);
//c.add(errorIcon);
//c.add(close);
c.add(found);
c.add(rb);
c.add(issueDetails);
errorIcon.setVisible(false);
this.setSize(1020,610);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setLocation(d.width/2 -this.getWidth()/2,d.height/2 - this.getHeight()/2);
this.setResizable(false);
this.setTitle("Books");
updateBookCount();
addListeners();
issueDetails.setDefaults(false);
found.setVisible(true);
this.setTitle("Book Return");

this.setVisible(true);
}


public void refreshAll()
{
bim=new BookIssueModel();
booksTable.setModel(bim);
setAreaTypeTableRowHeight();
booksTable.repaint();
booksTable.setBackground(Color.white);
this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(120);
this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bim.getColoumnName(0));
this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bim.getColoumnName(1));
this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(380);
this.booksTable.setFont(new Font("Times New Roman",Font.PLAIN,16));
this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
this.booksTable.getTableHeader().setFont(new Font("Times New Roman",Font.BOLD,16));
this.setAreaTypeTableRowHeight();
updateBookCount();
}



private void setAreaTypeTableRowHeight()
{
for(int x=0;x<this.booksTable.getRowCount();x++)
{ 
this.booksTable.setRowHeight(x,25);
}
}


public void addListeners()
{

booksTable.getSelectionModel().addListSelectionListener(this);
searchBox.getDocument().addDocumentListener(this);
}


public void actionPerformed(ActionEvent ae)
{
}

public void updateBookCount()
{ 
this.totalCount.setText(String.valueOf(bim.getRowCount()));
}

public void insertUpdate(DocumentEvent documentEvent)
{ // gets called when something gets inserted into the document
this.search(this.searchBox.getText().trim(),0,Direction.FORWARD,false);
}
public void removeUpdate(DocumentEvent documentEvent)
{ // gets called when something gets removed from the document
this.search(this.searchBox.getText().trim(),0,Direction.BACKWARD,false);
}
public void changedUpdate(DocumentEvent documentEvent)
{ // gets called when a
//set or set of attributes change
// In our application we don't want to do anything in this case
}



private void search(String electronicUnitName,int fromIndex,Direction direction,boolean isSelected)
{
 if(electronicUnitName.length()==0)
{ 
found.setText("");
this.booksTable.clearSelection();
return;
} 
if(booksTable.getRowCount()==0)
return;
if(direction==Direction.FORWARD)
{
for(int x=fromIndex;x<bim.getRowCount();x++)
{ 
if(((String)this.bim.getValueAt(x,0)).toUpperCase().startsWith(electronicUnitName.toUpperCase()))
{ 
this.booksTable.setRowSelectionInterval(x,x);
this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x,x,false));
found.setText("");
return;
}
found.setText("Not Found");
}} 
if(direction==Direction.BACKWARD)
{
for(int x=fromIndex;x>=0;x--)
{ if(((String)this.bim.getValueAt(x,0)).toUpperCase().startsWith(electronicUnitName.toUpperCase()))

{ 
this.booksTable.setRowSelectionInterval(x,x);
this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x,x,false));

found.setText("");
return;
}

found.setText("Not Found");
}} 
if(!isSelected)
{ 

found.setText("");
this.booksTable.clearSelection();
}}

public void valueChanged(ListSelectionEvent event)
{
if(issueDetails==null) return;
int selectedRowIndex=this.booksTable.getSelectedRow();
if(selectedRowIndex<0 || selectedRowIndex>=this.booksTable.getRowCount())
{ 
issueDetails.setDefaults(false);
} 
else
{
try
{
String id=(String)booksTable.getValueAt(selectedRowIndex,0);
String books=(String)booksTable.getValueAt(selectedRowIndex,1);
showDetails(id,books);
errorIcon.setVisible(false);
error.setText("");

}catch(Exception e)
{ JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
errorIcon.setVisible(true);
error.setText(e.getMessage());
issueDetails.setDefaults(false);
}
}

}

public void showDetails(String id,String books) throws Exception
{
StudentInterface s=null;
FacultyInterface f=null;
ArrayList<BookInterface> bks=new ArrayList<BookInterface>();

if(id.trim().contains("0801IT"))
{
s=bim.getStudent(id);
}
else
{
f=bim.getFaculty(id);
}

String[] k=books.split(",");
for(int i=0;i<k.length;i++)
{
bks.add(bim.getBook(k[i]));
}

if(s==null)
issueDetails.setFacultyDetails(f,bks);
else
issueDetails.setStudentDetails(s,bks);

}

public class IssueDetails extends JPanel
{
JLabel name,id,b1,b2,b3,b4,b5,person,books,contact;
private BookReturn br;

IssueDetails()
{
this.setLayout(null);
br=BookReturn.this;
name=new JLabel("");
id=new JLabel("");
b1=new JLabel("");
b2=new JLabel("");
b3=new JLabel("");
b4=new JLabel("");
b5=new JLabel("");

contact=new JLabel("");
person=new JLabel("Issued to : ");
books=new JLabel("Books List : ");
person.setBounds(10,10,500,20);
name.setBounds(10,30,500,20);
id.setBounds(10,50,500,20);
contact.setBounds(10,70,500,20);
books.setBounds(10,100,500,20);
b1.setBounds(10,120,500,20);
b2.setBounds(10,140,500,20);
b3.setBounds(10,160,500,20);
b4.setBounds(10,180,500,20);
b5.setBounds(10,200,500,20);


this.add(b1);
this.add(b4);
this.add(b3);
this.add(b2);
this.add(b5);
this.add(person);
this.add(name);
this.add(id);
this.add(contact);
this.add(books);
this.setVisible(true);
this.setBackground(new Color(225,176,98));

this.setBorder(new TitledBorder(new EtchedBorder(),"Issue Details"));
}


public void setDefaults(boolean value)
{
id.setVisible(value);
b1.setVisible(value);
b2.setVisible(value);
b3.setVisible(value);
b4.setVisible(value);
b5.setVisible(value);
name.setVisible(value);
contact.setVisible(false);
b1.setText("");
b2.setText("");
b3.setText("");
b4.setText("");
b5.setText("");
name.setText("");
id.setText("");
contact.setText("");
}


public void setStudentDetails(StudentInterface s,ArrayList<BookInterface> bks)
{
setDefaults(false);
id.setVisible(true);
name.setVisible(true);
contact.setVisible(true);
id.setText("ID    : "+s.getEnrollment());
name.setText("NAME    : "+s.getName());
contact.setText("Contact : "+s.getContact());
setBooks(bks);
}



public void setFacultyDetails(FacultyInterface s,ArrayList<BookInterface> bks)
{

setDefaults(false);

id.setVisible(true);
name.setVisible(true);
id.setText("ID      : "+s.getId());
name.setText("NAME    : "+s.getName());
setBooks(bks);
}

public void setBooks(ArrayList<BookInterface> bks)
{
int num=bks.size();

if(num==1)
{
b1.setVisible(true);
b1.setText("1. : "+bks.get(0).getName());
}
if(num==2)
{

b1.setVisible(true);
b1.setText("1. : "+bks.get(0).getName());
b2.setVisible(true);
b2.setText("2. : "+bks.get(1).getName());
}
if(num==3)
{
b1.setVisible(true);
b1.setText("1. : "+bks.get(0).getName());
b2.setVisible(true);
b2.setText("2. : "+bks.get(1).getName());
b3.setVisible(true);
b3.setText("3. : "+bks.get(2).getName());

}
if(num==4)
{
b1.setVisible(true);
b1.setText("1. : "+bks.get(0).getName());
b2.setVisible(true);
b2.setText("2. : "+bks.get(1).getName());
b3.setVisible(true);
b3.setText("3. : "+bks.get(2).getName());
b4.setVisible(true);
b4.setText("4. : "+bks.get(3).getName());

}
if(num==5)
{
b1.setVisible(true);
b1.setText("1. : "+bks.get(0).getName());
b2.setVisible(true);
b2.setText("2. : "+bks.get(1).getName());
b3.setVisible(true);
b3.setText("3. : "+bks.get(2).getName());
b4.setVisible(true);
b4.setText("4. : "+bks.get(3).getName());
b5.setVisible(true);
b5.setText("5. : "+bks.get(4).getName());

}

}
}

class ReturnBook extends JPanel implements ActionListener,ItemListener
{
private JButton returnb,save,close;
private JLabel id,b1,b2,b3,b4,b5,b111,b222,b333,b444,b555,idEr ,qtyEr,qtyLabel;
private JTextField b11,b22,b33,b44,b55,p;
private BookReturn br;
private JComboBox qty;
ArrayList<BookInterface> books=null;
int num=0;
ReturnBook()
{
br=BookReturn.this;
qtyLabel=new JLabel("Quantity");
String values[]={"Select","1","2","3","4","5"};
qty=new JComboBox(values);
qtyEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
this.setLayout(null);
returnb=new JButton("Return");
save=new JButton("Save");
close=new JButton("Close");
id=new JLabel("Person ID");
idEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b1=new JLabel("Book 1");
b2=new JLabel("Book 2");
b3=new JLabel("Book 3");
b4=new JLabel("Book 4");
b5=new JLabel("Book 5");
b111=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b222=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b333=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b444=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b555=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
b11=new JTextField("IT-");
b22=new JTextField("IT-");
b33=new JTextField("IT-");
b44=new JTextField("IT-");
b55=new JTextField("IT-");
b55=new JTextField("IT-");
p=new JTextField("");
returnb.setBounds(20,20,80,25);
save.setBounds(100,20,80,25);
close.setBounds(180,20,80,25);
id.setBounds(10,70,80,30);
p.setBounds(90,70,100,30);
idEr.setBounds(190,70,30,30);
qtyLabel.setBounds(240,70,60,30);
qty.setBounds(300,70,80,30);
qtyEr.setBounds(380,70,30,30);
b1.setBounds(10,110,50,30);
b11.setBounds(60,110,100,30);
b111.setBounds(160,110,30,30);
b2.setBounds(200,110,60,30);
b22.setBounds(260,110,100,30);
b222.setBounds(360,110,30,30);
b3.setBounds(400,110,60,30);
b33.setBounds(460,110,100,30);
b333.setBounds(560,110,30,30);
b4.setBounds(600,110,60,30);
b44.setBounds(660,110,100,30);
b444.setBounds(760,110,30,30);
b5.setBounds(800,110,60,30);
b55.setBounds(860,110,100,30);
b555.setBounds(960,110,30,30);
this.add(b1);
this.add(b2);
this.add(b3);
this.add(b4);
this.add(b5);
this.add(b11);
this.add(b22);
this.add(b33);
this.add(b44);
this.add(b55);
this.add(b111);
this.add(b222);
this.add(b333);
this.add(b444);
this.add(b555);
this.add(close);
this.add(returnb);
this.add(save);
this.add(p);
this.add(id);
this.add(idEr);
this.add(qtyLabel);
this.add(qty);
this.add(qtyEr);
this.setVisible(true);
this.setBackground(new Color(225,176,98));
this.setBorder(new TitledBorder(new EtchedBorder(),"Return Book"));
this.setDefault();
returnb.addActionListener(this);
save.addActionListener(this);
close.addActionListener(this);
this.qty.setSelectedIndex(0);
this.qtyLabel.setVisible(true);
this.qty.setBackground(new Color(225,176,98));
this.qty.setEnabled(false);
qty.addItemListener(this);
}

public void setDefault()
{
b111.setVisible(false);
b222.setVisible(false);
b333.setVisible(false);
b444.setVisible(false);
b555.setVisible(false);
idEr.setVisible(false);
b11.setBackground(new Color(225,176,98));
b22.setBackground(new Color(225,176,98));
b33.setBackground(new Color(225,176,98));
b44.setBackground(new Color(225,176,98));
b55.setBackground(new Color(225,176,98));
p.setBackground(new Color(225,176,98));
this.qty.setSelectedIndex(0);
qtyEr.setVisible(false);
p.setEditable(false);
this.qty.setSelectedIndex(0);
this.qty.setBackground(new Color(225,176,98));
this.qty.setEnabled(false);
b11.setEditable(false);
b22.setEditable(false);
b33.setEditable(false);
b44.setEditable(false);
b55.setEditable(false);
close.setVisible(false);
save.setVisible(false);
p.setText("");
b11.setText("IT-");
b22.setText("IT-");
b33.setText("IT-");
b44.setText("IT-");
b55.setText("IT-");
}

public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==close)
{
returnb.setVisible(true);
setDefault();
br.searchBox.setEditable(true);
br.booksTable.clearSelection();
br.booksTable.setEnabled(true);
br.issueDetails.setDefaults(false);
}

if(ae.getSource()==returnb)
{
returnb.setVisible(false);
setDefault();
this.qty.setSelectedIndex(0);
this.qty.setBackground(new Color(225,176,98));
this.qty.setEnabled(true);
p.setBackground(Color.white);
p.setEditable(true);
close.setVisible(true);
save.setVisible(true);
br.searchBox.setEditable(false);
br.booksTable.clearSelection();
br.booksTable.setEnabled(false);
br.issueDetails.setDefaults(false);
}


if(ae.getSource()==save)
{
try
{
int flag=0;
if(b11.getText().trim().equals(""))
{
flag++;
b111.setVisible(true);
}
else
{
if(flag>0)
flag--;
b111.setVisible(false);
}

if(b22.getText().trim().equals(""))
{
flag++;
b222.setVisible(true);
}
else
{
if(flag>0)
flag--;
b222.setVisible(false);
}

if(b33.getText().trim().equals(""))
{
flag++;
b333.setVisible(true);
}
else
{
if(flag>0)
flag--;
b333.setVisible(false);
}

if(b44.getText().trim().equals(""))
{
flag++;
b444.setVisible(true);
}
else
{
if(flag>0)
flag--;
b444.setVisible(false);
}

if(b55.getText().trim().equals(""))
{
flag++;
b555.setVisible(true);
}
else
{
if(flag>0)
flag--;
b555.setVisible(false);
}

if(p.getText().trim().equals(""))
{
flag++;
idEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
idEr.setVisible(false);
}

if(qty.getSelectedIndex()==0)
{
flag++;
qtyEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
qtyEr.setVisible(false);
}

if(flag!=0)
throw new Exception("Entries Required!!");


StudentInterface s=null;
FacultyInterface f=null;
String per=p.getText().trim();
if(per.contains("0801IT"))
{
s=br.bim.getStudent(per);
}
else
{
f=br.bim.getFaculty(per);
}

books=new ArrayList<BookInterface>();
if(num==2)
{
books.add(br.bim.getBook(b11.getText().trim()));
}
if(num==3)
{
books.add(br.bim.getBook(b11.getText().trim()));
books.add(br.bim.getBook(b22.getText().trim()));
}
if(num==4)
{
books.add(br.bim.getBook(b11.getText().trim()));
books.add(br.bim.getBook(b22.getText().trim()));
books.add(br.bim.getBook(b33.getText().trim()));

}
if(num==5)
{
books.add(br.bim.getBook(b11.getText().trim()));
books.add(br.bim.getBook(b22.getText().trim()));
books.add(br.bim.getBook(b33.getText().trim()));
books.add(br.bim.getBook(b44.getText().trim()));

}
if(num==6)
{
books.add(br.bim.getBook(b11.getText().trim()));
books.add(br.bim.getBook(b22.getText().trim()));
books.add(br.bim.getBook(b33.getText().trim()));
books.add(br.bim.getBook(b44.getText().trim()));
books.add(br.bim.getBook(b55.getText().trim()));
}
if(per.contains("0801IT"))
br.bim.personDAO.returnBook(s,books);
else
br.bim.personDAO.returnBook(f,books);

for(int i=0;i<books.size();i++)
{
BookInterface boo=books.get(i);
boo.setStatus("A");
br.bo.updateBook(boo);
}
br.bim.fireTableDataChanged();
br.booksTable.repaint();
br.setAreaTypeTableRowHeight();
br.refreshAll();
returnb.setVisible(true);
setDefault();
br.searchBox.setEditable(true);
br.booksTable.clearSelection();
br.booksTable.setEnabled(true);
br.issueDetails.setDefaults(false);
br.error.setText("Returned Successfully!!");
br.error.setForeground(Color.blue);
br.errorIcon.setVisible(false);

}catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
br.errorIcon.setVisible(true);
br.error.setText(e.getMessage());
br.error.setForeground(Color.red);
}
}
}

public void itemStateChanged(ItemEvent ie)
{
if(ie.getSource()==qty)
{

b111.setVisible(false);
b222.setVisible(false);
b333.setVisible(false);
b444.setVisible(false);
b555.setVisible(false);
b11.setBackground(new Color(225,176,98));
b22.setBackground(new Color(225,176,98));
b33.setBackground(new Color(225,176,98));
b44.setBackground(new Color(225,176,98));
b55.setBackground(new Color(225,176,98));
b11.setText("IT-");
b22.setText("IT-");
b33.setText("IT-");
b44.setText("IT-");
b55.setText("IT-");
b11.setEditable(false);
b22.setEditable(false);
b33.setEditable(false);
b44.setEditable(false);
b55.setEditable(false);
num=qty.getSelectedIndex()+1;
if(num==2)
{
b11.setEditable(true);
b11.setBackground(Color.white);
}

if(num==3)
{
b11.setEditable(true);
b11.setBackground(Color.white);
b22.setEditable(true);
b22.setBackground(Color.white);
}
if(num==4)
{
b11.setEditable(true);
b11.setBackground(Color.white);
b22.setEditable(true);
b22.setBackground(Color.white);
b33.setEditable(true);
b33.setBackground(Color.white);
}

if(num==5)
{
b11.setEditable(true);
b11.setBackground(Color.white);
b22.setEditable(true);
b22.setBackground(Color.white);
b33.setEditable(true);
b33.setBackground(Color.white);
b44.setEditable(true);
b44.setBackground(Color.white);

}


if(num==6)
{
b11.setEditable(true);
b11.setBackground(Color.white);
b22.setEditable(true);
b22.setBackground(Color.white);
b33.setEditable(true);
b33.setBackground(Color.white);
b44.setEditable(true);
b44.setBackground(Color.white);
b55.setEditable(true);
b55.setBackground(Color.white);

}


}
}

}
}