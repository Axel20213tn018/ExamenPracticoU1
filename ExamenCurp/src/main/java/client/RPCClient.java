package client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import server.BeanPersona;
import server.CURPGenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.Scanner;

public class RPCClient {
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        Scanner input = new Scanner(System.in);
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        Object[] persona = new Object[7];
        int opc;
        int opc2;
        String fechaTemp;
        boolean confirm;
        do {
            System.out.println("===========GENERADOR DE CURPs============");
            System.out.println("Menu:");
            System.out.println("1.- Generar CURP");
            System.out.println("2.- Consultar Datos de CURP existente");
            System.out.println("3.- Salir");
            opc = input.nextInt();
            switch (opc){
                case 1:
                    System.out.println("¿Cuenta con segundo nombre? (S/N)");
                    confirm = input.next().equalsIgnoreCase("S");

                    if (confirm){
                        System.out.println("Escriba su primer nombre: ");
                        persona[0] = input.next();
                        System.out.println("Escriba su segundo nombre: ");
                        persona[1] = input.next();
                    }else {
                        System.out.println("Escriba su nombre: ");
                        persona[0] = input.next();
                        persona[1] = "";
                    }
                    System.out.println("Escriba tu apellido paterno: ");
                    persona[2] = input.next();
                    System.out.println("Escribe tu apellido materno");
                    persona[3] = input.next();
                    do {
                        System.out.println("¿Cual es su sexo? \n1.- Hombre \n2.- Mujer");
                        opc2 = input.nextInt();
                        switch (opc2){
                            case 1:
                                persona[4] = "H";
                                break;
                            case 2:
                                persona[4] = "M";
                                break;
                            default:
                                System.out.println("Opcion Incorrecta");
                                break;
                        }
                    }while (opc2 != 1 && opc2 != 2);

                    do {
                        System.out.println("Escribe el codigo de tu estado de nacimiento (Ejemp. Morelos = MS) \nNota: Si naciste en el extranjero usa 'NE'");
                        String code = input.next();
                        confirm = stateCodeExist(code);
                        if (confirm){
                            persona[5] = code;
                        }else {
                            System.out.println("¡Error! ese codigo de estado no existe, intente nuevamente");
                        }
                    }while (!confirm);

                    System.out.println("Escriba su fecha de nacimiento (Formato AAAA-MM-DD)");
                    persona[6] = input.next();

                    String response = (String) client.execute("CURPGenerator.createCurp", persona);

                    System.out.println(response);
                    break;
                case 2:
                    System.out.println("Escribe una CURP: ");
                    String curp = input.next();
                    Object[] curpR = {curp};

                    String response2 = (String) client.execute("CURPGenerator.buscarCurp", curpR);
                    System.out.println(response2);
                    break;
                case 3:
                    System.out.println("Adios...");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }while (opc != 3);
    }

    public static boolean stateCodeExist(String estadoCodigo){
        String[] codigos = {
                "AS", "BC",
                "BS", "CC",
                "CL", "CM",
                "CS", "CH",
                "DF", "DG",
                "GT", "GR",
                "HG", "JC",
                "MC", "MN",
                "MS", "NT",
                "NL", "OC",
                "PL", "QT",
                "QR", "SP",
                "SL", "SR",
                "TC", "TS",
                "TL", "VZ",
                "YN", "ZS",
                "NE"
        };

        for (int i = 0; i < 33; i++) {
            if (estadoCodigo.equals(codigos[i])){
                return true;
            }
        }
        return false;
    }
}
