package com.hes.lis.mltd.utils;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


public class HexConvertionClassUtils {
	
	public static String hexByteArrayToStringWithSpace(byte[] data ) {
		StringBuffer hexString = new StringBuffer();
		try{
			for (int i = 0; i < data.length; i++) {
				hexString.append(StringUtils.leftPad(Integer.toHexString(0xFF & data[i])+"", 2, "0")+" ");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return hexString.toString();		
	}
	
    public static byte[] convertToByteArray(char value) {
        byte[] bytes = new byte[2];
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.putChar(value);
        return buffer.array();
    }
    
    
    public static byte[] convertToByteArray(int value) {
        byte[] bytes = new byte[4];
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.putInt(value);
        return buffer.array();
    }

    public static byte[] convertToByteArray(long value) {

        byte[] bytes = new byte[8];
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.putLong(value);
        return buffer.array();
    }

    public static byte[] convertToByteArray(short value) {

        byte[] bytes = new byte[2];
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.putShort(value);
        return buffer.array();
    }

    public static byte[] convertToByteArray(float value) {
        byte[] bytes = new byte[4];
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.putFloat(value);
        return buffer.array();
    }

    public static byte[] convertToByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.putDouble(value);
        return buffer.array();
    }

    public static byte[] convertToByteArray(String value) {
        return value.getBytes();
    }

    public static byte[] convertToByteArray(boolean value) {
        byte[] array = new byte[1];
        array[0] = (byte)(value == true ? 1 : 0);
        return array;
    }

    public static byte convertToByte(byte[] array) {
        return array[0];
    }

    public static int convertToInt(byte[] array) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return buffer.getInt();
    }

    public static long convertToLong(byte[] array) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return buffer.getLong();
    }

    public static short convertToShort(byte[] array) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return buffer.getShort();
    }

    public static String convertToString(byte[] array) {
        String value = new String(array);
        return value;
    }

    public static boolean convertToBoolean(byte[] array) {
        return (array[0] > 0 ? true : false );
    }

    public static char convertToCharacter(byte[] array) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return buffer.getChar();
    }

    public static double convertToDouble(byte[] array) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return buffer.getDouble();
    }

    public static float convertToFloat(byte[] array) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return buffer.getFloat();
    }
    
    public double currentReadingDecimal(String currentReadingHex){
    	currentReadingHex=currentReadingHex.replace(" ", "");
		HexToStringUtils obj=new HexToStringUtils();
		@SuppressWarnings("static-access")
		byte[] byteArr=obj.hexStringToByteArray(currentReadingHex);
		double balAmt=(double)convertToInt(byteArr);
		balAmt=balAmt*0.001;
    	return balAmt;
    }
    
    public int hexToDecimal(String hex) {
    	hex=hex.toUpperCase();
        int counter = hex.length()-1;
        String hexRep = "0123456789ABCDEF";
        int sum = 0;
        for (char c:hex.toCharArray()) {
            int i = hexRep.indexOf(c);
            sum = (int) (sum + (Math.pow(16,counter))*i);
            counter--;
        }
        return sum;
    }
    
    public static String toHexString(String myString) {
    	byte[] byteArr=convertToByteArray(myString);
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < byteArr.length; i++)
            str.append(StringUtils.leftPad(String.format("%x", byteArr[i]),2,"0")+" ");
        return str.toString();
    }
    
    
    public static String[] fetchSixeInHex(Integer size){
    	String[] sizeArr=new String[2];
    	String hex = Integer.toHexString(size);
    	if((hex.length()/2!=0)||(hex.length()<2)){
    		hex=StringUtils.leftPad(hex, 4,"0");
    	}
    	sizeArr[0]=hex.substring(0,2);
    	sizeArr[1]=hex.substring(2,4);
		return sizeArr;
    }
    
    
    public static String dateTimeHexBytesByDate(Date date,Integer dayOfWeek){
		String strDate="";
		Calendar currentDate = Calendar.getInstance();
    	currentDate.setTime(date);
    	dayOfWeek=dayOfWeek-1;

    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.YEAR)), 4,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.MONTH)+1), 2,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.DAY_OF_MONTH)), 2,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", dayOfWeek), 2,"0")+" ";
