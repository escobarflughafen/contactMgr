package utils;

import com.sun.imageio.spi.OutputStreamImageOutputStreamSpi;

import javax.swing.*;
import java.io.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

public class csvUtil {
    //private String[] rowData = new String[100];  //unused
    private int rowCount;
    private int colCount;
    //private String localSaveAddress;
    private String csvFileName;
    private JTable tableToRecord;
    private File csvFile;

    public csvUtil(String fileName,JTable table){
        //localSaveAddress = address;
        File dir = new File("csv");
        if(!dir.exists()) {
            dir.mkdir();
        }
        csvFile = new File("./csv/"+fileName);
        csvFileName = fileName;
        tableToRecord = table;
    }


    public String readRow(int rowIndex){
        String rowString = new String();
        for(int i = 0;i < tableToRecord.getColumnCount();i++){
            rowString += (tableToRecord.getValueAt(rowIndex,i) == null)
                            ? "" : tableToRecord.getValueAt(rowIndex,i).toString();
            rowString += (i == tableToRecord.getColumnCount() -1) ? "" : ",";
        }
        return rowString;
    }

    public void tabletoCSV(){
        try {
            File csv = new File("./csv/"+csvFileName);
            csv.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(csv));
            for (int i = 0; i < tableToRecord.getColumnCount(); i++){
                out.write(((tableToRecord.getColumnName(i).isEmpty())? "" : tableToRecord.getColumnName(i)) +
                        ((i == tableToRecord.getColumnCount() - 1) ? "" : ","));


            }
            out.write("\n");

            for (int j = 0; j < tableToRecord.getRowCount(); j++) {
                out.write(this.readRow(j));
                out.write("\n");
            }
            out.flush();
            out.close();
            System.out.println("csv file flushed.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CSVtoTable(){
        try{
            File csv = new File("./csv/"+csvFileName);
            if(!csv.exists())
            {
                System.out.println("csv file doesn't exists");
            } else {
                BufferedReader in = new BufferedReader(new FileReader(csv));
                for(int i = 0; i < in.lines().toArray().length; i++){
                  //  tableToRecord.getModel(). = in.readLine();
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
