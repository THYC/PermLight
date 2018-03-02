package net.teraoctet.permlight.core.command;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

/**
 *
 * @author thyc
 */
public class CommandManager {
            
    public CommandSpec CommandPermLightGroup = CommandSpec.builder()
            .description(Text.of("Manager vos permissions"))
            .permission("permlight") 
            .arguments(GenericArguments.seq( 
                    GenericArguments.optional(GenericArguments.string(Text.of("group"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("type"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("value"))),
                    GenericArguments.optional(GenericArguments.bool(Text.of("bool"))))) 
            .executor(new CommandPermLightType())
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
                    GenericArguments.optional(GenericArguments.bool(Text.of("bool"))))) 
            .executor(new CommandPermLightType())
            .build(); 
    
    public CommandSpec CommandPermLightUser = CommandSpec.builder()
            .description(Text.of("Manager vos permissions"))
            .permission("permlight") 
            .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.user(Text.of("user"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("type"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("value"))),
                    GenericArguments.optional(GenericArguments.bool(Text.of("bool")))))             
            .executor(new CommandPermLightType()) 
            .build(); 
        
    public CommandSpec CommandPermLight = CommandSpec.builder()
            .description(Text.of("Manager vos permissions"))
            .permission("permlight") 
            .arguments( 
                    GenericArguments.optional(GenericArguments.string(Text.of("info"))))
            .child(CommandPermLightGroup, "group")
            .child(CommandPermLightUser, "user")
            .child(CommandPermLightWorld, "world")
            .executor(new CommandPermLight())
            .build();
    
}
