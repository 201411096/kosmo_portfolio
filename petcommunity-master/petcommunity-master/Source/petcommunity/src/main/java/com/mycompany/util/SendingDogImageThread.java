package com.mycompany.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SendingDogImageThread extends Thread{

	int len;
	int data;
	byte[] buffer;
    FileInputStream fin;
    BufferedOutputStream bos;
    BufferedReader br = null;
    Socket soc=null; 
    String header;

	public SendingDogImageThread(Socket soc, BufferedOutputStream bos, int len, int data, byte[] buffer,FileInputStream fin, String header) {
		this.soc = soc;
		this.bos = bos;
		this.len = len;
		this.data = data;
		this.buffer = buffer;
		this.fin = fin;
		this.header = header;
	}

	@Override
	public void run() {		
		try {
			bos.write(header.getBytes());
			for (; data > 0; data--) { // 데이터를 읽어올 횟수만큼 FileInputStream에서 파일의 내용을 읽어옵니다.				
				len = fin.read(buffer); // FileInputStream을 통해 파일에서 입력받은 데이터를 버퍼에 임시저장하고 그 길이를 측정합니다.
	            bos.write(buffer, 0, len); // 서버에게 파일의 정보(1kbyte만큼보내고, 그 길이를 보냅니다.			
			} 
			bos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}