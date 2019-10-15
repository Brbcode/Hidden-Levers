package com.brb.brbmods.models.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;

public class FluidBarrierModel extends Model {
	private final RendererModel plane;

	public FluidBarrierModel() {
		this.plane = new RendererModel(this, 0, 0);
		this.plane.addBox(-8.0F, 0.0F, -8.0F, 16, 1, 16, 0.0F);

		//net.minecraft.client.renderer.tileentity.BedTileEntityRenderer
	}

	public void render() {
		this.plane.render(0.0625F);
	}
}
