package com.test.starter.test.ssh;

import com.jcraft.jsch.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: xq
 * @Date: 2021/03/11 11:22
 */
public class ExecTest {
    public static void main(String[] arg){
//        String[] aa = {"adf"};
//        officialExcample(aa);
        myExec();
    }

    public static void myExec() {
        try {
            // 创建Jsch
            JSch jsch = new JSch();

            // 获取会话。设置主机、账号、密码
            String username = "root";
            String host = "39.108.210.82";
            int port = 22;
            Session session = jsch.getSession(username, host, port);
            session.setPassword("Dmgyz,chlry");

            session.setConfig("StrictHostKeyChecking", "no");

            // 开启连接
            session.connect(1000);

            int size = 0;
            byte[] buf = new byte[1024];
            while ((size = System.in.read(buf)) != -1) {
                String command = new String(buf, 0, size);
                String result = execCommand(session, command);
                System.out.println(result);
                System.out.println(result.contains("\n"));
            }

//            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String execCommand(Session session, String command) throws JSchException, IOException {
        // 开启连接管道
        String type = "exec";
        ChannelExec channel = (ChannelExec) session.openChannel(type);

        // 通过连接管道传输cmd
        channel.setCommand(command);

        // 配置
        channel.setErrStream(System.err);

        // 运行命令
        channel.connect();

        // 从连接管道获取数据
        int len = 0;
        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        BufferedInputStream bis = new BufferedInputStream( channel.getInputStream());
        while ((len = bis.read(buf)) != -1) {
//            System.out.println(new String(buf, 0, len));
            sb.append(new String(buf, 0, len));
        }

        channel.disconnect();
        return sb.toString();
    }

    public static void officialExcample(String[] arg){
        try{
            JSch jsch=new JSch();

            String host=null;
            if(arg.length>0){
                host=arg[0];
            }
            else{
                host=JOptionPane.showInputDialog("Enter username@hostname",
                        System.getProperty("user.name")+
                                "@localhost");
            }
//            String user=host.substring(0, host.indexOf('@'));
            host = "root@39.108.210.82";
            String user=host.substring(0, host.indexOf('@'));
            host=host.substring(host.indexOf('@')+1);

            // 连接，获取会话
            Session session=jsch.getSession(user, host, 22);

      /*
      String xhost="127.0.0.1";
      int xport=0;
      String display=JOptionPane.showInputDialog("Enter display name",
                                                 xhost+":"+xport);
      xhost=display.substring(0, display.indexOf(':'));
      xport=Integer.parseInt(display.substring(display.indexOf(':')+1));
      session.setX11Host(xhost);
      session.setX11Port(xport+6000);
      */

            // username and password will be given via UserInfo interface.
            UserInfo ui=new MyUserInfo();
            session.setUserInfo(ui);
            session.connect();

            String command=JOptionPane.showInputDialog("Enter command",
                    "set|grep SSH");

            // 开启连接管道
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            // X Forwarding
            // channel.setXForwarding(true);

            //channel.setInputStream(System.in);
            channel.setInputStream(null);

            //channel.setOutputStream(System.out);

            //FileOutputStream fos=new FileOutputStream("/tmp/stderr");
            //((ChannelExec)channel).setErrStream(fos);
            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();

            channel.connect();

            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    if(in.available()>0) continue;
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
        public String getPassword(){ return passwd; }
        public boolean promptYesNo(String str){
            Object[] options={ "yes", "no" };
            int foo=JOptionPane.showOptionDialog(null,
                    str,
                    "Warning",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            return foo==0;
        }

        String passwd;
        JTextField passwordField=(JTextField)new JPasswordField(20);

        public String getPassphrase(){ return null; }
        public boolean promptPassphrase(String message){ return true; }
        public boolean promptPassword(String message){
            Object[] ob={passwordField};
            int result=
                    JOptionPane.showConfirmDialog(null, ob, message,
                            JOptionPane.OK_CANCEL_OPTION);
            if(result==JOptionPane.OK_OPTION){
                passwd=passwordField.getText();
                return true;
            }
            else{
                return false;
            }
        }
        public void showMessage(String message){
            JOptionPane.showMessageDialog(null, message);
        }
        final GridBagConstraints gbc =
                new GridBagConstraints(0,0,1,1,1,1,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(0,0,0,0),0,0);
        private Container panel;
        public String[] promptKeyboardInteractive(String destination,
                                                  String name,
                                                  String instruction,
                                                  String[] prompt,
                                                  boolean[] echo){
            panel = new JPanel();
            panel.setLayout(new GridBagLayout());

            gbc.weightx = 1.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 0;
            panel.add(new JLabel(instruction), gbc);
            gbc.gridy++;

            gbc.gridwidth = GridBagConstraints.RELATIVE;

            JTextField[] texts=new JTextField[prompt.length];
            for(int i=0; i<prompt.length; i++){
                gbc.fill = GridBagConstraints.NONE;
                gbc.gridx = 0;
                gbc.weightx = 1;
                panel.add(new JLabel(prompt[i]),gbc);

                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 1;
                if(echo[i]){
                    texts[i]=new JTextField(20);
                }
                else{
                    texts[i]=new JPasswordField(20);
                }
                panel.add(texts[i], gbc);
                gbc.gridy++;
            }

            if(JOptionPane.showConfirmDialog(null, panel,
                    destination+": "+name,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE)
                    ==JOptionPane.OK_OPTION){
                String[] response=new String[prompt.length];
                for(int i=0; i<prompt.length; i++){
                    response[i]=texts[i].getText();
                }
                return response;
            }
            else{
                return null;  // cancel
            }
        }
    }
}
