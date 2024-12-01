package persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Product;

public class Inventory {

    private static final String FILE_PATH = "inventory.json";

    public void write(Product product) {
        JSONArray inventoryList = readJSONFile();
        JSONObject productDetails = new JSONObject();
        productDetails.put("code", product.getCode());
        productDetails.put("name", product.getName());
        productDetails.put("stock", product.getStock());
        productDetails.put("price", product.getPrice());

        inventoryList.put(productDetails);
        writeJSONFile(inventoryList);
    }

    public ArrayList<Product> read() {
        JSONArray inventoryList = readJSONFile();
        ArrayList<Product> productList = new ArrayList<>();

        for (int i = 0; i < inventoryList.length(); i++) {
            JSONObject productJSON = inventoryList.getJSONObject(i);
            Product product = new Product(
                productJSON.getString("code"),
                productJSON.getString("name"),
                productJSON.getInt("stock"),
                productJSON.getInt("price")
            );
            productList.add(product);
        }
        return productList;
    }

    public void update(Product updatedProduct) {
        JSONArray inventoryList = readJSONFile();

        for (int i = 0; i < inventoryList.length(); i++) {
            JSONObject productJSON = inventoryList.getJSONObject(i);
            if (productJSON.getString("code").equals(updatedProduct.getCode())) {
                productJSON.put("name", updatedProduct.getName());
                productJSON.put("stock", updatedProduct.getStock());
                productJSON.put("price", updatedProduct.getPrice());
                break;
            }
        }
        writeJSONFile(inventoryList);
    }

    public void delete(String code) {
        JSONArray inventoryList = readJSONFile();
        boolean found = false; // Variable para verificar si se encontró el producto
    
        // Iterar sobre el JSONArray en orden inverso para evitar problemas de índice
        for (int i = inventoryList.length() - 1; i >= 0; i--) {
            JSONObject productJSON = inventoryList.getJSONObject(i);
            if (productJSON.getString("code").equals(code)) {
                inventoryList.remove(i); // Eliminar el producto
                found = true; // Marcar que se encontró y eliminó el producto
            }
        }
    
        if (found) {
            writeJSONFile(inventoryList); // Solo escribir si se realizó una eliminación
            System.out.println("Producto eliminado con éxito.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }
    

    private JSONArray readJSONFile() {
        JSONArray inventoryList = new JSONArray();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            StringBuilder content = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                content.append((char) i);
            }
            inventoryList = new JSONArray(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inventoryList;
    }

    private void writeJSONFile(JSONArray inventoryList) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(inventoryList.toString(4)); 
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
