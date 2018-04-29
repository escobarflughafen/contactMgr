package ui;

import classes.member;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import utils.revokeStack;
import utils.csvUtil;
import utils.contactUtil;

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
    private JButton statBtn;
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
    private JButton savBtn;
    private JButton revokeDeleteBtn;
    private JPanel btnPane;
    private JPanel userPane;
    private JButton editProfileBtn;
    private JPanel btnPane2;
    private JTable infoTable;
    private JScrollPane userInfoPane;
    private JLabel editStatusLbl;
    private JPanel catalogContainer;
    private JList searchResultList;
    private JPanel searchPane;

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


    //revoking stack
    public revokeStack deletStack = new revokeStack();
    public contactUtil contactBuilder = new contactUtil();


    String colHeaders[] = {"ID", "姓名", "方向", "年级", "班级", "电话", "电邮", "宿舍", "住址"};
    String userInfoHeaders[] = {"项", "值"};

    DefaultTableModel infoModel = new DefaultTableModel(colHeaders, 0);
    DefaultTableModel userInfoModel = new DefaultTableModel(userInfoHeaders, 0);

    public contacts(String username) {
        this.username = username;
        catalogue.setModel(infoModel);
        infoTable.setModel(userInfoModel);
        contactBuilder.setUsername(username);
        contactBuilder.setUserInfo(userInfoModel);

        searchPane.setVisible(false);
        searchResultList.remove(0);


        contacts = contactBuilder.createAllContactObjects(catalogue);

        JFrame frame = new JFrame("contacts");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Table row selection Listener;

        catalogue.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
             /*   selectedRowIdx = catalogue.getSelectedRow(); // get index of selected ROW
                selectedColumnIdx = catalogue.getSelectedColumn();

                editStatusLbl.setText(String.valueOf(selectedRowIdx) + ", " + String.valueOf(selectedColumnIdx)); */
                selectedRowIdx = catalogue.getSelectedRow(); // get index of selected ROW
                selectedColumnIdx = catalogue.getSelectedColumn();


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

        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (selectedRowIdx >= 0) {
                    member memInfo = new member();

                    memInfo.setId(idTextField.getText());
                    memInfo.setName(nameTextField.getText());
                    memInfo.setGroup(groupBox.getItemAt(groupBox.getSelectedIndex()).toString());
                    memInfo.setGrade(gradeBox.getItemAt(gradeBox.getSelectedIndex()).toString());
                    memInfo.setClas(clasBox.getItemAt(clasBox.getSelectedIndex()).toString());
                    memInfo.setPhoneNum(teleNumTextField.getText());
                    memInfo.setEmail(emailTextField.getText());
                    memInfo.setDormitory(dormTextField.getText());
                    memInfo.setAddress(addrTextField.getText());
                    /*
                    int tempRowIndex = selectedRowIdx;
                    infoModel.removeRow(tempRowIndex);
                    infoModel.insertRow(tempRowIndex, memInfo.getRecord());
                    */

                    contactBuilder.saveRow(catalogue, selectedRowIdx, infoModel, memInfo);

                    isSaved = false;
                }
            }
        });

        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int deletRowIndex = selectedRowIdx;
                //revokRow = {idTextField.getText(),nameTextField.getText(),groupBox.getItemAt(groupBox.getSelectedIndex()).toString(),}
                contactBuilder.deleteRow(catalogue, deletRowIndex, infoModel, deletStack);
                System.out.println(deletStack.toString());

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
                /*
                String[] emptyRow = {""};
                infoModel.insertRow((selectedRowIdx == 0 && rowCount == 0) ? selectedRowIdx : selectedRowIdx + 1, emptyRow); // 插入空表：插入非空表
                rowCount++;
                */
                contactBuilder.createNewRow(catalogue, selectedRowIdx, infoModel);
                isSaved = false;
            }
        });

        revokeDeleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                //infoModel.insertRow(deletStack.popIndex(), deletStack.popRevoke());
                contactBuilder.revokeDeletRow(catalogue, infoModel, deletStack);

            }
        });
        ex2csvBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                csvUtil csvMaker = new csvUtil("t01.csv", catalogue);
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
                contacts = contactBuilder.createAllContactObjects(catalogue);
            }
        });

        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (selectedRowIdx >= 0) {
                    member memInfo = new member();

                    memInfo.setId(idTextField.getText());
                    memInfo.setName(nameTextField.getText());
                    memInfo.setGroup(groupBox.getItemAt(groupBox.getSelectedIndex()).toString());
                    memInfo.setGrade(gradeBox.getItemAt(gradeBox.getSelectedIndex()).toString());
                    memInfo.setClas(clasBox.getItemAt(clasBox.getSelectedIndex()).toString());
                    memInfo.setPhoneNum(teleNumTextField.getText());
                    memInfo.setEmail(emailTextField.getText());
                    memInfo.setDormitory(dormTextField.getText());
                    memInfo.setAddress(addrTextField.getText());


                    contactBuilder.saveRow(catalogue, selectedRowIdx, infoModel, memInfo);

                    contacts = contactBuilder.createAllContactObjects(catalogue);

                    isSaved = false;
                }
            }
        });


        searchTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                searchPane.setVisible(true);
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
            }
        });
        btnGo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                //searching
                contactBuilder.contactSearch(searchTextField.getText(), contacts, searchResult, searchResultIndex, searchResultList);

            }
        });


        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
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
        searchTextField.setText("Keywords");
        searchTextField.setToolTipText("输入搜索内容");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        modifyPane.add(searchTextField, gbc);
        btnGo = new JButton();
        btnGo.setText("Go");
        btnGo.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        modifyPane.add(btnGo, gbc);
        btnPane = new JPanel();
        btnPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
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
        searchPane = new JPanel();
        searchPane.setLayout(new BorderLayout(0, 0));
        searchPane.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        modifyPane.add(searchPane, gbc);
        searchPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null));
        searchResultList = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("3117004478");
        searchResultList.setModel(defaultListModel1);
        searchResultList.setVisible(true);
        searchPane.add(searchResultList, BorderLayout.CENTER);
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
        btnPane2 = new JPanel();
        btnPane2.setLayout(new BorderLayout(0, 0));
        userPane.add(btnPane2, BorderLayout.SOUTH);
        editProfileBtn = new JButton();
        editProfileBtn.setText("修改用户信息");
        btnPane2.add(editProfileBtn, BorderLayout.CENTER);
        userInfoPane = new JScrollPane();
        userPane.add(userInfoPane, BorderLayout.CENTER);
        userInfoPane.setBorder(BorderFactory.createTitledBorder(null, "用户信息", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-16777216)));
        infoTable = new JTable();
        infoTable.setSelectionForeground(new Color(-1250068));
        userInfoPane.setViewportView(infoTable);
        ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.add(ctrlPanel, BorderLayout.SOUTH);
        searchByCondBtn = new JButton();
        searchByCondBtn.setText("条件查询");
        ctrlPanel.add(searchByCondBtn);
        statBtn = new JButton();
        statBtn.setText("统计信息");
        ctrlPanel.add(statBtn);
        ex2csvBtn = new JButton();
        ex2csvBtn.setText("输出 CSV");
        ctrlPanel.add(ex2csvBtn);
        savBtn = new JButton();
        savBtn.setText("保存通讯录");
        ctrlPanel.add(savBtn);
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
