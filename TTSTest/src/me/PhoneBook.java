package me;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.SynthesizerProperties;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PhoneBook extends JFrame {
	private JLabel label1;

	private JLabel label2;

	private JComboBox cboName;

	private JButton btnPhoneButton;

	// 声明实例变量speechSynthesizer
	private Synthesizer speechSynthesizer;

	private String[] namesArray = { "John", "Jennifer", "Howard" };

	private String[] numbersArray = { "555-98787", "555-1234", "555-456" };

	public PhoneBook() {
		try {

			// create a Synthesizer

			SynthesizerModeDesc desc = new SynthesizerModeDesc(
					"Unlimited domain FreeTTS Speech Synthesizer from Sun Labs",
					null, Locale.US, Boolean.FALSE, null);
			speechSynthesizer = Central.createSynthesizer(desc);

			// created successfully
			if (speechSynthesizer != null) {
				// prepare synthesizer to speek
				speechSynthesizer.allocate();
				speechSynthesizer.resume();

				// get synthesizer's properties
				SynthesizerProperties properties = speechSynthesizer
						.getSynthesizerProperties();
				// set up speeking rate 朗读语速
				properties.setSpeakingRate(100f);
			} else {

				System.err.println("Systhesizer created failed!");
				System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		createUserInterface();
	}

	private void createUserInterface() {
		Container contentPane = this.getContentPane();
		contentPane.setLayout(null);

		label1 = new JLabel("select a name:");
		label1.setBounds(16, 8, 264, 23);
		contentPane.add(label1);

		label2 = new JLabel("read the phonenumber of the man");
		label2.setBounds(16, 35, 264, 23);
		contentPane.add(label2);

		cboName = new JComboBox();
		cboName.setBounds(50, 65, 150, 23);
		cboName.addItem(namesArray[0]);
		cboName.addItem(namesArray[1]);
		cboName.addItem(namesArray[2]);
		contentPane.add(cboName);

		btnPhoneButton = new JButton("phoneNumber");
		btnPhoneButton.setBounds(50, 100, 150, 23);
		contentPane.add(btnPhoneButton);
		btnPhoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				getPhoneNumber(e);
			}
		});

		setTitle("Phone BOok");
		setSize(300, 165);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				frameWindowClosing(evt);
			}
		});
	}

	public void frameWindowClosing(WindowEvent evt) {
		try {
			speechSynthesizer.deallocate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	public void getPhoneNumber(ActionEvent evt) {
		int i = cboName.getSelectedIndex();
		String read = namesArray[i] + "'s phone Number is " + numbersArray[i];

		speechSynthesizer.speakPlainText(read, null);
	}

	public static void main(String[] args) {
		new PhoneBook();
	}
}
