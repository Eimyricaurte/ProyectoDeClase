package UI;

import javax.swing.*;

import logic.HandlingSales;
import model.Sale;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSale extends JFrame {
    private JTextField productCodeField;
    private JTextField quantityField;
    private HandlingSales handlingSales;

    public AddSale() {
        setTitle("Añadir venta");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        handlingSales = new HandlingSales();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Añadir venta");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel productCodeLabel = new JLabel("Código del producto");
        productCodeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        add(productCodeLabel, gbc);

        gbc.gridx = 1;
        productCodeField = new JTextField(15);
        productCodeField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        add(productCodeField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel quantityLabel = new JLabel("Cantidad vendida");
        quantityLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        add(quantityLabel, gbc);

        gbc.gridx = 1;
        quantityField = new JTextField(15);
        quantityField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        add(quantityField, gbc);

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
                String productCode = productCodeField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                handlingSales.processSale(productCode, quantity);

                JOptionPane.showMessageDialog(AddSale.this, "Venta procesada con éxito.");
                dispose();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    public Sale getNewSale() {
        return handlingSales.getProcessedSale();
    }
}
