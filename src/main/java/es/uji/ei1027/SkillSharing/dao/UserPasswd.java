package es.uji.ei1027.SkillSharing.dao;

import java.io.*;
import java.util.Scanner;

public class UserPasswd { //Clase para acceder al fichero de usuarios
    private File fich;

    public UserPasswd() {
        this.fich = new File("src/main/java/es/uji/ei1027/SkillSharing/fichero.txt");
    }

    public boolean encontrarUser(String user) throws FileNotFoundException {
        Scanner lector = new Scanner(fich);
        while (lector.hasNextLine()) {
            String linea = lector.nextLine();
            String[] campos = linea.strip().split(" ");
            if (user.equals(campos[0]))
                return true;
        }
        return false;
    }

    public boolean comprobarPasswd(String password) throws FileNotFoundException {
        Scanner lector = new Scanner(fich);
        while (lector.hasNextLine()) {
            String linea = lector.nextLine();
            String[] campos = linea.strip().split(" ");
            if (password.equals(campos[1]))
                return true;
        }
        return false;
    }

    public void nuevoUser(String user, String password) throws IOException {
        try{
            FileWriter escritor = new FileWriter(fich);
            String id = contarUsuarios();
            String linea = user + " " + password + " " + id + "\n";
            escritor.write(linea);
            escritor.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String contarUsuarios() throws IOException {
        int lineas = 0;
        String id = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(fich))){
            while(reader.readLine()!= null)
                lineas ++;
            if(lineas < 9)
                id = "00" + lineas+1;
            else
            if(lineas > 9 && lineas < 99)
                id = "0" + lineas + 1;
            else
                id = Integer.toString(lineas);
        }catch (IOException e){
            e.printStackTrace();
        }return id;
    }
}
