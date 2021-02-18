package life;

import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;

public class GameOfLife extends JFrame {

    private int genCount = 1;
    private final JLabel gen;
    private final JLabel aliveCount;
    private final Board board = new Board(20);
    private final JToggleButton button1;
    private final JButton button2;
    final Timer timer;


    public GameOfLife() {
        super("Game of Life");
        setSize(330, 405);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);


        gen = new JLabel();
        gen.setName("GenerationLabel");
        gen.setFont(new Font("Tahoma", Font.PLAIN, 11));

        aliveCount = new JLabel();
        aliveCount.setName("AliveLabel");
        aliveCount.setFont(new Font("Tahoma", Font.PLAIN, 11));

        timer = new Timer(80, e -> cycling());
        timer.start();

        button1 = new JToggleButton("pause");
        button1.setName("PlayToggleButton");
        button1.setVisible(true);
        button1.addActionListener((e -> {
            if (button1.isSelected()) {
                timer.stop();
                button1.setText("paused");
            } else {
                timer.start();
                button1.setText("pause");
            }
        }));

        button2 = new JButton("reset");
        button2.setName("ResetButton");
        button2.setVisible(true);
        button2.addActionListener((e -> reset()));


        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new BorderLayout());


        JPanel headPanel = new JPanel();
        headPanel.setAlignmentX(0.05f);
        headPanel.setAlignmentY(0.05f);
        headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.Y_AXIS));
        headPanel.add(gen);
        headPanel.add(aliveCount);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(button1);
        bottomPanel.add(button2);
        bottomPanel.setVisible(true);

        generalPanel.add(headPanel, BorderLayout.CENTER);
        generalPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(generalPanel);

    }

    private void reset() {
        board.initialization();
        genCount = 1;
    }

    private void cycling () {
            repaint();
            board.nextGeneration(board.getMap());
        }





    @Override
    public void paint(Graphics g) {

        Graphics2D cells = (Graphics2D) g;

        int x = 15;
        int y = 60;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                cells.drawRect(x, y, 15, 15);
                boolean isAlive = board.getElement(i, j) == 'O';
                if (isAlive) {

                    cells.fillRect(x + 1, y + 1, 14, 14);
                } else {

                    cells.clearRect(x + 1, y + 1, 14, 14);
                }
                x += 15;
            }
            x = 15;
            y += 15;
        }

        gen.setText("  Generation #" + genCount++);
        aliveCount.setText("  Alive: " + board.countAlive(board.getMap()));
    }
}
