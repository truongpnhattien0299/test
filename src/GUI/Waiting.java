package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Waiting  extends JFrame{
	 public JPanel start;

//   SoundPlayer mySound = new SoundPlayer();

   public Waiting() {
   	start=new JPanel();
      

       
       ImagePanel background = new ImagePanel("main.png", 0, 0, 800, 600);

       JButton oneButton = new JButton("Ready");
       JButton exitButton = new JButton("Exit");

       // Ä‘á»‹nh vá»‹ trĂ­ cĂ¡c button 
       oneButton.setBounds(300, 200, 200, 60);
      
       exitButton.setBounds(300, 350, 200, 60);
       start.add(oneButton);
       start.add(exitButton);
       start.add(background);
       
       start.setLayout(null);
       start.setBounds(0, 0, 800, 600);
       this.add(start);
       
       this.setLayout(null);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


       
       
       
 
              

           }
      

     

      

   
   public static void main(String[] args) {

       try { // sử Jato libary có chức năng thay đổi giao diện game đẹp hơn 
           UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
       } catch (Exception e) {
       };
       Waiting start = new Waiting();
   }

}
