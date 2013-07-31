package com.me.fibonacciservice.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.me.fibonacciservice.FibonacciService;


public class FibonacciActivator implements BundleActivator {

	@SuppressWarnings("rawtypes")
	private ServiceRegistration m_serviceReg;
	private FibonacciService m_fibonacciService;
	@Override
	public void start(BundleContext context) throws Exception {

		int index = readHistory(context);
		m_fibonacciService = new FibonacciImpl(index);
		m_serviceReg = context.registerService(FibonacciService.class.getName(), m_fibonacciService, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		m_serviceReg.unregister();
		writeHistory(context);
	}

	private void writeHistory(BundleContext context) throws IOException {
		File log = context.getDataFile("log.txt");

		if (log.exists() && !log.delete()) {
			throw new IOException("Unable to delete previous log file!");
		}
		write(context, log);
	}

	public void write(BundleContext context, File log) throws IOException
	{
		
		PrintWriter output = null;
		IOException original = null;
		try {
			output = new PrintWriter(new FileWriter(log));
			output.print(m_fibonacciService.getIndex());

		} catch (IOException ex) {
			original = ex;
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} finally {
				if (original != null) {
					throw original;
				}

			}
		}
	}

	private int readHistory(BundleContext context) throws IOException {
		File log = context.getDataFile("log.txt");
		int result = 0;

		if (log.isFile()) {
			result = read(context, log);
		}
		return result;
	}

	private int read(BundleContext context, File log) throws IOException
	{
		int index = 0;
		BufferedReader input = null;
		IOException original = null;
		try {
			input = new BufferedReader(new FileReader(log));
			for (String line = input.readLine(); line != null; line = input.readLine()) {
				index = Integer.parseInt(line);
			}
		} catch (IOException ex) {
			original = ex;
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} finally {
				if (original != null) {
					throw original;
				}
			}
		}

		return index;
	}


}
