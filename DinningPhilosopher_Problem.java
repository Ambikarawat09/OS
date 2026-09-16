import java.util.concurrent.Semaphore;
class SharedData
{
    Semaphore [] fork=new Semaphore[5];
    SharedData(){
    for(int i=0;i<5;i++)
    {
        fork[i]=new Semaphore(1);
    }
   }
    void eat(int philosopherid) throws InterruptedException
    {
        Semaphore leftfork=fork[philosopherid-1];
        Semaphore rightfork=fork[philosopherid%5];
        if(philosopherid==5)
        {
            rightfork.acquire();
            leftfork.acquire();
        }
        else
        {
            leftfork.acquire();
            rightfork.acquire();
        }
        System.out.println("Philosopher "+philosopherid+" is eating");
        Thread.sleep(500); 
        leftfork.release();
        rightfork.release();
        System.out.println("Philosopher "+philosopherid+" finished eating");     
    }
}
class Philosopher extends Thread{
    SharedData obj;
    int philosopherid;
    Philosopher(SharedData obj,int philosopherid)
    {
        this.obj=obj;
        this. philosopherid= philosopherid;
    }
    public void run()
    {
        for(int i=1;i<=5;i++)
        {
            try{
                 System.out.println("Philosopher "+philosopherid+" is thinking");
                 Thread.sleep(500); 
                 System.out.println("Philosopher "+philosopherid+" is hungry"); 
                 obj.eat(philosopherid);
                 Thread.sleep(500);
            }
            catch(InterruptedException e)
            {
                System.out.println("Philosopher Interrupted");
            }
        }
    }
}
public class DinningPhilosopher_Problem {
    public static void main(String[] args) {
        SharedData obj=new SharedData();
        Philosopher p1=new Philosopher(obj,1);
        Philosopher p2=new Philosopher(obj,2);
        Philosopher p3=new Philosopher(obj,3);
        Philosopher p4=new Philosopher(obj,4);
        Philosopher p5=new Philosopher(obj,5);
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }
}
