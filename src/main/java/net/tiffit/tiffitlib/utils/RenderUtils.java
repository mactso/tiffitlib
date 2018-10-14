package net.tiffit.tiffitlib.utils;

import org.lwjgl.opengl.GL11;

import codechicken.lib.vec.Cuboid6;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.tiffit.tiffitlib.TiffitLib;
import net.tiffit.tiffitlib.proxy.ClientProxy;

public class RenderUtils {
	
	public static ResourceLocation blank = new ResourceLocation(TiffitLib.MODID + ":textures/blocks/blank.png");
	public static final float pixel = 1 / 16F;
	
	public static void renderLine(Vec3d start, Vec3d end, Vec3d origin, int c){
		Minecraft mc = Minecraft.getMinecraft();
		GL11.glPushMatrix();
		GL11.glTranslated(origin.x, origin.y, origin.z);
		mc.getTextureManager().bindTexture(blank);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.color((c >> 16 & 0xff) / 255F, (c >> 8 & 0xff) / 255F, (c & 0xff) / 255F);
        double xyDistance = Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.z - start.z, 2));
        GlStateManager.rotate((float)Math.toDegrees(Math.atan2(end.x, end.z)) - 90, 0, 1, 0);
        GlStateManager.rotate((float)Math.toDegrees(Math.atan2(end.y, xyDistance)), 0, 0, 1);
        double distance = start.distanceTo(end);
		BufferBuilder builder = Tessellator.getInstance().getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		
		builder.pos(start.x, start.y, start.z).tex(0, 0).endVertex();
		builder.pos(start.x, start.y + pixel, start.z).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y + pixel, start.z).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y, start.z).tex(0, 0).endVertex();
		
		builder.pos(start.x, start.y, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y + pixel, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x, start.y + pixel, start.z + pixel).tex(0, 0).endVertex();
		
		builder.pos(start.x, start.y + pixel, start.z).tex(0, 0).endVertex();
		builder.pos(start.x, start.y + pixel, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y + pixel, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y + pixel, start.z).tex(0, 0).endVertex();
		
		builder.pos(start.x, start.y, start.z).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y, start.z).tex(0, 0).endVertex();
		builder.pos(start.x + distance, start.y, start.z + pixel).tex(0, 0).endVertex();
		builder.pos(start.x, start.y, start.z + pixel).tex(0, 0).endVertex();
		Tessellator.getInstance().draw();
		GL11.glPopMatrix();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
	}
	
	public static void renderBoxColor(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int color){
		float b = ((color)&0xFF) /255f;
		float g = ((color>>8)&0xFF) /255f;
		float r = ((color>>16)&0xFF) /255f;
		float a = ((color>>24)&0xFF) /255f;
		GlStateManager.disableTexture2D();
		GlStateManager.color(r, g, b, a);
		codechicken.lib.render.RenderUtils.drawCuboidSolid(new Cuboid6(minX, minY, minZ, maxX, maxY, maxZ));
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableTexture2D();
	}
	
	public static void renderCircle(){
		bind(blank);
		GL11.glCallList(ClientProxy.defierSphereIdOutside);
		GL11.glCallList(ClientProxy.defierSphereIdInside);
	}
	
	public static void bind(ResourceLocation loc){
		Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
	}
	
}
