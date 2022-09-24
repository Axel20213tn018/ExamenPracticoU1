package server;

import java.sql.Date;
import java.util.Random;

public class CURPGenerator {
    BeanPersona persona = new BeanPersona();
    DaoPersona dao = new DaoPersona();

    public String ALFANUMERICO = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String createCurp(String nombre1, String nombre2, String apellidoP, String apellidoM, String sexo, String estadoCodigo, String fechanac){
        persona.setNombre(""+nombre1+" "+nombre2);
        persona.setApellidoP(apellidoP);
        persona.setApellidoM(apellidoM);
        persona.setSexo(sexo.charAt(0));
        persona.setEstadoCodigo(estadoCodigo);
        persona.setFechanac(Date.valueOf(fechanac));

        char primerLetraAp = apellidoP.charAt(0);
        char primeraVocalA = '0';
        char primerLetraAm = apellidoM.charAt(0);
        char primerLetraNom = nombre1.charAt(0);
        String fechacurp = fechanac.substring(2, 4) + "" + fechanac.substring(5, 7) + "" + fechanac.substring(8, 10);
        char sigConsonanteAp = '0';
        char sigConsonanteAm = '0';
        char sigConsonanteNom = '0';
        
        //Busca la primera vocal del apellido paterno
        for (int i = 1; i < apellidoP.length(); i++) {
            char temp = apellidoP.toUpperCase().charAt(i);
            if (temp == 'A'){
                primeraVocalA = temp;
                break;
            }else if (temp == 'E'){
                primeraVocalA = temp;
                break;
            }else if (temp == 'I'){
                primeraVocalA = temp;
                break;
            }else if(temp == 'O'){
                primeraVocalA = temp;
                break;
            }else if(temp == 'U'){
                primeraVocalA = temp;
                break;
            }
        }
        
        for (int i = 1; i < apellidoP.length(); i++) {
            char temp = apellidoP.toUpperCase().charAt(i);
            
            if(esConsonante(temp)){
                if (temp == 'Ñ') {
                    sigConsonanteAp = 'X';
                }else {
                    sigConsonanteAp = temp;
                }
                break;
            }
        }

        for (int i = 1; i < apellidoM.length(); i++) {
            char temp = apellidoM.toUpperCase().charAt(i);

            if(esConsonante(temp)){
                if (temp == 'Ñ'){
                    sigConsonanteAm = 'X';
                }else {
                    sigConsonanteAm = temp;
                }
                break;
            }
        }

        for (int i = 1; i < nombre1.length(); i++) {
            char temp = nombre1.toUpperCase().charAt(i);

            if(esConsonante(temp)){
                sigConsonanteNom = temp;
                break;
            }
        }

        String curp = "" + primerLetraAp + primeraVocalA + primerLetraAm + primerLetraNom + fechacurp + sexo + estadoCodigo + sigConsonanteAp + sigConsonanteAm + sigConsonanteNom + homoclave();

        persona.setCurp(curp);


        dao.saveUser(persona);

        String respuesa = "Curp Generada con exito! Tu CURP es: " + curp +
                "\nDatos del dueño de la CURP:" +
                "\nNombre(S): " + persona.getNombre() +
                "\nApellido Paterno: " + persona.getApellidoP() +
                "\nApellido Materno: " + persona.getApellidoM() +
                "\nSexo: " + persona.getSexo() +
                "\nCodigo de estado: " + persona.getEstadoCodigo() +
                "\nFecha de nacimiento: " + persona.getFechanac();

        return respuesa;
    }

    public String buscarCurp(String curp){
        String respuesta = "";

        persona = dao.findPerson(curp);

        if (persona != null){
            respuesta = "¡CURP encontrada!\nDatos del dueño de la CURP:" +
            "\nNombre(S): " + persona.getNombre() +
                    "\nApellido Paterno: " + persona.getApellidoP() +
                    "\nApellido Materno: " + persona.getApellidoM() +
                    "\nSexo: " + persona.getSexo() +
                    "\nCodigo de estado: " + persona.getEstadoCodigo() +
                    "\nFecha de nacimiento: " + persona.getFechanac() +
                    "\nCURP: " + persona.getCurp();
        }else{
            respuesta = "Error! Curp no encontrada";
        }

        return respuesta;
    }

    public String homoclave(){
        String clave;
        Random r = new Random();
        int n = r.nextInt(35);
        char digito1 = ALFANUMERICO.charAt(n);

        n = r.nextInt(35);
        char digito2 = ALFANUMERICO.charAt(n);

        return digito1 + "" + digito2;
    }
    
    public boolean esConsonante(char letra){
        boolean comp = false;
        char[] consonantes = {
            'B', 'C', 'D',
            'F', 'G', 'H', 
            'J', 'K', 'L',
            'M', 'N', 'Ñ',
            'P', 'Q', 'R',
            'S', 'T', 'V',
            'W', 'X', 'Y',
            'Z'
        };
        
        for (int i = 0; i < 22; i++) {
            if (letra == consonantes[i]) {
                comp = true;
                break;
            }
        }
        return comp;
    }
}
