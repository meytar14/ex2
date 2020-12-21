package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class login extends JFrame implements Runnable {
    public static void main(String[] args) {


    }
login()
{
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(d.width / 2, d.height / 2);

}
    @Override
    public void run() {
        setVisible(true);
        JFrame f=new JFrame("Button Example");
        final JTextField tf=new JTextField();
        tf.setBounds(50,50, 150,20);
        JButton b=new JButton("Click Here");
        b.setBounds(50,100,95,30);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tf.setText("Welcome to Javatpoint.");
            }
        });
        f.add(b);f.add(tf);
        setLayout(null);
        f.setVisible(true);
    }
}
