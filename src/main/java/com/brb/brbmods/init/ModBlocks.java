package com.brb.brbmods.init;

import javax.annotation.Nullable;

import com.brb.brbmods.BrbMod;
import com.brb.brbmods.models.block.FuildBarrierBlock;
import com.brb.brbmods.models.block.TorchLever;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public final class ModBlocks {
	public static Block torch_lever = register("torch_lever",ItemGroup.REDSTONE,new TorchLever(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0).lightValue(14).sound(SoundType.WOOD)));
	public static Block torch_lever_on = register("torch_lever_on",new TorchLever(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0).lightValue(14).sound(SoundType.WOOD)),null);
    public static Block fluidbarrier = register("fluid_barrier_block", new FuildBarrierBlock(Block.Properties.create(Material.BARRIER).hardnessAndResistance(-1.0F, 3600000.8F).noDrops()));


    private ModBlocks() {}

    public static void registerAll(RegistryEvent.Register<Block> event) {
        // Workaround for Forge event bus bug
        if (!event.getName().equals(ForgeRegistries.BLOCKS.getRegistryName())) return;
    }

    private static <T extends Block> T register(String name, T block) {
        BlockItem item = new BlockItem(block, new Item.Properties());
        return register(name, block, item);
    }


	private static <T extends Block> T register(String name,ItemGroup group, T block) {
        BlockItem item = new BlockItem(block, new Item.Properties().group(group));
        return register(name, block, item);
    }

    private static <T extends Block> T register(String name, T block, @Nullable BlockItem item) {
        ResourceLocation id = BrbMod.getId(name);
        block.setRegistryName(id);
        ForgeRegistries.BLOCKS.register(block);

        if (item != null) {
            ModItems.BLOCKS_TO_REGISTER.put(name, item);
        }

        return block;
    }
}
