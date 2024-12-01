package logic;

import persistence.Inventory;
import persistence.SalePersistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import UI.Principal;
import model.Product;
import model.Sale;

public class HandlingSales {
    private Sale processedSale;

    public void processSale(String productCode, int quantity) {
        Inventory inventory = new Inventory();
        ArrayList<Product> inventoryList = inventory.read();
        processedSale = null;
        boolean productFound = false;

        for (Product product : inventoryList) {
            if (product.getCode().equals(productCode)) {
                if (product.getStock() < quantity) {
                    System.out.println("Stock insuficiente para el producto: " + product.getName());
                    return;
                }

                // Crear la venta
                String name = product.getName();
                double total = product.getPrice() * quantity;
                String saleCode = generateSaleCode();
                String date = getCurrentDate();
                processedSale = new Sale(saleCode, name, quantity, total, date);

                // Actualizar el inventario
                updateProductStock(product, product.getStock() - quantity);

                // Guardar la venta
                SalePersistence salePersistence = new SalePersistence();
                salePersistence.write(processedSale); // Guardar la venta

                productFound = true;
                break;
            }
        }

        if (!productFound) {
            System.out.println("Producto no encontrado en el inventario");
        }
    }

    private void updateProductStock(Product product, int newStock) {
        product.setStock(newStock); // Actualizar el stock del producto en el objeto
        
        Inventory inventory = new Inventory();
        inventory.update(product); // Guardar los cambios en el inventario
    
        // Notificar a la interfaz gráfica para actualizar la tabla
        if (Principal.getInstance() != null) {
            Principal.getInstance().updateInventoryTableRow(product);
        }
    }


    public Sale getProcessedSale() {
        return processedSale;
    }

    public ArrayList<Sale> getAllSales() {
        SalePersistence salesPersistence = new SalePersistence();
        return salesPersistence.read();
    }

    private String generateSaleCode() {
        SalePersistence salePersistence = new SalePersistence();
        List<Sale> salesList = salePersistence.read();
        int nextSaleNumber = salesList.size() + 1; 

        return "SALE" + nextSaleNumber; 
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
