/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;
import java.net.UnknownHostException;


import java.util.Random;
import java.io.IOException;
import java.net.Socket;

import Connection.ServerConnect;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author suong
 */
public class Authentication extends JFrame{
    private ServerConnect serverConnect;
    private JTextField IdField;
    private JPasswordField passwordField;
    private JButton connectButton;
    private JLabel statusLabel;
    private JTextField IdLabel;
    private JTextField ownPassword;
    private Socket socket;
    private DataOutputStream out;
    
    public Authentication() throws IOException
    {

        setTitle("Authentication");
        setSize(600 , 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Thanh phan cua giao dien
        IdField = new JTextField(15);
        passwordField = new JPasswordField(15);
        connectButton = new JButton("connect");
        statusLabel = new JLabel("Please enter your credentials");
        IdLabel = new JTextField(15);
        IdLabel.setText(getIp());
        IdLabel.setEditable(false);
        ownPassword = new JTextField(15);
        ownPassword.setText(getGeneratedPassword());
        ownPassword.setEditable(false);
        // Bố cục
        setLayout(new GridLayout(6 , 1));
        add(new JLabel("Your ID:"));
        add(IdLabel);
        add(new JLabel("Your Password:"));
        add(ownPassword);
        add(new JLabel("ID:"));
        add(IdField);
        
        add(new JLabel("Password:"));
        add(passwordField);
        add(connectButton);
        add(statusLabel);
        
        // khởi tạo server socket như một luồng chạy song song.
        startServerSocket();
        
        

        // Xử lý sự kiện khi nhấn nút Login
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = IdField.getText();
                String password = new String(passwordField.getPassword());

                // Gọi phương thức xác thực
                boolean isAuthenticated;
                isAuthenticated = authenticate(id, password);
                if (isAuthenticated) {
                    statusLabel.setText("Login successful");
                    // Mở kết nối đến server sau khi xác thực thành công
                    // connectToServer(password);
                } else {
                    statusLabel.setText("Invalid credentials, try again.");
                }
            }
        });  
    }

    // Phương thức xác thực
    private boolean authenticate(String id, String password) {
        boolean checkValidConnect = true;
        try{
            this.socket = new Socket(id , 1234);
            this.out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(password);
            out.flush();
            System.out.println("Ket noi thanh cong den server");
        }catch (IOException e)
        {
            e.printStackTrace();
            checkValidConnect = false;
        }
        return checkValidConnect;
    }

    
    // Lấy địa chỉ IP của máy client
    private String getIp()
    {
        String id;
        try{
            InetAddress inetAddress = InetAddress.getLocalHost();
            id = inetAddress.getHostAddress();
            return id;
            
        }catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
    private String getGeneratedPassword()
    {
        Random random = new Random();
        StringBuilder password = new StringBuilder(5);
        for (int i = 0 ; i < 5 ; i++ )
        {
            password.append(random.nextInt(10));
        }
        return password.toString();
    }
    
    public static void main(String[] args) throws IOException
    {
        Authentication auth = new Authentication();
        auth.setVisible(true);
    }
    
    private void startServerSocket() throws IOException
    {
        this.serverConnect = new ServerConnect(this.ownPassword.getText());
        Thread thread = new Thread(this.serverConnect);
        thread.start();
    }
}
