package com.company;
import java.io.*;
import java.util.*;

public class Driver {
	/* Example input file:
	 * 
	 * 0,1,0,7,5,3
     * 2,0,0,3,2,2
     * 3,0,2,9,0,2
     * 2,1,1,2,2,2
     * 0,0,2,4,3,3
     * 
     * # of rows = # of processes
     * String[] args = total amount of each resource (length 3)
     * 1st 3 columns = current allocation to process i of resource j
     * Last 3 columns = requirement of process i for resource j
     * 
	 * */
	
	public static void main(String[] args) throws FileNotFoundException {
		File infile = new File("\\Users\\rmone\\Documents\\banker_infile.txt");
		int nResources = args.length;
		int[] resources = new int[nResources];
		for(int i = 0; i < nResources; i++) {
			resources[i] = Integer.parseInt(args[i].trim());
		}
		
		Bank theBank = new BankImpl(resources);
		Thread[] workers = new Thread[Customer.COUNT]; // the customers
		
		// read in values and initialize the matrices
		
	    Scanner scan = new Scanner(infile);
	    
	    for(int threadNum = 0; threadNum < workers.length; threadNum++) {
	    	String line = scan.nextLine();
	    	String[] lineVector;
	        lineVector = line.split(",");
			int[] allocated = new int[nResources];
			int[] maxDemand = new int[nResources];
			
			for(int i = 0; i < nResources; i++) {
				allocated[i] = Integer.parseInt(lineVector[i]);
			}
			
			for(int i = nResources; i < nResources * 2; i++) {
				maxDemand[i - nResources] = Integer.parseInt(lineVector[i]);
			}
			
			workers[threadNum] = new Thread(new Customer(threadNum, maxDemand, theBank));
			theBank.addCustomer(threadNum, allocated, maxDemand);
	    }
		
		System.out.println("Driver: created threads"); // start the customers
		
		for(int i = 0; i < Customer.COUNT; i++) {
			workers[i].start();
		}
		System.out.println("Driver: started threads");
		}
}
