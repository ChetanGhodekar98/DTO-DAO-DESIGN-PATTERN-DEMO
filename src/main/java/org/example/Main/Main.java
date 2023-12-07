package org.example.Main;

import org.example.Main.DTO.Account;
import org.example.Main.Model.BankCrudOperations;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner=new Scanner(System.in);
    private static void operations() throws SQLException {
        boolean status=true;
        while (status) {
            System.out.println("1. DEPOSIT");
            System.out.println("2. WITHDRAW");
            System.out.println("3. BANK TRANSFER");
            System.out.println("4. STATEMENT");
            System.out.println("5. EXIT");
            System.out.println("ENTER YOUR CHOICE");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    deposit();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    transfer();
                    break;
                case 4:
                        displayAccountStatement();
                    break;
                case 5:
                    System.out.println("THANK FOR VISITING");
                    status=false;
                    break;
                default:
                    System.out.println("INVALID INPUT");
                    break;
            }
        }
    }
    private static void withdraw() throws SQLException {
        System.out.println("ENTER FROM ACCOUNT");
        int accountNumber= scanner.nextInt();
        System.out.println("ENTER TO NAME");
        String name= scanner.next();
        System.out.println("ENTER AMOUNT");
        double amount= scanner.nextDouble();

        Account a1=new Account();
        a1.setAccountNumber(accountNumber);
        a1.setAccountHolderName(name);
        a1.setWithdrawAmount(amount);

        BankCrudOperations b1=new BankCrudOperations();
        int count=b1.withdraw(a1);
        if(count==-1){
            System.out.println("INSUFFICIENT BALANCE");
        }else if(count>0){
            System.out.println(amount+" AMOUNT WITHDRAW SUCCESSFULLY");
        }else{
            System.out.println("SOMETHING WENT WRONG");
        }
    }
    private static void deposit(){
        System.out.println("ENTER ACCOUNT NUMBER");
        int number= scanner.nextInt();
        System.out.println("ENTER ACCOUNT HOLDER NAME");
        String name= scanner.next();
        System.out.println("ENTER AMOUNT");
        double amount= scanner.nextDouble();

        Account a1=new Account();
        a1.setAccountNumber(number);
        a1.setAccountHolderName(name);
        a1.setDepositAmount(amount);

        BankCrudOperations b1=new BankCrudOperations();
        int count=b1.deposit(a1);
        if(count>0){
            System.out.println(amount+" DEPOSITED INT YOUR ACCOUNT");
        }else {
            System.out.println("ERROR");
        }
    }
    private static void transfer(){
        System.out.println("ENTER FROM ACCOUNT");
        int fromAccount= scanner.nextInt();
        System.out.println("ENTER TO ACCOUNT");
        int toAccount= scanner.nextInt();
        System.out.println("ENTER NAME");
        String name= scanner.next();
        System.out.println("ENTER AMOUNT");
        double amount= scanner.nextDouble();

        Account a1=new Account();
        a1.setFromAccount(fromAccount);
        a1.setToAccount(toAccount);
        a1.setAccountHolderName(name);
        a1.setDepositAmount(amount);

        BankCrudOperations bco=new BankCrudOperations();
        int count=bco.transfer(a1);
        if(count>0){
            System.out.println(amount+" TRANSFER SUCCESSFUL");
        }else{
            System.out.println("SOMETHING WENT WRONG !");
        }
    }
    private static void displayAccountStatement(){
        System.out.println("ENTER ACCOUNT NUMBER");
        int accountNumber= scanner.nextInt();
        BankCrudOperations bco=new BankCrudOperations();
        ArrayList<Account> accountStatement=bco.accountStatement(accountNumber);
        System.out.println("DEPOSIT || WITHDRAW || FROM ACCOUNT || TO ACCOUNT || ACCOUNT BALANCE");
        for(Account a1:accountStatement){
            System.out.print(a1.getDepositAmount()+"\t");
            System.out.print("\t\t");
            System.out.print(a1.getWithdrawAmount()+"\t");
            System.out.print("\t\t");
            System.out.print(a1.getFromAccount()+"\t");
            System.out.print(a1.getToAccount()+"\t");
            System.out.print(a1.getAccountBalance()+"\t");
            System.out.println();
        }
    }
    public static void main(String[] args) throws SQLException {
        operations();
    }
}
