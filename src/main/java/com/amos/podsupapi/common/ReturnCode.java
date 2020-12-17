package com.amos.podsupapi.common;

public enum ReturnCode {
	SUCCESS(0, "Success",""),
	NO_DATA(1,"Success with no data","ไม่พบข้อมูลที่ระบุ"),
	NO_DATA_AUTO_PO(2,"Success with no data auto PO","ไม่พบข้อมูล Auto PO ของ Order ที่ระบุ"),
	MISSING_PARAM(101, "Missing request parameters","101 - พบปัญหาในการติดต่อระบบสำนักงานใหญ่"),
	INVALID_REQUEST_PARAM(102,"Invalid request paramters value","102 - ระบุข้อมูลในการทำงานไม่ถูกต้อง"),
	INTERNAL_WEB_ERROR(201, "Internal web service error","201 - ไม่สามารถติดต่อระบบสำนักงานใหญ่ได้"),
	DUPLICATE_EVENT(103,"Intersection of event period in same ID register","เลขที่บัตรประชาชน หรือเลข Passport ที่ระบุมีข้อมูลการสมัครกับโครงการอื่นในช่วงเวลาเดียวกันแล้ว"),
	INVALID_CITIZEN_ID(104," Invalid citizen ID","เลขที่บัตรประชาชน หรือเลข Passport ไม่ถูกต้อง"),
	STORE_IS_NOT_AVAILABLE(105,"Store is not available","ร้านสาขาที่ระบุไม่สามารถรับสินค้าได้ เนื่องจากปิด หรืออยู่ระหว่างปรับปรุง"),
	INVALID_USER_OR_PASSWORD(106,"Invalid Username or Password","Username หรือ Password ไม่ถูกต้อง"),
	EMAIL_IS_NOT_CONFIRM(107,"Username is not confirm e-mail","Username นี้ยังไม่ได้ยืนยันตัวตนจาก e-mail ที่ระบบส่งให้"),
	EMAIL_IS_ALREADY_USED(108,"E-mail already register","อีเมล์ที่ระบุมีข้อมูลอยู่แล้วในระบบ"),
	SAME_ID_ALREADY_REGISTER(109,"Citizen ID or Passport no. is already used","เลขที่บัตรประชาชน หรือเลข Passport ที่ระบุมีข้อมูลอยู่แล้วในระบบ"),
	DUPLICATE_VALUE(110,"Duplicate value","ข้อมูลซ้ำในระบบ"),
	NEED_UPDATE_MOBILE_APP(112,"Please update new version from store",""),
	STATUS_AUTO_PO(113, "The status of Auto PO cannot be modified","Status ของ Auto PO ไม่สามารถแก้ไขข้อมูลได้"),
	INTERNAL_WEB_SERVICE_ERROR(201,"Internal web service error","ไม่สามารถติดต่อระบบสำนักงานใหญ่ได้"),
	UNAUTHORIZED(401,"Unauthorized request, sign token is invalid or token expired","ไม่ผ่านการตรวจสอบ token หรือ token หมดอายุ"),
  INVALID_RE_PASSWORD(114,"NEW & RE-NEW Password not equal","ยืนยันรหัสผ่านใหม่ไม่ถูกต้อง"),
  USERNAME_ALREADY_EXIST(115,"Username is exist","มี username หรือ Email นี้ในระบบแล้ว"),
  INVALID_PASSWORD_LENGTH(116,"password leght incorrect up more than 4 character","ความยาวรหัสผ่านไม่ถูกต้อง");

	private final int code;
	private final String message;
	private final String webMessage;

	ReturnCode(int code, String message,String webMessage) {
		this.code = code;
		this.message = message;
		this.webMessage=webMessage;
	}

	public String getMessage() {
		return this.message;
	}

	public String getWebMessage() {
		return this.webMessage;
	}

	public int getCode() {
		return this.code;
	}

}
