package com.me.fibonacciservice.Impl;

import com.me.fibonacciservice.FibonacciService;

public class FibonacciImpl implements FibonacciService{

	private int m_index;
	public FibonacciImpl(int index)
	{
		this.m_index = index;
	}
	@Override
	public long getNextFib() {
		
		return fib(m_index++);
	}
	@Override
	public int getIndex()
	{
		return m_index;
	}
	
	public static int fib(int n) {
        if (n < 2) {
           return n;
        }
        else {
        	return fib(n-1)+fib(n-2);
        }
}
	
}
