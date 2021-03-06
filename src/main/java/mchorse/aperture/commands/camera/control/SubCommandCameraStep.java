package mchorse.aperture.commands.camera.control;

import mchorse.aperture.Aperture;
import mchorse.mclib.commands.McCommandBase;
import mchorse.mclib.commands.utils.L10n;
import mchorse.mclib.utils.OpHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

/**
 * Camera's sub-command /camera step
 *
 * This command is responsible for shifting (stepping) player's position based
 * on absolute or relative value. Very useful for adjusting position of the
 * cameras more precisely.
 */
public class SubCommandCameraStep extends McCommandBase
{
    @Override
    public String getName()
    {
        return "step";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "aperture.commands.camera.step";
    }

    @Override
    public String getSyntax()
    {
        return "{l}{6}/{r}camera {8}step{r} {7}[x] [y] [z]{r}";
    }

    @Override
    public L10n getL10n()
    {
        return Aperture.l10n;
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (!OpHelper.isPlayerOp())
        {
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;

        double x = args.length > 0 ? SubCommandCameraRotate.parseRelativeDouble(args[0], player.posX) : player.posX;
        double y = args.length > 1 ? SubCommandCameraRotate.parseRelativeDouble(args[1], player.posY) : player.posY;
        double z = args.length > 2 ? SubCommandCameraRotate.parseRelativeDouble(args[2], player.posZ) : player.posZ;

        player.setPositionAndRotation(x, y, z, player.rotationYaw, player.rotationPitch);
        player.setVelocity(0, 0, 0);
    }
}