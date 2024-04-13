package HuanXiangLIB.ui类.对话框;

import arc.math.Mathf;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.editor;

public class 地图大小调整对话框 extends BaseDialog {
    public static int minSize = 50, maxSize = 600, increment = 50;

    int width, height, shiftX, shiftY;

    public 地图大小调整对话框(ResizeListener cons){
        super("@editor.resizemap");

        closeOnBack();
        shown(() -> {
            cont.clear();
            width = editor.width();
            height = editor.height();

            Table table = new Table();

            for(boolean w : Mathf.booleans){
                table.add(w ? "@width" : "@height").padRight(8f);
                table.defaults().height(60f).padTop(8);

                table.field((w ? width : height) + "", TextField.TextFieldFilter.digitsOnly, value -> {
                    int val = Integer.parseInt(value);
                    if(w) width = val; else height = val;
                }).valid(value -> Strings.canParsePositiveInt(value) && Integer.parseInt(value) <= maxSize && Integer.parseInt(value) >= minSize).maxTextLength(3);

                table.row();
            }

            for(boolean x : Mathf.booleans){
                table.add(x ? "@editor.shiftx" : "@editor.shifty").padRight(8f);
                table.defaults().height(60f).padTop(8);

                table.field((x ? shiftX : shiftY) + "", value -> {
                    int val = Integer.parseInt(value);
                    if(x) shiftX = val; else shiftY = val;
                }).valid(Strings::canParseInt).maxTextLength(4);

                table.row();
            }

            cont.row();
            cont.add(table);

        });

        buttons.defaults().size(200f, 50f);
        buttons.button("@cancel", this::hide);
        buttons.button("@ok", () -> {
            cons.get(width, height, shiftX, shiftY);
            hide();
        });
    }

    public interface ResizeListener{
        void get(int width, int height, int shiftX, int shiftY);
    }
}