package net.teraoctet.permlight.core.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import static net.teraoctet.permlight.core.PermLight.PM;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import static org.spongepowered.api.text.Text.of;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

/**
 *
 * @author thyc
 */
public class CommandPermLightGroup implements CommandExecutor {
    
    private final CommandCallBack cb = new CommandCallBack();
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("permlight")) {
                        
            //-----
            // GROUP
            //-----
            
            if(ctx.getOne("group").isPresent()){
                if(ctx.getOne("group").isPresent() && !ctx.getOne("type").isPresent()) {
                    String group = ctx.<String> getOne("group").get();

                    PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
                    pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
                    List<Text> list = new ArrayList<>();
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " permission")).onClick(TextActions.runCommand("/permlight group " + group + " permission")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " prefix")).onClick(TextActions.runCommand("/permlight group " + group + " prefix")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " suffix")).onClick(TextActions.runCommand("/permlight group " + group + " suffix")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " parent")).onClick(TextActions.runCommand("/permlight group " + group + " parent")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " info")).onClick(TextActions.runCommand("/permlight group " + group + " info")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " remove")).onClick(TextActions.runCommand("/permlight group " + group + " remove")).build());
                    pages.contents(list);                
                    pages.sendTo(src);

                    return CommandResult.success();
                }

                if(ctx.getOne("group").isPresent() && ctx.getOne("type").isPresent()) {
                    String type = ctx.<String> getOne("type").get();
                    switch (type){
                        case "permission":
                            cb.callCmdGroupPermission(ctx).accept(src);
                            break;
                        case "prefix":
                            cb.callCmdGroupPrefix(ctx).accept(src);
                            break;
                        case "suffix":
                            cb.callCmdGroupSuffix(ctx).accept(src);
                            break;
                        case "parent":
                            cb.callCmdGroupParent(ctx).accept(src);
                            break;
                        case "info":
                            cb.callCmdGroupInfo(ctx).accept(src);
                            break;
                    }                
                    return CommandResult.success();
                }
            }
              
            PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
            pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
            List<Text> list = new ArrayList<>();
            for(Entry e : PM.getGroups().entrySet()){
                list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + e.getKey())).onClick(TextActions.runCommand("/permlight group " + e.getKey())).build());    
            }
            pages.contents(list);                
            pages.sendTo(src);

            return CommandResult.success();
        
        } else {
            src.sendMessage(Text.of("Vous n'avez pas la permission d'utiliser cette commande"));
        }
                
        return CommandResult.empty();
    }
}
