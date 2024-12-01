package UI;

import javax.swing.*;

import logic.CodeGenerator;
import model.Product;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import persistence.Inventory;

public class AddProduct extends JFrame {
    private JComboBox<String> typeComboBox;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField stockField;
    private Product newProduct;

    public AddProduct() {
        setTitle("Añadir producto");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Añadir producto");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel typeLabel = new JLabel("Tipo");
        typeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        add(typeLabel, gbc);

        gbc.gridx = 1;
        typeComboBox = new JComboBox<>(new String[]{"Animales", "Muñecas", "Accesorios"});
        typeComboBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        add(typeComboBox, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel nameLabel = new JLabel("Nombre");
        nameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        add(nameField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel priceLabel = new JLabel("Precio");
        priceLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        add(priceLabel, gbc);

        gbc.gridx = 1;
        priceField = new JTextField(15);
        priceField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        add(priceField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel stockLabel = new JLabel("Stock");
        stockLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        add(stockLabel, gbc);

        gbc.gridx = 1;
        stockField = new JTextField(15);
        stockField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        add(stockField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        JButton cancelButton = new JButton("Cancelar");
        JButton saveButton = new JButton("Guardar");

        cancelButton.setBackground(new Color(190, 140, 255));
        cancelButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(190, 140, 255));
        saveButton.setForeground(Color.WHITE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener tipo seleccionado
                String selectedType = (String) typeComboBox.getSelectedItem();
                Inventory inventory = new Inventory();

                // Generar código único
                CodeGenerator codeGenerator = new CodeGenerator();
                String code = codeGenerator.generateCode(selectedType, inventory.read());

                // Crear el nuevo producto
                newProduct = new Product(
                    code,
                    nameField.getText(),
                    Integer.parseInt(stockField.getText()),
                    Integer.parseInt(priceField.getText())
                );

                // Guardar el producto
                inventory.write(newProduct);

                JOptionPane.showMessageDialog(AddProduct.this, "Producto guardado");
                dispose();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    public Product getNewProduct() {
        return newProduct;
    }
}

