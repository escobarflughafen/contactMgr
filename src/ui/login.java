package ui;

import classes.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import ui.contacts.*;

public class login {
    private JPanel panel1;
    private JPanel CenterPanel;
    private JPanel TopPanel;
    private JPasswordField passwdTextField;
    private JTextField userTextField;
    private JButton loginBtn;
    private JButton signinBtn;
    private JPanel logPanel;
    private JLabel usernameLbl;
    private JLabel passwdLbl;


    public login() {
        loginBtn.addMouseMotionListener(new MouseMotionAdapter() {
        });
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String passwd = new String(passwdTextField.getPassword());
                admin user = new admin(userTextField.getText(), passwd);

                if (user.getPassword().equals("123456") && user.getUsername().equals("abc")) {
                    new contacts();
                    contacts.main();

                } else {
                    userTextField.setText("WRONG INFO");

                }
            }
        });
    }

    public static void main() {
        JFrame frame = new JFrame("LOGIN");
        frame.setContentPane(new login().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setEnabled(true);
        panel1.setFocusTraversalPolicyProvider(false);
        panel1.setInheritsPopupMenu(false);
        panel1.setMaximumSize(new Dimension(384, 256));
        panel1.setMinimumSize(new Dimension(256, 144));
        panel1.setPreferredSize(new Dimension(256, 192));
        panel1.setRequestFocusEnabled(true);
        TopPanel = new JPanel();
        TopPanel.setLayout(new BorderLayout(0, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel1.add(TopPanel, gbc);
        CenterPanel = new JPanel();
        CenterPanel.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel1.add(CenterPanel, gbc);
        logPanel = new JPanel();
        logPanel.setLayout(new GridBagLayout());
        CenterPanel.add(logPanel, BorderLayout.CENTER);
        passwdTextField = new JPasswordField();
        passwdTextField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        logPanel.add(passwdTextField, gbc);
        usernameLbl = new JLabel();
        Font usernameLblFont = this.$$$getFont$$$(null, -1, 18, usernameLbl.getFont());
        if (usernameLblFont != null) usernameLbl.setFont(usernameLblFont);
        usernameLbl.setText("用　戶");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        logPanel.add(usernameLbl, gbc);
        passwdLbl = new JLabel();
        Font passwdLblFont = this.$$$getFont$$$(null, -1, 18, passwdLbl.getFont());
        if (passwdLblFont != null) passwdLbl.setFont(passwdLblFont);
        passwdLbl.setText("密　码");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        logPanel.add(passwdLbl, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        logPanel.add(panel2, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        logPanel.add(panel3, gbc);
        loginBtn = new JButton();
        loginBtn.setText("登录");
        panel3.add(loginBtn);
        signinBtn = new JButton();
        signinBtn.setText("注册");
        panel3.add(signinBtn);
        userTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        logPanel.add(userTextField, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
