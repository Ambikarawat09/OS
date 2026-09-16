import java.util.concurrent.Semaphore;

class SharedData{
    int data=0,readCount=0;
     
    Semaphore mutex=new Semaphore(1);
    Semaphore resource =new Semaphore(1);

    void read(int readerid)  throws InterruptedException
    {
        mutex.acquire();
        readCount++;
        if(readCount==1)
        {
            resource.acquire();
        }
        mutex.release();
        System.out.println("Reader "+ readerid + " is reading : "+ data);
        Thread.sleep(500);
        mutex.acquire();
        readCount--;
        if(readCount==0)
        {
            resource.release();
        }
        mutex.release();
    }
    void write(int writerid) throws InterruptedException
    {
        resource.acquire();
        data++;
         System.out.println("Writer "+ writerid + " is writing : "+ data);
        Thread.sleep(500);
        resource.release();
    }
}
class Reader extends Thread
{
     SharedData obj;
     int readerid;
     Reader( SharedData obj,int readerid)
     {
        this.readerid=readerid;
        this.obj=obj;
     }
     public void run()
     {
        for(int i=1;i<=5;i++)
        {
            try{
                obj.read(readerid);
                Thread.sleep(500);
            }
            catch(InterruptedException e)
            {
                System.out.println("Reader interrupted");
            }
        }
     }
}
class Writer extends Thread
{
     SharedData obj;
     int writerid;
     Writer( SharedData obj,int writerid)
     {
        this.writerid=writerid;
        this.obj=obj;
     }
     public void run()
     {
        for(int i=1;i<=5;i++)
        {
            try{
                obj.write(writerid);
                Thread.sleep(500);
            }
            catch(InterruptedException e)
            {
                System.out.println("Writer interrupted");
            }
        }
     }
}

public class ReaderWriter_Problem {
    public static void main(String[] args) {
        SharedData obj=new SharedData();
        Reader r1=new Reader(obj,1);
        Reader r2=new Reader(obj,2);

        Writer w1=new Writer(obj,1);
        Writer w2=new Writer(obj,2);

        r1.start();
        r2.start();

        w1.start();
        w2.start();
    }
}
