package com.sdust.im.server.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sdust.im.server.ServerListen;

public class ServerFrame extends JFrame implements ActionListener{
	private JPanel jp;
	private JButton jbStart;
	private JButton jbClose;
	private ServerListen ser;
	public ServerFrame(){
		jp = new JPanel();
		jbClose = new JButton("关闭服务器");
		jbStart = new JButton("启动服务器");
		jbStart.addActionListener(this);
		jbClose.addActionListener(this);
	    jp.add(jbStart);
	    jp.add(jbClose);
		this.add(jp);
		this.setSize(500,500);
		this.setTitle("QQ服务器");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == jbStart){
			ser = new ServerListen();
			ser.begin();
		}
//		else
//			ser.close();
			
			
	}
    public static void main(String args[]){
    	new ServerFrame();
    	
    }
}
