/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author suong
 */
public class ServerConnect implements Runnable{
    
    private final ServerSocket serverSocket;
    private Socket socket;
    private BufferedOutputStream bous;
    private BufferedInputStream bin;
    private DataInputStream din;
    private DataOutputStream dous;
    private boolean clientConnect;
    private static final int port = 1234;
    private String password;
    
    public ServerConnect(String password) throws IOException
    {
        this.password = password;
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server dang lang nghe...");
        
        this.clientConnect = false;
    }
    
    @Override
    public void run()
    {
        while(clientConnect)
        {
            try {
                this.socket = serverSocket.accept();
                System.out.println("Client da ket noi toi Server thanh cong!");
                this.din = new DataInputStream(socket.getInputStream());
                this.dous = new DataOutputStream(socket.getOutputStream());
                String pass = din.readUTF();
                
                if (pass.equals(this.password))
                {
                    clientConnect = true;
                    
                    this.dous.writeUTF("accepted");
                    System.out.println("Chap nhan ket noi!");
                }
                
                if (clientConnect)
                {
                } else {
                    this.dous.writeUTF("refused");
                    this.socket.close();
                    System.out.println("Tu choi ket noi vi sai mat khau");
                }
                    

            } catch (IOException ex) {
                Logger.getLogger(ServerConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
    }
    
    public boolean getClientConnect()
    {
        return this.clientConnect;
    }
    
    public void setClientConnect(boolean value)
    {
        this.clientConnect = value;
    }
    
    
    public Socket getSocket()
    {
        return this.socket;
    }
}
