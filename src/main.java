import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.login;
import utils.revokeStack;
import ui.contacts;

public class main {

    public static void main(String[] args){
        new login();
    };
}
