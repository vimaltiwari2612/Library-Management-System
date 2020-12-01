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

public class BookOperations extends JFrame implements ActionListener,DocumentListener,ListSelectionListener
{
private JButton addBook,delete,update;
public static  boolean isAdding=false;
private JTable booksTable;
private JScrollPane scrollPane;
private BookModel bookModel;
private JTextField searchBox;
private JLabel search,list,totalCount,close,headingIcon,heading,error,errorIcon;
private Container c; 
private BookDetails bd;
private LibraryMenu lm;
public enum Direction{FORWARD,BACKWARD};
BookOperations(LibraryMenu lm)
{
try{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}
this.lm=lm;
this.setUndecorated(false);
close=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BACK_ICON)));
this.addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent me)
{
BookOperations.this.dispose();
BookOperations.this.lm.setVisible(true);

}
});

this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
heading=new JLabel("IT Departmental Library");
heading.setFont(new Font("Times New Roman",Font.BOLD,28));
headingIcon=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.BOOK_ICON)));
bd=new BookDetails();
this.setLayout(null);
c=this.getContentPane();
c.setBackground(new Color(225,176,98));
bookModel=new BookModel();
search=new JLabel("Search Book");

totalCount=new JLabel("");
list=new JLabel("Book List : ");
searchBox=new JTextField("");


addBook=new JButton("Add");
error=new JLabel("");
errorIcon=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));

delete=new JButton("Delete");


update=new JButton("Update");
booksTable=new JTable(bookModel);
booksTable.setBackground(Color.white);
this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(80);
this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookModel.getColoumnName(0));
this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookModel.getColoumnName(1));
this.booksTable.getColumnModel().getColumn(2).setHeaderValue(bookModel.getColoumnName(2));
this.booksTable.getColumnModel().getColumn(3).setHeaderValue(bookModel.getColoumnName(3));
this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(400);
this.booksTable.getColumnModel().getColumn(2).setPreferredWidth(300);
this.booksTable.getColumnModel().getColumn(3).setPreferredWidth(200);
this.booksTable.setFont(new Font("Times New Roman",Font.PLAIN,16));
this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
this.booksTable.getTableHeader().setFont(new Font("Times New Roman",Font.BOLD,16));
this.setAreaTypeTableRowHeight();
this.scrollPane=new JScrollPane(booksTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
scrollPane.setBackground(new Color(225,176,98));
search.setBounds(10,70,100,30);
addBook.setBounds(740,70,80,25);
update.setBounds(820,70,80,25);
delete.setBounds(900,70,80,25);
searchBox.setBounds(100,70,500,30);
list.setBounds(10,100,100,30);
totalCount.setBounds(90,100,100,30);
scrollPane.setBounds(10,140,1000,250);
bd.setBounds(10,400,1000,200);
errorIcon.setBounds(10,610,32,32);
error.setBounds(40,610,800,32);
error.setForeground(Color.red);
error.setFont(new Font("Times New Roman",Font.BOLD,15));
close.setBounds(980,3,32,32);
headingIcon.setBounds(10,3,64,64);
heading.setBounds(80,10,500,40);
c.add(list);
c.add(search);
c.add(searchBox);
c.add(totalCount);
c.add(this.scrollPane);
c.add(addBook);
c.add(delete);
c.add(update);
c.add(bd);
c.add(heading);
c.add(headingIcon);
//c.add(close);
//c.add(error);
//c.add(errorIcon);
errorIcon.setVisible(false);
this.setSize(1020,640);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setLocation(d.width/2 -this.getWidth()/2,d.height/2 - this.getHeight()/2);
this.setResizable(false);

this.setTitle("Books Details");
this.addListeners();
updateBookCount();
//this.setModal(true);
this.setVisible(true);
}


public void addListeners()
{
addBook.addActionListener(this);
delete.addActionListener(this);
update.addActionListener(this);
booksTable.getSelectionModel().addListSelectionListener(this);
searchBox.getDocument().addDocumentListener(this);
}

