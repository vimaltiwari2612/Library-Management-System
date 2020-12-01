package com.library.pl;
import com.library.exception.*;
import com.library.dao.*;
import com.library.pl.*;
import com.library.connection.*;
import com.library.model.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
public class Settings extends JFrame implements ItemListener
{
private LibraryMenu lm;
private JLabel heading;
private CheckboxGroup cbg;
private ChangeAdminPassword cap;
private ChangeAdminUsername cau;
private Checkbox username,password;
private Container c;
public Settings(LibraryMenu lm)
{
try{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}catch(Exception e)
{
}
c=getContentPane();
c.setLayout(null);
cau=new ChangeAdminUsername();
cap=new ChangeAdminPassword();

this.lm=lm;
lm.setVisible(false);
cbg=new CheckboxGroup();
username=new Checkbox("Change Username",cbg,true);
password=new Checkbox("Change Password",cbg,false);
this.username.setBounds(40,10,120,30);
this.password.setBounds(160,10,120,30);
this.cau.setBounds(10,50,300,180);
this.cap.setBounds(10,50,300,180);
this.c.add(username);
this.c.add(password);
this.c.add(cau);
//this.c.add(cap);
this.cau.setVisible(true);
this.cap.setVisible(false);
this.addWindowListener(new WindowAdapter(){
     public void windowClosing(WindowEvent e)
     {
      Settings.this.lm.setVisible(true);
      Settings.this.dispose();
        }
     });

this.setSize(320,270);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setLocation(d.width/2 -this.getWidth()/2,d.height/2 - this.getHeight()/2);
this.setResizable(false);
this.setTitle("Settings");
username.addItemListener(this);
password.addItemListener(this);
this.setResizable(false);
c.setBackground(new Color(225,176,98));
this.setVisible(true);
}




public void itemStateChanged(ItemEvent ie)
{

if(ie.getSource()==username)
{
cau.setVisible(true);
cap.setVisible(false);
this.remove(cap);
this.add(cau);
}

if(ie.getSource()==password)
{
this.remove(cau);
this.add(cap);
cau.setVisible(false);
cap.setVisible(true);
}
cau.repaint();
cap.repaint();
cap.refreshALL();
cau.refreshALL();
this.repaint();
}



class ChangeAdminPassword extends JPanel implements ActionListener
{
private JPasswordField oldPassword,newPassword,reTypePassword;
private JButton save,exit;
private JLabel oldPasswordLabel,newPasswordLabel,reTypePasswordLabel;
private Container c;
private AdminModel adminModel;

ChangeAdminPassword()
{
this.setLayout(null);
adminModel=new AdminModel();
oldPassword=new JPasswordField();
newPassword=new JPasswordField();
reTypePassword=new JPasswordField();
oldPasswordLabel=new JLabel("Current Password");
newPasswordLabel=new JLabel("New Password");
reTypePasswordLabel=new JLabel("Re-Type Password");
oldPasswordLabel.setOpaque(true);
newPasswordLabel.setOpaque(true);
reTypePasswordLabel.setOpaque(true);
newPasswordLabel.setBackground(new Color(225,176,98));
oldPasswordLabel.setBackground(new Color(225,176,98));
reTypePasswordLabel.setBackground(new Color(225,176,98));
save=new JButton("Save");
exit=new JButton("Exit");
setBackground(new Color(225,176,98));
oldPasswordLabel.setBounds(10,15,120,30);
newPasswordLabel.setBounds(10,55,120,30);
reTypePasswordLabel.setBounds(10,95,130,30);
oldPassword.setBounds(130,15,100,30);
newPassword.setBounds(130,55,100,30);
reTypePassword.setBounds(130,95,100,30);
save.setBounds(100,140,80,30);
exit.setBounds(150,140,100,30);
add(newPasswordLabel);
add(oldPasswordLabel);
add(reTypePassword);
add(reTypePasswordLabel);
add(newPassword);
add(oldPassword);
add(save);
//c.add(exit);

save.addActionListener(this);
exit.addActionListener(this);
this.setBorder(new TitledBorder(new EtchedBorder(),"Password"));
}

public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==save)
{
try{
if(adminModel.checkPassword(String.valueOf(oldPassword.getText()).trim()))
{
if(String.valueOf(newPassword.getText()).trim().equals("")==false && confirm(String.valueOf(newPassword.getText()),String.valueOf(reTypePassword.getText())))
{
adminModel.updatePassword(String.valueOf(newPassword.getText()).trim());
JOptionPane.showMessageDialog(this,"Successfully Updated!!","Notification",JOptionPane.INFORMATION_MESSAGE);
oldPassword.setText("");
newPassword.setText("");
reTypePassword.setText("");
}
else
{
JOptionPane.showMessageDialog(this,"new Password & ReType Password are Miss matched!!","Error",JOptionPane.ERROR_MESSAGE);
}
}
else
{
JOptionPane.showMessageDialog(this,"Invalid Current Password !!","Error",JOptionPane.ERROR_MESSAGE);
}
}
catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}

}
}

public boolean confirm(String newPassword,String reTypePassword)
{
char[] n=newPassword.toCharArray();
char[] r=reTypePassword.toCharArray();
if(n.length==r.length)
{
for(int i=0;i<n.length;i++)
{
if(n[i]!=r[i])
{
return false;
}
}
return true;
}
else
{
return false;
}
}

public void refreshALL()
{
oldPassword.setText("");
newPassword.setText("");
reTypePassword.setText("");
}
}


class ChangeAdminUsername extends JPanel implements ActionListener
{
private JTextField oldUsername,newUsername;
private JButton save,exit;
private JLabel oldUsernameLabel,newUsernameLabel;
private Container c;
private AdminModel adminModel;

public ChangeAdminUsername()
{
this.setLayout(null);

adminModel=new AdminModel();
oldUsername=new JTextField();
newUsername=new JTextField();
oldUsernameLabel=new JLabel("Current Username");
newUsernameLabel=new JLabel("New Username");
oldUsernameLabel.setOpaque(true);
newUsernameLabel.setOpaque(true);
newUsernameLabel.setBackground(new Color(225,176,98));
oldUsernameLabel.setBackground(new Color(225,176,98));
save=new JButton("Save");
exit=new JButton("Exit");
setBackground(new Color(225,176,98));
oldUsernameLabel.setBounds(10,15,120,30);
newUsernameLabel.setBounds(10,55,120,30);
oldUsername.setBounds(130,15,100,30);
newUsername.setBounds(130,55,100,30);
save.setBounds(100,140,80,30);
exit.setBounds(150,90,100,30);
add(newUsernameLabel);
add(oldUsernameLabel);
add(newUsername);
add(oldUsername);
add(save);
//c.add(exit);

save.addActionListener(this);
exit.addActionListener(this);

this.setBorder(new TitledBorder(new EtchedBorder(),"Username"));
	
}

public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==save)
{
try{
if(adminModel.checkUsername(oldUsername.getText().trim()) && newUsername.getText().trim().equals("")==false)
{
adminModel.updateUsername(newUsername.getText().trim());
JOptionPane.showMessageDialog(this,"Successfully Updated!!","Notification",JOptionPane.INFORMATION_MESSAGE);
oldUsername.setText("");
newUsername.setText("");

}
else
{
JOptionPane.showMessageDialog(this,"Invalid Current Username !!","Error",JOptionPane.ERROR_MESSAGE);
}
}
catch(Exception e)
{
JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}
}

}


public void refreshALL()
{
oldUsername.setText("");
newUsername.setText("");
}
}



}

