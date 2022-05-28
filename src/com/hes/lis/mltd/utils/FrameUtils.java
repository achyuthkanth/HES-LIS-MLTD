package com.hes.lis.mltd.utils;

/**
 * 
 * @author psvr
 *
 */
public class FrameUtils {
	
	
	public static Integer fetchIntFromResponse(String responseBytes){
		Integer value = null;
		
		try{
			String[] responseArr=responseBytes.split("\\ ");
			
			if(responseArr[11].equalsIgnoreCase("00")){
				Integer dataSize=Integer.parseInt(responseArr[12],16);
				if(responseArr[12].equalsIgnoreCase("06")){
					dataSize=4;
				}else if(responseArr[12].equalsIgnoreCase("05")){
					dataSize=4;
				}else if(responseArr[12].equalsIgnoreCase("12")){
					dataSize=2;
				}
				String versionStr="";
				
				for(Integer i=13;i<13+dataSize;i++){
					versionStr+=responseArr[i];
				}
				value=Integer.parseInt(versionStr,16);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static String fetchStringFromResponse(String responseBytes){
		String version=null;
		
		try{
			String[] responseArr=responseBytes.split("\\ ");
			
			if(responseArr[11].equalsIgnoreCase("00")&&((responseArr[12].equalsIgnoreCase("0A"))||(responseArr[12].equalsIgnoreCase("09")))){
				Integer dataSize=Integer.parseInt(responseArr[13],16);
				String versionStr="";
				
				for(Integer i=14;i<14+dataSize;i++){
					versionStr+=responseArr[i];
				}
				version=HexToStringUtils.unHex(versionStr);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return version;
	}

	public static void main(String[] args) {

		try {
			String str=FrameUtils.fetchStringFromResponse("00 01 00 01 00 01 00 13 c4 01 c4 00 0a 0d 12 4d 72 73 2e 4e 65 70 61 6c 20 54 65 ");
			
//			String str=HexToStringUtils.hexToString(("00 01 00 01 00 01 00 13 c4 01 c4 00 0a 0d 12 4d 72 73 2e 4e 65 70 61 6c 20 54 65").replace(" ", ""));
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
