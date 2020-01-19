package recaman;
import processing.core.*;
public class RECAMAIN extends PApplet {
//Define some global variables:
boolean isColored=true; //Set whether the sequence generated is colored.
int degree=1; //Control the feedback (creates some interesting results when !=1)
int iter=100; //Number of iterations

float wid;
int c;
int step=1;
int[] a;
int pos=0;
boolean up=false;
float colmax=0;
//Setup:
public void setup() {
  //setup colors:
  fullScreen();
  noFill();
  background(255);
  colorMode(HSB);
  //Calculate the initial recaman:
  a=calcSeq(ceil(iter));
  //Recurse for degree-1 iterations, using previous list as step list
  for(int i=0;i<degree-1;i++) {
    a=calcSeqOnList(a);
  }
  drawSeq(a);
}
//Empty draw loop
public void draw() {
}
//Exit on mouse click
public void mouseReleased() {
  exit();
}
//Generate a fibonacci sequence (Why did I write this?)
public int[] genFib(int start1, int start2, int to) {
  int[] seq0={};
  seq0=append(seq0, start1);
  seq0=append(seq0, start2);
  int currp=start1;
  int curr=start2;
  for(int i=0;i<to;i++) {
    int tempz=curr;
    curr=currp+curr;
    currp=tempz;
    seq0=append(seq0, curr);
  }
  return seq0;
}
//Calculate out a recaman sequence to 'to' iterations.
public int[] calcSeq(int to) {
  int[] seq1={0};
  for (int i=0; i<to; i++) {
    if (!isFound(seq1, c-step)&&c-step>0) {
      c-=step;
      seq1=append(seq1, c);
    } else if (!isFound(seq1, c+step)) {
      c+=step;
      seq1=append(seq1, c);
    } else {
    }
    step++;
  }
  return seq1;
}
//Calculate out a recaman sequence, but the input 'steps' is the step. The original recaman uses to step set '1,2,3,4,5...'
public int[] calcSeqOnList(int[] steps) {
  c=0;
  int[] seq2={0};
  int to=steps.length;
  for (int i=0; i<to; i++) {
    step=steps[i];
    if (!isFound(seq2, c-step)&&c-step>0) {
      c-=step;
      seq2=append(seq2, c);
    } else if (!isFound(seq2, c+step)) {
      c+=step;
      seq2=append(seq2, c);
    } else {
    }
  }
  return seq2;
}
//If found in list
public boolean isFound(int[] a, int v) {
  for (int rec=0; rec<a.length; rec++) {
    if (v==a[rec]) {
      return true;
    }
  }
  return false;
}

public void drawSeq(int[] d) {
  translate(0, height/2);
  wid=width/iter;
  for (int j=0; j<d.length-1; j++) {
    float diff=abs(d[j]-d[j+1]);
    colmax=max(colmax, diff);
  }
  for (int j=0; j<d.length-1; j++) {
    float diff=abs(d[j]-d[j+1]);
    float pixdiff=diff*wid;
    float cent=min(d[j], d[j+1])*wid+pixdiff/2;
    if (isColored) {
      //stroke(255*diff/colmax, 255, 255);
      stroke(j*(255/(d.length-1)),255,255);
    }
    if (up) {
      arc(cent, 0, pixdiff, pixdiff, PI, TWO_PI);
    } else {
      arc(cent, 0, pixdiff, pixdiff, 0, PI);
    }
    up=!up;
  }
}
public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "recaman.RECAMAIN" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
