
package utils;

import utils.Quaternion;
import utils.Vector3;

public class Mathbox{
    public static double PI = 3.14159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706798214808;
	
    private static double Psi=0;
    private static double Theta=0;
    
    
    public static double LinearInterpolate( double atm_x[] , double atm_y[] , double xx)
	{
	double yvalue=0;
	double y1 = 0,y2 = 0,x1 = 0,x2 = 0;
	int lines=atm_x.length;
	//............................................
	for(int ii=lines-1;ii>0;ii--)
	{
	if(atm_x[ii]>xx){
	y1 = atm_y[ii];
	x1 = atm_x[ii];
	}
	}
	//............................................
	for(int ii=0;ii<lines-1;ii++)
	{
	if(atm_x[ii]<xx){
	y2 = atm_y[ii];
	x2 = atm_x[ii];
	}
	}
	//...........................................
	if(xx<=atm_x[0]){
	y1 = atm_y[0];
	y2 = atm_y[1];
	x1 = atm_x[0];
	x2 = atm_x[1];
	//System.out.println("low limit reached");
	}
	if (xx>=atm_x[lines-1])
	{
	y1 = atm_y[lines-2];
	y2 = atm_y[lines-1];
	x1 = atm_x[lines-2];
	x2 = atm_x[lines-1];
	//System.out.println("high limit reached");
	}
    yvalue = y1 + ( y2 - y1 ) * ( xx - x1 ) / ( x2 - x1 ) ;
	//System.out.println(x1 + "|" + x2+ "|" +y1+ "|" +y2+ "|" +yvalue+ "|" +lines);
	return yvalue;
	}
	//---------------------------------------------------------------------------------------------------------------------
	//
	//			Linear Algebra service functions:  
	//
	//---------------------------------------------------------------------------------------------------------------------    
    public static double[][] Multiply_Matrices(double[][] A, double[][] B) {
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] C = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
    public static double[][] Multiply_Scalar_Matrix(double scalar, double[][] M){
    	int rows    = M.length;
    	int columns = M[0].length; 
    	double[][] result = new double[rows][columns];
        for (int i = 0; i < rows; i++) { // aRow
            for (int j = 0; j < columns; j++) { // bColumn
            	result[i][j] = M[i][j] * scalar; 
            }
            }
    	return result; 
    }
    public static double[][] Substract_Matrices(double[][] A, double[][] B){
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;
    	double[][] C = new double[aRows][aColumns];
    	
        if (aRows!=bRows || aColumns!=bColumns) {
        	System.out.println("ERROR: Matrix dimensions do not match");
        	return C; 
        } else {
	        for (int i = 0; i < aRows; i++) { // aRow
	            for (int j = 0; j < bColumns; j++) { // bColumn
	                    C[i][j] = A[i][j] - B[i][j];
	                }
	            }
	            return C; 
        }
    }
    @SuppressWarnings("unused")
	public static double[][] Addup_Matrices(double[][] ...M){
        boolean error_missmatch = false;
        int Rows = 0, Columns = 0; 
      //---------------------------------------------------------------------
      //   check that all input matrices have the same dimensions: 
      //---------------------------------------------------------------------
        for(double[][] I: M) {
        	Rows = I.length;
        	Columns = I[0].length;
        	for(double[][] J: M) {
                if(Rows!=J.length || Columns!=J[0].length) {
                	System.out.println("ERROR: Matrix dimensions do not match "+Rows+", "+Columns);
                	error_missmatch=true;
                }
        	}
        }
       //---------------------------------------------------------------------
    	double[][] C = new double[Rows][Columns];
        if (error_missmatch) {
        	//System.out.println("ERROR: Matrix dimensions do not match "+Rows+", "+Columns);
        	return C; 
        } else {
	        for (int i = 0; i < Rows; i++) { // aRow
	            for (int j = 0; j < Columns; j++) { // bColumn
	            	for(double[][] I: M) {
	                    C[i][j] = C[i][j] + I[i][j];
	            	}
	            }
	        }
	            return C; 
        }
    }
    public static double[][] Inverse_Matrix(double[][] A){
        int aRows = A.length;
        int aColumns = A[0].length;
    	double[][] X = new double[aRows][aColumns];
    			
    			return X; 
    }
    public static double[][] normalizeVector(double[][] vector) {
    	double lengthVector = Math.sqrt(vector[0][0]*vector[0][0] + vector[1][0]*vector[1][0] + vector[2][0]*vector[2][0]);
    	vector[0][0] = vector[0][0]/lengthVector;
    vector[1][0] = vector[1][0]/lengthVector;
    vector[1][0] = vector[2][0]/lengthVector;
    return vector;
    }
    
