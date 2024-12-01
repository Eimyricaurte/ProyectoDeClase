package logic;

import model.Sale;
import persistence.SalePersistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SalesAnalysis {

    private final ArrayList<Sale> sales;

    public SalesAnalysis() {
        SalePersistence salePersistence = new SalePersistence();
        this.sales = salePersistence.read();
    }

    public double calculateTotalEarnings() {
        double totalEarnings = 0;
        for (Sale sale : sales) {
            totalEarnings += sale.getPrice();
        }
        return totalEarnings;
    }

    public Map<String, Integer> calculateProductSales() {
        Map<String, Integer> productSales = new HashMap<>();
        for (Sale sale : sales) {
            productSales.put(sale.getProductName(),
                    productSales.getOrDefault(sale.getProductName(), 0) + sale.getQuantity());
        }
        return productSales;
    }

    public String getMostSoldProduct() {
        Map<String, Integer> productSales = calculateProductSales();
        return productSales.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay ventas");
    }

    public String getLeastSoldProduct() {
        Map<String, Integer> productSales = calculateProductSales();
        return productSales.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No hay ventas");
    }
}

