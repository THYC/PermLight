package net.teraoctet.permlight.core.command;

import java.util.ArrayList;
import java.util.List;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import static org.spongepowered.api.text.Text.of;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

/**
 *
 * @author thyc
 */
public class CommandPermLightType implements CommandExecutor {
    
    private final CommandCallBack cb = new CommandCallBack();
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("permlight")) {
            //-----
            // USER
            //-----
            
            if(ctx.getOne("user").isPresent()){
                if(ctx.getOne("user").isPresent() && !ctx.getOne("type").isPresent()) {
                    User user = ctx.<User> getOne("user").get();

                    PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
                    pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
                    List<Text> list = new ArrayList<>();
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight user " + user.getName() + " permission")).onClick(TextActions.suggestCommand("/permlight user " + user.getName() + " permission")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight user " + user.getName() + " group")).onClick(TextActions.suggestCommand("/permlight user " + user.getName() + " group")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight user " + user.getName() + " info")).onClick(TextActions.suggestCommand("/permlight user " + user.getName() + " info")).build());
                    pages.contents(list);                
                    pages.sendTo(src);

                    return CommandResult.success();
                }

                if(ctx.getOne("user").isPresent() && ctx.getOne("type").isPresent()) {
                    User user = ctx.<User> getOne("user").get();
                    String type = ctx.<String> getOne("type").get();
                    switch (type){
                        case "permission":
                            cb.callCmdUserPermission(ctx).accept(src);
                            break;
                        case "group":
                            cb.callCmdUserGroup(ctx).accept(src);
                            break;
                        case "info":
                            cb.callCmdUserInfo(ctx).accept(src);
                            break;
                    }                
                    return CommandResult.success();
                }
            }
            
            //-----
            // GROUP
            //-----
            
            if(ctx.getOne("group").isPresent()){
                if(ctx.getOne("group").isPresent() && !ctx.getOne("type").isPresent()) {
                    String group = ctx.<String> getOne("group").get();

                    PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
                    pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
                    List<Text> list = new ArrayList<>();
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " permission")).onClick(TextActions.suggestCommand("/permlight group " + group + " permission")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " prefix")).onClick(TextActions.suggestCommand("/permlight group " + group + " prefix")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " suffix")).onClick(TextActions.suggestCommand("/permlight group " + group + " suffix")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " parent")).onClick(TextActions.suggestCommand("/permlight group " + group + " parent")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group " + group + " info")).onClick(TextActions.suggestCommand("/permlight group " + group + " info")).build());
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
            
            //-----
            // WORLD
            //-----
            
            if(ctx.<World>getOne("world").isPresent()){
                World world = ctx.<World>getOne("world").get();
                
                if(!ctx.getOne("subtype").isPresent()) {

                    PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
                    pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
                    List<Text> list = new ArrayList<>();
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group ")).onClick(TextActions.suggestCommand("/permlight group ")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " info ")).onClick(TextActions.suggestCommand("/permlight info")).build());
                    pages.contents(list);                
                    pages.sendTo(src);

                    return CommandResult.success();
                }
                
                if(ctx.getOne("subtype").isPresent() && !ctx.getOne("group").isPresent()) {
                    if(ctx.<String>getOne("subtype").get().equalsIgnoreCase("info")){
                        cb.callCmdWorldInfo(ctx).accept(src);
                        return CommandResult.success();
                    }
                    PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
                    pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
                    List<Text> list = new ArrayList<>();
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group")).onClick(TextActions.suggestCommand("/permlight world " + world.getName() + " group")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " permission")).onClick(TextActions.suggestCommand("/permlight world " + world.getName() + " permission")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " info")).onClick(TextActions.suggestCommand("/permlight world " + world.getName() + " info")).build());
                    pages.contents(list);                
                    pages.sendTo(src);

                    return CommandResult.success();
                }
                
                if(ctx.getOne("subtype").isPresent() && ctx.getOne("group").isPresent() && !ctx.getOne("type").isPresent()) {
                    String group = ctx.<String> getOne("group").get();

                    PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
                    pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
                    List<Text> list = new ArrayList<>();
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group " + group + " permission")).onClick(TextActions.suggestCommand("/permlight group " + group + " permission")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group " + group + " prefix")).onClick(TextActions.suggestCommand("/permlight group " + group + " prefix")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group " + group + " suffix")).onClick(TextActions.suggestCommand("/permlight group " + group + " suffix")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group " + group + " info")).onClick(TextActions.suggestCommand("/permlight group " + group + " info")).build());
                    pages.contents(list);                
                    pages.sendTo(src);

                    return CommandResult.success();
                }

                if(ctx.getOne("subtype").isPresent() && ctx.getOne("group").isPresent() && ctx.getOne("type").isPresent()) {
                    String type = ctx.<String> getOne("type").get();
                    switch (type){
                        case "permission":
                            cb.callCmdWorldGroupPermission(ctx).accept(src);
                            break;
                        case "prefix":
                            cb.callCmdWorldGroupPrefix(ctx).accept(src);
                            break;
                        case "suffix":
                            cb.callCmdWorldGroupSuffix(ctx).accept(src);
                            break;
                        case "info":
                            cb.callCmdWorldGroupInfo(ctx).accept(src);
                            break;
                    }                
                    return CommandResult.success();
                }
            }
            
            PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
            pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
            List<Text> list = new ArrayList<>();
            list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight user")).onClick(TextActions.suggestCommand("/permlight user")).build());
            list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight group")).onClick(TextActions.suggestCommand("/permlight group")).build());
            list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world")).onClick(TextActions.suggestCommand("/permlight world")).build());
            pages.contents(list);                
            pages.sendTo(src);

            return CommandResult.success();
        
        } else {
            src.sendMessage(Text.of("Vous n'avez pas la permission d'utiliser cette commande"));
        }
                
        return CommandResult.empty();
    }
}
