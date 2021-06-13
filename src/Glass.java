public class Glass {
    private long timeMarket;
    private long timeOur;
    private double[] bidPrice;
    private double[] bidVolume;
    private double[] askPrice;
    private double[] askVolume;

    public long getTimeMarket() {
        return timeMarket;
    }

    public long getTimeOur() {
        return timeOur;
    }

    public double[] getAskPrice() {
        return askPrice;
    }

    public double[] getAskVolume() {
        return askVolume;
    }

    public double[] getBidPrice() {
        return bidPrice;
    }

    public double[] getBidVolume() {
        return bidVolume;
    }

    public Glass(String line){
        String[] numbers;
        numbers = line.split(",");
        if ((numbers.length != 43)|(Integer.parseInt(numbers[0]) != 0)){
            System.out.println("Неверные данные в конструкторе");
        }
        else {
            timeMarket = Long.parseLong(numbers[1]);
            timeOur = Long.parseLong(numbers[2]);
            bidPrice = new double[10];
            bidVolume = new double[10];
            askPrice = new double[10];
            askVolume = new double[10];
            for (int i = 0; i < 10; ++i){
                bidPrice[i] = Double.parseDouble(numbers[3 + 2 * i]);
                bidVolume[i] = Double.parseDouble(numbers[4 + 2 * i]);
                askPrice[i] = Double.parseDouble(numbers[23 + 2 * i]);
                askVolume[i] = Double.parseDouble(numbers[24 + 2 * i]);
            }
        }
    }

    public Glass(){
        timeMarket = 0;
        timeOur = 0;
        bidPrice = new double[10];
        bidVolume = new double[10];
        askPrice = new double[10];
        askVolume = new double[10];
        for (int i = 0; i < 10; ++i){
            bidPrice[i] = 0;
            bidVolume[i] = 0;
            askPrice[i] = 0;
            askVolume[i] = 0;
        }
    }
}
