package UI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import logic.HandlingSales;
import logic.SalesAnalysis;
import model.Product;
import model.Sale;
import persistence.Inventory;
import persistence.SalePersistence;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;


public class Principal extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DefaultTableModel inventoryTableModel;
    private DefaultTableModel salesTableModel;
    private JTable inventoryTable;
    private JTable salesTable;
    private static Principal instance;

public static Principal getInstance() {
    return instance;
}

    public Principal() {
        instance = this;
        setTitle("Welcome");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel, BorderLayout.CENTER);

        JPanel welcomePanel = createWelcomePanel();
        cardPanel.add(welcomePanel, "WelcomePanel");

        inventoryTableModel = new DefaultTableModel(new String[]{"Código", "Nombre", "Stock", "Precio", "Editar", "Eliminar"}, 0);
        JPanel inventoryPanel = createInventoryPanel();
        cardPanel.add(inventoryPanel, "InventoryPanel");

        salesTableModel = new DefaultTableModel(new String[]{"Código de Venta", "Producto", "Cantidad Vendida", "Precio Total", "Fecha de la Venta", "Eliminar"}, 0);
        JPanel salesPanel = createSalesPanel();
        cardPanel.add(salesPanel, "SalesPanel");

        JPanel reportPanel = createReportPanel();
        cardPanel.add(reportPanel, "ReportPanel");

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(200, 160, 255));
        sidePanel.setLayout(new GridLayout(4, 1, 15, 15));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton inventoryButton = createRoundedButton("Inventario");
        JButton salesButton = createRoundedButton("Ventas");
        JButton reportButton = createRoundedButton("Reporte");
        JButton exitButton = createRoundedButton("Salir");

        inventoryButton.addActionListener(e -> cardLayout.show(cardPanel, "InventoryPanel"));
        salesButton.addActionListener(e -> cardLayout.show(cardPanel, "SalesPanel"));
        reportButton.addActionListener(e -> cardLayout.show(cardPanel, "ReportPanel"));
        exitButton.addActionListener(e -> System.exit(0));

        sidePanel.add(inventoryButton);
        sidePanel.add(salesButton);
        sidePanel.add(reportButton);
        sidePanel.add(exitButton);

        add(sidePanel, BorderLayout.WEST);

        loadInventoryData();
        loadSalesData();
    }

    private void loadInventoryData() {
        Inventory inventory = new Inventory();
        ArrayList<Product> inventarioList = inventory.read();
        for (Product product : inventarioList) {
            inventoryTableModel.addRow(new Object[]{
                product.getCode(),
                product.getName(),
                product.getStock(),
                product.getPrice(),
                "Editar",
                "Eliminar"
            });
        }
    
        
    }

    public void updateInventoryTableRow(Product updatedProduct) {
        for (int i = 0; i < inventoryTableModel.getRowCount(); i++) {
            String code = inventoryTableModel.getValueAt(i, 0).toString();
            if (code.equals(updatedProduct.getCode())) {
                inventoryTableModel.setValueAt(updatedProduct.getStock(), i, 2); // Actualiza el stock
                break;
            }
        }
    }

    private void loadSalesData() {
        HandlingSales handlingSales = new HandlingSales();
        ArrayList<Sale> salesList = handlingSales.getAllSales();
        for (Sale sale : salesList) {
            salesTableModel.addRow(new Object[]{
                sale.getCode(),
                sale.getProductName(),
                sale.getQuantity(),
                sale.getPrice(),
                sale.getDate(),
                "Eliminar"
            });
        }
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(Color.WHITE);
    
        // Etiqueta de bienvenida
        JLabel welcomeLabel = new JLabel("Bienvenida", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(80, 80, 80));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Etiqueta para la imagen
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Ruta de la imagen
        File imgFile = new File("C:/Users/ricau/OneDrive/Desktop/Proyecto Progra/recursos/principal.jpg");
    
        if (imgFile.exists()) { // Validar si el archivo existe
            ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(300, 350, Image.SCALE_SMOOTH); // Redimensionar la imagen
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            // Mostrar un mensaje si la imagen no se encuentra
            imageLabel.setText("Imagen no disponible");
            imageLabel.setFont(new Font("Arial", Font.ITALIC, 18));
            imageLabel.setForeground(Color.RED);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
    
        // Añadir los componentes al panel
        welcomePanel.add(Box.createVerticalStrut(20)); // Espacio arriba
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createVerticalStrut(20)); // Espacio entre etiqueta y la imagen
        welcomePanel.add(imageLabel);
    
        return welcomePanel;
    }
    

    private JPanel createInventoryPanel() {
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(Color.WHITE);
    
        JLabel inventoryLabel = new JLabel("Inventario", SwingConstants.CENTER);
        inventoryLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        inventoryLabel.setForeground(new Color(80, 80, 80));
        inventoryPanel.add(inventoryLabel, BorderLayout.NORTH);
    
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JPanel searchAndButtonsPanel = new JPanel(new BorderLayout(10, 0));
        searchAndButtonsPanel.setBackground(Color.WHITE);
    
        JTextField searchField = new JTextField(20);
        JButton searchButton = createRoundedButton("Buscar");
        JButton addButton = createRoundedButton("Añadir Producto");
    
        searchButton.setBackground(new Color(160, 110, 255));
        searchButton.setForeground(Color.WHITE);
    
        addButton.setBackground(new Color(160, 110, 255));
        addButton.setForeground(Color.WHITE);
    
        searchAndButtonsPanel.add(searchField, BorderLayout.CENTER);
        searchAndButtonsPanel.add(searchButton, BorderLayout.EAST);
        searchAndButtonsPanel.add(addButton, BorderLayout.WEST);
    
        addButton.addActionListener(e -> {
            AddProduct addProductFrame = new AddProduct();
            addProductFrame.setVisible(true);
    
            addProductFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    Product newProduct = addProductFrame.getNewProduct();
                    if (newProduct != null) {
                        inventoryTableModel.addRow(new Object[]{
                            newProduct.getCode(),
                            newProduct.getName(),
                            newProduct.getStock(),
                            newProduct.getPrice(),
                            "Editar",
                            "Eliminar"
                        });
                    }
                }
            });
        });
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            if (!searchTerm.isEmpty()) {
                boolean found = false;
                for (int i = 0; i < inventoryTableModel.getRowCount(); i++) {
                    String code = inventoryTableModel.getValueAt(i, 0).toString();
                    String name = inventoryTableModel.getValueAt(i, 1).toString();
                    if (code.equalsIgnoreCase(searchTerm) || name.equalsIgnoreCase(searchTerm)) {
                        String productCode = inventoryTableModel.getValueAt(i, 0).toString();
                        String productName = inventoryTableModel.getValueAt(i, 1).toString();
                        int productStock = Integer.parseInt(inventoryTableModel.getValueAt(i, 2).toString());
                        int productPrice = Integer.parseInt(inventoryTableModel.getValueAt(i, 3).toString());
                        
                        Product product = new Product(productCode, productName, productStock, productPrice);
                        ViewProduct viewProductFrame = new ViewProduct(product); // Ventana para ver detalles
                        viewProductFrame.setVisible(true);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un término de búsqueda.", "Búsqueda", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        topPanel.add(searchAndButtonsPanel);
        inventoryPanel.add(topPanel, BorderLayout.NORTH);
    
        inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        inventoryTable.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), inventoryTable));
        inventoryTable.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        inventoryTable.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), inventoryTable));
    
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        inventoryPanel.add(scrollPane, BorderLayout.CENTER);
    
        return inventoryPanel;
    }
    
    

    private JPanel createSalesPanel() {
        JPanel salesPanel = new JPanel(new BorderLayout());
        salesPanel.setBackground(Color.WHITE);
    
        JLabel salesLabel = new JLabel("Ventas", SwingConstants.CENTER);
        salesLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        salesLabel.setForeground(new Color(80, 80, 80));
        salesPanel.add(salesLabel, BorderLayout.NORTH);
    
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JPanel searchAndButtonsPanel = new JPanel(new BorderLayout(10, 0));
        searchAndButtonsPanel.setBackground(Color.WHITE);
    
        JTextField searchField = new JTextField(20);
        JButton searchButton = createRoundedButton("Buscar");
        JButton addButton = createRoundedButton("Añadir Venta");
    
        searchButton.setBackground(new Color(160, 110, 255));
        searchButton.setForeground(Color.WHITE);
    
        addButton.setBackground(new Color(160, 110, 255));
        addButton.setForeground(Color.WHITE);
    
        searchAndButtonsPanel.add(searchField, BorderLayout.CENTER);
        searchAndButtonsPanel.add(searchButton, BorderLayout.EAST);
        searchAndButtonsPanel.add(addButton, BorderLayout.WEST);
    
        addButton.addActionListener(e -> {
            AddSale addSaleFrame = new AddSale();
            addSaleFrame.setVisible(true);
    
            addSaleFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    Sale newSale = addSaleFrame.getNewSale();
                    if (newSale != null) {
                        salesTableModel.addRow(new Object[]{
                            newSale.getCode(),
                            newSale.getProductName(),
                            newSale.getQuantity(),
                            newSale.getPrice(),
                            newSale.getDate(),
                            "Eliminar"
                        });
                    }
                }
            });
        });

        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            if (!searchTerm.isEmpty()) {
                HandlingSales handlingSales = new HandlingSales();
                ArrayList<Sale> salesList = handlingSales.getAllSales();
                ArrayList<Sale> filteredSales = new ArrayList<>();
                
                // Filtra las ventas que coincidan con el término de búsqueda
                for (Sale sale : salesList) {
                    if (sale.getCode().equalsIgnoreCase(searchTerm) || 
                        sale.getProductName().equalsIgnoreCase(searchTerm)) {
                        filteredSales.add(sale);
                    }
                }
                
                if (!filteredSales.isEmpty()) {
                    ViewSales viewSalesFrame = new ViewSales(filteredSales); // Ventana con tabla de resultados
                    viewSalesFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron ventas con ese término.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un término de búsqueda.", "Búsqueda", JOptionPane.WARNING_MESSAGE);
            }
        });
        
    
        topPanel.add(searchAndButtonsPanel);
        salesPanel.add(topPanel, BorderLayout.NORTH);
    
        salesTable = new JTable(salesTableModel);
        salesTable.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        salesTable.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), salesTable));
    
        JScrollPane scrollPane = new JScrollPane(salesTable);
        salesPanel.add(scrollPane, BorderLayout.CENTER);
    
        return salesPanel;
    }
    

    private JPanel createReportPanel() {
        // Panel principal del reporte
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBackground(Color.WHITE);
        reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen de 20px en todos los lados
    
        JLabel reportLabel = new JLabel("Reporte de Ventas", SwingConstants.CENTER);
        reportLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        reportLabel.setForeground(new Color(80, 80, 80));
        reportPanel.add(reportLabel, BorderLayout.NORTH);
    
        // Panel interno para el contenido del reporte
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
    
        // Cargar análisis de ventas
        SalesAnalysis salesAnalysis = new SalesAnalysis();
        double totalEarnings = salesAnalysis.calculateTotalEarnings();
        String mostSoldProduct = salesAnalysis.getMostSoldProduct();
        String leastSoldProduct = salesAnalysis.getLeastSoldProduct();
    
        JLabel earningsLabel = new JLabel("Ganancias Totales: $" + totalEarnings);
        JLabel mostSoldLabel = new JLabel("Producto Más Vendido: " + mostSoldProduct);
        JLabel leastSoldLabel = new JLabel("Producto Menos Vendido: " + leastSoldProduct);
    
        earningsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        mostSoldLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        leastSoldLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
    
        contentPanel.add(earningsLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(mostSoldLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(leastSoldLabel);
    
        // Gráfico de ventas
        Map<String, Integer> productSales = salesAnalysis.calculateProductSales();
        JPanel chartPanel = createSalesChart(productSales);
        contentPanel.add(Box.createVerticalStrut(20)); // Espaciado
        contentPanel.add(chartPanel);
    
        // Agregar barra de desplazamiento
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Incremento de desplazamiento suave
    
        reportPanel.add(scrollPane, BorderLayout.CENTER);
    
        return reportPanel;
    }
    

    private JPanel createSalesChart(Map<String, Integer> productSales) {
        // Crear el dataset para el gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : productSales.entrySet()) {
            dataset.addValue(entry.getValue(), "Cantidad Vendida", entry.getKey());
        }

        // Crear el gráfico de barras
        JFreeChart barChart = ChartFactory.createBarChart(
                "Productos Vendidos",
                "Producto",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        // Personalizar colores de las barras
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(200, 160, 230)); // Morado claro

        // Personalizar el fondo del gráfico
        plot.setBackgroundPaint(Color.WHITE); // Fondo blanco
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Ajustar el tamaño del panel
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(480, 240)); // Tamaño más pequeño
        return chartPanel;
}



    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                super.paintComponent(g);
            }

            @Override
            public void setContentAreaFilled(boolean b) {
                // Do nothing
            }
        };
        button.setFocusPainted(false);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(160, 110, 255));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Principal principalFrame = new Principal();
            principalFrame.setVisible(true);
        });
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                int row = table.convertRowIndexToModel(table.getEditingRow());
                try{
                    if (label.equals("Eliminar")) {
                        if (table == inventoryTable) {
                            String code = inventoryTableModel.getValueAt(row, 0).toString();
                            new Inventory().delete(code);
                            reloadInventoryTable(); // Recarga la tabla después de eliminar
                            JOptionPane.showMessageDialog(null, "Producto eliminado con éxito.");
                        } else if (table == salesTable) {
                            String saleCode = salesTableModel.getValueAt(row, 0).toString();
                            new SalePersistence().delete(saleCode);
                            reloadSalesTable(); // Recarga la tabla después de eliminar
                            JOptionPane.showMessageDialog(null, "Venta eliminada con éxito.");
                        }
                        // No se accede a la fila después de eliminar, así que no hay riesgo de ArrayIndexOutOfBoundsException
                    } else if (label.equals("Editar") && table == inventoryTable) {
                        String code = inventoryTableModel.getValueAt(row, 0).toString();
                        String name = inventoryTableModel.getValueAt(row, 1).toString();
                        int stock = Integer.parseInt(inventoryTableModel.getValueAt(row, 2).toString());
                        int price = Integer.parseInt(inventoryTableModel.getValueAt(row, 3).toString());
            
                        Product product = new Product(code, name, stock, price);
                        EditProduct editProductFrame = new EditProduct(product);
                        editProductFrame.setVisible(true);
            
                        editProductFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                                Product updatedProduct = editProductFrame.getUpdatedProduct();
                                if (updatedProduct != null) {
                                    new Inventory().update(updatedProduct);
                                    JOptionPane.showMessageDialog(null, "Producto actualizado con éxito.");
                                    reloadInventoryTable();
                                }
                            }
                        });
                    }
                }
                catch(ArrayIndexOutOfBoundsException e){
                    JOptionPane.showMessageDialog(null, "Error: Intento de acceder a un índice inválido.");
                }
            }
            isPushed = false;
            return label;
        }
        
        private void reloadInventoryTable() {
            inventoryTableModel.setRowCount(0);
            loadInventoryData();
        }
        private void reloadSalesTable() {
            salesTableModel.setRowCount(0);
            loadSalesData();
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
