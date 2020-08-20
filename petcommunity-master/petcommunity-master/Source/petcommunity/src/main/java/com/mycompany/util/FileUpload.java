package com.mycompany.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUpload {
	public static void uploadFile(MultipartHttpServletRequest mtfRequest, String path) {
		makeDirectory(path);
		MultipartFile mf = mtfRequest.getFile("file");

		String originFileName = mf.getOriginalFilename(); // 원본 파일 명
//		long fileSize = mf.getSize(); // 파일 사이즈

		String filePath = path + "/" + originFileName;
		if (originFileName == "" || originFileName == null)
			return;
		try {
			mf.transferTo(new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void uploadFiles(MultipartHttpServletRequest mtfRequest, String path) {
		makeDirectory(path);
		List<MultipartFile> fileList = mtfRequest.getFiles("file");

		for (MultipartFile mf : fileList) {
			String originFileName = mf.getOriginalFilename(); // 원본 파일 명
//			long fileSize = mf.getSize(); // 파일 사이즈

			String filePath = path + "/" + originFileName;
			if (originFileName == "" || originFileName == null)
				return;
			try {
				mf.transferTo(new File(filePath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void makeDirectory(String path) {
		File folder = new File(path);

		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!folder.exists()) {
			try {
				folder.mkdir(); // 폴더 생성합니다.
//				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
//			System.out.println("이미 폴더가 생성되어 있습니다.");
		}
	}

	public static void deleteDirectory(String path) {
		File folder = new File(path);
		try {
			if (folder.exists()) {
				File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기

				for (int i = 0; i < folder_list.length; i++) {
					if (folder_list[i].isFile()) {
						folder_list[i].delete();
						System.out.println("파일이 삭제되었습니다.");
					} else {
						deleteDirectory(folder_list[i].getPath()); // 재귀함수호출
						System.out.println("폴더가 삭제되었습니다.");
					}
					folder_list[i].delete();
				}
				folder.delete(); // 폴더 삭제
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	public static void deleteFileWithoutList(String path, String [] fileNameList) {
		File folder = new File(path);
		ArrayList<String> fileNameArrayList = null;
		if(fileNameList!=null)
			fileNameArrayList = new ArrayList<String>(Arrays.asList(fileNameList));
		else {
			// 살려야 되는 파일 목록이 하나도 없을 경우
			if (folder.exists()) {
				File[] fileListInDirectory = folder.listFiles(); // 파일리스트 얻어오기
				for(int i=0; i<fileListInDirectory.length; i++) {
					fileListInDirectory[i].delete(); // fileNameList에 포함되어 있지 않은 파일을 삭제함	
				}
			}
			return;
		}
		try {
			//살려야 되는 파일 목록이 있는 경우
			if (folder.exists()) {
				File[] fileListInDirectory = folder.listFiles(); // 파일리스트 얻어오기
				for(int i=0; i<fileListInDirectory.length; i++) {
//					System.out.println(fileListInDirectory[i].getName());
					if(!fileNameArrayList.contains(fileListInDirectory[i].getName())){
						fileListInDirectory[i].delete(); // fileNameList에 포함되어 있지 않은 파일을 삭제함
					}
						
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
