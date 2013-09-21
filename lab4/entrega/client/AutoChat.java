package lab4.entrega.client;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import lab4.entrega.Constants;

public class AutoChat extends Applet
{
	private static final long serialVersionUID = 4951315404688831092L;

	private final Panel p = new Panel();

	private final Button bt = new Button("Send");

	private final TextField send = new TextField();

	private final TextArea receive = new TextArea("", 1, 40,
			TextArea.SCROLLBARS_VERTICAL_ONLY);

	private Client c;

	@Override
	public void init()
	{
		setLayout(new BorderLayout());
		add(p, BorderLayout.SOUTH);
		add(receive, BorderLayout.CENTER);

		p.setLayout(new BorderLayout());

		p.add(send, BorderLayout.CENTER);
		p.add(bt, BorderLayout.LINE_END);

		receive.setEditable(false);

		send.addKeyListener(new KeyListener());

		bt.addActionListener(new ButtonListener());
	}

	@Override
	public void start()
	{
		try
		{
			c = new Client(Constants.IP, Constants.PORT);
		}
		catch (UnknownHostException e)
		{
			receiveAppendText("Servidor desconocido");
			sendClear();
			e.printStackTrace();
			return;
		}
		catch (IOException e)
		{
			receiveAppendText("Error al crear la conexion");
			sendClear();
			e.printStackTrace();
			return;
		}

		c.setListener(new ClientListener());

	}

	@Override
	public void stop()
	{
		try
		{
			c.stop();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void destroy()
	{
		try
		{
			c.stop();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String sendgetText()
	{
		return send.getText();
	}

	class KeyListener extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent t)
		{
			if (t.getKeyCode() == KeyEvent.VK_ENTER)
			{
				try
				{
					c.processMessage(sendgetText());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			String s = ae.getActionCommand();
			if (s.equals("Send"))
			{
				try
				{
					c.processMessage(sendgetText());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private void receiveAppendText(String sms)
	{
		receive.append(sms);
	}

	private void sendClear()
	{
		send.setText("");
	}

	private void receiveDisable()
	{
		receive.setEnabled(false);
	}

	private void sendDisable()
	{
		send.setEnabled(false);
	}

	class ClientListener implements Client.ClientListener
	{
		@Override
		public void close()
		{
			receiveDisable();
			sendDisable();
		}

		@Override
		public void receiveSetTextApplet(String sms)
		{
			receiveAppendText(sms);
		}

		@Override
		public void sendClearApplet()
		{
			sendClear();
		}
	}

}
