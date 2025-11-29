package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Set;

public class SimpleGUI extends JFrame {
    static Polygone currentPolygon = new Polygone();
    Canvas canvas = new Canvas();
    JCheckBox fillBox = new JCheckBox("Заливка");
    JRadioButton evenOdd = new JRadioButton("Even-Odd", true);
    JRadioButton nonZero = new JRadioButton("Non-Zero");

    public SimpleGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        Object constraints = BorderLayout.CENTER;
        add(canvas, constraints);

        JPanel top = new JPanel();
        top.add(new JButton("Очистить") {{
            addActionListener(e -> {
                currentPolygon.pts.clear();
                canvas.repaint();
            });
        }});
        top.add(new JButton("Инфо") {{
            addActionListener(e -> showInfo());
        }});
        top.add(fillBox);
        fillBox.addActionListener(e -> canvas.repaint());

        ButtonGroup bg = new ButtonGroup();
        bg.add(evenOdd);
        bg.add(nonZero);
        top.add(evenOdd);
        top.add(nonZero);
        evenOdd.addActionListener(e -> canvas.repaint());
        nonZero.addActionListener(e -> canvas.repaint());

        Object constraints2 = BorderLayout.NORTH;
        add(top, constraints2);
        setLocationRelativeTo(null);
    }

    void showInfo() {
        if (currentPolygon.pts.size() < 3) {
            JOptionPane.showMessageDialog(this, "Нужно минимум 3 точки");
            return;
        }
        String msg = "Вершин: " + currentPolygon.pts.size() + "\n";
        msg += currentPolygon.isSimple() ? "Простой (без самопересечений)" : "Сложный (самопересечения)";
        if (currentPolygon.isSimple()) {
            msg += "\n" + (currentPolygon.isConvex() ? "Выпуклый" : "Невыпуклый");
        }
        JOptionPane.showMessageDialog(this, msg, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    class Canvas extends JPanel {
        Canvas() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    currentPolygon.pts.add(e.getPoint());
                    repaint();
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (currentPolygon.pts.isEmpty()) return;

            if (fillBox.isSelected() && currentPolygon.pts.size() >= 3) {
                g.setColor(new Color(173, 216, 230));
                Set<Point> fill = evenOdd.isSelected() ? currentPolygon.evenOdd() : currentPolygon.nonZero();
                fill.forEach(p -> g.fillRect(p.x, p.y, 1, 1));
            }

            g.setColor(Color.BLACK);
            for (int i = 0; i < currentPolygon.pts.size(); i++) {
                Point p1 = currentPolygon.pts.get(i);
                Point p2 = currentPolygon.pts.get((i + 1) % currentPolygon.pts.size());
                Utils.line(g, p1.x, p1.y, p2.x, p2.y);
            }

            g.setColor(Color.RED);
            currentPolygon.pts.forEach(p -> g.fillOval(p.x - 3, p.y - 3, 6, 6));
        }
    }

    public static void main(String[] args) {
//        currentPolygon.pts.addAll(List.of(
//                new Point(100, 100),
//                new Point(600, 100),
//                new Point(300, 100)
//        ));
//
//        currentPolygon.pts.addAll(List.of(
//                new Point(100, 100),
//                new Point(600, 100),
//                new Point(600, 300),
//                new Point(600, 100),
//                new Point(300, 100)
//        ));
//        currentPolygon.pts.addAll(List.of(
//                new Point(100, 100),
//                new Point(200, 200),
//                new Point(300, 300)
//        ));
//
//        currentPolygon.pts.addAll(List.of(
//                new Point(100, 100),
//                new Point(600, 100),
//                new Point(600, 110),
//                new Point(590, 110),
//                new Point(590, 100),
//                new Point(300, 100)
//        ));

//        currentPolygon.pts.addAll(List.of(
//                new Point(100, 100),
//                new Point(101, 100),
//                new Point(101, 101),
//                new Point(100, 101)
//        ));

        SwingUtilities.invokeLater(() -> new SimpleGUI().setVisible(true));
    }
}
