/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 *
 * @author suong
 */
public class SendScreen implements Runnable{
    private final Socket socket;
    private final OutputStream out;
    private final Robot robot;
    private final boolean continueLoop = true;
    private final Rectangle screenRect;
    
    public SendScreen(Socket s) throws IOException, AWTException
    {
        this.socket = s;
        this.out = socket.getOutputStream();
        this.robot = new Robot();
        this.screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }
    
    @Override
    public void run() {
        try {

            // Vòng lặp gửi ảnh mỗi giây
            while (continueLoop) {
                BufferedImage screenCapture = robot.createScreenCapture(screenRect);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(screenCapture, "jpg", byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                out.write(imageBytes);
                out.flush();

                System.out.println("Screenshot sent to server.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
