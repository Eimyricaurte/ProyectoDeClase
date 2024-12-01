package persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Sale;

public class SalePersistence {

    private static final String FILE_PATH = "sales.json";

    public void write(Sale sale) {
        JSONArray salesList = readJSONFile();
        JSONObject saleDetails = new JSONObject();
        saleDetails.put("code", sale.getCode());
        saleDetails.put("productName", sale.getProductName());
        saleDetails.put("quantity", sale.getQuantity());
        saleDetails.put("price", sale.getPrice());
        saleDetails.put("date", sale.getDate());

        salesList.put(saleDetails);
        writeJSONFile(salesList);
    }

    public ArrayList<Sale> read() {
        JSONArray salesList = readJSONFile();
        ArrayList<Sale> saleList = new ArrayList<>();

        for (int i = 0; i < salesList.length(); i++) {
            JSONObject saleJSON = salesList.getJSONObject(i);
            Sale sale = new Sale(
                saleJSON.getString("code"),
                saleJSON.getString("productName"),
                saleJSON.getInt("quantity"),
                saleJSON.getDouble("price"),
                saleJSON.getString("date")
            );
            saleList.add(sale);
        }
        return saleList;
    }

    public void update(Sale updatedSale) {
        JSONArray salesList = readJSONFile();

        for (int i = 0; i < salesList.length(); i++) {
            JSONObject saleJSON = salesList.getJSONObject(i);
            if (saleJSON.getString("code").equals(updatedSale.getCode())) {
                saleJSON.put("productName", updatedSale.getProductName());
                saleJSON.put("quantity", updatedSale.getQuantity());
                saleJSON.put("price", updatedSale.getPrice());
                saleJSON.put("date", updatedSale.getDate());
                break;
            }
        }
        writeJSONFile(salesList);
    }

    public void delete(String code) {
        JSONArray salesList = readJSONFile();
        boolean found = false; // Variable para verificar si se encontró la venta
    
        // Iterar sobre el JSONArray en orden inverso para evitar problemas de índice
        for (int i = salesList.length() - 1; i >= 0; i--) {
            JSONObject saleJSON = salesList.getJSONObject(i);
            if (saleJSON.getString("code").equals(code)) {
                salesList.remove(i); // Eliminar la venta
                found = true; // Marcar que se encontró y eliminó la venta
            }
        }
    
        if (found) {
            writeJSONFile(salesList); // Solo escribir si se realizó una eliminación
            System.out.println("Venta eliminada con éxito.");
        } else {
            System.out.println("Venta no encontrada.");
        }
    }

    private JSONArray readJSONFile() {
        JSONArray salesList = new JSONArray();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            StringBuilder content = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                content.append((char) i);
            }
            salesList = new JSONArray(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return salesList;
    }

    private void writeJSONFile(JSONArray salesList) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(salesList.toString(4)); // Formateado con sangría de 4 espacios.
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

