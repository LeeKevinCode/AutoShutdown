package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

@SuppressWarnings("serial")
class MyFrame extends JFrame {
	
	private MyThread timer = null;

	private JButton btnSchedule = new JButton("Schedule");
	private JButton btnReset = new JButton("Reset");

	private JTextField txtA = new JTextField();
	private JTextField txtB = new JTextField();
	private JTextField txtC = new JTextField();

	private JLabel lblA = new JLabel("Hour :");
	private JLabel lblB = new JLabel("Minute :");
	private JLabel lblC = new JLabel("Second :");

	private final JLabel lbltimer = new JLabel();

	public MyFrame() {
		setTitle("Penjumlahan");
		setSize(400, 200);
		setLocation(new Point(300, 200));
		setLayout(null);
		setResizable(false);

		initComponent();
		initEvent();
	}

	private void initComponent() {
		btnSchedule.setBounds(250, 130, 120, 25);
		btnReset.setBounds(250, 100, 120, 25);

		txtA.setBounds(100, 10, 100, 20);
		txtB.setBounds(100, 40, 100, 20);
		txtC.setBounds(100, 70, 100, 20);
		
		txtA.setText("0");
		txtB.setText("0");
		txtC.setText("0");

		lblA.setBounds(20, 10, 100, 20);
		lblB.setBounds(20, 40, 100, 20);
		lblC.setBounds(20, 70, 100, 20);

		lbltimer.setBounds(20, 120, 200, 20);

		add(btnSchedule);
		add(btnReset);

		add(lblA);
		add(lblB);
		add(lblC);

		add(lbltimer);

		add(txtA);
		add(txtB);
		add(txtC);
	}

	private void initEvent() {

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});

		btnSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnScheduleClick(e);
			}
		});

		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnResetClick(e);
			}
		});
	}

	private void btnScheduleClick(ActionEvent evt) {
		
		try {
			final Integer hour = Integer.parseInt(txtA.getText());
			final Integer minute = Integer.parseInt(txtB.getText());
			final Integer second = Integer.parseInt(txtC.getText());
			
			if (timer != null){
				timer.terminate();
			}
			timer = new MyThread(){
				@Override
				public void run() {
					int total = hour * 3600 + minute * 60 + second;
					int currentTotal = total;
					int i = 0;
					while(running && (i < total)){
						try {
							int h = currentTotal / 3600;
							int m = (currentTotal - h * 3600) / 60;
							int s = currentTotal - h * 3600 - m * 60;
							Thread.sleep(1000);
							currentTotal--;
							lbltimer.setText("Shut donw at " + h + " : " + m + " : " + s);
							lbltimer.setForeground(Color.red);
							repaint();
							i++;
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					if(running){
						Runtime runtime = Runtime.getRuntime();
						try {
							Process proc = runtime.exec("shutdown -s -t 0");
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.exit(0);
					}
				}
			};
			timer.start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void btnResetClick(ActionEvent evt) {
		txtA.setText("0");
		txtB.setText("0");
		txtC.setText("0");
		
		timer.terminate();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lbltimer.setText("");
	}
}