public void repaintTable()
{
bookModel=new BookModel();
booksTable.setModel(bookModel);
setAreaTypeTableRowHeight();
booksTable.repaint();
booksTable.setBackground(Color.white);
this.booksTable.getColumnModel().getColumn(0).setPreferredWidth(80);
this.booksTable.getColumnModel().getColumn(0).setHeaderValue(bookModel.getColoumnName(0));
this.booksTable.getColumnModel().getColumn(1).setHeaderValue(bookModel.getColoumnName(1));
this.booksTable.getColumnModel().getColumn(2).setHeaderValue(bookModel.getColoumnName(2));
this.booksTable.getColumnModel().getColumn(3).setHeaderValue(bookModel.getColoumnName(3));
this.booksTable.getColumnModel().getColumn(1).setPreferredWidth(400);
this.booksTable.getColumnModel().getColumn(2).setPreferredWidth(300);
this.booksTable.getColumnModel().getColumn(3).setPreferredWidth(200);
this.booksTable.setFont(new Font("Times New Roman",Font.PLAIN,16));
this.booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
this.booksTable.getTableHeader().setFont(new Font("Times New Roman",Font.BOLD,16));
this.setAreaTypeTableRowHeight();
this.updateBookCount();

}

private void setAreaTypeTableRowHeight()
{
for(int x=0;x<this.booksTable.getRowCount();x++)
{ 
this.booksTable.setRowHeight(x,25);
}
}



public void actionPerformed(ActionEvent ae)
{
Object o=ae.getSource();
if(o==addBook)
{
try{
booksTable.clearSelection();
bd.clear();
isAdding=true;
bd.setDefaults(true);
bd.editFields();
this.setState(false);
bd.idBox.setText("IT-");
Date d=new Date();
bd.entryDateBox.setText(String.valueOf(d.getDate()+"/"+d.getMonth()+"/"+d.getYear()));
bd.yearBox.setText("20");
}
catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}
}

if(o==update)
{
int selectedRowIndex=this.booksTable.getSelectedRow();
if(selectedRowIndex==-1)
{
JOptionPane.showMessageDialog(this,"Select a Book to To Edit.","Error",JOptionPane.ERROR_MESSAGE);
return;
}
try{
isAdding=false;
bd.setDefaults(true);
bd.editFields();
this.setState(false);
bd.idBox.setEditable(false);
bd.idBox.setBackground(new Color(225,176,98));

}catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
/*errorIcon.setVisible(true);
error.setText(e.getMessage());
error.setForeground(Color.red);*/
}
}


if(o==delete)
{
int selectedRowIndex=this.booksTable.getSelectedRow();
if(selectedRowIndex==-1)
{
JOptionPane.showMessageDialog(this,"Select an Entry to delete.","Error",JOptionPane.ERROR_MESSAGE);
return;
}
BookInterface book=null;
try
{
book=bookModel.getBook((String)bookModel.getValueAt(selectedRowIndex,0));
}catch(DAOException daoException)
{
 // this case won't arise
}



int selection=JOptionPane.showConfirmDialog(this,"Delete : \n ID = "+book.getId()+"\nName="+book.getName(),"Confirmation",JOptionPane.YES_NO_OPTION);

if(selection==JOptionPane.YES_OPTION)
{ 
try
{

this.bookModel.deleteBook(book);
this.bookModel.fireTableDataChanged();
this.setAreaTypeTableRowHeight();
this.booksTable.repaint();
errorIcon.setVisible(false);
error.setText("book deleted!!");
error.setForeground(Color.blue);
updateBookCount();
this.repaintTable();
bd.repaint();
bd.clear();
searchBox.setText("");
}catch(DAOException e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
//errorIcon.setVisible(true);
//error.setText(e.getMessage());
//error.setForeground(Color.red);
}
}
return;
}

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

public void setState(boolean b)
{
addBook.setEnabled(b);
update.setEnabled(b);
delete.setEnabled(b);
searchBox.setEnabled(b);
searchBox.setText("");
booksTable.setEnabled(b);

error.setText("");
errorIcon.setVisible(false);
}

private void updateBookCount()
{ 
this.totalCount.setText(String.valueOf(bookModel.getRowCount()));
}

