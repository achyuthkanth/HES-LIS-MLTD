package com.hes.lis.mltd.utils;

import java.nio.ByteBuffer;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author achyuth kanth pamuru
 *
 */
public class HexToStringUtils {
	
	
	public static String hexByteArrayToStringWithSpace(byte[] data ) {
		StringBuffer hexString = new StringBuffer();
		try{
			for (int i = 0; i < data.length; i++) {
				String str=Integer.toHexString(0xFF & data[i])+"";
				hexString.append(StringUtils.leftPad(str, 2, "0")+" ");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return hexString.toString();		
	}
	
	public String hexByteArrayToString(byte[] data ) {
		StringBuffer hexString = new StringBuffer();
		try{
			for (int i = 0; i < data.length; i++) {
				String str=Integer.toHexString(0xFF & data[i])+"";
				hexString.append(StringUtils.leftPad(str, 2, "0"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return hexString.toString();
	}
	
	public static String hexToStringByte(byte byteData){
		String responseByte=null;
		try{
			String str=Integer.toHexString(0xFF & byteData)+"";
			responseByte=StringUtils.leftPad(str, 2, "0");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return responseByte;
	}
	
	 public static String hexToString(String txtInHex)
	  {
	    byte [] txtInByte = new byte [txtInHex.length() / 2];
	    int j = 0;
	    for (int i = 0; i < txtInHex.length(); i += 2)
	    {
	        txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);
	    }
	    return new String(txtInByte);
	  }

	 public static String hex(int n) {
		    // call toUpperCase() if that's required
		    return String.format("0x%8s", Integer.toHexString(n)).replace(' ', '0');
		}

		public static String hex(float f) {
		    // change the float to raw integer bits(according to the OP's requirement)
		    return hex(Float.floatToRawIntBits(f));
		}
		
		
		public static byte[] hexStringToByteArray(String s) {
			s=s.replace(" ", "");
			int len = s.length();
			byte[] data = new byte[len / 2];
			for (int i = 0; i < len; i += 2) {
				data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
						.digit(s.charAt(i + 1), 16));
			}
			return data;
		}
		
		 public static int convertToInt(byte[] array) {
		        ByteBuffer buffer = ByteBuffer.wrap(array);
		        return buffer.getInt();
		    }
		
		public String hexToAmount(String hexString){
			byte[] byteArr=hexStringToByteArray(hexString);
			double balAmt=(double)convertToInt(byteArr)/100;
			return balAmt+"";
		}

		public static byte[] convertToByteArray(int value) {
	        byte[] bytes = new byte[4];
	        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
	        buffer.putInt(value);
	        return buffer.array();
	    }
	   
		public String amountToHex(String amount){
			double dblAmt=Double.parseDouble(amount);
			dblAmt=dblAmt*100;
			Integer balanceAmount=(int)dblAmt;
			String hexBytes = hexByteArrayToStringWithSpace(convertToByteArray(balanceAmount));
			return hexBytes;
		}
		
		public static String unHex(String arg) {        
		    String str = "";
		    arg=arg.replace(" ","");
		    for(int i=0;i<arg.length();i+=2)
		    {
		        String s = arg.substring(i, (i + 2));
		        int decimal = Integer.parseInt(s, 16);
		        str = str + (char) decimal;
		    }       
		    return str;
		}
		
		
		public String hexToBinary(String hex) {
		    int i = Integer.parseInt(hex, 16);
		    String bin = StringUtils.leftPad(Integer.toBinaryString(i), 8, "0");
		    return bin;
		}
		
		
		
		
	public static void main(String[] args) {
		
//		 byte[] byteAwee=new HexToStringUtils().hexStringToByteArray("00 01 00 01 00 01 00 17 C1 01 C1 00 01 00 00 60 02 CF FF 02 00 02 03 03 01 12 00 3C 12 00 1E");
//		System.out.println(byteAwee.length);
//		System.out.println(new HexToStringUtils().hexToBinary("7F"));
		
		
		
//		System.out.println(new HexToStringUtils().unHex("3837363534333231"));
		
//		System.out.println(Hex.encodeHexString("FP60093V07R007M002".getBytes()));
		
//		00 01 00 20 00 01 00 40 C0 01 C1 00 07 01 00 63 01 00 ff 02 01 01 02 04 02 04 12 00 08 09 06 00 00 01 00 00 ff 0f 02 12 00 00 
//		09 0c 07 e1 03 14 02 00 00 00 00 00 00 00 09 0c 07 e1 03 15 03 00 00 00 00 00 00 00 01 00 //data
		
		
		
//		67 FF 90 EA
		
		
//		System.out.println(StringUtils.leftPad(String.format("%x", 325575), 8,"0"));
		
//		System.out.println(StringUtils.leftPad(String.format("%x", 103), 2,"0"));
//		System.out.println(StringUtils.leftPad(String.format("%x", 255), 2,"0"));
//		System.out.println(StringUtils.leftPad(String.format("%x", 144), 2,"0"));
//		System.out.println(StringUtils.leftPad(String.format("%x", 234), 2,"0"));
			
		
		
//		System.out.println(new HexToStringUtils().hexToString("07e1"));

		
//		System.out.println(new HexToStringUtils().hex("09"));
//		System.out.println(new HexToStringUtils().hexToAmount("00020788"));
		
//		String strVal=String.format("0x%8s", Integer.toHexString((Float.floatToRawIntBits(584.24f)))).replace(' ', '0');
//		System.out.println(strVal);
		
//		String myString = "0000E438";
//		HexToStringUtils obj=new HexToStringUtils();
		
//		byte[] byteArr=obj.hexStringToByteArray(myString);
//		float f = ByteBuffer.wrap(byteArr).order(ByteOrder.LITTLE_ENDIAN).getFloat();
//		
//		System.out.println(f);
//		
//	    System.out.printf("0x%08X", Float.floatToRawIntBits(f));
		
//        Long i = Long.parseLong(myString, 16);
//        Float f = Float.intBitsToFloat(i.intValue());
//        System.out.println(f);
//        System.out.println(Integer.toHexString(Float.floatToIntBits(f)));
//		
//		System.out.println(hex(584.24f));
//		float f = 584.24f;

	   /* returns a hexadecimal string representation of the
	   float argument */
//	   String str = f.toHexString(50.32f);
//	   System.out.println("Hex String = " + str);
////	    
//	   str = f.toHexString(3.0f);
//	   System.out.println("Hex String = " + str);
//	    
//	   str = f.toHexString(0.25f);
//	   System.out.println("Hex String = " + str);
//	    
//	   str = f.toHexString(Float.MIN_VALUE);
//	   System.out.println("Hex String = " + str);
	   
//	   System.out.println(-1*((16777215 - 16593640) + 1));
	   
//	   System.out.println(Float.floatToRawIntBits(f));
	   
	
	}

}
