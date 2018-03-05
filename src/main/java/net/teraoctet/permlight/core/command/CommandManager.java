package net.teraoctet.permlight.core.command;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

/**
 *
 * @author thyc
 */
public class CommandManager {
    
    public CommandSpec CommandPermLightReload = CommandSpec.builder()
            .description(Text.of("Reload permission"))
            .permission("permlight") 
            .executor(new CommandPermLightReload())
            .build(); 
    
    public CommandSpec CommandPermLightGroup = CommandSpec.builder()
            .description(Text.of("Manager vos permissions"))
            .permission("permlight") 
            .arguments(GenericArguments.seq( 
                    GenericArguments.optional(GenericArguments.string(Text.of("group"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("type"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("value"))),
                    GenericArguments.optional(GenericArguments.bool(Text.of("bool"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("cde"))))) 
            .executor(new CommandPermLightGroup())
            .build(); 
    
    public CommandSpec CommandPermLightWorld = CommandSpec.builder()
            .description(Text.of("Manager vos permissions"))
            .permission("permlight") 
            .arguments(GenericArguments.seq( 
                    GenericArguments.optional(GenericArguments.world(Text.of("world"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("subtype"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("group"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("type"))),                  
                    GenericArguments.optional(GenericArguments.string(Text.of("value"))),
                    GenericArguments.optional(GenericArguments.bool(Text.of("bool"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("cde"))))) 
            .executor(new CommandPermLightWorld())
            .build(); 
    
    public CommandSpec CommandPermLightUser = CommandSpec.builder()
            .description(Text.of("Manager vos permissions"))
            .permission("permlight") 
            .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.user(Text.of("user"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("type"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("value"))),
                    GenericArguments.optional(GenericArguments.bool(Text.of("bool"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("cde"))))) 
            .executor(new CommandPermLightUser()) 
            .build(); 
        
    public CommandSpec CommandPermLight = CommandSpec.builder()
            .description(Text.of("Manager vos permissions"))
            .permission("permlight") 
            .arguments( 
                    GenericArguments.optional(GenericArguments.string(Text.of("info"))))
            .child(CommandPermLightGroup, "group")
            .child(CommandPermLightUser, "user")
            .child(CommandPermLightWorld, "world")
            .child(CommandPermLightReload, "reload")
            .executor(new CommandPermLight())
            .build();
    
    public CommandSpec CommandReload = CommandSpec.builder()
            .description(Text.of("Reload permission"))
            .permission("permlight") 
            .executor(new CommandPermLightReload())
            .build();
}
