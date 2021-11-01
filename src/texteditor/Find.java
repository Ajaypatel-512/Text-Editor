package texteditor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Find extends JDialog implements ActionListener {
    private JPanel contentpane;
    private JLabel label1,direction;
    private JTextField findtext;
    private JButton bfindnext,bcancel;
    private JRadioButton up,down;
    private String find;
    int start,end;

    public Find() {
        this.setSize(380, 180);
        this.setTitle("Find");
        this.setResizable(false);
        this.setBackground(Color.white);
        (this.contentpane = new JPanel()).setBorder(null);
        this.contentpane.setLayout(null);
        this.setContentPane(this.contentpane);
        (this.label1 = new JLabel("Find What : ")).setBounds(20, 20, 100, 15);
        this.label1.setBackground(Color.white);
        this.label1.setForeground(Color.black);
        this.label1.setFont(new Font("NewsGoth BT", 0, 15));
        this.contentpane.add(this.label1);
        (this.findtext = new JTextField()).setBounds(140, 15, 200, 25);
        this.findtext.setFont(new Font("Arial", 0, 15));
        this.contentpane.add(this.findtext);
        (this.bfindnext = new JButton("Find Next")).setBounds(235, 65, 100, 25);
        this.bfindnext.setBackground(Color.white);
        this.bfindnext.setFocusable(false);
        this.bfindnext.setVisible(true);
        this.bfindnext.addActionListener(this);
        this.contentpane.add(this.bfindnext);
        (this.bcancel = new JButton("Cancel")).setBounds(235, 100, 100, 25);
        this.bcancel.setBackground(Color.white);
        this.bcancel.setFocusable(false);
        this.bcancel.setVisible(true);
        this.bcancel.addActionListener(this);
        this.contentpane.add(this.bcancel);
        final JPanel panel = new JPanel();
        panel.setBounds(10, 65, 170, 60);
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
    }

    public static void main(final String args) {
        final Find f = new Find();
        f.setVisible(true);
        f.setLocation(400, 200);
    }
}

