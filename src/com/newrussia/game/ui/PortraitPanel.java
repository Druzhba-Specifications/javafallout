package com.newrussia.game.ui;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public final class PortraitPanel extends JPanel {
    private String seed = "default";
    private int tick;

    public PortraitPanel() {
        setPreferredSize(new Dimension(560, 560));
        setMinimumSize(new Dimension(420, 420));

        Timer timer = new Timer(100, e -> {
            tick++;
            repaint();
        });
        timer.start();
    }

    public void setSeed(String seed) {
        this.seed = seed == null ? "default" : seed;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        int hash = Math.abs(seed.hashCode());
        Color c1 = new Color(40 + hash % 160, 40 + (hash / 3) % 160, 40 + (hash / 9) % 160);
        Color c2 = c1.brighter();
        g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2));
        g2.fillRect(0, 0, getWidth(), getHeight());

        int bob = (int) (Math.sin(tick * 0.2) * 4);
        g2.setColor(new Color(0, 0, 0, 155));
        g2.fillOval(getWidth() / 2 - 110, 130 + bob, 220, 300);

        int eyeBlink = tick % 20 == 0 ? 2 : 12;
        g2.setColor(Color.WHITE);
        g2.fillOval(getWidth() / 2 - 55, 230 + bob, 30, eyeBlink);
        g2.fillOval(getWidth() / 2 + 25, 230 + bob, 30, eyeBlink);

        int mouthHeight = 8 + Math.abs((tick % 14) - 7);
        g2.setColor(new Color(220, 95, 95));
        g2.fillRoundRect(getWidth() / 2 - 30, 315 + bob, 60, mouthHeight, 10, 10);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        g2.drawString("Talking Head: " + seed, 20, 26);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g2.drawString("Voice animation placeholder + portrait seed renderer", 20, 46);

        g2.dispose();
    }
}
