package com.library.pl;
import com.library.exception.*;
import com.library.dao.*;
import com.library.pl.*;

import com.library.connection.*;
import com.library.model.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class LoginSystem extends JFrame implements ActionListener
{
private JTextField username;
private JPasswordField password;
private JButton login,exit;
private JLabel usernameLabel,passwordLabel,frontLabel;
Container container;
private AdminModel adminModel;

public LoginSystem()
{
try{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}catch(Exception e)
{
}
adminModel=new AdminModel();
this.setTitle("LOGIN - Departmental Library");
this.setIconImage(new ImageIcon(this.getClass().getResource(GlobalResources.COLLAGE_LOGO)).getImage());
usernameLabel=new JLabel("Username");
frontLabel=new JLabel("SGSITS IT - Departmental Library ");
frontLabel.setFont(new Font("Times New Roman",Font.BOLD,22));
passwordLabel=new JLabel("Password");
container=getContentPane();
login=new JButton("Login");
exit=new JButton("Exit");
username=new JTextField();
password=new JPasswordField();
container.setLayout(null);
frontLabel.setBounds(15,0,500,70);
usernameLabel.setBounds(50,100,80,30);
passwordLabel.setBounds(50,140,80,30);
username.setBounds(130,100,130,30);
password.setBounds(130,140,130,30);
login.setBounds(50,220,100,30);
exit.setBounds(200,220,100,30);
container.setBackground(new Color(225,176,98));
frontLabel.setOpaque(true);
frontLabel.setForeground(Color.black);
frontLabel.setBackground(new Color(225,176,98));
container.add(frontLabel);
container.add(usernameLabel);
container.add(passwordLabel);
container.add(username);
container.add(password);
container.add(login);
container.add(exit);
this.setSize(400,300);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation(d.width/2-this.getWidth()/2,d.height/2-this.getHeight()/2);

this.setVisible(true);
login.addActionListener(this);
exit.addActionListener(this);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setResizable(false);
	
}

public void actionPerformed(ActionEvent ae)
{
Object o=ae.getSource();
if(o==login)
{
try{
if(isNotEmpty())
{
if(adminModel.isRightAuthentication(username.getText(),password.getText()))
{
this.dispose();
new LibraryMenu(); 
}
else
{
JOptionPane.showMessageDialog(this,"Invalid Username/Password !!","Error",JOptionPane.ERROR_MESSAGE);
}
}
}catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}
}
if(o==exit)
{
System.exit(0);
}
}

public boolean isNotEmpty() throws DAOException
{
if(this.username.getText().equals(""))
{
throw new DAOException("Username Can't be empty !!");
}
if(this.password.getText().equals(""))
{
throw new DAOException("Password Can't be empty !!");
}
return true;
}


}

