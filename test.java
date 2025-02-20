package ditiedingpiaoxitong;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

        public class test {
            public static void main(String[] args) {
                // 创建一个JFrame实例
                JFrame frame = new JFrame("表格示例");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);

                // 创建表格的模型，设置列名
                String[] columnNames = {"姓名", "年龄", "性别"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                // 添加表格数据
                model.addRow(new Object[]{"张三", 25, "男"});
                model.addRow(new Object[]{"李四", 30, "女"});
                model.addRow(new Object[]{"王五", 28, "男"});

                // 创建JTable实例，并设置模型
                JTable table = new JTable(model);

                // 设置JTable的滚动面板
                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);

                // 显示窗体
                frame.setVisible(true);
            }
        }


