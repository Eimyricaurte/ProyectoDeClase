package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Sale;

public class ViewSales extends JFrame {
    public ViewSales(ArrayList<Sale> salesList) {
        setTitle("Resultados de Ventas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración del modelo de la tabla
        String[] columnNames = {"Código de Venta", "Producto", "Cantidad Vendida", "Precio Total", "Fecha"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Sale sale : salesList) {
            tableModel.addRow(new Object[]{
                sale.getCode(),
                sale.getProductName(),
                sale.getQuantity(),
                sale.getPrice(),
                sale.getDate()
            });
        }

        JTable salesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(salesTable);

        // Panel principal con la tabla
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Botón de aceptar
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.addActionListener(e -> dispose()); // Cierra la ventana al presionar

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(acceptButton);

        // Agregar componentes a la ventana
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
