import beans.Dispatcher;
import org.junit.Assert;
import org.junit.Test;

/**
 * Dispatcher Tester
 */
public class DispatcherTest {

    @Test
    public void dispatchTenCallsThreads() {

      try {
          Dispatcher dispatcher = new Dispatcher();

          dispatcher.startGeneratingCallsP(10);

          while(dispatcher.getExecutorPool().getActiveCount()!=0){
                ///WAIT TO ALL THREADS TO FINISH TO CONTINUE
              dispatcher.getExecutorPool().shutdown();
          }
          //dispatcher.stopGeneratingCalls();


          Assert.assertEquals(10, Dispatcher.countCantCall);
          Assert.assertEquals(0, Dispatcher.countCantCallRejected);
      }

        catch ( Exception ex) {
          ex.getMessage();
        }

    }

    //By default the pool can handle only 10 calls at the same time, but have a work queue who can handle some excess. I add another 10 calls, so it can
    //handle 20 calls before start rejecting other calls
    @Test
    public void dispatchMoreCallsThatPoolCanHandle()  {

        try {
            Dispatcher dispatcher = new Dispatcher();

            dispatcher.startGeneratingCallsP(30);


            while(dispatcher.getExecutorPool().getActiveCount()!=0){
                ///WAIT TO ALL THREADS TO FINISH TO CONTINUE
                dispatcher.getExecutorPool().shutdown();
            }
            //dispatcher.stopGeneratingCalls();

           // Thread.sleep(25000);

            Assert.assertEquals(20, Dispatcher.countCantCall);
            Assert.assertEquals(10, Dispatcher.countCantCallRejected);
        }

        catch ( Exception ex) {
            ex.getMessage();
        }
    }
}
