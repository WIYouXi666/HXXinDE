package HuanXiangDIY.内容.局内ui内容;

import HuanXiangLIB.libxx;
import HuanXiangLIB.注册类.修正;
import arc.scene.ui.layout.Table;
import mindustry.gen.Icon;

import static HuanXiangLIB.核心内容.修正类记录.修正加;

public class 测试 extends uilib{
    public 测试() {
        super("测试");
    }
    @Override
    public void 加载(Table ggg) {
        ggg.pane(Cggg -> {
//            Cggg.button("开局加载对话框", 开局加载对话框::show).row();
//
//
//
//
//
//
//
//
//
//
//
//
//            Cggg.button(Icon.chat, () -> {
//                gn添加聊天信息(String.valueOf(get计时器()));
//            }).row();
//
//            Cggg.add(""+get世界宽度()+" h h "+get世界高度()).update(
//                    i->{
//                        i.setText(""+get世界宽度()+" h h "+get世界高度()+"  \n"+get相机位置());
//                    }
//            ).row();
//            Cggg.button(Icon.chat, () -> {
//                gn控制相机位置(100,100,0.2f,10);
//            });
            Cggg.button(Icon.chat, libxx::gn关闭控制相机位置).row();
//            Cggg.button(Icon.chat, () -> {
//                if (ui.chatfrag.shown()) {
//                    ui.chatfrag.hide();
//                } else {
//                    ui.chatfrag.toggle();
//                }
//            });
//            Cggg.button(Icon.tree, () -> {
//                ui.research.show();
//            });
//            Cggg.button(Icon.book, () -> {
//                ui.database.show();
//            });
//            Cggg.row();
//            Cggg.buttonRow("@hostserver.mobile", Icon.host, ui.host::show).disabled(b -> net.active());
//
//
//            Cggg.button("fhg", () -> {
//                信息ui.add("测是"+get计时器());
//            });

            Cggg.button("fhg", () -> {
                修正加(new 修正("gbhfhg"){{
                    无限火力 = true;
                }});
            });

        });
    }
}
