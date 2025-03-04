/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *  
 * @author danilos
 */
public class PruebaDeArchivos {
    private File mfile=null;
    Scanner sc=new Scanner(System.in);

    
    void setFile(String direccion){
       mfile=new File(direccion);
     }
    
 
    boolean crearFIle()throws IOException{
    return mfile.createNewFile();
    }
    boolean crearFolder(){
    return mfile.mkdirs();
    }
    boolean borrar(){
    return mfile.delete();
    }
    
    public static void login(){
    Scanner lea = new Scanner(System.in);
        PruebaDeArchivos mf  = new PruebaDeArchivos();
         System.out.println("Ingrese su nombre de usuario");
                        String nombre2=lea.next();
                        System.out.println("Ingrese su contrasena");
                        String contrasena2=lea.next();
                        try {
                            File archivo2 = new File(nombre2 + "/datos.bin");
                            java.io.DataInputStream entrada = new java.io.DataInputStream(new java.io.FileInputStream(archivo2));
                            String nombreArchivo = entrada.readUTF();
                            String contrasenaArchivo = entrada.readUTF();
                            entrada.close();
                            
                            if(nombre2.equals(nombreArchivo) && contrasena2.equals(contrasenaArchivo)) {
                                System.out.println("Inicio de sesion exitoso");
                                Menu();
                                return;

                            } else {
                                System.out.println("Usuario o contraseña incorrectos");
                                menup();
                                return;
                            }
                        } catch(IOException e) {
                            System.out.println("Error usuario no encontrado ");
                            menup();
                            return;
                        }
    }
    
    public static void crear(){
     Scanner lea = new Scanner(System.in);
        PruebaDeArchivos mf  = new PruebaDeArchivos();
    
    System.out.println("Ingrese su nombre de usuario");
                        String nombre=lea.next();  
                        System.out.println("Ingrese una contrasena");               
                        String contrasena=lea.next(); 
                        mf.setFile(nombre);
                        try{
                            if(mf.crearFolder()){
                                try {
                                    File archivo = new File(nombre + "/datos.bin");
                                    java.io.DataOutputStream salida = new java.io.DataOutputStream(new java.io.FileOutputStream(archivo));
                                    salida.writeUTF(nombre);
                                    salida.writeUTF(contrasena);
                                    salida.close();
                                    Menu();

                                return;
                                } catch (IOException e) {
                                    System.out.println("Error al guardar los datos: " + e.getMessage());
                                    menup();
                                    return;
                                }
                                
                            }
                        } catch(Exception e) {
                            System.out.println("Error: " + e.getMessage());
                             menup();
                            return;
                        }
                    }
    public static void main(String[] args)  {
        menup();
            }
    public static void Menu(){
        PruebaDeArchivos mf  = new PruebaDeArchivos();
        Scanner lea = new Scanner(System.in);
        System.out.println("1. Borrar Cuenta\n"
                + "2. Salir");
        int op=lea.nextInt();
        switch(op){
            case 1:
            mf.borrar();
            System.out.println("BORRANDO CUENTA");
            menup();
        break;
            case 2:
                System.out.println("SALIENDO");
        break;
            default:
                System.out.println("NO SE PUEDE");
                Menu();
                return;
        }
        
        }
      public static void menup(){
        Scanner lea = new Scanner(System.in);
        PruebaDeArchivos mf  = new PruebaDeArchivos();
        
                while(true){
                    System.out.println(
                        "1. Iniciar sesion\n" +
                        "2. Crear cuenta\n" +
                        "3. Salir"
                    );
                    int menu=lea.nextInt();
                    switch(menu){
                        case 1:
                        login();
                        break; 
                        case 2:
                        crear();
                        break;
   
                        case 3:
                        System.exit(0);
                        break;
                        
                        default:
                            System.out.println("NO SE PUEDE");
                            return;
                    }
                }
            
      } 
    }
     
    

      
       
    




