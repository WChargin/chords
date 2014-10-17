package chords;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class ChordSearchGUI {

	private final class SearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			final int a = (int) spnA.getValue();
			final int b = (int) spnB.getValue();
			final ChordSearch.Result result = ChordSearch.search(a, b);
			final String message;
			if (result.isRoot) {
				message = String.format("Tree %d; root", result.tree);
			} else {
				message = String.format("Tree %d at position (%d, %d)",
						result.tree, result.position.a, result.position.b);
			}
			lblResultValue.setText(message);
		}

	}

	private JFrame frmChordSearch;
	private JSpinner spnA;
	private JSpinner spnB;
	private JLabel lblResultValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		optimizeSwing();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChordSearchGUI window = new ChordSearchGUI();
					window.frmChordSearch.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void optimizeSwing() {
		String systemLaf = UIManager.getSystemLookAndFeelClassName();
		try {
			if (systemLaf.toLowerCase().contains("metal")) {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
			}
			UIManager.setLookAndFeel(systemLaf);
		} catch (Exception e) {
			UIManager.put("swing.boldMetal", Boolean.FALSE);
		}

		if (systemLaf.toLowerCase().contains("windows")) {
			// Windows LAF uses monospaced fonts for JTextArea.
			// Let's fix that.
			Font defaultTextAreaFont = UIManager.getFont("TextArea.font");
			UIManager.put(
					"TextArea.font",
					new FontUIResource(Font.SANS_SERIF, defaultTextAreaFont
							.getStyle(), defaultTextAreaFont.getSize()));

			// Again, Windows messes up combo box display with an "XPFillBorder"
			if (UIManager.getBorder("ComboBox.border").getClass().getName()
					.contains("XPFillBorder")) { //$NON-NLS-1$
				UIManager.put("ComboBox.border", new EmptyBorder(0, 0, 0, 0));
			}
		}
	}

	/**
	 * Create the application.
	 */
	public ChordSearchGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChordSearch = new JFrame();
		frmChordSearch.setTitle("Chord Search");
		frmChordSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel pnlContent = new JPanel();
		frmChordSearch.setContentPane(pnlContent);
		pnlContent.setBorder(new EmptyBorder(10, 10, 10, 10));

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };

		pnlContent.setLayout(gridBagLayout);

		JLabel lblA = new JLabel("a");
		lblA.setFont(lblA.getFont().deriveFont(
				lblA.getFont().getStyle() | Font.BOLD));
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.anchor = GridBagConstraints.EAST;
		gbc_lblA.gridx = 0;
		gbc_lblA.gridy = 0;
		pnlContent.add(lblA, gbc_lblA);

		spnA = new JSpinner();
		spnA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				final int a = (int) spnA.getValue();
				final int b = (int) spnB.getValue();
				spnB.setModel(new SpinnerNumberModel(Math.max(a, b), a, null, 1));
			}
		});
		spnA.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1),
				null, new Integer(1)));
		GridBagConstraints gbc_spnA = new GridBagConstraints();
		gbc_spnA.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnA.anchor = GridBagConstraints.WEST;
		gbc_spnA.insets = new Insets(0, 0, 5, 0);
		gbc_spnA.gridx = 1;
		gbc_spnA.gridy = 0;
		pnlContent.add(spnA, gbc_spnA);

		JLabel lblB = new JLabel("b");
		lblB.setFont(lblB.getFont().deriveFont(
				lblB.getFont().getStyle() | Font.BOLD));
		GridBagConstraints gbc_lblB = new GridBagConstraints();
		gbc_lblB.anchor = GridBagConstraints.EAST;
		gbc_lblB.insets = new Insets(0, 0, 5, 5);
		gbc_lblB.gridx = 0;
		gbc_lblB.gridy = 1;
		pnlContent.add(lblB, gbc_lblB);

		spnB = new JSpinner();
		spnB.setModel(new SpinnerNumberModel(new Integer(2), new Integer(1),
				null, new Integer(1)));
		GridBagConstraints gbc_spnB = new GridBagConstraints();
		gbc_spnB.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnB.anchor = GridBagConstraints.WEST;
		gbc_spnB.insets = new Insets(0, 0, 5, 0);
		gbc_spnB.gridx = 1;
		gbc_spnB.gridy = 1;
		pnlContent.add(spnB, gbc_spnB);

		JButton btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.gridwidth = 2;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 2;
		pnlContent.add(btnSearch, gbc_btnSearch);
		btnSearch.addActionListener(new SearchListener());

		JLabel lblResult = new JLabel("Result");
		lblResult.setFont(lblResult.getFont().deriveFont(
				lblResult.getFont().getStyle() | Font.BOLD));
		GridBagConstraints gbc_lblResult = new GridBagConstraints();
		gbc_lblResult.anchor = GridBagConstraints.EAST;
		gbc_lblResult.insets = new Insets(0, 0, 0, 5);
		gbc_lblResult.gridx = 0;
		gbc_lblResult.gridy = 3;
		pnlContent.add(lblResult, gbc_lblResult);

		lblResultValue = new JLabel("Not yet calculated");
		GridBagConstraints gbc_lblResultValue = new GridBagConstraints();
		gbc_lblResultValue.anchor = GridBagConstraints.WEST;
		gbc_lblResultValue.gridx = 1;
		gbc_lblResultValue.gridy = 3;
		pnlContent.add(lblResultValue, gbc_lblResultValue);

		frmChordSearch.pack();
		frmChordSearch.setSize(Math.max(350, frmChordSearch.getWidth()), frmChordSearch.getHeight());
	}
}
