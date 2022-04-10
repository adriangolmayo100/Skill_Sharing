package es.uji.ei1027.SkillSharing.dao;

import java.io.*;
import java.util.Scanner;

public class UserPasswd {
    private File fich;

    public UserPasswd() {
        this.fich = new File("fichero.txt");
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
            String linea = user + " " + password + "\n";
            escritor.write(linea);
            escritor.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
