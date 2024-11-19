/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 *
 * @author huy
 */
public class ahh {
    private JPanel jPanel3;

    public ahh() {
        JFrame frame = new JFrame("Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        jPanel3 = new JPanel();
        jPanel3.setLayout(new java.awt.BorderLayout());
        frame.add(jPanel3);

        SetGraph();

        frame.setVisible(true);
    }

    public void SetGraph() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1, "Category 1", "Label 1");
        dataset.addValue(4, "Category 1", "Label 2");
        dataset.addValue(3, "Category 1", "Label 3");
        dataset.addValue(5, "Category 1", "Label 4");

        // Create a chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Sample Column Chart",
                "Category",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        // Create a ChartPanel and add it to jPanel3
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel3.removeAll();
        jPanel3.add(chartPanel);
        jPanel3.revalidate();
        jPanel3.repaint();

        JOptionPane.showMessageDialog(null, "ok");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ahh::new);
    }
}
