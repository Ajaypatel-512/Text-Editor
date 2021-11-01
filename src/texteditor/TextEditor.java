package texteditor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.undo.UndoManager;

public class TextEditor extends JFrame implements ActionListener
{
    private JPanel contentPane,sidebarpanel,menubarpanel,textpanel;
    private UndoManager manager;
    private int widthofarea,colornumber,selectioncolornumber;
    private boolean opensidebar,fontpanel,colorpanel,selectioncolorpanel;
    private JButton close,ok;
    public static JTextArea textarea;
    private JMenuBar menubar;
    private JMenu mfile,medit,mview,mfont,mcolor;
    private JMenuItem open, save,saveas,exit,copy,cut,paste,print,undo,redo,delete,find,replace,zoomin,zoomout,defaultzoom,selectall,font,defaultfont,color,defaultcolor,selectioncolor,newfile,newwindow;
    private JComboBox stylecombo,fontcombo;
    private JComboBox sizecombo;
    private JScrollPane sc;
    private String[] fontname,fontstyle,fontsize;
    private JButton[] b;
    private String saveddata,savedfilename,savedpath;
    private Border activecolorborder,border;
    private int[][] colors;
    private JLabel label,fontlabel;

    
    public TextEditor() {
        this.manager = new UndoManager();
        this.opensidebar = false;
        this.fontpanel = false;
        this.colorpanel = false;
        this.selectioncolorpanel = false;
        this.colornumber = 37;
        this.selectioncolornumber = 22;
        this.fontname = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        this.fontstyle = new String[] { "Plain", "Bold", "Italic", "Bold-Italic" };
        this.saveddata = "";
        this.colors = new int[][] { { 139, 0, 0 }, { 255, 0, 0 }, { 255, 9, 71 }, { 205, 92, 92 }, { 240, 128, 128 }, { 233, 150, 122 }, { 255, 140, 0 }, { 155, 165, 0 }, { 255, 215, 0 }, { 240, 230, 140 }, { 255, 255, 0 }, { 124, 252, 0 }, { 0, 100, 0 }, { 0, 128, 0 }, { 0, 250, 154 }, { 32, 178, 170 }, { 47, 79, 79 }, { 0, 139, 139 }, { 0, 255, 255 }, { 0, 206, 209 }, { 70, 130, 180 }, { 100, 149, 237 }, { 0, 191, 255 }, { 30, 144, 255 }, { 135, 206, 235 }, { 0, 0, 128 }, { 65, 105, 225 }, { 138, 43, 226 }, { 139, 0, 139 }, { 153, 50, 204 }, { 186, 85, 211 }, { 199, 21, 133 }, { 255, 105, 180 }, { 210, 105, 30 }, { 205, 133, 63 }, { 244, 164, 96 }, { 188, 143, 143 }, new int[3], { 105, 105, 105 }, { 128, 128, 128 }, { 169, 169, 169 }, { 192, 192, 192 }, { 211, 211, 211 }, { 245, 245, 245 }, { 155, 120, 30 }, { 220, 20, 60 }, { 184, 134, 11 }, { 189, 183, 107 } };
        this.savedfilename = "Untitled";
        this.savedpath = "null";
        final Timer timer = new Timer(1, this);
        timer.start();
        this.setTitle("Untitled");
        this.setDefaultCloseOperation(0);
        this.setSize(1000, 700);

        (this.contentPane = new JPanel()).setBackground(new Color(206, 207, 211));
        this.contentPane.setBorder(null);
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);

        (this.sidebarpanel = new JPanel()).setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
        this.sidebarpanel.setBackground(new Color(245, 255, 250));
        this.sidebarpanel.setBounds(this.getWidth() - 215, 72, 187, 578);
        this.contentPane.add(this.sidebarpanel);
        this.sidebarpanel.setLayout(null);

        (this.close = new JButton("close")).setForeground(Color.BLACK);
        this.close.setBackground(new Color(255, 255, 255));
        this.close.setFont(new Font("Sitka Small", 0, 18));
        this.close.setBounds(10, 534, 83, 33);
        this.sidebarpanel.add(this.close);
        this.close.addActionListener(this);

        (this.ok = new JButton("Ok")).setForeground(Color.BLACK);
        this.ok.setFont(new Font("Sitka Text", 0, 18));
        this.ok.setBackground(Color.WHITE);
        this.ok.setBounds(103, 534, 74, 33);
        this.ok.setFocusable(false);
        this.ok.addActionListener(this);
        this.sidebarpanel.add(this.ok);

