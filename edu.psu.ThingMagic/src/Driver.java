
public class Driver {
	public static void main(String[] args){
		myApp ma = new myApp();
		ma.createReader();
		ma.readTag();
		//ma.myReader.getTagIdBuffer(1, 1);
		System.out.println(ma.t.bitLength);
		System.out.println(ma.myReader.getTagIdBuffer(1, 1));
		System.out.println(ma.t);
	}
}
