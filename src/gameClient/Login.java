package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Login extends JFrame implements Runnable {
    private JButton loginButton;
    private JTextArea idTextField;
    private JTextArea levelTextField;
    private long id;
    private int level;
    public static void main(String[] args) {
Login l=new Login();
Thread t=new Thread(l);
t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int getLevel() {
        return level;
    }

    public long getId() {
        return id;
    }

    public Login()
{
    setLayout(null);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(d.width / 2, d.height / 2);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Font f=new Font("ariel",Font.BOLD,10);
    loginButton=new JButton();
    loginButton.setBounds(getWidth()/2,110,30,20);
    Label login_label=new Label("click the button for login");
    login_label.setBounds(getWidth()/2-130,110,130,20);
    login_label.setFont(f);
    loginButton.setAction(new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                id = Long.parseLong(idTextField.getText());
            }
            catch (NumberFormatException eror)
            {
                JOptionPane.showMessageDialog(null,"Invalid id","id exeption",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                level= Integer.parseInt(levelTextField.getText());
            }
            catch (NumberFormatException eror)
            {
                JOptionPane.showMessageDialog(null,"Invalid level number","id exeption",JOptionPane.ERROR_MESSAGE);
                return;
            }

            setVisible(false);
        }
    });

    idTextField=new JTextArea();
    idTextField.setBounds(getWidth()/2,50,60,20);
    idTextField.setBorder(BorderFactory.createBevelBorder(1));
    Label id_label=new Label("Insert id:");
    id_label.setBounds(getWidth()/2-50,50,50,20);
    id_label.setFont(f);



    levelTextField=new JTextArea();
    levelTextField.setBounds(getWidth()/2,80,30,20);
    levelTextField.setBorder(BorderFactory.createBevelBorder(1));
    Label level_label=new Label("Insert level:");
    level_label.setBounds(getWidth()/2-63,80,63,20);
    level_label.setFont(f);

    add(levelTextField);
    add(idTextField);
    add(loginButton);
    add(id_label);
    add(level_label);
    add(login_label);
}
    @Override
    public void run() {
        setVisible(true);
        while (isVisible())
        {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