        (this.fontlabel = new JLabel("Font")).setHorizontalAlignment(0);
        this.fontlabel.setFont(new Font("Square721 BT", 0, 21));
        this.fontlabel.setBounds(10, 11, 167, 98);
        this.sidebarpanel.add(this.fontlabel);
        this.fontlabel.setVisible(false);
        this.fontcombo = new JComboBox(fontname);
        fontcombo.setMaximumRowCount(10);
        this.fontcombo.setFont(new Font("Sitka Text", 0, 15));
        this.fontcombo.setForeground(Color.BLACK);
        this.fontcombo.setBackground(Color.WHITE);
        this.fontcombo.setFocusable(false);
        this.fontcombo.setBounds(10, 120, 167, 41);
        this.sidebarpanel.add(this.fontcombo);
        this.fontcombo.setVisible(false);
        this.fontcombo.setSelectedItem("Arial");
        this.stylecombo = new JComboBox(fontstyle);
		stylecombo.setFont(new Font("Sitka Text", 0, 15));
        this.stylecombo.setForeground(Color.BLACK);
        this.stylecombo.setBackground(Color.WHITE);
        this.stylecombo.setBounds(10, 182, 167, 41);
        this.stylecombo.setFocusable(false);
        this.stylecombo.setVisible(false);
        this.sidebarpanel.add(this.stylecombo);
        this.fontsize = new String[95];
        for (int i = 5; i <= 99; ++i) {
            this.fontsize[i - 5] = new String(new StringBuilder(String.valueOf(i)).toString());
        }
        this.sizecombo = new JComboBox(fontsize);
        sizecombo.setForeground(Color.BLACK);
        this.sizecombo.setFont(new Font("Sitka Text", 0, 15));
        this.sizecombo.setBackground(Color.WHITE);
        this.sizecombo.setFocusable(false);
        this.sizecombo.setBounds(10, 244, 167, 41);
        this.sizecombo.setSelectedItem("20");
        this.sizecombo.setVisible(false);
        this.sidebarpanel.add(this.sizecombo);
        this.activecolorborder = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        this.border = BorderFactory.createLineBorder(this.getBackground(), 0);
        this.b = new JButton[48];
        int row = 10;
        int colume = 10;
        int count = 0;
        for (int j = 1; j <= 12; ++j) {
            colume = 10;
            for (int k = 1; k <= 4; ++k) {
                (this.b[count] = new JButton()).setBounds(colume, row, 35, this.getHeight() / 20);
                this.b[count].setVisible(false);
                this.b[count].setFocusPainted(false);
                this.b[count].setForeground(Color.white);
                this.b[count].addActionListener(this);
                this.b[count].setFont(new Font("Segoe UI Semibold", 1, 25));
                this.sidebarpanel.add(this.b[count]);
                ++count;
                colume += 43;
            }
            row += this.getHeight() / 20 + 5;
        }
        for (int j = 0; j < this.colors.length; ++j) {
            this.b[j].setBackground(new Color(this.colors[j][0], this.colors[j][1], this.colors[j][2]));
        }

        (this.menubarpanel = new JPanel()).setBounds(10, 11, 964, 44);
        this.contentPane.add(this.menubarpanel);
        this.menubarpanel.setLayout(null);

        (this.menubar = new JMenuBar()).setForeground(Color.BLACK);
        this.menubar.setBackground(Color.WHITE);
        this.menubar.setBounds(0, 0, 964, 44);
        this.menubar.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
        this.menubarpanel.add(this.menubar);

        (this.mfile = new JMenu("File    ")).setForeground(Color.BLACK);
        this.mfile.setFont(new Font("NewsGoth BT", 1, 20));
        this.mfile.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\file.png"));
        this.menubar.add(this.mfile);

        (this.open = new JMenuItem("Open")).setAccelerator(KeyStroke.getKeyStroke(79, 2));
        this.open.setHorizontalAlignment(2);
        this.open.setForeground(Color.BLACK);
        this.open.setBackground(Color.WHITE);
        this.open.setFont(new Font("NewsGoth BT", 0, 18));
        this.open.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\openfile.png"));
        this.open.addActionListener(this);