    public static double[][] vectorDotSquare(double[][] vector){
    	for(int i=0;i<vector.length;i++) {
    		vector[i][0] =  vector[i][0] * vector[i][0];
    	}
    	return vector;
    }
	public static double[] Spherical2Cartesian_Velocity(double[] X) {
		double[] result = new double[3];
		result[0]  =  X[0] * Math.cos(X[1]) * Math.cos(X[2]);
		result[1]  =  X[0] * Math.cos(X[1]) * Math.sin(X[2]);
		result[2]  = -X[0] * Math.sin(X[1]);
		// Filter small errors from binary conversion: 
		for(int i=0;i<result.length;i++) {if(Math.abs(result[i])<1E-9) {result[i]=0; }}
		if(result[0]==-0.0) {result[0]=0;}
		if(result[1]==-0.0) {result[1]=0;}
		if(result[2]==-0.0) {result[2]=0;}
		return result; 
		}		
	public static double[] Cartesian2Spherical_Velocity(double[] X) {
		double[] result = new double[3];
		result[1] = -Math.atan(X[2]/(Math.sqrt(X[0]*X[0] + X[1]*X[1])));
		result[0] =  Math.sqrt(X[0]*X[0] + X[1]*X[1] + X[2]*X[2]);
		result[2] =  Math.atan2(X[1],X[0]);
		/*
		if(X[1]<0 && X[0]>0) {
			result[2]= PI + Math.abs(result[2]);
		} else if (X[1]<0 && X[0]<0) {
			result[2]= PI/2 + Math.abs(result[2]);
		}
		*/
		// Filter small errors from binary conversion: 
		for(int i=0;i<result.length;i++) {if(Math.abs(result[i])<1E-9) {result[i]=0; }}
		return result; 
		}
	public static double[] Spherical2Cartesian_Position(double[] r_spherical) {
		double[] result = new double[3];
		result[0] = r_spherical[2]*Math.cos(r_spherical[0])*Math.cos(r_spherical[1]);
		result[1] = r_spherical[2]*Math.cos(r_spherical[1])*Math.sin(r_spherical[0]);
		result[2] = r_spherical[2]*Math.sin(r_spherical[1]);
		return result;
	}
	
	public static double[][] Quaternions2Euler(double[][] Quaternions){
		double[][] EulerAngles = {{0},{0},{0}};
		
		double a = Quaternions[0][0];
		double b = Quaternions[1][0];
		double c = Quaternions[2][0];
		double d = Quaternions[3][0];
		
		EulerAngles[1][0] = Math.asin(-2*(b*d - a*c));
		if(Double.isNaN(EulerAngles[1][0])){
			EulerAngles[1][0] = Theta;
		} else {
			Theta = EulerAngles[1][0] ;
		}
		double range = 0.0;
		
		if(EulerAngles[1][0] > PI/2 - range) {
			double delta = Math.atan2(2*(c*d + a*b) - 2*(b*c + a*d) , 2*(b*d + a*c) + (a*a - b*b + c*c - d*d));
			EulerAngles[0][0] = delta + Psi;
			EulerAngles[2][0] =  Psi;
		} else if (EulerAngles[1][0] < -PI/2 + range) {
			double delta = Math.atan2(2*(c*d + a*b) + 2*(b*c + a*d), 2*(b*d + a*c) - (a*a - b*b + c*c - d*d));
			EulerAngles[0][0] = delta - Psi;
			EulerAngles[2][0] = Psi;		
		} else {
			EulerAngles[0][0] =   Math.atan2( 2*(c*d+a*b),(a*a-b*b-c*c+d*d));
			EulerAngles[2][0] = - Math.atan2( 2*(b*c + a*d),(a*a + b*b - c*c - d*d));	
		}
		
		if(Double.isNaN(EulerAngles[0][0])){
			EulerAngles[0][0] = 0;
		}
		if(Double.isNaN(EulerAngles[2][0])){
			EulerAngles[2][0] = 0;
		} else {
			Psi = EulerAngles[2][0];	
		}
		return EulerAngles;
	}
	
