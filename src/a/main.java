package a;

public class main{
    public static void main(String[] args) {
        sanJX a=new sanJX();//新建三角形
        a.setA(2.4);
        a.setB(3.5);
        a.setC(4);//设置三边长度
        System.out.println(a.toString());
        yuan b=new yuan();//新建圆形
        b.setR(4.5);//设置半径
        System.out.println( b.toString());
        jXing c=new jXing();//新建矩形
        c.setLength(3.7);
        c.setWidth(2.4);//设置两边长
        System.out.println( c.toString());
        System.out.println("三角形与圆形比较");
        a.bjMj(b);
        a.bjZc(b);
        System.out.println("矩形与圆形比较");
        c.bjMj(a);
        c.bjZc(a);
        System.out.println("圆形与矩形比较");
        b.bjMj(c);
        b.bjZc(c);
    }
}
 abstract class tuXing {
    abstract double zhouChang();
    abstract double mianJi();
}




  class sanJX extends tuXing{
    private double a,b,c;
   public double zhouChang(){
        return a+b+c;
    }
 public  double mianJi(){
       double p=(a+b+c)/2;
       double s=p*(p-a)*(p-b)*(p-c);
       s=Math.sqrt(s);
       return s;
 }
 public void setA(double a){
       this.a=a;

 }
      public void setB(double b){
          this.b=b;

      }
      public void setC(double c){
          this.c=c;

      }

      public double getA(){
       return a;
      }
      public double getB(){
          return b;
      }
      public double getC(){
          return c;
      }

      @Override
      public String toString() {
       return   "该三角形三边长为："+a+" "+b+" "+c+"面积为："+mianJi()+"周长为："+zhouChang();
      }
      public  void bjZc(tuXing a){
          System.out.print("周长比较：");
          if (zhouChang()>a.zhouChang()){
              System.out.println(toString()+"更大");
          }
          else if(zhouChang()<a.zhouChang())System.out.println(toString()+"更小");
          else System.out.println("一样大");;
      }
      public void bjMj(tuXing a){
          System.out.print("面积比较：");
          if (mianJi()>a.mianJi()){
              System.out.println(toString()+"更大");
          }
          else if(mianJi()<a.mianJi()) System.out.println(toString()+"更小");
          else System.out.println("一样大");;
      }
      sanJX(double a,double b,double c){
       this.a=a;
       this.b=b;
       this.c=c;
      }
    sanJX(){
       this.a=0;
       this.b=0;
       this.c=0;
    }
  }



class yuan extends tuXing{
     private double r;
     public double zhouChang(){
         return 2*r*Math.PI;
     }
     public double mianJi(){
         return r*Math.PI*Math.PI;
     }
     public void setR(double r){
         this.r=r;
     }
     public double getR(){
         return r;
     }
    @Override
     public String toString(){
         return "该圆形半径为："+r+"面积为："+mianJi()+"周长为："+zhouChang();
     }
    public  void bjZc(tuXing a){
        System.out.print("周长比较：");
        if (zhouChang()>a.zhouChang()){
            System.out.println(toString()+"更大");
        }
        else if(zhouChang()<a.zhouChang())System.out.println(toString()+"更小");
        else System.out.println("一样大");;
    }
    public void bjMj(tuXing a){
        System.out.print("面积比较：");
        if (mianJi()>a.mianJi()){
            System.out.println(toString()+"更大");
        }
        else if(mianJi()<a.mianJi()) System.out.println(toString()+"更小");
        else System.out.println("一样大");;
    }
     yuan( double r){
         this.r=r;
     }
     yuan(){
         r=0;
     }
}
class jXing extends tuXing{
     private double  width,length;
     public double mianJi(){
      return width*length;
     }
     public  double zhouChang(){
         return 2*(width+length);
     }
     public void setWidth(double width){
         this.width=width;
     }
     public void setLength(double length){
         this.length=length;
     }
     public double getWidth(){
         return width;
     }
     public double getLength(){
         return length;
     }

    @Override
    public String toString() {
        return  "该矩形长为："+length+" 宽为"+width+"面积为："+mianJi()+"周长为："+zhouChang();
    }
    public  void bjZc(tuXing a){
        System.out.print("周长比较：");
        if (zhouChang()>a.zhouChang()){
            System.out.println(toString()+"更大");
        }
        else if(zhouChang()<a.zhouChang())System.out.println(toString()+"更小");
        else System.out.println("一样大");;
    }
    public void bjMj(tuXing a){
        System.out.print("面积比较：");
        if (mianJi()>a.mianJi()){
            System.out.println(toString()+"更大");
        }
        else if(mianJi()<a.mianJi()) System.out.println(toString()+"更小");
        else System.out.println("一样大");;
    }
    jXing(){width=length=0;}
}