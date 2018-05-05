//enroll members from database

package Dao;

import classes.admin;
import classes.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class memberDao {

    public boolean memberIDChk(Vector<member> contacts, member newMember){
        for(int i = 0; i < contacts.size(); i++){
            if( newMember.getId().equals(contacts.get(i).getId())){
                return false; // ID is not unique
            }
        }
        return true; // ID is unique.
    }

    public member dbStringToMem(String queryStr){
        String[] queryArr = queryStr.split("|");
        member newContact = new member( queryArr[0],
                                        queryArr[1],
                                        queryArr[2],
                                        queryArr[3],
                                        queryArr[4],
                                        queryArr[5],
                                        queryArr[6],
                                        queryArr[7],
                                        queryArr[8]);
        return newContact;
    }

    public Vector<member> readFromDB(Connection conn) throws Exception{
        Vector<member> contacts = new Vector<>();

        String sql = "select * from t_members order by ID asc limit 0,1000";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            contacts.add(new member(rs.getString("id"),
                                    rs.getString("name"),
                                    rs.getString("grp"),
                                    rs.getString("grade"),
                                    rs.getString("class"),
                                    rs.getString("phonenum"),
                                    rs.getString("email"),
                                    rs.getString("dormitory"),
                                    rs.getString("address")));
        }
        return contacts;

    }



    public boolean saveDB(Vector<member> contacts, Connection conn) throws Exception{
        String truncate = "truncate table t_members";
        PreparedStatement dstmt = conn.prepareStatement(truncate);
        dstmt.executeUpdate();

        String save = "insert into t_members values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement istmt = conn.prepareStatement(save);

        int count = 0;

        for(int i = 0; i < contacts.size(); i++){
            istmt.setString(1,contacts.get(i).getId());
            istmt.setString(2,contacts.get(i).getName());
            istmt.setString(3,contacts.get(i).getGroup());
            istmt.setString(4,contacts.get(i).getGrade());
            istmt.setString(5,contacts.get(i).getClas());
            istmt.setString(6,contacts.get(i).getPhoneNum());
            istmt.setString(7,contacts.get(i).getEmail());
            istmt.setString(8,contacts.get(i).getDormitory());
            istmt.setString(9,contacts.get(i).getAddress());

            count += istmt.executeUpdate();
        }
        if (count == contacts.size()){
            System.out.println("Database updated");
            return true;
        }

        System.out.println("Failed to update database");
        return false;

    }



}
