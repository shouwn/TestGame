package ganta;

import java.util.Random;

public class Baron{

	private boolean isInGaming = false;

	public static final int HP = 500;

	private int currentHp = HP;

	private static final int DECREMENT = 1;
	private static final int ATTACK_DMG = 50;
	private static final int INTERVAL = 20;

	private Processor processor;
	private BaronListener listener;

	public Baron(Processor processor) {
		this.processor = processor;
	}

	public void setListener(BaronListener listener) {
		this.listener = listener;
	}

	private void hpDecrement(){
		this.currentHp -= new Random().nextInt(3);
		if(currentHp <= 0)
			processor.add(() -> this.listener.onGameOver());
		else
			processor.add(() -> this.listener.onHpChange(this.currentHp));
	}

	public void gameStart(){
		isInGaming = true;
		new GameThread().start();

		processor.add(() -> this.listener.onGameStart());
	}

	public void attack(){
		if(isInGaming) {
			isInGaming = false;
			currentHp -= ATTACK_DMG;

			processor.add(() -> this.listener.onAttack());
			if(currentHp <= 0)
				processor.add(() -> this.listener.onGameClear());
			else
				processor.add(() -> this.listener.onGameOver());
		}
	}

	class GameThread extends Thread{

		public GameThread() {
			super();
			setDaemon(true);
		}

		@Override
		public void run() {

			while(isInGaming){
				Baron.this.hpDecrement();

				try {
					Thread.sleep(INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}


