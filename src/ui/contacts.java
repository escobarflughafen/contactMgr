package ui;

import classes.member;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import utils.revokeStack;
import utils.csvUtil;
import utils.contactUtils;

public class contacts {

    private JPanel panel1;
    private JTextField searchTextField;
    private JButton btnGo;
    private JPanel modifyPane;
    private JTabbedPane tabbedPane1;
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
    private JButton searchByCondBtn;
    private JButton savBtn;
    private JButton revokeDeleteBtn;
    private JPanel btnPane;
    private JPanel userPane;
    private JButton editProfileBtn;
    private JPanel btnPane2;
    private JTable infoTable;
    private JScrollPane userInfoPane;


    //status variables;
    public int selectedRowIdx = 0; //selected row index in catalogue
    public boolean isSaved = true;    //records whether the data is modified after saving or not
    private int rowCount = 0;
    private String username;


    //revoking stack
    public revokeStack deletStack = new revokeStack();
    public contactUtils infoCreater = new contactUtils();


    String colHeaders[] = {"ID", "姓名", "方向", "年级", "班级", "电话", "电邮", "宿舍", "住址"};
    String userInfoHeaders[] = {"项", "值"};

    DefaultTableModel infoModel = new DefaultTableModel(colHeaders, 0);
    DefaultTableModel userInfoModel = new DefaultTableModel(userInfoHeaders, 0);

    public contacts(String username) {
        this.username = username;
        catalogue.setModel(infoModel);
        infoTable.setModel(userInfoModel);
        infoCreater.setUsername(username);
        infoCreater.setUserInfo(userInfoModel);

        JFrame frame = new JFrame("contacts");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Table row selection Listener;

        catalogue.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRowIdx = catalogue.getSelectedRow(); // get index of selected ROW
                //  idTextField.setText(infoModel.getValueAt(selectedRowIdx, 0).toString());
            }
        });

        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                member memInfo = new member(idTextField.getText(), nameTextField.getText(),
                        groupBox.getItemAt(groupBox.getSelectedIndex()).toString(),
                        gradeBox.getItemAt(gradeBox.getSelectedIndex()).toString(),
                        clasBox.getItemAt(clasBox.getSelectedIndex()).toString(),
                        teleNumTextField.getText(), emailTextField.getText(),
                        dormTextField.getText(), addrTextField.getText());

                int tempRowIndex = selectedRowIdx;
                infoModel.removeRow(tempRowIndex);
                infoModel.insertRow(tempRowIndex, memInfo.getRecord());


                isSaved = false;

            }
        });

        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //revokRow = {idTextField.getText(),nameTextField.getText(),groupBox.getItemAt(groupBox.getSelectedIndex()).toString(),}

                deletStack.enIndex(selectedRowIdx);

                String[] row = new String[9];
                for (int i = 0; i < 9; i++) {
                    row[i] = (catalogue.getValueAt(selectedRowIdx, i)).toString();
                    System.out.println((catalogue.getValueAt(selectedRowIdx, i)).toString());
                }

                deletStack.enDelet(row);//
                infoModel.removeRow(selectedRowIdx);

            }
        });

        createNewBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String[] emptyRow = {""};
                infoModel.insertRow((selectedRowIdx == 0 && rowCount == 0) ? selectedRowIdx : selectedRowIdx + 1, emptyRow); // 插入空表：插入非空表
                rowCount++;
                isSaved = false;
            }
        });

        revokeDeleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                infoModel.insertRow(deletStack.popIndex(), deletStack.popRevoke());
            }
        });
        ex2csvBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                csvUtil csvMaker = new csvUtil("t01.csv", catalogue);
                csvMaker.tabletoCSV();

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
        contentsScrollPane = new JScrollPane();
        Font contentsScrollPaneFont = this.$$$getFont$$$(null, -1, 16, contentsScrollPane.getFont());
        if (contentsScrollPaneFont != null) contentsScrollPane.setFont(contentsScrollPaneFont);
        contactTable.add(contentsScrollPane, BorderLayout.CENTER);
        catalogue = new JTable();
        catalogue.setGridColor(new Color(-16777216));
        catalogue.setSelectionBackground(new Color(-1));
        catalogue.setSelectionForeground(new Color(-1250068));
        catalogue.setShowHorizontalLines(true);
        catalogue.setVisible(true);
        contentsScrollPane.setViewportView(catalogue);
        tabbedPane1 = new JTabbedPane();
        mainSplit.setRightComponent(tabbedPane1);
        toModifyPanel = new JPanel();
        toModifyPanel.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("修改", toModifyPanel);
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
        tabbedPane1.addTab("用户", userPane);
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
