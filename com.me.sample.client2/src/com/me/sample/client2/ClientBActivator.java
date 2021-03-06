package com.me.sample.client2;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import com.me.fibonacciservice.FibonacciService;
import com.me.fibonacciservice.FibServiceTracker;

public class ClientBActivator implements BundleActivator {


	private FibServiceTracker m_tracker = null;
	private MyThread m_thread;
	;

	@Override
	public void start(BundleContext context) throws Exception {
		
		m_tracker = new FibServiceTracker(context, FibonacciService.class.getName(), null);
		m_tracker.open();
		m_thread = new MyThread(m_tracker);
		m_thread.start();

	}
	@Override
	public void stop(BundleContext context) throws Exception {

		m_tracker.close();
		m_thread.stopThread();
		m_thread.join();
	}
	
	private static class MyThread extends Thread {

		private volatile boolean active = true;
		private FibonacciService m_service;
		private int m_iterations = 0;
		private int m_maxIterations = 3;
		private FibServiceTracker m_tracker;
		

		public MyThread(FibServiceTracker tracker) {
			m_tracker = tracker;
		}

		public void run() {
			m_iterations = 0;
			while (active) {
				
				if(m_service != null){
					System.out.println("Client-B = " + m_service.getNextFib());
					m_iterations++;
				}
				else
				{
					m_service = (FibonacciService) m_tracker.getService();
					System.out.println("Service not registered");
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.println("Thread interrupted " + e.getMessage());
				}
				if(m_iterations > m_maxIterations)
				{
					stopThread();
				}
			}
		}

		public void stopThread() {
			active = false;
		}
		
		@SuppressWarnings("unused")
		public void setMaxIterations(int nr)
		{
			m_maxIterations = nr;
		}

	}
}
