package com.brb.hiddenlevers.models.block;


import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;


public class BookLever extends BaseLever{

	public BookLever(Properties builder) {
		super(builder);
	}

	/**
	* Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
	* transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
	*/
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		if(!state.get(HORIZONTAL_FACING).equals(hit.getFace()))
			return false;
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}







}
