package beans;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

//CLASS TO IMPLEMENT THE REJECTED CALLS BECAUSE THE DISPATCHER CAN HANDLE 10 THREADS
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        //IMPLEMENT SOME LIST WHERE TO KEEP INCOMMING CALLS
        //LOG
       try {
           System.out.println(r.toString() + " is rejected");
           executor.getQueue().add(r);
       }
       catch (Exception e )
       {
           System.out.println(e.getMessage());
       }

    }


}
