package entity;

import java.security.NoSuchAlgorithmException;

import static util.PasswordEncryption.hashPassword;

public class User {
    private long id;
    private String nickName;
    private String sex;
    private String password;

    public User(String nickName, String password ,String sex) throws NoSuchAlgorithmException {
        this.nickName = nickName;
        this.password = hashPassword(password);
        this.sex=sex;
        if(nickName.equals("")||nickName==null)
        {
            this.nickName = "未命名";
        }else{
            this.nickName = nickName;
        }
    }

    public User(String nickName, String password) throws NoSuchAlgorithmException {
        this.nickName = nickName;
        this.password = hashPassword(password);
    }


    public long getId() {return this.id;}
    public void setId(long id){
        this.id = id;
    }

    public void setNickName(String name) {this.nickName = name;}

    public String getNickName() {return this.nickName;}

    public Boolean checkPassword(String password) throws NoSuchAlgorithmException {
        return this.password.equals(hashPassword(password));
    }

    public String getPassword() {return this.password;}

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = hashPassword(password);
    }

    public void setSex(String sex) {this.sex = sex;}

    public String getSex() {return this.sex;}

}

