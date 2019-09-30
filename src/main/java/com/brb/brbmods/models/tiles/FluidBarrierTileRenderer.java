package com.brb.brbmods.models.tiles;

import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

import com.brb.brbmods.BrbMod;
import com.brb.brbmods.models.model.FluidBarrierModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;



public class FluidBarrierTileRenderer extends TileEntityRenderer<FuildBarrierTile>  {
	private final FluidBarrierModel model = new FluidBarrierModel();

	@Override
	public void renderTileEntityFast(FuildBarrierTile te, double x, double y, double z, float partialTicks,
			int destroyStage, BufferBuilder buffer) {

		if(GetPlayerItem().getRegistryName().toString().equals("brbmods:fluid_barrier_block") ||
				GetPlayerOffHandItem().getRegistryName().toString().equals("brbmods:fluid_barrier_block")	)
		{
			Vector3f block_pos = new Vector3f((float) x+0.5f,(float) y +0.5f,(float) z+0.5f);
			if(block_pos.length()<=1) return;

			GL11.glPushMatrix();

			GL11.glTranslatef(block_pos.getX() , block_pos.getY() , block_pos.getZ() );

			Vec3d d = new Vec3d(x+0.5,y +0.5f,z+0.5).normalize();
			Vec3d cp = d.crossProduct(new Vec3d(0,1,0).normalize());

			float angle = (float) (Math.atan2(d.x, d.z)*180/Math.PI);
			float angley = - (float) (block_pos.angle(new Vector3f(0,1,0))*180/Math.PI);

			GL11.glRotated(angley, cp.x, cp.y, cp.z);
			GL11.glRotatef(angle, 0, 1, 0);

			ResourceLocation location = BrbMod.getId("textures/block/fluid_barrier.png");

			Minecraft.getInstance().getTextureManager().bindTexture(location);

			model.render();

			GL11.glPopMatrix();
		}


	}

	private Item GetPlayerItem() {
		return Minecraft.getInstance().player.getHeldItemMainhand().getItem();
	}

	private Item GetPlayerOffHandItem() {
		return Minecraft.getInstance().player.getHeldItemOffhand().getItem();
	}


}
