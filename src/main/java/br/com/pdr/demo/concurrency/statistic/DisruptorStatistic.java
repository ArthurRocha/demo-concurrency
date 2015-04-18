package br.com.pdr.demo.concurrency.statistic;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import br.com.pdr.demo.concurrency.ConcurrencyApplication;
import br.com.pdr.demo.concurrency.service.HighProccessService;
import br.com.pdr.demo.concurrency.service.Service;
import br.com.pdr.demo.concurrency.service.SmallProccessService;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class DisruptorStatistic extends Statistic {

	boolean processesEnds;
	
	@Override
	public void init() {
		// NOP
	}

	@Override
	public void destroy() {
		// NOP
	}
	
	@Override
	public void executeHighProccess(HighProccessService service)
			throws Exception {
		executeDisruptor(service);
	}
	
	@Override
	public void executeSmallProccess(SmallProccessService service)
			throws Exception {
		executeDisruptor(service);
	}
	
	public void setProcessesEnds(boolean endOfBatch) {
		this.processesEnds = endOfBatch;
	}
	
	public boolean isProcessesEnds() {
		return processesEnds;
	}
	
	// https://github.com/LMAX-Exchange/disruptor/wiki/Getting-Started
	// Wait Strategies
	private void executeDisruptor(final Service service) throws Exception {
		
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		int sliceData = Math.floorDiv(ConcurrencyApplication.SIZE_OF_THE_GAME,
				availableProcessors);
		
		class SlicedProcessEventHandler implements EventHandler<SlicedEvent>
		{
		    public void onEvent(SlicedEvent event, long sequence, boolean endOfBatch)
		    {
		    	service.playSliced(event.start, event.end);
		        
		        setProcessesEnds(endOfBatch);
		    }
		}
		
		// Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();
        
        // The factory for the event
 		ProcessSlicedFactory factory = new ProcessSlicedFactory();

         // Specify the size of the ring buffer, must be power of 2.
         int bufferSize = 1024;

         // Construct the Disruptor
         Disruptor<SlicedEvent> disruptor = new Disruptor<SlicedEvent>(factory, bufferSize, executor);

         // Connect the handler
         disruptor.handleEventsWith(new SlicedProcessEventHandler());

         // Start the Disruptor, starts all threads running
         disruptor.start();
         
         // Get the ring buffer from the Disruptor to be used for publishing.
         RingBuffer<SlicedEvent> ringBuffer = disruptor.getRingBuffer();

         ProcesstProducer producer = new ProcesstProducer(ringBuffer);

		int start = 0;
		int end = sliceData;
		for (int i = 0; i < availableProcessors; i++) {
			producer.onData(start, end);
			start = end + 1;
			end += sliceData;
		}
		
		while (!isProcessesEnds()) {
			Thread.yield();
		}
	}
	
}

class SlicedEvent
{
    int start;
    int end;

}

class ProcessSlicedFactory implements EventFactory<SlicedEvent>
{
    public SlicedEvent newInstance()
    {
        return new SlicedEvent();
    }
}

class ProcesstProducer
{
    private final RingBuffer<SlicedEvent> ringBuffer;

    public ProcesstProducer(RingBuffer<SlicedEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(final int start, final int end)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
        	SlicedEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
                                                        // for the sequence
        	event.start = start;
        	event.end = end;
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}

//// Executor that will be used to construct new threads for consumers
//Executor executor = Executors.newCachedThreadPool();
//
//// The factory for the event
//LongEventFactory factory = new LongEventFactory();
//
//// Specify the size of the ring buffer, must be power of 2.
//int bufferSize = 1024;
//
//// Construct the Disruptor
//Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, executor);
//
//// Connect the handler
//disruptor.handleEventsWith(new LongEventHandler());
//
//// Start the Disruptor, starts all threads running
//disruptor.start();
//
//// Get the ring buffer from the Disruptor to be used for publishing.
//RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
//
//LongEventProducer producer = new LongEventProducer(ringBuffer);
//
//ByteBuffer bb = ByteBuffer.allocate(8);
//for (long l = 0; true; l++)
//{
//  bb.putLong(0, l);
//  producer.onData(bb);
//  Thread.sleep(1000);
//}

//class LongEvent
//{
//    private long value;
//
//    public void set(long value)
//    {
//        this.value = value;
//    }
//}
//
//class LongEventFactory implements EventFactory<LongEvent>
//{
//    public LongEvent newInstance()
//    {
//        return new LongEvent();
//    }
//}
//
//class LongEventHandler implements EventHandler<LongEvent>
//{
//    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
//    {
//        System.out.println("Event: " + event);
//    }
//}
//
//class LongEventProducer
//{
//    private final RingBuffer<LongEvent> ringBuffer;
//
//    public LongEventProducer(RingBuffer<LongEvent> ringBuffer)
//    {
//        this.ringBuffer = ringBuffer;
//    }
//
//    public void onData(ByteBuffer bb)
//    {
//        long sequence = ringBuffer.next();  // Grab the next sequence
//        try
//        {
//            LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
//                                                        // for the sequence
//            event.set(bb.getLong(0));  // Fill with data
//        }
//        finally
//        {
//            ringBuffer.publish(sequence);
//        }
//    }
//}
