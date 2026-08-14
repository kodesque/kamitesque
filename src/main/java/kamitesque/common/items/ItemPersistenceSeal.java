package kamitesque.common.items;

import com.invadermonky.thaumicapi.api.ThaumicAPI;
import kamitesque.client.fx.FXDispatcherInternal;
import kamitesque.common.templates.ItemKTBase;
import kamitesque.init.KTItems;
import kamitesque.init.KTSounds;
import kamitesque.root.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;

public class ItemPersistenceSeal extends ItemKTBase {

    public ItemPersistenceSeal(String name, String... variants) {
        super(name, variants);
        this.setMaxStackSize(1);

        this.addPropertyOverride(
                new ResourceLocation(Main.MODID, "charging"),
                new IItemPropertyGetter() {

                    @SideOnly(Side.CLIENT)
                    @Override
                    public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {

                        return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
                    }
                }
        );
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 3600;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(
            World world,
            EntityPlayer player,
            EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);
        boolean pass = false;

        if (!player.isCreative()) {

            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                if (player.inventory.getStackInSlot(i).getItem().equals(Items.TOTEM_OF_UNDYING)) {
                    pass = true;
                }
            }
        } else {
            pass = true;
        }

        if (hand.equals(EnumHand.OFF_HAND)
                || player.getHeldItemOffhand().isEmpty()
                || player.getHeldItemOffhand().getItem().equals(KTItems.persistence_seal)
                || (player.getHeldItemOffhand().getTagCompound() != null && player.getHeldItemOffhand().getTagCompound().hasKey("kamitesque.persistent"))
                || player.getHeldItemOffhand().getItem() instanceof ItemBlock
                || !pass) {

            return new ActionResult<>(
                    EnumActionResult.FAIL,
                    stack
            );
        }

        player.setActiveHand(hand);

        return new ActionResult<>(
                EnumActionResult.SUCCESS,
                stack
        );
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

        float volume = 0.1F + (float)(((stack.getMaxItemUseDuration() - count) / 20.0F));

        if (count != 0 && count % 10 == 0) {
            player.world.playSound(
                    null,
                    player.posX,
                    player.posY,
                    player.posZ,
                    KTSounds.stamping,
                    SoundCategory.PLAYERS,
                    volume,
                    1.0F
            );

            for (int i = 0; i < 4; i++) {

                Vec3d look = player.getLookVec();

                FXDispatcherInternal.blockRunes(
                        player.posX - 0.5 + look.x * 0.5,
                        player.posY + 1.0,
                        player.posZ - 0.5 + look.z * 0.5,
                        Color.ORANGE.getRed() + player.world.rand.nextFloat() * 0.7f,
                        Color.ORANGE.getGreen(),
                        Color.ORANGE.getBlue(),
                        15,
                        0.03f,
                        player.world
                );
            }
        }

    }

    public @NotNull IRarity getForgeRarity(@NotNull ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public void onPlayerStoppedUsing(
            ItemStack stack,
            World world,
            EntityLivingBase entity,
            int timeLeft) {

        if (entity instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer)entity;

            int usedTime = getMaxItemUseDuration(stack) - timeLeft;

            boolean pass = false;

            if (usedTime >= 20) {

                if (!player.isCreative()) {

                        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                            if (player.inventory.getStackInSlot(i).getItem().equals(Items.TOTEM_OF_UNDYING)) {
                                ItemStack totem = player.inventory.getStackInSlot(i);
                                totem.shrink(1);
                                pass = true;
                            }
                        }
                    } else {
                    pass = true;
                }
                }

            if (!pass) {return;}

            entity.world.playSound(
                    null,
                    entity.posX,
                    entity.posY,
                    entity.posZ,
                    KTSounds.persistence,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            NBTTagCompound nbt = entity.getHeldItemOffhand().getTagCompound() != null ? entity.getHeldItemOffhand().getTagCompound() : new NBTTagCompound();
            nbt.setBoolean("kamitesque.persistent", true);
            nbt.setUniqueId("kamitesque.persistent.owner", entity.getUniqueID());
            entity.getHeldItemOffhand().setTagCompound(nbt);

            ThaumicAPI.addWarpingToStack(entity.getHeldItemOffhand(), 1);

            ((EntityPlayer)entity).getCooldownTracker().setCooldown(this, 600);

            }
        }
    }



