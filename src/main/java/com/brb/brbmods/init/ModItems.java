package com.brb.brbmods.init;

import java.util.LinkedHashMap;
import java.util.Map;

import com.brb.brbmods.BrbMod;
import com.brb.brbmods.models.item.TorchLeverItem;

import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public final class ModItems {
	public static final Item TORCH_LEVER = register(new TorchLeverItem(ModBlocks.torch_lever, (new Item.Properties()).group(ItemGroup.DECORATIONS)));
	
    static final Map<String, BlockItem> BLOCKS_TO_REGISTER = new LinkedHashMap<>();

    private ModItems() {}

    public static void registerAll(RegistryEvent.Register<Item> event) {
        // Workaround for Forge event bus bug
        if (!event.getName().equals(ForgeRegistries.ITEMS.getRegistryName())) return;

        // Register block items first
        BLOCKS_TO_REGISTER.forEach(ModItems::register);

    }

    private static <T extends Item> T register(String name, T item) {
        ResourceLocation id = BrbMod.getId(name);
        item.setRegistryName(id);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }

    private static <T extends BlockItem> T register(T item) {
    	ResourceLocation blockLoc = item.getBlock().getRegistryName();
        item.setRegistryName(blockLoc);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
    
    public static Item GetBlockItem(String name) {
    	return BLOCKS_TO_REGISTER.get(name);
    }
}
