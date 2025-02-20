package ditiedingpiaoxitong;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class SystemFrame extends JFrame implements ActionListener {
    String passwd=null;
    Scanner sc=new Scanner(System.in);
    JPanel p1=new JPanel();
    JPanel p2=new JPanel();
    JMenuBar jMenuBar=new JMenuBar();
    JMenu functionJMenu=new JMenu("功能");
    JMenuItem administrator=new JMenuItem("管理员");
    JMenuItem tuichu=new JMenuItem("退出");
    JMenuItem clear=new JMenuItem("清空数据");

    JButton b1 =new JButton("购票");
    JButton b2=new JButton("站点查询");
    JButton b3=new JButton("票价查询");
    JButton b4=new JButton("全部票价");
    JButton addstation=new JButton("添加");
    JButton deletestation=new JButton("删除");
    JButton change=new JButton("修改");
    JButton chpasswd=new JButton("改密码");

    JMenuBar jMenuBar2=new JMenuBar();
    JMenu sort=new JMenu("票数排序");
    JMenuItem jm1=new JMenuItem("冒泡");
    JMenuItem jm2=new JMenuItem("直接插入");
    JMenuItem jm3=new JMenuItem("折半插入");
    JMenuItem jm4=new JMenuItem("希尔排序");
    List<Station> important=new ArrayList<>();
    TextArea t=new TextArea();
    void showMenu() {//菜单
        String str="地铁站序列：\n";
        for(int i=0;i<important.size()-1;i++)
        {
            str+=(important.get(i).stationName+"->");
            if(i%5==0&&i!=0)
                str+="\n";
        }
        if(important.size()!=0)
        str+=important.get(important.size()-1).stationName;
        jMenuBar.add(functionJMenu);
        jMenuBar.add(administrator);
        functionJMenu.add(tuichu);
        functionJMenu.add(clear);
        jMenuBar2.add(sort);
        sort.add(jm1);
        sort.add(jm2);
        sort.add(jm3);
        sort.add(jm4);
        t.setText(str);
        p1.add(t);
        p2.setLayout(new FlowLayout());
        p2.add(b1);
        p2.add(b2);
        p2.add(b3);
        p2.add(b4);



        this.setSize(603,680);//设置界面宽高
        this.setTitle("地铁订票系统");//设置界面标题
        this.setAlwaysOnTop(true);//该界面一直置顶
        this.setLocationRelativeTo(null);//设置界面居中
        this.setJMenuBar(jMenuBar);
        this.setDefaultCloseOperation(3);//设置关闭模式
        //operation 参数0：什么都不做的默认窗口关闭操作
        //参数1：hide-windows默认窗口关闭操作
        //参数2：对多个界面设置，关闭最后一个窗口，结束程序
        //参数3：关闭任意界面，结束程序

        this.setLayout(new GridLayout(2,1,0,0));//取消默认居中放置，只有取消了才会按照XY轴的形式添加组件
        this.getContentPane().add(p1);
        this.getContentPane().add(p2);
        this.setVisible(true);//界面可视化


        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        tuichu.addActionListener(this);
        administrator.addActionListener(this);
        addstation.addActionListener(this);
        deletestation.addActionListener(this);
        change.addActionListener(this);
        chpasswd.addActionListener(this);
        jm1.addActionListener(this);
        jm2.addActionListener(this);
        jm3.addActionListener(this);
        jm4.addActionListener(this);
    }

    void fundationStation(List<Station> important) {//初始化基站
        Path path= Paths.get(".//src//ditiedingpiaoxitong//file.txt");
        try {
            List <String> lines= Files.readAllLines(path);
            if(lines.size()<=1)return;
            int trag=0;
            for(String s:lines)
            {
                trag++;
                if(trag==1)continue;
                String[] str=s.split("\t\t");
                important.add(new Station(Integer.parseInt(str[0]),str[1],Integer.parseInt(str[2]),Integer.parseInt(str[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sortStation(List<Station> v1) {
        for(int i=0;i<v1.size();i++)
        {
            v1.get(i).stationNumber=i+1;
        }
    }
    void swap(Station s1,Station s2){//交换
        int shoupiaoshu =s1.saleTicket;
        int xuhao = s1.stationNumber;
        String ditieming = s1.stationName;
        int renshu = s1.comers;

        s1.saleTicket= s2.saleTicket;
        s1.stationNumber= s2.stationNumber;
        s1.stationName = s2.stationName;
        s1.comers = s2.comers;

        s2.saleTicket= shoupiaoshu;
        s2.stationNumber= xuhao;
        s2.stationName = ditieming;
        s2.comers = renshu;
    }
    void assign(Station s1,Station s2){
        s1.saleTicket= s2.saleTicket;
        s1.stationNumber= s2.stationNumber;
        s1.stationName = s2.stationName;
        s1.comers = s2.comers;
    }

    //冒泡排序
    String maopao()
    {
        Station[] arr=new Station[important.size()];
        for(int k=0;k<important.size();k++)
            arr[k]=new Station();
        int m=-1;
        for(Station x:important)
        {
            m++;
            assign(arr[m],x);

        }
        int flag=0;
        for(int i=m-1;i>=1;i--)
        {
            flag=0;
            for(int j=1;j<=i;j++)
            {
                if(arr[j-1].saleTicket<arr[j].saleTicket)
                {
                    swap(arr[j-1],arr[j]);
                    flag=1;
                }
            }
            if(flag==0)
                break;
        }
        String str="冒泡排序结果为：\n";
        for(int i=0;i<m;i++)
        {
            str+=("站点代号: "+arr[i].stationNumber+"     站点名称: "+arr[i].stationName+"     已售票数: "+arr[i].saleTicket+"     将接待人数: "+arr[i].comers+"\n");
        }
        return str;
    }
    //直接插入排序
    String directlyinsert()
    {

        Station[] arr=new Station[important.size()];
        for(int k=0;k<important.size();k++)
            arr[k]=new Station();
        int m=-1;
        for(Station x:important)
        {
            m++;
           assign(arr[m],x);
        }
        int i,j;Station tmp=new Station();
        for(i=1;i<m;i++)
        {

            assign(tmp,arr[i]);
                j=i-1;
                while (j>=0&&arr[j].saleTicket<tmp.saleTicket)
                {
                    assign(arr[j+1],arr[j]);
                    j--;
                }
                assign(arr[j+1],tmp);

        }
        String str="直接插入排序结果为：\n";
        for(i=0;i<m;i++)
        {
            str+=("站点代号: "+arr[i].stationNumber+"     站点名称: "+arr[i].stationName+"     已售票数: "+arr[i].saleTicket+"     将接待人数: "+arr[i].comers+"\n");
        }
        return str;
    }
    //折半插入排序
    String zhebaninsert()
    {

        Station[] arr=new Station[important.size()];
        for(int k=0;k<important.size();k++)
            arr[k]=new Station();
        int m=-1;
        for(Station x:important)
        {
            m++;
            assign(arr[m],x);
        }
        int i,j,low,high,mid;
        Station tmp=new Station();
        for(i=1;i<m;i++)
        {
            if(arr[i].saleTicket>arr[i-1].saleTicket)
            {
                assign(tmp,arr[i]);
                low=0;high=i-1;
                while(low<=high)
                {
                    mid=(low+high)/2;
                    if(tmp.saleTicket>arr[mid].saleTicket)
                    {
                        high=mid-1;
                    }
                    else{
                        low=mid+1;
                    }
                }
                for(j=i-1;j>=high+1;j--)
                {
                    assign(arr[j+1],arr[j]);
                }
                assign(arr[high+1],tmp);
            }
        }
        String str="折半插入排序结果为：\n";
        for( i=0;i<m;i++)
        {
            str+=("站点代号: "+arr[i].stationNumber+"     站点名称: "+arr[i].stationName+"     已售票数: "+arr[i].saleTicket+"     将接待人数: "+arr[i].comers+"\n");
        }
        return str;
    }
    String shellsort()
    {
        Station[] arr=new Station[important.size()];
        for(int k=0;k<important.size();k++)
            arr[k]=new Station();
        int m=-1;
        for(Station x:important)
        {
            m++;
            assign(arr[m],x);
        }
        int i,j,d;
        Station tmp=new Station();
        d=m/2;
        while(d>0)
        {
            for(i=d;i<m;i++)
            {
                assign(tmp,arr[i]);
                j=i-d;
                while(j>=0&&tmp.saleTicket>arr[j].saleTicket)
                {
                    assign(arr[j+d],arr[j]);
                    j=j-d;
                }
                assign(arr[j+d],tmp);
            }
            d=d/2;
        }
        String str="希尔排序结果为：\n";
        for( i=0;i<m;i++)
        {
            str+=("站点代号: "+arr[i].stationNumber+"     站点名称: "+arr[i].stationName+"     已售票数: "+arr[i].saleTicket+"     将接待人数: "+arr[i].comers+"\n");
        }
        return str;
    }
    SystemFrame()  {

        fundationStation(important);
        try {
            BufferedReader bf=new BufferedReader(new FileReader(".//src//ditiedingpiaoxitong//passwd.txt"));
            passwd=bf.readLine();
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        showMenu();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o=e.getSource();
        if(o==b1)//点击购票按钮
        {
            JDialog jDialog=new JDialog();
            jDialog.setSize(332,398);
            Container c=jDialog.getContentPane();
            jDialog.setTitle("购票");

            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog.setModal(true);

            JLabel j1=new JLabel("起点");
            JLabel j2=new JLabel("终点");

            JTextField jt1=new JTextField();
            JTextField jt2=new JTextField();

            jt1.setBounds(120,105,100,20);
            jt2.setBounds(120,255,100,20);

            c.setLayout(null);
            c.add(j1);
            c.add(j2);
            c.add(jt1);
            c.add(jt2);

            j1.setBounds(20,100,70,20);
            j2.setBounds(20,250,70,20);


            //让弹框显示出来
            jDialog.setVisible(true);

            String first=jt1.getText();
            String last=jt2.getText();
            Station firsts=null,lasts=null;
            int flag1=1,flag2=1,begin=0,end=0;
            for(Station x:important)
            {
                if(Objects.equals(x.stationName, first))
                {
                    firsts=x;
                    flag1=0;
                    begin=important.indexOf(x);
                }
                if(Objects.equals(x.stationName, last))
                {
                    lasts=x;
                    flag2=0;
                    end=important.indexOf(x);
                }
            }
            //创建一个弹框对象
            JDialog jDialog2=new JDialog();
            //给弹框设置大小
            jDialog2.setSize(344,344);
            //让弹框置顶
            jDialog2.setAlwaysOnTop(true);
            //让弹框居中
            jDialog2.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog2.setModal(true);
            String str="";
            if(flag1==0&&flag2==0)
            {
                if(firsts.stationNumber==lasts.stationNumber)
                {
                    str="您所处的正是当前站点！";
                }
                else if(firsts.stationNumber<lasts.stationNumber)
                {
                    str="您的行程路线为:\n";
                    for(int i=begin;i<end;i++)
                    {
                        str+=(important.get(i).stationName+"->");
                    }
                    str+=(lasts.stationName+"\n");
                    firsts.saleTicket+=1;
                    lasts.comers+=1;
                    if((lasts.stationNumber-firsts.stationNumber)%2==1)
                    {
                        str+=("您的票价为"+"￥"+((lasts.stationNumber-firsts.stationNumber)/2+2)+"\n");
                        str+=("欢迎下次使用！");
                    }
                    else{
                        str+=("您的票价为"+"￥"+((lasts.stationNumber-firsts.stationNumber)/2+1)+"\n");
                        str+=("欢迎下次使用！");
                    }
                }
                else{
                    str="您的行程路线为:\n";
                    for(int i=begin;i>end;i--)
                    {
                        str+=(important.get(i).stationName+"->");
                    }
                    str+=(lasts.stationName+"\n");
                    firsts.saleTicket+=1;
                    lasts.comers+=1;
                    if((firsts.stationNumber-lasts.stationNumber)%2==1)
                    {
                        str+=("您的票价为"+"￥"+((firsts.stationNumber-lasts.stationNumber)/2+2)+"\n");
                        str+=("欢迎下次使用！");
                    }
                    else{
                        str+=("您的票价为"+"￥"+((firsts.stationNumber-lasts.stationNumber)/2+1)+"\n");
                        str+=("欢迎下次使用！");
                    }
                }
            }
            else{
                str=("你输入的起始站点或终止站点不存在！");
            }
            TextArea ta=new TextArea(str);
            //让弹框显示出来
            jDialog2.getContentPane().add(ta);
            jDialog2.setVisible(true);

        }
        else if(o==b2)//点击站点查询按钮
        {
            JDialog jDialog=new JDialog();
            jDialog.setSize(332,398);
            Container c=jDialog.getContentPane();
            jDialog.setTitle("站点查询");

            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog.setModal(true);

            JLabel j1=new JLabel("查询站点");

            JTextField jt1=new JTextField();

            jt1.setBounds(120,105,100,20);

            c.setLayout(null);
            c.add(j1);
            c.add(jt1);

            j1.setBounds(20,100,70,20);

            //让弹框显示出来
            jDialog.setVisible(true);

            int flag=1;
            String smname=jt1.getText();

            //创建一个弹框对象
            JDialog jDialog2=new JDialog();
            //给弹框设置大小
            jDialog2.setSize(344,344);
            //让弹框置顶
            jDialog2.setAlwaysOnTop(true);
            //让弹框居中
            jDialog2.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog2.setModal(true);
            String str="";
            for(Station x:important)
            {
                if(Objects.equals(x.stationName, smname))
                {
                    str=("站点号码:"+x.stationNumber+"\n站点名称:"+x.stationName+"\n已售票数:"+x.saleTicket+"\n将接待人数:"+x.comers);
                    flag=0;
                }
            }
            if(flag==0)
            {
                str+=("\n查询完成！");
            }
            else{
                str=("抱歉！没有找到你想要的站点！");
            }
            TextArea ta=new TextArea(str);
            jDialog2.getContentPane().add(ta);
            jDialog2.setVisible(true);
        }
        else if(o==b3)//点击票价查询按钮
        {
            JDialog jDialog=new JDialog();
            jDialog.setSize(332,398);
            Container c=jDialog.getContentPane();
            jDialog.setTitle("票价查询");

            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog.setModal(true);

            JLabel j1=new JLabel("查询起点");
            JLabel j2=new JLabel("查询终点");

            JTextField jt1=new JTextField();
            JTextField jt2=new JTextField();

            jt1.setBounds(120,105,100,20);
            jt2.setBounds(120,255,100,20);

            c.setLayout(null);
            c.add(j1);
            c.add(j2);
            c.add(jt1);
            c.add(jt2);

            j1.setBounds(20,100,70,20);
            j2.setBounds(20,250,70,20);


            //让弹框显示出来
            jDialog.setVisible(true);

            String first=jt1.getText();
            String last=jt2.getText();
            Station firsts=null,lasts=null;
            int flag1=1,flag2=1,begin=0,end=0;
            for(Station x:important)
            {
                if(Objects.equals(x.stationName, first))
                {
                    firsts=x;
                    flag1=0;
                    begin=important.indexOf(x);
                }
                if(Objects.equals(x.stationName, last))
                {
                    lasts=x;
                    flag2=0;
                    end=important.indexOf(x);
                }
            }
            //创建一个弹框对象
            JDialog jDialog2=new JDialog();
            //给弹框设置大小
            jDialog2.setSize(344,344);
            //让弹框置顶
            jDialog2.setAlwaysOnTop(true);
            //让弹框居中
            jDialog2.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog2.setModal(true);
            String str="";
            if(flag1==0&&flag2==0)
            {
                if(firsts.stationNumber==lasts.stationNumber)
                {
                    str="您所处的正是当前站点！";
                }
                else if(firsts.stationNumber<lasts.stationNumber)
                {
                    str="您的行程路线为:\n";
                    for(int i=begin;i<end;i++)
                    {
                        str+=(important.get(i).stationName+"->");
                    }
                    str+=(lasts.stationName+"\n");
                    if((lasts.stationNumber-firsts.stationNumber)%2==1)
                    {
                        str+=("您的票价为"+"￥"+((lasts.stationNumber-firsts.stationNumber)/2+2)+"\n");
                        str+=("欢迎下次使用！");
                    }
                    else{
                        str+=("您的票价为"+"￥"+((lasts.stationNumber-firsts.stationNumber)/2+1)+"\n");
                        str+=("欢迎下次使用！");
                    }
                }
                else{
                    str="您的行程路线为:\n";
                    for(int i=begin;i>end;i--)
                    {
                        str+=(important.get(i).stationName+"->");
                    }
                    str+=(lasts.stationName+"\n");
                    if((firsts.stationNumber-lasts.stationNumber)%2==1)
                    {
                        str+=("您的票价为"+"￥"+((firsts.stationNumber-lasts.stationNumber)/2+2)+"\n");
                        str+=("欢迎下次使用！");
                    }
                    else{
                        str+=("您的票价为"+"￥"+((firsts.stationNumber-lasts.stationNumber)/2+1)+"\n");
                        str+=("欢迎下次使用！");
                    }
                }
            }
            else{
                str=("你输入的起始站点或终止站点不存在！");
            }
            TextArea ta=new TextArea(str);
            //让弹框显示出来
            jDialog2.getContentPane().add(ta);
            jDialog2.setVisible(true);
        }
        else if(o==b4)
        {
            // 创建一个JFrame实例
            JFrame frame = new JFrame("全部票价查询");
            frame.setSize(400, 300);
            int m=important.size();

            //让界面置顶
            frame.setAlwaysOnTop(true);
            //让界面居中
            frame.setLocationRelativeTo(null);


            // 创建表格的模型
            String[] columnNames = new String[m+1];
            columnNames[0]="";
            int k=0;
            for(Station x:important)
            {
                k++;
                columnNames[k]=x.stationName;
            }
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            // 添加表格数据
            String[] str=new String[m+1];
            for(int i=1;i<=m;i++)
            {
                str[0]=columnNames[i];
                for(int j=1;j<=m;j++)
                {
                    int a=(Math.abs(i-j)+1)/2+1;
                    str[j]= String.valueOf(a);
                    if(a==1)
                        str[j]="";
                }
                model.addRow(str);
            }

            // 创建JTable实例，并设置模型
            JTable table = new JTable(model);

            // 设置JTable的滚动面板
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);

            // 显示窗体
            frame.setVisible(true);
        }
        else if(o==tuichu)//点击退出菜单
        {
            try {
                File file=new File(".//src//ditiedingpiaoxitong//file.txt");
                FileWriter fw=new FileWriter(file);
                fw.write("站点代号\t\t站点名称\t\t已售票数\t\t将接待人数\n");
                for(Station x:important)
                {
                    fw.append(x.toString()+"\n");
                }
                fw.close();
                File file1=new File(".//src//ditiedingpiaoxitong//passwd.txt");
                FileWriter fw1=new FileWriter(file1);
                fw1.write(passwd);
                fw1.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            System.exit(0);
        }
        else if(o==administrator)//点击管理员菜单
        {
            JDialog jDialog=new JDialog();
            jDialog.setSize(332,398);
            Container c=jDialog.getContentPane();
            jDialog.setTitle("登录管理员");

            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog.setModal(true);

            JLabel j1=new JLabel("管理员密码");

            JTextField jt1=new JTextField();

            jt1.setBounds(120,105,100,20);

            c.setLayout(null);
            c.add(j1);
            c.add(jt1);

            j1.setBounds(20,100,70,20);

            //让弹框显示出来
            jDialog.setVisible(true);
            String input=jt1.getText();
            if(Objects.equals(input,passwd))
            {
                JFrame jf=new JFrame("管理员界面");
                jf.setSize(405,427);//设置界面宽高
                jf.setAlwaysOnTop(true);//该界面一直置顶
                jf.setLocationRelativeTo(null);//设置界面居中
                jf.setJMenuBar(jMenuBar2);
                jf.setLayout(new FlowLayout());
                Container container=jf.getContentPane();
                container.add(addstation);
                container.add(deletestation);
                container.add(change);
                container.add(chpasswd);


                jf.setVisible(true);//界面可视化
            }
            else
            {
                JDialog jDialog2=new JDialog();
                jDialog.setSize(332,398);

                //让弹框置顶
                jDialog.setAlwaysOnTop(true);
                //让弹框居中
                jDialog.setLocationRelativeTo(null);
                //弹框不关闭，则无法操作下面的界面
                jDialog.setModal(true);
                JLabel jLabel=new JLabel("密码错误");
            }
        }
        else if(o==clear)//点击清空菜单
        {
            for(Station x:important)
            {
                x.saleTicket=0;
                x.comers=0;
            }
        }
        else if(o==addstation)//点击添加站点按钮
        {
            JDialog jDialog=new JDialog();
            jDialog.setSize(332,398);
            Container c=jDialog.getContentPane();
            jDialog.setTitle("添加站点");

            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog.setModal(true);

            JLabel j1=new JLabel("站点名字");
            JLabel j2=new JLabel("加入位置");

            JTextField jt1=new JTextField();
            JTextField jt2=new JTextField();

            jt1.setBounds(120,105,100,20);
            jt2.setBounds(120,255,100,20);

            c.setLayout(null);
            c.add(j1);
            c.add(j2);
            c.add(jt1);
            c.add(jt2);

            j1.setBounds(20,100,70,20);
            j2.setBounds(20,250,70,20);

            //让弹框显示出来
            jDialog.setVisible(true);

            String nname=jt1.getText();
            if(nname.equals(""))
                return;
            int local=Integer.parseInt(jt2.getText());
            if(local>important.size()||local<0)
            {
                System.out.println("请输入正确位置");
                return;
            }
            Station s=new Station(0,nname,0,0);
            important.add(local,s);
            sortStation(important);

            String str="地铁站序列：\n";
            for(int i=0;i<important.size()-1;i++)
            {
                str+=(important.get(i).stationName+"->");
                if(i%5==0&&i!=0)
                    str+="\n";
            }
            if(important.size()!=0)
                str+=important.get(important.size()-1).stationName;
            t.setText(str);

        }
        else if(o==deletestation)//点击删除站点按钮
        {
            JDialog jDialog=new JDialog();
            jDialog.setSize(332,398);
            Container c=jDialog.getContentPane();
            jDialog.setTitle("删除站点");

            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog.setModal(true);

            JLabel j1=new JLabel("站点名称");

            JTextField jt1=new JTextField();

            jt1.setBounds(120,105,100,20);

            c.setLayout(null);
            c.add(j1);
            c.add(jt1);

            j1.setBounds(20,100,70,20);

            //让弹框显示出来
            jDialog.setVisible(true);
            String nname=jt1.getText();
            if(nname.equals(""))
                return;
            int flag=1;
            for(Station x:important) {
                if(Objects.equals(x.stationName, nname)) {
                    important.remove(x);
                    flag=0;
                    System.out.println("删除完成");
                    break;
                }
            }
            if(flag==0) {
                for(int i=0;i<important.size();i++)
                {
                    important.get(i).stationNumber=i+1;
                }
            } else{
                System.out.println("输入的站点不存在！");
            }
            String str="地铁站序列：\n";
            for(int i=0;i<important.size()-1;i++)
            {
                str+=(important.get(i).stationName+"->");
                if(i%5==0&&i!=0)
                    str+="\n";
            }
            if(important.size()!=0)
                str+=important.get(important.size()-1).stationName;
            t.setText(str);
        }
        else if(o==change)//点击修改站点按钮
        {
            JDialog jDialog=new JDialog();
            jDialog.setSize(332,398);
            Container c=jDialog.getContentPane();
            jDialog.setTitle("修改站点");

            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog.setModal(true);

            JLabel j1=new JLabel("站点原名");
            JLabel j2=new JLabel("修改名");

            JTextField jt1=new JTextField();
            JTextField jt2=new JTextField();

            jt1.setBounds(120,105,100,20);
            jt2.setBounds(120,255,100,20);

            c.setLayout(null);
            c.add(j1);
            c.add(j2);
            c.add(jt1);
            c.add(jt2);

            j1.setBounds(20,100,70,20);
            j2.setBounds(20,250,70,20);

            //让弹框显示出来
            jDialog.setVisible(true);
            String name1=jt1.getText();
            String name2=jt2.getText();
            int flag=1;
            for(Station x:important) {
                if(Objects.equals(x.stationName, name1)) {
                    x.stationName=name2;
                    flag=0;
                    System.out.println("修改成功");
                }
            }
            if(flag==1) {
                System.out.println("修改失败！没有找到你需要的站点！");
            }
            String str="地铁站序列：\n";
            for(int i=0;i<important.size()-1;i++)
            {
                str+=(important.get(i).stationName+"->");
                if(i%5==0&&i!=0)
                    str+="\n";
            }
            if(important.size()!=0)
                str+=important.get(important.size()-1).stationName;
            t.setText(str);
        }
        else if(o==chpasswd)//点击修改密码按钮
        {
            JDialog jDialog=new JDialog();
            jDialog.setSize(332,398);
            Container c=jDialog.getContentPane();
            jDialog.setTitle("修改密码");

            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog.setModal(true);

            JLabel j1=new JLabel("新密码");

            JTextField jt1=new JTextField();

            jt1.setBounds(120,105,100,20);

            c.setLayout(null);
            c.add(j1);
            c.add(jt1);

            j1.setBounds(20,100,70,20);

            //让弹框显示出来
            jDialog.setVisible(true);
            String newpasswd=jt1.getText();
            if(newpasswd.equals(""))
                return;
            passwd=newpasswd;
        }
        else if(o==jm1)//点击冒泡排序菜单
        {
            //创建一个弹框对象
            JDialog jDialog2=new JDialog();
            //给弹框设置大小
            jDialog2.setSize(544,344);
            //让弹框置顶
            jDialog2.setAlwaysOnTop(true);
            //让弹框居中
            jDialog2.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog2.setModal(true);
            TextArea ta=new TextArea(maopao());
            //让弹框显示出来
            jDialog2.getContentPane().add(ta);
            jDialog2.setVisible(true);
        }
        else if(o==jm2)//点击直接插入排序菜单
        {
            //创建一个弹框对象
            JDialog jDialog2=new JDialog();
            //给弹框设置大小
            jDialog2.setSize(544,344);
            //让弹框置顶
            jDialog2.setAlwaysOnTop(true);
            //让弹框居中
            jDialog2.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog2.setModal(true);
            TextArea ta=new TextArea(directlyinsert());
            //让弹框显示出来
            jDialog2.getContentPane().add(ta);
            jDialog2.setVisible(true);
        }
        else if(o==jm3)//点击折半插入排序菜单
        {
            //创建一个弹框对象
            JDialog jDialog2=new JDialog();
            //给弹框设置大小
            jDialog2.setSize(544,344);
            //让弹框置顶
            jDialog2.setAlwaysOnTop(true);
            //让弹框居中
            jDialog2.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog2.setModal(true);
            TextArea ta=new TextArea(zhebaninsert());
            //让弹框显示出来
            jDialog2.getContentPane().add(ta);
            jDialog2.setVisible(true);
        }
        else if(o==jm4)
        {
            //创建一个弹框对象
            JDialog jDialog2=new JDialog();
            //给弹框设置大小
            jDialog2.setSize(544,344);
            //让弹框置顶
            jDialog2.setAlwaysOnTop(true);
            //让弹框居中
            jDialog2.setLocationRelativeTo(null);
            //弹框不关闭，则无法操作下面的界面
            jDialog2.setModal(true);
            TextArea ta=new TextArea(shellsort());
            //让弹框显示出来
            jDialog2.getContentPane().add(ta);
            jDialog2.setVisible(true);
        }
    }
}