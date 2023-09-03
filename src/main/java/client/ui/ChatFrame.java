package client.ui;

import client.util.ClientUtil;
import client.DataBuffer;
import entity.Message;
import entity.Request;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatFrame extends JFrame {
    public static void main(String[] args) {
        new ChatFrame();
    }
    /**聊天对方的信息Label*/
    private JLabel otherInfoLbl;
    /** 当前用户信息Lbl */
    private JLabel currentUserLbl;
    /**聊天信息列表区域*/
    public static JTextArea msgListArea;
    /**要发送的信息区域*/
    public static JTextArea sendArea;
    /** 在线用户列表 */
    public static JList onlineList;
    /** 在线用户数统计Lbl */
    public static JLabel onlineCountLbl;
    /** 所有用户统计 */
    public static JLabel alluserLable;
    /** 准备发送的文件 */
    //public static FileInfo sendFile;

    /** 私聊复选框 */
    public JCheckBox rybqBtn;

    public ChatFrame(){
        this.init();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void init(){
        this.setTitle("JQ聊天室");
        this.setSize(750, 550);
        this.setResizable(false);

        //设置默认窗体在屏幕中央
        int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((x - this.getWidth()) / 2, (y-this.getHeight())/ 2);

        //左边主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //右边用户面板
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        //所有用户面板
        JPanel alluserPanel = new JPanel();
        alluserPanel.setLayout(new BorderLayout());


        // 创建一个分隔窗格
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                userPanel,mainPanel);
        splitPane.setDividerLocation(150);
        splitPane.setDividerSize(6);
        splitPane.setOneTouchExpandable(true);
        this.add(splitPane, BorderLayout.CENTER);

        //
        JSplitPane splitPaneright =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                splitPane,alluserPanel);
        splitPaneright.setDividerLocation(580);
        splitPaneright.setDividerSize(6);
        splitPaneright.setOneTouchExpandable(true);
        this.add(splitPaneright, BorderLayout.CENTER);

        //左上边信息显示面板
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(new Color(0x2700C4A7, true)); // 替换为你想要的颜色

        //右下连发送消息面板
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());

        // 创建一个分隔窗格
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                infoPanel, sendPanel);
        splitPane2.setDividerLocation(300);
        splitPane2.setDividerSize(3);
        mainPanel.add(splitPane2, BorderLayout.CENTER);

        otherInfoLbl = new JLabel("当前状态：群聊中...");
        infoPanel.add(otherInfoLbl, BorderLayout.NORTH);


        msgListArea = new JTextArea();
        msgListArea.setLineWrap(true);
        infoPanel.add(new JScrollPane(msgListArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());
        sendPanel.add(tempPanel, BorderLayout.NORTH);

        // 聊天按钮面板
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(btnPanel, BorderLayout.CENTER);

        //私聊按钮
        rybqBtn = new JCheckBox("私聊");
        tempPanel.add(rybqBtn, BorderLayout.EAST);

        //要发送的信息的区域
        sendArea = new JTextArea();
        sendArea.setLineWrap(true);
        sendPanel.add(new JScrollPane(sendArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        //聊天按钮面板
        JPanel btn2Panel = new JPanel();
        btn2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.add(btn2Panel, BorderLayout.SOUTH);
        JButton closeBtn = new JButton("关闭");
        closeBtn.setToolTipText("退出整个程序");
        btn2Panel.add(closeBtn);
        JButton submitBtn = new JButton("发送");
        submitBtn.setToolTipText("按Enter键发送消息");
        btn2Panel.add(submitBtn);
        sendPanel.add(btn2Panel, BorderLayout.SOUTH);

        //当前用户面板
        JPanel currentUserPane = new JPanel();
        currentUserPane.setLayout(new BorderLayout());
        currentUserPane.add(new JLabel("当前用户"), BorderLayout.NORTH);
        currentUserPane.setBackground(new Color(0x2776C400, true));

        //在线用户列表面板
        JPanel onlineListPane = new JPanel();
        onlineListPane.setLayout(new BorderLayout());
        onlineCountLbl = new JLabel("在线用户列表");
        onlineListPane.add(onlineCountLbl, BorderLayout.NORTH);
        onlineListPane.setBackground(new Color(0x2776C400, true));

        //所有用户列表面板
        alluserLable = new JLabel("所有注册用户列表");
        alluserPanel.add(alluserLable, BorderLayout.NORTH);
        alluserPanel.setBackground(new Color(0x2776C400, true));

        // 右边用户列表创建一个分隔窗格
        JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                currentUserPane,onlineListPane);
        splitPane3.setDividerLocation(180);
        splitPane3.setDividerSize(3);
        userPanel.add(splitPane3, BorderLayout.CENTER);



        //当前用户信息Label
        currentUserLbl = new JLabel();
        currentUserPane.add(currentUserLbl);

        //关闭窗口
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                logout();
            }
        });

        //关闭按钮的事件
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                logout();
            }
        });

        //选择某个用户私聊
        rybqBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(rybqBtn.isSelected()){
                    User selectedUser = (User)onlineList.getSelectedValue();
                    if(null == selectedUser){
                        otherInfoLbl.setText("当前状态：私聊(选中在线用户列表中某个用户进行私聊)...");
                    }else if(DataBuffer.currentUser.getId() == selectedUser.getId()){
                        otherInfoLbl.setText("当前状态：想自言自语?...系统不允许");
                    }else{
                        otherInfoLbl.setText("当前状态：与 "+ selectedUser.getNickName()
                                +"(" + selectedUser.getId() + ") 私聊中...");
                    }
                }else{
                    otherInfoLbl.setText("当前状态：群聊...");
                }
            }
        });

        //发送文本消息
        sendArea.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == Event.ENTER){
                    sendTxtMsg();
                }
            }
        });
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sendTxtMsg();
            }
        });

        //this.loadData();  //加载初始数据
    }



    /** 发送文本消息 */
    public void sendTxtMsg(){
        String content = sendArea.getText();
        if ("".equals(content)) { //无内容
            JOptionPane.showMessageDialog(ChatFrame.this, "不能发送空消息!",
                    "不能发送", JOptionPane.ERROR_MESSAGE);
        } else { //发送
            User selectedUser = (User)onlineList.getSelectedValue();

            //如果设置了ToUser表示私聊，否则群聊
            Message msg = new Message();
            if(rybqBtn.isSelected()){  //私聊
                if(null == selectedUser){
                    JOptionPane.showMessageDialog(ChatFrame.this, "没有选择私聊对象!",
                            "不能发送", JOptionPane.ERROR_MESSAGE);
                    return;
                }else if (DataBuffer.currentUser.getId() == selectedUser.getId()){
                    JOptionPane.showMessageDialog(ChatFrame.this, "不能给自己发送消息!",
                            "不能发送", JOptionPane.ERROR_MESSAGE);
                    return;
                }else{
                    msg.setToUser(selectedUser);
                }
            }
            msg.setFromUser(DataBuffer.currentUser);
            msg.setSendTime(new Date());

            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            StringBuffer sb = new StringBuffer();
            sb.append(" ").append(df.format(msg.getSendTime())).append(" ")
                    .append(msg.getFromUser().getNickName())
                    .append("(").append(msg.getFromUser().getId()).append(") ");
            if(!this.rybqBtn.isSelected()){//群聊
                sb.append("对大家说");
            }
            sb.append("\n  ").append(content).append("\n");
            msg.setMessage(sb.toString());

            Request request = new Request();
            request.setAction("chat");
            request.setAttributeCustom("msg", msg);
            try {
                ClientUtil.sendTextRequest2(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //JTextArea中按“Enter”时，清空内容并回到首行
            InputMap inputMap = sendArea.getInputMap();
            ActionMap actionMap = sendArea.getActionMap();
            Object transferTextActionKey = "TRANSFER_TEXT";
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),transferTextActionKey);
            actionMap.put(transferTextActionKey,new AbstractAction() {
                private static final long serialVersionUID = 7041841945830590229L;
                public void actionPerformed(ActionEvent e) {
                    sendArea.setText("");
                    sendArea.requestFocus();
                }
            });
            sendArea.setText("");
            ClientUtil.appendTxt2MsgListArea(msg.getMessage());
        }
    }
    private void logout() {
        int select = JOptionPane.showConfirmDialog(ChatFrame.this,
                "确定退出吗？\n\n退出程序将中断与服务器的连接!", "退出聊天室",
                JOptionPane.YES_NO_OPTION);
        if (select == JOptionPane.YES_OPTION) {
            Request req = new Request();
            req.setAction("exit");
            req.setAttributeCustom("user", DataBuffer.currentUser);
            try {
                ClientUtil.sendTextRequest(req);
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally{
                System.exit(0);
            }
        }else{
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
    }


}