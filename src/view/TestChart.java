package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class TestChart {
    public static void main(String[] args) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1, "Visiteurs", "Lundi");
        dataset.addValue(3, "Visiteurs", "Mardi");
        dataset.addValue(2, "Visiteurs", "Mercredi");

        JFreeChart chart = ChartFactory.createBarChart(
                "Visiteurs par jour", "Jour", "Nombre", dataset
        );

        JFrame frame = new JFrame("Test JFreeChart");
        frame.setContentPane(new ChartPanel(chart));
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
