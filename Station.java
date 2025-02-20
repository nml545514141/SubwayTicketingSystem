package ditiedingpiaoxitong;
public class Station {
    int stationNumber;//地铁序号
    String stationName;//地铁名
    int saleTicket;//卖出票数
    int comers;//接待人数
     Station(int xuhao, String ditieming, int shoupiaoshu, int people) {
        this.saleTicket=shoupiaoshu;
        this.stationNumber=xuhao;
        this.stationName=ditieming;
        this.comers=people;
    }
    Station(){}
    @Override
    public String toString()
    {
        return (stationNumber+"\t\t"+stationName+"\t\t"+saleTicket+"\t\t"+comers);
    }
}
