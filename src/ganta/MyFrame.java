package ganta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyFrame extends JFrame implements BaronListener {

    private Image scrimg;
    private Graphics2D scrgrp;

    private Image introbg;
    private JButton startb;
    private JButton quitb;
    private JButton hpbar;

    private JLabel hpLabel;

    private Baron baron;

    private static final int HP_BAR_SIZE = 500;

    public MyFrame(Baron baron) {
        this.baron = baron;

        this.introbg = this.imageIconFromPath("../images/introbg.png").getImage();
        this.startb = new JButton(this.imageIconFromPath("../images/startb.png"));
        this.quitb = new JButton(this.imageIconFromPath("../images/quitb.png"));
        this.hpbar = new JButton(this.imageIconFromPath("../images/hpbar.png"));

        this.hpLabel = new JLabel("TEST");
        hpLabel.setBounds(400, 30, 100, 100);
        hpLabel.setForeground(Color.WHITE);
        hpLabel.setBackground(new Color(0, 0, 0, 0));
        hpLabel.setFont(new Font("",Font.PLAIN,25));

        this.init();
        this.menuInit();
        this.startButtonInit();
        this.quitButtonInit();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F)
                    baron.attack();
            }
        });
    }

    private ImageIcon imageIconFromPath(String path){
        return new ImageIcon(Main.class.getResource(path));
    }

    public void paint(Graphics g) {
        scrimg = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        scrgrp = (Graphics2D) scrimg.getGraphics();
        screenDraw(scrgrp);
        g.drawImage(scrimg, 0, 0, null);
    }

    private void screenDraw(Graphics2D g) {
        g.drawImage(introbg, 0, 0, null);
        paintComponents(g);
        this.repaint();
    }

    private void init() {

        this.setUndecorated(true);
        this.setTitle("Baron");
        this.setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void menuInit() {

        JMenuBar Menu =new JMenuBar();
        JMenu system = new JMenu("모름");
        JMenuItem exit = new JMenuItem("이것도 모름");
        system.add(exit);
        MainMenu listener = new MainMenu();
        exit.addActionListener(listener);
        Menu.add(system);
        setJMenuBar(Menu);
    }

    private void startButtonInit() {

        this.startb.setBounds(190, 400, 200, 95);
        this.startb.setBorderPainted(false);
        this.startb.setContentAreaFilled(false);
        this.startb.setFocusPainted(false);
        this.startb.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                baron.gameStart();
            }
        });
        this.add(startb);
    }

    private void quitButtonInit() {

        this.quitb.setBounds(410, 400, 200, 95);
        this.quitb.setBorderPainted(false);
        this.quitb.setContentAreaFilled(false);
        this.quitb.setFocusPainted(false);
        this.quitb.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        add(quitb);
    }

    private void changeScreen(String path){
        this.introbg = imageIconFromPath(path).getImage();
        this.repaint();
    }

    @Override
    public void onGameStart() {
        this.introbg = imageIconFromPath("../images/ingame.png").getImage();

        this.startb.setVisible(false);
        this.quitb.setVisible(false);

        this.hpbar.setBounds(100, 30, HP_BAR_SIZE, 30);

        this.hpbar.setBorderPainted(false);
        this.hpbar.setContentAreaFilled(false);
        this.hpbar.setFocusPainted(false);

        this.hpbar.setVisible(true);

        this.setFocusable(true);
        this.hpLabel.setText(String.valueOf(Baron.HP));

        this.add(hpbar);
        this.add(hpLabel);
    }

    @Override
    public void onGameOver(){

        gameEndLayoutInflation();
        changeScreen("../images/fail_game.png");
    }

    @Override
    public void onGameClear(){

        gameEndLayoutInflation();

        changeScreen("../images/success_game.png");
    }

    private void gameEndLayoutInflation() {
        this.quitb.setBounds(295, 400, 200, 95);
        this.quitb.setVisible(true);

        this.hpbar.setVisible(false);
        this.hpLabel.setVisible(false);
    }

    @Override
    public void onHpChange(int hp){
        int barSize = (int) (((double) hp) / Baron.HP * HP_BAR_SIZE);
        this.hpbar.setBounds(100, 30, barSize, 30);
        this.hpLabel.setText(String.valueOf(hp));
        this.repaint();
    }

    @Override
    public void onAttack() {

        changeScreen("../images/attack_game.png");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
