package view;

// --- ReportingView.java ---
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ReportingView extends JFrame {
    public ReportingView(Map<String, Integer> pieData, Map<String, Float> revenueData) {
        setTitle("Dashboard Reporting");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new NavigationBar("Statistiques"), BorderLayout.NORTH);


        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Répartition paiements", createPieChartPanel(pieData));
        tabs.add("Revenus par attraction", createBarChartPanel(revenueData));

        add(tabs, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createPieChartPanel(Map<String, Integer> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Commandes payées vs en attente",
                dataset,
                true,
                true,
                false
        );

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBarChartPanel(Map<String, Float> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Float> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Chiffre d'affaires", String.valueOf(entry.getKey()));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Chiffre d'affaires par attraction",
                "Attraction ID",
                "Revenu (€)",
                dataset
        );


        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
        return panel;
    }
}
