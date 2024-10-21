/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author suong
 */
public class ReceiveEvent implements Runnable{
    private final Socket clientSocket;
    private boolean continueLoop;
    private final Robot robot;
    private final BufferedReader reader;
    
    public ReceiveEvent (Socket socket) throws AWTException, IOException
    {
        this.clientSocket = socket;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.continueLoop = true;
        this.robot = new Robot();
    }
    
    @Override
    public void run()
    {
        try
        {
            while (continueLoop)
            {
                String eventMessage = reader.readLine();

                if (eventMessage != null)
                {
                    handleEvent(eventMessage);
                }
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void handleEvent(String eventMessage)
    {
        try
        {
            String[] parts = eventMessage.split(":");
            String  eventType = parts[0];
            String[] values = parts[1].split(",");
            
            switch(eventType)
            {
                case "KEY_PRESS":
                    int keyCodePress = Integer.parseInt(values[0]);
                    robot.keyPress(keyCodePress);
                    System.out.println("Key pressed: " + keyCodePress);
                    break;
                case "KEY_RELEASE":
                    int keyCodeRelease = Integer.parseInt(values[0]);
                    robot.keyRelease(keyCodeRelease);
                    System.out.println("Key released: " + keyCodeRelease);
                    break;
                case "MOUSE_PRESS":
                    int mouseButtonPress = Integer.parseInt(values[0]);
                    handleMousePress(mouseButtonPress);
                    System.out.println("Mouse button pressed: " + mouseButtonPress);
                    break;

                case "MOUSE_RELEASE":
                    int mouseButtonRelease = Integer.parseInt(values[0]);
                    handleMouseRelease(mouseButtonRelease);
                    System.out.println("Mouse button released: " + mouseButtonRelease);
                    break;

                case "MOUSE_MOVE":
                    int mouseX = Integer.parseInt(values[0]);
                    int mouseY = Integer.parseInt(values[1]);
                    robot.mouseMove(mouseX, mouseY);
                    System.out.println("Mouse moved to: " + mouseX + "," + mouseY);
                    break;

                default:
                    System.out.println("Unknown event type: " + eventType);
                    break;
            }
            
        }catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }
    }
    
    // Xử lý sự kiện nhấn chuột
    private void handleMousePress(int button) {
        switch (button) {
            case 1:
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);  // Chuột trái
                break;
            case 2:
                robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);  // Chuột giữa
                break;
            case 3:
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);  // Chuột phải
                break;
            default:
                System.out.println("Unknown mouse button: " + button);
        }
    }
        
    // Xử lý sự kiện thả chuột
    private void handleMouseRelease(int button) {
        switch (button) {
            case 1:
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);  // Chuột trái
                break;
            case 2:
                robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);  // Chuột giữa
                break;
            case 3:
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);  // Chuột phải
                break;
            default:
                System.out.println("Unknown mouse button: " + button);
        }
    }
    
    // Phương thức để dừng luồng nhận sự kiện
    public void stop() {
        continueLoop = false;
        close();
    }

    // Đóng kết nối
    private void close() {
        try {
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