public void valueChanged(ListSelectionEvent event)
{ 
if(bd==null) return;
int selectedRowIndex=this.booksTable.getSelectedRow();
if(selectedRowIndex<0 || selectedRowIndex>=this.booksTable.getRowCount())
{ 
bd.setDefaults(false);
} 
else
{
try
{
BookInterface book=bookModel.getBook((String)bookModel.getValueAt(selectedRowIndex,0));
bd.setBookDetails(book);
}catch(DAOException daoException)
{ 

bd.setDefaults(false);
}
}

}


public void search(String id)
{
if(id.length()==0)
{ 
this.booksTable.clearSelection();
return;
}

for(int x=0;x<bookModel.getRowCount();x++)
{
if(((String)this.bookModel.getValueAt(x,1)).toUpperCase().startsWith(id.toUpperCase()))
{ 
this.booksTable.setRowSelectionInterval(x,x);
this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x,x,false));
return;
}
}
this.booksTable.clearSelection();
}

private void search(String electronicUnitName,int fromIndex,Direction direction,boolean isSelected)
{
 if(electronicUnitName.length()==0)
{ 

this.booksTable.clearSelection();
return;
} 

if(booksTable.getRowCount()==0)
return;

if(direction==Direction.FORWARD)
{
for(int x=fromIndex;x<bookModel.getRowCount();x++)
{ 
if(((String)this.bookModel.getValueAt(x,0)).toUpperCase().startsWith(electronicUnitName.toUpperCase()))
{ 
this.booksTable.setRowSelectionInterval(x,x);
this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x,x,false));
return;
}
if(((String)this.bookModel.getValueAt(x,1)).toUpperCase().startsWith(electronicUnitName.toUpperCase()))
{ 
this.booksTable.setRowSelectionInterval(x,x);
this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x,x,false));
return;
}
if(((String)this.bookModel.getValueAt(x,2)).toUpperCase().startsWith(electronicUnitName.toUpperCase()))
{ 
this.booksTable.setRowSelectionInterval(x,x);
this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x,x,false));
return;
}

}} 
if(direction==Direction.BACKWARD)
{
for(int x=fromIndex;x>=0;x--)
{ if(((String)this.bookModel.getValueAt(x,0)).toUpperCase().startsWith(electronicUnitName.toUpperCase()))

{ 
this.booksTable.setRowSelectionInterval(x,x);
this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x,x,false));
return;
}
 if(((String)this.bookModel.getValueAt(x,1)).toUpperCase().startsWith(electronicUnitName.toUpperCase()))

{ 
this.booksTable.setRowSelectionInterval(x,x);
this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x,x,false));
return;
}
if(((String)this.bookModel.getValueAt(x,2)).toUpperCase().startsWith(electronicUnitName.toUpperCase()))

{ 
this.booksTable.setRowSelectionInterval(x,x);
this.booksTable.scrollRectToVisible(this.booksTable.getCellRect(x,x,false));
return;
}

}} 
if(!isSelected)
{ 

this.booksTable.clearSelection();
}}


