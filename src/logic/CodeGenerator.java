package logic;

import model.Product;
import java.util.List;

public class CodeGenerator {

    public String generateCode(String type, List<Product> inventoryList) {
        String prefix;

        switch (type) {
            case "Animales":
                prefix = "an";
                break;
            case "Muñecas":
                prefix = "mu";
                break;
            case "Accesorios":
                prefix = "ac";
                break;
            default:
                throw new IllegalArgumentException("Tipo desconocido: " + type);
        }

        int count = 1;
        String newCode;

        boolean exists;

        do {
            newCode = prefix + count;
            exists = false;

            for (Product product : inventoryList) {
                if (product.getCode().equals(newCode)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) break; // Salir del bucle si no hay coincidencia
            count++;
        } while (true);


        return newCode;
    }
}
