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

public class main {

    public static void main(String[] args){
        revokeStack a = new revokeStack();
        a.enIndex(5);


        new login();
    }
}
