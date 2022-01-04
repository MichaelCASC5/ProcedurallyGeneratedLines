import java.util.ArrayList;

public class Line{
    private ArrayList<Point> points = new ArrayList<>();
    private String ID;
    
    public Line(int w, int h){
        points.add(new Point(w, h));
        ID = "rand";
    }
    public Line(int w, int h, String str){
        points.add(new Point(w, h));
        ID = str;
    }
    public int getX(int i){
        return points.get(i).getX();
    }
    public int getY(int i){
        return points.get(i).getY();
    }
    public Point getLast(){
        return points.get(points.size()-1);
    }
    public void newPoint(int a, int b){
        points.add(new Point(a, b));
    }
    public int getCount(){
        return points.size();
    }
    public void remove(){
        try{
            points.remove(0);
        }catch(Exception e){
            
        }
    }
    public String getID(){
        return ID;
    }
}