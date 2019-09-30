package com.brb.brbmods.models.block;


import com.brb.brbmods.models.tiles.FuildBarrierTile;

import net.minecraft.block.BarrierBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

@SuppressWarnings("deprecation")
public class FuildBarrierBlock extends BarrierBlock implements ITileEntityProvider{

	public FuildBarrierBlock(Properties properties) {
		super(properties.doesNotBlockMovement());		
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new FuildBarrierTile();
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new FuildBarrierTile();
	}
}
