public class Point{
    private int x;
    private int y;
    
    public Point(int a, int b){
        x = a;
        y = b;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int n){
        x = n;
    }
    public void setY(int n){
        y = n;
    }
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}