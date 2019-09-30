package com.brb.brbmods;

import com.brb.brbmods.init.ModBlocks;
import com.brb.brbmods.init.ModItems;
import com.brb.brbmods.init.ModTiles;
import com.brb.brbmods.models.tiles.FluidBarrierTileRenderer;
import com.brb.brbmods.models.tiles.FuildBarrierTile;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

class SideProxy {
    SideProxy() {
        BrbMod.LOGGER.debug("SideProxy init");

        // Add listeners for common events
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::imcEnqueue);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::imcProcess);

        // Add listeners for registry events
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModBlocks::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModItems::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModTiles::registerAll);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        BrbMod.LOGGER.debug("SideProxy commonSetup");
    }

    private void imcEnqueue(InterModEnqueueEvent event) {
        BrbMod.LOGGER.debug("SideProxy imcEnqueue");
    }

    private void imcProcess(InterModProcessEvent event) {
        BrbMod.LOGGER.debug("SideProxy imcProcess");
    }

    static class Client extends SideProxy {
        Client() {
            BrbMod.LOGGER.debug("SideProxy.Client init");
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
            ClientRegistry.bindTileEntitySpecialRenderer(FuildBarrierTile.class,new FluidBarrierTileRenderer());
        }

        private void clientSetup(FMLClientSetupEvent event) {
            BrbMod.LOGGER.debug("SideProxy.Client clientSetup");
        }
    }

    static class Server extends SideProxy {
        Server() {
            BrbMod.LOGGER.debug("SideProxy.Server init");
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
        }

        private void serverSetup(FMLDedicatedServerSetupEvent event) {
            BrbMod.LOGGER.debug("SideProxy.Server serverSetup");
        }
    }
}