class BookDetails extends JPanel implements ActionListener
{
JLabel name,publisher,isbn,price,author,pages,remark,status,entryDate,id,year;
JTextField nameBox,publisherBox,isbnBox,idBox,remarkBox,statusBox,entryDateBox,pagesBox,authorBox,priceBox,yearBox;
JButton save,close;
JLabel nameEr,publisherEr,isbnEr,idEr,remarkEr,statusEr,entryDateEr,pagesEr,authorEr,priceEr,yearEr;
private BookOperations bo;

BookDetails()
{
this.setLayout(null);
bo=BookOperations.this;
nameEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
isbnEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
publisherEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
priceEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
authorEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
remarkEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
pagesEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
entryDateEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
idEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
statusEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
yearEr=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.ERROR_ICON)));
save=new JButton("Save");
close=new JButton("Close");
name=new JLabel("Name");
year=new JLabel("Year");
isbn=new JLabel("ISBN");
publisher=new JLabel("Publisher");
price=new JLabel("Price");
author=new JLabel("Author");
remark=new JLabel("Remark");
pages=new JLabel("Pages");
entryDate=new JLabel("Entry Date");
id=new JLabel("ID");
status=new JLabel("Status");
nameBox=new JTextField("");
publisherBox=new JTextField("");
idBox=new JTextField("");
yearBox=new JTextField("");
isbnBox=new JTextField("");
remarkBox=new JTextField("");
statusBox=new JTextField("");
entryDateBox=new JTextField("");
pagesBox=new JTextField("");
authorBox=new JTextField("");
priceBox=new JTextField("");
name.setBounds(10,20,70,30);
nameBox.setBounds(90,20,500,30);
nameEr.setBounds(590,20,30,30);
id.setBounds(10,60,70,30);
idBox.setBounds(90,60,100,30);
idEr.setBounds(190,60,30,30);
pages.setBounds(250,60,70,30);
pagesBox.setBounds(320,60,100,30);
pagesEr.setBounds(330,60,30,30);
isbn.setBounds(450,60,70,30);
isbnBox.setBounds(520,60,120,30);
isbnEr.setBounds(640,60,30,30);
publisher.setBounds(10,100,70,30);
publisherBox.setBounds(90,100,300,30);
publisherEr.setBounds(390,100,30,30);
status.setBounds(10,140,70,30);
statusBox.setBounds(90,140,100,30);
statusEr.setBounds(190,140,30,30);
entryDate.setBounds(250,140,70,30);
entryDateBox.setBounds(320,140,100,30);
entryDateEr.setBounds(420,140,30,30);
remark.setBounds(500,140,70,30);
remarkBox.setBounds(570,140,100,30);
remarkEr.setBounds(670,140,30,30);
year.setBounds(750,140,70,30);
yearBox.setBounds(820,140,100,30);
yearEr.setBounds(920,140,30,30);
author.setBounds(450,100,70,30);
authorBox.setBounds(520,100,300,30);
authorEr.setBounds(720,100,30,30);
price.setBounds(660,20,70,30);
priceBox.setBounds(740,20,100,30);
priceEr.setBounds(840,20,30,30);
save.setBounds(860,10,60,25);
close.setBounds(920,10,60,25);
this.add(save);
this.add(name);
this.add(nameBox);
this.add(price);
this.add(priceBox);
this.add(author);
this.add(authorBox);
this.add(publisher);
this.add(publisherBox);
this.add(remark);
this.add(remarkBox);
this.add(isbn);
this.add(isbnBox);
this.add(pages);
this.add(pagesBox);
this.add(entryDate);
this.add(entryDateBox);
this.add(status);
this.add(statusBox);
this.add(id);
this.add(idBox);
this.add(nameEr);
this.add(priceEr);
this.add(authorEr);
this.add(publisherEr);
this.add(remarkEr);
this.add(isbnEr);
this.add(pagesEr);
this.add(entryDateEr);
this.add(statusEr);
this.add(idEr);
this.add(year);
this.add(yearBox);
this.add(yearEr);
this.add(close);
this.save.addActionListener(this);
close.addActionListener(this);
this.setBackground(new Color(225,176,98));
this.setVisible(true);
setDefaults(false);
this.setBorder(new TitledBorder(new EtchedBorder(),"Book Details"));
}


void setDefaults(boolean value)
{

this.nameBox.setEditable(value);
this.nameBox.setBackground(new Color(225,176,98));
idBox.setEditable(value);
publisherBox.setEditable(value);
priceBox.setEditable(value);
isbnBox.setEditable(value);
authorBox.setEditable(value);
pagesBox.setEditable(value);
entryDateBox.setEditable(value);
remarkBox.setEditable(value);
statusBox.setEditable(value);
yearBox.setEditable(value);
save.setVisible(value);
close.setVisible(value);
idBox.setBackground(new Color(225,176,98));
publisherBox.setBackground(new Color(225,176,98));
priceBox.setBackground(new Color(225,176,98));
isbnBox.setBackground(new Color(225,176,98));
pagesBox.setBackground(new Color(225,176,98));
authorBox.setBackground(new Color(225,176,98));
entryDateBox.setBackground(new Color(225,176,98));
remarkBox.setBackground(new Color(225,176,98));
statusBox.setBackground(new Color(225,176,98));
yearBox.setBackground(new Color(225,176,98));
idEr.setVisible(false);
nameEr.setVisible(false);
publisherEr.setVisible(false);
priceEr.setVisible(false);
pagesEr.setVisible(false);
isbnEr.setVisible(false);
remarkEr.setVisible(false);
statusEr.setVisible(false);
entryDateEr.setVisible(false);
authorEr.setVisible(false);
yearEr.setVisible(false);

}

