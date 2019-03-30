package com.example.hospital.table;

/**
 * Created by Wachsbeere on 2019/1/7.
 */

public class User {

    private String accountName;
    private String password;
    private int flag;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
