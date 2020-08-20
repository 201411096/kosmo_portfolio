package com.mycompany.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class DogBreedClassifyingThread extends Thread{

	String breed="";
	String result="";
	OutputStream out;
    InputStream in;
    FileInputStream fin;
    BufferedOutputStream bos;
    BufferedReader br = null;
    Socket soc=null;    

	public DogBreedClassifyingThread(Socket socket, BufferedReader br) {
		this.soc = socket;
		this.br = br;
	}

	@Override
	public void run() {
		try {		
			 br = new BufferedReader(new InputStreamReader(soc.getInputStream()));         
	         while((breed = br.readLine())!=null) {	 
	        	 if(breed!=null)break;
	         }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getBreed() {
		return breed;
	}
}