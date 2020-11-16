package com.company;
// implementation of the Bank

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.min;
import com.company.ExtVector;

public class BankImpl implements Bank {
	private int m;	// the number of threads in the system (rows)
	private int n;	// the number of resources (columns)
	
	private int[] available; // the amount available of each resource
	private ExtVector maximum; // the maximum demand of each thread
	private ExtVector allocation; // the amount currently allocated to each thread
	private ExtVector need; // the remaining needs of each thread
	private ArrayList<Integer> Finish; // indicates state of thread (0 = ongoing, 1 = finished)
	private ArrayList<Integer> safeSeq;
		
	private void showAllMatrices() {
		System.out.println("      ALLOCATED      MAXIMUM      NEED"); //6 spaces between each matrix
		for(int i = 0; i < m; i++) {
			System.out.println("      " + Arrays.toString(allocation.get(i)) + "      " + Arrays.toString(maximum.get(i)) + "      " + Arrays.toString(need.get(i)));
		}
		System.out.print("      -------      -------      -------\n       -------      -------      -------\n");
	}
	
	//create a new bank (with resources)
	public BankImpl(int[] resources) { 
		n = resources.length;
		m = 0;
		available = new int[n];
		for(int i = 0; i < n; i++) {
			available[i] = resources[i];
		}
		maximum = new ExtVector(0, n);
		allocation = new ExtVector(new ExtVector(0, n));
		need = new ExtVector(new ExtVector(0, n));
		Finish = new ArrayList<Integer>();
		safeSeq = new ArrayList<Integer>();
	}
	
	// invoked by a thread when it enters the system; also records max demand
	public void addCustomer(int threadNum, int[] allocated, int[] maxDemand) {
		System.out.println("Adding customer " + threadNum + "...");
		m++;
		Finish.add(0); 
		safeSeq.add(0);
		allocation.add(allocated);
		maximum.add(maxDemand);	
		
		int[] needArr = new int[n];
		for(int i = 0; i < n; i++) {
			needArr[i] = maxDemand[i] - allocated[i];
			if(needArr[i] < 0) {
				needArr[i] = 0;
			}
		}
		need.add(needArr);
	}
	
	// output state for each thread
	public void getState() {
		// todo
	}
	
	public boolean isSafeState(int threadNum, int[] request) {
		int[] newAvail = new int[n];
		ExtVector newAlloc = new ExtVector(allocation);
		ExtVector newMax = new ExtVector(maximum);
		ExtVector newNeed = new ExtVector(need);
		ArrayList<Integer> finish = new ArrayList<Integer>(Collections.nCopies(m, 0));

		for(int i = 0; i < n; i++) { newAvail[i] = available[i]; }
		for(int i = 0; i < m; i++) { safeSeq.set(i, 0); }

        for(int i = 0; i < n; i++){
            newAvail[i] = newAvail[i] - request[i];
            newAlloc.get(threadNum)[i] = newAlloc.get(threadNum)[i] + request[i];
            newNeed.get(threadNum)[i] = newNeed.get(threadNum)[i] - request[i];
        }
        
        int count = 0;
        while(true) {
        	int I = -1;
        	
        	for(int i = 0; i < m; i++) {
        		boolean needLessThanAvail = true;
        		for(int j = 0; j < n; j++) {
        			if(newNeed.get(i)[j] > newAvail[j] || finish.get(i) == 1) {
        				needLessThanAvail = false;
        				break;
        			}
        		}
            	if(needLessThanAvail) {
            		I = i;
            		break;
            	}
        	}
        	
        	if(I != -1) {
        		if(count < safeSeq.size()) {
        			safeSeq.set(count, I);
        		}
        		count++;
        		finish.set(I, 1);
        		for(int i = 0; i < n; i++) {
        			newAvail[i] = newAvail[i] + newAlloc.get(I)[i];
        		}
        	}
        	else {
        		for(int i = 0; i < m; i++) {
        			if(finish.get(i) == 0) {
        				return false;
        			}
        		}
        		return true;
        	}
        	
        }
	}
	
	// make request for resources. will block until request is satisfied safely
	public synchronized boolean requestResources(int threadNum, int[] request) {
		boolean returnVal = false;
		
		System.out.print("#P" + threadNum + " RQ: " + Arrays.toString(request) + ", " + "needs:" + Arrays.toString(need.get(threadNum)) + ", available: " +  Arrays.toString(available));
		
		for(int i = 0; i < n; i++) {
			if(request[i] > available[i]) {
	    		System.out.println(" ---> DENIED");
				return false;
			}
		}
		
		returnVal = isSafeState(threadNum, request);
		
		if(returnVal = true) {
			boolean needIsZero = true;
			
			for(int j = 0; j < n; j++) {
				
				int sum = allocation.get(threadNum)[j] + request[j];
				
				if(sum > maximum.get(threadNum)[j]) {
					request[j] = sum - maximum.get(threadNum)[j];
				}
				
				allocation.get(threadNum)[j] = allocation.get(threadNum)[j] + request[j];
				available[j] = available[j] - request[j];
				need.get(threadNum)[j] = need.get(threadNum)[j] - request[j];
				if(need.get(threadNum)[j] < 0) {
					need.get(threadNum)[j] = 0;
				}
				if(need.get(threadNum)[j] != 0) {
					needIsZero = false;
				}
			}
			
    		System.out.println(" ---> APPROVED, #P" + threadNum + " now at: " + Arrays.toString(allocation.get(threadNum)));
			showAllMatrices();
    		
			if(needIsZero) {
				System.out.println("------------------------------> #P" + threadNum + " has all its resources! RELEASING ALL and SHUTTING DOWN...");
				Finish.set(threadNum, 1);
				System.out.println("======================== customer #" + threadNum + " releasing: " + Arrays.toString(allocation.get(threadNum)) + ", allocated=[0 0 0]");
				releaseResources(threadNum, new int[]{0});
			}
		}
		
		return returnVal;
	}
	
	public synchronized void releaseResources(int threadNum, int[] release) {
		for(int i = 0; i < n; i++) {
			available[i] = available[i] + allocation.get(threadNum)[i];
			allocation.get(threadNum)[i] = 0;
		}
		Thread.currentThread().interrupt();
	}
}