public void setUpdateBookEntry()
{
int flag=0;
if(nameBox.getText().trim().equals(""))
{
flag++;
nameEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
nameEr.setVisible(false);
}
if(idBox.getText().trim().equals(""))
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
if(publisherBox.getText().trim().equals(""))
{

flag++;
publisherEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
publisherEr.setVisible(false);
}
if(isbnBox.getText().trim().equals(""))
{
flag++;
isbnEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
isbnEr.setVisible(false);
}
if(statusBox.getText().trim().equals(""))
{
flag++;
statusEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
statusEr.setVisible(false);
}
if(remarkBox.getText().trim().equals(""))
{
flag++;
remarkEr.setVisible(true);
}
else
{if(flag>0)
flag--;
remarkEr.setVisible(false);
}
if(entryDateBox.getText().trim().equals(""))
{
flag++;
entryDateEr.setVisible(true);
}
else
{if(flag>0)
flag--;
entryDateEr.setVisible(false);
}
if(authorBox.getText().trim().equals(""))
{
flag++;
authorEr.setVisible(true);
}
else
{if(flag>0)
flag--;
authorEr.setVisible(false);
}

if(yearBox.getText().trim().equals(""))
{
flag++;
yearEr.setVisible(true);
}
else 
{
if(flag>0)
flag--;
yearEr.setVisible(false);
}
if(priceBox.getText().trim().equals(""))
{
flag++;
priceEr.setVisible(true);
}
else
{if(flag>0)
flag--;
priceEr.setVisible(false);
}

if(pagesBox.getText().trim().equals(""))
{
flag++;
pagesEr.setVisible(true);
}
else
{if(flag>0)
flag--;
pagesEr.setVisible(false);
}



if(flag==0)
{
try{
BookInterface book=new Book();
book.setName(nameBox.getText().trim());
book.setId(idBox.getText().trim());
book.setYear((yearBox.getText().trim()));
book.setPrice((priceBox.getText().trim()));
book.setTotalPages(pagesBox.getText().trim());
book.setPublisher(publisherBox.getText().trim());
book.setISBN(isbnBox.getText().trim());
book.setEntryDate(entryDateBox.getText().trim());
book.setAuthor(authorBox.getText().trim());
book.setRemark(remarkBox.getText().trim());
book.setStatus(statusBox.getText().trim());
bo.bookModel.updateBook(book);
bo.bookModel.fireTableDataChanged();
bo.setAreaTypeTableRowHeight();
bo.booksTable.repaint();
bo.updateBookCount();
bo.repaintTable();
this.repaint();
bo.search(idBox.getText().trim(),0,Direction.FORWARD,false);
bo.error.setText("Book updated!!");
bo.error.setForeground(Color.blue);
bo.errorIcon.setVisible(false);
bo.setState(true);
}catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
bo.errorIcon.setVisible(true);
bo.error.setText(e.getMessage());
bo.error.setForeground(Color.red);
}
}
}

