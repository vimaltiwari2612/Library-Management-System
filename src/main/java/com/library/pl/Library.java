package com.library.pl;
import com.library.pl.*;
import javax.swing.*;
import java.awt.*;
public class Library extends JWindow implements Runnable
{
private JLabel loading,logo,name1,name2,developed;
public Library()
{
this.setLayout(null);
name1=new JLabel("SGSITS IT");
name2=new JLabel("Departmental Library");
name1.setFont(new Font("Times New Roman",Font.BOLD,50));
name1.setForeground(new Color(255,128,0));
name2.setFont(new Font("Times New Roman",Font.BOLD,16));
developed=new JLabel("Developed By : Vimal Tiwari");
logo=new JLabel(new ImageIcon(getClass().getResource(GlobalResources.COLLAGE_LOGO)));
loading=new JLabel("Loading...");
logo.setBounds(10,10,100,100);
developed.setBounds(10,102,200,30);
name1.setBounds(120,20,300,40);
name2.setBounds(200,60,150,20);
loading.setBounds(280,102,100,30);
this.add(name1);
this.add(name2);
this.add(loading);
this.add(developed);
this.add(logo);
try{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}catch(Exception e)
{
}
this.setSize(400,130);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setLocation(d.width/2 -this.getWidth()/2,d.height/2 - this.getHeight()/2);
this.setVisible(true);
}

public void run()
{
try{
for(int i=1;i<=100;i++)
{
this.loading.setText("Loading "+i+"%");
Thread.sleep(50);
}
this.dispose();
new LoginSystem();
}catch(Exception e){

}
}


public static void main(String gg[])
{
new Thread(new Library()).start();
}
}