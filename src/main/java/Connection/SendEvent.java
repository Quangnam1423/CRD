/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author suong
 */
public class SendEvent implements Runnable , KeyListener , MouseListener , MouseMotionListener{
    private Socket socket;
    private OutputStream outputStream;
    private boolean continueLoop;
    
    public SendEvent(Socket s) throws IOException
    {
        this.socket = s;
        this.outputStream = s.getOutputStream();
        this.continueLoop = true;
    }
    
    
    // bắt đầu luồng chạy của Thread này
    @Override
    public void run()
    {
        try
        {
            while(continueLoop)
            {
                
                
                Thread.sleep(100);
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    // Bắt sự kiện nhấn phím
    @Override
    public void keyPressed(KeyEvent e)
    {
        sender("KEY_PRESS" , e.getKeyCode());
    }

    // Bắt sự kiện nhấn chuột
    @Override
    public void mousePressed(MouseEvent e) {
        sender("MOUSE_PRESS", e.getButton());
    }

    // Bắt sự kiện thả chuột
    @Override
    public void mouseReleased(MouseEvent e) {
        sender("MOUSE_RELEASE", e.getButton());
    }

    // Bắt sự kiện di chuyển chuột
    @Override
    public void mouseMoved(MouseEvent e) {
        sender("MOUSE_MOVE", e.getX(), e.getY());
    }
    
    private void sender(String typeEvent , int... values)
    {
        try
        {
            StringBuilder eventBuilder = new StringBuilder(typeEvent + ":");
            
            for (int value : values)
            {
                eventBuilder.append(value).append(":");
            }
            
            String eventMessage = eventBuilder.toString();
            if (eventMessage.endsWith(","))
            {
                eventMessage = eventMessage.substring(0 , eventMessage.length() - 1);
            }
            eventMessage += "/n";
            this.outputStream.write(eventMessage.getBytes());
            
            this.outputStream.flush();
            
            
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
