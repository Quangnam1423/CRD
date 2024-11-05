package ClientSide;

/*
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JPanel;
*/


import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author suong
 */
public class SendEvents implements KeyListener , MouseListener , MouseMotionListener{
	private Socket socket;
	private OutputStream outputStream;
	private boolean continueLoop;
	double width , height;
	private JPanel cPanel = null;

	public SendEvents(Socket s , JPanel panel, double width , double height) throws IOException
	{
		this.cPanel = panel;
		this.socket = s;
		this.outputStream = s.getOutputStream();
		this.continueLoop = true;
		this.width = width;
		this.height = height;

		cPanel.addKeyListener(this);
		cPanel.addMouseListener(this);
		cPanel.addMouseMotionListener(this);
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
		double xScale = (double)width/cPanel.getWidth();
		double yScale = (double)height/cPanel.getHeight();
		sender("MOUSE_MOVE", (int)(e.getX()*xScale), (int)(e.getY()*yScale));
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
/*
class SendEvents implements KeyListener, MouseMotionListener, MouseListener{
	private Socket cSocket = null;
	private JPanel cPanel = null;
	private PrintWriter writer = null;
	String width = "", height = "";
	double w;
	double h;

	SendEvents(Socket s, JPanel p, String width, String height){
		cSocket = s;
		cPanel = p;
		this.width = width;
		this.height = height;
		w = Double.valueOf(width.trim()).doubleValue();
		h = Double.valueOf(width.trim()).doubleValue();

		//Associate event listeners to the panel

		cPanel.addKeyListener(this);
		cPanel.addMouseListener(this);
		cPanel.addMouseMotionListener(this);

		try{
			//Prepare PrintWriter which will be used to send commands to the client
			writer = new PrintWriter(cSocket.getOutputStream());
			} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public void mouseDragged(MouseEvent e){
	}

	public void mouseMoved(MouseEvent e){
		double xScale = (double)w/cPanel.getWidth();
		double yScale = (double)h/cPanel.getHeight();
		writer.println(Commands.MOVE_MOUSE.getAbbrev());
		writer.println((int)(e.getX()*xScale));
		writer.println((int)(e.getY()*yScale));
		writer.flush();
	}

	public void mouseClicked(MouseEvent e){
	}

	public void mousePressed(MouseEvent e){
		writer.println(Commands.PRESS_MOUSE.getAbbrev());
		int button = e.getButton();
		int xButton = 16;
		if(button==3){
			xButton = 4;
		}
		writer.println(xButton);
		writer.flush();
	}

	public void mouseReleased(MouseEvent e){
		writer.println(Commands.RELEASE_MOUSE.getAbbrev());
		int button = e.getButton();
		int xButton = 16;
		if(button==3){
			xButton = 4;
		}
		writer.println(xButton);
		writer.flush();
	}

	public void mouseEntered(MouseEvent e){
	}

	public void mouseExited(MouseEvent e){
	}

	public void keyTyped(KeyEvent e){
	}

	public void keyPressed(KeyEvent e){
		writer.println(Commands.PRESS_KEY.getAbbrev());
		writer.println(e.getKeyCode());
		writer.flush();
	}

	public void keyReleased(KeyEvent e){
		writer.println(Commands.RELEASE_KEY.getAbbrev());
		writer.println(e.getKeyCode());
		writer.flush();
	}
}


*/
