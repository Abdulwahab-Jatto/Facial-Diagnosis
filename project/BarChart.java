/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project;

/**
 *
 * @author User 2
 */
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart extends JFrame {
    
    public BarChart(Map map) {
        this.map = map;
        initUI();
        
    }
    static Map<String,Integer> map = new HashMap<>();
    private void initUI() {

        CategoryDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
          dataset.setValue(map.get("Red"), "Diagnosis", "Red");
          dataset.setValue(map.get("Green"), "Diagnosis", "Green");
          dataset.setValue(map.get("Blue"), "Diagnosis", "Blue");
          dataset.setValue(map.get("Yellow"), "Diagnosis", "Yellow");
          dataset.setValue(map.get("Black"), "Diagnosis", "Black");
          dataset.setValue(map.get("Normal"), "Diagnosis", "Normal");
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                "Facial Colour Diagnosis",
                "",
                "Facial Colour Percentage",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }

    public static void displayChart(Map m) {

        SwingUtilities.invokeLater(() -> {
            BarChart ex = new BarChart(m);
            ex.setVisible(true);
        });
    }

}