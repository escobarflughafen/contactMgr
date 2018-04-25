package utils;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class revokeStack {
    private Stack <String[]> revokStack = new Stack<>();
    private Stack <Integer> indexStack = new Stack<>();
    //private JTable table;

    public void enIndex(int idx){
        indexStack.push(idx);
    }

    public void enDelet(String[] row){
        revokStack.push(row);
    }

    public int popIndex(){
        return indexStack.pop();
    }

    public String[] popRevoke(){
        return revokStack.pop();
    }


}
