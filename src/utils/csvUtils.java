package utils;

import com.sun.imageio.spi.OutputStreamImageOutputStreamSpi;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class csvUtils {
    //private String[] rowData = new String[100];  //unused
    private int rowCount;
    private int colCount;
    private String localSaveAddress;
    private String csvFileName;
    private JTable tableToRecord;

    public csvUtils(String address,String fileName,JTable table){
        localSaveAddress = address;
        csvFileName = fileName;
        tableToRecord = table;
    }

    public String getFileName(){
        return (localSaveAddress + "/" + csvFileName);
    }

    public String readRow(int rowIndex){
        String rowString = new String();
        for(int i = 0;i < tableToRecord.getColumnCount();i++){
            rowString += tableToRecord.getValueAt(rowIndex,i).toString();
            rowString += (i == tableToRecord.getColumnCount() -1) ? "" : ",";
        }
        return rowString;
    }

    public void makeCSVFile(){
        File f = new File(this.getFileName());


    }


}
