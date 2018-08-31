import beans.Dispatcher;
import org.junit.Assert;
import org.junit.Test;

public class DispatcherTest {

    @Test
    public void dispatchTenCallsThreads() {

      try {
          Dispatcher dispatcher = new Dispatcher();

          dispatcher.startGeneratingCallsP(10);

          while(dispatcher.getExecutorPool().getActiveCount()!=0){
                ///WAIT TO ALL THREADS TO FINISH TO CONTINUE
          }
          //dispatcher.stopGeneratingCalls();


          Assert.assertEquals(10, Dispatcher.countCantCall);
          Assert.assertEquals(0, Dispatcher.countCantCallRejected);
      }

        catch ( Exception ex) {
          ex.getMessage();
        }


    }
}
