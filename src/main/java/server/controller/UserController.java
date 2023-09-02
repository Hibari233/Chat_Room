package server.controller;

import entity.User;
import util.DatabaseUtil;
import static util.PasswordEncryption.hashPassword;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserController {
    private static int idCount = 1; //用户数
    private final String jdbcUrl = "jdbc:mysql://localhost:3306/chatroom";
    private final String username = "root";
    private final String password = "password"; //修改为MySQL密码
    // 新增用户
    public void addUser( User user) {
        user.setId(++idCount);
        List<User> users = loadAllUser();
        users.add(user);
        saveAllUser(users);
    }

    //用户登录
    public User login(long id, String password) throws NoSuchAlgorithmException {
        User result = null;
        List<User> users = loadAllUser();
        for(User user: users) {
            if (id == user.getId() && hashPassword(password).equals(user.getPassword())) {
                result = user;
                break;
            }
        }
        return result;
    }

    //根据id加载用户
    public User loadUser(long id) {
        User result = null;
        List<User> users = loadAllUser();
        for (User user: users) {
            if (id == user.getId()) {
                result = user;
                break;
            }
        }
        return  result;
    }

    //加载所有用户
    public List<User> loadAllUser() {
        List<User> users = new ArrayList<>();
        try {
            DatabaseUtil databaseUtil = new DatabaseUtil(this.jdbcUrl, this.username, this.password);
            Connection connection = databaseUtil.connect();
            if (connection != null) {
                String query = "Select * From users;";
                ResultSet resultSet = databaseUtil.executeQuery(query);

                while (resultSet.next()) {
                    User user = new User("", "", "");
                    user.setId(resultSet.getLong("id"));
                    user.setNickName(resultSet.getString("nickName"));
                    user.setPassword((resultSet.getString("password")));
                    user.setSex(resultSet.getString("sex"));
                    users.add(user);
                }
                databaseUtil.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    //写入数据库现有内存用户
    public void saveAllUser(List<User> users) {
        try {
            DatabaseUtil databaseUtil = new DatabaseUtil(this.jdbcUrl, this.username, this.password);
            Connection connection = databaseUtil.connect();
            if (connection != null) {
                String query = "INSERT INTO users (nickName, password, sex) VALUES (?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                for (User user: users) {
                    preparedStatement.setString(1, user.getNickName());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setString(3, user.getSex());

                    preparedStatement.executeUpdate();
                }

                databaseUtil.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始化一个测试用户
    public void initUser() throws NoSuchAlgorithmException {
        User user = new User("admin", "Admin", "m");
        user.setId(2);
        List<User> users = new CopyOnWriteArrayList<User>();
        users.add(user);

        this.saveAllUser(users);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        new UserController().initUser();
        List<User> users = new UserController().loadAllUser();
        for (User user : users) {
            System.out.println("User ID: " + user.getId());
            System.out.println("NickName: " + user.getNickName());
            System.out.println("Password: " + user.getPassword());
            System.out.println("Sex: " + user.getSex());
            System.out.println("----------------------------------");
        }
    }
}