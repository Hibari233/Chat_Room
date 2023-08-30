package entity;

import javax.swing.*;
import java.util.List;

public class OnlineUserListModel extends AbstractListModel{
    private List<User> onlineUsers;

    public OnlineUserListModel(List<User> onlineUsers) { this.onlineUsers = onlineUsers; }
    public void addElement(Object object){
        if (onlineUsers.contains((User) object))
            return;
        int index = onlineUsers.size();
        onlineUsers.add((User) object);

        fireIntervalAdded(this, index, index); //这里是swing，会触发相应的事件，以便界面组件进行更新。
    }

    public boolean removeElement(Object object) {
        int index = onlineUsers.indexOf((User) object); //获得要删除User的索引
        if (index > 0) {
            fireIntervalRemoved(this, index, index);
        }
        return onlineUsers.remove((User) object);
    }

    @Override
    public int getSize() {
        return onlineUsers.size();
    }

    @Override
    public Object getElementAt(int index) {
        return onlineUsers.get(index);
    }

    public List<User> getOnlineUsers() { return onlineUsers; }
}
