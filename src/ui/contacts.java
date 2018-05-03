package ui;

import classes.admin;
import classes.member;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jdk.nashorn.internal.scripts.JO;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import utils.revokeStack;
import utils.csvUtil;
import utils.contactUtil;
import utils.dbUtil;
import utils.memberUtil;
import utils.adminUtil;

public class contacts {

    private JPanel panel1;
    private JTextField searchTextField;
    private JButton btnGo;
    private JPanel modifyPane;
    private JTabbedPane modifyTabbedPane;
    private JButton saveBtn;
    private JButton createNewBtn;
    private JTextField nameTextField;
    private JTextField teleNumTextField;
    private JTextField emailTextField;
    private JTextField idTextField;
    private JTextField dormTextField;
    private JTextField addrTextField;
    private JPanel contactTable;
    private JPanel detailPanel;
    private JPanel checkPanel;
    private JSpinner clsSpin;
    private JComboBox groupBox;
    private JButton deleteBtn;
    private JButton ex2csvBtn;
    private JPanel ctrlPanel;
    private JPanel tabBasePanel;
    private JPanel toModifyPanel;
    private JLabel idLbl;
    private JLabel nameLbl;
    private JLabel direLbl;
    private JLabel gradeLbl;
    private JLabel clsLbl;
    private JLabel teleNumLbl;
    private JLabel emailLbl;
    private JLabel dormLbl;
    private JLabel addrLbl;
    private JScrollPane detailScrollPanel;
    private JSplitPane mainSplit;
    private JTable catalogue;
    private JComboBox gradeBox;
    private JComboBox clasBox;
    private JScrollPane contentsScrollPane;
    private JButton dbSaveBtn;
    private JButton revokeDeleteBtn;
    private JPanel btnPane;
    private JPanel userPane;
    private JPanel userCtrlPane;
    private JTable infoTable;
    private JLabel editStatusLbl;
    private JPanel catalogContainer;
    private JList searchResultList;
    private JPanel searchPane;
    private JSpinner catalogTextSizeSpin;
    private JLabel searchStatLbl;
    private JPanel bottomPane;
    private JPanel StatPane;
    private JLabel contactCountLbl;
    private JPanel westPane;
    private JTree tableTree;
    private JButton userSettingsBtn;
    private JButton editPasswdBtn;
    private JButton deletUserBtn;
    private JLabel usrLbl;
    private JLabel dbStatus;

    //constants
    private int columnCount = 9;


    //status variables;
    public int selectedRowIdx = -1; //selected row index in catalogue
    public int selectedColumnIdx = -1;
    public boolean isSaved = true;    //records whether the data is modified after saving or not
    private int rowCount = 0;
    private String username;
    private Vector<member> contacts;
    private Vector<String> searchResult = new Vector<>();
    private Vector<Integer> searchResultIndex = new Vector<>();
    private int saveCount = 0;

    public int CONTACTS_SAVE = 0;
    public int CONTACTS_CANCEL = 1;
    public int CONTACTS_DISPOSE = 2;


    //revoking stack
    public revokeStack deletStack = new revokeStack();
    public contactUtil contactBuilder = new contactUtil();


    String colHeaders[] = {"ID", "姓名", "方向", "年级", "班级", "电话", "电邮", "宿舍", "住址"};
    String groups[] = {"数据挖掘", "嵌入式", "前端", "后端", "手游", "设计"}; //need readFROM DATABASE

    DefaultTableModel infoModel = new DefaultTableModel(colHeaders, 0);

    private dbUtil dbLink = new dbUtil();
    private memberUtil dbReader = new memberUtil();
    private adminUtil adminMgr = new adminUtil();