public void setNewBookEntry()
{
int flag=0;
if(nameBox.getText().trim().equals(""))
{
flag++;
nameEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
nameEr.setVisible(false);
}
if(idBox.getText().trim().equals(""))
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
if(publisherBox.getText().trim().equals(""))
{

flag++;
publisherEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
publisherEr.setVisible(false);
}
if(isbnBox.getText().trim().equals(""))
{
flag++;
isbnEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
isbnEr.setVisible(false);
}
if(statusBox.getText().trim().equals(""))
{
flag++;
statusEr.setVisible(true);
}
else
{
if(flag>0)
flag--;
statusEr.setVisible(false);
}
if(remarkBox.getText().trim().equals(""))
{
flag++;
remarkEr.setVisible(true);
}
else
{if(flag>0)
flag--;
remarkEr.setVisible(false);
}
if(entryDateBox.getText().trim().equals(""))
{
flag++;
entryDateEr.setVisible(true);
}
else
{if(flag>0)
flag--;
entryDateEr.setVisible(false);
}
if(authorBox.getText().trim().equals(""))
{
flag++;
authorEr.setVisible(true);
}
else
{if(flag>0)
flag--;
authorEr.setVisible(false);
}

if(yearBox.getText().trim().equals(""))
{
flag++;
yearEr.setVisible(true);
}
else 
{
if(flag>0)
flag--;
yearEr.setVisible(false);
}
if(priceBox.getText().trim().equals(""))
{
flag++;
priceEr.setVisible(true);
}
else
{if(flag>0)
flag--;
priceEr.setVisible(false);
}

if(pagesBox.getText().trim().equals(""))
{
flag++;
pagesEr.setVisible(true);
}
else
{if(flag>0)
flag--;
pagesEr.setVisible(false);
}


if(flag==0)
{
try{

BookInterface book=new Book();
book.setName(nameBox.getText().trim());
book.setId(idBox.getText().trim());
book.setYear((yearBox.getText().trim()));
book.setPrice((priceBox.getText().trim()));
book.setTotalPages(pagesBox.getText().trim());
book.setPublisher(publisherBox.getText().trim());
book.setISBN(isbnBox.getText().trim());
book.setEntryDate(entryDateBox.getText().trim());
book.setAuthor(authorBox.getText().trim());
book.setRemark(remarkBox.getText().trim());
book.setStatus(statusBox.getText().trim());
bo.bookModel.addBook(book);
bo.bookModel.fireTableDataChanged();
bo.setAreaTypeTableRowHeight();
bo.booksTable.repaint();
bo.updateBookCount();
bo.repaintTable();
this.repaint();
bo.search(idBox.getText().trim(),0,Direction.FORWARD,false);
bo.error.setText("Book Added!!");
bo.error.setForeground(Color.blue);
bo.errorIcon.setVisible(false);
clear();
bo.setState(true);
}catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
bo.errorIcon.setVisible(true);
bo.error.setText(e.getMessage());
bo.error.setForeground(Color.red);
}
}
}

public void actionPerformed(ActionEvent ae)
{
Object o=ae.getSource();
if(o==save)
{
editFields();
if(bo.isAdding)
setNewBookEntry();
else
setUpdateBookEntry();
}

if(o==close)
{
setDefaults(false);
clear();
bo.setState(true);
}
}

public void clear()
{
nameBox.setText("");
publisherBox.setText("");
yearBox.setText("");
priceBox.setText("");
isbnBox.setText("");
pagesBox.setText("");
statusBox.setText("");
remarkBox.setText("");
authorBox.setText("");
entryDateBox.setText("");
idBox.setText("");
}

public void editFields()
{
this.nameBox.setBackground(Color.white);
idBox.setBackground(Color.white);
publisherBox.setBackground(Color.white);
priceBox.setBackground(Color.white);
isbnBox.setBackground(Color.white);
pagesBox.setBackground(Color.white);
authorBox.setBackground(Color.white);
entryDateBox.setBackground(Color.white);
remarkBox.setBackground(Color.white);
statusBox.setBackground(Color.white);
yearBox.setBackground(Color.white);
}

public void setBookDetails(BookInterface book)
{
idBox.setText(book.getId());
nameBox.setText(book.getName());
publisherBox.setText(book.getPublisher());
isbnBox.setText(book.getISBN());
statusBox.setText(book.getStatus());
remarkBox.setText(book.getRemark());
authorBox.setText(book.getAuthor());
priceBox.setText(String.valueOf(book.getPrice()));
pagesBox.setText(String.valueOf(book.getTotalPages()));
entryDateBox.setText(book.getEntryDate());
yearBox.setText(String.valueOf(book.getYear()));

}


}


}
