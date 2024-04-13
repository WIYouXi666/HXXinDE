package HuanXiangDIY.内容.局内ui内容;

import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;

import java.util.LinkedList;

import static HuanXiangLIB.libxx.get获得1秒;

public class 信息ui extends uilib {

    private static LinkedList<String> Z队列 = new LinkedList<>();
    private LinkedList<TextureRegion> T队列 = new LinkedList<>();
    private static float 间隔 = 20f;
    private static float i = 0f;
    public 信息ui() {
        super("xinxi");
    }

    /**
     * @param table
     */
    @Override
    public void 加载(Table table) {
        table.pane(a -> {

        }).row();
        table.pane( a-> {
            a.add(Z队列.toString()).update(i -> {
                i.setText(Z队列.toString());
            });
        });


    }
    public static void add(String a) {
        if(!Z队列.offer(a)){
            Z队列.poll();
            Z队列.offer(a);
        };
    }
    public static void 更新() {
        if(i>get获得1秒()*间隔){
          if (!Z队列.isEmpty()){
              Z队列.poll();
          }
            i=0;
        }else {
            i++;
        }
    }

}
