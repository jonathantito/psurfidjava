package client_server;

public class Driver {
	SocketReaderFrame srf;
	public Driver(){
		ServerFrame sf = new ServerFrame(this);
		srf = new SocketReaderFrame();
		srf.setVisible(false);
	}
	public static void main(String[] args) throws Exception{
		new Driver();
	}
	public void connect(int port){
		final int newport = port;
		
		srf.setVisible(true);
		srf.jlPort.setText("Port: " + port);
		//MyServer ms = new MyServer(port, srf);
		Thread r = new Thread(){
			public void run(){
				new MyServer(newport,srf);
			}
		};
		r.start();
		
	}
}
