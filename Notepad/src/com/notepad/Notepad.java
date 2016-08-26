package com.notepad;

import java.awt.BorderLayout;
import javax.swing.Action;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.print.PrinterException;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;

import com.notepad.io.FileIO;

public class Notepad extends JFrame implements ActionListener, ItemListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//panel to add components
	private JPanel panel;
	
	//menu bar
	private JMenuBar jMenuBar;
	
	//menus
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu formatMenu;
	private JMenu viewMenu;
	private JMenu helpMenu;
	
	//menu items
	//file menu items
	private JMenuItem newFileMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenuItem saveFileMenuItem;
	private JMenuItem saveAsFileMenuItem;
	private JMenuItem printFileMenuItem;
	private JMenuItem exitMenuItem;
	
	//edit menu item
	private JMenuItem undoEditMenuItem;
	private Action cutAction;
	private Action copyAction;
	private Action pasteAction;
	private JMenuItem selectAllEditMenuItem;
	
	//format menu item
	private JCheckBoxMenuItem wordWrapMenuItem;
	private JMenuItem fontMenuItem;
	
	//view menu item
	private JCheckBoxMenuItem showStatusMenuItem;
	
	//help menu item
	private JMenuItem aboutMenuItem;
	
	//icons
	private ImageIcon newFileIcon;
	private ImageIcon openFileIcon;
	private ImageIcon saveFileIcon;
	private ImageIcon saveAsFileIcon;
	private ImageIcon printIcon;
	private ImageIcon exitIcon;
	private ImageIcon iconImage;
	
	//text area
	private JTextArea jTextArea;
	
	//scroll pane
	private JScrollPane scrollPane;
	
	//status bar
	private JLabel statusbar;
	
	//File chooser
	private JFileChooser fileChooser;
	
	//extension variable
	private String extension;
	private File currentFile;
	private boolean fileChanged;
	UndoManager undoManager;

	public Notepad() {
		//loading all the required resources - images and icons
		loadResources();
		initUI(); //initializing UI
		fileChanged = false;
		currentFile = null;
		
		//undo manager
		undoManager = new UndoManager();
		//adding undo functionality to text area	
		jTextArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
			
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				// TODO Auto-generated method stub
				undoManager.addEdit(e.getEdit());
			}
		});
		this.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				exitProgram();
		    }
		});
	}
	
	//setter and getters
	public boolean isFileChanged() {
		return fileChanged;
	}



	public void setFileChanged(boolean fileChanged) {
		this.fileChanged = fileChanged;
	}

	/*
	 * Function to initialize the UI of app
	 */
	private void initUI() {
		
		//drawing window
		this.setTitle("Notepad - Untitled");
		this.setSize(600, 700);
		this.setLocationRelativeTo(null); //to set the starting position of app as center of screen
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//adding panel to frame
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.add(panel);
		
		//creating menus
		createMenuBar();
		
		//initialize text area
		jTextArea = new JTextArea();
		jTextArea.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		jTextArea.setFont(new Font("Monaco", Font.PLAIN, 12));
		jTextArea.addKeyListener(this);
		
		
		//adding scrollers
		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().add(jTextArea); //adding textarea to scroll pane
		panel.add(scrollPane); //adding scroller to panel
		
		
		statusbar = new JLabel(" Statusbar");
        statusbar.setPreferredSize(new Dimension(-1, 22));
        statusbar.setBorder(LineBorder.createGrayLineBorder());
        this.add(statusbar, BorderLayout.SOUTH);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	private void createMenuBar() {
		//creating menu bar
		jMenuBar = new JMenuBar();
		
		//creating the "FILE" menu
		fileMenu = new JMenu("File");
		//adding mnemonic for menu
		fileMenu.setMnemonic(KeyEvent.VK_F);
		//adding menu to menubar
		jMenuBar.add(fileMenu);
		
		//EDIT menu
		editMenu = new JMenu("Edit");
		jMenuBar.add(editMenu);
		
		//FORMAT menu
		formatMenu = new JMenu("Format");
		jMenuBar.add(formatMenu);
		
		//VIEW menu
		viewMenu = new JMenu("View");
		jMenuBar.add(viewMenu);
		
		//HELP Menu
		helpMenu = new JMenu("Help");
		jMenuBar.add(helpMenu);
		
		//new file
		newFileMenuItem = new JMenuItem("New", newFileIcon); //adding menu items to file menu
		newFileMenuItem.addActionListener(this); //adding a action listener to respond to clicks
		fileMenu.add(newFileMenuItem); //adding item to menu
		
		//open file
		openFileMenuItem = new JMenuItem("Open File...", openFileIcon);
		openFileMenuItem.addActionListener(this);
		fileMenu.add(openFileMenuItem);
		
		//save file
		saveFileMenuItem = new JMenuItem("Save", saveFileIcon);
		saveFileMenuItem.addActionListener(this);
		fileMenu.add(saveFileMenuItem);
		
		//save as file
		saveAsFileMenuItem = new JMenuItem("Save as..", saveAsFileIcon);
		saveAsFileMenuItem.addActionListener(this);
		fileMenu.add(saveAsFileMenuItem);
		
		fileMenu.addSeparator(); //adding a separator
		
		//print
		printFileMenuItem = new JMenuItem("Print", printIcon);
		printFileMenuItem.addActionListener(this);
		fileMenu.add(printFileMenuItem);
		
		fileMenu.addSeparator(); //adding a separator
		
		//exit
		exitMenuItem = new JMenuItem("Exit", exitIcon);
		exitMenuItem.addActionListener(this);
		fileMenu.add(exitMenuItem);
		
		//undo
		undoEditMenuItem = new JMenuItem("Undo");
		undoEditMenuItem.addActionListener(this);
		editMenu.add(undoEditMenuItem);
		
		editMenu.addSeparator(); //adding separator
		
		//cut
		cutAction = new DefaultEditorKit.CutAction();
		cutAction.putValue(Action.NAME, "Cut");
		editMenu.add(cutAction);
		
		//copy
		copyAction = new DefaultEditorKit.CopyAction();
		copyAction.putValue(Action.NAME, "Copy");
		editMenu.add(copyAction);
		
		//paste
		pasteAction = new DefaultEditorKit.PasteAction();
		pasteAction.putValue(Action.NAME, "Paste");
		editMenu.add(pasteAction);
				
		editMenu.addSeparator(); //adding separator
		
		//select all
		selectAllEditMenuItem = new JMenuItem("Select All");
		selectAllEditMenuItem.addActionListener(this);
		editMenu.add(selectAllEditMenuItem);
		
		//word wrap
		wordWrapMenuItem = new JCheckBoxMenuItem("Word Wrap");
		wordWrapMenuItem.setSelected(false);
		wordWrapMenuItem.addItemListener(this);
		formatMenu.add(wordWrapMenuItem);
		
		//font
		fontMenuItem = new JMenuItem("Font...");
		fontMenuItem.addActionListener(this);
		formatMenu.add(fontMenuItem);
		
		//show status bar
		showStatusMenuItem = new JCheckBoxMenuItem("Show Statusbar");
		showStatusMenuItem.setSelected(true);
		showStatusMenuItem.addItemListener(this);
		viewMenu.add(showStatusMenuItem);
		
		//about
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(this);
		helpMenu.add(aboutMenuItem);
		
		//adding menu bar to frame
		this.setJMenuBar(jMenuBar);
	}
	
	private void loadResources() {
		newFileIcon = new ImageIcon("./Icons/new-file.png");
		openFileIcon = new ImageIcon("./Icons/folder.png");
		saveFileIcon = new ImageIcon("./Icons/save.png");
		saveAsFileIcon = new ImageIcon("./Icons/saveas.png");
		printIcon = new ImageIcon("./Icons/printer.png");
		exitIcon = new ImageIcon("./Icons/exit.png");
		iconImage = new ImageIcon("./Icons/notepad-icon.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.exitMenuItem) {
			exitProgram();
		}
		else if(e.getSource() == this.openFileMenuItem) {
			// Opening file
			if(openFile()) {
				this.setFileChanged(false);
			}
		}
		else if(e.getSource() == this.saveFileMenuItem) {
			//Saving file
			if(saveFile()) {
				this.setFileChanged(false);
			}
		}
		else if(e.getSource() == this.newFileMenuItem) {
			//new file
			newFile();
		}
		else if(e.getSource() == this.saveAsFileMenuItem) {
			//Save as file
			File tempFile = currentFile;
			currentFile = null;
			if(saveFile()) {
				this.setFileChanged(false);
			}
			else {
				currentFile = tempFile;
			}
		}
		else if(e.getSource() == this.selectAllEditMenuItem) {
			//select all
			jTextArea.selectAll();
		}
		else if(e.getSource() == this.undoEditMenuItem) {
			//undo
			undoManager.undo();
		}
		else if(e.getSource() == this.printFileMenuItem) {
			try {
				jTextArea.print();
			} catch (PrinterException e1) {
				// TODO Auto-generated catch block
				System.out.println("ERROR: Unable to print document.");
			}
		}
		else if(e.getSource() == this.fontMenuItem) {
			showFontChooser();
		}
		else if(e.getSource() == this.aboutMenuItem) {
			String message = "Notepad in Java\nDeveloped by Sunil Nair\nversion 1.0";
			JOptionPane.showMessageDialog(this, message, "Notepad", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.wordWrapMenuItem) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				jTextArea.setLineWrap(true);
				jTextArea.setWrapStyleWord(true);
			}
			else {
				jTextArea.setLineWrap(false);
				jTextArea.setWrapStyleWord(false);
			}
		}
		else if(e.getSource() == this.showStatusMenuItem) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				statusbar.setText("Checked");
			}
			else {
				statusbar.setText("UnChecked");
			}
		}
	}
	
	/*
	 * Function to get filename from file chooser
	 * 
	 */
	private File openFileChooser() {
		fileChooser = new JFileChooser();
		
		//creating file format filters
		FileFilter javaFilter = new FileNameExtensionFilter("Java Files(.java)", "java");
		FileFilter textFilter = new FileNameExtensionFilter("Text Files(.txt)", "txt");
		
		//adding filters to file chooser
		fileChooser.addChoosableFileFilter(javaFilter);
		fileChooser.addChoosableFileFilter(textFilter);
		
		int ret = fileChooser.showOpenDialog(this);
		
		if(ret == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			return file;
		}
		
		return null;
	}
	
	/*
	 * Function to save file
	 * 
	 */
	private File saveFileChooser() {
		
		fileChooser = new JFileChooser();
		
		//creating file format filters
		FileFilter javaFilter = new FileNameExtensionFilter("Java Files(.java)", "java");
		FileFilter textFilter = new FileNameExtensionFilter("Text Files(.txt)", "txt");
				
		//adding filters to file chooser
		fileChooser.addChoosableFileFilter(javaFilter);
		fileChooser.addChoosableFileFilter(textFilter);
				
		int ret = fileChooser.showSaveDialog(this);
				
		if(ret == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			setExtension(fileChooser);
			return file;
		}
				
		return null;
		
	}
	
	/*
	 * Function to get the file extension
	 * 
	 */
	private void setExtension(JFileChooser fileChooser) {
		
		if(fileChooser.getFileFilter().getDescription().equals("Text Files(.txt)")) {
			extension = ".txt";
		}
		else if(fileChooser.getFileFilter().getDescription().equals("Java Files(.java)")) {
			extension = ".java";
		}
		else {
			extension = "";
		}
	}
	
	/*
	 *  Function for new document
	 */
	private void newFile() {
		if(isFileChanged()) {
			int ret = JOptionPane.showConfirmDialog(panel, "Do you want to save changes?", 
						"Notepad", JOptionPane.YES_NO_CANCEL_OPTION);
			if(ret == JOptionPane.YES_OPTION) {
				if(saveFile()) {
					this.setFileChanged(false);
					this.jTextArea.setText("");
					this.setTitle("Notepad - Untitled");
					currentFile = null;
				}
			}
			else if(ret == JOptionPane.NO_OPTION) {
				this.setFileChanged(false);
				this.jTextArea.setText("");
				this.setTitle("Notepad - Untitled");
				currentFile = null;
			}
		}
		else {
			this.jTextArea.setText("");
			this.setTitle("Notepad - Untitled");
			currentFile = null;
		}
	}
	
	/*
	 * 
	 * Function for save file menu item
	 */
	private boolean saveFile() {
		if(currentFile == null) {
			File file = saveFileChooser();
			if(file != null) {
				FileIO io = new FileIO();
				io.saveFile(file, extension, jTextArea.getText());
				this.setTitle("Notepad - " + file.getName());
				currentFile = file;
				return true;
			}
		}
		else {
			FileIO io = new FileIO();
			io.saveFile(currentFile, extension, jTextArea.getText());
			this.setTitle("Notepad - " + currentFile.getName());
			return true;
		}
		return false;
	}
	
	/*
	 * 
	 * Function to open file
	 */
	private boolean openFile() {
		if(isFileChanged()) {
			int ret = JOptionPane.showConfirmDialog(panel, "Do you want to save changes?", 
					"Notepad", JOptionPane.YES_NO_CANCEL_OPTION);
			if(ret == JOptionPane.YES_OPTION) {
				if(!saveFile()) {
					return false;
				}
			}
			else if(ret == JOptionPane.NO_OPTION) {
				//do nothing
			}
			else if(ret == JOptionPane.CANCEL_OPTION) {
				return false;
			}
		}
		File file = openFileChooser();
		if(file != null) {
			FileIO io = new FileIO();
			String content = io.openFile(file);
			jTextArea.setText(content);
			this.setTitle("Notepad - " + file.getName());
			currentFile = file;
			return true;
		}

		return false;
	}
	
	/*
	 * Function to display font chooser
	 */
	private void showFontChooser() {
		JFontChooser fontChooser = new JFontChooser();
		int result = fontChooser.showDialog(this);
		if(result == JFontChooser.OK_OPTION) {
			Font font = fontChooser.getSelectedFont();
			jTextArea.setFont(font);
		}
	}
	
	/*
	 * Function to exit the program
	 */
	private void exitProgram() {
		if(fileChanged) {
			//int returnValue = JOptionPane.showConfirmDialog(n, "There are some unsaved changes!\nDo you want to save the changes?", "Notepad", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	        String message = "There are unsaved changes!\nDo you want to save the document ?";
			int returnValue = JOptionPane.showOptionDialog(this, message, "Notepad", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(returnValue == JOptionPane.YES_OPTION) {
	        	if(saveFile()) {
	        		System.exit(0);
	        	}
	        }
	        else if(returnValue == JOptionPane.NO_OPTION) {
	        	System.exit(0);
	        }
		}
		else {
			System.exit(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		//file has been modified
		this.setFileChanged(true);
		if(currentFile == null) {
			this.setTitle("Notepad - *Untitled");
		}
		else {
			this.setTitle("Notepad - *" + currentFile.getName());
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}