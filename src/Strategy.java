import java.io.*;
import java.math.*;

public class Strategy {
    public int position;
    public double price;
    public double profit;
    static public int request;
    public boolean close;

    public Strategy(){
        position = 0;
        price = 0;
        profit = 0;
        close = false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader brTrain = null;
        BufferedReader brTest = null;
        String line;
        String[] numbers;
        Glass[] glTrain = new Glass[10000];
        Glass[] glTest = new Glass[1000000];
        int count = 0, request;
        int n = 10000;
        double askE = 0, askD = 0, f, par = 0.2;
        try {
            File train = new File("train.txt");
            brTrain = new BufferedReader(new FileReader(train));
            while ((line = brTrain.readLine()) != null) {
                if (line.toCharArray()[0] != '0')
                    continue;
                numbers = line.split(",");
                glTrain[count] = new Glass(line);
                askE += glTrain[count].getAskPrice()[0] / n;
                ++count;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
                brTrain.close();
        }
        for (int i = 0; i < n; ++i) {
            askD += (glTrain[i].getAskPrice()[0] - askE) * (glTrain[i].getAskPrice()[0] - askE) / n;
        }
        Optimazer opt = new Optimazer(glTrain, n, 1, askE, askD);
        int k = 2;
        try {
            File test = new File("test.txt");
            brTest = new BufferedReader(new FileReader(test));
            count = 0;
            Strategy[] str = new Strategy[1000000];
            while (((line = brTest.readLine()) != null) & (count < k - 1)){
                if (line.toCharArray()[0] != '0')
                    continue;
                glTest[count] = new Glass(line);
                str[count] = new Strategy();
                ++count;
            }
            while (((line = brTest.readLine()) != null) & (count < 1000000)){
                if (line.toCharArray()[0] != '0')
                    continue;
                glTest[count] = new Glass(line);
                str[count] = new Strategy();
                f = 0;
                for (int l = 0; l < k; ++l){
                    f += opt.getA()[l] * (glTest[count - l].getAskPrice()[0] - askE) / Math.sqrt(askD);
                }
                f = Math.tanh(f);
                if (str[count - 1].close){
                    if (str[Strategy.request].position == 1){
                        str[Strategy.request].profit = (glTest[count].getBidPrice()[0] -
                                glTest[Strategy.request + 1].getAskPrice()[0]) / 1000;
                    }else{
                        str[Strategy.request].profit = (glTest[Strategy.request + 1].getBidPrice()[0] -
                                glTest[count].getAskPrice()[0]) / 1000;
                    }
                    Strategy.request = count - 1;
                }
                if (f > par){
                    str[count].position = 1;
                    if (str[count - 1].position == -1){
                        str[count].close = true;
                    }
                    if (str[count - 1]. position == 0){
                        Strategy.request = count;
                    }
                }
                else{
                    if (f < -par){
                        str[count].position = -1;
                        if (str[count - 1].position == 1){
                            str[count].close = true;
                        }
                        if (str[count - 1]. position == 0){
                            Strategy.request = count;
                        }
                    }else{
                        str[count].position = 0;
                        if (str[count - 1].position != 0){
                            str[count].close = true;
                        }
                        else
                            Strategy.request = count;
                    }
                }
                ++count;
            }
            double e = 0, d = 0;
            for (int i = 0; i < count; ++i){
                e += str[i].profit / count;
            }
            for (int i = 0; i < count; ++i){
                d += (str[i].profit - e) * (str[i].profit - e) / count;
            }
            //System.out.println("Мат. ожидание: " + e);
            //System.out.println("Дисперсия: " + d);
            System.out.println("Шарп: " + e/Math.sqrt(d));
        } catch (IOException e) {
            System.out.println("Error: " + e);

        }finally {
            brTest.close();
        }
    }
}
