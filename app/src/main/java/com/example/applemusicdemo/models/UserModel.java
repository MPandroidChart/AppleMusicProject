package com.example.applemusicdemo.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 *  一个模型就相当于一个表，模型中的每一列相当于表中的一个字段；
 */
public class UserModel extends RealmObject {
    @PrimaryKey
    private String phone;
    @Required
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
