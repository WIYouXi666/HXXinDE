package HuanXiangLIB;

import arc.util.CommandHandler;
import mindustry.mod.Mod;

public class LIBMOD extends Mod {
    public static 事件集 logic;

    @Override
    public void loadContent() {
        logic = new 事件集();
    }

    @Override
    public void init() {


    }

    /***注册服务器命令*/
    @Override
    public void registerServerCommands(CommandHandler handler) {
//        handler.<Player>register("help", "[page]", "Lists all commands.", (args, player) -> {
//            if(args.length > 0 && !Strings.canParseInt(args[0])){
//                player.sendMessage("[scarlet]'page' must be a number.");
//                return;
//            }
//            int commandsPerPage = 6;
//            int page = args.length > 0 ? Strings.parseInt(args[0]) : 1;
//            int pages = Mathf.ceil((float)handler.getCommandList().size / commandsPerPage);
//
//            page--;
//
//            if(page >= pages || page < 0){
//                player.sendMessage("[scarlet]'page' must be a number between[orange] 1[] and[orange] " + pages + "[scarlet].");
//                return;
//            }
//
//            StringBuilder result = new StringBuilder();
//            result.append(Strings.format("[orange]-- Commands Page[lightgray] @[gray]/[lightgray]@[orange] --\n\n", (page + 1), pages));
//
//            for(int i = commandsPerPage * page; i < Math.min(commandsPerPage * (page + 1), handler.getCommandList().size); i++){
//                CommandHandler.Command command = handler.getCommandList().get(i);
//                result.append("[orange] /").append(command.text).append("[white] ").append(command.paramText).append("[lightgray] - ").append(command.description).append("\n");
//            }
//            player.sendMessage(result.toString());
//        });
    }

    /**
     * 注册客户端命令
     **/
    @Override
    public void registerClientCommands(CommandHandler handler) {
    }
}
