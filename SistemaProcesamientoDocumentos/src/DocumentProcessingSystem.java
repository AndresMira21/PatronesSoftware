import java.util.Scanner;

interface Document {
    void process(String format, String country);
}

class ElectronicInvoice implements Document {
    @Override
    public void process(String format, String country) {
        System.out.println("Procesando Factura Electrónica en formato " + format + " para " + country);
    }
}

class LegalContract implements Document {
    @Override
    public void process(String format, String country) {
        System.out.println("Procesando Contrato Legal en formato " + format + " para " + country);
    }
}

class FinancialReport implements Document {
    @Override
    public void process(String format, String country) {
        System.out.println("Procesando Reporte Financiero en formato " + format + " para " + country);
    }
}

class DigitalCertificate implements Document {
    @Override
    public void process(String format, String country) {
        System.out.println("Procesando Certificado Digital en formato " + format + " para " + country);
    }
}

class TaxDeclaration implements Document {
    @Override
    public void process(String format, String country) {
        System.out.println("Procesando Declaración Tributaria en formato " + format + " para " + country);
    }
}

class DocumentFactory {

    public Document createDocument(String type) {
        if (type.equals("factura")) {
            return new ElectronicInvoice();
        } else if (type.equals("contrato")) {
            return new LegalContract();
        } else if (type.equals("reporte")) {
            return new FinancialReport();
        } else if (type.equals("certificado")) {
            return new DigitalCertificate();
        } else if (type.equals("declaracion")) {
            return new TaxDeclaration();
        } else {
            System.out.println("Tipo no válido, creando factura por defecto");
            return new ElectronicInvoice();
        }
    }
}

public class DocumentProcessingSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DocumentFactory factory = new DocumentFactory();

        // Arrays simples con las opciones
        String[] paises = {"Colombia", "México", "Argentina", "Chile"};
        String[] tipos = {"factura", "contrato", "reporte", "certificado", "declaracion"};
        String[] formatos = {"pdf", "doc", "docx", "txt", "csv", "xlsx"};

        System.out.println("=== SISTEMA DE DOCUMENTOS ===");
        System.out.println("GlobalDocs Solutions\n");

        // Menú simple
        System.out.println("¿Qué quieres hacer?");
        System.out.println("1. Procesar un documento");
        System.out.println("2. Procesar varios documentos");
        System.out.print("Elige 1 o 2: ");

        int opcion = sc.nextInt();

        if (opcion == 1) {
            procesarUnDocumento(sc, factory, paises, tipos, formatos);

        } else if (opcion == 2) {
            System.out.print("¿Cuántos documentos? ");
            int cantidad = sc.nextInt();

            for (int i = 1; i <= cantidad; i++) {
                System.out.println("\n--- Documento " + i + " ---");
                procesarUnDocumento(sc, factory, paises, tipos, formatos);
            }

        } else {
            System.out.println("Opción no válida");
        }

        sc.close();
    }

    public static void procesarUnDocumento(Scanner sc, DocumentFactory factory,
                                           String[] paises, String[] tipos, String[] formatos) {

        System.out.println("\nSelecciona el país:");
        for (int i = 0; i < paises.length; i++) {
            System.out.println((i + 1) + ". " + paises[i]);
        }
        System.out.print("Tu opción: ");
        int paisOpcion = sc.nextInt();
        String pais = paises[paisOpcion - 1];

        System.out.println("\nSelecciona el tipo de documento:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
        System.out.print("Tu opción: ");
        int tipoOpcion = sc.nextInt();
        String tipo = tipos[tipoOpcion - 1];

        System.out.println("\nSelecciona el formato:");
        for (int i = 0; i < formatos.length; i++) {
            System.out.println((i + 1) + ". " + formatos[i]);
        }
        System.out.print("Tu opción: ");
        int formatoOpcion = sc.nextInt();
        String formato = formatos[formatoOpcion - 1];

        Document documento = factory.createDocument(tipo);
        documento.process(formato, pais);

        System.out.println("Creado correctamente");
    }
}