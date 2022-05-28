package com.hes.lis.mltd.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class NepaliDateUtils {
	
	private int nepaliYear, nepaliMonth, nepaliDate;
	private int englishYear, englishMonth, englishDate;
	private int weekDay;
	
	private final int[] englishMonths, englishLeapMonths;
	
	private final int[][] nepaliMonths;
	
	public NepaliDateUtils(){
		
		//English no of days in months
		englishMonths = new int[]{31, 28, 31, 30, 31, 30,31, 31, 30, 31, 30, 31};
		englishLeapMonths = new int[]{31, 29, 31, 30, 31, 30,31, 31, 30, 31, 30, 31};
		
		//Nepali no of days in months
		nepaliMonths = new int[][]{
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 }, //2000
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },	//2001
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 },
			{ 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 },
			{ 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 30, 32, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 },
			{ 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 30, 32, 31, 32, 31, 31, 29, 30, 29, 30, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },
			{ 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },  //2071
			{ 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30 },  //2072
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 },  //2073
			{ 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 },//2077
			{ 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 },
			{ 31, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 31, 32, 31, 32, 30, 31, 30, 30, 29, 30, 30, 30 },
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30 },
			{ 30, 31, 32, 32, 30, 31, 30, 30, 29, 30, 30, 30 },
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 },	//2090
			{ 31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30 },
			{ 30, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 30, 30, 30, 30 },
			{ 30, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 },
			{ 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 },
			{ 31, 31, 32, 31, 31, 31, 29, 30, 29, 30, 29, 31 },
			{ 31, 31, 32, 31, 31, 31, 30, 29, 29, 30, 30, 30 }  //2099
		};
	}
	
	public void setCurrentDate(Date dNow) throws Exception{
		//Setting current english Date
	    SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		int y = new Integer(ft.format(dNow));
	    ft = new SimpleDateFormat("MM");
	    int m = new Integer(ft.format(dNow));
	    ft = new SimpleDateFormat("dd");
	    int d = new Integer(ft.format(dNow));
	    
		setEnglishDate(y,m,d);
	}
	
	public String fetchNepaliDateStrVal(Date englishDate){
		String dateStr=null;
		
		try{
			Date d1Date=new SimpleDateFormat("dd-MM-yyyy").parse("14-04-1943");
			//Setting nepali reference to 2000/1/1 //WEDNESDAY  with english date 1943/4/14 //WEDNESDAY
			int days =  (int)( (englishDate.getTime() - d1Date.getTime()) / (1000 * 60 * 60 * 24));
			
			Integer year=0;
			Integer month=0;
			Integer day=0;
			while(true){
				try{
					if(year==76){
						System.out.println();
					}
					System.out.println("year :: "+year);
					for(int i=0;i<12;i++){
						month=i+1;
						System.out.print(nepaliMonths[year][i]+" ,");
						
						if((days-day)>=nepaliMonths[year][i]){
							day=day+nepaliMonths[year][i];
						}
						
						if(day>=days){
							break;
						}
					}
					if(day>=days){
						break;
					}
					System.out.println();
					year++;
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			System.out.println("Nepali Year ::: "+year);
			System.out.println("Nepali month ::: "+month);
			
			
			
			
			
			/*Integer currentYear=(Integer.parseInt(new SimpleDateFormat("yyyy").format(englishDate))-1943);
			Integer currentMonth=(Integer.parseInt(new SimpleDateFormat("MM").format(englishDate)));
			Integer currentDay=(Integer.parseInt(new SimpleDateFormat("MM").format(englishDate)));

			
			
			
			Integer dayOfWeek=3;//WEDNESDAY
			for(int i=0;i<currentYear;i++){
				
				for(int j=0;j<12;i++){
					
					
					
				}
				
			}*/
			
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return dateStr;			
		
	}
	
	
	public Integer fetchDayOfWeekNumber(Date nepaliDate){
		Integer weekOfDay=00;//SUNDAY
		
//		Integer day=Integer.parseInt(new SimpleDateFormat("dd").format(nepaliDate));
//		Integer month=Integer.parseInt(new SimpleDateFormat("MM").format(nepaliDate));
		Integer year=Integer.parseInt(new SimpleDateFormat("yyyy").format(nepaliDate));

		for(int i=0;i<(year-2000)*12;i++){
			if(i>6){
				weekOfDay=0;
			}
			
		}
		return weekOfDay;
	}
	
	
	public void setCurrentDate() throws Exception{
		//Setting current english Date
		Date dNow = new Date();
	    SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		int y = new Integer(ft.format(dNow));
	    ft = new SimpleDateFormat("MM");
	    int m = new Integer(ft.format(dNow));
	    ft = new SimpleDateFormat("dd");
	    int d = new Integer(ft.format(dNow));
	    
		setEnglishDate(y,m,d);
	}
	
	public void assignDate(Date dNow) throws Exception{
		//Setting current english Date
	    SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		int y = new Integer(ft.format(dNow));
	    ft = new SimpleDateFormat("MM");
	    int m = new Integer(ft.format(dNow));
	    ft = new SimpleDateFormat("dd");
	    int d = new Integer(ft.format(dNow));
		setEnglishDate(y,m,d);
	}
	
	//English to Nepali date conversion
	
	public void setEnglishDate(int year, int month, int date) throws Exception{
		if(!this.isEnglishRange(year,month,date))
			throw new Exception("Invalid date format.");
		
		this.englishYear = year;
		this.englishMonth = month;
		this.englishDate = date;
		
		//Setting nepali reference to 2000/1/1 //WEDNESDAY  with english date 1943/4/14 //WEDNESDAY
		this.nepaliYear = 2000;
		this.nepaliMonth = 1;
		this.nepaliDate = 1;
		
		int difference = this.getEnglishDateDifference(1943, 4, 14);
		
		//Getting nepali year untill the difference remains less than 365
		int index = 0;
		while( difference >= this.nepaliYearDays(index) ){
			this.nepaliYear++;
			difference = difference - this.nepaliYearDays(index);
			index++;
		}
		
		//Getting nepali month untill the difference remains less than 31
		int i = 0;
		while(difference >= this.nepaliMonths[index][i]){
			difference = difference - this.nepaliMonths[index][i];
			this.nepaliMonth++;
			i++;
		}
		
		//Remaning days is the date;
		this.nepaliDate = this.nepaliDate + difference;
		
		this.getDay();
		
	}
	
	public String toEnglishString(){
		return toEnglishString("-");
	}
	
	public String toEnglishString(String format){
		return this.englishYear+format+this.englishMonth+format+this.englishDate;
	}
	
	public int getEnglishDateDifference(int year, int month, int date){

		//Getting difference from the current date with the date provided
		int difference = countTotalEnglishDays(this.englishYear, this.englishMonth, this.englishDate) - countTotalEnglishDays(year, month, date);
		return (difference < 0 ? -difference : difference );
		
	}
	
	private int countTotalEnglishDays(int year, int month, int date){

		int totalDays = year * 365 + date;
				
		for(int i=0; i < month-1; i++)
			totalDays = totalDays + this.englishMonths[i];
		
		totalDays = totalDays +countleap(year, month);
		return totalDays;
	}
		
	public int countleap(int year, int month)
	{
		if (month <= 2)
			year--;
		
		return (int) (Math.floor(year/4)-Math.floor(year/100)+Math.floor(year/400));
	}
	
	private boolean isEnglishRange(int year, int month, int date){
		
		if(year < 1944 || year > 2042)
			return false;
		
		if(month < 1 || month > 12)
			return false;
		
		if(date < 1 || date > 31)
			return false;
		
		return true;
		
	}
	
	private boolean isLeapYear(int year){
		if(year%4 == 0){
            return (year%100 == 0) ? (year%400 == 0) : true;                
		}
        else
            return false;
	}
	
	
	//Nepali to English date conversion
	
	public void setNepaliDate(int year, int month, int date) throws Exception{
		if(!this.isNepaliRange(year,month,date))
			throw new Exception("Invalid date format.");
		
		this.nepaliYear = year;
		this.nepaliMonth = month;
		this.nepaliDate = date;
		
		//Setting english reference to 1944/1/1 with nepali date 2000/9/17
		this.englishYear = 1944;
		this.englishMonth = 1;
		this.englishDate = 1;
		
		int difference = this.getNepaliDateDifference(2000, 9, 17);
		
		//Getting english year untill the difference remains less than 365
		while( difference >= (isLeapYear(this.englishYear) ? 366 : 365)){
			difference = difference - (isLeapYear(this.englishYear) ? 366 : 365);
			this.englishYear++;
		}
		
		//Getting english month untill the difference remains less than 31
		int[] monthDays = isLeapYear(this.englishYear) ? this.englishLeapMonths : this.englishMonths;
		int i = 0;
		while( difference >= monthDays[i]){
			this.englishMonth++;
			difference = difference - monthDays[i];
			i++;
		}
		
		//Remaning days is the date;
		this.englishDate = this.englishDate + difference;
		
		this.getDay();
		
	}
	
	public String toNepaliString(){
		return toNepaliString("-");
	}
	
	public String toNepaliString(String format){
		return this.nepaliYear+format+this.nepaliMonth+format+this.nepaliDate;
	}
	
	public int getNepaliDateDifference(int year, int month, int date){

		//Getting difference from the current date with the date provided
		int difference = countTotalNepaliDays(this.nepaliYear, this.nepaliMonth, this.nepaliDate) - countTotalNepaliDays(year, month, date);
		return (difference < 0 ? -difference : difference );
		
	}
	
	private int countTotalNepaliDays(int year, int month, int date){
		int total = 0;
		if(year < 2000)
			return 0;
		
		total = total + (date-1);
		
		int yearIndex = year - 2000;
		for(int i=0; i < month-1; i++)
			total = total + this.nepaliMonths[yearIndex][i];
		
		for(int i=0;i < yearIndex; i++)
			total = total + this.nepaliYearDays(i);
		
		return total;
	}
	
	private int nepaliYearDays(int index)
	{
		int total = 0;
		
		for(int i = 0 ; i < 12; i++)
			total += this.nepaliMonths[index][i];
		
		return total;
	}
	
	private boolean isNepaliRange(int year, int month, int date){
		if(year < 2000 || year > 2098)
			return false;
		
		if(month < 1 || month > 12)
			return false;
		
		if(date < 1 || date > this.nepaliMonths[year-2000][month-1])
			return false;
		
		return true;
	}
	
	
	
	//Class Regular methods
	
	public int getDay(){
		
		//Reference date 1943/4/14 Wednesday 
		int difference = this.getEnglishDateDifference(1943, 4, 14);
		this.weekDay = ((3 + (difference%7) ) % 7 ) + 1;
		return this.weekDay;
	}
	
	public int getEnglishYear(){ return this.englishYear; }
	
	public int getEnglishMonth(){ return this.englishMonth; }
	
	public int getEnglishDate(){ return this.englishDate; }
	
	public int getNepaliYear(){ return this.nepaliYear; }
	
	public int getNepaliMonth(){ return this.nepaliMonth; }
	
	public int getNepaliDate(){ return this.nepaliDate; }
	
	@Override
	public String toString()
	{
		return "English Date: "+this.toEnglishString()+"\n"+"Nepali Date: "+this.toNepaliString()+"\nDay: "+this.weekDay;
	}
	
	public Date convertEnglishToNepaliDate(Date englishdate){
		Date nepaliDate=null;
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm:ss");
		
		try{
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return nepaliDate;
	}
	
	
	public static void main(String[] args) {
    	
    	try {
    		
//    		System.out.println(new NepaliDateUtils().fetchNepaliDateStrVal(new Date()));
    		
    		
    		Date toDate=new Date();
			toDate=new Utils().convertEnglishToNepali(toDate);
			
//			System.out.println("toDate :: "+toDate);
    		
    		
    		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    		formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // Or whatever IST is supposed to be
    		System.out.println("Eng date : "+formatter.format(toDate)); 

    		
    		
    		
    		
    		/*   
    		SimpleDateFormat dateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//    		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");		
			
			Date toDate=new SimpleDateFormat("dd-MM-yyyy").parse("25-08-2020");
    		NepaliDateUtils toDateConverter = new NepaliDateUtils();
    		toDateConverter.setCurrentDate(toDate);
    		String toDateStr=toDateConverter.getNepaliYear()+"-"+toDateConverter.getNepaliMonth()+"-"+toDateConverter.getNepaliDate()+" "+timeFormat.format(toDate);
    		Date nepaliToDate=dateTimeFormat.parse(toDateStr);
    		
    		System.out.println(nepaliToDate);
    		System.out.println(nepaliToDate.getDay());*/

			/*Date nepaliToDate=new SimpleDateFormat("dd-MM-yyyy").parse("08-05-2077");

    		
    		System.out.println(nepaliToDate);
    		System.out.println(nepaliToDate.getDay());*/
    		
    		
    		
    		
	    	    
    	  /*  Date fromdate=new Date(toDate.getTime()-15*60*1000);
    	    NepaliDateUtils fromDateConverter = new NepaliDateUtils();
    	    fromDateConverter.setCurrentDate(fromdate);
    		String fromDateStr=fromDateConverter.getNepaliYear()+"-"+fromDateConverter.getNepaliMonth()+"-"+fromDateConverter.getNepaliDate()+" "+timeFormat.format(fromdate);
    		Date nepaliFromdate=dateTimeFormat.parse(fromDateStr);
    		
    		System.out.println(yearFormat.format(nepaliFromdate));
    		
    		String fromDateHexStr=StringUtils.leftPad(String.format("%x", Integer.parseInt(yearFormat.format(nepaliFromdate))), 4,"0")+
    							  StringUtils.leftPad(String.format("%x", Integer.parseInt(monthFormat.format(nepaliFromdate))), 2,"0")+
    							  StringUtils.leftPad(String.format("%x", Integer.parseInt(dayFormat.format(nepaliFromdate))), 2,"0");
    		
    		String fromTimeHexStr=StringUtils.leftPad(String.format("%x", Integer.parseInt(hoursFormat.format(nepaliFromdate))), 2,"0")+
    						      StringUtils.leftPad(String.format("%x", Integer.parseInt(minFormat.format(nepaliFromdate))), 2,"0")+
    						      StringUtils.leftPad(String.format("%x", Integer.parseInt(secFormat.format(nepaliFromdate))), 2,"0");
    		
    		String toDateHexStr=StringUtils.leftPad(String.format("%x", Integer.parseInt(yearFormat.format(nepaliToDate))), 4,"0")+
								  StringUtils.leftPad(String.format("%x", Integer.parseInt(monthFormat.format(nepaliToDate))), 2,"0")+
								  StringUtils.leftPad(String.format("%x", Integer.parseInt(dayFormat.format(nepaliToDate))), 2,"0");

			String toTimeHexStr=StringUtils.leftPad(String.format("%x", Integer.parseInt(hoursFormat.format(nepaliToDate))), 2,"0")+
							      StringUtils.leftPad(String.format("%x", Integer.parseInt(minFormat.format(nepaliToDate))), 2,"0")+
							      StringUtils.leftPad(String.format("%x", Integer.parseInt(secFormat.format(nepaliToDate))), 2,"0");

			String obisDataCommand="00 01 00 01 00 01 00 20 C0 01 C1 00 07 01 00 63 80 00 FF " +
					"01 01 02 04 02 04 12 00 08 09 06 00 00 01 00 00 FF 0F 02 12 00 00 " +
//					"09 0C "+"08 1C 03 12 "+"FF"+" 00 15 25 "+"FF 80 00 FF "+
//					"09 0C "+"08 1C 03 12 "+"FF"+" 0E 15 25 "+"FF 80 00 FF "+
					"09 0C "+fromDateHexStr+"FF"+fromTimeHexStr+"FF 80 00 FF "+
					"09 0C "+toDateHexStr+"FF"+toTimeHexStr+"FF 80 00 FF "+
					"01 00 ";
			
			System.out.println(obisDataCommand);*/

    		
    		
    	    
    	    
    	    

    	} catch (Exception e) {
    	   e.printStackTrace();
    	    System.out.println(e.toString());
    	}
    	
    	
    }
	
}
