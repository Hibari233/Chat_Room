package server.entity;

import entity.User;
import server.DataBuffer;
import util.IOUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static util.PasswordEncryption.hashPassword;

public class UserService {
    private static int idCount = 10003; //id

    /** 新增用户 */
    public void addUser(User user){
        user.setId(++idCount);
        List<User> users = loadAllUser();
        users.add(user);
        saveAllUser(users);
    }

    /** 用户登录 */
    public User login(long id, String password) throws NoSuchAlgorithmException {
        User result = null;
        List<User> users = loadAllUser();
        for (User user : users) {
            if(id == user.getId() && hashPassword(password).equals(hashPassword(user.getPassword()))){
                result = user;
                break;
            }
        }
        return result;
    }

    /** 根据ID加载用户 */
    public User loadUser(long id){
        User result = null;
        List<User> users = loadAllUser();
        for (User user : users) {
            if(id == user.getId()){
                result = user;
                break;
            }
        }
        return result;
    }


    /** 加载所有用户 */
    @SuppressWarnings("unchecked")
    public List<User> loadAllUser() {
        List<User> list = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new FileInputStream(
                            DataBuffer.configProp.getProperty("dbpath")));

            list = (List<User>)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            IOUtil.close(ois);
        }
        return list;
    }

    private void saveAllUser(List<User> users) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(
                            DataBuffer.configProp.getProperty("dbpath")));
            //写回用户信息
            oos.writeObject(users);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            IOUtil.close(oos);
        }
    }



    /** 初始化几个测试用户 */
    public void initUser() throws NoSuchAlgorithmException {
        User user = new User("Hibari", "admin", "男");
        user.setId(10000);

        User user2 = new User("Xiaoyu", "admin","男");
        user2.setId(10001);

        User user3 = new User("Nina", "admin","女");
        user3.setId(10002);

        List<User> users = new CopyOnWriteArrayList<User>();
        users.add(user);
        users.add(user2);
        users.add(user3);

        this.saveAllUser(users);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        new UserService().initUser();
        List<User> users = new UserService().loadAllUser();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
