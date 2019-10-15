package com.brb.hiddenlevers.init;

import javax.annotation.Nullable;

import com.brb.hiddenlevers.BrbMod;
import com.brb.hiddenlevers.models.block.BookLever;
import com.brb.hiddenlevers.models.block.TorchLever;

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
	public static Block torch_lever_on = register("torch_lever_on",new TorchLever(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0).lightValue(14).sound(SoundType.WOOD).lootFrom(ModBlocks.torch_lever)),null);
	public static final Block bookshelf_lever = register("bookshelf_lever",ItemGroup.REDSTONE, new BookLever(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD)));
	public static final Block bookshelf_lever_east = register("bookshelf_lever_east", new BookLever(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD).lootFrom(ModBlocks.bookshelf_lever)),null);
	public static final Block bookshelf_lever_west = register("bookshelf_lever_west", new BookLever(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD).lootFrom(ModBlocks.bookshelf_lever)),null);
	public static final Block bookshelf_lever_south = register("bookshelf_lever_south", new BookLever(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD).lootFrom(ModBlocks.bookshelf_lever)),null);
	public static final Block bookshelf_lever_on = register("bookshelf_lever_on", new BookLever(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD).lootFrom(ModBlocks.bookshelf_lever)),null);
	public static final Block bookshelf_lever_east_on = register("bookshelf_lever_east_on", new BookLever(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD).lootFrom(ModBlocks.bookshelf_lever)),null);
	public static final Block bookshelf_lever_west_on = register("bookshelf_lever_west_on", new BookLever(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD).lootFrom(ModBlocks.bookshelf_lever)),null);
	public static final Block bookshelf_lever_south_on = register("bookshelf_lever_south_on", new BookLever(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD).lootFrom(ModBlocks.bookshelf_lever)),null);

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
