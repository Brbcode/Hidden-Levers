package com.brb.brbmods.models.block;

import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TorchLever extends Block{
	public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), Direction.WEST, Block.makeCuboidShape(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST, Block.makeCuboidShape(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

	public TorchLever(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED, Boolean.valueOf(false)));
		//net.minecraft.block.Blocks
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return func_220289_j(state);
	}

	public static VoxelShape func_220289_j(BlockState p_220289_0_) {
		return SHAPES.get(p_220289_0_.get(HORIZONTAL_FACING));
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = this.getDefaultState();
		IWorldReader iworldreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		Direction[] adirection = context.getNearestLookingDirections();

		for(Direction direction : adirection) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction1 = direction.getOpposite();
				blockstate = blockstate.with(HORIZONTAL_FACING, direction1);
				if (blockstate.isValidPosition(iworldreader, blockpos)) {
					return blockstate;
				}
			}
		}

		return null;
	}

	/**
	* Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	* For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	* returns its solidified counterpart.
	* Note that this method should ideally consider only the specific face passed in.
	*/
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing.getOpposite() == stateIn.get(HORIZONTAL_FACING) && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : stateIn;
	}

	/**
	* Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
	* this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
	* of whether the block can receive random update ticks
	*/
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		Direction direction = stateIn.get(HORIZONTAL_FACING);
		double d0 = pos.getX() + ((stateIn.get(POWERED)) ? 0.5D : 0.5D);
		double d1 = pos.getY() + ((stateIn.get(POWERED)) ? 0.55D : 0.7D);
		double d2 = pos.getZ() + ((stateIn.get(POWERED)) ? 0.3D : 0.5D);

		Direction direction1 = direction.getOpposite();
		worldIn.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * direction1.getXOffset(), d1 + 0.22D, d2 + 0.27D * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d0 + 0.27D * direction1.getXOffset(), d1 + 0.22D, d2 + 0.27D * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
		if(stateIn.get(POWERED))
				worldIn.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, 1.0F), d0 + 0.27D * direction1.getXOffset(), d1 + 0.22D, d2 + 0.27D * direction1.getZOffset(), 0.0D, 0.0D, 0.0D);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		state = state.cycle(POWERED);
		boolean flag = state.get(POWERED);
		if (worldIn.isRemote) {
			if (flag) {
				addRedstoneParticles(state, worldIn, pos, 1.0F);
			}

			return true;
		} else {
			worldIn.setBlockState(pos, state, 3);
			float f = flag ? 0.6F : 0.5F;
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
			this.updateNeighbors(state, worldIn, pos);
			return true;
		}
	}

	private static void addRedstoneParticles(BlockState state, IWorld worldIn, BlockPos pos, float alpha) {
		Direction direction = state.get(HORIZONTAL_FACING).getOpposite();
		Direction direction1 = getFacing(state).getOpposite();
		double d0 = pos.getX() + 0.5D + 0.1D * direction.getXOffset() + 0.2D * direction1.getXOffset();
		double d1 = pos.getY() + 0.5D + 0.1D * direction.getYOffset() + 0.2D * direction1.getYOffset();
		double d2 = pos.getZ() + 0.5D + 0.1D * direction.getZOffset() + 0.2D * direction1.getZOffset();
		worldIn.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, alpha), d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving && state.getBlock() != newState.getBlock()) {
			if (state.get(POWERED)) {
				this.updateNeighbors(state, worldIn, pos);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	/**
	* @deprecated call via {@link IBlockState#getWeakPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
	* Implementing/overriding is fine.
	*/
	@Deprecated
	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.get(POWERED) ? 15 : 0;
	}

	/**
	* @deprecated call via {@link IBlockState#getStrongPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
	* Implementing/overriding is fine.
	*/
	@Deprecated
	@Override
	public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.get(POWERED) && getFacing(blockState) == side ? 15 : 0;
	}

	/**
	* Can this block provide power. Only wire currently seems to have this change based on its state.
	* @deprecated call via {@link IBlockState#canProvidePower()} whenever possible. Implementing/overriding is fine.
	*/
	@Deprecated
	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}

	private void updateNeighbors(BlockState p_196378_1_, World p_196378_2_, BlockPos p_196378_3_) {
		p_196378_2_.notifyNeighborsOfStateChange(p_196378_3_, this);
		p_196378_2_.notifyNeighborsOfStateChange(p_196378_3_.offset(getFacing(p_196378_1_).getOpposite()), this);
	}

	protected static Direction getFacing(BlockState p_196365_0_)
	{
		return p_196365_0_.get(HORIZONTAL_FACING);
	}

	/**
	* Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	* blockstate.
	* @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
	* fine.
	*/
	@Deprecated
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
	}

	/**
	* Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	* blockstate.
	* @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
	*/
	@Deprecated
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
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
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
	      Direction direction = state.get(HORIZONTAL_FACING);
	      BlockPos blockpos = pos.offset(direction.getOpposite());
	      BlockState blockstate = worldIn.getBlockState(blockpos);
	      return blockstate.func_224755_d(worldIn, blockpos, direction);
	 }

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
	      builder.add(HORIZONTAL_FACING,POWERED);
	}

}
