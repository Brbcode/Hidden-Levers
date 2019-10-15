package com.brb.hiddenlevers.init;


import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;


public class ModTiles {



	public static void registerAll(RegistryEvent.Register<TileEntityType<?>> event) {
	        // Workaround for Forge event bus bug
	        if (!event.getName().equals(ForgeRegistries.TILE_ENTITIES.getRegistryName())) return;

	 }

	public static <T extends TileEntity> TileEntityType<T> register(String name,Supplier<? extends T> factoryIn, Block... validBlocks) {
		TileEntityType.Builder<T> builder = TileEntityType.Builder.create(factoryIn,validBlocks);
		TileEntityType<T> tileEntityType = builder.build(null);
		tileEntityType.setRegistryName(name);

		ForgeRegistries.TILE_ENTITIES.register(tileEntityType);

		return tileEntityType;
     }

}
