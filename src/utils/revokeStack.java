package utils;

import javax.swing.*;
import java.awt.*;
import java.util.EmptyStackException;
import java.util.Stack;
import classes.member;

public class revokeStack {
    private Stack <member> revokStack = new Stack<>();
    private Stack <Integer> indexStack = new Stack<>();

    //private JTable table;

    public void enIndex(int idx){
        indexStack.push(idx);
    }

    public void enDelet(member row){
        revokStack.push(row);
    }

    public int popIndex() {
        if(!indexStack.isEmpty()){
            return indexStack.pop();
        } else {
            return -1;
        }
    }

    public member popRevoke(){
        if(!revokStack.isEmpty()){
            return revokStack.pop();
        } else {
            return null;
        }
    }

}
