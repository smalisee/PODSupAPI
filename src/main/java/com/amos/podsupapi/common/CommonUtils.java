package com.amos.podsupapi.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CommonUtils {

private CommonUtils() {}

	public static Date getTimeAfter(int hours) {
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(new Date()); // sets calendar time/date
		cal.add(Calendar.HOUR_OF_DAY, hours); // adds one hour

		return cal.getTime();
	}

	public static String[] convertBarcodeToDocNo(String barcodeNo) {
		Integer tmp = Integer.valueOf(barcodeNo.substring(0, 2));
		String type = Character.toString((char) tmp.shortValue());
		String[] docNoArr;
		String docNo = "";
		switch (type) {
		case "C":
			docNo += "CO4V";
			break;
		case "D":
			docNo += "CO4T";
			break;
			default:
				String msgStr= String.format("Unknow barcode format, Please check your barcode : %s",barcodeNo);
				throw(new InvalidParameterException(msgStr));
		}
		String[] month = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C" };
		if ("C".equals(type)
				&& LocalDate.now().getDayOfMonth() == Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) {

			Calendar calendar = Calendar.getInstance();

			//Calendar.MONTH Start with 0
			int currentMonth = calendar.get(Calendar.MONTH);
			String[] tmpArr = { docNo, docNo };

			if ( currentMonth == Calendar.getInstance().getActualMaximum(Calendar.MONTH)) {
				tmpArr[0] += month[currentMonth] + barcodeNo.substring(2);
				tmpArr[1] += month[0] + barcodeNo.substring(2);
			} else {
				tmpArr[0] += month[currentMonth] + barcodeNo.substring(2);
				tmpArr[1] += month[currentMonth + 1] + barcodeNo.substring(2);
			}
			docNoArr = tmpArr;
		} else {
			docNoArr = new String[] { docNo + month[LocalDate.now().getMonthValue() - 1] + barcodeNo.substring(2) };
		}
		return docNoArr;
	}
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static String toJsonString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String ret = null;
		try {
			ret = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			ret += String.format("toJsonString JsonProcessingException : %s",e.getMessage());
		}
		return ret;

	}
	public static String passwordEncrypt(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes("UTF-8"));
		byte[] digest = md.digest();
		return DatatypeConverter.printHexBinary(digest).toUpperCase();
	}
	public static java.util.Date sqlDateToUtilDate(java.sql.Date date){
		return java.util.Date.from(date.toInstant());
	}
	public static java.util.Date localDateToUtilDate(java.time.LocalDate date){
		return java.util.Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static void writeFile(String filepath, byte[] bt) throws IOException {
		try( OutputStream outStream = new FileOutputStream(filepath)){
			outStream.write(bt);
		}
	}

	public static byte[] readFile(String filepath) throws IOException {
		File file = new File(filepath);
		try(InputStream stream = new FileInputStream(file))
		{
			byte[] buffer = new byte[stream.available()];
			int count = 0;

			int hasRead = 0;
			while((hasRead = stream.read(buffer)) > 0){
				count += hasRead;
			}
			if(count != stream.available()) {
				throw(new IOException("total available and actual read is mismatch"));
			}
			return buffer;
		}
	}
	public static File getResource(String path) {
		ClassPathResource res = new ClassPathResource(path);
		return new File(res.getPath());
	}

}
