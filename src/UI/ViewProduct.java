package UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Product;

public class ViewProduct extends JFrame {
    public ViewProduct(Product product) {
        setTitle("Detalles del Producto");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal para mostrar los detalles del producto
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Código:"));
        panel.add(new JLabel(product.getCode()));

        panel.add(new JLabel("Nombre:"));
        panel.add(new JLabel(product.getName()));

        panel.add(new JLabel("Stock:"));
        panel.add(new JLabel(String.valueOf(product.getStock())));

        panel.add(new JLabel("Precio:"));
        panel.add(new JLabel(String.valueOf(product.getPrice())));

        // Botón "Aceptar" para cerrar la ventana
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.addActionListener(e -> dispose()); // Cierra la ventana al presionar

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(acceptButton);

        // Agregar los paneles a la ventana
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
