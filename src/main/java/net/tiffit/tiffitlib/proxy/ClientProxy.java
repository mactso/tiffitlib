package net.tiffit.tiffitlib.proxy;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.tiffit.tiffitlib.render.WorldRenderTask;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	public static int defierSphereIdOutside;
	public static int defierSphereIdInside;
	
	public static List<WorldRenderTask> worldrendertasks = new ArrayList<WorldRenderTask>();
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		{
			Sphere sphere = new Sphere();
			sphere.setDrawStyle(GLU.GLU_FILL);
			sphere.setNormals(GLU.GLU_SMOOTH);
			sphere.setOrientation(GLU.GLU_OUTSIDE);

			defierSphereIdOutside = GL11.glGenLists(1);
			GL11.glNewList(defierSphereIdOutside, GL11.GL_COMPILE);
			sphere.draw(0.5F, 30, 30);
			GL11.glEndList();

			sphere.setOrientation(GLU.GLU_INSIDE);
			defierSphereIdInside = GL11.glGenLists(1);
			GL11.glNewList(defierSphereIdInside, GL11.GL_COMPILE);
			sphere.draw(0.5F, 30, 30);
			GL11.glEndList();
		}
	}
	
	@SubscribeEvent
	public static void onWorldRender(RenderWorldLastEvent e) {
		synchronized (worldrendertasks) {
			List<WorldRenderTask> finished = new ArrayList<WorldRenderTask>();
			for (WorldRenderTask task : worldrendertasks) {
				if (task.canRun()){
					task.run();
					if(task.done)finished.add(task);
				}
			}
			worldrendertasks.removeAll(finished);
		}
	}
	
	public static void addWorldRenderTask(WorldRenderTask task){
		synchronized (worldrendertasks) {
			worldrendertasks.add(task);
		}
	}
}
