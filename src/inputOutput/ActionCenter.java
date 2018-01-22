package inputOutput;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ActionCenter extends Thread {
	
	private JFrame jf;
	private JButton closeButton;
	private JButton endButton;
	private JPanel jp;
	
	public ActionCenter() {
		jf = new JFrame();
		jp = new JPanel();
		//addStopButton();
	}
	
	@Override
	public void run() {
		addStopButton();
	}
	
	private void addStopButton() {
		jf = new JFrame();
		jf.setTitle("Stop Button");
		jf.setSize(256+16, 64+40);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);ImageIcon img = new ImageIcon("icon.png");
		jf.setIconImage(img.getImage());
		jf.setBackground(Color.RED);
		jf.setAlwaysOnTop(true);
		jf.setResizable(false);
		
		closeButton = new JButton("End");
		closeButton.addActionListener(closeButtonActionListener());
		closeButton.setPreferredSize(new Dimension(64, 64));
		closeButton.setOpaque(true);
		closeButton.setBackground(Color.GREEN);
		closeButton.setForeground(Color.BLACK);
		
		endButton = new JButton("Halt");
		endButton.addActionListener(endButtonActionListener());
		endButton.setPreferredSize(new Dimension(64, 64));
		endButton.setOpaque(true);
		endButton.setBackground(Color.RED);
		endButton.setForeground(Color.WHITE);
		
		jp.setBackground(Color.BLACK);
		jp.add(closeButton);
		jp.add(endButton);
		//jp.add(pauseButton);
		//jp.add(resumeButton);
		jf.add(jp);
		jf.setVisible(true);
	}
	
	private ActionListener closeButtonActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		};
	}
	
	private ActionListener endButtonActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Runtime.getRuntime().halt(MAX_PRIORITY);
			}
		};
	}
}

/*

	// In variable decloration
		private JButton pauseButton;
		private JButton resumeButton;
		private Thread thr;
		private boolean pause;
	
	
	// in constructor
	// Add (Thread main) as paramater
		pause = false;
		thr = main;
		
	// in make button method
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(pauseButtonActionListener());
		pauseButton.setPreferredSize(new Dimension(64, 64));
		pauseButton.setOpaque(true);
		pauseButton.setBackground(Color.YELLOW);
		pauseButton.setForeground(Color.BLACK);
		
		resumeButton = new JButton("Resume");
		resumeButton.addActionListener(resumeButtonActionListener());
		resumeButton.setPreferredSize(new Dimension(64, 64));
		resumeButton.setOpaque(true);
		resumeButton.setBackground(Color.GREEN);
		resumeButton.setForeground(Color.BLACK);
		resumeButton.setEnabled(false);
		
	// As methods
		private ActionListener resumeButtonActionListener() {
				return new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pauseButton.setEnabled(true);
						resumeButton.setEnabled(false);
						pause = false;
						notifyAll();
					}
				};
			}
			private ActionListener pauseButtonActionListener() {
				return new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pauseButton.setEnabled(false);
						resumeButton.setEnabled(true);
						pause = true;
						Thread t = new Thread() {
							public void run() {
								synchronized (thr) {
									while(pause) {
										try {
										thr.wait();
										} catch (InterruptedException e1) {}
									}
								}
							}
						};
						t.start();
					}
				};
			}
	
*/
