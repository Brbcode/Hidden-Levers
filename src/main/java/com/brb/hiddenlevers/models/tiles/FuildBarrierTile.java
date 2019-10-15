package com.brb.brbmods.models.tiles;

import com.brb.brbmods.init.ModTiles;

import net.minecraft.tileentity.TileEntity;

public class FuildBarrierTile extends TileEntity{

	public FuildBarrierTile() {
		super(ModTiles.FLUID_BARRIER_TILE);
	}


	@Override
	public boolean hasFastRenderer() {
		return true;
	}




}