//    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.DAY_OF_WEEK_IN_MONTH)), 2,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.HOUR_OF_DAY)), 2,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.MINUTE)), 2,"0")+" ";
    	strDate=strDate+StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.SECOND)), 2,"0")+" ";
    	strDate=strDate+"10 80 00 FF";
		return "09 0c "+strDate;
	}
    
    
    
    
    
    public static void main(String[] arg){
    	
//    	String str=String.format("%x", Integer.parseInt((2076)+""), 4,"0");
//    	System.out.println(str);
    	
    	System.out.println(HexConvertionClassUtils.toHexString("ntnet"));
    	
    	
    	
    	
    	
//    	00000DFC
    	
//    	System.out.println(new HexConvertionClassUtils().toHexString("ACHYUTH KANTH"));
    	
    	
//		00 01 00 20 00 01 00 40 C0 01 C1 00 07 01 00 63 01 00 ff 02 01 01 02 04 02 04 12 00 08 09 06 00 00 01 00 00 ff 0f 02 12 00 00 
//		09 0c 07 e1 03 14 02 00 00 00 00 00 00 00 09 0c 07 e1 03 15 03 00 00 00 00 00 00 00 01 00 //data

    	
//    	HexToStringUtils obj=new HexToStringUtils();
//		byte[] byteArr=obj.hexStringToByteArray("07e1");
//		convertToInt(byteArr);
		
    	
//    	Calendar currentDate = Calendar.getInstance();
//    	currentDate.setTime(new Date());
//    	
//    	System.out.println(StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.YEAR)), 4,"0")+" ");
//    	System.out.println(StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.MONTH)+1), 2,"0")+" ");
//    	System.out.println(StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.DAY_OF_MONTH)), 2,"0")+" ");
//    	System.out.println(StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.DAY_OF_WEEK)), 2,"0")+" ");
//    	System.out.println(StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.HOUR_OF_DAY)), 2,"0")+" ");
//    	System.out.println(StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.MINUTE)), 2,"0")+" ");
//    	System.out.println(StringUtils.leftPad(String.format("%x", currentDate.get(Calendar.SECOND)), 2,"0")+" ");
    	
    	
    	
//    	07 E1 02 11 05 11 2C 32 FF
//    	String myString = "07E1 03 0F 03 0E 39 00 00 01 4A 00";
//    	String[] strArr=myString.split("\\ ");
//    	
//    	for(String val:strArr){
//    		System.out.println(new HexConvertionClassUtils().hexToDecimal("1E"));
//    	}
    	
//    	07 E1 02 11 05 11 2C 32 FF
//    	String myString = "30";
//    	System.out.println(new HexConvertionClassUtils().hexToDecimal(myString));
    	
//    	
    	
    	
//    	System.out.println("C1 01 C1 00 14 00 00 0D 00 00 FF 06 00 09 07 54 53 53 50 44 43 4C".replace(" ", "").length()/2);
//    	String str="900";
//    	
//    	System.out.println(StringUtils.leftPad(String.format("%x", Integer.parseInt((1259)+"")), 4,"0")+" ");
//    	System.out.println(StringUtils.leftPad(Integer.toHexString(0xFF & 15)+"", 2, "0")+" ");
    	
//    	String myString="SEASON1";
//		System.out.println(toHexString(myString));
    	
    	
//    	System.out.println(StringUtils.leftPad(String.format("%x", Integer.parseInt((20*1000)+"")), 8,"0")+" ");
    	
    	
//		HexToStringUtils obj=new HexToStringUtils();
//		byte[] byteArr=obj.hexStringToByteArray(myString);
//		int balAmt=convertToInt(byteArr);
//		System.out.println(balAmt);
    }
    
    
}