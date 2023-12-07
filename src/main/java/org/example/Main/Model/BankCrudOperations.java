package org.example.Main.Model;

import org.example.Main.DTO.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//SIMPLE EXAMPLE TO UNDERSTAND DTO-DAO DESIGN PATTERN

public class BankCrudOperations {
    private static String deposit="INSERT INTO bank(account_number,account_name,deposite,balance) VALUES(?,?,?,?)";
    private static String depositHelper="SELECT balance FROM bank WHERE account_number=? ORDER BY tx_id DESC LIMIT 1";
    private static String checkBalance="select balance from bank where account_number=? and balance>=? order by balance desc limit 1";
    private static String withdraw="INSERT INTO bank(account_number,account_name,withdraw,balance) VALUES(?,?,?,?)";
    private static String getBalance="SELECT BALANCE FROM BANK WHERE ACCOUNT_NUMBER=? " +
            "ORDER BY TX_ID DESC LIMIT 1;";
    private static String fromAccountQuery="insert into bank(account_number,withdraw,to_account,balance) values(?,?,?,?)";

    private static String toAccountQuery="insert into bank(account_number,deposite,from_account,balance) values(?,?,?,?)";

    private static String accountStatement="select * from bank where account_number=?";
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

    public  int transfer(Account a1) {
        String check1="select * from bank where account_number=?";
        String check2="select * from bank where account_number=?";
        Connection con=GetConnection.GetConnection();
        try {
            PreparedStatement pstmt1=con.prepareStatement(check1);
            pstmt1.setInt(1,a1.getFromAccount());
            ResultSet rs1=pstmt1.executeQuery();

            if(rs1.next()){
                PreparedStatement pstmt2=con.prepareStatement(check2);
                pstmt2.setInt(1,a1.getToAccount());
                ResultSet rs2=pstmt2.executeQuery();

                if(rs2.next()){

                    PreparedStatement pstmt3=con.prepareStatement(getBalance);
                    pstmt3.setInt(1,a1.getFromAccount());
                    ResultSet rs3=pstmt3.executeQuery();
                    if(rs3.next() && rs3.getDouble(1)>=a1.getDepositAmount()){

                        double tempBalance=rs3.getDouble(1);

                        PreparedStatement pstmt4=con.prepareStatement(fromAccountQuery);
                        pstmt4.setInt(1,a1.getFromAccount());
                        pstmt4.setDouble(2,a1.getDepositAmount());
                        pstmt4.setInt(3,a1.getToAccount());
                        pstmt4.setDouble(4,tempBalance-a1.getDepositAmount());
                        int count1=pstmt4.executeUpdate();

                        PreparedStatement pstmt5=con.prepareStatement(getBalance);
                        pstmt5.setInt(1,a1.getToAccount());
                        ResultSet rs5=pstmt5.executeQuery();

                        if(rs5.next()){
                            double tempBalance1=rs5.getDouble(1);
                            PreparedStatement pstmt6= con.prepareStatement(toAccountQuery);
                            pstmt6.setInt(1,a1.getToAccount());
                            pstmt6.setDouble(2,a1.getDepositAmount());
                            pstmt6.setInt(3,a1.getFromAccount());
                            pstmt6.setDouble(4,tempBalance1+a1.getDepositAmount());

                            int count2=pstmt6.executeUpdate();

                            if(count1>0 && count2>0){
                                return count1+count2;
                            }

                        }

                    }else {
                        return -1;
                    }


                }else {
                     return -1;
                }

            }else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
    public void accountStatement(int accountNumber){
        Connection con=GetConnection.GetConnection();
        try {
            PreparedStatement psmt= con.prepareStatement(accountStatement);
            psmt.setInt(1,accountNumber);
            ResultSet rs=psmt.executeQuery();

            System.out.println("TX-ID || DEPOSIT || WITHDRAW || FROM ACCOUNT || TO ACCOUNT || ACCOUNT BALANCE");
            while(rs.next()){
                System.out.print( rs.getInt(1)+"\t");
                System.out.print(rs.getDouble(4)+"\t");
                System.out.print("\t\t");
                System.out.print(rs.getDouble(5)+"\t");
                System.out.print("\t\t");
                System.out.print(rs.getInt(6)+"\t");
                System.out.print(rs.getInt(7)+"\t");
                System.out.print(rs.getDouble(8)+"\t");
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
