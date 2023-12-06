package org.example.Main.Model;

import org.example.Main.DTO.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankCrudOperations {
    private static String deposit="INSERT INTO bank(account_number,account_name,deposite,balance) VALUES(?,?,?,?)";
    private static String depositHelper="SELECT balance FROM bank WHERE account_number=? ORDER BY balance DESC LIMIT 1";
    private static String checkBalance="select balance from bankaccount where account_number=? and balance>=? order by balance desc limit 1";
    private static String withdraw="INSERT INTO bankaccount(account_number,account_name,withdraw,balance) VALUES(?,?,?,?)";
    public int deposit(Account a1){
        try {
            PreparedStatement temp=GetConnection.GetConnection().prepareStatement(depositHelper);
            temp.setInt(1,a1.getAccountNumber());
            ResultSet rs=temp.executeQuery();
            double balance=0.0;
            if(rs.next()){
                balance=rs.getDouble(1);
            }
            PreparedStatement pstmt=GetConnection.GetConnection().prepareStatement(deposit);
            pstmt.setInt(1,a1.getAccountNumber());
            pstmt.setString(2,a1.getAccountHolderName());
            pstmt.setDouble(3,a1.getDepositAmount());
            pstmt.setDouble(4,(balance+a1.getDepositAmount()));
            int count=pstmt.executeUpdate();
            
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int withdraw(Account a1) {

        PreparedStatement stmt= null;
        try {
            stmt = GetConnection.GetConnection().prepareStatement(checkBalance);
            stmt.setInt(1,a1.getAccountNumber());
            stmt.setDouble(2,a1.getWithdrawAmount());
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                double balance= rs.getDouble(1);
                PreparedStatement pstmt=GetConnection.GetConnection().prepareStatement(withdraw);
                pstmt.setInt(1,a1.getAccountNumber());
                pstmt.setString(2,a1.getAccountHolderName());
                pstmt.setDouble(3,a1.getWithdrawAmount());
                pstmt.setDouble(4,(balance-a1.getWithdrawAmount()));
                int count=pstmt.executeUpdate();
             return count;

            } {
             return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }






    }
}