        (this.newfile = new JMenuItem("New")).setHorizontalAlignment(2);
        this.newfile.setForeground(Color.BLACK);
        this.newfile.setBackground(Color.WHITE);
        this.newfile.setAccelerator(KeyStroke.getKeyStroke(78, 2));
        this.newfile.setFont(new Font("NewsGoth BT", 0, 18));
        this.newfile.addActionListener(this);
        this.newfile.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\newfile.png"));
        this.mfile.add(this.newfile);

        (this.newwindow = new JMenuItem("New Window")).setAccelerator(KeyStroke.getKeyStroke(78, 3));
        this.newwindow.setHorizontalAlignment(2);
        this.newwindow.setForeground(Color.BLACK);
        this.newwindow.setFont(new Font("NewsGoth BT", 0, 18));
        this.newwindow.setBackground(Color.WHITE);
        this.newwindow.addActionListener(this);
        this.newwindow.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\newwindow.png"));
        this.mfile.add(this.newwindow);
        this.mfile.add(this.open);

        (this.save = new JMenuItem("Save")).setAccelerator(KeyStroke.getKeyStroke(83, 2));
        this.save.setForeground(Color.BLACK);
        this.save.setBackground(Color.WHITE);
        this.save.setHorizontalAlignment(2);
        this.save.setFont(new Font("NewsGoth BT", 0, 18));
        this.save.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\savefile.png"));
        this.save.addActionListener(this);
        this.mfile.add(this.save);

        (this.saveas = new JMenuItem("Save As")).setAccelerator(KeyStroke.getKeyStroke(83, 3));
        this.saveas.setForeground(Color.BLACK);
        this.saveas.setBackground(Color.WHITE);
        this.saveas.setHorizontalAlignment(2);
        this.saveas.setFont(new Font("NewsGoth BT", 0, 18));
        this.saveas.addActionListener(this);
        this.saveas.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\saveas.png"));
        this.mfile.add(this.saveas);

        (this.print = new JMenuItem("Print")).setAccelerator(KeyStroke.getKeyStroke(80, 2));
        this.print.setBackground(Color.WHITE);
        this.print.setFont(new Font("NewsGoth BT", 0, 18));
        this.print.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\print.png"));
        this.print.addActionListener(this);
        this.mfile.add(this.print);

        (this.exit = new JMenuItem("Exit")).setAccelerator(KeyStroke.getKeyStroke(27, 0));
        this.exit.setBackground(Color.WHITE);
        this.exit.setFont(new Font("NewsGoth BT", 0, 18));
        this.exit.addActionListener(this);
        this.exit.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\exit.png"));
        final JLabel lblNewLabel_1 = new JLabel("                               ");
        this.mfile.add(lblNewLabel_1);
        this.mfile.add(this.exit);


        (this.medit = new JMenu("Edit    ")).setForeground(Color.BLACK);
        this.medit.setFont(new Font("NewsGoth BT", 1, 20));
        this.medit.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\edit.png"));
        this.menubar.add(this.medit);

        (this.copy = new JMenuItem("Copy")).setAccelerator(KeyStroke.getKeyStroke(67, 2));
        this.copy.setBackground(Color.WHITE);
        this.copy.setHorizontalAlignment(2);
        this.copy.setFont(new Font("NewsGoth BT", 0, 18));
        this.copy.addActionListener(this);
        this.copy.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\copy.png"));
        this.medit.add(this.copy);

        (this.cut = new JMenuItem("Cut")).setAccelerator(KeyStroke.getKeyStroke(88, 2));
        this.cut.setBackground(Color.WHITE);
        this.cut.setFont(new Font("NewsGoth BT", 0, 18));
        this.cut.setHorizontalAlignment(2);
        this.cut.addActionListener(this);
        this.cut.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\cut.png"));
        this.medit.add(this.cut);

        (this.paste = new JMenuItem("Paste")).setAccelerator(KeyStroke.getKeyStroke(86, 2));
        this.paste.setFont(new Font("NewsGoth BT", 0, 18));
        this.paste.setBackground(Color.white);
        this.paste.setHorizontalAlignment(2);
        this.paste.addActionListener(this);
        this.paste.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\paste.png"));
        this.medit.add(this.paste);

        (this.undo = new JMenuItem("Undo")).setAccelerator(KeyStroke.getKeyStroke(90, 2));
        this.undo.setFont(new Font("NewsGoth BT", 0, 18));
        this.undo.setBackground(new Color(255, 255, 255));
        this.undo.addActionListener(this);

