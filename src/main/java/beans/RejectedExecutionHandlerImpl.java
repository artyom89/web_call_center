package beans;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

//CLASS TO IMPLEMENT THE REJECTED CALLS BECAUSE THE DISPATCHER CAN HANDLE 10 THREADS
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {


        //LOG
       try {
           System.out.println(r.toString() + " is rejected");
           Dispatcher.countCantCallRejected++;

          // executor.getQueue().add(r);
       }
       catch (Exception e )
       {
           System.out.println(e.getMessage());
       }

    }


}
