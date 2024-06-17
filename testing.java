import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

class testing extends JFrame {
    private JTextField tfno1, tfno2, tfResult;
    private JButton btAdd, btSub, btMul, btDiv;
    private JLabel lb1;

    public testing() {
        btAdd = new JButton("Add");
        btSub = new JButton("Subtract");
        btMul = new JButton("Multiply");
        btDiv = new JButton("Divide");

        tfno1 = new JTextField(3);
        tfno2 = new JTextField(3);
        tfResult = new JTextField(8);
        tfResult.setEditable(false); // 禁止用户编辑结果文本框

        // Panel p1 to hold/add text fields and labels
        JPanel p1 = new JPanel(new FlowLayout());
        p1.add((lb1 = new JLabel("Number 1")));
        p1.add(tfno1);
        p1.add(new JLabel("Number 2"));
        p1.add(tfno2);
        p1.add(new JLabel("Result"));
        p1.add(tfResult);

        // Panel p2 to hold/add buttons
        JPanel p2 = new JPanel(new GridLayout(1, 4));
        p2.add(btAdd);
        p2.add(btSub);
        p2.add(btMul);
        p2.add(btDiv);

        // setting the layout for frame and add panels to the fram  e
        setLayout(new BorderLayout());
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        testing frame = new testing(); // your program is a frame
        frame.setSize(400, 150);
        frame.setTitle("Simple Calculator");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}