	public static double[][] Quaternions2Euler2(double[][] Quaternions){
		
		double[][] EulerAngles = {{0},{0},{0}};
		
		double w = Quaternions[0][0];
		double x = Quaternions[1][0];
		double y = Quaternions[2][0];
		double z = Quaternions[3][0];

		
		double r11 = -2*(y*z - w*x);
		double r12 = w*w - x*x - y*y + z*z;
		double r21 = 2*(x*z + w*y);
		double r31 = -2*(x*y - w*z);
		double r32 = w*w + x*x - y*y - z*z;
	/**	
	case xyz:
	      threeaxisrot( -2*(q.y*q.z - q.w*q.x),
	                    q.w*q.w - q.x*q.x - q.y*q.y + q.z*q.z,
	                    2*(q.x*q.z + q.w*q.y),
	                   -2*(q.x*q.y - q.w*q.z),
	                    q.w*q.w + q.x*q.x - q.y*q.y - q.z*q.z,
	                    res);
		**/
		double[][] result = {{0},{0},{0}};
		EulerAngles  = threeaxisrot(r11, r12, r21, r31, r32);
		EulerAngles[0][0] = result[2][0];
		EulerAngles[1][0] = result[1][0];
		EulerAngles[2][0] = result[0][0];
				
			return EulerAngles;
	}
	
	private static double[][] threeaxisrot(double r11, double r12, double r21, double r31, double r32){
		  double[][] res = {{0},{0},{0}};
		  res[0][0] = Math.atan2( r31, r32 );
		  res[1][0] = Math.asin ( r21 );
		  res[2][0] = Math.atan2( r11, r12 );
		  return res;
		}
	
	public static double[][] Quaternions2Euler3(double[][] Quaternions){
		double[][] EulerAngles = {{0},{0},{0}};
		
		double w = Quaternions[0][0];
		double x = Quaternions[1][0];
		double y = Quaternions[2][0];
		double z = Quaternions[3][0];
		
		double C11 = w*w + x*x - y*y - z*z;
		double C12 = 2* ( x*y - w*z );
		double C13 = 2* ( x*z + w*y );		
		double C21 = 2* ( x*y + w*z );
		double C33 = w*w - x*x - y*y + z*z;
		double C32 = 2* ( y*z + w*x );
		double C31 = 2* ( x*z - w*y );
		
		double E1=0;
		double E2=0;
		double E3=0;
		
		if(C31!=1 || C31!=-1) {
			E2 = - Math.asin(C31);
			//E2 = PI + Math.asin(C31);
			E1 = Math.atan2(C32/Math.cos(E2), C33/Math.cos(E2));
			//E3 = Math.atan2(C32/Math.cos(E2), C33/Math.cos(E2));
			E3 = Math.atan2(C21/Math.cos(E2), C11/Math.cos(E2));
			//E1 = Math.atan2(C21/Math.cos(E2), C11/Math.cos(E2));
			//System.out.println("case 1");
		} else {
			 E3 = 0;//Theta;
			if(C31 == -1) {
				E2 = PI/2;
				E1 = E3 + Math.atan2(C12, C13);
			} else {
				E2 = - PI/2;
				E1 = -E3 + Math.atan2(-C12,  -C13);
			}
		}

		Theta = E3;
		
		if(!Double.isNaN(E1)) {
		EulerAngles[0][0] = E1;
		}
		if(!Double.isNaN(E2)) {
		EulerAngles[1][0] = E2;
		}
		if(!Double.isNaN(E3)) {
		EulerAngles[2][0] = E3;
		}
		return EulerAngles;
	}
	
	public static Quaternion Euler2Quarternions(Vector3 E){
		Quaternion Quat = new Quaternion();
		Quat.w = Math.cos(Math.toRadians(E.x/2)) * Math.cos(Math.toRadians(E.y/2)) * Math.cos(Math.toRadians(E.z/2)) 
				+ Math.sin(Math.toRadians(E.x/2)) * Math.sin(Math.toRadians(E.y/2)) * Math.sin(Math.toRadians(E.z/2));
		
		Quat.x = Math.sin(Math.toRadians(E.x/2)) * Math.cos(Math.toRadians(E.y/2)) * Math.cos(Math.toRadians(E.z/2)) 
				- Math.cos(Math.toRadians(E.x/2)) * Math.sin(Math.toRadians(E.y/2)) * Math.sin(Math.toRadians(E.z/2));
		
		Quat.y = Math.cos(Math.toRadians(E.x/2)) * Math.sin(Math.toRadians(E.y/2)) * Math.cos(Math.toRadians(E.z/2)) 
				+ Math.sin(Math.toRadians(E.x/2)) * Math.cos(Math.toRadians(E.y/2)) * Math.sin(Math.toRadians(E.z/2));
		
		Quat.z =  Math.cos(Math.toRadians(E.x/2)) * Math.cos(Math.toRadians(E.y/2)) * Math.sin(Math.toRadians(E.z/2)) 
				+ Math.sin(Math.toRadians(E.x/2)) * Math.sin(Math.toRadians(E.y/2)) * Math.cos(Math.toRadians(E.z/2));
		
		return Quat;
	}

}