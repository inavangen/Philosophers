import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;


public class Philosophers {

	public static void main (String [] args){

		new Philosophers().start();
		
	}

		void start(){

			Chopstick c1 = new Chopstick();
			Chopstick c2 = new Chopstick();
			Chopstick c3 = new Chopstick();
			Chopstick c4 = new Chopstick();
			Chopstick c5 = new Chopstick();

			int numOfChopsticks = 5;
			Chopstick [] chopstickArray = {c1, c2, c3, c4, c5};
/*
			new Philosopher(c1, c2, c3, c4, c5, numOfChopsticks).start();
			new Philosopher(c1, c2, c3, c4, c5, numOfChopsticks).start();
			new Philosopher(c1, c2, c3, c4, c5, numOfChopsticks).start();
			new Philosopher(c1, c2, c3, c4, c5, numOfChopsticks).start();
			new Philosopher(c1, c2, c3, c4, c5, numOfChopsticks).start();
*/

			new Philosopher(chopstickArray, numOfChopsticks).start();
			new Philosopher(chopstickArray, numOfChopsticks).start();
			new Philosopher(chopstickArray, numOfChopsticks).start();
			new Philosopher(chopstickArray, numOfChopsticks).start();
			new Philosopher(chopstickArray, numOfChopsticks).start();
		}

	public class Philosopher extends Thread {

		Chopstick c1;
		Chopstick c2;
		Chopstick c3;
		Chopstick c4;
		Chopstick c5;
		int numOfChopsticks;
		Chopstick [] chopstickArray;

		Philosopher(Chopstick [] chopstickArray, int numOfChopsticks){
			this.chopstickArray = chopstickArray;
			this.numOfChopsticks = numOfChopsticks;
		}	

		/*Philosopher(Chopstick c1, Chopstick c2, Chopstick c3, Chopstick c4, Chopstick c5, int numOfChopsticks){
			this.c1 = c1;
			this.c1 = c2;
			this.c1 = c3;
			this.c1 = c4;
			this.c1 = c5;
			this.numOfChopsticks = numOfChopsticks;

		}*/



		public void run() {

			boolean philosopherExsists = true;

			while (philosopherExsists) {

		
				
					System.out.println("Philosopher " + this.getName() + " wants to think");
					think();		// is now thinking
					System.out.println("Philosopher " + this.getName() + " wants to eat");
					eat();		
										// wants to eat


			}				
		}


		public void eat(){

			boolean haveEaten = false;

			while (haveEaten == false){
				if(lockChopsticks()){ // if true					

					// eat
					System.out.println("Philosopher " + this.getName() + "Nom nom!");
					try{
						Thread.sleep(4000);
					} catch (InterruptedException e){
					}
					
					// unlock chopsticks
					unlockChopsticks();
					System.out.println("Philosopher " + this.getName() + " finished eating");
					haveEaten = true;

				} else {
				
					System.out.println("Not enough chopsticks free. Need to wait");
					try{
						Thread.sleep(500);
					} catch (InterruptedException ex){

					}
				}
			}	

		}

		public void think(){
			System.out.println("Philosopher " + this.getName()+ "......");
			try{
				Thread.sleep(2000);
			} catch (InterruptedException e){

			}
		}

	

		public boolean lockChopsticks (){

			int count = 0;
			System.out.println("NUM OF CHOPSTICKS LEFT: " + numOfChopsticks);

			if (numOfChopsticks >= 2){
				while (count <= 2){
					for(int i = 0; i < chopstickArray.length; i++){
						if(chopstickArray[i].isLocked() == false){
							chopstickArray[i].lock();
							numOfChopsticks--;
							count++;
						}
					}
				}
				count = 0;
				return true;
			} else {
				System.out.println("!!!!!!!!!There is not enough chopsticks free");
				return false;
			}
		}

		public void unlockChopsticks (){

			int count = 0;

			while (count <= 2){
				for(int i = 0; i < chopstickArray.length; i++){
					if(chopstickArray[i].isLocked()){
						chopstickArray[i].unlock();
						numOfChopsticks++;
						count++;
					}
				}
			}
			count = 0;			
		}
	}

	public class Chopstick  {

			private final ReentrantLock lock = new ReentrantLock();

			public void lock(){
				lock.lock();

			}

			public void unlock(){
				lock.unlock();
			}

			public boolean isLocked(){
				return lock.isLocked();
			}

	}

}