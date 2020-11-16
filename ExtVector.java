package com.company;
import java.util.List;
import java.util.Vector;

//--------------------------------------------------------------------------
// class ExtVector
// --------------------------------------------------------------------------
class ExtVector {
  private Vector<int[]> v;
  int columns = 0;

  ExtVector() { v = new Vector<int[]>(); }
  ExtVector(int size,  int[] k) {
    v = new Vector<int[]>(size);
    for (int i = 0; i < size; ++i) { v.add(k); }
  }
  ExtVector(int rows, int columns){
	  this.columns = columns;
	  v = new Vector<int[]>(rows);
  }
  ExtVector(ExtVector other) {
    int size = other.size();
    v = new Vector<int[]>(size);
    for (int i = 0; i < size; ++i) {
      v.add(other.v.get(i));
    }
  }
  ExtVector(Vector<int[]> other)  {
    v = new Vector<int[]>(other.size());
    for (int i = 0; i < other.size(); ++i) {
      v.add(other.get(i));
    }
  }
  ExtVector(List<int[]> li) {
    v = new Vector<int[]>(li.size());
    for (int[] el : li) {
      v.add(el);
    }
  }
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
  //------------------------------------------------------------------
  boolean isEmpty()   { return v.isEmpty(); }
  int size()  { return v.size(); }
  //------------------------------------------------------------------
  void add(int[] val) { v.add(val); }
  void clear() {
    while (!isEmpty()) { v.remove(v.size() - 1); }
  }
  //------------------------------------------------------------------
  int[] elementAt(int i)  { return v.get(i); }
  int[] get(int i) { return v.get(i); }
  void set(int i, int[] e) { v.set(i, e); }

  //  typename Vector<E>::_iterator begin()  { return v.begin(); }
//  typename Vector<E>::_iterator end()    { return v.end();   }
//------------------------------------------------------------------
  /*ExtVector addIncrement( ExtVector other) {
    for (int i = 0; i < other.v.size(); ++i) {
      v.set(i, v.get(i) + other.v.get(i));
    }
    return this;
  }*/
  ExtVector subDecrement(ExtVector other) {
    for(int i = 0; i < other.size(); i++) {
    	int[] secondNum = other.get(i);
    	
    	for(int j = 0; j < this.get(i).length; i++) {
    		this.get(i)[j] -= secondNum[j];
    	}
    }
    return this;
  }/*
  //------------------------------------------------------------------
  ExtVector add( ExtVector other)  {
    ExtVector temp = new ExtVector(v);
    temp.addIncrement(other);
    return temp;
  }*/
  ExtVector sub( ExtVector other)  {
    ExtVector temp = new ExtVector(v);
    temp.subDecrement(other);
    return temp;
  }/*
  ExtVector mul(int k)  {
    ExtVector temp = new ExtVector(v);
    for (int i = 0; i < v.size(); ++i) { temp.set(i, temp.get(i) * k); }            // returns v * k
    return temp;
  }
  //------------------------------------------------------------------
  boolean lt( ExtVector other)  {
    for (int i = 0; i < other.size(); ++i) {
      if (v.get(i) >= other.v.get(i)) {
        return false;
      }
    }
    return true;
  }
  boolean le( ExtVector other)  {
    for (int i = 0; i < other.size(); ++i) {
      if (v.get(i) > other.v.get(i)) {
        return false;
      }
    }
    return true;
  }
  public boolean gt( ExtVector other)  { return !le(other);  }
  public boolean ge( ExtVector other)  { return !lt(other); }
  public boolean equals( ExtVector other)  {
    for (int i = 0; i < other.size(); ++i) {
      if (v.get(i) != other.v.get(i)) {
        return false;
      }
    }
    return true;
  }
  public boolean ne( ExtVector other) { return !equals(other); }
  public boolean isZero()  {
    for (Integer el : v) {
      if (el != 0) { return false; }
    }
    return true;
  }
  public boolean isK(int k) {
    for (int i = 0; i < v.size(); ++i) {
      if (v.get(i) != k) { return false; }
    }
    return true;
  }
  //------------------------------------------------------------------
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (v.isEmpty()) { return "ExtVector<T> is empty]\n"; }
    int i = 0;
    for (Integer el : v) {
      sb.append(el);
      if (i++ < v.size() - 1) { sb.append(" "); }
    }
    return sb.toString();
  }*/
//------------------------------------------------------------------
//  public static void run_tests() {
//    ExtVector v = new ExtVector(Arrays.asList(1, 2, 3, 4, 5));
//    ExtVector v2 = new ExtVector(Arrays.asList(1, 0, 2, 0, 3));
//    so.println("v  is: " + v);
//    so.println("v2 is: " + v2);
//
//    v.add_increment(v2);
//    so.println("v += v2 is: " + v);
//    so.println("v +  v2 is: " + (v.add(v2)));
//    so.println("v -  v2 is: " + (v.sub(v2)));
//    so.println("v -  v  is: " + (v.sub(v)));
//
//    so.println("3 * v is: " + (v.mul(3)));
//    so.println("v * 2 is: " + (v.mul(2)));
//
//    so.println("v < 2v ? " + (v.lt(v.mul(2))));
//    so.println("2v < v ? " + ((v.mul(2)).lt(v)));
//    so.println("v == v ? " + (v.equals(v)));
//  }
}
