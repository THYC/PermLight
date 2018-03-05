package net.teraoctet.permlight.core.command;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.api.Sponge;
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
import org.spongepowered.api.world.World;

/**
 *
 * @author thyc
 */
public class CommandPermLightWorld implements CommandExecutor {
    
    private final CommandCallBack cb = new CommandCallBack();
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("permlight")) {
            
            //-----
            // WORLD
            //-----
            
            if(ctx.getOne("world").isPresent()){
                World world = ctx.<World>getOne("world").get();
                
                if(!ctx.getOne("subtype").isPresent()) {

                    PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
                    pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
                    List<Text> list = new ArrayList<>();
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group ")).onClick(TextActions.runCommand("/permlight group ")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " info ")).onClick(TextActions.runCommand("/permlight info")).build());
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
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group")).onClick(TextActions.runCommand("/permlight world " + world.getName() + " group")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " permission")).onClick(TextActions.runCommand("/permlight world " + world.getName() + " permission")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " info")).onClick(TextActions.runCommand("/permlight world " + world.getName() + " info")).build());
                    pages.contents(list);                
                    pages.sendTo(src);

                    return CommandResult.success();
                }
                
                if(ctx.getOne("subtype").isPresent() && ctx.getOne("group").isPresent() && !ctx.getOne("type").isPresent()) {
                    String group = ctx.<String> getOne("group").get();

                    PaginationList.Builder pages = getGame().getServiceManager().provide(PaginationService.class).get().builder();
                    pages.title(Text.builder().color(TextColors.GREEN).append(Text.of(TextColors.AQUA, "PermLight")).build());
                    List<Text> list = new ArrayList<>();
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group " + group + " permission")).onClick(TextActions.runCommand("/permlight group " + group + " permission")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group " + group + " prefix")).onClick(TextActions.runCommand("/permlight group " + group + " prefix")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group " + group + " suffix")).onClick(TextActions.runCommand("/permlight group " + group + " suffix")).build());
                    list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + world.getName() + " group " + group + " info")).onClick(TextActions.runCommand("/permlight group " + group + " info")).build());
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
            Sponge.getServer().getWorlds().forEach((w) -> {
                list.add(Text.builder().color(TextColors.YELLOW).append(of("/permlight world " + w.getName())).onClick(TextActions.runCommand("/permlight world " + w.getName())).build());
            });
            pages.contents(list);                
            pages.sendTo(src);

            return CommandResult.success();
        
        } else {
            src.sendMessage(Text.of("Vous n'avez pas la permission d'utiliser cette commande"));
        }
                
        return CommandResult.empty();
    }
}
