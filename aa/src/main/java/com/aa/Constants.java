package com.aa;

import org.springframework.stereotype.Component;

@Component
public class Constants {
	
	// Url Path
	public static String ABBREVIATION_XDM = "xdm";	// 어드민
	public static String ABBREVIATION_USR = "usr";	// 유저
	
	// Login Form URL
	public static String URL_LOGIN_FORM_XDM = "/xdm/member/MemberXdmSignIn";
	public static String URL_LOGIN_FORM_USR = "/usr/member/MemberUsrSignIn";
	
	// Login User Seq
	public static String SESSION_SEQ_NAME_XDM = "sessSeqXdm";
	public static String SESSION_SEQ_NAME_USR = "sessSeqUsr";
	
	// Login Session Time
	public static int SESSION_MINUTE = 60 * 30; // 30분
	
	// Order에서 사용하는 코드 그룹
	public final static int ORDERS_CODE_GROUP_SEQ_ORDER_STATE = 1; // oStateCd
	public final static int ORDERSPRODUCT_CODE_GROUP_SEQ_ORDERPRODUCT_STATE = 7; // opStateCd
	public final static int ORDERSDELIVERY_CODE_GROUP_SEQ_COMPANY = 8; // oDeliveryCompCd
	
	// oStateCd
	public final static int ORDERS_CODE_SEQ_ORDER_STATE_NEW = 1;
	public final static int ORDERS_CODE_SEQ_ORDER_STATE_READY_IN = 2;
	public final static int ORDERS_CODE_SEQ_ORDER_STATE_READY_OUT = 3;
	public final static int ORDERS_CODE_SEQ_ORDER_STATE_DELI = 4;
	public final static int ORDERS_CODE_SEQ_ORDER_STATE_DELI_COMPLATE = 5;
	public final static int ORDERS_CODE_SEQ_ORDER_STATE_END = 21;
	
	// opStateCd
	public final static int ORDERS_CODE_SEQ_ORDER_PRODUCT_STATE_REQ_DELI = 6;
	public final static int ORDERS_CODE_SEQ_ORDER_PRODUCT_STATE_V_ORDER = 7;
	public final static int ORDERS_CODE_SEQ_ORDER_PRODUCT_STATE_IN = 8;
	
}
