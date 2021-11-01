package texteditor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Replace extends JDialog implements ActionListener {
    private JLabel label1,label2;
    private JTextField findtext,replacetext;
    private JButton bfindnext,breplace,breplaceall,bcancel;
    private JPanel contentpane;
    private JRadioButton up,down;
    private String find,replace;
    private int start,end;

    public Replace() {
        this.find = "";
        this.replace = "";
        this.start = 0;
        this.end = 0;
        (this.contentpane = new JPanel()).setBorder(null);
        this.setResizable(false);
        this.setContentPane(this.contentpane);
        this.setTitle("Replace");
        this.setSize(500, 200);
        this.setBackground(Color.white);
        this.contentpane.setLayout(null);
        (this.label1 = new JLabel("Find What : ")).setBounds(20, 20, 100, 15);
        this.label1.setBackground(Color.white);
        this.label1.setForeground(Color.black);
        this.label1.setFont(new Font("NewsGoth BT", 0, 15));
        this.contentpane.add(this.label1);
        (this.label2 = new JLabel("Replace with : ")).setBounds(20, 60, 100, 15);
        this.label2.setBackground(Color.white);
        this.label2.setForeground(Color.black);
        this.label2.setFont(new Font("NewsGoth BT", 0, 15));
        this.contentpane.add(this.label2);
        (this.findtext = new JTextField()).setBounds(140, 15, 200, 25);
        this.findtext.setFont(new Font("Arial", 0, 15));
        this.contentpane.add(this.findtext);
        (this.replacetext = new JTextField()).setBounds(140, 55, 200, 25);
        this.replacetext.setFont(new Font("Arial", 0, 15));
        this.contentpane.add(this.replacetext);
        (this.bfindnext = new JButton("Find Next")).setBounds(360, 15, 100, 25);
        this.bfindnext.setBackground(Color.white);
        this.bfindnext.setFocusable(false);
        this.bfindnext.setVisible(true);
        this.bfindnext.addActionListener(this);
        this.contentpane.add(this.bfindnext);
        (this.breplace = new JButton("Replace")).setBounds(360, 45, 100, 25);
        this.breplace.setBackground(Color.white);
        this.breplace.setFocusable(false);
        this.breplace.addActionListener(this);
        this.breplace.setVisible(true);
        this.contentpane.add(this.breplace);
        (this.breplaceall = new JButton("Replace All")).setBounds(360, 75, 100, 25);
        this.breplaceall.setBackground(Color.white);
        this.breplaceall.setFocusable(false);
        this.breplaceall.setVisible(true);
        this.breplaceall.addActionListener(this);
        this.contentpane.add(this.breplaceall);
        (this.bcancel = new JButton("Cancel")).setBounds(360, 105, 100, 25);
        this.bcancel.setBackground(Color.white);
        this.bcancel.setFocusable(false);
        this.bcancel.setVisible(true);
        this.bcancel.addActionListener(this);
        this.contentpane.add(this.bcancel);
        final JPanel panel = new JPanel();
        panel.setBounds(140, 85, 170, 60);
        panel.setBorder(BorderFactory.createTitledBorder("Direction"));
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.gray), "Direction"));
        this.contentpane.add(panel);
        (this.up = new JRadioButton("Up")).setFocusPainted(false);
        panel.add(this.up);
        (this.down = new JRadioButton("Down")).setSelected(true);
        this.down.setFocusPainted(false);
        panel.add(this.down);
        final ButtonGroup bg = new ButtonGroup();
        bg.add(this.up);
        bg.add(this.down);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Object source = e.getSource();
        if (source == this.bfindnext && this.down.isSelected()) {
            final String str = TextEditor.textarea.getText().substring(this.end);
            this.find = this.findtext.getText();
            if (str.contains(this.find)) {
                this.start = TextEditor.textarea.getText().indexOf(str) + str.indexOf(this.find);
                this.end = this.start + this.find.length();
                TextEditor.textarea.setSelectionStart(this.start);
                TextEditor.textarea.setSelectionEnd(this.end);
            }
            else {
                JOptionPane.showMessageDialog(null, "cannot find " + this.find);
            }
        }
        else if (source == this.bfindnext && this.up.isSelected()) {
            final String str = TextEditor.textarea.getText().substring(0, this.start);
            this.find = this.findtext.getText();
            if (str.contains(this.find)) {
                this.start = TextEditor.textarea.getText().indexOf(str) + str.lastIndexOf(this.find);
                this.end = this.start + this.find.length();
                TextEditor.textarea.setSelectionStart(this.start);
                TextEditor.textarea.setSelectionEnd(this.end);
            }
            else {
                JOptionPane.showMessageDialog(null, "cannot find " + this.find);
            }
        }
        else if (source == this.breplace) {
            TextEditor.textarea.cut();
            this.replace = this.replacetext.getText();
            String data = TextEditor.textarea.getText();
            data = String.valueOf(data.substring(0, this.start)) + this.replace + data.substring(this.start);
            TextEditor.textarea.setText(data);
        }
        else if (source == this.breplaceall) {
            this.find = this.findtext.getText();
            this.end = 0;
            boolean allreplace = false;
            while (!allreplace) {
                final String str2 = TextEditor.textarea.getText().substring(this.end);
                if (str2.contains(this.find)) {
                    this.start = TextEditor.textarea.getText().indexOf(str2) + str2.indexOf(this.find);
                    this.end = this.start + this.find.length();
                    this.replace = this.replacetext.getText();
                    String data2 = TextEditor.textarea.getText();
                    data2 = String.valueOf(data2.substring(0, this.start)) + this.replace + data2.substring(this.end);
                    TextEditor.textarea.setText(data2);
                }
                else {
                    allreplace = true;
                }
            }
        }
        else if (source == this.bcancel) {
            this.dispose();
        }
    }

    public static void main(final String[] args) {
        final Replace r = new Replace();
        r.setVisible(true);
        r.setLocation(400, 200);
    }
}