package com.me.fibonacciservice;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

@SuppressWarnings("rawtypes")
public class FibServiceTracker extends ServiceTracker {

	@SuppressWarnings("unchecked")
	public FibServiceTracker(BundleContext context, String clazz, ServiceTrackerCustomizer customizer) {
		super(context, clazz, customizer);
	}
	@SuppressWarnings("unchecked")
	public Object addingService(ServiceReference reference) {
        System.out.println("Inside FibServiceTracker.addingService " + reference.getBundle());
        return super.addingService(reference);
    }
    @SuppressWarnings("unchecked")
	public void removedService(ServiceReference reference, Object service) {
        System.out.println("Inside FibServiceTracker.removedService " + reference.getBundle());
        super.removedService(reference, service);
    }

}
