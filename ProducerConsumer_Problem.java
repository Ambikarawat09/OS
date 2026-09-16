import java.util.ArrayList;
import java.util.concurrent.Semaphore;
class Storage
{
    ArrayList<Integer> items=new ArrayList<>();
    int capacity=5;
    Semaphore full=new Semaphore(0);
    Semaphore empty=new Semaphore(capacity);
    Semaphore mutex=new Semaphore(1);

    void produce(int value) throws InterruptedException
    {
      empty.acquire();
      mutex.acquire();

      items.add(value);
      System.out.println("Produced : "+value);

      full.release();
      mutex.release();
    }
    void consume()  throws InterruptedException
    {
            full.acquire();
            mutex.acquire();

            int value=items.remove(0);
            System.out.println("Consumed : "+ value);

            empty.release();
            mutex.release();
    }
}
class Producer extends Thread
{
    Storage obj;
    Producer (Storage obj)
    {
        this.obj=obj;
    }
    public void run()
    {
        for(int i=1;i<=5;i++)
        {
            try{
                obj.produce(i);
                Thread.sleep(500);
            }
            catch(InterruptedException e){
             System.out.println("Producer interrupted");
            }
        }
    }
}
class Consumer extends Thread
{
    Storage obj;
    Consumer(Storage obj)
    {
         this.obj=obj;
    }
    public void run()
    {
        for(int i=1;i<=5;i++)
        {
            try{
               obj.consume();
               Thread.sleep(500);
            }
            catch(InterruptedException e){
             System.out.println("Consumer interrupted");
            }
        }
    }
}
class ProducerConsumer_Problem
{
    public static void main(String [] args)
    {
     Storage obj=new Storage();
     Producer produce=new Producer(obj);
     Consumer consume=new Consumer(obj);
     produce.start();
     consume.start();
    }
}