        (this.selectall = new JMenuItem("Select All")).setBackground(Color.WHITE);
        this.selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        this.selectall.setFont(new Font("NewsGoth BT", 0, 18));
        this.selectall.addActionListener(this);
        
        delete = new JMenuItem("Delete");
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        delete.setBackground(Color.WHITE);
        delete.setFont(new Font("NewsGoth BT", Font.PLAIN, 18));
        delete.addActionListener(this);
        this.delete.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\delete.png"));

        medit.add(delete);
        this.selectall.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\selectall.png"));
        this.medit.add(this.selectall);
        this.undo.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\undo.png"));
        this.medit.add(this.undo);

        (this.redo = new JMenuItem("Redo")).setAccelerator(KeyStroke.getKeyStroke(89, 2));
        this.redo.setBackground(new Color(255, 255, 255));
        this.redo.setFont(new Font("NewsGoth BT", 0, 18));
        this.redo.addActionListener(this);
        this.redo.setBackground(Color.white);
        this.redo.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\redo.png"));
        this.medit.add(this.redo);

        (this.find = new JMenuItem("Find")).setForeground(Color.BLACK);
        this.find.setBackground(Color.WHITE);
        this.find.setAccelerator(KeyStroke.getKeyStroke(70, 2));
        this.find.setFont(new Font("NewsGoth BT", 0, 18));
        this.find.addActionListener(this);
        final JLabel lblNewLabel = new JLabel("                ");
        this.medit.add(lblNewLabel);
        this.find.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\find.png"));
        this.medit.add(this.find);

        (this.replace = new JMenuItem("Replace")).setBackground(Color.WHITE);
        this.replace.setForeground(Color.BLACK);
        this.replace.setAccelerator(KeyStroke.getKeyStroke(82, 2));
        this.replace.setFont(new Font("NewsGoth BT", 0, 18));
        this.medit.add(this.replace);
        this.replace.addActionListener(this);
        this.replace.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\replace.png"));


        (this.mview = new JMenu("View    ")).setForeground(Color.BLACK);
        this.mview.setBackground(Color.WHITE);
        this.mview.setFont(new Font("NewsGoth BT", 1, 20));
        this.mview.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\view.png"));
        this.menubar.add(this.mview);

        (this.zoomin = new JMenuItem("Zoom In")).setForeground(Color.BLACK);
        this.zoomin.setBackground(Color.WHITE);
        this.zoomin.setFont(new Font("NewsGoth BT", 0, 18));
        this.zoomin.setAccelerator(KeyStroke.getKeyStroke(93, 2));
        this.zoomin.addActionListener(this);
        this.zoomin.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\zoomin.png"));
        this.mview.add(this.zoomin);

        (this.zoomout = new JMenuItem("Zoom Out")).setForeground(Color.BLACK);
        this.zoomout.setBackground(Color.WHITE);
        this.zoomout.setAccelerator(KeyStroke.getKeyStroke(91, 2));
        this.zoomout.setFont(new Font("NewsGoth BT", 0, 18));
        this.zoomout.addActionListener(this);
        this.zoomout.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\zoomout.png"));
        this.mview.add(this.zoomout);

        (this.defaultzoom = new JMenuItem("Default Zoom")).setForeground(Color.BLACK);
        this.defaultzoom.setBackground(Color.WHITE);
        this.defaultzoom.setAccelerator(KeyStroke.getKeyStroke(10, 2));
        this.defaultzoom.setFont(new Font("NewsGoth BT", 0, 18));
        this.defaultzoom.addActionListener(this);
        this.defaultzoom.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\defaultzoom.png"));
        this.mview.add(this.defaultzoom);


        (this.mfont = new JMenu("Font     ")).setForeground(Color.BLACK);
        this.mfont.setFont(new Font("NewsGoth BT", 1, 20));
        this.mfont.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\mfont.png"));
        this.menubar.add(this.mfont);

        (this.font = new JMenuItem("Font")).setBackground(Color.WHITE);
        this.font.setFont(new Font("NewsGoth BT", 0, 18));
        this.font.addActionListener(this);
        this.font.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\font.png"));
        this.mfont.add(this.font);

