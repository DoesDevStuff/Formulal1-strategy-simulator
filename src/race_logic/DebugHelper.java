package race_logic;

public class DebugHelper {
	public static boolean debugGlobal = false;
	
	public static void speedDistanceDbg1(boolean localDebug, Car car, boolean before) {
		if ( (!DebugHelper.debugGlobal) || (!localDebug) ) return; // FFF - if this is false turns off debugging at global level
		
		String beforeMsg = "---- before --------\n";
		String afterMsg = "---- after --------\n";
		String msg;
		//
		StringBuffer stringBuffer = new StringBuffer();
		++(car.iterationNumber);
		msg = (before) ? beforeMsg : afterMsg;
		stringBuffer.append(msg);
		stringBuffer.append("iterationNumner: " + car.iterationNumber + "\n");
		stringBuffer.append("CAR ID " + car.carID + "\n");
		stringBuffer.append("Elapsed time: " + car.elapsedTimeSeconds + "\n");
		stringBuffer.append("DISTANCE COVERED: " + car.currentDistTravelled + "\n");
		stringBuffer.append("NITROUS USED: " + car.isNitroUsed + "\n");
		stringBuffer.append("ACCELERATION: " + car.acceleration + "\n");
		stringBuffer.append("TOP SPEED: " + car.topSpeed + "\n");
		stringBuffer.append("CURRENT SPEED: " + car.currentSpeed + "\n");
		msg = (before) ? beforeMsg : afterMsg;
		stringBuffer.append(msg);
		System.out.println(stringBuffer. toString());
		//		

	}
	
	public static void limitTopSpeedDbg(boolean localDebug, Car car, boolean before) {
		if ( (!DebugHelper.debugGlobal) || (!localDebug) ) return; // FFF - if this is false turns off debugging at global level
		
		System.out.println("Car " + car.carID + car.MessageBundle(1));
		
	}

	public static void reduceSpeedCollisionDbg(boolean localDebug, Car car, boolean before) {
		if ( (!DebugHelper.debugGlobal) || (!localDebug) ) return; // FFF - if this is false turns off debugging at global level
		
        System.out.println("Car " + car.carID + car.MessageBundle(2) + car.currentSpeed);
		
	}
	
	public static void useNitroDbg(boolean localDebug, Car car, boolean before) {
		if ( (!DebugHelper.debugGlobal) || (!localDebug) ) return; // FFF - if this is false turns off debugging at global level
		
	    System.out.println("Car " + car.carID + car.MessageBundle(3));
		
	}
}