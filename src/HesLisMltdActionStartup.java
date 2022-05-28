import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.hes.lis.mltd.process.HesLisMltdProcessor;
import com.hes.lis.mltd.utils.Utils;

/**
 * 
 * @author psvr
 *
 */
public class HesLisMltdActionStartup {
	
	private static final Logger logger=Logger.getLogger(TcpModemIpListenerServer.class);
	
	private static ServerSocket serverSocketObj;
	private static boolean isServerStarted = false;
	private static Properties prop;
	private static Socket socketObj;

	public void tcpServerStart(Integer portNumber) {
		System.out.println("HesLisMltdActionStartup Server Trying to Start on Port :: "+portNumber);
		try {
			
			serverSocketObj = new ServerSocket(portNumber);
			if (serverSocketObj != null) {
				isServerStarted = true;
				System.out.println("HesLisMltdActionStartup Server Started Successfully on Port :: "+portNumber);
			}
		} catch (IOException e) {
			System.out.println("Error in Starting HesLisMltdActionStartup Listener on Port :: "+portNumber+" :: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public void invokeProcess(Integer portNumber) {

		try {
			while(true){
				
				tcpServerStart(portNumber);
				socketObj=null;
				try{
					while (isServerStarted) {
						try{
							socketObj = serverSocketObj.accept();
							boolean canStartUpplication=true;
							if(canStartUpplication){
								TcpModemIpListenerServer threadObj = new TcpModemIpListenerServer(
										socketObj,prop);
								threadObj.start();
							}else{
								System.out.println("******************************************************************************");
								System.out.println("HES-LIS-MLTD Liscense Expired.Cannot Run Application.Please contact Admin team");
								System.out.println("******************************************************************************");
							}
							
							
						}catch (Exception e) {
							System.out.println("while (isServerStarted) { :: "+e.getMessage());
							e.printStackTrace();
						}
						
						if(serverSocketObj==null){
							logger.error("serverSocketObj==null");
							isServerStarted=false;
						}
					}
				}catch (Exception e) {
					System.out.println("Error in processStart :: "+e.getMessage());
					e.printStackTrace();
				}finally{
//					try {
//						if (socketObj != null) {
//							socketObj.close();
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//						logger.error("Error in Closing Socket Connection :: "+e.getMessage());
//					}
				}
//				Thread.sleep(1000);
			}
			
		}catch (Exception e) {
			System.out.println("Error in Closing Socket Connection :: "+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
//				if (socketObj != null) {
//					socketObj.close();
//				}
			} catch (Exception e) {
				System.out.println("Error in Closing Socket Connection :: "+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	private static class TcpModemIpListenerServer extends Thread {
		private Socket socketObjext;
		private Properties propObj;

		public TcpModemIpListenerServer(Socket socketObj,Properties prop) {
			this.socketObjext = socketObj;
			this.propObj=prop;
		}

		public synchronized void run() {
			try{
				new HesLisMltdProcessor().processCommunication(socketObjext,propObj);
			}catch (Exception e) {
				System.out.println("Error in synchronized void run :: "+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		try{
			boolean canStartUpplication=true;

			if(canStartUpplication){
				try{
					System.out.println("HES-LIS-MLTD SmartMeter HES Tool started ===>>>>>");
					prop=new Utils().readPortForTcpListener();
					String port = prop.getProperty("TcpListenerPortNumber");
					
					try{
						port=args[0];
					}catch (Exception e) {
						// TODO: handle exception
					}
					Integer portNumber=Integer.parseInt(port);
					new HesLisMltdActionStartup().invokeProcess(portNumber);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}catch (Exception e) {
			System.out.println("Error in starting Application");
			e.printStackTrace();
		}
		
	}

}