        (this.defaultfont = new JMenuItem("Default Font")).setBackground(Color.WHITE);
        this.defaultfont.setFont(new Font("NewsGoth BT", 0, 18));
        this.defaultfont.addActionListener(this);
        this.defaultfont.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\defaultfont.png"));
        this.mfont.add(this.defaultfont);


        (this.mcolor = new JMenu("Color     ")).setForeground(Color.BLACK);
        this.mcolor.setBackground(Color.WHITE);
        this.mcolor.setFont(new Font("NewsGoth BT", 1, 20));
        this.mcolor.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\mcolor.png"));
        this.menubar.add(this.mcolor);

        (this.color = new JMenuItem("Text Color")).setBackground(Color.WHITE);
        this.color.setFont(new Font("NewsGoth BT", 0, 20));
        this.color.addActionListener(this);
        this.color.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\color.png"));
        this.mcolor.add(this.color);

        (this.defaultcolor = new JMenuItem("Default Color")).setFont(new Font("NewsGoth BT", 0, 20));
        this.defaultcolor.setBackground(Color.WHITE);
        this.defaultcolor.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\defaultcolor.png"));
        this.defaultcolor.addActionListener(this);

        (this.selectioncolor = new JMenuItem("Selection Color")).setFont(new Font("NewsGoth BT", 0, 20));
        this.selectioncolor.addActionListener(this);
        this.selectioncolor.setBackground(Color.white);
        this.selectioncolor.setIcon(new ImageIcon("F:\\Assets\\TextEditor\\selectioncolor.png"));
        this.mcolor.add(this.selectioncolor);
        this.mcolor.add(this.defaultcolor);

