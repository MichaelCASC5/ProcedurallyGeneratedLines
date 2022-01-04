/*
    * Creating a random number generator using the Middle Square method
*/

public class Pseudorandom{
    long seed;
    long saveSeed;
    int save;
    
    String seedStr, next;
    
    int pos, end;
    long holdSeed = seed;
    
    private String exp;
    
    public Pseudorandom(long s){
        seed = s;
        seedStr = "" + seed;
        save = seedStr.length();
        saveSeed = seed;
        
        exp = "" + saveSeed;
    }
    public double runMid(double x){
        //Saving input seed to str
        seedStr = "" + seed;

        //Squaring input seed
        seed*=seed;

        //Saving output seed to str
        next = "" + seed;

        while(seedStr.length() < save){
            seedStr = "0" + seedStr;
            next = next + "0";
        }

        //Calculating where to split string
        pos = seedStr.length() / 2;
        end = seedStr.length() + pos;

        //If end is longer than the length, reduce end
        if(next.length() < end){
            end = next.length();
        }

        //Splitting string, saving to input seed
        next = next.substring(pos,end);
        seed = Long.parseLong(next);
        
        //Finds repeating patterns
        if(seed == holdSeed){
            seed = saveSeed;
        }
        holdSeed = seed;
        
        if(next.length() > 6){
            next = next.substring(0,6);
        }
        
        x = Double.parseDouble(next);
        x = x/(Math.pow(10.0,next.length()));
        return x;
    }
    public double runLin(double x){
        long mult = 923234L;
        long incr = 903878L;
        long mod = 252454L;
        
        seed = ((mult * seed) + incr) % mod;
        next = "" + seed;
        
        x = Double.parseDouble(next);
        x = x/(Math.pow(10.0,next.length()));
        
        return x;
    }
}