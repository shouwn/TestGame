package ganta;

public class Main {
	
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	public static void main(String[] args) {

		Processor processor = new Processor();
		processor.start();
		Baron baron = new Baron(processor);
		MyFrame frame = new MyFrame(baron);
		baron.setListener(frame);

	}

}