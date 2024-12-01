package UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import model.Product;
import persistence.Inventory;

public class EditProduct extends JFrame {

    private Product product;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField stockField;

    public EditProduct(Product product) {
        this.product = product;
        setTitle("Editar producto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Editar producto");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Nombre
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel nameLabel = new JLabel("Nombre");
        nameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        nameField.setText(product.getName());  // Asignar el valor del producto
        add(nameField, gbc);

        // Precio
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel priceLabel = new JLabel("Precio");
        priceLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        add(priceLabel, gbc);

        gbc.gridx = 1;
        priceField = new JTextField(15);
        priceField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        priceField.setText(String.valueOf(product.getPrice()));  // Asignar el valor del precio
        add(priceField, gbc);

        // Stock
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel stockLabel = new JLabel("Stock");
        stockLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        add(stockLabel, gbc);

        gbc.gridx = 1;
        stockField = new JTextField(15);
        stockField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        stockField.setText(String.valueOf(product.getStock()));  // Asignar el valor del stock
        add(stockField, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        JButton cancelButton = new JButton("Cancelar");
        JButton saveButton = new JButton("Guardar");

        cancelButton.setBackground(new Color(190, 140, 255)); 
        cancelButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(190, 140, 255));  
        saveButton.setForeground(Color.WHITE);

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            // Actualizamos el producto con los nuevos valores
            product.setName(nameField.getText());
            product.setPrice(Integer.parseInt(priceField.getText()));
            product.setStock(Integer.parseInt(stockField.getText()));

            // Llamar al método para guardar el producto actualizado en el archivo
            Inventory inventory = new Inventory();
            inventory.update(product);

            JOptionPane.showMessageDialog(EditProduct.this, "Producto actualizado");
            dispose();  // Cerrar el frame
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }
    public Product getUpdatedProduct() {
        return product;
    }
}