        if (!this.opensidebar) {
            this.widthofarea = this.getWidth() - 35;
            this.sidebarpanel.setVisible(false);
            this.close.setVisible(false);
        }
        else {
            this.widthofarea = this.getWidth() - this.sidebarpanel.getWidth() - 45;
            this.sidebarpanel.setVisible(true);
            this.close.setVisible(true);
        }
        (this.textpanel = new JPanel()).setBackground(new Color(192, 192, 192));
        this.textpanel.setBounds(10, 72, this.widthofarea, 578);
        this.contentPane.add(this.textpanel);
        this.textpanel.setLayout(null);
        (TextEditor.textarea = new JTextArea()).setBounds(20, 5, this.widthofarea - 10, 568);
        TextEditor.textarea.setFont(new Font("Arial", 0, 20));
        TextEditor.textarea.setSelectionColor(new Color(this.colors[22][0], this.colors[22][1], this.colors[22][2]));
        this.textpanel.add(TextEditor.textarea);
        (this.sc = new JScrollPane(TextEditor.textarea, 20, 30)).setBackground(Color.white);
        this.sc.setBounds(25, 10, this.widthofarea - 20, 578);
        this.sc.getVerticalScrollBar().setBackground(Color.white);
        this.sc.getHorizontalScrollBar().setBackground(Color.white);
        this.sc.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        this.sc.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        this.textpanel.add(this.sc);
        TextEditor.textarea.getDocument().addUndoableEditListener(this.manager);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent windowenent) {
                final Object sc = TextEditor.this.exit;
                TextEditor.this.filemenuaction(sc);
            }
        });

        
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
    	
        final Object source = e.getSource();
        this.filemenuaction(source);
        this.editmenuaction(source);
        this.viewmenuaction(source);
        this.fontmenuaction(source);
        this.colormenuaction(source);
        if (this.saveddata.compareTo(TextEditor.textarea.getText())==0)
        {
            if (this.getTitle().charAt(0) == '*') {
                this.setTitle(this.getTitle().substring(1));
            }
        }
        else if (this.getTitle().charAt(0) != '*') {
            this.setTitle("*" + this.getTitle());
        }
        for (int i = 0; i < this.b.length; ++i) {
            if (source == this.b[i]) {
                if (this.colorpanel) {
                    this.colornumber = i;
                }
                else if (this.selectioncolorpanel) {
                    this.selectioncolornumber = i;
                }
            }
        }
        if (source == this.close) {
            this.opensidebar = false;
            this.fontpanel = false;
            this.colorpanel = false;
            this.selectioncolorpanel = false;
        }
        else if (source == this.ok) {
            this.setCursor(new Cursor(3));
            this.opensidebar = false;
            this.changefontstyle(TextEditor.textarea);
        }
        if (!this.opensidebar) {
            this.widthofarea = this.getWidth() - 35;
            this.sidebarpanel.setVisible(false);
            this.close.setVisible(false);
        }
        else {
            this.widthofarea = this.getWidth() - this.sidebarpanel.getWidth() - 45;
            this.sidebarpanel.setVisible(true);
            this.close.setVisible(true);
        }
        this.resize();
    }
    
    public void resize() {
        this.textpanel.setBounds(10, 72, this.widthofarea, this.getHeight() - this.menubar.getHeight() - 75);
        this.setCursor(new Cursor(0));
        TextEditor.textarea.setCursor(new Cursor(2));
        this.sc.setBounds(5, 5, this.widthofarea - 10, this.getHeight() - this.menubar.getHeight() - 75 - 10);
        this.sidebarpanel.setBounds(this.getWidth() - 215, 72, 187, this.getHeight() - this.menubar.getHeight() - 75);
        this.close.setBounds(10, this.sidebarpanel.getHeight() - 43, this.sidebarpanel.getWidth() / 2 - 10, 33);
        this.ok.setBounds(100, this.sidebarpanel.getHeight() - 43, this.sidebarpanel.getWidth() / 2 - 10, 33);
        this.menubar.setBounds(0, 0, this.getWidth() - 36, 44);
        this.menubarpanel.setBounds(10, 10, this.getWidth() - 36, 44);
        this.setMinimumSize(new Dimension(500, 500));
        this.changefontstyle(this.fontlabel);
        int row = 10;
        int colume = 10;
        int count = 0;
        for (int i = 1; i <= 12; ++i) {
            colume = 10;
            for (int j = 1; j <= 4; ++j) {
                this.b[count].setBounds(colume, row, 35, this.getHeight() / 25);
                colume += 43;
                if (this.selectioncolorpanel) {
                    if (count == this.selectioncolornumber) {
                        this.b[count].setBorder(this.activecolorborder);
                        this.b[count].setText("+");
                        TextEditor.textarea.setSelectionColor(new Color(this.colors[count][0], this.colors[count][1], this.colors[count][2]));
                        this.ok.setBackground(new Color(this.colors[this.selectioncolornumber][0], this.colors[this.selectioncolornumber][1], this.colors[this.selectioncolornumber][2]));
                        this.ok.setForeground(Color.black);
                        this.ok.setEnabled(false);
                    }
                    else {
                        this.b[count].setBorder(this.border);
                        this.b[count].setText("");
                    }
                }
                if (this.colorpanel) {
                    if (this.colornumber == count) {
                        this.b[count].setBorder(this.activecolorborder);
                        this.b[count].setText("+");
                        TextEditor.textarea.setForeground(new Color(this.colors[count][0], this.colors[count][1], this.colors[count][2]));
                        this.ok.setBackground(new Color(this.colors[this.colornumber][0], this.colors[this.colornumber][1], this.colors[this.colornumber][2]));
                        this.ok.setForeground(Color.black);
                        this.ok.setEnabled(false);
                    }
                    else {
                        this.b[count].setBorder(this.border);
                        this.b[count].setText("");
                    }
                }
                if (this.fontpanel) {
                    this.ok.setBackground(Color.white);
                    this.ok.setForeground(Color.black);
                    this.ok.setText("Ok");
                    this.ok.setEnabled(true);
                }
                ++count;
            }
            row += this.getHeight() / 20 + this.getHeight() / 200;
        }
    }
    
    public void changefontstyle(final JComponent c) {
        final String fn = (String)this.fontcombo.getSelectedItem();
        final String fs = (String)this.stylecombo.getSelectedItem();
        final String se = (String)this.sizecombo.getSelectedItem();
        final int s = Integer.parseInt(se);
        if (fs == "Plain") {
            c.setFont(new Font(fn, 0, s));
        }
        else if (fs == "Bold") {
            c.setFont(new Font(fn, 1, s));
        }
        else if (fs == "Italic") {
            c.setFont(new Font(fn, 2, s));
        }
        else if (fs == "Bold-Italic") {
            c.setFont(new Font(fn, 3, s));
        }
    }
    
    public void filemenuaction(final Object source)
    {
        if (source == this.newfile) 
                {
            this.savedpath = "null";
            this.savedfilename = "Untitled";
            TextEditor.textarea.setText("");
            this.saveddata = "";
            this.setTitle("Untitled");
        }
        else if (source == this.newwindow)
        {
            final TextEditor m = new TextEditor();
            m.setVisible(true);
            m.setLocation(this.getX() + 20, this.getY() + 20);
        }
        else if (source == this.open) 
        {
            if (this.saveddata.length() != TextEditor.textarea.getText().length()) 
            {
                final int result = JOptionPane.showConfirmDialog(null, "Do you want to save this " + this.savedfilename + " file ?");
                if (result == 0) 
                {
                    final Object sc = this.save;
                    this.filemenuaction(sc);
                    this.openfile();
                }
                else if (result == 1) {
                    this.openfile();
                }
            }
            else 
            {
                this.openfile();
            }
        }
        else if (source == this.save) {
            if (this.savedfilename == "*Untitled" || this.savedfilename == "Untitled") {
                final JFileChooser filechooser = new JFileChooser("./file");
                final int result2 = filechooser.showSaveDialog(null);
                if (result2 == 0) {
                    try {
                    	this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    	TextEditor.textarea.setCursor(new Cursor(3));
                        final File filename = filechooser.getSelectedFile();
                        this.savedfilename = this.getTitle();
                        this.savedpath = filename.getAbsolutePath();
                        this.setTitle(filechooser.getName(filename));
                        final FileWriter fw = new FileWriter(filename, false);
                        final BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(TextEditor.textarea.getText());
                        bw.close();
                        this.saveddata = TextEditor.textarea.getText();
                    }
                    catch (Exception exp) {
                        System.out.println("File cannot save");
                    }
                }
            }
            else {
                try {
                	this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    final FileWriter fw2 = new FileWriter(this.savedpath, false);
                    final BufferedWriter bw2 = new BufferedWriter(fw2);
                    this.saveddata = TextEditor.textarea.getText();
                    bw2.write(TextEditor.textarea.getText());
                    bw2.close();
                    
                }
                catch (Exception exp2) {
                    System.out.println("File not saved");
                }
            }
        }
        else if (source == this.saveas) {
            final JFileChooser filechooser = new JFileChooser("./file");
            final int result2 = filechooser.showSaveDialog(null);
            if (result2 == 0) {
                try {
                	this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    TextEditor.textarea.setCursor(new Cursor(3));
                    final File filename = filechooser.getSelectedFile();
                    if (this.savedpath != "null") {
                        final File file = new File(this.savedpath);
                        file.delete();
                    }
                    this.setTitle(filechooser.getName(filename));
                    this.savedfilename = this.getTitle();
                    this.savedpath = filename.getAbsolutePath();
                    final FileWriter fw = new FileWriter(filename);
                    final BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(TextEditor.textarea.getText());
                    bw.close();
                    this.saveddata = TextEditor.textarea.getText();
                    System.out.println(this.saveddata);
                }
                catch (Exception exp) {
                    System.out.println("File cannot save");
                }
            }
        }
        else if (source == this.print) {
            try {
                TextEditor.textarea.print();
            }
            catch (PrinterException e) {
                e.printStackTrace();
            }
        }
        else if (source == this.exit) {
            if (this.saveddata.length() != TextEditor.textarea.getText().length()) {
                final int result = JOptionPane.showConfirmDialog(null, "Do you want to save this file ?");
                if (result == 0) {
                    final Object sc = this.save;
                    this.filemenuaction(sc);
                    this.dispose();
                }
                else if (result == 1) {
                	 this.dispose();
                }
            }
            else {
            	 this.dispose();
            }
        }
    }
    
    public void editmenuaction(final Object source) {
        if (source == this.copy) {
            TextEditor.textarea.copy();
        }
        else if (source == this.paste) {
            TextEditor.textarea.paste();
        }
        else if (source == this.cut) {
            TextEditor.textarea.cut();
        }
        else if (source == this.selectall) {
            TextEditor.textarea.selectAll();
        }
        else if (source == this.undo) {
            try {
                this.manager.undo();
            }
            catch (Exception ex) {}
        }
        else if(source==this.delete)
        {
        	int start=textarea.getSelectionStart();
        	int end=textarea.getSelectionEnd();
        	String str=textarea.getText();
        	str=str.substring(0,start)+str.substring(end);
        	textarea.setText(str);
        }
        else if (source == this.redo) {
            try {
                this.manager.redo();
            }
            catch (Exception ex2) {}
        }
        else if (source == this.replace) {
            Replace r = new Replace();
            r.setVisible(true);
            r.setLocation(this.getX() + this.getWidth() / 4, this.getY() + this.getHeight() / 4);
        }
        else if (source == this.find) {
            final Find f = new Find();
            f.setVisible(true);
            f.setLocation(this.getX() + this.getWidth() / 4, this.getY() + this.getHeight() / 4);
        }
    }
    
    public void viewmenuaction(final Object source) {
        if (source == this.zoomin) {
            if (this.sizecombo.getSelectedIndex() < 93) {
                this.sizecombo.setSelectedIndex(this.sizecombo.getSelectedIndex() + 2);
                this.changefontstyle(TextEditor.textarea);
            }
        }
        else if (source == this.zoomout) {
            if (this.sizecombo.getSelectedIndex() > 0) {
                this.sizecombo.setSelectedIndex(this.sizecombo.getSelectedIndex() - 2);
                this.changefontstyle(TextEditor.textarea);
            }
        }
        else if (source == this.defaultzoom) {
            this.sizecombo.setSelectedItem("20");
            this.changefontstyle(TextEditor.textarea);
        }
    }
    
    public void fontmenuaction(final Object source) {
        if (source == this.font) {
            this.opensidebar = true;
            this.sizecombo.setVisible(true);
            this.fontcombo.setVisible(true);
            this.stylecombo.setVisible(true);
            this.fontlabel.setVisible(true);
            this.fontpanel = true;
            this.colorpanel = false;
            this.selectioncolorpanel = false;
            for (int i = 0; i < this.b.length; ++i) {
                this.b[i].setVisible(false);
            }
        }
        else if (source == this.defaultfont) {
            this.sizecombo.setSelectedItem("20");
            this.fontcombo.setSelectedItem("Arial");
            this.stylecombo.setSelectedItem("Plain");
            this.changefontstyle(TextEditor.textarea);
        }
    }
    
    public void colormenuaction(final Object source) {
        if (source == this.color) {
            this.opensidebar = true;
            for (int i = 0; i < this.b.length; ++i) {
                this.b[i].setVisible(true);
            }
            this.ok.setText("");
            this.fontpanel = false;
            this.selectioncolorpanel = false;
            this.colorpanel = true;
            this.sizecombo.setVisible(false);
            this.fontcombo.setVisible(false);
            this.stylecombo.setVisible(false);
            this.fontlabel.setVisible(false);
        }
        else if (source == this.defaultcolor) {
            this.colornumber = 37;
            this.selectioncolornumber = 22;
            TextEditor.textarea.setForeground(new Color(this.colors[37][0], this.colors[37][1], this.colors[37][2]));
            TextEditor.textarea.setSelectionColor(new Color(this.colors[22][0], this.colors[22][1], this.colors[22][2]));
        }
        else if (source == this.selectioncolor) {
            this.opensidebar = true;
            for (int i = 0; i < this.b.length; ++i) {
                this.b[i].setVisible(true);
            }
            this.ok.setText("");
            this.fontpanel = false;
            this.colorpanel = false;
            this.selectioncolorpanel = true;
            this.sizecombo.setVisible(false);
            this.fontcombo.setVisible(false);
            this.stylecombo.setVisible(false);
            this.fontlabel.setVisible(false);
        }
    }
    
    public void openfile() {
        final JFileChooser filechooser = new JFileChooser("./file");
        final int result = filechooser.showOpenDialog(this);
        if (result == 0) {
            try {
                final File selectedfile = filechooser.getSelectedFile();
                TextEditor.textarea.setCursor(new Cursor(3));
                final FileReader fw = new FileReader(selectedfile.getAbsoluteFile());
                final BufferedReader br = new BufferedReader(fw);
                this.setTitle(filechooser.getName(selectedfile));
                this.savedfilename = this.getTitle();
                this.savedpath = selectedfile.getAbsolutePath();
                TextEditor.textarea.setText("");
                String data;
                while ((data = br.readLine()) != null) {
                    TextEditor.textarea.append(String.valueOf(data) + "\n");
                }
                br.close();
                this.saveddata = TextEditor.textarea.getText();
            }
            catch (Exception exp) {
                System.out.println("File Not Open");
            }
        }
    }

    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final TextEditor frame = new TextEditor();
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    frame.setVisible(true);
                    frame.setLocation(200, 20);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}