import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UI {

    private JFrame frame;
    private JTextField inMonth;
    private JTextField monthNumber;
    private JTextField year;
    private JTextField workToDay;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UI window = new UI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public UI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        inMonth = new JTextField();
        inMonth.setBounds(150, 28, 120, 20);
        frame.getContentPane().add(inMonth);
        inMonth.setColumns(10);

        JLabel lblInMonth = new JLabel("W miesiącu");
        lblInMonth.setBounds(65, 31, 200, 14);
        frame.getContentPane().add(lblInMonth);

        JLabel lblMonthNumber = new JLabel("Nr miesiąca");
        lblMonthNumber.setBounds(65, 68, 200, 14);
        frame.getContentPane().add(lblMonthNumber);


        monthNumber = new JTextField();
        monthNumber.setBounds(150, 65, 40, 20);
        frame.getContentPane().add(monthNumber);
        monthNumber.setColumns(10);

        JLabel lblYear = new JLabel("Rok");
        lblYear.setBounds(65, 115, 46, 14);
        frame.getContentPane().add(lblYear);

        year = new JTextField();
        year.setBounds(150, 112, 40, 17);
        frame.getContentPane().add(year);
        year.setColumns(10);

        JLabel lblLastDayOfWork = new JLabel("Praca do dnia");
        lblLastDayOfWork.setBounds(65, 162, 200, 14);
        frame.getContentPane().add(lblLastDayOfWork);


        workToDay = new JTextField();
        workToDay.setBounds(150, 157, 40, 17);
        frame.getContentPane().add(workToDay);
        workToDay.setColumns(10);


        JButton btnSubmit = new JButton("submit");
        btnSubmit.setBounds(65, 387, 89, 23);
        frame.getContentPane().add(btnSubmit);


        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (inMonth.getText().isEmpty() || (monthNumber.getText().isEmpty()) || (year.getText().isEmpty()))
                    JOptionPane.showMessageDialog(null, "Data Missing");
                else {
                    try {
                        CreateExcel.createFile(
                                inMonth.getText(),
                                Integer.valueOf(monthNumber.getText()) - 1,
                                Integer.valueOf(year.getText()),
                                Integer.valueOf(workToDay.getText()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}

