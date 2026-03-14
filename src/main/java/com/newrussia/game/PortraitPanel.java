package com.newrussia.game;

import javax.swing.*;
import java.awt.*;

public final class PortraitPanel extends JPanel {
    private String seed = "default";
    private int tick;

    public PortraitPanel() {
        Timer timer = new Timer(120, e -> {
            tick++;
            repaint();
        });
        timer.start();
        setPreferredSize(new Dimension(420, 420));
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int hash = Math.abs(seed.hashCode());
        Color base = new Color(80 + hash % 120, 80 + (hash / 3) % 120, 80 + (hash / 9) % 120);
        g2.setPaint(new GradientPaint(0, 0, base.darker(), getWidth(), getHeight(), base.brighter()));
        g2.fillRect(0, 0, getWidth(), getHeight());

        int bob = (int) (Math.sin(tick * 0.2) * 4);
        g2.setColor(new Color(25, 25, 25, 180));
        g2.fillOval(120, 95 + bob, 170, 220);

        g2.setColor(Color.WHITE);
        int blink = tick % 25 == 0 ? 1 : 8;
        g2.fillOval(165, 180 + bob, 24, blink);
        g2.fillOval(220, 180 + bob, 24, blink);

        g2.setColor(new Color(220, 100, 100));
        int mouth = 10 + Math.abs((tick % 16) - 8);
        g2.fillRoundRect(185, 245 + bob, 40, mouth / 2, 8, 8);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString("Talking Head: " + seed, 24, 32);
        g2.dispose();
    }
}