    public contacts(admin administrator) {


        try {
            usrLbl.setText(administrator.getUsername());
            Connection con = dbLink.getConnection();
            this.username = username;
            catalogue.setModel(infoModel);
            contactBuilder.setUsername(username);

            contacts = dbReader.readFromDB(con);
            contactBuilder.tableRefresh(infoModel, contacts);

            searchPane.setVisible(false);
            searchStatLbl.setVisible(false);
            searchResultList.remove(0);

            contactCountLbl.setText("共 " + contacts.size() + " 条记录");
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("QG工作室");
            root.setAllowsChildren(true);

/*
            DefaultMutableTreeNode groupA = new DefaultMutableTreeNode("数据挖掘");
            DefaultMutableTreeNode groupB = new DefaultMutableTreeNode("嵌入式");
            DefaultMutableTreeNode groupC = new DefaultMutableTreeNode("前端");
            DefaultMutableTreeNode groupD = new DefaultMutableTreeNode("后端");
            DefaultMutableTreeNode groupE = new DefaultMutableTreeNode("手游");
            DefaultMutableTreeNode groupF = new DefaultMutableTreeNode("设计");
            root.add(groupA);
            root.add(groupB);
            root.add(groupC);
            root.add(groupD);
            root.add(groupE);
            root.add(groupF);

*/
            //       System.out.println(groupA.getParent());

            DefaultTreeModel tmdl = new DefaultTreeModel(root);

            tableTree.setModel(tmdl);

            JFrame frame = new JFrame("通讯录");
            frame.setContentPane(panel1);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {

                    if (isSaved) {
                        System.exit(1);
                    } else {
                        Object[] options = {"保存", "不保存", "取消"};
                        int status = JOptionPane.showOptionDialog(null, "要在关闭之前保存对通讯录的更改吗？", "未保存",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, options, options[0]);
                        if (status == 0) {
                            try {
                                dbReader.saveDB(contacts, dbLink.getConnection());
                                isSaved = true;
                                System.exit(1);
                            } catch (Exception eee) {
                                eee.printStackTrace();
                            }
                        }
                        if (status == 1) {
                            System.exit(1);
                        }
                        if (status == 2) {
                            //WELCOME BACK
                        }

                    }


                }

                @Override
                public void windowClosed(WindowEvent e) {

                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });
            frame.setMinimumSize(new Dimension(800, 600));
            frame.pack();
            frame.setVisible(true);


            // 搜索框监听器 实时显示搜索内容
            searchTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    System.out.println("insertUpdate");
                    searchResult.removeAllElements();
                    searchResultIndex.removeAllElements();
                    //searching
                    contactBuilder.contactSearch(searchTextField.getText(), contacts, searchResult, searchResultIndex, searchResultList);
                    searchStatLbl.setText("共 " + String.valueOf(searchResult.size()) + " 项结果");

                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    System.out.println("removeUpdate");
                    searchResult.removeAllElements();
                    searchResultIndex.removeAllElements();
                    //searching
                    contactBuilder.contactSearch(searchTextField.getText(), contacts, searchResult, searchResultIndex, searchResultList);
                    searchStatLbl.setText("共 " + String.valueOf(searchResult.size()) + " 项结果");
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    System.out.println("changedUpdate");
                    searchResult.removeAllElements();
                    searchResultIndex.removeAllElements();
                    //searching
                    contactBuilder.contactSearch(searchTextField.getText(), contacts, searchResult, searchResultIndex, searchResultList);
                    searchStatLbl.setText("共 " + String.valueOf(searchResult.size()) + " 项结果");

                }
            });


            // get (row,col)
            catalogue.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
             /*   selectedRowIdx = catalogue.getSelectedRow(); // get index of selected ROW
                selectedColumnIdx = catalogue.getSelectedColumn();

                editStatusLbl.setText(String.valueOf(selectedRowIdx) + ", " + String.valueOf(selectedColumnIdx)); */
                    selectedRowIdx = catalogue.getSelectedRow(); // get index of selected ROW
                    selectedColumnIdx = catalogue.getSelectedColumn();
                    contactCountLbl.setText("共 " + contacts.size() + " 条记录");
                    frame.setTitle((isSaved) ? "通讯录" : "通讯录 [未保存]");


                    //  idTextField.setText(infoModel.getValueAt(selectedRowIdx, 0).toString());

                    if (selectedRowIdx >= 0) {
                        String row[] = new String[columnCount];
                        Integer classInt = new Integer(5);

                        for (int i = 0; i < columnCount; i++) {
                            row[i] = (catalogue.getValueAt(selectedRowIdx, i) == null) ? "" : (catalogue.getValueAt(selectedRowIdx, i).toString());
                        }


                        // setting TEXTFIELD texts
                        idTextField.setText(row[0]);
                        nameTextField.setText(row[1]);

                        switch (row[2]) {
                            case "数据挖掘":
                                groupBox.setSelectedIndex(0);
                                break;
                            case "嵌入式":
                                groupBox.setSelectedIndex(1);
                                break;
                            case "前端":
                                groupBox.setSelectedIndex(2);
                                break;
                            case "后端":
                                groupBox.setSelectedIndex(3);
                                break;
                            case "手游":
                                groupBox.setSelectedIndex(4);
                                break;
                            case "设计":
                                groupBox.setSelectedIndex(5);
                                break;
                            default:
                                groupBox.setSelectedIndex(0);
                                break;
                        }

                        switch (row[3]) {
                            case "大一":
                                gradeBox.setSelectedIndex(0);
                                break;
                            case "大二":
                                gradeBox.setSelectedIndex(1);
                                break;
                            case "大三":
                                gradeBox.setSelectedIndex(2);
                                break;
                            case "大四":
                                gradeBox.setSelectedIndex(3);
                                break;
                            default:
                                gradeBox.setSelectedIndex(0);
                                break;
                        }
                        ;

                        clasBox.setSelectedIndex((row[4] == "") ? 0 : (Integer.parseInt(row[4]) - 1));
                        teleNumTextField.setText(row[5]);
                        emailTextField.setText(row[6]);
                        dormTextField.setText(row[7]);
                        addrTextField.setText(row[8]);

                    }
                }
            });


            deleteBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    int deletRowIndex = selectedRowIdx;
                    //revokRow = {idTextField.getText(),nameTextField.getText(),groupBox.getItemAt(groupBox.getSelectedIndex()).toString(),}
                    deletStack.enDelet(contacts.remove(deletRowIndex));
                    deletStack.enIndex(deletRowIndex);
                    // contactCountLbl.setText("共 " + contacts.size() + " 条记录");
                    System.out.println(deletStack.toString());

                    contactBuilder.tableRefresh(infoModel, contacts);
                    selectedRowIdx = (deletRowIndex == 0) ? 0 : (deletRowIndex - 1);
                    isSaved = false;
                    frame.setTitle((isSaved) ? "通讯录" : "通讯录 [未保存]");


            /*    if (selectedRowIdx >= 0) {

                    deletStack.enIndex(selectedRowIdx);
                    String[] row = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        row[i] = (catalogue.getValueAt(selectedRowIdx, i) == null) ? "" : catalogue.getValueAt(selectedRowIdx, i).toString();
                        System.out.println(row[i]);
                    }

                    deletStack.enDelet(row);//
                    infoModel.removeRow(selectedRowIdx);

                }*/
                }


            });

            createNewBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if (saveCount == 0) {
                        selectedRowIdx = 0;
                        selectedColumnIdx = 0;
                    }
                /*
                String[] emptyRow = {""};
                infoModel.insertRow((selectedRowIdx == 0 && rowCount == 0) ? selectedRowIdx : selectedRowIdx + 1, emptyRow); // 插入空表：插入非空表
                rowCount++;
                *
                */

                    int recRow = selectedRowIdx;

                    contacts.insertElementAt(new member("", "", "", "", "", "", "", "", ""), selectedRowIdx);
                    // contactCountLbl.setText("共 " + contacts.size() + " 条记录");
                    contactBuilder.swapMember(contacts, selectedRowIdx, selectedRowIdx + 1);
                    contactBuilder.tableRefresh(infoModel, contacts);
                    selectedRowIdx = recRow + 1;  // move pointer to the created row
                    saveCount++;
                    isSaved = false;
                    frame.setTitle((isSaved) ? "通讯录" : "通讯录 [未保存]");

                }
            });

            saveBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if (selectedRowIdx >= 0) {
                        int recRow = selectedRowIdx;
                        int recCol = selectedColumnIdx;
                        member memInfo = new member(idTextField.getText(),
                                nameTextField.getText(),
                                groupBox.getItemAt(groupBox.getSelectedIndex()).toString(),
                                gradeBox.getItemAt(gradeBox.getSelectedIndex()).toString(),
                                clasBox.getItemAt(clasBox.getSelectedIndex()).toString(),
                                teleNumTextField.getText(),
                                emailTextField.getText(),
                                dormTextField.getText(),
                                addrTextField.getText());
                    /*
                    memInfo.setId(idTextField.getText());
                    memInfo.setName(nameTextField.getText());
                    memInfo.setGroup(groupBox.getItemAt(groupBox.getSelectedIndex()).toString());
                    memInfo.setGrade(gradeBox.getItemAt(gradeBox.getSelectedIndex()).toString());
                    memInfo.setClas(clasBox.getItemAt(clasBox.getSelectedIndex()).toString());
                    memInfo.setPhoneNum(teleNumTextField.getText());
                    memInfo.setEmail(emailTextField.getText());
                    memInfo.setDormitory(dormTextField.getText());
                    memInfo.setAddress(addrTextField.getText());
                    */
                        contacts.remove(selectedRowIdx);
                        contacts.insertElementAt(memInfo, selectedRowIdx);
                        contactBuilder.tableRefresh(infoModel, contacts);

                        selectedColumnIdx = recCol;
                        selectedRowIdx = recRow;

                        isSaved = false;
                        frame.setTitle((isSaved) ? "通讯录" : "通讯录 [未保存]");


                    }
                }
            });

            revokeDeleteBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    //infoModel.insertRow(deletStack.popIndex(), deletStack.popRevoke());
                    int revkIndex = deletStack.popIndex();
                    contacts.insertElementAt(deletStack.popRevoke(), revkIndex);
                    // contactCountLbl.setText("共 " + contacts.size() + " 条记录");
                    contactBuilder.tableRefresh(infoModel, contacts);
                    isSaved = false;
                    frame.setTitle((isSaved) ? "通讯录" : "通讯录 [未保存]");
                    selectedRowIdx = revkIndex;


                }
            });
            ex2csvBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Date dt = new Date();
                    super.mousePressed(e);
                    csvUtil csvMaker = new csvUtil(dt.toString() + ".csv", catalogue);
                    csvMaker.tabletoCSV();

                }
            });


            catalogue.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    // selectedRowIdx = catalogue.getSelectedRow(); // get index of selected ROW
                    //   selectedColumnIdx = catalogue.getSelectedColumn();

                    //   editStatusLbl.setText(String.valueOf(selectedRowIdx) + ", " + String.valueOf(selectedColumnIdx));
                }
            });


            catalogue.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    selectedRowIdx = catalogue.getSelectedRow(); // get index of selected ROW
                    selectedColumnIdx = catalogue.getSelectedColumn();

                    editStatusLbl.setText(String.valueOf(selectedRowIdx) + ", " + String.valueOf(selectedColumnIdx));
                    contactCountLbl.setText("共 " + contacts.size() + " 条记录");
                    contacts = contactBuilder.createAllContactObjects(catalogue);
                }
            });


            searchTextField.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    searchPane.setVisible(true);
                    searchStatLbl.setVisible(true);
                    contacts = contactBuilder.createAllContactObjects(catalogue);

                }
            });
            searchResultList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);

                }
            });
            searchResultList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    int index = searchResultIndex.get(searchResultList.getSelectedIndex());
                    catalogue.setRowSelectionInterval(index, index);
                    searchResultList.removeAll();
                    searchResult.removeAllElements();
                    searchResultIndex.removeAllElements();
                    searchPane.setVisible(false);
                    searchStatLbl.setVisible(false);
                }
            });

            dbSaveBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        super.mousePressed(e);
                        boolean status = dbReader.saveDB(contacts, dbLink.getConnection());
                        if (status) {
                            isSaved = true;


                        } else {
                            isSaved = false;

                        }
                        frame.setTitle((isSaved) ? "通讯录" : "通讯录 [未保存]");
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            });

            panel1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    super.mouseMoved(e);
                    frame.setTitle((isSaved) ? "通讯录" : "通讯录 [未保存]");
                }
            });

            searchTextField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                }
            });
            catalogTextSizeSpin.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    //catalogue.setFont(new Font("courier", Font.PLAIN, Integer.valueOf(catalogTextSizeSpin.getValue().toString())));

                }


            });


        } catch (Exception e) {
            e.printStackTrace();
        }


        userSettingsBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Object[] msgOptions = {"创建用戶", "删除用戶"};
                int option = JOptionPane.showOptionDialog(null, "选择操作", "用戶设置", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, msgOptions, 0);

                try {
                    Vector<admin> users = adminMgr.getUsers(dbLink.getConnection());
                    Vector<String> usernames = new Vector<>();

                    for (int i = 0; i < users.size(); i++) {
                        usernames.add(users.get(i).getUsername());
                    }

                    //add NEW ADMIN
                    if (option == 0) {
                        //create new admin
                        Object[] msgCreateNewUser = {"用戶名", "密码", "确认密码"}; // 用戶的选择项
                        String newUsername = new String();
                        newUsername = JOptionPane.showInputDialog(null, msgCreateNewUser[0], "用戶名", JOptionPane.QUESTION_MESSAGE);
                        boolean isExist = false;
                        for (int i = 0; i < users.size(); i++) {
                            if (usernames.get(i).equals(newUsername)) {
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            String newPassword = new String();
                            newPassword = JOptionPane.showInputDialog(null, msgCreateNewUser[1], "创建用戶", JOptionPane.QUESTION_MESSAGE);
                            String newPasswdAgain = new String();
                            newPasswdAgain = JOptionPane.showInputDialog(null, msgCreateNewUser[2], "创建用戶", JOptionPane.QUESTION_MESSAGE);
                            admin newAdmin = new admin();

                            if (newPassword.equals("") || newUsername.equals("") || newPasswdAgain.equals("")) {
                                JOptionPane.showMessageDialog(null, "输入错误", "创建用戶", JOptionPane.PLAIN_MESSAGE);
                            } else {
                                if (!newPasswdAgain.equals(newPassword)) {
                                    JOptionPane.showMessageDialog(null, "两次输入密码不一致", "创建用戶", JOptionPane.PLAIN_MESSAGE);

                                } else {

                                    try {
                                        newAdmin.setUsername(newUsername);
                                        newAdmin.setPassword(newPassword);
                                        adminMgr.createAdmin(dbLink.getConnection(), newAdmin);

                                        JOptionPane.showConfirmDialog(null, "创建成功", "创建用戶", JOptionPane.PLAIN_MESSAGE);
                                    } catch (Exception eeee) {
                                        JOptionPane.showConfirmDialog(null, "创建失败", "创建用戶", JOptionPane.PLAIN_MESSAGE);
                                        eeee.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showConfirmDialog(null, "用戶名重复，创建失败", "创建用戶", JOptionPane.PLAIN_MESSAGE);
                        }

                    }

                    //delete EXISTED ADMIN
                    if (option == 1) {
                        try {

                            // init username array


                            int userIndex = JOptionPane.showOptionDialog(null, "选择要删除的用戶", "删除用戶", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, usernames.toArray(), 0);

                            String password = JOptionPane.showInputDialog("输入 " + usernames.get(userIndex) + " 的密码");

                            if (password.equals(users.get(userIndex).getPassword())) {

                                adminMgr.deleteAdmin(dbLink.getConnection(), users.get(userIndex));
                                JOptionPane.showMessageDialog(null, "用戶 " + usernames.get(userIndex) + " 已删除", "删除用戶", JOptionPane.PLAIN_MESSAGE);


                            } else {
                                JOptionPane.showMessageDialog(null, "用戶 " + usernames.get(userIndex) + " 删除失败" + ": 密码错误", "删除用戶", JOptionPane.PLAIN_MESSAGE);
                            }


                        } catch (Exception eeeeee) {
                            System.out.println("ERROR: cannot modify administrators");
                            eeeeee.printStackTrace();
                        }

                    }
                } catch (Exception eeeeeeee) {
                    eeeeeeee.printStackTrace();
                }


            }


        });

        editPasswdBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
        });
        editPasswdBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


                String newPasswd = new String();

                String oldPasswd = new String();

                oldPasswd = (String) JOptionPane.showInputDialog(null, "输入旧密码", "输入旧密码", JOptionPane.QUESTION_MESSAGE);


                if (!oldPasswd.equals(administrator.getPassword().toString())) {
                    JOptionPane.showMessageDialog(null, "旧密码输入错误，修改错误");
                } else {
                    newPasswd = (String) JOptionPane.showInputDialog(null, "输入新密码", "输入新密码", JOptionPane.QUESTION_MESSAGE);

                    try {

                        adminMgr.adminEditPassword(dbLink.getConnection(), administrator.getUsername(), newPasswd);
                        JOptionPane.showMessageDialog(null, "密码修改成功");


                    } catch (Exception eeeee) {
                        eeeee.printStackTrace();
                    }

                }


            }


        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }

    public static void main() {


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
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setMinimumSize(new Dimension(512, 384));
        panel1.setPreferredSize(new Dimension(800, 480));
        panel1.putClientProperty("html.disable", Boolean.FALSE);
        tabBasePanel = new JPanel();
        tabBasePanel.setLayout(new BorderLayout(0, 0));
        panel1.add(tabBasePanel, BorderLayout.CENTER);
        mainSplit = new JSplitPane();
        mainSplit.setDividerSize(8);
        mainSplit.setEnabled(true);
        mainSplit.setOneTouchExpandable(true);
        mainSplit.setOrientation(1);
        mainSplit.setResizeWeight(1.0);
        tabBasePanel.add(mainSplit, BorderLayout.CENTER);
        contactTable = new JPanel();
        contactTable.setLayout(new BorderLayout(0, 0));
        mainSplit.setLeftComponent(contactTable);
        modifyPane = new JPanel();
        modifyPane.setLayout(new GridBagLayout());
        modifyPane.setFocusable(true);
        modifyPane.setName("");
        modifyPane.setToolTipText("");
        contactTable.add(modifyPane, BorderLayout.NORTH);
        searchTextField = new JTextField();
        searchTextField.setText("关键字");
        searchTextField.setToolTipText("输入搜索内容");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        modifyPane.add(searchTextField, gbc);
        btnPane = new JPanel();
        btnPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        modifyPane.add(btnPane, gbc);
        deleteBtn = new JButton();
        deleteBtn.setText("删除");
        btnPane.add(deleteBtn);
        revokeDeleteBtn = new JButton();
        revokeDeleteBtn.setText("撤销删除");
        revokeDeleteBtn.setVisible(true);
        btnPane.add(revokeDeleteBtn);
        editStatusLbl = new JLabel();
        editStatusLbl.setText("");
        btnPane.add(editStatusLbl);
        catalogTextSizeSpin = new JSpinner();
        catalogTextSizeSpin.setVisible(false);
        btnPane.add(catalogTextSizeSpin);
        searchPane = new JPanel();
        searchPane.setLayout(new BorderLayout(0, 0));
        searchPane.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        modifyPane.add(searchPane, gbc);
        searchPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(64, 98));
        searchPane.add(scrollPane1, BorderLayout.CENTER);
        searchResultList = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("<搜索结果>");
        searchResultList.setModel(defaultListModel1);
        searchResultList.setVisible(true);
        scrollPane1.setViewportView(searchResultList);
        searchStatLbl = new JLabel();
        searchStatLbl.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        modifyPane.add(searchStatLbl, gbc);
        catalogContainer = new JPanel();
        catalogContainer.setLayout(new CardLayout(0, 0));
        contactTable.add(catalogContainer, BorderLayout.CENTER);
        contentsScrollPane = new JScrollPane();
        Font contentsScrollPaneFont = this.$$$getFont$$$(null, -1, 16, contentsScrollPane.getFont());
        if (contentsScrollPaneFont != null) contentsScrollPane.setFont(contentsScrollPaneFont);
        contentsScrollPane.putClientProperty("html.disable", Boolean.FALSE);
        catalogContainer.add(contentsScrollPane, "Card1");
        catalogue = new JTable();
        catalogue.setGridColor(new Color(-16777216));
        catalogue.setSelectionBackground(new Color(-13221020));
        catalogue.setSelectionForeground(new Color(-1));
        catalogue.setShowHorizontalLines(true);
        catalogue.setShowVerticalLines(true);
        catalogue.setVisible(true);
        catalogue.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
        contentsScrollPane.setViewportView(catalogue);
        modifyTabbedPane = new JTabbedPane();
        mainSplit.setRightComponent(modifyTabbedPane);
        toModifyPanel = new JPanel();
        toModifyPanel.setLayout(new BorderLayout(0, 0));
        modifyTabbedPane.addTab("修改", toModifyPanel);
        checkPanel = new JPanel();
        checkPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        toModifyPanel.add(checkPanel, BorderLayout.NORTH);
        saveBtn = new JButton();
        saveBtn.setBackground(new Color(-1250068));
        saveBtn.setDefaultCapable(true);
        saveBtn.setEnabled(true);
        saveBtn.setHorizontalAlignment(0);
        saveBtn.setHorizontalTextPosition(2);
        saveBtn.setText("保存");
        saveBtn.setVerticalTextPosition(0);
        checkPanel.add(saveBtn);
        createNewBtn = new JButton();
        createNewBtn.setActionCommand("");
        createNewBtn.setHideActionText(false);
        createNewBtn.setLabel("新建");
        createNewBtn.setText("新建");
        createNewBtn.putClientProperty("html.disable", Boolean.FALSE);
        checkPanel.add(createNewBtn);
        detailScrollPanel = new JScrollPane();
        detailScrollPanel.setVerticalScrollBarPolicy(20);
        toModifyPanel.add(detailScrollPanel, BorderLayout.CENTER);
        detailPanel = new JPanel();
        detailPanel.setLayout(new GridBagLayout());
        detailScrollPanel.setViewportView(detailPanel);
        idLbl = new JLabel();
        idLbl.setText("ＩＤ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        detailPanel.add(idLbl, gbc);
        nameLbl = new JLabel();
        nameLbl.setText("姓名");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        detailPanel.add(nameLbl, gbc);
        nameTextField = new JTextField();
        nameTextField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(nameTextField, gbc);
        direLbl = new JLabel();
        direLbl.setText("方向");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        detailPanel.add(direLbl, gbc);
        gradeLbl = new JLabel();
        gradeLbl.setText("年级");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        detailPanel.add(gradeLbl, gbc);
        clsLbl = new JLabel();
        clsLbl.setText("班级");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        detailPanel.add(clsLbl, gbc);
        teleNumLbl = new JLabel();
        teleNumLbl.setText("电话");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        detailPanel.add(teleNumLbl, gbc);
        teleNumTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(teleNumTextField, gbc);
        emailLbl = new JLabel();
        emailLbl.setText("电邮");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        detailPanel.add(emailLbl, gbc);
        emailTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(emailTextField, gbc);
        idTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(idTextField, gbc);
        dormLbl = new JLabel();
        dormLbl.setText("宿舍");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        detailPanel.add(dormLbl, gbc);
        addrLbl = new JLabel();
        addrLbl.setText("住址");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        detailPanel.add(addrLbl, gbc);
        dormTextField = new JTextField();
        dormTextField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(dormTextField, gbc);
        addrTextField = new JTextField();
        addrTextField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(addrTextField, gbc);
        groupBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("数据挖掘");
        defaultComboBoxModel1.addElement("嵌入式");
        defaultComboBoxModel1.addElement("前端");
        defaultComboBoxModel1.addElement("后端");
        defaultComboBoxModel1.addElement("手游");
        defaultComboBoxModel1.addElement("设计");
        groupBox.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(groupBox, gbc);
        gradeBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("大一");
        defaultComboBoxModel2.addElement("大二");
        defaultComboBoxModel2.addElement("大三");
        defaultComboBoxModel2.addElement("大四");
        gradeBox.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(gradeBox, gbc);
        clasBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("1");
        defaultComboBoxModel3.addElement("2");
        defaultComboBoxModel3.addElement("3");
        defaultComboBoxModel3.addElement("4");
        defaultComboBoxModel3.addElement("5");
        defaultComboBoxModel3.addElement("6");
        defaultComboBoxModel3.addElement("7");
        defaultComboBoxModel3.addElement("8");
        defaultComboBoxModel3.addElement("9");
        defaultComboBoxModel3.addElement("10");
        defaultComboBoxModel3.addElement("11");
        defaultComboBoxModel3.addElement("12");
        defaultComboBoxModel3.addElement("13");
        defaultComboBoxModel3.addElement("14");
        defaultComboBoxModel3.addElement("15");
        defaultComboBoxModel3.addElement("16");
        clasBox.setModel(defaultComboBoxModel3);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        detailPanel.add(clasBox, gbc);
        userPane = new JPanel();
        userPane.setLayout(new BorderLayout(0, 0));
        modifyTabbedPane.addTab("用户", userPane);
        userCtrlPane = new JPanel();
        userCtrlPane.setLayout(new GridBagLayout());
        userPane.add(userCtrlPane, BorderLayout.CENTER);
        deletUserBtn = new JButton();
        deletUserBtn.setText("删除用户");
        deletUserBtn.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        userCtrlPane.add(deletUserBtn, gbc);
        editPasswdBtn = new JButton();
        editPasswdBtn.setHorizontalAlignment(0);
        editPasswdBtn.setText("修改密码");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        userCtrlPane.add(editPasswdBtn, gbc);
        userSettingsBtn = new JButton();
        userSettingsBtn.setHorizontalTextPosition(0);
        userSettingsBtn.setText("用戶管理");
        userSettingsBtn.putClientProperty("hideActionText", Boolean.FALSE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        userCtrlPane.add(userSettingsBtn, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("用戶名");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        userCtrlPane.add(label1, gbc);
        usrLbl = new JLabel();
        usrLbl.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        userCtrlPane.add(usrLbl, gbc);
        bottomPane = new JPanel();
        bottomPane.setLayout(new BorderLayout(0, 0));
        panel1.add(bottomPane, BorderLayout.SOUTH);
        ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bottomPane.add(ctrlPanel, BorderLayout.EAST);
        ex2csvBtn = new JButton();
        ex2csvBtn.setText("输出 CSV");
        ctrlPanel.add(ex2csvBtn);
        dbSaveBtn = new JButton();
        dbSaveBtn.setText("保存通讯录");
        ctrlPanel.add(dbSaveBtn);
        StatPane = new JPanel();
        StatPane.setLayout(new GridBagLayout());
        bottomPane.add(StatPane, BorderLayout.WEST);
        contactCountLbl = new JLabel();
        contactCountLbl.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        StatPane.add(contactCountLbl, gbc);
        westPane = new JPanel();
        westPane.setLayout(new BorderLayout(0, 0));
        panel1.add(westPane, BorderLayout.WEST);
        tableTree = new JTree();
        tableTree.setVisible(false);
        westPane.add(tableTree, BorderLayout.EAST);
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
