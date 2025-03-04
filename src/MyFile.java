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
public class MyFile {
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
    
    }



