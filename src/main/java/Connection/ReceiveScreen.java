/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 *
 * @author suong
 */
public class ReceiveScreen implements Runnable{
    private Socket socket;
    private InputStream inputStream;
    private boolean continueLoop;
    
    public ReceiveScreen(Socket socket) throws IOException
    {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.continueLoop = true;
    }

    @Override
    public void run() {
        try
        {
            while(continueLoop)
            {
                BufferedImage screenImage = ImageIO.read(inputStream);
                if (screenImage != null)
                {
                    
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void stop() {
        continueLoop = false;
        close();
    }

    // Đóng kết nối
    private void close() {
        try {
            if (socket != null) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
